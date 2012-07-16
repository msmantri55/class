#!/usr/bin/python

'''
Set up the datastore, as described at
<http://code.google.com/appengine/docs/python/gettingstarted/usingdatastore.html>.
'''

import cgi
from google.appengine.ext import db

class Person (db.Model):
	name = db.ReferenceProperty(required = True)
	username = db.StringProperty()
	emails = db.ListProperty(db.Email, required = True)
	websites = db.ListProperty(db.Key)
	courses = db.ListProperty(db.Key)
	phone_numbers = db.ListProperty(db.Key)
	research_areas = db.ListProperty(db.Key)
	research_groups = db.ListProperty(db.Key)
	degrees = db.ListProperty(db.Key)
	offices = db.ListProperty(db.Key)
	presentations = db.ListProperty(db.Key)
	awards = db.ListProperty(db.Key)
	writings = db.ListProperty(db.Key)
	office_hours = db.ListProperty(db.Key)

class Name (db.Model):
	first = db.StringProperty(required = True)
	middle = db.StringProperty()
	last = db.StringProperty(required = True)
	suffix = db.StringProperty()

class Faculty (Person):
	title = db.StringProperty(required = True, choices = set(['Lecturer', 'Senior Lecturer', 'Post Doc', 'Professor', 'Researcher', 'Adjunct Professor', 'Associate Professor', 'Assistant Professor', 'Visiting Professor']))
	advisees = db.ListProperty(db.Key)

class Student (Person):
	status = db.StringProperty(required = True, choices = set(['PhD Student', 'Master\'s Student', 'Post Doc']))
	advisors = db.ListProperty(db.Key)
	# this must be here for schema related reasons. during export, order matters, 
	# and things common only to faculty have to happen after things common to person.
	# since advisee is in person... anyway, i know it's lame but trust me :)
	advisees = db.ListProperty(db.Key)

class Website (db.Model):
	#person = db.ReferenceProperty(required = True, collection_name = 'persons')
	address = db.LinkProperty(required = True)
	group = db.ReferenceProperty(required = True, collection_name = 'research_groups')
	authors = db.ListProperty(db.Key, required = True)
	
	
class PhoneNumber (db.Model):
	type = db.StringProperty(required = True, choices = set(['home', 'work', 'cell', 'fax', 'pager']))
	country_code = db.StringProperty()
	area_code = db.StringProperty()
	number = db.StringProperty(required = True)

class ResearchArea (db.Model):
	name = db.TextProperty(required = True)

class ResearchGroup (db.Model):
	name = db.StringProperty(required = True)

class Degree (db.Model):
	degree_types = ['Certificate of Completion', 'Associate\'s Degree', 'Bachelor\'s Degree', 'Master\'s Degree', 'Doctoral Degree', 'Honorary Degree', 'Laurea']
	year = db.StringProperty(required = True)
	institution = db.StringProperty(required = True)
	type = db.StringProperty(choices = set(degree_types), required = True)
	specialization = db.StringProperty(required = True)

class Award (db.Model):
	name = db.StringProperty(required = True)
	year = db.StringProperty(required = True)

class Building (db.Model):
	name = db.StringProperty(required = True)
	abbreviation = db.StringProperty()

class Location (db.Model):
	building = db.ReferenceProperty(required = True)
	floor = db.StringProperty(required = True)
	room = db.StringProperty(required = True)

class Schedule (db.Model):
	start_date = db.StringProperty(required = True)
	start_time = db.StringProperty(required = True)
	days = db.StringListProperty(required = True)
	end_time = db.StringProperty(required = True)
	end_date = db.StringProperty()

class Event (db.Model):
	type = db.StringProperty(required = True, choices = set(['Office Hours', 'Meeting', 'Seminar']))
	schedule = db.ReferenceProperty(required = True, collection_name = 'event_schedules')
	location = db.ReferenceProperty(required = True, collection_name = 'event_locations')

class CourseNumber (db.Model):
	department = db.StringProperty(required = True)
	number = db.StringProperty(required = True)

class StaffMember (db.Model):
	name = db.ReferenceProperty(required = True)
	office_hours = db.ReferenceProperty(collection_name = 'staff_member_office_hours')

