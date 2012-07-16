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

class Name (db.Model):
	first = db.StringProperty(required = True)
	middle = db.StringProperty()
	last = db.StringProperty(required = True)
	suffix = db.StringProperty()

class Faculty (Person):
	title = db.StringProperty(required = True, choices = set(['Lecturer', 'Senior Lecturer', 'Post Doc', 'Professor', 'Researcher', 'Adjunct Professor', 'Associate Professor', 'Assistant Professor', 'Visiting Professor']))

class Student (Person):
	status = db.StringProperty(required = True, choices = set(['PhD Student', 'Master\'s Student', 'Post Doc']))
	advisors = db.ListProperty(db.Key)

class Website (db.Model):
	person = db.ReferenceProperty(required = True, collection_name = 'persons')
	address = db.LinkProperty(required = True)
	group = db.ReferenceProperty(required = True, collection_name = 'research_groups')
	authors = db.ListProperty(db.Key, required = True)

class PhoneNumber (db.Model):
	person = db.ReferenceProperty(required = True)
	type = db.StringProperty(required = True, choices = set(['home', 'work', 'cell', 'fax', 'pager']))
	country_code = db.StringProperty()
	area_code = db.StringProperty()
	number = db.StringProperty(required = True)

class ResearchArea (db.Model):
	people = db.ListProperty(db.Key)
	name = db.StringProperty(required = True)

class ResearchGroup (db.Model):
	people = db.ListProperty(db.Key)
	name = db.StringProperty(required = True)

class Degree (db.Model):
	person = db.ReferenceProperty(required = True)
	year = db.StringProperty(required = True)
	institution = db.StringProperty(required = True)
	type = db.StringProperty(choices = set(['Certificate of Completion', 'Associate\'s Degree', 'Bachelor\'s Degree', 'Master\'s Degree', 'Doctoral Degree', 'Honorary Degree', 'Laurea']))
	specialization = db.StringProperty(required = True)

class Award (db.Model):
	person = db.ReferenceProperty(required = True)
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
	ancestor = db.ReferenceProperty(required = True)
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
	person = db.ReferenceProperty(required = True)
	unique_id = db.StringProperty(required = True)
	name = db.StringProperty(required = True)
	description = db.StringProperty(required = True)
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
	person = db.ReferenceProperty(required = True)
	name = db.StringProperty(required = True)
	conference = db.ReferenceProperty(required = True, collection_name = 'presentation_conferences')

class Office (db.Model):
	person = db.ReferenceProperty(required = True)
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
	# See if this student is already in the data store
	query = Faculty.all()
	query = query.filter('name = ', name)
	for email in emails:
		query = query.filter('emails = ', email)
	query = query.filter('title = ', title)
	query = query.filter('username = ', username)

	# Return the instance if it already exists
	faculty = query.get()
	if faculty is not None:
		return faculty
	
	# Create and return a new instance if necessary
	emails = [db.Email(email) for email in emails]
	faculty = Faculty(name = name, emails = emails, title = title)
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
	if advisors is None: advisors = []

	# See if this student is already in the data store
	query = Student.all()
	query = query.filter('name = ', name)
	for email in emails:
		query = query.filter('emails = ', email)
	query = query.filter('status = ', status)
	query = query.filter('username = ', username)
	for advisor in advisors:
		query = query.filter('advisors = ', advisor)

	# Return the instance if it already exists
	student = query.get()
	if student is not None:
		return student
	
	# Create and return a new instance if necessary
	emails = [db.Email(email) for email in emails]
	advisors = [advisor.key() for advisor in advisors]
	student = Student(name = name, emails = emails, status = status)
	student.username = username
	student.advisors = advisors
	db.put(student)

	return student

def addName (first, last, middle = None, suffix = None):
	'''
	@param first
	@param last
	@param middle
	@param suffix
	'''
	# See if this name is already in the data store
	query = Name.all()
	query = query.filter('first = ', first)
	query = query.filter('last = ', last)
	query = query.filter('middle = ', middle)
	query = query.filter('last = ', last)

	# Return the instance if it already exists
	name = query.get()
	if name is not None:
		return name

	# Create and return a new instance if necessary
	name = Name(first = first, last = last)
	name.middle = middle
	name.suffix = suffix
	db.put(name)

	return name

