from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from google.appengine.ext.webapp.util import run_wsgi_app
import Faculty3
import cProfile
import os
import pstats

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
		
pages = [
	('/view/person', ViewPersonPage)
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