class Course (db.Model):
	unique_id = db.StringProperty(required = True)
	name = db.StringProperty(required = True)
	description = db.TextProperty(required = True)
	course_number = db.ReferenceProperty(required = True, collection_name = 'course_numbers')
	semester = db.StringProperty(required = True, choices = set(['Fall', 'Spring', 'Summer First Term', 'Summer Second Term', 'Summer Whole Term', 'Summer Nine Week']))
	year = db.StringProperty(required = True)
	staff_members = db.ListProperty(db.Key, required = True)
	schedule = db.ReferenceProperty(required = True, collection_name = 'course_schedules')

class Article (db.Model):
	journal = db.StringProperty(required = True)
	edition = db.StringProperty(required = True)

class PublishInfo (db.Model):
	publisher = db.StringProperty(required = True)
	date = db.StringProperty(required = True)
	isbn = db.StringProperty()
	article = db.ReferenceProperty()

class Writing (db.Model):
	title = db.StringProperty(required = True)
	type = db.StringProperty(required = True, choices = set(['thesis', 'article', 'tech report', 'editorial', 'book']))
	authors = db.ListProperty(db.Key)
	publish_infos = db.ListProperty(db.Key)

class Conference (db.Model):
	name = db.StringProperty(required = True)
	date = db.StringProperty(required = True)
	location = db.StringProperty(required = True)

class Presentation (db.Model):
	name = db.StringProperty(required = True)
	conference = db.ReferenceProperty(required = True, collection_name = 'presentation_conferences')

class Office (db.Model):
	location = db.ReferenceProperty(required = True, collection_name = 'office_locations')

'''
Functions for adding things to the data store.
'''

def addFaculty (name, emails, title, username = None):
	'''
	@param name
	@param emails
	@param title
	@param username
	'''

	# key is concatenation of all present name identifiers
	key = str(name.key()) + 'faculty'

	# if this faculty member already exists, return the object
	faculty = Faculty.get_by_key_name(key)
	if faculty is not None:
		return faculty
	
	emails = [db.Email(email) for email in emails]

	faculty = Faculty(key_name = key, name = name, emails = emails, title = title)
	faculty.username = username

	db.put(faculty)

	return faculty

def addStudent (name, emails, status, username = None, advisors = None):
	'''
	@param name
	@param emails
	@param status
	@param username
	@param advisors
	'''
	
	# key is key from name + 'student'
	key = str(name.key()) + 'student'

	# if this student member already exists, return the object	
	student = Student.get_by_key_name(key)
	if student is not None:
		return student

	if advisors is None: advisors = []
	if type(advisors) is Faculty: 
		advisors = [advisors]
		
		
	# Create and return a new instance if necessary
	emails = [db.Email(email) for email in emails]
	advisors = [advisor.key() for advisor in advisors]
	student = Student(key_name = key, name = name, emails = emails, status = status)
	student.username = username
	student.advisors = advisors
	db.put(student)
	for advisor in db.get(student.advisors):
		if student.key() not in advisor.advisees:
			advisor.advisees.append(student.key())
		db.put(advisor)

	return student

def addName (first, last, middle = None, suffix = None):
	'''
	@param first
	@param last
	@param middle
	@param suffix
	'''

	# key is concatenation of all name identifiers
	# if middle and suffix are NoneType, str(None) e.g. "GlennPDowningNone"
	key = first + str(middle) + last + str(suffix)

	# return DB record if already present
	name = Name.get_by_key_name(key)
	if name is not None:
		return name
	
	name = Name(key_name = key, first = first, last = last)
	if middle == 'None Listed':
		middle = None
	if suffix == 'None Listed':
		suffix = None

	name.middle = middle
	name.suffix = suffix
	db.put(name)

	return name

def addWebsite (address, group, authors):
	'''
	@param person
	@param address
	@param group
	@param authors
	'''
	# See if this website is already in the data store
	# Create and return a new instance if necessary
	# key is concatenation of all name identifiers
	# if middle and suffix are NoneType, str(None) e.g. "GlennPDowningNone"
	key = address

	# return DB record if already present
	website = Website.get_by_key_name(key)
	
	if website is not None:
		for author in authors:
			website.authors.append(author.key())
		return website
		
	authors = [author.key() for author in authors]
	website = Website(key_name = key, address = address, group = group, authors = authors)
	
	return website

