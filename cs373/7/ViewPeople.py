from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from google.appengine.ext.webapp.util import run_wsgi_app
import Faculty3
import cProfile
import os
import pstats

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
		
pages = [
	('/view/people', ViewPeoplePage)
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