def addWebsite (person, address, group, authors):
	'''
	@param person
	@param address
	@param group
	@param authors
	'''
	# See if this website is already in the data store
	query = Website.all()
	query = query.filter('person = ', person)
	query = query.filter('address = ', address)
	query = query.filter('group = ', group)
	for author in authors:
		query = query.filter('authors = ', author)

	# Return the instance if it already exists
	website = query.get()
	if website is not None:
		return website
	
	# Create and return a new instance if necessary
	authors = [author.key() for author in authors]
	website = Website(person = person, address = address, group = group, authors = authors)
	db.put(website)

	return website

def addPhoneNumber (person, type, number, country_code = None, area_code = None):
	'''
	@param person
	@param type
	@param country_code
	@param area_code
	@param number
	'''
	# See if this phone number is already in the data store
	query = PhoneNumber.all()
	query = query.filter('person = ', person)
	query = query.filter('type = ', type)
	query = query.filter('country_code = ', country_code)
	query = query.filter('area_code = ', area_code)
	query = query.filter('number = ', number)

	# Return the instance if it already exists
	phone_number = query.get()
	if phone_number is not None:
		return phone_number
	
	# Create and return a new instance if necessary
	phone_number = PhoneNumber(person = person, type = type, number = number)
	phone_number.country_code = country_code
	phone_number.area_code = area_code
	db.put(phone_number)

	return phone_number

def addResearchArea (name):
	'''
	@param name
	'''
	# See if this research area is already in the data store
	query = ResearchArea.all()
	query = query.filter('name = ', name)

	# Return the instance if it already exists
	research_area = query.get()
	if research_area is not None:
		return research_area
	
	# Create and return a new instance if necessary
	research_area = ResearchArea(name = name)
	db.put(research_area)

	return research_area

def addResearchGroup (name):
	'''
	@param name
	'''
	# See if this research group is already in the data store
	query = ResearchGroup.all()
	query = query.filter('name = ', name)

	# Return the instance if it already exists
	research_group = query.get()
	if research_group is not None:
		return research_group
	
	# Create and return a new instance if necessary
	research_group = ResearchGroup(name = name)
	db.put(research_group)

	return research_group

def associatePersonWithResearchArea (person, research_area):
	'''
	@param person
	@param research_area
	'''
	# See if this person is already associated with the research area
	query = ResearchArea.all()
	query = query.filter('name = ', research_area.name)
	query = query.filter('people = ', person)

	# Don't duplicate associations
	if query.get() is not None:
		return

	research_area.people.append(person.key())

def associatePersonWithResearchGroup (person, research_group):
	'''
	@param person
	@param research_group
	'''
	# See if this person is already assocaited with the research group
	query = ResearchGroup.all()
	query = query.filter('name = ', research_group.name)
	query = query.filter('people = ', person)

	# Don't duplicate associations
	if query.get() is not None:
		return

	research_group.people.append(person.key())

def addDegree (person, year, institution, type, specialization):
	'''
	@param person
	@param year
	@param institution
	@param type
	@param specialization
	'''
	# See if this degree is already in the data store
	query = Degree.all()
	query = query.filter('person = ', person)
	query = query.filter('year = ', year)
	query = query.filter('institution = ', institution)
	query = query.filter('type = ', type)
	query = query.filter('specialization = ', specialization)

	# Return the instance if it already exists
	degree = query.get()
	if degree is not None:
		return degree

	# Create and return a new instance if necessary
	degree = Degree(person = person, year = year, institution = institution, type = type, specialization = specialization)
	db.put(degree)

	return degree

def addAward (person, name, year):
	'''
	@param person
	@param name
	@param year
	'''
	# See if this award is already in the data store
	query = Award.all()
	query = query.filter('person = ', person)
	query = query.filter('name = ', name)
	query = query.filter('year = ', year)

	# Return the instance if it already exists
	award = query.get()
	if award is not None:
		return award

	# Create and return a new instance if necessary
	award = Award(person = person, name = name, year = year)
	db.put(award)

	return award