def addPhoneNumber (type, number, country_code = None, area_code = None):
	'''
	@param person
	@param type
	@param country_code
	@param area_code
	@param number
	'''
	# Create and return a new instance if necessary
	key = str(country_code) + str(area_code) + str(number)
	phone_number = PhoneNumber.get_by_key_name(key)
	
	if phone_number is not None:
		return phone_number
	
	phone_number = PhoneNumber(key_name = key, type = type, number = number)
	phone_number.country_code = country_code
	phone_number.area_code = area_code
	
	return phone_number

def addResearchArea (name):
	'''
	Create research area and associate person with it. If area
	already exists, add person to its association list.

	Keyword arguments:
	name -- name of the research area
	
	return ResearchArea DB record
	'''
	research_area = ResearchArea.get_by_key_name(name)
	if research_area is None:
		research_area = ResearchArea(key_name = name, name = name)
	
	return research_area


def addResearchGroup (name):
	'''
	Create research group and associate person with it. If group
	already exists, add person to its association list.

	Keyword arguments:
	name -- name of the research group
	
	return ResearchGroup DB record
	'''
	research_group = ResearchGroup.get_by_key_name(name)
	if research_group is None:
		research_group = ResearchGroup(key_name = name, name = name)

	
	return research_group

def addDegree (year, institution, type, specialization):
	'''
	@param year
	@param institution
	@param type
	@param specialization
	'''
	key = year + institution + type + specialization
	degree = Degree.get_by_key_name(key)
	
	if degree is None:
		degree = Degree(key_name = key, year = year, institution = institution, type = type, specialization = specialization)
		#db.put(degree)
	
	#person.degrees.append(degree.key())
	#db.put(person)

	return degree

def addAward (name, year):
	'''
	@param name
	@param year
	'''

	# Create and return a new instance if necessary
	key = name + year
	award = Award.get_by_key_name(key)
	
	if award is None:
		award = Award(key_name = key, name = name, year = year)

	return award

def addBuilding (name, abbreviation = None):
	'''
	@param name
	@param abbreviation
	'''
	
	building = Building.get_by_key_name(name)
	
	# Create and return a new instance if necessary
	if building is None:
		building = Building(key_name = name, name = name, abbreviation = abbreviation)
		db.put(building)
		
	return building

def addLocation (building, floor, room):
	'''
	@param building
	@param floor
	@param room
	'''
	location = Location(building = building, floor = floor, room = room)
	db.put(location)

	return location

def addSchedule (start_date, start_time, days, end_time, end_date = None):
	'''
	@param start_date
	@param start_time
	@param dats
	@param end_time
	@param end_date
	'''
	# Create and return a new instance if necessary
	schedule = Schedule(start_date = start_date, start_time = start_time, days = days, end_time = end_time)
	schedule.end_date = end_date
	db.put(schedule)

	return schedule

def addOfficeHour(schedule, location):
	office_hour = addEvent('Office Hours', schedule, location)

	return office_hour
	
def addEvent (type, schedule, location):
	'''
	@param ancestor
	@param type
	@param schedule
	@param location
	'''
	# Create and return a new instance if necessary
	event = Event(type = type, schedule = schedule, location = location)

	return event

def addCourseNumber (department, number):
	'''
	@param department
	@param number
	'''
	# See if this course number is already in the data store
	key = department + number

	course_number = CourseNumber.get_by_key_name(key)

	if course_number is not None:
		return course_number

	# Create and return a new instance if necessary
	course_number = CourseNumber(key_name = key, department = department, number = number)

	return course_number

def addStaffMember (name, office_hours):
	'''
	@param name
	@param office_hours
	'''
	# See if this staff member is already in the data store
	"""query = StaffMember.all()
	query = query.filter('name = ', name)
	query = query.filter('office_hours = ', office_hours)
	
	# Return the instance if it already exists
	staff_member = query.get()
	if staff_member is not None:
		return staff_member
	"""
	# Create and return a new instance if necessary
	staff_member = StaffMember(name = name, office_hours = office_hours)
	db.put(staff_member)

	return staff_member

