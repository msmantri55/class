from google.appengine.api import memcache
from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from google.appengine.ext.webapp.util import run_wsgi_app
from xml.dom.minidom import Document
import Faculty4
import cgi
import os
import sys
import xml.etree.ElementTree 
from StringIO import StringIO

class ImportPage (webapp.RequestHandler):
	
	def __init__(self):
		self.objects_to_add = []
	
	def get (self):
		body = '''
			<form method="post" enctype="multipart/form-data">
				<fieldset>
					<legend>Import an XML instance</legend>
					
					<input type="file" name="instance">
					<input type="submit">
				</fieldset>
			</form>
		'''
		self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
		path = os.path.join(os.path.dirname(__file__), 'templates/base.html')
		template_values = { 'title' : 'Import', 'body' : body }
		self.response.out.write(template.render(path, template_values))
		
	def post (self):

		def get_text(tag):
			"""
			get string associated with an xml tag
		
			Keyword arguments:
			tag -- the xml tag to be parsed, must by type 'text'
		
			return tag if not Nonetype, else return string "None Listed"
			"""
			if tag.text is None or tag.text == "":
				return "None Listed" 
			return tag.text
	
	
	
		def put_name(name):
			"""
			creates a name object in the database
		
			Keyword arguments:
			name -- the xml tag 'name' (of a person)
		
			return reference to Name class db object
			"""
		
			#cannot be None since required
			first_name = get_text(name.find('first_name'))
			last_name = get_text(name.find('last_name'))
		
			#can be None
			middle_name = name.find('middle_name')
			suffix = name.find('suffix')
		
			if middle_name is not None:
				middle_name = get_text(middle_name)
			if suffix is not None:
				suffix = get_text(suffix)
		
			assert first_name is not None and last_name is not None
		
			return Faculty4.addName(first_name, last_name, middle_name, suffix)	
		
		def put_names(names):
			"""
			takes a list of xml names tags, adds each one to the DB
		
			Keyword arguments:
			names -- list of name xml tags (names of a person)
		
			return list of DB Name objects
			"""
		
			name_list = []
			if names is not None:
				for name in names:
					name_list.append(put_name(name))
			return name_list
		
		def put_person(person):
			"""
			pulls xml 'name',  'email', 'username', and 'title' or 'status'
			from 'person', makes DB Faculty or Student Object
		
			Keyword arguments:
			person -- xml person tag
		
			return reference to DB Faculty or Student object
			"""
		
			assert person.tag == 'faculty' or person.tag == 'student'
		
			name = put_name(person.find('name'))
		
			username = None
			if person.find('username') is not None:
				username = person.find('username').text
		
			emails = person.findall('email')
			emails = [email.text for email in emails]
		
			if person.tag == 'faculty':
				title = get_text(person.find('title'))
				person_handle = Faculty4.addFaculty(name, emails, title, username)
			elif person.tag == 'student':
				status = get_text(person.find('status'))
				person_handle = Faculty4.addStudent(name, emails, status, username)
		
			return person_handle
			
		def put_building(building):
			"""
			pulls xml 'building_name' and 'building_abbr' from 'building, makes 
			DB Building object
		
			Keyword arguments:
			building -- xml building tag
		
			return reference to DB Building object
			"""
		
			building_abbr = building.find('building_abbr')
			if building_abbr is not None:
				building_abbr = get_text(building_abbr)
			building_name = get_text(building.find('building_name'))
			
			return Faculty4.addBuilding(building_name, building_abbr)	
		
		def put_location(location):
			"""
			pulls xml 'floor' and 'room' from xml tag 'location', makes 
			Building DB object via function, associates it all together 
			in a DB Location object
		
			Keyword arguments:
			location -- xml tag 'location'
		
			return reference to DB Location object
			"""
		
			building = put_building(location.find('building'))
			floor = get_text(location.find('floor'))
			room = get_text(location.find('room'))
			return Faculty4.addLocation(building, floor, room)	
		
		def put_event(event):
			"""
			makes DB Event objects, associating them with their ... association
		
			Keyword arguments:
			association -- thing (person, staff, or class maybe?) to associate with
			event -- one event xml tag
		
			return event
			"""
		
			event_type = get_text(event.find('event_type'))
			location = put_location(event.find('location'))
			schedule = put_schedule(event.find('schedule'))
		
			return Faculty4.addEvent(event_type, schedule, location)
		
		def put_office_hour(event):
			"""
			makes DB Event objects, associating them with their ... association
		
			Keyword arguments:
			association -- thing (person, staff, or class maybe?) to associate with
			event -- one event xml tag
		
			return event
			"""
		
			location = put_location(event.find('location'))
			schedule = put_schedule(event.find('schedule'))
		
			return Faculty4.addOfficeHour(schedule, location)
		
		def put_schedule(schedule):
			"""
			pulls xml schedule sub-things from schedule, makes DB Schedule object
		
			Keyword arguments:
			building -- xml schedule tag
		
			return reference to DB Schedule object
			"""
			start_time = get_text(schedule.find('start_time'))
			end_time = get_text(schedule.find('end_time'))
		
			days = schedule.findall('day')
			day_list = []
			for day in days:
				day_list.append(get_text(day))
		
			start_date = get_text(schedule.find('start_date'))
			end_date = schedule.find('end_date')
			if end_date is not None:
				end_date = get_text(end_date)
		
			return Faculty4.addSchedule(start_date, start_time, day_list, end_time, end_date)
		
		def put_websites(websites):
			"""
			adds Website records to DB from a list of xml website tags
		
			Keyword arguments:
			websites -- list of website xml tags
		
			no return
			"""
			group_list = []
			website_list = []
			
			if websites is not None:
				for website in websites:
		
					url = get_text(website.find('url'))
					group = get_text(website.find('group'))
					group = Faculty4.addResearchGroup(group)
					
					group_list.append(group)
					authors = website.findall('author')
					
					author_list = put_names(authors)
					website_list.append(Faculty4.addWebsite(url, group, author_list))
			
			for g in group_list:
				db.put(g)
				
			return website_list
		
		def put_phone_numbers(phone_numbers):
			"""
			add a list of phone_numbers to the database, associating them with
			a person
		
			Keyword arguments:
			phone_numbers -- list of phone number xml tags
		
			no return
			"""
			l = []
			if phone_numbers is not None:
				for phone_number in phone_numbers:
		
					phone_t = get_text(phone_number.find('phone_t'))
					number = get_text(phone_number.find('number'))
		
					country_code = phone_number.find('country_code')
					if country_code is not None:
						country_code = country_code.text
					else:
						country_code = '01'
		
					area_code = phone_number.find('area_code')
					if area_code is not None:
						area_code = area_code.text
					else:
						area_code = '512'
		
					l.append(Faculty4.addPhoneNumber(phone_t, number, country_code, area_code))
					
			return l
		
		def put_research_areas(research_areas):
			"""
			make DB ResearchArea objects, associate them with their person
		
			Keyword arguments:
			research_areas -- list of research_area xml tags
		
			Return:
			list of objects
			"""
			l = []
			if research_areas is not None:
				for research_area in research_areas:
					l.append(Faculty4.addResearchArea(get_text(research_area)))
			return l
		
		def put_research_groups(research_groups):
			"""
			makes DB ResearchGroup objects, associating them with their person
		
			Keyword arguments:
			research_groups -- list of research_area xml tags
		
			Return:
			list of objects
			"""
			l = []
			if research_groups is not None:
				for research_group in research_groups:
					l.append(Faculty4.addResearchGroup(get_text(research_group)))
					
			return l
		
		def put_degrees(degrees):
			"""
			makes DB Degree objects, associating them with their person
		
			Keyword arguments:
			degrees -- list of degree xml tags
		
			Return:
			list of degrees that have not been put
			"""
		
			l = []
			if degrees is not None:
				for degree in degrees:
					date_awarded = get_text(degree.find('date_awarded'))
					institution = get_text(degree.find('institution'))
					degree_t = get_text(degree.find('degree_t'))
					specialization = get_text(degree.find('specialization'))
			
					l.append(Faculty4.addDegree(date_awarded, institution, degree_t, specialization))
			return l
				
		def put_awards(awards):
			"""
			makes DB Award objects, associating them with their person
		
			Keyword arguments:
			awards -- list of award xml tags
		
			Return:
			list of awards that have not been put
			"""
		
			l = []
			if awards is not None:
				for award in awards:
					name = get_text(award.find('name'))
					date_awarded = get_text(award.find('date_awarded'))
					
					l.append(Faculty4.addAward(name, date_awarded))
			return l
		
		def put_courses(courses):
			"""
			makes DB Course objects, associating them with their person
		
			Keyword arguments:
			person_handle -- person that owns these courses
			courses -- list of courses to associate with person
		
			no return
			"""
		
			l = []
			
			for course in courses:
				unique = get_text(course.find('unique_number'))
				course_name = get_text(course.find('name'))
				description = get_text(course.find('description'))
		
				course_number = course.find('course_number')
		
				field_of_study = get_text(course_number.find('field_of_study'))
				short_number = get_text(course_number.find('short_number'))
		
				semester = get_text(course.find('semester'))
				year = get_text(course.find('year'))
		
				staff_members = course.findall('staff')
				staff = []
		
				for person in staff_members:
					name = put_name(person.find('person'))
					hours = person.find('hours')
					if hours is not None:
						hours = put_event(hours)
						db.put(hours)
						staff.append(Faculty4.addStaffMember(name, hours))
		
				schedule = course.find('schedule')
				schedule = put_event(schedule)
				db.put(schedule)
				course_number = Faculty4.addCourseNumber(field_of_study, short_number)
				db.put(course_number)
				l.append(Faculty4.addCourse(unique, course_name, description, course_number, semester, year, staff, schedule))
		
		
			return l
		
		def put_writings(person_handle, writings):
			"""
			makes DB Writing objects, associating them with their person
		
			Keyword arguments:
			person_handle -- person that owns these courses
			writings -- list of writings to associate with person
		
			no return
			"""
			for writing in writings:
				title = get_text(writing.find('title'))
				w_type = get_text(writing.find('writing'))
		
				publish_info_list = []
				publish_infos = writing.findall('publish_info')
				for publish_info in publish_infos:
					if publish_info is not None: 
						publisher = get_text(publish_info.find('publisher'))
						publish_date = get_text(publish_info.find('publish_date'))
						article = publish_info.find('article')
						if article is not None:
							journal = get_text(article.find('journal'))
							edition = get_text(article.find('edition'))
							article = Faculty4.addArticle(journal, edition)
						isbn = publish_info.find('isbn')
						if isbn is not None:
							isbn = get_text(isbn)
						publish_info_list.append(Faculty4.addPublishInfo(publisher, publish_date, isbn, article))
				authors = writing.findall('author')
				author_list = put_names(authors)
		
				writing = Faculty4.addWriting(title, w_type, author_list, publish_info_list)
				Faculty4.associatePersonWithWriting(person_handle, writing)
				
		def put_conferences(conferences):
			"""
			makes DB Conference objects, associating them with their person
		
			Keyword arguments:
			person_handle -- person that owns these courses
			conferences -- list of courses to associate person with
		
			no return
			"""
			l = []
			for conference in conferences:
				conf = conference.find('conference')
				date = get_text(conf.find('date'))
				name = get_text(conf.find('name'))
				conf_location = get_text(conf.find('conf_location'))
				presentation_title = get_text(conference.find('presentation_title'))
				conference = Faculty4.addConference(name, date, conf_location)
				l.append(Faculty4.addPresentation(presentation_title, conference))
			
			return l
		
		def put_advisees(person_handle, advisees):
			for advisee in advisees:
				name = put_name(advisee.find('name'))
				email = get_text(advisee.find('email'))
				status = get_text(advisee.find('status'))
		
				username = advisee.find('username')
				if username is not None:
					username = get_text(username)
		
				advisee = Faculty4.addStudent(name, [email], status, username, advisors = person_handle)
				#Faculty4.associateAdvisorWithStudent(person_handle, advisee)
		
		def put_offices(offices):
			"""
			makes DB Office objects, associating them with their person
		
			Keyword arguments:
			person_handle -- person that owns these offices
			offices -- list of offices to associate with person
		
			no return
			"""	
			l = []
			for office in offices:
				building = put_building(office.find('building'))
		
				floor = get_text(office.find('floor'))
				room = get_text(office.find('room'))
		
				location = Faculty4.addLocation(building, floor, room)
				l.append(Faculty4.addOffice(location))
			
			return l
		
		def parse_xml_load_db(xml_file, path = None):
			"""
			parses xml_file, making DB objects that model its contents 
		
			Keyword arguments:
			xml_file -- xml instance, must be valid
			path -- path to xml file. if none, defaults to source file's directory
		
			no return
			"""	
		
			tree = xml.etree.ElementTree.ElementTree()
		
			
			# Parse the input XML as an element tree
			tree.parse(xml_file)
		
			# Get the root element from the tree
			people = tree.getroot()
			assert people.tag == 'people'
		
			faculty = people.findall('faculty')
			students = people.findall('student')
			people = faculty + students
		
			for person in people:
		
				# Instantiate person.
				person_handle = put_person(person)
		
				# Phone numbers
				phone_numbers = put_phone_numbers(person.findall('phone_number'))
				#db.put(phone_numbers)
				
				for pn in phone_numbers:
					person_handle.phone_numbers.append(pn.key())
					self.objects_to_add.append(pn)
					
				# Research areas
				research_areas = put_research_areas(person.findall('research_area'))
				#db.put(research_areas)
				for r in research_areas:
					person_handle.research_areas.append(r.key())
					self.objects_to_add.append(r)
				
				# Research group
				research_groups = put_research_groups(person.findall('research_group'))
				#db.put(research_groups)
				for r in research_groups:
					person_handle.research_groups.append(r.key())
					self.objects_to_add.append(r)
				
				# Websites
				websites = put_websites(person.findall('website'))
				#db.put(websites)
				
				for w in websites:
					person_handle.websites.append(w.key())
					self.objects_to_add.append(w)
					
				# Degrees
				degrees = put_degrees(person.findall('degree'))
				#db.put(degrees)
				for d in degrees:
					person_handle.degrees.append(d.key())
					self.objects_to_add.append(d)
				
				# Awards
				awards = put_awards(person.findall('award'))
				#db.put(awards)
				for a in awards:
					person_handle.awards.append(a.key())
					self.objects_to_add.append(a)
				
				# Office Hours
				office_hours_list = []
				office_hours = person.findall('office_hours')
				for office_hour in office_hours:
					office_hours_list.append(put_office_hour(office_hour))
					#put_event(person_handle, office_hour)
				
				#Needed to put here first...
				db.put(office_hours_list)
				#self.objects_to_add.append(office_hours_list)
				
				for o in office_hours_list:
					person_handle.office_hours.append(o.key())
								
				#Office
				offices = put_offices(person.findall('office'))
				#db.put(offices)
				
				for o in offices:
					person_handle.offices.append(o.key())
					self.objects_to_add.append(o)
				
				# Courses
				courses = put_courses(person.findall('class'))
				#db.put(courses)
				for c in courses:
					person_handle.courses.append(c.key())
					self.objects_to_add.append(c)
				
				# Conference
				conferences = put_conferences(person.findall('conference'))
				
				
				#db.put(conferences)
				for c in conferences:
					person_handle.presentations.append(c.key())
					self.objects_to_add.append(c)
		
				
				db.put(person_handle)
				
				# Advisee
				put_advisees(person_handle, person.findall('advisee'))
				
				#db.put(person_handle)
		
				# Writings
				put_writings(person_handle, person.findall('writing'))
		
				
		
			db.put(self.objects_to_add)
		
		
		instance = self.request.get('instance')
		fileObject = StringIO(instance)
		parse_xml_load_db(fileObject)
		self.redirect('/')
	
pages = []
pages.append(('/import', ImportPage))
application = webapp.WSGIApplication(pages, debug=True)



def real_main ():
	run_wsgi_app(application)

def profile_main():
    # This is the main function for profiling
    # We've renamed our original main() above to real_main()
    import cProfile, pstats
    prof = cProfile.Profile()
    prof = prof.runctx("real_main()", globals(), locals())
    print "<pre>"
    stats = pstats.Stats(prof)
    stats.sort_stats("time")  # Or cumulative
    stats.print_stats(1000)  # 80 = how many to print
    # The rest is optional.
    # stats.print_callees()
    # stats.print_callers()
    print "</pre>"

if __name__ == '__main__':
	#profile_main()
	real_main()
