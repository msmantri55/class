from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from google.appengine.ext.webapp.util import run_wsgi_app
import Faculty3
import os

class EditPersonPage (webapp.RequestHandler):
	'''
	Provides an interface to edit a person in the data store.
	@param webapp.RequestHandler
	'''
	def get (self):
		'''
		GET requests show a form with all of the person's information.
		@param self
		'''
		# Get the person's key from the query string and make it a Key object
		key = self.request.get('key')
		key = db.Key(key)
		
		# Get the person from the database
		person = db.get(key)
		
		# Set up values for the template
		values = {
			'person' : person,
			'Faculty' : Faculty3.Faculty, # needed to pick Faculty.title.choices
			'Student' : Faculty3.Student, # needed to pick Student.status.choices
			'websites' : [db.get(key) for key in person.websites],
			'research_areas' : [x for x in Faculty3.ResearchArea.all()],
			'research_groups' : [x for x in Faculty3.ResearchGroup.all()]
		}
		
		# Build the path to the template file
		path = os.path.join(os.path.dirname(__file__), 'templates/base_edit_person.html')
		
		# Apply the template and print it out
		output = template.render(path, values)
		self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
		self.response.out.write(output)
	
	def post (self):
		'''
		Put all the submitted data into the data store and redirect to the person's page.
		@param self
		'''
		# Get the person's key from the query string and make it a Key object
		key = self.request.get('key')
		key = db.Key(key)
		
		# Get the person from the database
		person = db.get(key)
		
		# NAME
		name = {
			'first' : self.request.get('name.first'),
			'middle' : self.request.get('name.middle'),
			'last' : self.request.get('name.last'),
			'suffix' : self.request.get('name.suffix')
		}
		person.name = Faculty3.addName(**name)
		
		# USERNAME
		person.username = self.request.get('username')
		
		# TITLE / STATUS
		if person.kind() == 'Faculty':
			person.title = self.request.get('title')
		else:
			assert person.kind() == 'Student'
			person.status = self.request.get('status')
		
		# EMAILS
		emails = []
		for argument in self.request.arguments():
			if argument.startswith('email-') and self.request.get(argument + '-toggle') == 'on':
				email = self.request.get(argument)
				emails.append(db.Email(email))
		person.emails = emails
		
		# WEBSITES
		websites = set()
		for argument in self.request.arguments():
			if argument.startswith('website-') and self.request.get(argument + '-toggle') == 'on':
				address = self.request.get(argument)
				group = self.request.get(argument + '-group')
				group = db.get(group)
				website = Faculty3.addWebsite(address, group, [person.name])
				website = db.put(website)
				websites.add(website)
		person.websites = list(websites)
		
		# RESEARCH AREAS
		research_areas = set()
		for argument in self.request.arguments():
			if argument.startswith('research_area-') and self.request.get(argument + '-toggle') == 'on':
				research_area = self.request.get(argument)
				research_area = db.Key(research_area)
				research_areas.add(research_area)
		person.research_areas = list(research_areas)
		
		# RESEARCH GROUPS
		research_groups = set()
		for argument in self.request.arguments():
			if argument.startswith('research_group-') and self.request.get(argument + '-toggle') == 'on':
				research_group = self.request.get(argument)
				research_group = db.Key(research_group)
				research_groups.add(research_group)
		person.research_groups = list(research_groups)
		
		# Commit all changes to the data store and redirect to the person's page
		db.put(person)
		self.redirect('/view/person?key=' + str(key))
		
application = webapp.WSGIApplication([('/edit/person', EditPersonPage)], debug = True)
def main (): run_wsgi_app(application)
if __name__ == '__main__': main()