def addCourse (unique_id, name, description, course_number, semester, year, staff_members, schedule):
	'''
	@param person
	@param unique_id
	@param name
	@param description
	@param course_number
	@param semester
	@param staff_members
	@param year
	@param schedule
	'''
	# See if this course is already in the data store

	days = ''
	for day in schedule.schedule.days:
		days += day
	key = unique_id + days + schedule.schedule.start_time + schedule.schedule.end_time

	course = Course.get_by_key_name(key)

	if course is not None:
		return course
	
	# Create and return a new instance if necessary
	staff_members = [staff_member.key() for staff_member in staff_members]
	course = Course(key_name = key,unique_id = unique_id, name = name, description = description, course_number = course_number, semester = semester, year = year, staff_members = staff_members, schedule = schedule)

	return course

def addArticle (journal, edition):
	'''
	@param journal
	@param edition
	'''
	# key is journal name + edition
	key = journal + edition
	
	# See if this article is already in the data store
	article = Article.get_by_key_name(key)
	if article is not None:
		return article
	
	# Create and return a new instance if necessary
	article = Article(key_name = key, journal = journal, edition = edition)
	db.put(article)

	return article

def addPublishInfo (publisher, date, isbn = None, article = None):
	'''
	@param publisher
	@param date
	@param isbn
	@param article
	'''
	key = publisher + date + str(isbn)
	
	# See if this publish info is already in the data store
	publish_info = PublishInfo.get_by_key_name(key)
	if publish_info is not None:
		return publish_info
	
	# Create and return a new instance if necessary
	publish_info = PublishInfo(key_name = key, publisher = publisher, date = date, isbn = isbn, article = article)
	db.put(publish_info)

	return publish_info

def addWriting (title, type, authors, publish_infos = None):
	'''
	@param title
	@param type
	@param publish_infos
	'''
	if publish_infos == None: publish_infos = []


	# using title + type for key, e.g. "Lipstick on pigsbook"
	key = title + type

	# See if this writing is already in the data store
	writing = Writing.get_by_key_name(key)
	
	# if it already exists, we need to make sure the authors in our list are
	# all spoken for
	if writing is not None:
		for author in authors:
			if author not in writing.authors:
				writing.authors.append(author)
		# put it back in case list has changed
		db.put(writing)
		return writing
	
	# Create and return a new instance if a new writing
	authors = [author.key() for author in authors]
	publish_infos = [publish_info.key() for publish_info in publish_infos]
	writing = Writing(title = title, type = type, authors = authors, publish_infos = publish_infos)
	db.put(writing)

	return writing

def associatePersonWithWriting (person, writing):
	'''
	@param name
	@param writing
	'''
	if not person.name.key() in writing.authors:
		writing.authors.append(person.name.key())
		db.put(writing)
	
	if person is not None:
		person.writings.append(writing.key())
		db.put(person)

def addConference (name, date, location):
	'''
	@param name
	@param date
	@param location
	'''
	
	key = name + date
	# See if this conference is already in the data store
	

	# Return the instance if it already exists
	conference = Conference.get_by_key_name(key)
	if conference is not None:
		return conference
	
	# Create and return a new instance if necessary
	conference = Conference(key_name = key, name = name, date = date, location = location)
	db.put(conference)

	return conference

def addPresentation (name, conference):
	'''
	@param person
	@param name
	@param conference
	'''
	# See if this presentation is already in the data store
	key = name

	# Return the instance if it already exists
	presentation = Presentation.get_by_key_name(key)
	if presentation is not None:
		return presentation
	
	# Create and return a new instance if necessary
	return Presentation(key_name = key, name = name, conference = conference)

def addOffice (location):
	'''
	@param person
	@param location
	'''
	# See if this office is already in the data store

	key = location.building.name + location.room
	# Return the instance if it already exists
	office = Office.get_by_key_name(key)
	if office is not None:
		return office
	
	# Create and return a new instance if necessary
	office = Office(key_name = key,location = location)

	return office

