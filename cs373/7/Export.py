from google.appengine.api import memcache
from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from google.appengine.ext.webapp.util import run_wsgi_app
from xml.dom.minidom import Document
import Faculty3
import cgi
import os
import sys
import xml.etree.ElementTree 

class ExportPage (webapp.RequestHandler):
	def get (self):

		def append_text_node(document, parent_element, this_element_name, db_text_record ):
			"""
			creates and appends a text node called this_element_name to parent_element
			with contents of db_text_record
			
			Keyword arguments:
			document the xml document being built
			parent_element the node to which this tag will belong
			this_element_name the name of the node to be created
			db_text_record text to belong with this node
			
			no return
			"""
			assert type(this_element_name) is str
			
			this_element = document.createElement(this_element_name)
			parent_element.appendChild(this_element)
			text_node = document.createTextNode(db_text_record)
			this_element.appendChild(text_node)			
	
		def append_person_optionals(document, person_element, person):
			"""
			append to xml document 'document' optional properties common to 
			a person
	
			Keyword arguments:
			document the xml document being built
			person_element the person node to which this optional tags will 
				belong
			person the DB object corresponding to this person
			
			no return
			"""
			append_websites(document, person_element, person)
			append_phones(document, person_element, person)

			append_research_areas(document, person_element, person)
			append_research_groups(document, person_element, person)
			append_degrees(document, person_element, person)
			append_awards(document, person_element, person)
			append_office_hours(document, person_element, person)
			append_courses(document, person_element, person)
			append_writings(document, person_element, person)
			append_conferences(document, person_element, person)
			append_advisees(document, person_element, person)
			append_offices(document, person_element, person)
			
		def append_name (document, person_element, person):
			name_element = document.createElement('name')
			person_element.appendChild(name_element)
			
			append_text_node(document, name_element, 'first_name', person.name.first)

			middle = person.name.middle
			if middle is not None:
				append_text_node(document, name_element, 'middle_name', middle)
					
			append_text_node(document, name_element, 'last_name', person.name.last)
							
			suffix = person.name.suffix
			if suffix is not None:
				append_text_node(document, name_element, 'suffix', suffix)

		def append_email (document, person_element, person):
			for email in person.emails:
				email_element = document.createElement('email')
				person_element.appendChild(email_element)

				email_text_node = document.createTextNode(email)
				email_element.appendChild(email_text_node)

		def append_websites (document, person_element, faculty):
			website_query = db.get(faculty.websites)
			#website_query = db.GqlQuery("SELECT * FROM Website WHERE person = :this_person", this_person=faculty.key())			
			for website in website_query:
				# main tag
				website_element = document.createElement('website')
				person_element.appendChild(website_element)

				#one url, one group, one or more author

				append_text_node(document, website_element, 'url', website.address)
				append_text_node(document, website_element, 'group', website.group.name)
				
				for author in website.authors:
					author_element = document.createElement('author')
					website_element.appendChild(author_element)

					append_text_node(document, author_element, 'first_name', db.get(author).first)
					append_text_node(document, author_element, 'last_name', db.get(author).last)

					middle = db.get(author).middle
					if middle is not None:
						append_text_node(document, author_element, 'middle_name', middle)
										
					suffix = db.get(author).suffix
					if suffix is not None:
						append_text_node(document, author_element, 'suffix', suffix)

		def append_phones(document, person_element, faculty):
			phone_query = db.get(faculty.phone_numbers)
			#phone_query = db.GqlQuery("SELECT * FROM PhoneNumber WHERE person = :this_person", this_person=faculty.key())			
			for phone in phone_query:
				phone_element = document.createElement('phone_number')
				person_element.appendChild(phone_element)

				#phone_t
				append_text_node(document, phone_element, 'phone_t', phone.type)

				#number
				append_text_node(document, phone_element, 'number', phone.number)

				#country_code
				country_code = phone.country_code
				if country_code is not None:
					append_text_node(document, phone_element, 'country_code', country_code)

				#area_code
				area_code = phone.area_code
				if area_code is not None:
					append_text_node(document, phone_element, 'area_code', area_code)

		def append_research_areas(document, person_element, person):
			#research_area_query = db.GqlQuery("SELECT * FROM ResearchArea WHERE people = :person", person=person)	
			research_area_query = db.get(person.research_areas)
			for area in research_area_query:
				append_text_node(document, person_element, 'research_area', area.name)

		def append_research_groups(document, person_element, person):
			research_group_query = db.get(person.research_groups)
			#research_group_query = db.GqlQuery("SELECT * FROM ResearchGroup WHERE people = :person", person=person)	
			for group in research_group_query:
				append_text_node(document, person_element, 'research_group', group.name)
		
		def append_degrees(document, person_element, person):
			#query = db.GqlQuery("SELECT * FROM Degree WHERE person = :person", person=person)
			query = db.get(person.degrees)

			for degree in query:
				degree_element = document.createElement('degree')
				person_element.appendChild(degree_element)
				append_text_node(document, degree_element, 'date_awarded', degree.year)
				append_text_node(document, degree_element, 'institution', degree.institution)
				append_text_node(document, degree_element, 'degree_t', degree.type)
				append_text_node(document, degree_element, 'specialization', degree.specialization)

		def append_awards(document, person_element, person):
			#query = db.GqlQuery("SELECT * FROM Award WHERE person = :person", person=person)
			query = db.get(person.awards)
			for award in query:
				award_element = document.createElement('award')
				person_element.appendChild(award_element)
				append_text_node(document, award_element, 'name', award.name)
				append_text_node(document, award_element, 'date_awarded',  award.year)
		
		def append_conferences(document, person_element, person):
			#query = db.GqlQuery("SELECT * FROM Presentation WHERE person = :person", person=person)
			query = db.get(person.presentations)
			for presentation in query:
				conference_element = document.createElement('conference')
				person_element.appendChild(conference_element)
				conference_inner_element = document.createElement('conference')
				conference_element.appendChild(conference_inner_element)

				conference = presentation.conference
				append_text_node(document, conference_inner_element, 'date',  conference.date)
				append_text_node(document, conference_inner_element, 'name',  conference.name)
				append_text_node(document, conference_inner_element, 'conf_location',  conference.location)

				append_text_node(document, conference_element, 'presentation_title',  presentation.name)

		def append_office_hours(document, person_element, person):
			#query = db.GqlQuery("SELECT * FROM Event WHERE _ancestor = :person and type = :office", person=person, office='Office Hours')
			query = db.get(person.office_hours)
			for event in query:
				event_element = document.createElement('office_hours')
				person_element.appendChild(event_element)
				
				append_text_node(document, event_element, 'event_type',  event.type) #kill me
				append_schedule(document, event_element, event)
				append_location(document, event_element, event)
		
		def append_schedule(document, event_element, event):
			schedule_element = document.createElement('schedule')
			event_element.appendChild(schedule_element)

			schedule = event.schedule

			append_text_node(document, schedule_element, 'start_time',  schedule.start_time)
			append_text_node(document, schedule_element, 'end_time',  schedule.end_time)

			days = schedule.days
			for day in days:
				append_text_node(document, schedule_element, 'day',  day)
				
			append_text_node(document, schedule_element, 'start_date',  schedule.start_date)
			end_date = schedule.end_date
			if end_date is not None:
				append_text_node(document, schedule_element, 'end_date',  schedule.end_date)

		def append_location(document, event_element, event, office = False):
			if office:
				location_element = document.createElement('office')
			else:
				location_element = document.createElement('location')	
			event_element.appendChild(location_element)
			location = event.location		

			#building
			building_element = document.createElement('building')
			location_element.appendChild(building_element)

			building = location.building
			if building.abbreviation is not None:
				append_text_node(document, building_element, 'building_abbr',  building.abbreviation)
			
			append_text_node(document, building_element, 'building_name',  building.name)
			
			#floor
			append_text_node(document, location_element, 'floor',  location.floor)
			#room
			append_text_node(document, location_element, 'room',  location.room)
			
		def append_offices(document, person_element, person):
			#query = db.GqlQuery("SELECT * FROM Office WHERE person = :person", person=person)
			query = db.get(person.offices)
			for office in query:
				append_location(document, person_element, office, True)

		def append_staff_office_hours(document, staff_element, person):
			"""
			only for staff in a course. wow.
			"""
			event_element = document.createElement('hours')
			staff_element.appendChild(event_element)
			
			append_text_node(document, event_element, 'event_type',  'Office Hours')
			append_schedule(document, event_element, person.office_hours)
			append_location(document, event_element, person.office_hours)

		def append_courses(document, person_element, person):
			#query = db.GqlQuery("SELECT * FROM Course WHERE person = :person", person=person)
			query = db.get(person.courses)
			for course in query:
				course_element = document.createElement('class')
				person_element.appendChild(course_element)
		
				append_text_node(document, course_element, 'unique_number',  course.unique_id)
				append_text_node(document, course_element, 'name',  course.name)
				append_text_node(document, course_element, 'description',  course.description)

				course_number_element = document.createElement('course_number')
				course_element.appendChild(course_number_element)
				append_text_node(document, course_number_element, 'field_of_study',  course.course_number.department)
				append_text_node(document, course_number_element, 'short_number',  course.course_number.number)
				
				append_text_node(document, course_element, 'semester',  course.semester)
				append_text_node(document, course_element, 'year',  course.year)
				staff = course.staff_members
				
				for member in staff:
					person = db.get(member)
					staff_element = document.createElement('staff')
					course_element.appendChild(staff_element)
					inner_person_element = document.createElement('person')
					staff_element.appendChild(inner_person_element)
					append_text_node(document, inner_person_element, 'first_name',  person.name.first)
					append_text_node(document, inner_person_element, 'last_name',  person.name.last)
					#if person.office_hours is not None:
					#	append_staff_office_hours(document, staff_element, person)

				#event has type, schedule, and location, but here that's all called schedule
				schedule_outer_element = document.createElement('schedule')
				course_element.appendChild(schedule_outer_element)
				append_text_node(document, schedule_outer_element, 'event_type',  'Meeting')
				append_schedule(document, schedule_outer_element, course.schedule)
				append_location(document, schedule_outer_element, course.schedule)
		
		def append_student(document, parent_element, student, tag_name):

			student_element = document.createElement(tag_name)
			parent_element.appendChild(student_element)

			append_name(document, student_element, student)
			append_email(document, student_element, student)

			append_person_optionals(document, student_element, student)

			status_element = document.createElement('status')
			student_element.appendChild(status_element)

			status_text_node = document.createTextNode(student.status)
			status_element.appendChild(status_text_node)
			

		def append_advisees(document, person_element, person):

			#query = db.GqlQuery("SELECT * FROM Student WHERE advisors = :person", person=person)
			query = db.get(person.advisees)
			for advisee in query:
				append_student(document, person_element, advisee, 'advisee')
				
			
		
		def append_writings(document, person_element, person):
			#writings_query = db.GqlQuery("SELECT * FROM Writing WHERE authors = :name", name=person.name)
			writings_query = db.get(person.writings)
			for writing in writings_query:

				writing_element = document.createElement('writing')
				person_element.appendChild(writing_element)
				append_text_node(document, writing_element, 'title', writing.title)
				publish_infos = writing.publish_infos
				for info in publish_infos:
					publish_info = db.get(info)
					publish_element = document.createElement('publish_info')
					writing_element.appendChild(publish_element)
					
					append_text_node(document, publish_element, 'publisher', publish_info.publisher)
					append_text_node(document, publish_element, 'publish_date', publish_info.date)
					
					isbn = publish_info.isbn
					if isbn is not None:
						append_text_node(document, publish_element, 'isbn', isbn)
					article = publish_info.article
					if article is not None:
						article_element = document.createElement('article')
						publish_element.appendChild(article_element)
											
						append_text_node(document, article_element, 'journal', article.journal)
						append_text_node(document, article_element, 'edition', article.edition)	
				
				append_text_node(document, writing_element, 'writing', writing.type)	
				authors = writing.authors
				for a in authors:
					author = db.get(a)
					author_element = document.createElement('author')
					writing_element.appendChild(author_element)
					append_text_node(document, author_element, 'first_name', author.first)
					append_text_node(document, author_element, 'last_name', author.last)
					
		self.response.headers['Content-Type'] = 'application/xml'

		document = Document()
		people_element = document.createElement('people')
		document.appendChild(people_element)

		for faculty in Faculty3.Faculty.all():
			faculty_element = document.createElement('faculty')
			people_element.appendChild(faculty_element)

			append_name(document, faculty_element, faculty)
			append_email(document, faculty_element, faculty)
				
			append_person_optionals(document, faculty_element, faculty)

			title_element = document.createElement('title')
			faculty_element.appendChild(title_element)

			title_text_node = document.createTextNode(faculty.title)
			title_element.appendChild(title_text_node)

		for student in Faculty3.Student.all():
			student_element = document.createElement('student')
			people_element.appendChild(student_element)

			append_name(document, student_element, student)
			append_email(document, student_element, student)

			append_person_optionals(document, student_element, student)

			status_element = document.createElement('status')
			student_element.appendChild(status_element)

			status_text_node = document.createTextNode(student.status)
			status_element.appendChild(status_text_node)

		self.response.out.write(document.toprettyxml('', ''))

pages = []
pages.append(('/export', ExportPage))
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
