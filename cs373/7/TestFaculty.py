from google.appengine.api import memcache
from google.appengine.ext import db
from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from google.appengine.ext.webapp.util import run_wsgi_app
from xml.dom.minidom import Document
import Faculty3 as F
import cgi
import os
import sys
import xml.etree.ElementTree 
import unittest

class TestFaculty (unittest.TestCase) :
    
    def test_base (self):
        self.assert_(True == True)
        
    def test_addRetrieveName(self):
        name = F.addName('This', 'Sucks', 'Really', 'Bad')
        
        self.assert_(name.first == 'This')
        self.assert_(name.last == 'Sucks')
        self.assert_(name.suffix == 'Bad')
        self.assert_(name.middle == 'Really')
        
        name_key = name.key()
        
        name = db.get(name_key)

        self.assert_(name.first == 'This')
        self.assert_(name.last == 'Sucks')
        self.assert_(name.suffix == 'Bad')
        self.assert_(name.middle == 'Really')
    
    def test_addRetrievePhone(self):
        phone = F.addPhoneNumber('work', '1234567', '01', '512')
        
        self.assert_(phone.type == 'work')
        self.assert_(phone.number == '1234567')
        self.assert_(phone.country_code == '01')
        self.assert_(phone.area_code == '512')
        
        p_key = db.put(phone)
        
        p = db.get(p_key)
        
        self.assert_(p.type == 'work')
        self.assert_(p.number == '1234567')
        self.assert_(p.country_code == '01')
        self.assert_(p.area_code == '512')
        
    def test_addRetrieveDegree(self):
        d = F.addDegree('2000', 'University of Texas', "Master's Degree", 'Crappy Code')
        
        self.assert_(d.year == '2000')
        self.assert_(d.institution == 'University of Texas')
        self.assert_(d.type == "Master's Degree")
        self.assert_(d.specialization == 'Crappy Code')
        
        key = db.put(d)
        
        d = db.get(key)
        
        self.assert_(d.year == '2000')
        self.assert_(d.institution == 'University of Texas')
        self.assert_(d.type == "Master's Degree")
        self.assert_(d.specialization == 'Crappy Code')
        
    def test_addRetrieveAward(self):
        a = F.addAward('Worst Code Design', '2010')
        
        self.assert_(a.name == 'Worst Code Design')
        self.assert_(a.year == '2010')
        
        key = db.put(a)
        
        a = db.get(key)
        
        self.assert_(a.name == 'Worst Code Design')
        self.assert_(a.year == '2010')
    
    def test_addRetrieveResearchArea(self):
        ra = F.addResearchArea('Beer quality')
        
        self.assert_(ra.name == 'Beer quality')
        
        key = db.put(ra)
        
        ra = db.get(key)    
        
        self.assert_(ra.name == 'Beer quality')

    def test_addRetrieveResearchGroup(self):
        ra = F.addResearchGroup('Beer drinkers anonymous')
        
        self.assert_(ra.name == 'Beer drinkers anonymous')
        
        key = db.put(ra)
        
        ra = db.get(key)    
        
        self.assert_(ra.name == 'Beer drinkers anonymous')
        
    def test_addRetrieveWebsite(self):
        ra = F.addResearchGroup('Beer drinkers anonymous')
        db.put(ra)
        
        auth1 = F.addName('Author', 'One')
        auth2 = F.addName('Author', 'Two')
        
        auth1_key = db.put(auth1)
        auth2_key = db.put(auth2)
        
        auths = [auth1, auth2]
        
        w = F.addWebsite('http://beer.com', ra, auths)
        
        self.assert_(w.address == 'http://beer.com')
        self.assert_(w.group.name == 'Beer drinkers anonymous')
        #self.assert_(len(w.authors) == 2)   
        self.assert_(auth1_key in w.authors and auth2_key in w.authors)
        
        w_key = db.put(w)
        
        w = db.get(w_key)
        
        self.assert_(w.address == 'http://beer.com')
        self.assert_(w.group.name == 'Beer drinkers anonymous')
        #self.assert_(len(w.authors) == 2)   
        self.assert_(auth1_key in w.authors and auth2_key in w.authors)

    def test_addBuilding(self):
        b = F.addBuilding('Deschutes Brewery', 'DB')
        
        self.assert_(b.name == 'Deschutes Brewery')
        self.assert_(b.abbreviation == 'DB')
        
        key = b.key()
        
        b = db.get(key)
        
        self.assert_(b.name == 'Deschutes Brewery')
        self.assert_(b.abbreviation == 'DB')
    
    def test_addLocation(self):
        b = F.addBuilding('Location Test', 'LT')
         
        l = F.addLocation(b, '2nd', '201') 
        
        self.assert_(l.building.name == 'Location Test' and l.building.abbreviation == 'LT')
        self.assert_(l.floor == '2nd' and l.room == '201')
        
        key = l.key()
        
        l = db.get(key)
        
        self.assert_(l.building.name == 'Location Test' and l.building.abbreviation == 'LT')
        self.assert_(l.floor == '2nd' and l.room == '201')
      
    def test_addSchedule(self):
        s = F.addSchedule('1/1/2000', '1pm', ['Monday', 'Tuesday'], '2pm', '1/1/2010')
          
        self.assert_(s.start_date == '1/1/2000')
        self.assert_(s.end_date == '1/1/2010')
        self.assert_(s.start_time == '1pm')
        self.assert_(s.end_time == '2pm')
        self.assert_('Monday' in s.days and 'Tuesday' in s.days)
        
        key = s.key()
        
        s = db.get(key)
        
        self.assert_(s.start_date == '1/1/2000')
        self.assert_(s.end_date == '1/1/2010')
        self.assert_(s.start_time == '1pm')
        self.assert_(s.end_time == '2pm')
        self.assert_('Monday' in s.days and 'Tuesday' in s.days)
      
    def test_addRetrieveOffice(self):
        b = F.addBuilding('Office Test', 'ET')
        l = F.addLocation(b, '2nd', '201') 
        
        o = F.addOffice(l)
        
        self.assert_(o.location.building.name == 'Office Test' and o.location.building.abbreviation == 'ET')
        self.assert_(o.location.floor == '2nd' and o.location.room == '201')
        
        db.put(o)
        
        key = o.key()
        
        o = db.get(key)
        
        self.assert_(o.location.building.name == 'Office Test' and o.location.building.abbreviation == 'ET')
        self.assert_(o.location.floor == '2nd' and o.location.room == '201')
        
    def test_addRetrieveEvent(self):
        b = F.addBuilding('Event Test', 'ET')
        l = F.addLocation(b, '2nd', '201') 
        s = F.addSchedule('1/1/2000', '1pm', ['Monday', 'Tuesday'], '2pm', '1/1/2010')
        
        e = F.addEvent('Meeting', s, l)
        
        self.assert_(e.type == 'Meeting')
        self.assert_(e.location.building.name == 'Event Test' and e.location.building.abbreviation == 'ET')
        self.assert_(e.location.floor == '2nd' and e.location.room == '201')
        
        db.put(e)
        
        key = e.key()
        
        e = db.get(key)
        
        self.assert_(e.type == 'Meeting')
        self.assert_(e.location.building.name == 'Event Test' and e.location.building.abbreviation == 'ET')
        self.assert_(e.location.floor == '2nd' and e.location.room == '201')
    
    def test_addOfficeHour(self):
        b = F.addBuilding('Office Hour Test', 'ET')
        l = F.addLocation(b, '2nd', '201') 
        s = F.addSchedule('1/1/2000', '1pm', ['Monday', 'Tuesday'], '2pm', '1/1/2010')
        
        oh = F.addOfficeHour(s, l)
        
        self.assert_(oh.type == 'Office Hours')
        self.assert_(oh.location.building.name == 'Office Hour Test' and oh.location.building.abbreviation == 'ET')
        self.assert_(oh.location.floor == '2nd' and oh.location.room == '201')
        self.assert_(oh.schedule.start_date == '1/1/2000' and oh.schedule.end_date == '1/1/2010')
        self.assert_(oh.schedule.start_time == '1pm' and oh.schedule.end_time == '2pm')
        self.assert_('Monday' in oh.schedule.days and 'Tuesday' in oh.schedule.days)

            
        db.put(oh)
        
        key = oh.key()
        
        oh = db.get(key)
        
        self.assert_(oh.type == 'Office Hours')
        self.assert_(oh.location.building.name == 'Office Hour Test' and oh.location.building.abbreviation == 'ET')
        self.assert_(oh.location.floor == '2nd' and oh.location.room == '201')
        self.assert_(oh.schedule.start_date == '1/1/2000' and oh.schedule.end_date == '1/1/2010')
        self.assert_(oh.schedule.start_time == '1pm' and oh.schedule.end_time == '2pm')
        self.assert_('Monday' in oh.schedule.days and 'Tuesday' in oh.schedule.days)

    def test_addStaffMembers(self):
        name = F.addName('Staff', 'Member')
        b = F.addBuilding('Staff Test', 'ET')
        l = F.addLocation(b, '2nd', '201') 
        s = F.addSchedule('1/1/2000', '1pm', ['Monday', 'Tuesday'], '2pm', '1/1/2010')
        oh = F.addOfficeHour(s, l)
        db.put(oh)
        
        sm = F.addStaffMember(name, oh)
        
        self.assert_(sm.name.first == 'Staff' and sm.name.last == 'Member')
        self.assert_(sm.office_hours.type == 'Office Hours')
        self.assert_(sm.office_hours.location.building.name == 'Staff Test' and sm.office_hours.location.building.abbreviation == 'ET')
        self.assert_(sm.office_hours.location.floor == '2nd' and sm.office_hours.location.room == '201')
        self.assert_(sm.office_hours.schedule.start_date == '1/1/2000' and sm.office_hours.schedule.end_date == '1/1/2010')
        self.assert_(sm.office_hours.schedule.start_time == '1pm' and sm.office_hours.schedule.end_time == '2pm')
        self.assert_('Monday' in sm.office_hours.schedule.days and 'Tuesday' in sm.office_hours.schedule.days)
        
        key = sm.key()
        
        sm = db.get(key)
        
        self.assert_(sm.name.first == 'Staff' and sm.name.last == 'Member')
        self.assert_(sm.office_hours.type == 'Office Hours')
        self.assert_(sm.office_hours.location.building.name == 'Staff Test' and sm.office_hours.location.building.abbreviation == 'ET')
        self.assert_(sm.office_hours.location.floor == '2nd' and sm.office_hours.location.room == '201')
        self.assert_(sm.office_hours.schedule.start_date == '1/1/2000' and sm.office_hours.schedule.end_date == '1/1/2010')
        self.assert_(sm.office_hours.schedule.start_time == '1pm' and sm.office_hours.schedule.end_time == '2pm')
        self.assert_('Monday' in sm.office_hours.schedule.days and 'Tuesday' in sm.office_hours.schedule.days)
          
    def test_addRetrieveCourseNumber(self):
        c = F.addCourseNumber('CS', '373W')
        
        self.assert_(c.department == 'CS' and c.number == '373W')
        
        db.put(c)
        
        key = c.key()
        
        c = db.get(key)
        
        self.assert_(c.department == 'CS' and c.number == '373W')
          
    def test_addRetrieveCourse(self):
        name1 = F.addName('Staff', 'Member')
        name2 = F.addName('WHAT', 'MORE')
        sched = F.addSchedule('1/15/2010', '11am', ['Monday', 'Wednesday', 'Friday'], '12pm', '5/7/2010')
        
        b = F.addBuilding('Course Test', 'ET')
        l = F.addLocation(b, '2nd', '201') 
        s = F.addSchedule('1/1/2000', '1pm', ['Monday', 'Tuesday'], '2pm', '1/1/2010')
        oh = F.addOfficeHour(s, l)
        db.put(oh)
        
        event = F.addEvent('Meeting', sched, l)
        db.put(event)
        sm1 = F.addStaffMember(name1, oh)
        sm2 = F.addStaffMember(name2, oh)
        
        staff = [sm1, sm2]
        cn = F.addCourseNumber('CS', '373W')
        db.put(cn)
        
        c = F.addCourse('12345', 'SWE', 'Really annoying final projects', cn, 'Spring', '2010', staff, event)
        
        self.assert_(c.unique_id == '12345' and c.name == 'SWE' and c.description == 'Really annoying final projects')
        self.assert_(c.semester == 'Spring' and c.year == '2010')
        self.assert_(c.schedule.schedule.start_date == '1/15/2010' and c.schedule.schedule.end_date == '5/7/2010')
        self.assert_(c.schedule.schedule.start_time == '11am' and c.schedule.schedule.end_time == '12pm')
        self.assert_(c.course_number.department == 'CS' and c.course_number.number == '373W')
        
        db.put(c)
        
        key = c.key()
        
        c = db.get(key)
        
        self.assert_(c.unique_id == '12345' and c.name == 'SWE' and c.description == 'Really annoying final projects')
        self.assert_(c.semester == 'Spring' and c.year == '2010')
        self.assert_(c.schedule.schedule.start_date == '1/15/2010' and c.schedule.schedule.end_date == '5/7/2010')
        self.assert_(c.schedule.schedule.start_time == '11am' and c.schedule.schedule.end_time == '12pm')
        self.assert_(c.course_number.department == 'CS' and c.course_number.number == '373W')
        
    def test_addRetrieveArticle(self):
    	article = F.addArticle('Psychology in the Lab', '47000')
    	self.assert_(article.journal == 'Psychology in the Lab' and article.edition == '47000')  
    	db.put(article)  
    	key = article.key()
    	article = db.get(key)
    	self.assert_(article.journal == 'Psychology in the Lab' and article.edition == '47000')

    def test_addRetrieveConference(self):
        c = F.addConference('My Conference', '4/1/2010', 'Chicago')
        
        self.assert_(c.name == 'My Conference' and c.date == '4/1/2010' and c.location == 'Chicago')
        
        key = c.key()
        
        c = db.get(key)
        
    	self.assert_(c.name == 'My Conference' and c.date == '4/1/2010' and c.location == 'Chicago')
    	    
    def test_addRetrievePresentation(self):
        c = F.addConference('My Presentation Conference', '4/1/2010', 'Austin')
        
        p = F.addPresentation('Project 7', c)
        
        self.assert_(p.name == 'Project 7')
        self.assert_(p.conference.name == 'My Presentation Conference' and p.conference.date == '4/1/2010' and p.conference.location == 'Austin')
        
        db.put(p)
        
        key = p.key()
        
        p = db.get(key)
        
    	self.assert_(p.name == 'Project 7')
        self.assert_(p.conference.name == 'My Presentation Conference' and p.conference.date == '4/1/2010' and p.conference.location == 'Austin')
            
        
             
    def test_addRetrievePublishInfo(self):
    	article = F.addArticle('Psychology in the Lab', '47000')
    	db.put(article)
    	
    	publish = F.addPublishInfo('Borders', '1/1/2010', 'ISBN1234', article)
    	
    	self.assert_(publish.publisher == 'Borders' and publish.date == '1/1/2010' and publish.isbn == 'ISBN1234')
    	self.assert_(publish.article.journal == 'Psychology in the Lab' and publish.article.edition == '47000')
    	
    	key = publish.key()
    	
    	publish = db.get(key)
    	
    	self.assert_(publish.publisher == 'Borders' and publish.date == '1/1/2010' and publish.isbn == 'ISBN1234')
    	self.assert_(publish.article.journal == 'Psychology in the Lab' and publish.article.edition == '47000')
    	
    def test_addRetrieveFaculty(self):
        name = F.addName('This', 'Sucks', 'Really', 'Bad')
        
        faculty = F.addFaculty(name, ['no@no.com', 'yes@yes.com'], 'Lecturer', 'tsucks')
        
        self.assert_(faculty.username == 'tsucks')
        self.assert_('no@no.com' in faculty.emails and 'yes@yes.com' in faculty.emails)
        self.assert_(faculty.name.first == 'This')
        self.assert_(faculty.name.last == 'Sucks')
        self.assert_(faculty.name.suffix == 'Bad')
        self.assert_(faculty.name.middle == 'Really')
        
        faculty_key = db.put(faculty)
        
        faculty = db.get(faculty_key)
        
        self.assert_(faculty.username == 'tsucks')
        self.assert_('no@no.com' in faculty.emails and 'yes@yes.com' in faculty.emails)
        self.assert_(faculty.name.first == 'This')
        self.assert_(faculty.name.last == 'Sucks')
        self.assert_(faculty.name.suffix == 'Bad')
        self.assert_(faculty.name.middle == 'Really')
     
    def test_addRetrieveStudent(self):
        
        name = F.addName('Some', 'Student')
        name1 = F.addName('Some', 'Faculty')
        faculty = F.addFaculty(name, ['myemail@a.com'], 'Lecturer', 'sfac')

        student = F.addStudent(name, ['no@no.com', 'yes@yes.com'], 'PhD Student', 'stud', [faculty])
        
        self.assert_(student.username == 'stud')
        self.assert_('no@no.com' in student.emails and 'yes@yes.com' in student.emails)
        self.assert_(student.name.first == 'Some')
        self.assert_(student.name.last == 'Student')
        self.assert_(student.name.suffix is None)
        self.assert_(student.name.middle is None)
        self.assert_(faculty.key() in student.advisors)
        
        key = student.key()
        
        student = db.get(key)
        
        self.assert_(student.username == 'stud')
        self.assert_('no@no.com' in student.emails and 'yes@yes.com' in student.emails)
        self.assert_(student.name.first == 'Some')
        self.assert_(student.name.last == 'Student')
        self.assert_(student.name.suffix is None)
        self.assert_(student.name.middle is None)
        self.assert_(faculty.key() in student.advisors)

class TestPage (webapp.RequestHandler):
    
    def __init__(self):
        pass
    
    def get (self):
        self.run_unit_tests()
    
    def run_unit_tests(self):
        loader = unittest.TestLoader()
        suite = unittest.TestSuite()
        # replace this with the name of your unit test class
        suite.addTest(loader.loadTestsFromTestCase(TestFaculty))
        runner = unittest.TextTestRunner(sys.stdout)
        runner.run(suite)
    
pages = []
pages.append(('/unittest', TestPage))
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
    profile_main()
    #real_main()