def addBuilding (name, abbreviation = None):
	'''
	@param name
	@param abbreviation
	'''
	# See if this building is already in the data store
	query = Building.all()
	query = query.filter('name = ', name)
	query = query.filter('abbreviation = ', abbreviation)

	# Return the instance if it already exists
	building = query.get()
	if building is not None:
		return building
	
	# Create and return a new instance if necessary
	building = Building(name = name, abbreviation = abbreviation)
	db.put(building)

	return building

def addLocation (building, floor, room):
	'''
	@param building
	@param floor
	@param room
	'''
	# See if this location is already in the data store
	query = Location.all()
	query = query.filter('building = ', building)
	query = query.filter('floor = ', floor)
	query = query.filter('room = ', room)

	# Return the instance if it already exists
	location = query.get()
	if location is not None:
		return location
	
	# Create and return a new instance if necessary
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
	# See if this schedule is already in the data store
	query = Schedule.all()
	query = query.filter('start_date = ', start_date)
	query = query.filter('start_time = ', start_time)
	for day in days:
		query = query.filter('days = ', day)
	query = query.filter('end_time = ', end_time)
	query = query.filter('end_date = ', end_date)

	# Return the instance if it already exists
	schedule = query.get()
	if schedule is not None:
		return schedule
	
	# Create and return a new instance if necessary
	schedule = Schedule(start_date = start_date, start_time = start_time, days = days, end_time = end_time)
	schedule.end_date = end_date
	db.put(schedule)

	return schedule

def addEvent (ancestor, type, schedule, location):
	'''
	@param ancestor
	@param type
	@param schedule
	@param location
	'''
	# See if this event is already in the data store
	query = Event.all()
	query = query.filter('ancestor = ', ancestor)
	query = query.filter('type = ', type)
	query = query.filter('schedule = ', schedule)
	query = query.filter('location = ', location)

	# Return the instance if it already exists
	event = query.get()
	if event is not None:
		return event
	
	# Create and return a new instance if necessary
	event = Event(ancestor = ancestor, type = type, schedule = schedule, location = location)
	db.put(event)

	return event

def addCourseNumber (department, number):
	'''
	@param department
	@param number
	'''
	# See if this course number is already in the data store
	query = CourseNumber.all()
	query = query.filter('department = ', department)
	query = query.filter('number = ', number)

	# Return the instance if it already exists
	course_number = query.get()
	if course_number is not None:
		return course_number

	# Create and return a new instance if necessary
	course_number = CourseNumber(department = department, number = number)
	db.put(course_number)

	return course_number

def addStaffMember (name, office_hours):
	'''
	@param name
	@param office_hours
	'''
	# See if this staff member is already in the data store
	query = StaffMember.all()
	query = query.filter('name = ', name)
	query = query.filter('office_hours = ', office_hours)

	# Return the instance if it already exists
	staff_member = query.get()
	if staff_member is not None:
		return staff_member
	
	# Create and return a new instance if necessary
	staff_member = StaffMember(name = name, office_hours = office_hours)
	db.put(staff_member)

	return staff_member

def addCourse (person, unique_id, name, description, course_number, semester, year, staff_members, schedule):
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
	query = Course.all()
	query = query.filter('person = ', person)
	query = query.filter('unique_id = ', unique_id)
	query = query.filter('name = ', name)
	query = query.filter('description = ', description)
	query = query.filter('course_number = ', course_number)
	query = query.filter('semester = ', semester)
	query = query.filter('year = ', year)
	for staff_member in staff_members:
		query = query.filter('staff_members = ', staff_member)
	query = query.filter('schedule = ', schedule)

	# Return the instance if it already exists
	course = query.get()
	if course is not None:
		return course
	
	# Create and return a new instance if necessary
	staff_members = [staff_member.key() for staff_member in staff_members]
	course = Course(person = person, unique_id = unique_id, name = name, description = description, course_number = course_number, semester = semester, year = year, staff_members = staff_members, schedule = schedule)
	db.put(course)

	return course

