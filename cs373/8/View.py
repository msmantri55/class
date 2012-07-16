from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from google.appengine.ext.webapp.util import run_wsgi_app
import Faculty4
import cProfile
import os
import pstats

class ViewAwardPage (webapp.RequestHandler):
	def get (self):
		# Grab the person's key from the URL
		key = self.request.get('key')
		
		# Get the person out of the database
		award = db.get(db.Key(key))
		
		# Create the page
		template_values = {
			'award' : award
		}
		path = os.path.join(os.path.dirname(__file__), 'templates/base_view_award.html')

		# Print everything out
		self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
		self.response.out.write(template.render(path, template_values))

class ViewCoursePage (webapp.RequestHandler):
	def get (self):
		# Grab the person's key from the URL
		key = self.request.get('key')
		
		# Get the person out of the database
		course = db.get(db.Key(key))
		
		# Create the page
		template_values = {
			'course' : course
		}
		path = os.path.join(os.path.dirname(__file__), 'templates/base_view_course.html')

		# Print everything out
		self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
		self.response.out.write(template.render(path, template_values))
		
class ViewDegreePage (webapp.RequestHandler):
	def get (self):
		# Grab the person's key from the URL
		key = self.request.get('key')
		
		# Get the person out of the database
		degree = db.get(db.Key(key))
		
		# Create the page
		template_values = {
			'degree' : degree
		}
		path = os.path.join(os.path.dirname(__file__), 'templates/base_view_degree.html')

		# Print everything out
		self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
		self.response.out.write(template.render(path, template_values))

class ViewPeoplePage (webapp.RequestHandler):
	def get (self):
		# Create the page
		template_values = {
			'faculty' : [x for x in db.GqlQuery('select * from Faculty')],
			'students' : [x for x in db.GqlQuery('select * from Student')]
		}
		path = os.path.join(os.path.dirname(__file__), 'templates/base_view_people.html')
		
		# Print everything out
		self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
		self.response.out.write(template.render(path, template_values))

class ViewPersonPage (webapp.RequestHandler):
	def get (self):
		# Grab the person's key from the URL
		key = self.request.get('key')
		
		# Get the person out of the database
		person = db.get(db.Key(key))
		
		# Create the page
		template_values = {
			'person' : person,
			'phone_numbers' : [db.get(key) for key in person.phone_numbers],
			'degrees' : [db.get(key) for key in person.degrees],
			'websites' : [db.get(key) for key in person.websites],
			'research_areas' : [db.get(key) for key in person.research_areas],
			'research_groups' : [db.get(key) for key in person.research_groups],
			'awards' : [db.get(key) for key in person.awards],
			'courses' : [db.get(key) for key in person.courses],
			'offices' : [db.get(key) for key in person.offices],
			'office_hours' : [db.get(key) for key in person.office_hours],
			'presentations' : [db.get(key) for key in person.presentations],
			'writings' : [db.get(key) for key in person.writings]
		}
		path = os.path.join(os.path.dirname(__file__), 'templates/base_view_person.html')
		
		# Print everything out
		self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
		self.response.out.write(template.render(path, template_values))

class ViewPresentationPage (webapp.RequestHandler):
	def get (self):
		# Grab the person's key from the URL
		key = self.request.get('key')
		
		# Get the person out of the database
		presentation = db.get(db.Key(key))
		
		# Create the page

		template_values = {
			'presentation'	: presentation,
			'conference'	: presentation.conference
		}
		path = os.path.join(os.path.dirname(__file__), 'templates/base_view_presentation.html')

		# Print everything out
		self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
		self.response.out.write(template.render(path, template_values))

class ViewPublicationPage (webapp.RequestHandler):
	def get (self):
		# Grab the person's key from the URL
		key = self.request.get('key')
		
		# Get the person out of the database
		publication = db.get(db.Key(key))
		
		# Create the page
			
		if not publication.publish_infos:
			publication.publish_infos = []

		template_values = {
			'publication' : publication,
			'publish_infos' : [db.get(key) for key in publication.publish_infos],
			'authors' : [db.get(key) for key in publication.authors]
		}
		path = os.path.join(os.path.dirname(__file__), 'templates/base_view_publication.html')

		# Print everything out
		self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
		self.response.out.write(template.render(path, template_values))

class ViewResearchAreaPage (webapp.RequestHandler):
	def get (self):
		# Grab the person's key from the URL
		key = self.request.get('key')
		
		# Get the person out of the database
		research_area = db.get(db.Key(key))
		
		# Create the page
		faculty = [x for x in db.GqlQuery('select * from Faculty where research_areas = :1', research_area)]
		students = [x for x in db.GqlQuery('select * from Student where research_areas = :1', research_area)]
		people = faculty + students
		template_values = {
			'research_area' : research_area,
			'people' : people
		}
		path = os.path.join(os.path.dirname(__file__), 'templates/base_view_research_area.html')
		
		# Print everything out
		self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
		self.response.out.write(template.render(path, template_values))

class ViewResearchGroupPage (webapp.RequestHandler):
	def get (self):
		# Grab the person's key from the URL
		key = self.request.get('key')
		
		# Get the person out of the database
		research_group = db.get(db.Key(key))
		
		# Create the page
		faculty = [x for x in db.GqlQuery('select * from Faculty where research_groups = :1', research_group)]
		students = [x for x in db.GqlQuery('select * from Student where research_groups = :1', research_group)]
		people = faculty + students
		template_values = {
			'research_group' : research_group,
			'people' : people
		}
		path = os.path.join(os.path.dirname(__file__), 'templates/base_view_research_group.html')

		# Print everything out
		self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
		self.response.out.write(template.render(path, template_values))

pages = [
	('/view/award', ViewAwardPage),
	('/view/course', ViewCoursePage),
	('/view/degree', ViewDegreePage),
	('/view/people', ViewPeoplePage),
	('/view/person', ViewPersonPage),
	('/view/presetation', ViewPresentationPage),
	('/view/publication', ViewPublicationPage),
	('/view/research_area', ViewResearchAreaPage),
	('/view/research_group', ViewResearchGroupPage)
]
application = webapp.WSGIApplication(pages, debug = True)

def profile_main ():
	profile = cProfile.Profile()
	profile = profile.runctx('main()', globals(), locals())
	stats = pstats.Stats(profile)
	stats.sort_stats('time')
	print '<!--'
	stats.print_stats(80)
	print '-->'

def main ():
	run_wsgi_app(application)

if __name__ == '__main__':
	profile_main()