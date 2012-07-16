from google.appengine.api import users
from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from google.appengine.ext.webapp.util import run_wsgi_app
import Faculty4 as F
import os

class LoginPage (webapp.RequestHandler):
	def get (self):
		# List all faculty, sorted by last name
		faculty = [person for person in F.Faculty.all()]
		faculty = sorted(faculty, key = lambda person: person.name.last.lower())
		
		# List all students, sorted by last name
		students = [person for person in F.Student.all()]
		students = sorted(students, key = lambda person: person.name.last.lower())
		
		# Get the template and prep the values
		path = os.path.join(os.path.dirname(__file__), 'templates/base_login.html')
		values = {
			'faculty' : faculty,
			'students' : students
		}
		
		# Apply the template and output it
		self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
		self.response.out.write(template.render(path, values))

	def post (self):
		self.response.out.write(self.request.get('name'))


application = webapp.WSGIApplication([('/login', LoginPage)])
def main (): run_wsgi_app(application)
if __name__ == '__main__': main()