def addArticle (journal, edition):
	'''
	@param journal
	@param edition
	'''
	# See if this article is already in the data store
	query = Article.all()
	query = query.filter('journal = ', journal)
	query = query.filter('edition = ', edition)

	# Return the instance if it already exists
	article = query.get()
	if article is not None:
		return article
	
	# Create and return a new instance if necessary
	article = Article(journal = journal, edition = edition)
	db.put(article)

	return article

def addPublishInfo (publisher, date, isbn = None, article = None):
	'''
	@param publisher
	@param date
	@param isbn
	@param article
	'''
	# See if this publish info is already in the data store
	query = PublishInfo.all()
	query = query.filter('publisher = ', publisher)
	query = query.filter('date = ', date)
	query = query.filter('isbn = ', isbn)
	query = query.filter('article = ', article)

	# Return the instance if it already exists
	publish_info = query.get()
	if publish_info is not None:
		return publish_info
	
	# Create and return a new instance if necessary
	publish_info = PublishInfo(publisher = publisher, date = date, isbn = isbn, article = article)
	db.put(publish_info)

	return publish_info

def addWriting (title, type, authors, publish_infos = None):
	'''
	@param title
	@param type
	@param publish_infos
	'''
	if publish_infos == None: publish_infos = []

	# See if this writing is already in the data store
	query = Writing.all()
	query = query.filter('title = ', title)
	query = query.filter('type = ', type)
	for author in authors:
		query = query.filter('authors = ', author)
	for publish_info in publish_infos:
		query = query.filter('publish_infos = ', publish_info)

	# Return the instance if it already exists
	writing = query.get()
	if writing is not None:
		return writing
	
	# Create and return a new instance if necessary
	authors = [author.key() for author in authors]
	publish_infos = [publish_info.key() for publish_info in publish_infos]
	writing = Writing(title = title, type = type, authors = authors, publish_infos = publish_infos)
	db.put(writing)

	return writing

def associatePersonWithWriting (person, writing):
	'''
	@param person
	@param writing
	'''
	# See if this author is already assocaited with the writing
	query = Writing.all()
	query = query.filter('author = ', person.name)
	query = query.filter('title = ', writing.title)

	# Don't duplicate associations
	if query.get() is not None:
		return

	writing.authors.append(person.key())

def addConference (name, date, location):
	'''
	@param name
	@param date
	@param location
	'''
	# See if this conference is already in the data store
	query = Conference.all()
	query = query.filter('name = ', name)
	query = query.filter('date = ', date)
	query = query.filter('location = ', location)

	# Return the instance if it already exists
	conference = query.get()
	if conference is not None:
		return conference
	
	# Create and return a new instance if necessary
	conference = Conference(name = name, date = date, location = location)
	db.put(conference)

	return conference

def addPresentation (person, name, conference):
	'''
	@param person
	@param name
	@param conference
	'''
	# See if this presentation is already in the data store
	query = Presentation.all()
	query = query.filter('person = ', person)
	query = query.filter('name = ', name)
	query = query.filter('conference = ', conference)

	# Return the instance if it already exists
	presentation = query.get()
	if presentation is not None:
		return presentation
	
	# Create and return a new instance if necessary
	presentation = Presentation(person = person, name = name, conference = conference)
	db.put(presentation)

	return presentation

def addOffice (person, location):
	'''
	@param person
	@param location
	'''
	# See if this office is already in the data store
	query = Office.all()
	query = query.filter('person = ', person)
	query = query.filter('location = ', location)

	# Return the instance if it already exists
	office = query.get()
	if office is not None:
		return office
	
	# Create and return a new instance if necessary
	office = Office(person = person, location = location)
	db.put(office)

	return office

def associateAdvisorWithStudent (advisor, student):
	'''
	@param advisor
	@param student
	'''
	# See if this advisor is already assocaited with the student
	query = Student.all()
	query = query.filter('name = ', student.name)
	query = query.filter('advisors = ', advisor)

	# Don't duplicate associations
	if query.get() is not None:
		return

	student.advisors.append(advisor.key())