def associateAdvisorWithStudent (advisor, student):
	'''
	@param advisor
	@param student
	'''
	if not advisor.key() in student.advisors:
		student.advisors.append(advisor.key())

def getAllFaculty ():
	'''
	'''
	return Faculty.all()

def getAllStudents ():
	'''	
	'''
	return Student.all() 

#Returns a set of Faculty and Student
def getAllPeople ():
	'''
	'''
	query = Student.all()
	students = query.get()

	query = Faculty.all()
	faculty = query.get()

	people = set(students) | set(faculty)

	return people

def getStudentByName (name):
	'''
	@param name 
	'''
	query = Student.all()
	query.filter('name = ', name)
	
	student = query.get()

	return student 

def getFacultyByName (name):
	'''
	@param name 
	'''
	query = Faculty.all()
	query.filter('name = ', name)

	faculty = query.get()
	
	return faculty

def getStudentByKey (key):
	'''
	@param key
	'''
	student = db.get(key)

	return student

def getFacultyByKey (key):
	'''
	@param key
	'''
	faculty = db.get(key)

	return faculty

def getNameByKey (key):
	'''
	@param key
	'''
	name = db.get(key)

	return name

def getPersonByName (name):
	'''
	@param key 
	'''
	query =  Person.all()
	query.filter('name = ', name)
	
	person = query.get()
	return person 

def getResearchGroupsWebsites (research_group):
	'''
	@param research_group
	'''
	query = Website.all()
	query.filter('group = ', research_group)

	website = query.get()

	return website

def getPersonsWebsites (person):
	'''
	@param person
	'''
	query = Website.all()
	query.filter('person = ', person)
	
	return [x for x in query]

def getPersonsPhoneNumbers (person):
	'''
	@param person
	'''
	query = PhoneNumber.all()
	query.filter('person = ', person)
	
	return [x for x in query]

def getPersonsResearchAreas (person):
	'''
	@param person
	'''
	query = ResearchArea.all()
	query.filter('people = ', person)
	
	return [x for x in query]

def getPersonsResearchGroups (person):
	'''
	@param person
	'''
	query = ResearchGroup.all()
	query.filter('people = ', person)
	
	return [x for x in query]

def getPersonsDegrees (person):
	'''
	@param person
	'''
	query = Degree.all()
	query.filter('person = ', person)
	
	return [x for x in query]
	
def getPersonsAwards (person):
	'''
	@param person
	'''
	query = Award.all()
	query.filter('person = ', person)
	
	return [x for x in query]
	
def getPersonsCourses (person):
	'''
	@param person
	'''
	query = Course.all()
	query.filter('person = ', person)
	
	return [x for x in query]

def getPersonsWritings (person):
	'''
	@param person
	'''
	query = Writing.all()
	query.filter('authors = ', person)
	
	return [x for x in query]

def getPersonsPresentations (person):
	'''
	@param key
	'''
	query = Presentation.all()
	query.filter('person = ', person)
	
	return [x for x in query]

def getPersonsOffices (person):
	'''
	@param person
	'''
	query = Office.all()
	query.filter('person = ', person)
	
	return [x for x in query]

def getConference (key):
	'''
	@param key
	'''
	conference = db.get(key)

	return conference

def getBuilding (key):
	'''
	@param key
	'''
	building = db.get(key)

	return building

def getLocation (key):
	'''
	@param key
	'''
	location = db.get(key)
	
	return location

def getSchedule ( key):
	'''
	@param key
	'''
	schedule = db.get(key)
	
	return schedule

def getCourseNumber (key):
	'''
	@param key
	'''
	course_number = db.get(key)

	return course_number

def getStaffMember (key):
	'''
	@param key
	'''
	staff_member = db.get(key)
	
	return staff_member

def getCourse (unique_id):
	'''	
	@param unique_id
	'''
	query = Course.all()
	query.filter('unique_id = ', unique_id)

	course = query.get()

	return course

def getArticle (key):
	'''
	@param key
	'''
	article = db.get(key)

	return article

def getArticlePublishInfo (article):
	'''	
	@param article
	'''
	query = PublishInfo.all()
	query.filter('article = ', article)

	publish_info = query.get()

	return publish_info