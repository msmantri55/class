from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from google.appengine.ext.webapp.util import run_wsgi_app
import cProfile
import os
import pstats

class IndexPage (webapp.RequestHandler):
	def get (self):
		path = os.path.join(os.path.dirname(__file__), 'templates/base.html')
		template_values = {
			'title' : 'CS373 project 8',
			'body' : '''
				<ul>
					<li>
						<a href="/import">Import</a>
					</li>
					<li>
						<a href="/export">Export</a>
					</li>
					<li>
						<a href="/_ah/admin">Admin</a>
					</li>
				</ul>
			'''
		}
		
		self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
		self.response.out.write(template.render(path, template_values))

pages = [
	('/', IndexPage)
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
