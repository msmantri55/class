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

class MainPage (webapp.RequestHandler):
	def debug_import(self):
		template_values = {
			'title' : 'CS 373 project 7',
			'body' : '''
				<ul>
					<li>
						<a href="/import">Import</a>
					</li>
					<li>
						<a href="/export">Export</a>
					</li>
					<li>
						<a href="/view/people">View people</a>
					</li>
					<li>
						<a href="/_ah/admin">Admin</a>
					</li>
					<li>
						<a href="/unittest">Unit test</a>
					</li>
				</ul>
			'''
		}
		path = os.path.join(os.path.dirname(__file__), 'templates/base.html')
		
		# Print everything out
		self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
		self.response.out.write(template.render(path, template_values))
		
	
		"""# Testing students
		students = Faculty3.Student.all()
		for student in students:
			self.response.out.write(student.status + '\n')
			self.response.out.write(str(student.name) + '\n')
				
		# Testing phone numbers
		phone_numbers = Faculty3.PhoneNumber.all()
		for phone_number in phone_numbers:
			self.response.out.write(phone_number.number + '\n')

		# Testing research areas
		research_groups = Faculty3.ResearchGroup.all()
		for group in research_groups:
			self.response.out.write(str(group.name) + '\n')
			for person in group.people:
				self.response.out.write(str(person) + '\n')
					
		
		# Testing websites
		websites = Faculty3.Website.all()
		for website in websites:
			self.response.out.write(website.address + '\n')
			for a in website.authors:
				self.response.out.write('site author: ' + db.get(a).first + '\n\n')
		
		# Testing degrees
		degrees = Faculty3.Degree.all()
		for degree in degrees: 
			self.response.out.write(degree.type + '\n')
		

		# Testing awards
		awards = Faculty3.Award.all()
		for award in awards:
			self.response.out.write(award.name + '\n')
		
		# Testing office_hours
		office_hours = Faculty3.Event.all()
		for office_hour in office_hours: 
			self.response.out.write(office_hour.type + '\n')
			self.response.out.write(office_hour.schedule.start_time + '\n')
			self.response.out.write(office_hour.location.building.name + '\n')
		
		# Testing courses
		courses = Faculty3.Course.all()
		for course in courses:
			self.response.out.write(course.unique_id + '\n')
			self.response.out.write(course.semester + '\n')
			self.response.out.write(course.description + '\n')
			self.response.out.write(str(course.schedule.schedule.start_date) + '\n')
		
		# Testing writings
		writings = Faculty3.Writing.all()
		for writing in writings:
			self.response.out.write(writing.title + '\n')
			self.response.out.write(str(writing.authors) + '\n')
		
		# Testing conferences
		conferences = Faculty3.Conference.all()
		for conference in conferences:
			self.response.out.write(conference.date + '\n')
			self.response.out.write(conference.location + '\n')
		
		# Testing office
		offices = Faculty3.Office.all()
		for office in offices:
			self.response.out.write(office.location.building.name + '\n')
		"""
	def get(self):
		self.debug_import()


		
pages = []
pages.append(('/', MainPage))
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
