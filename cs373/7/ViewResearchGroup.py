from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from google.appengine.ext.webapp.util import run_wsgi_app
import Faculty3
import cProfile
import os
import pstats

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