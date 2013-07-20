
import jinja2
import webapp2
import os

class Detector(webapp2.RequestHandler):

	def get(self):
		uastring = self.request.headers.get('user_agent')
		if "Mobile" in uastring and "Safari" in uastring:
			JINJA_ENVIRONMENT = jinja2.Environment(loader=jinja2.FileSystemLoader(os.path.dirname(__file__)),extensions=['jinja2.ext.autoescape'])
			#Render regular index
			template = JINJA_ENVIRONMENT.get_template('index.html')
		else:
			JINJA_ENVIRONMENT = jinja2.Environment(loader=jinja2.FileSystemLoader(os.path.dirname(__file__)),extensions=['jinja2.ext.autoescape'])			
			template = JINJA_ENVIRONMENT.get_template('mobile.html')

		self.response.out.write(template.render())
		
application = webapp2.WSGIApplication([
										('/', Detector), 
									], debug=True)
