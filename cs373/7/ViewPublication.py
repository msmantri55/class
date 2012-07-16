from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from google.appengine.ext.webapp.util import run_wsgi_app
import Faculty3
import cProfile
import os
import pstats

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

pages = [
	('/view/publication', ViewPublicationPage)
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