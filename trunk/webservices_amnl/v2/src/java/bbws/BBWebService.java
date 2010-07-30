/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */
package bbcrsws;

//blackboard
import blackboard.admin.data.course.Enrollment;
import blackboard.admin.data.IAdminObject.RecStatus;
import blackboard.admin.data.IAdminObject.RowStatus;
import blackboard.admin.data.user.Person;
import blackboard.admin.persist.course.EnrollmentLoader.Default;
import blackboard.admin.persist.user.impl.PersonDbPersister;
import blackboard.admin.persist.user.impl.PersonDbLoader;
import blackboard.base.FormattedText;
import blackboard.base.FormattedText.Type;
import blackboard.data.announcement.Announcement;



import blackboard.data.course.Course;
import blackboard.data.course.CourseMembership;
import blackboard.data.course.CourseMembership.Role;
import blackboard.data.course.CourseQuota;
import blackboard.data.course.Group;
import blackboard.data.course.GroupMembership;
import blackboard.data.gradebook.impl.Attempt;
import blackboard.data.gradebook.impl.GradeBookSettings;
import blackboard.data.gradebook.impl.Outcome;
import blackboard.data.gradebook.impl.OutcomeDefinition;
import blackboard.data.gradebook.Lineitem;
import blackboard.data.gradebook.Score;
import blackboard.data.registry.CourseRegistryEntry;
import blackboard.data.user.User;
import blackboard.data.user.User.EducationLevel;
import blackboard.data.user.User.Gender;
import blackboard.data.user.UserRole;
import blackboard.db.ConstraintViolationException;
import blackboard.persist.announcement.AnnouncementDbLoader;
import blackboard.persist.announcement.AnnouncementDbPersister.Default;


import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseDbPersister;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.course.CourseMembershipDbPersister;
import blackboard.persist.course.GroupDbLoader;
import blackboard.persist.course.GroupMembershipDbLoader;
import blackboard.persist.course.GroupMembershipDbPersister;

import blackboard.persist.course.GroupDbPersister;
import blackboard.persist.gradebook.impl.AttemptDbLoader;
import blackboard.persist.gradebook.impl.AttemptDbPersister;
import blackboard.persist.gradebook.impl.GradeBookSettingsDbLoader;
import blackboard.persist.gradebook.impl.GradeBookSettingsDbPersister;
import blackboard.persist.gradebook.impl.OutcomeDbLoader;
import blackboard.persist.gradebook.impl.OutcomeDefinitionDbLoader;
import blackboard.persist.gradebook.impl.OutcomeDefinitionDbPersister;
import blackboard.persist.gradebook.LineitemDbLoader;
import blackboard.persist.gradebook.LineitemDbPersister;
import blackboard.persist.gradebook.ScoreDbLoader;
import blackboard.persist.Id;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;
import blackboard.persist.registry.CourseRegistryEntryDbLoader;
import blackboard.persist.registry.CourseRegistryEntryDbPersister;
import blackboard.persist.role.PortalRoleDbLoader;
import blackboard.persist.user.UserDbLoader;
import blackboard.persist.user.UserDbPersister;
import blackboard.persist.user.UserRoleDbPersister;
import blackboard.platform.BbServiceManager;
import blackboard.platform.security.DomainManagerFactory;
import blackboard.platform.security.SecurityUtil;
import blackboard.platform.security.SystemRole;


//java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Calendar;
import java.util.GregorianCalendar;

import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//javax
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
@WebService(name="BBAnnouncementsWebService", serviceName="BBAnnouncementsWebService", targetNamespace="http://www.ncl.ac.uk/BBAnnouncementsWebService")
public class BBAnnouncementsWebService
{
    private WebServiceProperties wsp = new WebServiceProperties("amnl","BBAnnouncementsWebService");

    private String checkAnnouncementDetail(String announcementDetail) throws Exception
    {
        if(announcementDetail!=null && !announcementDetail.trim().equalsIgnoreCase(""))
        {
            return announcementDetail.trim();
        }
        throw new Exception("Invalid announcement detail (cAD): '"+announcementDetail+"'");
    }

    private Calendar getCalendarObjFromDayMonthYear(int year, int month, int day) throws Exception
    {
        Calendar cal = new GregorianCalendar();
        cal.setLenient(false);
        if(day!=0 && month!=0 && year!=0)
        {
            try
            {
            //Remember the gc object month field is 0-11
            cal.set(year,month-1,day);
            }
            catch(Exception e)
            {
            throw new Exception("Invalid starting date "+day+"/"+month+"/"+year+" "+e.toString()+" "+e.getMessage());
            }
        }
        return cal;
    }

    private Boolean handleNullValue(Boolean value)
    {
        if (value == null)
        {
            return false;
        }
        return value;
    }


    /*********************
     * Announcement posting really should have userID's passed
     * so you can check if user is allowed to post where they
     * want to but this would require authentication, passing
     * the userid and trust of that authentication.
     *
     * textType(null) = HTML / TEXT - DEFAULT TEXT
     * courseID(null) = e.g. bbd510
     * permanent(null) = true / false - DEFAULT false
     * type(null) = COURSE / SYSTEM - DEFAULT COURSE - what happens if -
     *					  no courseid? user not allowed to post system ann.?
     * title(!null) = Title of announcement
     * body(!null) = message to announce
     * startDay/Month/Year(null) = Date to make available - DEFAULT Today -
     *						    startDay 1-31, startMonth 1-12
     * endDay/Month/Year(null) = Date to make unavailable - DEFAULT Always available -
     *						    startDay 1-31, startMonth 1-12
     *******************/
	/*++ (vic) - added from v2 for readable diff matching
	private Boolean announcementCreate(BBAnnouncement announcement, BBCourse course, String textType) throws WebServiceException
	++*/
    private Boolean postAnnouncement(String announcementId, String textType,
				    String courseID, Boolean permanent,
				    String type, String title, String body,
				    int startDay, int startMonth, int startYear,
				    int endDay, int endMonth, int endYear) throws Exception
    {
        try
        {
            Announcement a = new Announcement();
            //We can't use checkAnnouncementDetail as this mustn't throw an error as null or ""
            //is valid when posting message, but not when modifying them.
            if(announcementId!=null && !announcementId.trim().equalsIgnoreCase(""))
            {
                announcementId = announcementId.trim();
                //We are modifying an announcement
                a.setId(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Announcement.DATA_TYPE,announcementId));
            }
            //else we are creating an announcement
            a.setTitle(checkAnnouncementDetail(title));
            body = checkAnnouncementDetail(body);
            FormattedText ft = null;
            if(textType.equalsIgnoreCase("HTML"))
            {
                ft = new FormattedText(body,Type.HTML);
            }
            else
            {
               ft = new FormattedText(body,Type.PLAIN_TEXT);
            }
            a.setBody(ft);
            //Assume type is course unless specifically set as SYSTEM
            a.setType(blackboard.data.announcement.Announcement.Type.COURSE);
            try
            {
                type = checkAnnouncementDetail(type);
                //may be course or system
                if(type.equalsIgnoreCase("SYSTEM"))
                {
                    //it's def a system
                    courseID = "SYSTEM";
                    a.setType(blackboard.data.announcement.Announcement.Type.SYSTEM);
                }
                //it's def a course
            }catch(Exception e){/*it's def a course*/}
            courseID = checkAnnouncementDetail(courseID);
            a.setCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseID).getId());
            a.setCreatorUserId(UserDbLoader.Default.getInstance().loadByUserName("administrator").getId());
            permanent = handleNullValue(permanent);
            a.setIsPermanent(permanent);
            try{a.setRestrictionStartDate(getCalendarObjFromDayMonthYear(startYear,startMonth,startDay));}catch(Exception e){}
            //else don't set a start date at all.
            try{a.setRestrictionEndDate(getCalendarObjFromDayMonthYear(endYear,endMonth,endDay));}catch(Exception e){}
            //else don't set an end date at all.
            Default.getInstance().persist(a);
        }
        catch(Exception e)
        {
            throw new Exception("Error while trying to post announcement (pA): "+e.toString());
        }
        return true;
    }
	/*!! (vic)  - new, code came from deleteAnnouncement

    private boolean announcementDelete(BBAnnouncement announcement) throws WebServiceException



	++*/

	/*++ (vic) - added from v2 for readable diff matching
    private List<BBAnnouncement> announcementReadSearchByAvailableAnnouncementAndUserId(BBUser user) throws WebServiceException
	++*/
    private List<AnnouncementDetails> getAllAnnouncementsForGivenUserId(String userId) throws Exception
    {
        List<AnnouncementDetails> rl = new ArrayList<AnnouncementDetails>();
        List<Announcement> al = AnnouncementDbLoader.Default.getInstance().loadAvailableByUserId(UserDbLoader.Default.getInstance().loadByUserName(userId).getId());
        if(al.size()<1)
        {
            throw new Exception("No announcements found");
        }
        Iterator i = al.iterator();
        while(i.hasNext())
        {
            rl.add(new AnnouncementDetails((Announcement)i.next()));
        }
        return rl;
    }

	/*++ (vic) - added from v2 for readable diff matching
    private List<BBAnnouncement> announcementReadSearchByCourseId(BBCourse course) throws WebServiceException
	++*/
    private List<AnnouncementDetails> getAllAnnouncementsForGivenCourseId(String courseId) throws Exception
    {
        List<AnnouncementDetails> rl = new ArrayList<AnnouncementDetails>();
        List<Announcement> al = AnnouncementDbLoader.Default.getInstance().loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId());
        if(al.size()<1)
        {
            throw new Exception("No announcements found");
        }
        Iterator i = al.iterator();
        while(i.hasNext())
        {
            rl.add(new AnnouncementDetails((Announcement)i.next()));
        }
        return rl;
    }

	/*++ (vic) - added from v2 for readable diff matching
    private boolean announcementUpdate(BBAnnouncement announcement, BBCourse course, String textType) throws WebServiceException
	++*/
    private Boolean modifyAnnouncement(String announcementBbId, String textType,
                                        String courseId, String title,
                                        String body, int startDay, int startMonth,
                                        int startYear, int endDay, int endMonth, int endYear) throws WebServiceException
    {
        try
        {
            announcementBbId = checkAnnouncementDetail(announcementBbId);
            String type = "COURSE";
            try
            {
                courseId = checkAnnouncementDetail(courseId);
            }
            catch(Exception e)
            {
                courseId = null;
                type = "SYSTEM";
            }
            return postAnnouncement(announcementBbId,textType,courseId,null,type,title,body,startDay,startMonth,startYear,endDay,endMonth,endYear);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: "+e.toString()+" "+e.getMessage());
        }
    }

    private void authoriseMethodUse(String methodPwd, String methodName) throws WebServiceException
    {
        if(!wsp.isMethodAccessible(methodName) || (wsp.usingPassword() && !wsp.passwordMatches(methodPwd)))
        {
            throw new WebServiceException("Access Denied");
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
	public Boolean bbAnnouncementCreate(@WebParam(name = "pwd") String pwd, @WebParam(name = "announcement") BBAnnouncement announcement, @WebParam(name = "course") BBCourse course, @WebParam(name = "textType") String textType) throws WebServiceException
	++*/
    public Boolean postCourseAnnouncement(@WebParam(name = "pwd") String pwd, @WebParam(name = "textType") String textType,
				    @WebParam(name = "courseID") String courseID,
				    @WebParam(name = "permanent") Boolean permanent,
				    @WebParam(name = "title") String title,
				    @WebParam(name = "body") String body,
				    @WebParam(name = "startDay") int startDay, @WebParam(name = "startMonth") int startMonth,
				    @WebParam(name = "startYear") int startYear, @WebParam(name = "endDay") int endDay,
				    @WebParam(name = "endMonth") int endMonth, @WebParam(name = "endYear") int endYear) throws WebServiceException
    {
        authoriseMethodUse(pwd,"postCourseAnnouncement");
        try
        {
            return postAnnouncement(null,textType,courseID,permanent,"COURSE",title,body,startDay,startMonth,startYear,endDay,endMonth,endYear);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error (pCA): "+e.toString()+" "+e.getMessage());
        }
    }

    /*********************
     * This method should check that either it is the creator user
     * or someone of a similar or elevated role or not allow
     * the deletion, e.g. a student couldn't delete a course
     * announcement or a sys. wide announcement!
     ********************
     * 
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public Boolean bbAnnouncementDelete(@WebParam(name = "pwd") String pwd, @WebParam(name = "announcement") BBAnnouncement announcement) throws WebServiceException
	++*/
    public Boolean deleteAnnouncement(@WebParam(name = "pwd") String pwd, @WebParam(name = "announcementBbId") String announcementBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteAnnouncement");
        try
        {
            Default.getInstance().deleteById(AnnouncementDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Announcement.DATA_TYPE,announcementBbId)).getId());
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Please provide a valid dbid "+e.toString()+" "+e.getMessage());
            //return "Error while trying to delete announcement: "+e.toString();
        }
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public List<BBAnnouncement> bbAnnouncementReadSearchByAvailableAnnouncementAndUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user) throws WebServiceException
	++*/
    public List<AnnouncementDetails> getAllAvailableAnnouncementsForGivenUserIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId)  throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllAvailableAnnouncementsForGivenUserIdWithNamedElements");
        try
        {
            return getAllAnnouncementsForGivenUserId(userId);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: "+e.toString()+" "+e.getMessage());
        }
    }



    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public List<BBAnnouncement> bbAnnouncementReadSearchByCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course) throws WebServiceException
	++*/
    public List<AnnouncementDetails> getAllAnnouncementsForGivenCourseIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllAnnouncementsForGivenCourseIdWithNamedElements");
        try
        {
            return getAllAnnouncementsForGivenCourseId(courseId);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: "+e.toString()+" "+e.getMessage());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    @WebMethod(operationName = "bbAnnouncementUpdate")
    public boolean bbAnnouncementUpdate(@WebParam(name = "pwd") String pwd, @WebParam(name = "announcement") BBAnnouncement announcement, @WebParam(name = "course") BBCourse course, @WebParam(name = "textType") String textType) throws WebServiceException
	++*/
    public Boolean modifyAnnouncement(@WebParam(name = "pwd") String pwd, @WebParam(name = "announcementBbId") String announcementBbId,
                                        @WebParam(name = "textType") String textType,
                                        @WebParam(name = "courseId") String courseId,
                                        @WebParam(name = "title") String title,
                                        @WebParam(name = "body") String body,
                                        @WebParam(name = "startDay") int startDay, @WebParam(name = "startMonth") int startMonth,
                                        @WebParam(name = "startYear") int startYear, @WebParam(name = "endDay") int endDay,
                                        @WebParam(name = "endMonth") int endMonth, @WebParam(name = "endYear") int endYear) throws WebServiceException
    {
        authoriseMethodUse(pwd,"modifyAnnouncement");
        return modifyAnnouncement(announcementBbId, textType, courseId, title,
                                        body, startDay, startMonth,
                                        startYear, endDay, endMonth, endYear);
    }



@WebService(name="BBCourseWebService", serviceName="BBCourseWebService", targetNamespace="http://www.ncl.ac.uk/BBCourseWebService")
public class BBCourseWebService
{
    private WebServiceProperties wsp = new WebServiceProperties("amnl","BBCourseWebService");
    //private enum Verbosity{minimal,quota,standard,extended}

    /**
     *
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public Boolean bbCourseCreate(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course) throws WebServiceException
	++*/
    public Boolean addCourse(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "batchUID") String batchUID,
			    @WebParam(name = "title") String title, @WebParam(name = "courseDescription") String courseDescription,
			    @WebParam(name = "available") Boolean available, @WebParam(name = "allowGuests") Boolean allowGuests,
			    @WebParam(name = "allowObservers") Boolean allowObservers) throws WebServiceException
    {
        authoriseMethodUse(pwd,"addCourse");
        return addCourse(courseId, batchUID, title, courseDescription, available, allowGuests, allowObservers);
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public Boolean bbCourseDelete(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course) throws WebServiceException
	++*/
    public Boolean deleteCourseByCourseBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseBbId") String courseBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteCourseByCourseBbId");
        return deleteCourseByCourseBbId(courseBbId);
    }

    /**
     * Role (Case insensitive): COURSE_BUILDER, DEFAULT, GRADER, GUEST, INSTRUCTOR, TEACHING_ASSISTANT, NONE, STUDENT, 
     *
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public Boolean bbCourseMembershipCreate(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembership") BBCourseMembership courseMembership) throws WebServiceException
	++*/
    public Boolean enrollGivenUserOnGivenCourse(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "userId") String userId, @WebParam(name = "role") String role) throws WebServiceException
    {
        authoriseMethodUse(pwd,"enrollGivenUserOnGivenCourse");
        return enrollGivenUserOnGivenCourse(userId,courseId,role);
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public Boolean bbCourseMembershipDelete(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembership") BBCourseMembership courseMembership) throws WebServiceException
	++*/
    public Boolean unEnrollGivenUserFromGivenCourse(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"unenrollGivenUserFromGivenCourse");
        return unEnrollGivenUserFromGivenCourse(userId,courseId);
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public BBCourseMembership bbCourseMembershipRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembership") BBCourseMembership courseMembership, @WebParam(name = "verbosity") BBCourseMembership.BBCourseMembershipVerbosity verbosity) throws WebServiceException
	++*/
    public CourseMembershipDetails getCourseMembershipDetailsForGivenCourseMembershipBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembershipBbId") String courseMembershipBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getCourseMembershipDetailsForGivenCourseMembershipBbIdWithNamedElements");
        return getCourseMembershipDetailsForGivenCourseMembershipBbId(courseMembershipBbId);
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
        public BBCourseMembership bbCourseMembershipReadSearchByUserIdAndCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course, @WebParam(name = "user") BBUser user, @WebParam(name = "verbosity") BBCourseMembership.BBCourseMembershipVerbosity verbosity, @WebParam(name = "loadUser") Boolean loadUser)
	++*/
    public CourseMembershipDetails getCourseMembershipBbIdForGivenUserIdAndCourseIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getCourseMembershipBbIdForGivenUserIdAndCourseIdWithNamedElements");
        return getCourseMembershipBbIdForGivenUserIdAndCourseId(userId,courseId);
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public BBCourseQuota bbCourseQuotaRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course) throws WebServiceException
	++*/
    public CourseQuotaDetails getCourseQuotaDetailsWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getCourseQuotaDetailsWithNamedElements");
        return getCourseQuotaDetails(courseId);
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public Boolean bbCourseQuotaUpdate(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course, @WebParam(name = "courseQuota") BBCourseQuota courseQuota) throws WebServiceException
	++*/
    public Boolean modifyCourseQuotaDetails(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseID") String courseID, @WebParam(name = "overrideDiskQuota") Boolean overrideDiskQuota, @WebParam(name = "overrideUploadLimit") Boolean overrideUploadLimit, @WebParam(name = "absoluteLimit") Long absoluteLimit, @WebParam(name = "softLimit") Long softLimit, @WebParam(name = "uploadLimit") Long uploadLimit) throws WebServiceException
    {
        authoriseMethodUse(pwd,"modifyCourseQuotaDetails");
        return modifyCourseQuotaDetails(courseID, overrideDiskQuota, overrideUploadLimit, absoluteLimit, softLimit, uploadLimit);
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public BBCourse bbCourseRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course, @WebParam(name = "verbosity") BBCourse.BBCourseVerbosity verbosity) throws WebServiceException
	++*/
    public CourseDetails getCourseDetailsWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getCourseDetailsWithNamedElements");
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            return getCourseDetailsObjFromCourseId(courseId,extendedDetails);
        }
        catch(KeyNotFoundException knfe)
        {
            throw new WebServiceException("courseId '"+courseId+"' not found or does not exist ("+knfe.getMessage()+")");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Could not retrieve course with that Id");
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public List<BBCourse> bbCourseReadAll(@WebParam(name = "pwd") String pwd, @WebParam(name = "verbosity") BBCourse.BBCourseVerbosity verbosity) throws WebServiceException
	++*/
    public List<CourseDetails> getAllCourseIDsNoDetailsWithNamedElements(@WebParam(name = "pwd") String pwd) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllCourseIDsNoDetailsWithNamedElements");
        try
        {
            return getAllCourseObjsNoDetails();
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while getting courses: "+e.toString());
        }
    }

    /**
     * Web service operation
     */
	/*++ (vic) - added from v2 for readable diff matching
    public List<BBCourse> bbCourseReadSearchByRegex(@WebParam(name = "pwd") String pwd, @WebParam(name = "regex") String regex, @WebParam(name = "verbosity") BBCourse.BBCourseVerbosity verbosity) throws WebServiceException
	++*/
    @WebMethod
    public List<CourseDetails> getAllCourseIDsWhereCourseIdContainsGivenSearchStringWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "searchString") String searchString) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllCourseIDsWhereCourseIdContainsGivenSearchStringWithNamedElements");
        try
        {
            return getAllCourseIDObjsWhereCourseIdContainsGivenSearchString(searchString);

        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while matching courses: "+e.toString());
        }
    }

	/*++ (vic) - added from v2 for readable diff matching
        return courseReadSearchByRegex(regex, verbosity);
    }

    @WebMethod
    public List<BBCourse> bbCourseReadSearchByUserIdAndCMRole(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user, @WebParam(name = "") BBCourseMembershipRole cmRole, @WebParam(name = "verbosity") BBCourse.BBCourseVerbosity verbosity) throws WebServiceException
	++*/
    @WebMethod
    public List<CourseDetails> getAllCourseIDsWhereGivenUserIdIsAnInstructorWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllCourseIDsWhereGivenUserIdIsAnInstructorWithNamedElements");
        try
        {
            return getAllCourseIDsWhereGivenUserIdIsAnInstructor(userId);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while retrieving courses: "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public List<BBEnrollment> bbEnrollmentReadSearchByUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user) throws WebServiceException
	++*/
    public List<EnrollmentDetails> getEnrolledCoursesForGivenUserWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
    {
        /*Figure out if you can distinguish courses from organisations (communities)
         * - think you can, isn't there a service type enumerator somewhere?
         * - Yes.... Course.ServiceLevel
         */
        authoriseMethodUse(pwd,"getEnrolledCoursesForGivenUserWithNamedElements");
        try
        {
            return getEnrollmentDetailsObjsForGivenUser(userId);
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public boolean bbGradeCentreAttemptDelete(@WebParam(name = "pwd") String pwd, @WebParam(name = "attempt") BBAttempt attempt) throws WebServiceException
	++*/
    public Boolean deleteGivenAttemptByAttemptBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "attemptBbId") String attemptBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteGivenAttemptByAttemptBbId");
        return deleteGivenAttemptByAttemptBbId(attemptBbId);
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
	public List<BBAttempt> bbGradeCentreAttemptReadSearchByOutcomeDefinitionId(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefinition") BBOutcomeDefinition outcomeDef, @WebParam(name = "verbosity") BBAttempt.BBAttemptVerbosity verbosity) throws WebServiceException
	++*/
    public String[][] getAttemptsByGivenOutcomeDefBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAttemptsByGivenOutcomeDefBbId");
        headerDesc = handleNullValue(headerDesc);
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            List<AttemptDetails> adsl = getAttemptDetailsObjsForGivenOutcomeDefBbId(outcomeDefBbId, extendedDetails);
            List<String[]> rl = new ArrayList<String[]>();
            if(headerDesc)
            {
            rl.add(new AttemptDetails(extendedDetails?AttemptDetails.Verbosity.extended:AttemptDetails.Verbosity.standard).toStringArrayHeader());
            }
            Iterator i = adsl.iterator();
            while(i.hasNext())
            {
            rl.add(((AttemptDetails)i.next()).toStringArray());
            }
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: "+e.toString());
        }
    }
    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public boolean bbGradeCentreLineitemAdd(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitem") BBLineitem lineitem, @WebParam(name = "course") BBCourse course) throws WebServiceException
	++*/
    public Boolean addLineitemToGivenCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "Name") String name,
						@WebParam(name = "type") String type, @WebParam(name = "pointsPossible") Float pointsPossible,
						@WebParam(name = "weight") Float weight, @WebParam(name = "available") Boolean available) throws WebServiceException
    {
        authoriseMethodUse(pwd,"addLineitemToGivenCourseId");
        return addLineitemToGivenCourseId(courseId, name, type, pointsPossible, weight, available);
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public Boolean bbGradeCentreLineitemDelete(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitem") BBLineitem lineitem) throws WebServiceException
	++*/
    public Boolean deleteLineItemByLineItemBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitemBbId") String lineItemBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteLineItemByLineItemBbId");
        return deleteOutcomeDefinitionByOutcomeDefBbId(lineItemBbId);
    }

    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public BBLineitem bbGradeCentreLineitemRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitem") BBLineitem lineitem) throws WebServiceException
	++*/
    public LineitemDetails getLineitemDetailsForGivenLineitemBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitemBbId") String lineitemBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getLineitemDetailsForGivenLineitemBbIdWithNamedElements");
        return getLineitemDetailsForGivenLineitemBbId(lineitemBbId);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getAllLineItemsForCourseIdWithNamedElements")
	/*++ (vic) - added from v2 for readable diff matching
    public List<BBLineitem> bbGradeCentreLineitemReadSearchByCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course) throws WebServiceException
	++*/
    public List<LineitemDetails> getAllLineItemsForCourseIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId)
    {
        authoriseMethodUse(pwd,"getAllLineItemsForCourseIdWithNamedElements");
        try
        {
            return getAllLineitemObjsForCourseId(courseId);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve line items for this course "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public Boolean bbGradeCentreOutcomeDefinitionDelete(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDef") BBOutcomeDefinition outcomeDef) throws WebServiceException
	++*/
    public Boolean deleteOutcomeDefinitionByOutcomeDefBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteOutcomeDefinitionByOutcomeDefBbId");
        return deleteOutcomeDefinitionByOutcomeDefBbId(outcomeDefBbId);
    }

    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public BBOutcomeDefinition bbGradeCentreOutcomeDefinitionRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDef") BBOutcomeDefinition outcomeDef) throws WebServiceException
	++*/
    public OutcomeDefinitionDetails getOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdWithNamedElements");
        return getOutcomeDefinitionDetailsFromOutcomeDefinitionBbId(outcomeDefBbId);
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public List<BBOutcomeDefinition> bbGradeCentreOutcomeDefinitionReadSearchByCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course) throws WebServiceException
	++*/
    public List<OutcomeDefinitionDetails> getAllOutcomeDefinitionsForGivenCourseWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllOutcomeDefinitionsForGivenCourseWithNamedElements");
        try
        {
            return getAllOutcomeDefinitionsForGivenCourse(courseId);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while getting outcome definitions for course '"+courseId+"': "+e.toString());
        }
    }

    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public BBOutcome bbGradeCentreOutcomeRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcome") BBOutcome outcome) throws WebServiceException
	++*/
    public OutcomeDetails getOutcomeDetailsFromOutcomeBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeBbId") String outcomeBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getOutcomeDetailsFromOutcomeBbIdWithNamedElements");
        return getOutcomeDetailsFromOutcomeBbId(outcomeBbId);
    }

    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public List<BBOutcome> bbGradeCentreOutcomeReadSearchByOutcomeDefinitionId(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBOutcomeDefinition outcomeDef) throws WebServiceException
	++*/
    public List<OutcomeDetails> getAllOutcomesForGivenOutcomeDefBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllOutcomesForGivenOutcomeDefBbIdWithNamedElements");
        try
        {
            return getAllOutcomesForGivenOutcomeDefBbId(outcomeDefBbId);

        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while getting outcomes: "+e.toString());
        }
    }

    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public BBScore bbGradeCentreScoreRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "score") BBScore score, @WebParam(name = "verbosity") BBScore.BBScoreVerbosity verbosity) throws WebServiceException
	++*/
    /**
     * Web service operation
     */
    public ScoreDetails getScoreDetailsForGivenScoreBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "scoreBbId") String scoreBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreForGivenCourseMembershipBbIdAndLineItemBbIdWithNamedElements");
        return getScoreDetailsForGivenScoreBbId(scoreBbId, extendedDetails);
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public List<BBScore> bbGradeCentreScoreReadSearchByLineitemId(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitem") BBLineitem lineitem, @WebParam(name = "verbosity") BBScore.BBScoreVerbosity verbosity) throws WebServiceException
	++*/
    public List<ScoreDetails> getAllScoreDetailsForGivenLineItemBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name="extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllScoreDetailsForGivenLineItemBbIdWithNamedElements");
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            return getAllScoreObjsForGivenLineItemBbId(lineItemBbId, extendedDetails);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve scores for this line item "+e.toString());
        }
    }

    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public BBScore bbGradeCentreScoreReadSearchByLineitemIdAndCourseMembershipId(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitem") BBLineitem lineitem, @WebParam(name = "courseMembership") BBCourseMembership courseMembership, @WebParam(name = "verbosity") BBScore.BBScoreVerbosity verbosity) throws WebServiceException
	++*/
    /**
     * Web service operation
     */
    public ScoreDetails getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembershipBbId") String courseMembershipBbId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdWithNamedElements");
        return getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbId(courseMembershipBbId, lineItemBbId, extendedDetails);
    }

    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public BBScore bbGradeCentreScoreReadSearchByLineitemIdAndUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitem") BBLineitem lineitem, @WebParam(name = "user") BBUser user, @WebParam(name = "verbosity") BBScore.BBScoreVerbosity verbosity) throws WebServiceException
	++*/
    public ScoreDetails getScoreDetailsForGivenUserIdAndLineItemBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreDetailsForGivenUserIdAndLineItemBbIdWithNamedElements");
        return getScoreDetailsForGivenUserIdAndLineItemBbId(userId, lineItemBbId, extendedDetails);
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public Boolean bbGroupAdd(@WebParam(name = "pwd") String pwd, @WebParam(name = "group") BBGroup group, @WebParam(name = "course") BBCourse course, @WebParam(name = "") String descriptionTextType) throws WebServiceException
	++*/
    public Boolean addGroupToGivenCourse(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "title") String title,
					@WebParam(name = "description") String description, @WebParam(name = "descType") String descType,
					@WebParam(name = "available") Boolean available, @WebParam(name = "chatRoomAvailable") Boolean chatRoomAvailable,
					@WebParam(name = "discussionBoardAvailable") Boolean discussionBoardAvailable, @WebParam(name = "emailAvailable") Boolean emailAvailable,
					@WebParam(name = "transferAvailable") Boolean transferAvailable) throws WebServiceException
    {
        authoriseMethodUse(pwd,"addGroupToGivenCourse");
        return addGroupToGivenCourse(courseId, title, description, descType,
                                        available, chatRoomAvailable, discussionBoardAvailable,
                                        emailAvailable, transferAvailable);
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public Boolean bbGroupDelete(@WebParam(name = "pwd") String pwd, @WebParam(name = "group") BBGroup group, @WebParam(name = "course") BBCourse course, @WebParam(name = "") String descriptionTextType) throws WebServiceException
	++*/
    public Boolean deleteGroup(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteGroup");
        return deleteGroup(groupId);
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public BBGroup bbGroupRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "group") BBGroup group) throws WebServiceException
	++*/
    public GroupDetails getGroupDetailsForGivenGroupBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGroupDetailsForGivenGroupBbIdWithNamedElements");
        return getGroupDetailsForGivenGroupBbId(groupBbId);
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public boolean bbGroupUpdate(@WebParam(name = "pwd") String pwd, @WebParam(name = "group") BBGroup group, @WebParam(name = "descriptionTextType") String descriptionTextType) throws WebServiceException
	++*/
    public Boolean modifyGroup(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupBbId,/* @WebParam(name = "courseId") String courseId,*/
				@WebParam(name = "title") String title, @WebParam(name = "description") String description,
				@WebParam(name = "descType") String descType, @WebParam(name = "available") Boolean available,
				@WebParam(name = "chatRoomAvailable") Boolean chatRoomAvailable, @WebParam(name = "discussionBoardAvailable") Boolean discussionBoardAvailable,
				@WebParam(name = "emailAvailable") Boolean emailAvailable, @WebParam(name = "transferAvailable") Boolean transferAvailable) throws WebServiceException
    {
        authoriseMethodUse(pwd,"modifyGroup");
        return modifyGroup(groupBbId, title, description, descType, available, chatRoomAvailable,
                                discussionBoardAvailable, emailAvailable, transferAvailable);
    }


    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public List<BBGroup> bbGroupReadByCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course) throws WebServiceException
	++*/
    public List<GroupDetails> getGroupDetailsForGivenCourseIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGroupDetailsForGivenCourseIdWithNamedElements");
        return getGroupDetailsForGivenCourseId(courseId);
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public List<BBGroupMembership> bbGroupMembershipReadByGroupId(@WebParam(name = "pwd") String pwd, @WebParam(name = "group") BBGroup group) throws WebServiceException
	++*/
    public List<GroupMemberDetails> getGroupMemberDetailsForGivenGroupBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGroupMemberDetailsForGivenGroupBbIdWithNamedElements");
        return getGroupMembersForGivenGroupBbId(groupBbId);
    }


    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public boolean bbGroupMembershipCreateByUserIdAndGroupId(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user, @WebParam(name = "group") BBGroup group) throws WebServiceException
	++*/
    public Boolean addGivenUserToGivenGroup(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "groupBbId") String groupId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"addGivenUserToGivenGroup");
        return addGivenUserToGivenGroup(userId,groupId);
    }

    /**
     * Web service operation
     */
    @WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public boolean bbGroupMembershipDeleteByUserIdAndGroupId(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user, @WebParam(name = "group") BBGroup group) throws WebServiceException
	++*/
    public Boolean deleteGivenUserFromGivenGroup(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "groupBbId") String groupId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteGivenUserFromGivenGroup");
        return deleteGivenUserFromGivenGroup(userId,groupId);
    }

    /**
     * Web service operation
     */
    @WebMethod 
	/*++ (vic) - added from v2 for readable diff matching
    public BBGradeCentreSettings bbGradeCentreSettingsRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course) throws WebServiceException
	++*/
    public GradeBookSettingsDetails getGradebookSettingsForGivenCourseIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGradebookSettingsForGivenCourseIDWithNamedElements");
        try
        {
            return getGradeBookSettingDetailsObjForGivenCourseId(courseId);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve gradebook settings for this course"+e.toString());
        }
    }


	/**
	 * Web service operation
	 */
	@WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public List<BBRole> bbRoleSecondaryPortalReadSearchByUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user) throws WebServiceException
	++*/
	public List<bbuws.Role> getSecondaryPortalRolesForGivenUserIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getSecondaryPortalRolesForGivenUserIdWithNamedElements");
	    //validateUserId(pwd,userId);
	    try
	    {
            return getSecondaryPortalRolesForGivenUserId(userId);
	    }
	    catch(Exception e)
	    {
            //return new String[][]{{"Error"},{"Couldn't load roles: "+e.toString()}};
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	 * roles can be NULL (I think) or 0 length
	 *
	 * Web service operation
	 */
	@WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public Boolean bbRoleSecondaryPortalUpdate(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user, @WebParam(name = "roles") List<BBRole> roles) throws WebServiceException
	++*/
	public Boolean setOrModifySecondaryPortalRolesForGivenUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "roles") String[] roles) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"setOrModifySecondaryPortalRolesForGivenUserId");
        return setOrModifySecondaryPortalRolesForGivenUserId(userId,parseSecondaryPortalRoles(roles));
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public List<BBRole> bbRoleSecondarySystemReadSearchByUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user) throws WebServiceException
	++*/
	public List<bbuws.Role> getSecondarySystemRolesForGivenUserIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getSecondarySystemRolesForGivenUserIdWithNamedElements");
	    //validateUserId(pwd,userId);
	    try
	    {
		return getSecondarySystemRolesForGivenUserId(userId);
	    }
	    catch(Exception e)
	    {
		throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}


	/**
	 * Web service operation
	 */
	@WebMethod
	/*++ (vic) - added from v2 for readable diff matching
        return roleSecondarySystemReadSearchByUserId(user);
    }

    @WebMethod
    public BBRole bbRoleUserReadSearchByUserIdAndCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user, @WebParam(name = "course") BBCourse course) throws WebServiceException
	++*/
	public bbuws.Role getUserRoleInCourseWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "courseId") String courseId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getUserRoleInCourseWithNamedElements");
	    try
	    {
            return new bbuws.Role(CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId(),UserDbLoader.Default.getInstance().loadByUserName(userId).getId()));
	    }
	    catch(Exception e)
	    {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	@WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public Boolean bbUserCreate(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user, @WebParam(name = "portalRole") BBRole portalRole, @WebParam(name = "secondaryPortalRoles") List<BBRole> secondaryPortalRoles, @WebParam(name = "systemRole") BBRole systemRole) throws WebServiceException
	++*/
	/**
	 *
	 * Web service operation
	 */
	public Boolean addUser(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "batchUId") String batchUId,
				@WebParam(name = "firstName") String firstName, @WebParam(name = "middleName") String middleName,
				@WebParam(name = "lastName") String lastName, @WebParam(name = "emailAddress") String emailAddress,
				@WebParam(name = "studentId") String studentId, @WebParam(name = "userPwd") String userPwd,
				@WebParam(name = "gender") String gender, @WebParam(name = "birthYear") String birthYear,
				@WebParam(name = "birthMonth") String birthMonth, @WebParam(name = "birthDay") String birthDay,
				@WebParam(name = "eduLevel") String eduLevel, @WebParam(name = "company") String company,
				@WebParam(name = "jobTitle") String jobTitle, @WebParam(name = "department") String department,
				@WebParam(name = "street1") String street1, @WebParam(name = "street2") String street2,
				@WebParam(name = "city") String city, @WebParam(name = "stateOrProvince") String stateOrProvince,
				@WebParam(name = "zipOrPostCode") String zipOrPostCode, @WebParam(name = "country") String country,
				@WebParam(name = "website") String website, @WebParam(name = "homePhone") String homePhone,
				@WebParam(name = "workPhone") String workPhone, @WebParam(name = "workFax") String workFax,
				@WebParam(name = "mobilePhone") String mobilePhone, @WebParam(name = "portalRole") String portalRole,
				@WebParam(name = "secPortalRole") String[] secPortalRoles, @WebParam(name = "systemRole") String systemRole,
				/*@WebParam(name = "secSystemRole") String[] secSystemRoles,*/ @WebParam(name = "available") Boolean available) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"addUser");
	    return addAUser(pwd,userId,batchUId,firstName,middleName,lastName,emailAddress,studentId,
			    userPwd,gender,birthYear,birthMonth,birthDay,eduLevel,company,jobTitle,
			    department,street1,street2,city,stateOrProvince,zipOrPostCode,
			    country,website,homePhone,workPhone,workFax,mobilePhone,
			    portalRole,secPortalRoles,systemRole,/*new String[]{""},*/available);
	}


	/**
	 * Web service operation
	 */
	@WebMethod
	/*++ (vic) - added from v2 for readable diff matching
    public Boolean bbUserDelete(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user)
	++*/
	public Boolean deleteUserByUserBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userBbId") String userBbId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"deleteUserByUserBbId");
	    try
	    {
		//You could just deleteById(bbPm.generateId()) but this way you also check if user exists
		UserDbPersister.Default.getInstance().deleteById(UserDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(User.DATA_TYPE,userBbId)).getId());
	    }
	    catch(Exception e)
	    {
		//return "Error: Please provide a valid Id for a user";
		throw new WebServiceException("Invalid userId");
	    }
	    return true;
	}

    /**
	* Web service getUserDetailsExtendedWithNamedElements
	*/
	@WebMethod
	/*++ (vic) - added from v2 for readable diff matching
        authoriseMethodUse(pwd,"bbUserDelete");
        return userDelete(user);
    }

    @WebMethod
    public BBUser bbUserRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user, @WebParam(name = "vebrosity") BBUser.BBUserVerbosity verbosity) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbUserRead");
	++*/
    public UserDetails getUserDetailsWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name="userId") String userId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException    {
        authoriseMethodUse(pwd,"getUserDetailsWithNamedElements");
        extendedDetails = handleNullValue(extendedDetails);
        try        {
            if(extendedDetails)            {
            }
            else            {
                return getUserDetailsFromUserId(userId,UserDetails.Verbosity.standard);
            }
        }
        catch (KeyNotFoundException knfe)        {
            //return new UserDetails(new Exception("No users found: "+e.getMessage()));
            throw new WebServiceException("No user found: "+knfe.getMessage());
        }
        catch(Exception e)        {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
        }
    }



	/**
	 * Web service operation
	 */
	@WebMethod
	/*++ (vic) - added from v2 for readable diff matching
        return userRead(user,verbosity);
    }

    @WebMethod
    public List<BBUser> bbUserReadAll(@WebParam(name = "pwd") String pwd, @WebParam(name = "vebrosity") BBUser.BBUserVerbosity verbosity) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbUserReadAll");
	++*/
	public List<UserDetails> getAllUserIDsNoDetailsWithNamedElements(@WebParam(name = "pwd") String pwd) throws WebServiceException	{
	    authoriseMethodUse(pwd,"getAllUserIDsNoDetailsWithNamedElements");
	    try	    {
            return getAllUserObjsNoDetails();
	    }
	    catch(Exception e)	    {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	/*++ (vic) - added from v2 for readable diff matching
        return userReadAll(verbosity);
    }

    @WebMethod
    public List<BBUser> bbUserReadSearchByCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course, @WebParam(name = "verbosity") BBUser.BBUserVerbosity verbosity) throws WebServiceException
	++*/
	public List<UserDetails> getEnrolledUserDetailsForGivenCourseWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getEnrolledUserDetailsForGivenCourseWithNamedElements");
	    extendedDetails = handleNullValue(extendedDetails);
	    try
	    {
    		return getEnrolledUserObjsForGivenCourse(courseId,extendedDetails?UserDetails.Verbosity.extended:UserDetails.Verbosity.standard);
        }
        catch (KeyNotFoundException knfe)
        {
    		throw new WebServiceException("CourseId '"+courseId+"' not found or does not exist ("+knfe.getMessage()+")");
	    }
        catch (Exception e)
        {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}


	/*!! (vic) - added role parameter
    @WebMethod
    public List<BBUser> bbUserReadSearchByCourseIdAndCMRole(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course, @WebParam(name = "") BBCourseMembershipRole cmRole, @WebParam(name = "verbosity") BBUser.BBUserVerbosity verbosity) throws WebServiceException
	++*/
	@WebMethod
	public List<UserDetails> getEnrolledStudentsForGivenCourseIDWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getEnrolledStudentsForGivenCourseIDWithNamedElements");
	    try
	    {
            return getEnrolledStudentsForGivenCourseIDWithNamedElements(courseId);
	    }
	    catch(Exception e)
	    {
    		throw new WebServiceException("Error while retrieving students: "+e.toString()+" "+e.getMessage());
	    }
	}

	/*!! (vic) - new
    @WebMethod
    public Boolean bbUserUpdate(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user, @WebParam(name = "portalRole") BBRole portalRole, @WebParam(name = "secondaryPortalRoles") List<BBRole> secondaryPortalRoles, @WebParam(name = "systemRole") BBRole systemRole) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbUserAdd");
        return userCreateOrUpdate(user,portalRole,secondaryPortalRoles,systemRole,true);
    }
	++*/

	/*!! (vic) - new, came from addCourseXML ??
    private String checkAndTrimParam(String courseDetail) throws Exception
    {
	if(courseDetail!=null && !courseDetail.equalsIgnoreCase(""))
	{
	    return courseDetail.trim();
	}
	throw new Exception("Invalid course detail: '"+courseDetail+"'");
    }
	++*/
	/*!! (vic) - new
    private Boolean checkParam(String param)
    {
	if(param!=null && !param.equalsIgnoreCase(""))
	{
	    return true;
	}
	return false;
    }
	++*/
	/*!! (vic) - new, similar to the code in getAllCourseObjsNoDetails
    private List<BBCourse> convertCourseListToBBCourseList(List<Course> cl, BBCourse.BBCourseVerbosity verbosity) throws Exception
    {
        if(cl!=null && cl.size()>0)
        {
            List<BBCourse> rl = new ArrayList<BBCourse>();
            Course c = null;
            Iterator<Course> i = cl.iterator();
            while(i.hasNext())
            {
                c = ((Course)i.next());
                //The verbosity exception here "should" NEVER happen
                try{rl.add(new BBCourse(c,verbosity));}catch(Exception e){System.out.println("Error while instantiating course "+c.getCourseId()+": "+e.getMessage());}
            }
            return rl;
        }
        throw new Exception("No courses found");
    }
	++*/
	/*!! (vic) - new, similar to the code in getAllUserObjsNoDetails
    private List<BBUser> convertUserListToBBUserList(List<User> ul, BBUser.BBUserVerbosity verbosity) throws Exception
    {
        if(ul!=null && ul.size()>0)
        {
            List<BBUser> rl = new ArrayList<BBUser>();
            User u = null;
            Iterator<User> i = ul.iterator();
            while(i.hasNext())
            {
                u = ((User)i.next());
                //The verbosity exception here "should" NEVER happen
                try{rl.add(new BBUser(u,verbosity));}catch(Exception e){System.out.println("Error while instantiating user "+u.getUserName()+": "+e.getMessage());}
            }
            return rl;
        }
        throw new Exception("No courses found");
    }
	++*/

	/*++ (vic) - added from v2 for readable diff matching
    private boolean courseCreate(BBCourse course)
	++*/
    private boolean addCourse(String courseId, String batchUID, String title, String courseDescription,
                              Boolean available, Boolean allowGuests, Boolean allowObservers) throws WebServiceException
    {
        available = handleNullValue(available);
        allowGuests = handleNullValue(allowGuests);
        allowObservers = handleNullValue(allowObservers);

        try
        {
            CourseDbLoader.Default.getInstance().loadByCourseId(courseId);
            //return "Error: Course may already exist";
            throw new WebServiceException("Error: Course may already exist");
        }
        catch(KeyNotFoundException knfe){}
        catch(Exception e)
        {
            //return "Error while trying to check if course already exists: "+e;
            throw new WebServiceException("Error while trying to check if course already exists: "+e.toString()+" "+e.getMessage());
        }

        Course c = new Course();
        try
        {
            c.setBatchUid(checkCourseDetail(batchUID));
            c.setCourseId(checkCourseDetail(courseId));
            c.setDescription(checkCourseDetail(courseDescription));
            c.setTitle(checkCourseDetail(title));
            c.setAllowGuests(allowGuests);
            c.setAllowObservers(allowObservers);
            c.setIsAvailable(available);
            blackboard.persist.course.CourseDbPersister.Default.getInstance().persist(c);
        }
        catch(Exception e)
        {
            //return "Error while trying to add course: "+e.toString();
            throw new WebServiceException("Error while trying to add course: "+e.toString());
        }
        return true;
    }


	/*++ (vic) - added from v2 for readable diff matching
    private boolean courseDelete(BBCourse course)
	++*/
    private Boolean deleteCourseByCourseBbId(String courseBbId) throws WebServiceException
    {
        try
        {
            //You could just deleteById(bbPm.generateId()) but this way you also check if course exists
            CourseDbPersister.Default.getInstance().deleteById(CourseDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Course.DATA_TYPE,courseBbId)).getId());
        }
        catch(Exception e)
        {
            throw new WebServiceException("Please provide a valid courseBbId for a course: "+e.toString());
        }
        return true;
    }


	/*++ (vic) - added from v2 for readable diff matching
    private Boolean courseMembershipCreate(BBCourseMembership courseMembership) throws WebServiceException
	++*/
    private Boolean enrollGivenUserOnGivenCourse(String userId, String courseId, String role) throws WebServiceException
    {
        Id uid = null;
        Id cid = null;

        try
        {
            uid = UserDbLoader.Default.getInstance().loadByUserName(userId).getId();
        }
        catch(Exception e)
        {
            //return "Error: Please provide a valid userId";
            throw new WebServiceException("Please provide a valid userId "+e.toString());
        }

        try
        {
            cid = CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId();
        }
        catch(Exception e)
        {
            //return "Error: Please provide a valid courseId";//+e.toString();
            throw new WebServiceException("Please provide a valid courseId "+e.toString());
        }

        try
        {
            CourseMembership cm = new CourseMembership();
            cm.setCourseId(cid);
            cm.setUserId(uid);
            cm.setIsAvailable(true);
            cm.setRole(Role.fromFieldName(role.toUpperCase()));
            CourseMembershipDbPersister.Default.getInstance().persist(cm);
        }
        catch(IllegalArgumentException iae)
        {
            //return "Error: Problem while trying to set role, does role exist? "+iae.toString();
            throw new WebServiceException("Problem while trying to set role, does role exist? "+iae.toString());
        }
        catch(PersistenceException e)
        {
            throw new WebServiceException("Problem while trying to update coursemembership details "+e.toString()+", does this enrollment already exist?");
        }
        catch(Exception e)
        {
            //return "Error: Problem while trying to update coursemembership details "+e.toString();
            throw new WebServiceException("Problem while trying to update coursemembership details "+e.toString());
        }
        return true;
    }

	/*++ (vic) - added from v2 for readable diff matching
    private Boolean courseMembershipDelete(BBCourseMembership courseMembership) throws WebServiceException
	++*/
    private Boolean unEnrollGivenUserFromGivenCourse(String userId, String courseId) throws WebServiceException
    {
        Course c = null;
        User u = null;
        CourseMembership cm = null;

        try
        {
            c = CourseDbLoader.Default.getInstance().loadByCourseId(courseId);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Course Id is invalid or does not exist "+e.getMessage());
        }

        try
        {
            u = UserDbLoader.Default.getInstance().loadByUserName(userId);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: User Id is invalid or does not exist "+e.getMessage());
        }

        try
        {
            //get enrollment id
            cm = CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(c.getId(),u.getId());
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Given user does not appear to be enrolled on given course "+e.getMessage());
        }

        try
        {
            //then delete
            CourseMembershipDbPersister.Default.getInstance().deleteById(cm.getId());
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error whilst trying to unenroll given user from give course "+e.toString());
        }
        return true;
    }

	/*++ (vic) - added from v2 for readable diff matching
    private BBCourseQuota courseQuotaRead(BBCourse course) throws WebServiceException
	++*/
    private CourseQuotaDetails getCourseQuotaDetails(String courseId) throws WebServiceException
    {
        try
        {
            return new CourseQuotaDetails(CourseQuota.createInstance(CourseDbLoader.Default.getInstance().loadByCourseId(courseId)));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not find course with that Id "+e.getMessage()+" "+e.toString());
        }
    }

	/*++ (vic) - added from v2 for readable diff matching
    private Boolean courseQuotaUpdate(BBCourse course, BBCourseQuota courseQuota) throws WebServiceException
	++*/
    private Boolean modifyCourseQuotaDetails(String courseID, Boolean overrideDiskQuota, Boolean overrideUploadLimit, Long absoluteLimit, Long softLimit, Long uploadLimit) throws WebServiceException
    {
        if(overrideDiskQuota!=null || overrideUploadLimit!=null || absoluteLimit!=null || softLimit!=null || uploadLimit!=null)
        {
            Course course = null;
            try
            {
                course = CourseDbLoader.Default.getInstance().loadByCourseId(courseID);
            }
            catch(Exception e)
            {
                throw new WebServiceException("Error: Could not load course to modify quota for, does it exist? "+e.getMessage());
            }

            try
            {
                setOrModifyCourseRegistryValue(course.getId(),"quota_override",overrideDiskQuota?"Y":"N");
                setOrModifyCourseRegistryValue(course.getId(),"quota_upload_override",overrideUploadLimit?"Y":"N");

                if(absoluteLimit!=null)
                {
                    course.setAbsoluteLimit(absoluteLimit);
                }

                if(softLimit!=null)
                {
                    course.setSoftLimit(softLimit);
                }

                if(uploadLimit!=null)
                {
                    course.setUploadLimit(uploadLimit);
                }

                CourseDbPersister.Default.getInstance().persist(course);
            }
            catch(Exception e)
            {
                throw new WebServiceException( "Error: Could not modify course quota settings - "+e.getMessage());
            }
        }
        return true;
    }

	/*!! (vic) - added loading by CourseId and UserId if they are available as courseMembership.getUser().getBbId() and courseMembership.getCourse().getCourseBbId()
    private BBCourseMembership courseMembershipRead(BBCourseMembership courseMembership, BBCourseMembership.BBCourseMembershipVerbosity verbosity) throws WebServiceException
	++*/
    private CourseMembershipDetails getCourseMembershipDetailsForGivenCourseMembershipBbId(String courseMembershipBbId) throws WebServiceException
    {
        try
        {
            return new CourseMembershipDetails(CourseMembershipDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(CourseMembership.DATA_TYPE, courseMembershipBbId)),CourseMembershipDetails.Verbosity.standard);
        }
        catch(KeyNotFoundException e)
        {
            throw new WebServiceException("Error: Given course membership does not exist");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: While retrieving course membership... "+e.toString());
        }
    }


	/*++ (vic) - added from v2 for readable diff matching
    /*private BBCourseMembership courseMembershipReadSearchByUserIdAndCourseId(BBUser user, BBCourse course,BBCourseMembership.BBCourseMembershipVerbosity verbosity)
    {
        try
        {
            return new BBCourseMembership(CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseId()).getId(),UserDbLoader.Default.getInstance().loadByUserName(user.getUserName()).getId()),verbosity);
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
        }
    }*/

    private BBCourseMembership courseMembershipReadSearchByUserIdAndCourseId(BBUser user, BBCourse course,BBCourseMembership.BBCourseMembershipVerbosity verbosity, Boolean loadUser)
	++*/

    private CourseMembershipDetails getCourseMembershipBbIdForGivenUserIdAndCourseId(String userId, String courseId) throws WebServiceException
    {
        try
        {
            return new CourseMembershipDetails(CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId(),UserDbLoader.Default.getInstance().loadByUserName(userId).getId()),CourseMembershipDetails.Verbosity.minimal);
        }
        catch(KeyNotFoundException e)
        {
            throw new WebServiceException("Error: Given course or user does not exist or user is not enrolled on given course");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: While retrieving coursemembership... "+e.toString());
        }
    }

	/*!! (vic) - added to load by bbId if courseId == null
    private BBCourse courseRead(BBCourse course, BBCourse.BBCourseVerbosity verbosity) throws WebServiceException
	++*/
    private CourseDetails getCourseDetailsObjFromCourseId(String courseId, Boolean extendedDetails) throws Exception
    {
        return new CourseDetails(CourseDbLoader.Default.getInstance().loadByCourseId(courseId),extendedDetails?CourseDetails.Verbosity.extended:CourseDetails.Verbosity.standard);
    }


    /**
     * Role (Case insensitive): COURSE_BUILDER, DEFAULT, GRADER, GUEST, INSTRUCTOR, TEACHING_ASSISTANT, NONE, STUDENT,
     *
     * Web service operation
     */
    @WebMethod
    public String enrollGivenUserOnGivenCourseXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "userId") String userId, @WebParam(name = "role") String role) throws WebServiceException
    {
        authoriseMethodUse(pwd,"enrollGivenUserOnGivenCourseXML");
        return toXML(null,enrollGivenUserOnGivenCourse(userId,courseId,role));
    }

	/*!! (vic) - added verbosity parameter
        catch(KeyNotFoundException knfe)
        {
            error = "No matching course";
        }
        catch(Exception e)
        {
            error = "Error whilst searching to see if course exists: "+e.toString();
        }
        throw new WebServiceException(error);
    }
    private List<BBCourse> courseReadAll(BBCourse.BBCourseVerbosity verbosity)
	++*/
    private List<CourseDetails> getAllCourseObjsNoDetails() throws WebServiceException
    {
        try
        {
            List<CourseDetails> cl = new ArrayList<CourseDetails>();
            List<Course> courseList = CourseDbLoader.Default.getInstance().loadAllCourses();
            Iterator i = courseList.iterator();
            while(i.hasNext())
            {
                cl.add(new CourseDetails((Course)i.next(),CourseDetails.Verbosity.minimal));
            }
            return cl;
        }
        catch (KeyNotFoundException knfe)
        {
            //return new UserDetailsStandard(new Exception("No users found: "+e.getMessage()));
            throw new WebServiceException("No courses found: "+knfe.getMessage());
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.toString());
        }
    }


	/*!! (vic) - added role parameter, added verbosity parameter
    private List<BBCourse> courseReadSearchByUserIdAndCMRole(BBUser user, BBCourseMembershipRole cmRole, BBCourse.BBCourseVerbosity verbosity) throws WebServiceException
	++*/
    private List<CourseDetails> getAllCourseIDsWhereGivenUserIdIsAnInstructor(String userId) throws Exception
    {
        List<CourseDetails> rl = new ArrayList<CourseDetails>();
        List <Course> cl = CourseDbLoader.Default.getInstance().loadByUserIdAndCourseMembershipRole(UserDbLoader.Default.getInstance().loadByUserName(userId).getId(),CourseMembership.Role.INSTRUCTOR);

        if(cl.size()>0)
        {
            Iterator i = cl.iterator();
            while(i.hasNext())
            {
                rl.add(new CourseDetails(((Course)i.next()),CourseDetails.Verbosity.minimal));
            }
            return rl;
        }
        throw new Exception("No courses found");
    }

    
	/*!! (vic) - added verbosity parameter
            throw new WebServiceException("Error whilst searching to see if course exists: "+e.toString());
        }
    }

    private List<BBCourse> courseReadSearchByRegex(String regex, BBCourse.BBCourseVerbosity verbosity) throws WebServiceException
	++*/
    private List<CourseDetails> getAllCourseIDObjsWhereCourseIdContainsGivenSearchString(String searchString) throws Exception
    {
        List<CourseDetails> rl = new ArrayList<CourseDetails>();
        List<Course> cl = CourseDbLoader.Default.getInstance().loadAllCourses();

        if(cl.size()>0)
        {
            Pattern pattern =  Pattern.compile(searchString);
            Course c = null;
            Iterator i = cl.iterator();

            while(i.hasNext())
            {
            c = ((Course)i.next());
            if(isAMatch(pattern,c.getCourseId()))
            {
                rl.add(new CourseDetails(c,CourseDetails.Verbosity.minimal));
            }
            }

            if(rl.size()<1)
            {
            throw new Exception("No matches found");
            }
            return rl;
        }
        throw new Exception("No courses found");
    }

	/*++ (vic) - added from v2 for readable diff matching
    private List<BBEnrollment> enrollmentReadSearchByUserId(BBUser user) throws WebServiceException
	++*/
    private List<EnrollmentDetails> getEnrollmentDetailsObjsForGivenUser(String userId) throws Exception
    {
        List<EnrollmentDetails> rl = new ArrayList<EnrollmentDetails>();
        Enrollment enrollment = new Enrollment();
        enrollment.setPersonBatchUid(userId);
        List<Enrollment> membershipList = Default.getInstance().load(enrollment);
        if(membershipList.size()<1)
        {
            throw new Exception("The user does not exist or is not enrolled on any courses");
        }
        Iterator i = membershipList.iterator();
        while(i.hasNext())
        {
            rl.add(new EnrollmentDetails((Enrollment)i.next()));
        }
        return rl;
    }


    private Score getScoreObjForGivenCourseMembershipBbIdAndLineItemBbId(String courseMembershipBbId, String lineItemBbId) throws Exception
    {
	    return ((ScoreDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(ScoreDbLoader.TYPE)).loadByCourseMembershipIdAndLineitemId
		(
		    CourseMembershipDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(CourseMembership.DATA_TYPE,courseMembershipBbId)).getId(),
		    ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId)).getId()
		);
    }


	/*++ (vic) - added from v2 for readable diff matching
    private boolean gradeCentreAttemptDelete(BBAttempt attempt) throws WebServiceException
	++*/
    private Boolean deleteGivenAttemptByAttemptBbId(String attemptBbId) throws WebServiceException
    {
        try
        {
            AttemptDbPersister.Default.getInstance().deleteById(AttemptDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Attempt.DATA_TYPE,attemptBbId)).getId());
        }
        catch(Exception e)
        {
            throw new WebServiceException( "Error: Could not delete attempt - "+e.getMessage());
        }
        return true;
    }


	/*++ (vic) - added from v2 for readable diff matching
    private List<BBAttempt> gradeCentreAttemptReadSearchByOutcomeDefinitionId(BBOutcomeDefinition outcomeDef, BBAttempt.BBAttemptVerbosity verbosity) throws WebServiceException
	++*/
    private List<AttemptDetails> getAttemptDetailsObjsForGivenOutcomeDefBbId(String outcomeDefBbId, Boolean extendedDetails) throws Exception
    {
        List<Attempt> al = AttemptDbLoader.Default.getInstance().loadByOutcomeDefinitionId(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(OutcomeDefinition.DATA_TYPE,outcomeDefBbId));
        List<AttemptDetails> rl = new ArrayList<AttemptDetails>();
        Iterator i = al.iterator();
        while(i.hasNext())
        {
            rl.add(new AttemptDetails((Attempt)i.next(),extendedDetails?AttemptDetails.Verbosity.extended:AttemptDetails.Verbosity.standard));
        }
        return rl;
    }



	/*++ (vic) - added from v2 for readable diff matching
    private boolean gradeCentreLineitemAdd(BBLineitem lineitem, BBCourse course) throws WebServiceException
	++*/
    private Boolean addLineitemToGivenCourseId(String courseId, String name,
                                                String type, Float pointsPossible, Float weight,
                                                Boolean available) throws WebServiceException
    {
        try
        {
            Lineitem li = new Lineitem();
            li.setCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId());
            //li.setAssessmentLocation(Lineitem.AssessmentLocation.INTERNAL);
            li.setName(name);
            li.setIsAvailable(available);
            li.setPointsPossible(pointsPossible);
            li.setType(type);
            li.setWeight(weight);
            ((LineitemDbPersister)BbServiceManager.getPersistenceService().getDbPersistenceManager().getPersister(LineitemDbPersister.TYPE)).persist(li);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not add lineitem "+e.toString());
        }
        return true;
    }


	/*++ (vic) - added from v2 for readable diff matching
    private boolean gradeCentreLineitemOrOutcomeDefinitionDelete(String Id)
	++*/
    private Boolean deleteOutcomeDefinitionByOutcomeDefBbId(String outcomeDefBbId) throws WebServiceException
    {
    	try
        {
            OutcomeDefinitionDbPersister.Default.getInstance().deleteById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(OutcomeDefinition.DATA_TYPE,outcomeDefBbId));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Does that item/outcome exist? "+e.toString());
        }
        return true;
    }

	/*++ (vic) - added from v2 for readable diff matching
    private BBLineitem gradeCentreLineitemRead(BBLineitem lineitem) throws WebServiceException
	++*/
    private LineitemDetails getLineitemDetailsForGivenLineitemBbId(String lineitemBbId) throws WebServiceException
    {
        try
        {
            return new LineitemDetails(((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE, lineitemBbId)));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve lineitem details "+e.toString());
        }
    }


	/*!! (vic) - modified to use blackboard.platform.gradebook2.GradebookManagerFactory for loading of list of LineitemDetails
    private List<BBLineitem> gradeCentreLineitemReadSearchByCourseId(BBCourse course) throws WebServiceException
	++*/
    private List<LineitemDetails> getAllLineitemObjsForCourseId(String courseId) throws Exception
    {
        List<LineitemDetails> rl = new ArrayList<LineitemDetails>();
        Iterator i = null;
        //try{System.err.println(new blackboard.platform.plugin.PlugInManager().getPlatformVersion().toString());}catch(Exception e){System.err.println("Could not retrieve version info");}
        //if(true) - this should be a version check instead of (just) a try/catch block
        //{
            try
            {
                Class gradebookManagerFactoryClass = Class.forName("blackboard.platform.gradebook2.GradebookManagerFactory");
                Object gradebookManager = gradebookManagerFactoryClass.getDeclaredMethod("getInstanceWithoutSecurityCheck").invoke(gradebookManagerFactoryClass.newInstance());
                //blackboard.platform.gradebook2.GradebookManager gm = blackboard.platform.gradebook2.GradebookManagerFactory.getInstanceWithoutSecurityCheck();

                Class gbmClass = gradebookManager.getClass();
                List l = (List)gbmClass.getDeclaredMethod("getGradebookItems",new Class[]{blackboard.persist.Id.class}).invoke(gradebookManager,new Object[]{((CourseDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(CourseDbLoader.TYPE)).loadByCourseId(courseId).getId()});
                //List<blackboard.platform.gradebook2.GradableItem> l = gm.getGradebookItems(((CourseDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(CourseDbLoader.TYPE)).loadByCourseId(courseId).getId());

                i = l.iterator();
                while (i.hasNext())
                {
                    rl.add(new LineitemDetails(i.next()));
                }
                //or you will get an out of memory error...
                l.clear();
                l = null;
            }
            catch(ClassNotFoundException cnfe)
            {
                List<Lineitem> lil = ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId());
                if(lil.size()<1)
                {
                    throw new Exception("No lineitems found");
                }
                i = lil.iterator();
                while(i.hasNext())
                {
                    rl.add(new LineitemDetails((Lineitem)i.next()));
                }
            }
        //}
        return rl;
    }


	/*++ (vic) - added from v2 for readable diff matching
    private List<BBOutcome> gradeCentreOutcomeReadSearchByOutcomeDefinitionId(BBOutcomeDefinition outcomeDef) throws WebServiceException
	++*/
    private List<OutcomeDetails> getAllOutcomesForGivenOutcomeDefBbId(String outcomeDefBbId) throws Exception
    {
        List<OutcomeDetails> rl = new ArrayList<OutcomeDetails>();
        List<Outcome> ol = Arrays.asList(((OutcomeDefinitionDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(OutcomeDefinitionDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(OutcomeDefinition.DATA_TYPE,outcomeDefBbId)).getOutcomes());
        if(ol.isEmpty())
        {
            throw new Exception("No outcomes found for outcomeDefBbId");
        }
        Iterator i = ol.iterator();
        while(i.hasNext())
        {
            rl.add(new OutcomeDetails((Outcome)i.next()));
        }
        return rl;
    }



	/*++ (vic) - added from v2 for readable diff matching
    private BBOutcomeDefinition gradeCentreOutcomeDefinitionRead(BBOutcomeDefinition outcomeDef) throws WebServiceException
	++*/
    private OutcomeDefinitionDetails getOutcomeDefinitionDetailsFromOutcomeDefinitionBbId(String outcomeDefBbId) throws WebServiceException
    {
        try
        {
            return new OutcomeDefinitionDetails(OutcomeDefinitionDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(OutcomeDefinition.DATA_TYPE,outcomeDefBbId)));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve outcomeDefinition details "+e.toString());
        }
    }


	/*++ (vic) - added from v2 for readable diff matching
    private List<BBOutcomeDefinition> gradeCentreOutcomeDefinitionReadSearchByCourseId(BBCourse course) throws WebServiceException
	++*/
    private List<OutcomeDefinitionDetails> getAllOutcomeDefinitionsForGivenCourse(String courseId) throws Exception
    {
        List<OutcomeDefinition> ods = ((OutcomeDefinitionDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(OutcomeDefinitionDbLoader.TYPE)).loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId());
        if(ods.size()<1)
        {
            throw new Exception("No outcome defs found");
        }
        List<OutcomeDefinitionDetails> rl = new ArrayList<OutcomeDefinitionDetails>();
        Iterator i = ods.iterator();
        while(i.hasNext())
        {
            rl.add(new OutcomeDefinitionDetails((OutcomeDefinition)i.next()));
        }
        return rl;
    }


	/*++ (vic) - added from v2 for readable diff matching
    private BBOutcome gradeCentreOutcomeRead(BBOutcome outcome) throws WebServiceException
	++*/
    private OutcomeDetails getOutcomeDetailsFromOutcomeBbId(String outcomeBbId) throws WebServiceException
    {
        try
        {
            return new OutcomeDetails(OutcomeDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Outcome.DATA_TYPE, outcomeBbId)));
        }
        catch(KeyNotFoundException e)
        {
            throw new WebServiceException("Error: No outcome found. Does outcome exist?");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: While retrieving outcome... "+e.toString());
        }
    }


	/*++ (vic) - added from v2 for readable diff matching
    private BBScore gradeCentreScoreRead(BBScore score, BBScore.BBScoreVerbosity verbosity) throws WebServiceException
	++*/
    private ScoreDetails getScoreDetailsForGivenScoreBbId(String scoreBbId, Boolean extendedDetails) throws WebServiceException
    {
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            return new ScoreDetails(((ScoreDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(ScoreDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Score.SCORE_DATA_TYPE,scoreBbId)),extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard);
        }
        catch(KeyNotFoundException e)
        {
            throw new WebServiceException("Error: No score found, has user taken test and has it been marked? Is user on course? Does lineitem exist?");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: While retrieving score... "+e.toString());
        }
    }



	/*++ (vic) - added from v2 for readable diff matching
    private List<BBScore> gradeCentreScoreReadSearchByLineitemId(BBLineitem lineitem, BBScore.BBScoreVerbosity verbosity) throws WebServiceException
	++*/
    private List<ScoreDetails> getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, Boolean extendedDetails) throws Exception
    {
        List<Score> scores = ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId)).getScores();
        if(scores.size()<1)
        {
            throw new Exception("No scores found");
        }
        List<ScoreDetails> rl = new ArrayList<ScoreDetails>();
        Iterator i = scores.iterator();
        while(i.hasNext())
        {
            rl.add(getScoreDetailsObjForGivenScoreObj((Score)i.next(),extendedDetails));
        }
        return rl;
    }


	/*++ (vic) - added from v2 for readable diff matching
    private BBScore gradeCentreScoreReadSearchByLineitemIdAndUserId(BBLineitem lineitem, BBUser user, BBScore.BBScoreVerbosity verbosity) throws WebServiceException
	++*/
    private ScoreDetails getScoreDetailsForGivenUserIdAndLineItemBbId(String userId, String lineItemBbId, Boolean extendedDetails) throws WebServiceException
    {
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            return getScoreDetailsObjForGivenScoreObj
            (
                getScoreObjForGivenCourseMembershipBbIdAndLineItemBbId
                (
                    CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId
                    (
                        ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId)).getCourseId(),
                        UserDbLoader.Default.getInstance().loadByUserName(userId).getId()
                    ).getId().toExternalString(),
                    lineItemBbId
                ),
                extendedDetails
            );
        }
        catch(KeyNotFoundException e)
        {
            throw new WebServiceException("Error: No score found, has user taken test and has it been marked? Is user on course? Does lineitem exist?");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: While retrieving score... "+e.toString());
        }
    }


	/*++ (vic) - added from v2 for readable diff matching
    private BBScore gradeCentreScoreReadSearchByLineitemIdAndCourseMembershipId(BBLineitem lineitem, BBCourseMembership courseMembership, BBScore.BBScoreVerbosity verbosity) throws WebServiceException
	++*/
    private ScoreDetails getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbId(String courseMembershipBbId, String lineItemBbId, Boolean extendedDetails) throws WebServiceException
    {
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            return getScoreDetailsObjForGivenScoreObj(getScoreObjForGivenCourseMembershipBbIdAndLineItemBbId(courseMembershipBbId, lineItemBbId),extendedDetails);
        }
        catch(KeyNotFoundException e)
        {
            throw new WebServiceException("Error: No score found, has user taken test and has it been marked? Is user on course? Does lineitem exist?");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: While retrieving score... "+e.toString());
        }
    }


	/*++ (vic) - added from v2 for readable diff matching
    private BBGradeCentreSettings gradeCentreSettingsRead(BBCourse course) throws WebServiceException
	++*/
    private GradeBookSettingsDetails getGradeBookSettingDetailsObjForGivenCourseId(String courseId) throws Exception
    {
        return new GradeBookSettingsDetails(GradeBookSettingsDbLoader.Default.getInstance().loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId()));
    }

	/*++ (vic) - added from v2 for readable diff matching
    private boolean groupAdd(BBGroup group, BBCourse course, String descType) throws WebServiceException
	++*/
    /**
     * Web service operation
     */
    @WebMethod
    private Boolean addGroupToGivenCourse(String courseId, String title, String description, String descType,
					Boolean available, Boolean chatRoomAvailable, Boolean discussionBoardAvailable,
                    Boolean emailAvailable, Boolean transferAvailable) throws WebServiceException
    {
        try
        {
            Group g = new Group();
            g.setCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId());
            g.setDescription(new FormattedText(description,Type.fromFieldName(descType.trim().toUpperCase())));
            g.setIsAvailable(available);
            g.setIsChatRoomAvailable(chatRoomAvailable);
            g.setIsDiscussionBoardAvailable(discussionBoardAvailable);
            g.setIsEmailAvailable(emailAvailable);
            g.setIsTransferAreaAvailable(transferAvailable);
            g.setTitle(title);
            GroupDbPersister.Default.getInstance().persist(g);
        }
        catch(Exception e)
        {
            //return "Invalid description formatted text type, try: HTML/PLAIN_TEXT/SMART_TEXT";
            throw new WebServiceException("Error while trying to add group "+e.toString());
        }
        return true;
    }


	/*++ (vic) - added from v2 for readable diff matching
    private boolean groupDelete(BBGroup group) throws WebServiceException
	++*/
    private Boolean deleteGroup(String groupId) throws WebServiceException
    {
        try
        {
            GroupDbPersister.Default.getInstance().deleteById(GroupDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,groupId)).getId());
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Please provide a valid Id for a group");
        }
        return true;
    }


	/*++ (vic) - added from v2 for readable diff matching
    private List<BBGroup> groupReadByCourseId(BBCourse course) throws WebServiceException
	++*/
    private List<GroupDetails> getGroupDetailsForGivenCourseId(String courseId) throws WebServiceException
    {
        try
        {
            List<GroupDetails> rl = new ArrayList<GroupDetails>();
            List<Group> gl = GroupDbLoader.Default.getInstance().loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId());
            if(gl.size()<1)
            {
                throw new Exception("No groups found");
            }
            Iterator i = gl.iterator();
            while(i.hasNext())
            {
                rl.add(new GroupDetails((Group)i.next()));
            }
            return rl;
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error "+e.toString());
        }
    }


	/*++ (vic) - added from v2 for readable diff matching
    private BBGroup groupRead(BBGroup group) throws WebServiceException
	++*/
    private GroupDetails getGroupDetailsForGivenGroupBbId(String groupBbId) throws WebServiceException
    {
        try
        {
            return new GroupDetails(GroupDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,groupBbId)));
        }
        catch(KeyNotFoundException knfe)
        {
            throw new WebServiceException("Error: The given group does not exist");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve group for given Id "+e.toString());
        }
    }


	/*++ (vic) - added from v2 for readable diff matching
    private boolean groupUpdate(BBGroup group, String descType) throws WebServiceException
	++*/
    private Boolean modifyGroup(String groupBbId, String title, String description,
                                String descType, Boolean available, Boolean chatRoomAvailable,
                                Boolean discussionBoardAvailable, Boolean emailAvailable,
                                Boolean transferAvailable) throws WebServiceException
    {
        try
        {
            Group g = GroupDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,groupBbId));
            g.setDescription(new FormattedText(description,Type.fromFieldName(descType)));
            g.setIsAvailable(available);
            g.setIsChatRoomAvailable(chatRoomAvailable);
            g.setIsDiscussionBoardAvailable(discussionBoardAvailable);
            g.setIsEmailAvailable(emailAvailable);
            g.setIsTransferAreaAvailable(transferAvailable);
            g.setTitle(title);
            GroupDbPersister.Default.getInstance().persist(g);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while trying to add group "+e.toString());
        }
        return true;
    }


	/*++ (vic) - added from v2 for readable diff matching
    private List<BBGroupMembership> groupMembershipReadByGroupId(BBGroup group) throws WebServiceException
	++*/
    private List<GroupMemberDetails> getGroupMembersForGivenGroupBbId(String groupBbId) throws WebServiceException
    {
        try
        {
            List<GroupMemberDetails> rl = new ArrayList<GroupMemberDetails>();
            List<GroupMembership> gml = GroupMembershipDbLoader.Default.getInstance().loadByGroupId(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,groupBbId));
            if(gml.size()<1)
            {
                throw new Exception("No group members found");
            }
            Iterator i = gml.iterator();
            while(i.hasNext())
            {
                rl.add(new GroupMemberDetails((GroupMembership)i.next()));
            }
            return rl;
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: "+e.toString());
        }
    }
    

	/*++ (vic) - added from v2 for readable diff matching
    private boolean groupMembershipCreateByUserIdAndGroupId(BBUser user, BBGroup group) throws WebServiceException
	++*/
    private Boolean addGivenUserToGivenGroup(String userId, String groupId) throws WebServiceException
    {
        try
        {
            Group g = GroupDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,groupId));
            GroupMembership gm = new GroupMembership();
            gm.setCourseMembershipId(CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(g.getCourseId(),UserDbLoader.Default.getInstance().loadByUserName(userId).getId()).getId());
            gm.setGroupId(g.getId());
            GroupMembershipDbPersister.Default.getInstance().persist(gm);
        }
        catch(PersistenceException pe)
        {
            throw new WebServiceException("Error: Is user already part of this group?");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while trying to add user to group: "+e.toString());
        }
        return true;
    }


	/*++ (vic) - added from v2 for readable diff matching
    private boolean groupMembershipDeleteByUserIdAndGroupId(BBUser user, BBGroup group) throws WebServiceException
	++*/
    private Boolean deleteGivenUserFromGivenGroup(String userId, String groupId) throws WebServiceException
    {
        try
        {
            GroupMembershipDbPersister.Default.getInstance().deleteById(GroupMembershipDbLoader.Default.getInstance().loadByGroupAndUserId(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,groupId),UserDbLoader.Default.getInstance().loadByUserName(userId).getId()).getId());
        }
        catch(KeyNotFoundException knfe)
        {
            throw new WebServiceException("Error: User is not a member of this group");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while attempting to delete group membership: "+e.toString());
        }
        return true;
    }


    private boolean isAMatch(Pattern pattern, String searchStr)
    {
        boolean match = false;
        Matcher matcher = pattern.matcher(searchStr);
        while (matcher.find())
        {
            //System.err.println("I found the text "+matcher.group()+" starting at index "+matcher.start()+" and ending at index "+matcher.end());
            match = true;
        }
        return match;
    }


	/*++ (vic) - added from v2 for readable diff matching
    private List<PortalRole> parseSecondaryPortalRoles(List<BBRole> roles) throws WebServiceException
	++*/
	private ArrayList<PortalRole> parseSecondaryPortalRoles(String[] roles) throws WebServiceException
	{
	    ArrayList pRoles = new ArrayList();
        try
        {
            PortalRoleDbLoader prl = PortalRoleDbLoader.Default.getInstance();
            for(int i=0; i<roles.length; i++)
            {
                if(roles[i]!=null && !roles[i].trim().equalsIgnoreCase(""))
                {
                    pRoles.add(prl.loadByRoleId(roles[i].trim()));
                }
            }
        }
        catch(Exception e)
        {
            throw new WebServiceException("Invalid role specified "+e.toString());
        }
	    return pRoles;
	}



	/*++ (vic) - added from v2 for readable diff matching
    private List<BBRole> roleSecondaryPortalReadSearchByUserId(BBUser user) throws WebServiceException
	++*/
	private List<bbuws.Role> getSecondaryPortalRolesForGivenUserId(String userId) throws Exception
	{
	    List<PortalRole> rl = PortalRoleDbLoader.Default.getInstance().loadSecondaryRolesByUserId(UserDbLoader.Default.getInstance().loadByUserName(userId).getId());
	    List<bbuws.Role> prl = new ArrayList<bbuws.Role>();
	    Iterator i = rl.iterator();

	    while(i.hasNext())
	    {
            prl.add(new bbuws.Role((PortalRole)i.next()));
	    }
	    return prl;
	}


	/*!! (vic) - new, similar to setOrModifySecondaryPortalRolesForGivenUserId
    private Boolean roleSecondaryPortalUpdate(BBUser user, List<BBRole> roles) throws WebServiceException
    {
        //if roles.Length == 0 it will simply delete any existing roles
        try
        {
            User u = UserDbLoader.Default.getInstance().loadByUserName(user.getUserName(),null,true);
            PortalRole priPR = u.getPortalRole();
            UserRoleDbPersister prstr = UserRoleDbPersister.Default.getInstance();
            prstr.deleteAllByUserId(u.getId());

            Iterator<PortalRole> i = parseSecondaryPortalRoles(roles).iterator();
            PortalRole pr = null;
            UserRole ur = null;
	++*/


	/*++ (vic) - added from v2 for readable diff matching
    private List<BBRole> roleSecondarySystemReadSearchByUserId(BBUser user) throws WebServiceException
	++*/
	private List<bbuws.Role> getSecondarySystemRolesForGivenUserId(String userId) throws Exception
	{
	    List<SystemRole> rl = DomainManagerFactory.getInstance().getDefaultDomainRolesForUser(userId);
	    List<bbuws.Role> prl = new ArrayList<bbuws.Role>();
	    Iterator i = rl.iterator();

	    while(i.hasNext())
	    {
            prl.add(new bbuws.Role((SystemRole)i.next()));
	    }
	    return prl;
	}


	/*!! (vic) - new, complements getCourseMembershipBbIdForGivenUserIdAndCourseId
    private BBRole roleUserReadSearchByUserIdAndCourseId(BBUser user, BBCourse course) throws WebServiceException
	++*/


    private Boolean setOrModifyCourseRegistryValue(Id crsId, String regKey, String value) throws Exception
    {
        CourseRegistryEntry entry = null;
        try
        {
            entry = CourseRegistryEntryDbLoader.Default.getInstance().loadByKeyAndCourseId(regKey,crsId);
        }
        catch(Exception e)
        {
            //ignore this error. if there is an exception, it means entry not found for this course,
            entry = new CourseRegistryEntry();
            entry.setCourseId(crsId);
            entry.setKey(regKey);
            entry.validate();
        }
        entry.setValue(value);
        CourseRegistryEntryDbPersister.Default.getInstance().persist(entry);
        return true;
    }


    private Boolean setOrModifySecondaryPortalRolesForGivenUserId(String userId, ArrayList<PortalRole> roles) throws WebServiceException
    {
   	    //if roles.Length == 0 it will simply delete any existing roles
	    try
	    {
            User u = UserDbLoader.Default.getInstance().loadByUserName(userId,null,true);
            PortalRole priPR = u.getPortalRole();
            UserRoleDbPersister prstr = UserRoleDbPersister.Default.getInstance();
            prstr.deleteAllByUserId(u.getId());
            PortalRole pr = null;
            UserRole ur = null;

            for(int i=0; i<roles.size();i++)
            {
                ur = new UserRole();
                ur.setUser(u);
                pr = roles.get(i);

                if(!pr.getRoleID().equalsIgnoreCase(priPR.getRoleID()))
                {
                    ur.setPortalRoleId(pr.getId());
                    prstr.persist(ur);
                }
            }
	    }
	    catch(Exception e)
	    {
            //return "Error: could not set secondary roles for user "+e.toString();
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	    return true;
    }


	/*!! (vic) - added update logic
    private Boolean userCreateOrUpdate(BBUser user,BBRole portalRole,List<BBRole> secPortalRoles,BBRole systemRole,Boolean isUpdate) throws WebServiceException
	++*/
	private Boolean addAUser(String pwd, String userId, String batchUId, String firstName, String middleName, String lastName,
				String emailAddress, String studentId, String userPwd, String gender, String birthYear,
				String birthMonth, String birthDay, String eduLevel, String company,
				String jobTitle, String department, String street1, String street2,
				String city, String stateOrProvince, String zipOrPostCode, String country,
				String website, String homePhone, String workPhone, String workFax,
				String mobilePhone, String portalRole, String[] secPortalRoles, String systemRole,
				/*String[] secSystemRoles,*/ Boolean available) throws WebServiceException
	{
	    try
	    {
            PersonDbLoader.Default.getInstance().load(userId);
            //return "Error: User may already exist";
            throw new WebServiceException("User may already exist");
	    }
	    catch(KeyNotFoundException knfe){} //We need the user to not exist
	    catch(Exception e)
	    {
            //return "Error while trying to check if user already exists: "+e;
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }

	    Person p = new Person();
        ArrayList<PortalRole> secPRoles = null;
        String debug = "setting user name";
        try
	    {
            p.setUserName(checkUserDetail(userId));//userid
            debug = "setting batch uid";
            p.setBatchUid(checkUserDetail(batchUId));//batchuid
            debug = "setting given name";
            p.setGivenName(checkUserDetail(firstName));//firstname
            debug = "setting middle name";
            try{p.setMiddleName(checkUserDetail(middleName));}catch(Exception e){}//middlename - Catch Exception as it's not a mandatory field
            debug = "setting family name";
            p.setFamilyName(checkUserDetail(lastName));//lastname
            debug = "setting email address";
            p.setEmailAddress(emailAddress);//emailaddress
            debug = "setting student id";
            try{p.setStudentId(checkUserDetail(studentId));}catch(Exception e){}//studentid - Catch Exception as it's not a mandatory field
            debug = "setting password";
            p.setPassword(SecurityUtil.getHashValue(userPwd));//password - The password in blackboard is irrelevant if you're using ldap
            debug = "setting gender";
            try//gender - Is this working?
            {
                p.setGender(Gender.fromExternalString(gender.trim().toUpperCase()));
            }
            catch(Exception e)
            {
                p.setGender(Gender.UNKNOWN);
            }
            debug = "setting birthdate";
            try//birthdate
            {
                p.setBirthDate(new GregorianCalendar(Integer.parseInt(birthYear),Integer.parseInt(birthMonth)-1,Integer.parseInt(birthDay)));
            }catch(Exception e){}
            debug = "setting education level";
            try//Education Level
            {
                p.setEducationLevel(EducationLevel.fromExternalString(eduLevel.trim().toUpperCase()));
            }
            catch(Exception e)
            {
                p.setEducationLevel(EducationLevel.UNKNOWN);
            }
            debug = "setting company";
            try{p.setCompany(checkUserDetail(company));}catch(Exception e){}//Company - Catch Exception as it's not a mandatory field
            debug = "setting job title";
            try{p.setJobTitle(checkUserDetail(jobTitle));}catch(Exception e){}//Job Title - Catch Exception as it's not a mandatory field
            debug = "setting department";
            try{p.setDepartment(checkUserDetail(department));}catch(Exception e){}//Department - Catch Exception as it's not a mandatory field
            debug = "setting street1";
            try{p.setStreet1(checkUserDetail(street1));}catch(Exception e){}//Street 1 - Catch Exception as it's not a mandatory field
            debug = "setting street2";
            try{p.setStreet2(checkUserDetail(street2));}catch(Exception e){}//Street 2 - Catch Exception as it's not a mandatory field
            debug = "setting city";
            try{p.setCity(checkUserDetail(city));}catch(Exception e){}//City - Catch Exception as it's not a mandatory field
            debug = "setting state or province";
            try{p.setState(checkUserDetail(stateOrProvince));}catch(Exception e){}//State / Province - Catch Exception as it's not a mandatory field
            debug = "setting zip or post code";
            try{p.setZipCode(checkUserDetail(zipOrPostCode));}catch(Exception e){}//Zip / Postal Code - Catch Exception as it's not a mandatory field
            debug = "setting country";
            try{p.setCountry(checkUserDetail(country));}catch(Exception e){}//Country - Catch Exception as it's not a mandatory field
            debug = "setting website";
            try{p.setWebPage(checkUserDetail(website));}catch(Exception e){}//Website - Catch Exception as it's not a mandatory field
            debug = "setting home phone";
            try{p.setHomePhone1(checkUserDetail(homePhone));}catch(Exception e){}//Home Phone - Catch Exception as it's not a mandatory field
            debug = "setting work phone";
            try{p.setHomePhone2(checkUserDetail(workPhone));}catch(Exception e){}//Work Phone - Catch Exception as it's not a mandatory field
            debug = "setting work fax";
            try{p.setHomeFax(checkUserDetail(workFax));}catch(Exception e){}//Work Fax - Catch Exception as it's not a mandatory field
            debug = "setting mobile phone";
            try{p.setMobilePhone(checkUserDetail(mobilePhone));}catch(Exception e){}//Mobile Phone - Catch Exception as it's not a mandatory field
            debug = "setting portal role";
            //Portal Role
            PortalRole pr = null;
            if(portalRole!=null && !portalRole.equalsIgnoreCase(""))
            {
                portalRole = portalRole.trim();
                pr = PortalRoleDbLoader.Default.getInstance().loadByRoleId(portalRole);

            }
            else
            {
                pr = PortalRoleDbLoader.Default.getInstance().loadByRoleId("STUDENT");
            }
            p.setPortalRole(pr);
            debug = "setting system role";
            //System Role
            if(systemRole!=null && !systemRole.equalsIgnoreCase(""))
            {
                p.setSystemRole(blackboard.data.user.User.SystemRole.fromFieldName(systemRole.trim().toUpperCase()));
            }
            else
            {
                p.setSystemRole(blackboard.data.user.User.SystemRole.NONE);
            }
            debug = "setting available";
            if(available!=null){p.setIsAvailable(available);}else{throw new Exception("Invalid availability");}//Available
            debug = "setting row status";
            p.setRowStatus(RowStatus.ENABLED);
            debug = "setting rec status";
            p.setRecStatus(RecStatus.ADD);
            //p.setReplacementBatchUid(userId);
            debug = "checking secondary portal roles, number specified="+secPortalRoles.length;
            for(int i=0;i<secPortalRoles.length;i++)
            {
                debug += ", "+secPortalRoles[i];
            }
            //parse valid secondary roles, assuming any roles are specified then they
            //must be valid or the user is not added. No specified roles, null or blank roles are ignored.
            secPRoles = parseSecondaryPortalRoles(secPortalRoles);
            debug = "exiting try to persist user, you really shouldn't see this!";
	    }
	    catch(Exception e)
	    {
            throw new WebServiceException(e.toString()+" (hint: code was in the process of... "+debug+")");
	    }

	    try
	    {
            PersonDbPersister.Default.getInstance().insert(p);
	    }
	    catch(ConstraintViolationException cve)
	    {
	        throw new WebServiceException("The user you're trying to add may already exist");
	    }
	    catch(Exception e)
	    {
	        throw new WebServiceException("Error while trying to add user: "+e.toString());
	    }

	    /******
	     * Following must be set AFTER user is created as you need User.Id
	     * in order to set their UserRoles
	     *****/

	    //Secondary Portal Roles
        if(secPRoles!=null && secPRoles.size()>0)
        {
            try
            {
                setOrModifySecondaryPortalRolesForGivenUserId(userId,secPRoles);
            }
            catch(Exception e)
            {
                throw new WebServiceException(e.toString());
            }
        }

        return true;
	    //return userId+" : "+batchUId+" : "+firstName+" : "+middleName+" : "+lastName+" : "+emailAddress+" : "+studentId+" : "+pwd+" : "+gender+" : "+birthYear+" : "+birthMonth+" : "+birthDay+" : "+eduLevel+" : "+company+" : "+jobTitle+" : "+department+" : "+street1+" : "+street2+" : "+city+" : "+stateOrProvince+" : "+zipOrPostCode+" : "+country+" : "+website+" : "+homePhone+" : "+workPhone+" : "+workFax+" : "+mobilePhone+" : "+portalRole+" : "+systemRole+" : "+available;
	}


	/*!! (vic) - new, came from deleteUserByUserBbId(), also deletes by name
    private boolean userDelete(BBUser user)

	++*/


	/*!! (vic) - added to load by bbId if userName == null (previously loaded by userName only (named "FromUserId")
    private BBUser userRead(BBUser user, BBUser.BBUserVerbosity verbosity) throws WebServiceException
    {
        String error = "";
        try
        {
            if(user.getUserName()!=null && !user.getUserName().equalsIgnoreCase(""))
            {
                return new BBUser(UserDbLoader.Default.getInstance().loadByUserName(user.getUserName()),verbosity);
            }
            else if(user.getBbId()!=null && !user.getBbId().equalsIgnoreCase(""))
            {
                return new BBUser(UserDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(User.DATA_TYPE, user.getBbId())),verbosity);
            }
            error = "You must specify either userId or userBBId";
        }

	++*/
	private UserDetails getUserDetailsFromUserId(String userId, UserDetails.Verbosity verbosity) throws Exception
	{
	    return new UserDetails(UserDbLoader.Default.getInstance().loadByUserName(userId),verbosity);
	}


	/*!! (vic) - added verbosity parameter
        catch(KeyNotFoundException knfe)
        {
            error = "No matching user";
        }
        catch(Exception e)
        {
            error = "Error whilst finding user: "+e.toString();
        }
        throw new WebServiceException(error);
    }

    private List<BBUser> userReadAll(BBUser.BBUserVerbosity verbosity)
	++*/
	private List<UserDetails> getAllUserObjsNoDetails() throws WebServiceException
	{
	    List<UserDetails> al = new ArrayList<UserDetails>();
	    Person p = new Person();
	    p.setBatchUid("%%");

	    try
	    {
            List userList = PersonDbLoader.Default.getInstance().load(p);
            Iterator i = userList.iterator();
            while(i.hasNext())
            {
                al.add(new UserDetails((User)i.next(),UserDetails.Verbosity.minimal));
            }
            return al;
        }
        catch (KeyNotFoundException knfe)
        {
            //return new UserDetailsStandard(new Exception("No users found: "+e.getMessage()));
            throw new WebServiceException("No users found: "+knfe.getMessage());
	    }
	    catch(Exception e)
	    {
		throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}


	/*!! (vic) - added role parameter, verbosity parameter
    private List<BBUser> userReadSearchByCourseIdAndCMRole(BBCourse course, BBCourseMembershipRole cmRole, BBUser.BBUserVerbosity verbosity) throws WebServiceException
    {
        List<BBUser> rl = new ArrayList<BBUser>();
        try
        {
            //This may require heavy loading instead of lightweight
            List<CourseMembership> cml = CourseMembershipDbLoader.Default.getInstance().loadByCourseIdAndRole(CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseId()).getId(),CourseMembership.Role.fromExternalString(cmRole.name()),null,true);
	    if(cml.size()>0)
	    {
		Iterator<CourseMembership> i = cml.iterator();
	++*/
	private List<UserDetails> getEnrolledStudentsForGivenCourseIDWithNamedElements(String courseId) throws Exception
	{
	    List<UserDetails> rl = new ArrayList<UserDetails>();
	    //This may require heavy loading instead of lightweight
	    List<CourseMembership> cml = CourseMembershipDbLoader.Default.getInstance().loadByCourseIdAndRole(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId(),CourseMembership.Role.STUDENT);

	    if(cml.size()>0)
	    {
		Iterator i = cml.iterator();
		while(i.hasNext())
		{
		    //getuserid and load userdb for each user? less memory as only one user.
		    //loaded at a time, but do you need heavyweight load to get userid??
		    //rl.add(new UserDetailsStandard(((CourseMembership)i.next()).getUser(),true));
		    rl.add(new UserDetails(UserDbLoader.Default.getInstance().loadById(((CourseMembership)i.next()).getUserId()),UserDetails.Verbosity.minimal));
		}
		return rl;
	    }
	    throw new Exception("No students found");
	}
	



	/*++ (vic) - added from v2 for readable diff matching
		while(i.hasNext())
		{
		    rl.add(new BBUser(i.next().getUser(),verbosity));
		}
		return rl;
	    }
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
        }
        return rl;
    }
    private List<BBUser> userReadSearchByCourseId(BBCourse course, BBUser.BBUserVerbosity verbosity) throws WebServiceException
	++*/
	private List<UserDetails> getEnrolledUserObjsForGivenCourse(String courseId, UserDetails.Verbosity verbosity) throws Exception
	{
	    List<UserDetails> al = new ArrayList();
	    List<CourseMembership> membershipList = getCourseMembershipFromCourseId(courseId);
	    Iterator i = membershipList.iterator();
	    while(i.hasNext())
	    {
            al.add(new UserDetails(((CourseMembership)i.next()).getUser(),verbosity));
	    }
	    return al;
	}
    

    @WebMethod
	public String deleteAnnouncementXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "announcementBbId") String announcementBbId)
	{
        authoriseMethodUse(pwd,"deleteAnnouncementXML");
        try
	    {
            Default.getInstance().deleteById(AnnouncementDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Announcement.DATA_TYPE,announcementBbId)).getId());
	    }
	    catch(Exception e)
	    {
            throw new WebServiceException("Error: Please provide a valid dbid "+e.toString()+" "+e.getMessage());
	    }
        return toXML(null, true);
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getAllAnnouncementsForGivenCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllAnnouncementsForGivenCourseId");
        headerDesc = handleNullValue(headerDesc);
        try
        {
            List<String[]> rl = new ArrayList<String[]>();
            List<AnnouncementDetails> adl = getAllAnnouncementsForGivenCourseId(courseId);
            if(headerDesc)
            {
                rl.add(new AnnouncementDetails().toStringArrayHeader());
            }
            Iterator i = adl.iterator();
            while(i.hasNext())
            {
                rl.add(((AnnouncementDetails)i.next()).toStringArray());
            }
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: "+e.toString()+" "+e.getMessage());
        }
    }

    @WebMethod
	public String getAllAnnouncementsForGivenCourseIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
	{
        authoriseMethodUse(pwd,"getAllAnnouncementsForGivenCourseIdXML");
        try
	    {
            return toXML("Announcements",getAllAnnouncementsForGivenCourseId(courseId));
	    }
        catch(Exception e)
        {
            throw new WebServiceException("Error: "+e.toString()+" "+e.getMessage());
        }
	}

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getAllAvailableAnnouncementsForGivenUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "headerDesc") Boolean headerDesc)  throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllAvailableAnnouncementsForGivenUserId");
        headerDesc = handleNullValue(headerDesc);
        try
        {
            List<String[]> rl = new ArrayList<String[]>();
            List<AnnouncementDetails> adl = getAllAnnouncementsForGivenUserId(userId);
            if(headerDesc)
            {
            rl.add(new AnnouncementDetails().toStringArrayHeader());
            }
            Iterator i = adl.iterator();
            while(i.hasNext())
            {
            rl.add(((AnnouncementDetails)i.next()).toStringArray());
            }
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: "+e.toString()+" "+e.getMessage());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getAllAvailableAnnouncementsForGivenUserIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId)  throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllAvailableAnnouncementsForGivenUserIdXML");
        try
        {
            return toXML("Announcements",getAllAnnouncementsForGivenUserId(userId));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: "+e.toString()+" "+e.getMessage());
        }
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getAllSystemAnnouncements(@WebParam(name = "pwd") String pwd, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllSystemAnnouncements");
        headerDesc = handleNullValue(headerDesc);
        try
        {
            List<String[]> rl = new ArrayList<String[]>();
            List<AnnouncementDetails> adl = getAllAnnouncementsForGivenCourseId("SYSTEM");
            if(headerDesc)
            {
                rl.add(new AnnouncementDetails().toStringArrayHeader());
            }
            Iterator i = adl.iterator();
            while(i.hasNext())
            {
                rl.add(((AnnouncementDetails)i.next()).toStringArray());
            }
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: "+e.toString()+" "+e.getMessage());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public List<AnnouncementDetails> getAllSystemAnnouncementsWithNamedElements(@WebParam(name = "pwd") String pwd) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllSystemAnnouncementsWithNamedElements");
        try
        {
            return getAllAnnouncementsForGivenCourseId("SYSTEM");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: "+e.toString()+" "+e.getMessage());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getAllSystemAnnouncementsXML(@WebParam(name = "pwd") String pwd) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllSystemAnnouncementsXML");
        try
        {
            return toXML("Announcements",getAllAnnouncementsForGivenCourseId("SYSTEM"));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: "+e.toString()+" "+e.getMessage());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String modifyAnnouncementXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "announcementBbId") String announcementBbId,
                                        @WebParam(name = "textType") String textType,
                                        @WebParam(name = "courseId") String courseId,
                                        @WebParam(name = "title") String title,
                                        @WebParam(name = "body") String body,
                                        @WebParam(name = "startDay") int startDay, @WebParam(name = "startMonth") int startMonth,
                                        @WebParam(name = "startYear") int startYear, @WebParam(name = "endDay") int endDay,
                                        @WebParam(name = "endMonth") int endMonth, @WebParam(name = "endYear") int endYear) throws WebServiceException
    {
        authoriseMethodUse(pwd,"modifyAnnouncementXML");
        return toXML(null,modifyAnnouncement(announcementBbId, textType, courseId, title,
                                        body, startDay, startMonth,
                                        startYear, endDay, endMonth, endYear));
    }
    /**
     * Web service operation
     */
    @WebMethod
    public String postCourseAnnouncementXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "textType") String textType,
				    @WebParam(name = "courseID") String courseID,
				    @WebParam(name = "permanent") Boolean permanent,
				    @WebParam(name = "title") String title,
				    @WebParam(name = "body") String body,
				    @WebParam(name = "startDay") int startDay, @WebParam(name = "startMonth") int startMonth,
				    @WebParam(name = "startYear") int startYear, @WebParam(name = "endDay") int endDay,
				    @WebParam(name = "endMonth") int endMonth, @WebParam(name = "endYear") int endYear) throws WebServiceException
    {
        authoriseMethodUse(pwd,"postCourseAnnouncementXML");
        try
        {
            return toXML(null,postAnnouncement(null,textType,courseID,permanent,"COURSE",title,body,startDay,startMonth,startYear,endDay,endMonth,endYear));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error (pCAXML): "+e.toString()+" "+e.getMessage());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean postSystemAnnouncement(@WebParam(name = "pwd") String pwd, @WebParam(name = "textType") String textType,
				    @WebParam(name = "permanent") Boolean permanent,
				    @WebParam(name = "title") String title,
				    @WebParam(name = "body") String body,
				    @WebParam(name = "startDay") int startDay, @WebParam(name = "startMonth") int startMonth,
				    @WebParam(name = "startYear") int startYear, @WebParam(name = "endDay") int endDay,
				    @WebParam(name = "endMonth") int endMonth, @WebParam(name = "endYear") int endYear) throws WebServiceException
    {
        authoriseMethodUse(pwd,"postSystemAnnouncement");
        try
        {
            return postAnnouncement(null,textType,null,permanent,"SYSTEM",title,body,startDay,startMonth,startYear,endDay,endMonth,endYear);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error (pSA): "+e.toString()+" "+e.getMessage());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String postSystemAnnouncementXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "textType") String textType,
				    @WebParam(name = "permanent") Boolean permanent,
				    @WebParam(name = "title") String title,
				    @WebParam(name = "body") String body,
				    @WebParam(name = "startDay") int startDay, @WebParam(name = "startMonth") int startMonth,
				    @WebParam(name = "startYear") int startYear, @WebParam(name = "endDay") int endDay,
				    @WebParam(name = "endMonth") int endMonth, @WebParam(name = "endYear") int endYear) throws WebServiceException
    {
        authoriseMethodUse(pwd,"postSystemAnnouncementXML");
        try
        {
            return toXML(null,postAnnouncement(null,textType,null,permanent,"SYSTEM",title,body,startDay,startMonth,startYear,endDay,endMonth,endYear));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error (pSAXML): "+e.toString()+" "+e.getMessage());
        }
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[] getAllCourseIDsWhereCourseIdContainsGivenSearchString(@WebParam(name = "pwd") String pwd, @WebParam(name = "searchString") String searchString) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllCourseIDsWhereCourseIdContainsGivenSearchString");
        try
        {
            List<CourseDetails> rl = getAllCourseIDObjsWhereCourseIdContainsGivenSearchString(searchString);
            List<String> sl = new ArrayList<String>();
            Iterator i = rl.iterator();
            while(i.hasNext())
            {
                sl.add(((CourseDetails)i.next()).getCourseId());
            }
            return (String[])sl.toArray(new String[sl.size()]);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while matching courses: "+e.toString());
        }
    }

    @WebMethod
    public String addCourseXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "batchUID") String batchUID,
			    @WebParam(name = "title") String title, @WebParam(name = "courseDescription") String courseDescription,
			    @WebParam(name = "available") Boolean available, @WebParam(name = "allowGuests") Boolean allowGuests,
			    @WebParam(name = "allowObservers") Boolean allowObservers) throws WebServiceException
    {
        authoriseMethodUse(pwd,"addCourseXML");
        return toXML(null,addCourse(courseId, batchUID, title, courseDescription, available, allowGuests, allowObservers));
    }

    private String checkCourseDetail(String courseDetail) throws Exception
    {
	if(courseDetail!=null && !courseDetail.equalsIgnoreCase(""))
	{
	    return courseDetail.trim();
	}
	throw new Exception("Invalid course detail: '"+courseDetail+"'");
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String deleteCourseByCourseBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseBbId") String courseBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteCourseByCourseBbIdXML");
        return toXML(null,deleteCourseByCourseBbId(courseBbId));
    }

    private Boolean deleteCourseByCourseId(String courseId) throws WebServiceException
    {
        try
        {
            CourseDbPersister.Default.getInstance().deleteById(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId());
        }
        catch(Exception e)
        {
            throw new WebServiceException("Please provide a valid courseId: "+e.toString());
        }
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean deleteCourseByCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteCourseByCourseId");
        return deleteCourseByCourseId(courseId);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String deleteCourseByCourseIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteCourseByCourseIdXML");
        return toXML(null,deleteCourseByCourseId(courseId));
    }

    private Boolean doesCourseExist(String courseId)
    {
        try
        {
            CourseDbLoader.Default.getInstance().loadByCourseId(courseId);
        }
        catch(KeyNotFoundException knfe)
        {
            return false;
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error whilst searching to see if course exists: "+e.toString());
        }
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean doesCourseExist(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"doesCourseExist");
        return doesCourseExist(courseId);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String doesCourseExistXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"doesCourseExistXML");
        return toXML(null,doesCourseExist(courseId));
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[] getAllCourseIDsNoDetails(@WebParam(name = "pwd") String pwd) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllCourseIDsNoDetails");
        List<String> rl = new ArrayList<String>();
        try
        {
            List<CourseDetails> cl = getAllCourseObjsNoDetails();
            Iterator i = cl.iterator();
            while(i.hasNext())
            {
                rl.add(((CourseDetails)i.next()).getCourseId());
            }
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while getting courses: "+e.toString());
        }
        return (String[])rl.toArray(new String[rl.size()]);
    }
    

    /**
     * Web service operation
     */
    @WebMethod
    public String getAllCourseIDsNoDetailsXML(@WebParam(name = "pwd") String pwd) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllCourseIDsNoDetailsXML");
        try
        {
            return toXML("Courses",getAllCourseObjsNoDetails());
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while getting courses: "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getAllCourseIDsWhereCourseIdContainsGivenSearchStringXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "searchString") String searchString) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllCourseIDsWhereCourseIdContainsGivenSearchStringXML");
        try
        {
            return toXML("Courses",getAllCourseIDObjsWhereCourseIdContainsGivenSearchString(searchString));

        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while matching courses: "+e.toString());
        }
    }

    @WebMethod
    public String getAllCourseIDsWhereGivenUserIdIsAnInstructorXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllCourseIDsWhereGivenUserIdIsAnInstructorXML");
        try
        {
            return toXML("Courses",getAllCourseIDsWhereGivenUserIdIsAnInstructor(userId));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while retrieving courses: "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getCourseDetails(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "extendedDetails") Boolean extendedDetails, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getCourseDetails");
        headerDesc = handleNullValue(headerDesc);
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            List<String[]> rl = new ArrayList<String[]>();
            if(headerDesc)
            {
                rl.add(new CourseDetails(extendedDetails?CourseDetails.Verbosity.extended:CourseDetails.Verbosity.standard).toStringArrayHeader());
            }
            rl.add(getCourseDetailsObjFromCourseId(courseId,extendedDetails).toStringArray());
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(KeyNotFoundException knfe)
        {
            throw new WebServiceException("courseId '"+courseId+"' not found or does not exist ("+knfe.getMessage()+")");
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.toString());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getCourseDetailsXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getCourseDetailsXML");
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            return toXML("Courses",getCourseDetailsObjFromCourseId(courseId,extendedDetails));
        }
        catch(KeyNotFoundException knfe)
        {
            throw new WebServiceException("courseId '"+courseId+"' not found or does not exist ("+knfe.getMessage()+")");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Could not retrieve course with that Id");
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getCourseMembershipBbIdForGivenUserIdAndCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getCourseMembershipBbIdForGivenUserIdAndCourseId");
        return getCourseMembershipBbIdForGivenUserIdAndCourseId(userId,courseId).getCourseMembershipBbId();
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getCourseMembershipBbIdForGivenUserIdAndCourseIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getCourseMembershipBbIdForGivenUserIdAndCourseIdXML");
        return toXML("CourseMemberships",getCourseMembershipBbIdForGivenUserIdAndCourseId(userId,courseId));
    }


    /**
     * Web service operation
     */
    @WebMethod
    public String getCourseMembershipDetailsForGivenCourseMembershipBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembershipBbId") String courseMembershipBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getCourseMembershipDetailsForGivenCourseMembershipBbIdXML");
        return toXML("CourseMemberships",getCourseMembershipDetailsForGivenCourseMembershipBbId(courseMembershipBbId));
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getCourseQuotaDetails(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getCourseQuotaDetails");
        headerDesc = handleNullValue(headerDesc);
        try
        {
            ArrayList<String[]> cqdAL = new ArrayList<String[]>();
            CourseQuotaDetails cqd = getCourseQuotaDetails(courseId);
            if(headerDesc)
            {
                cqdAL.add(cqd.toStringArrayHeader());
            }
            cqdAL.add(cqd.toStringArray());
            return cqdAL.toArray(new String[1][]);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not find course with that Id "+e.getMessage()+" "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getCourseQuotaDetailsXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getCourseQuotaDetailsXML");
        return toXML("CourseQuotas",getCourseQuotaDetails(courseId));
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getEnrolledCoursesForGivenUser(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
        /*Figure out if you can distinguish courses from organisations (communities)
         * - think you can, isn't there a service type enumerator somewhere?
         * - Yes.... Course.ServiceLevel
         */
        authoriseMethodUse(pwd,"getEnrolledCoursesForGivenUser");
        headerDesc = handleNullValue(headerDesc);
        try
        {
            List<String[]> rl = new ArrayList<String[]>();
            List<EnrollmentDetails> membershipList = getEnrollmentDetailsObjsForGivenUser(userId);
            Iterator i = membershipList.iterator();
            if(headerDesc)
            {
                rl.add(new EnrollmentDetails().toStringArrayHeader());
            }
            while(i.hasNext())
            {
                rl.add(((EnrollmentDetails)i.next()).toStringArray());
            }
            return rl.toArray(new String[rl.size()][]);
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getEnrolledCoursesForGivenUserXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
    {
        /*Figure out if you can distinguish courses from organisations (communities)
         * - think you can, isn't there a service type enumerator somewhere?
         * - Yes.... Course.ServiceLevel
         */
        authoriseMethodUse(pwd,"getEnrolledCoursesForGivenUserXML");
        try
        {
            return toXML("Enrollments",getEnrollmentDetailsObjsForGivenUser(userId));
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
        }
    }

    private Boolean handleNullValue(Boolean value)
    {
        if (value == null)
        {
            return false;
        }
        return value;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String modifyCourseQuotaDetailsXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseID") String courseID, @WebParam(name = "overrideDiskQuota") Boolean overrideDiskQuota, @WebParam(name = "overrideUploadLimit") Boolean overrideUploadLimit, @WebParam(name = "absoluteLimit") Long absoluteLimit, @WebParam(name = "softLimit") Long softLimit, @WebParam(name = "uploadLimit") Long uploadLimit) throws WebServiceException
    {
        authoriseMethodUse(pwd,"modifyCourseQuotaDetailsXML");
        return toXML(null,modifyCourseQuotaDetails(courseID, overrideDiskQuota, overrideUploadLimit, absoluteLimit, softLimit, uploadLimit));
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String unEnrollGivenUserFromGivenCourseXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"unenrollGivenUserFromGivenCourseXML");
        return toXML(null,unEnrollGivenUserFromGivenCourse(userId,courseId));
    }

@WebService()
public class BBGradebookWebService
{
    private WebServiceProperties wsp = new WebServiceProperties("amnl","BBGradebookWebService");

    /**
     * Web service operation
     */
    @WebMethod
    public String addLineitemToGivenCourseIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "Name") String name,
						@WebParam(name = "type") String type, @WebParam(name = "pointsPossible") Float pointsPossible,
						@WebParam(name = "weight") Float weight, @WebParam(name = "available") Boolean available) throws WebServiceException
    {
        authoriseMethodUse(pwd,"addLineitemToGivenCourseIdXML");
        return toXML(null,addLineitemToGivenCourseId(courseId, name, type, pointsPossible, weight, available));
    }


    /**
     * Web service operation
     */
    @WebMethod
    public String deleteGivenAttemptByAttemptBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "attemptBbId") String attemptBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteGivenAttemptByAttemptBbIdXML");
        return toXML(null,deleteGivenAttemptByAttemptBbId(attemptBbId));
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String deleteLineItemByLineItemBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitemBbId") String lineItemBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteLineItemByLineItemBbIdXML");
        return toXML(null,deleteOutcomeDefinitionByOutcomeDefBbId(lineItemBbId));
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String deleteOutcomeDefinitionByOutcomeDefBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteOutcomeDefinitionByOutcomeDefBbIdXML");
        return toXML(null,deleteOutcomeDefinitionByOutcomeDefBbId(outcomeDefBbId));
    }


    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getAllLineItemsForCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "headerDesc") Boolean headerDesc)
    {
        authoriseMethodUse(pwd,"getAllLineItemsForCourseId");
        headerDesc = handleNullValue(headerDesc);
        try
        {
            List<String[]> rl = new ArrayList<String[]>();
            List<LineitemDetails> lil = getAllLineitemObjsForCourseId(courseId);
            if(headerDesc)
            {
            rl.add(new LineitemDetails().toStringArrayHeader());
            }
            Iterator i = lil.iterator();
            while(i.hasNext())
            {
            rl.add(((LineitemDetails)i.next()).toStringArray());
            }
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve line items for this course "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getAllLineItemsForCourseIdXML")
    public String getAllLineItemsForCourseIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId)
    {
        authoriseMethodUse(pwd,"getAllLineItemsForCourseIdXML");
        try
        {
            return toXML("LineItems",getAllLineitemObjsForCourseId(courseId));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve line items for this course "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getAllOutcomeDefinitionsForGivenCourse(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllOutcomeDefinitionsForGivenCourse");
        headerDesc = handleNullValue(headerDesc);
        try
        {
            List<OutcomeDefinitionDetails> odl = getAllOutcomeDefinitionsForGivenCourse(courseId);
            List<String[]> rl = new ArrayList<String[]>();
            if(headerDesc)
            {
            rl.add(new OutcomeDefinitionDetails().toStringArrayHeader());
            }
            Iterator i = odl.iterator();
            while(i.hasNext())
            {
            rl.add(((OutcomeDefinitionDetails)i.next()).toStringArray());
            }
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while getting outcome definitions for course '"+courseId+"': "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getAllOutcomeDefinitionsForGivenCourseXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllOutcomeDefinitionsForGivenCourseXML");
        try
        {
            return toXML("OutcomeDefinitions",getAllOutcomeDefinitionsForGivenCourse(courseId));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while getting outcome definitions for course '"+courseId+"': "+e.toString());
        }
    }

    @WebMethod
    public String getAllOutcomesForGivenOutcomeDefBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllOutcomesForGivenOutcomeDefBbIdXML");
        try
        {
            return toXML("Outcomes",getAllOutcomesForGivenOutcomeDefBbId(outcomeDefBbId));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while getting outcomes: "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getAllScoreDetailsForGivenLineItemBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name="extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllScoreDetailsForGivenLineItemBbIdXML");
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            return toXML("Scores",getAllScoreObjsForGivenLineItemBbId(lineItemBbId, extendedDetails));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve scores for this line item "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getAllScoresForGivenLineItemBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name="extendedDetails") Boolean extendedDetails, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllScoresForGivenLineItemBbId");
        headerDesc = handleNullValue(headerDesc);
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            List<ScoreDetails> sdsl = getAllScoreObjsForGivenLineItemBbId(lineItemBbId, extendedDetails);
            List<String[]> rl = new ArrayList<String[]>();
            if(headerDesc)
            {
                rl.add(new ScoreDetails(extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard).toStringArrayHeader());
            }
            Iterator i = sdsl.iterator();
            while(i.hasNext())
            {
                rl.add(((ScoreDetails)i.next()).toStringArray());
            }
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve scores for this line item "+e.toString());
        }
    }


    /**
     * Web service operation
     */
    @WebMethod
    public String getAttemptDetailsByGivenOutcomeDefBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAttemptDetailsByGivenOutcomeDefBbIdXML");
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            return toXML("Attempts",getAttemptDetailsObjsForGivenOutcomeDefBbId(outcomeDefBbId, extendedDetails));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod 
    public String[][] getGradebookSettingsForGivenCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGradebookSettingsForGivenCourseID");
        headerDesc = handleNullValue(headerDesc);
        try
        {
            List<String[]> rl = new ArrayList<String[]>();
            if(headerDesc)
            {
            rl.add(new GradeBookSettingsDetails().toStringArrayHeader());
            }
            rl.add(getGradeBookSettingDetailsObjForGivenCourseId(courseId).toStringArray());
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve gradebook settings for this course"+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getGradebookSettingsForGivenCourseIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGradebookSettingsForGivenCourseIDXML");
        try
        {
            return toXML("GradeBookSetting",getGradeBookSettingDetailsObjForGivenCourseId(courseId));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve gradebook settings for this course"+e.toString());
        }
    }

    @WebMethod
    public String getLineitemDetailsForGivenLineitemBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitemBbId") String lineitemBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getLineitemDetailsForGivenLineitemBbIdXML");
        return toXML("LineItems",getLineitemDetailsForGivenLineitemBbId(lineitemBbId));
    }

    @WebMethod
    public String getOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdWithNamedElements");
        return toXML("OutcomeDefinitions",getOutcomeDefinitionDetailsFromOutcomeDefinitionBbId(outcomeDefBbId));
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getScoreDetailsForGivenScoreBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "scoreBbId") String scoreBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreForGivenCourseMembershipBbIdAndLineItemBbIdXML");
        return toXML("ScoreDetails",getScoreDetailsForGivenScoreBbId(scoreBbId, extendedDetails));
    }

    @WebMethod
    public String getScoreDetailsForGivenUserIdAndLineItemBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreDetailsForGivenUserIdAndLineItemBbIdXML");
        return toXML("Scores",getScoreDetailsForGivenUserIdAndLineItemBbId(userId, lineItemBbId, extendedDetails));
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembershipBbId") String courseMembershipBbId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdXML");
        return toXML("Scores",getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbId(courseMembershipBbId, lineItemBbId, extendedDetails));
    }

    private ScoreDetails getScoreDetailsObjForGivenScoreObj(Score s, Boolean extendedDetails) throws Exception
    {
        return new ScoreDetails(s,extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard);
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getScoreForGivenCourseMembershipBbIdAndLineItemBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembershipBbId") String courseMembershipBbId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails, @WebParam(name="headerDesc") Boolean headerDesc) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreForGivenCourseMembershipBbIdAndLineItemBbId");
        headerDesc = handleNullValue(headerDesc);
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            List<String[]> rl = new ArrayList<String[]>();
            if(headerDesc)
            {
                rl.add(new ScoreDetails(extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard).toStringArrayHeader());
            }
            rl.add(getScoreDetailsObjForGivenScoreObj(getScoreObjForGivenCourseMembershipBbIdAndLineItemBbId(courseMembershipBbId, lineItemBbId),extendedDetails).toStringArray());
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(KeyNotFoundException e)
        {
            throw new WebServiceException("Error: No score found, has user taken test and has it been marked? Is user on course? Does lineitem exist?");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: While retrieving score... "+e.toString());
        }
    }

    @Deprecated
    @WebMethod
    public String[][] getScoreForGivenUserIdAndLineItemBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails, @WebParam(name="headerDesc") Boolean headerDesc) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreForGivenUserIdAndLineItemBbId");
        headerDesc = handleNullValue(headerDesc);
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            List<String[]> rl = new ArrayList<String[]>();
            if(headerDesc)
            {
            rl.add(new ScoreDetails(extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard).toStringArrayHeader());
            }
            rl.add(
                getScoreDetailsObjForGivenScoreObj
                (
                getScoreObjForGivenCourseMembershipBbIdAndLineItemBbId
                (
                    CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId
                    (
                        ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId)).getCourseId(),
                        UserDbLoader.Default.getInstance().loadByUserName(userId).getId()
                    ).getId().toExternalString(),
                    lineItemBbId
                ),
                extendedDetails
                ).toStringArray()
            );
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(KeyNotFoundException e)
        {
            throw new WebServiceException("Error: No score found, has user taken test and has it been marked? Is user on course? Does lineitem exist?");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: While retrieving score... "+e.toString());
        }
    }

    @WebMethod
    public String getOutcomeDetailsFromOutcomeBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeBbId") String outcomeBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getOutcomeDetailsFromOutcomeBbIdXML");
        return toXML("Outcomes",getOutcomeDetailsFromOutcomeBbId(outcomeBbId));
    }

    private Boolean handleNullValue(Boolean value)
    {
        if (value == null)
        {
            return false;
        }
        return value;
    }

    private Boolean setLineItemWeightByLineItemBbId(String lineItemBbId, String weight) throws WebServiceException
    {
        try
        {
            Lineitem li = ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId));
            li.setWeight(Float.parseFloat(weight));
            ((LineitemDbPersister)BbServiceManager.getPersistenceService().getDbPersistenceManager().getPersister(LineitemDbPersister.TYPE)).persist(li);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: While setting or persisting weight for lineitem "+e.toString());
        }
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean setLineItemWeightByLineItemBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "weight") String weight) throws WebServiceException
    {
        authoriseMethodUse(pwd,"setLineItemWeightByLineItemBbId");
        return setLineItemWeightByLineItemBbId(lineItemBbId, weight);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String setLineItemWeightByLineItemBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "weight") String weight) throws WebServiceException
    {
        authoriseMethodUse(pwd,"setLineItemWeightByLineItemBbId");
        return toXML(null,setLineItemWeightByLineItemBbId(lineItemBbId, weight));
    }

    private Boolean setWeightByItemOrCategoryForGradebookInGivenCourseId(String courseId, String itemOrCategory) throws WebServiceException
    {
        try
        {
            if(itemOrCategory!=null && itemOrCategory.length()>0)
            {
            itemOrCategory = itemOrCategory.toLowerCase().trim();
            String firstChar = itemOrCategory.substring(0,1).toUpperCase();
            itemOrCategory = firstChar+itemOrCategory.substring(1);
            }
            else
            {
            throw new Exception("Invalid item or category");
            }
            GradeBookSettings gbs = GradeBookSettingsDbLoader.Default.getInstance().loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId());
            gbs.setWeightType(GradeBookSettings.WeightType.fromFieldName(itemOrCategory));
            GradeBookSettingsDbPersister.Default.getInstance().persist(gbs);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: While setting or persisting weightings type for gradebook "+e.toString());
        }
        return true;
    }

    /**
     * Web service operation
     *
     * itemOrCategory = ITEM / CATEGORY
     *
     */
    @WebMethod
    public Boolean setWeightByItemOrCategoryForGradebookInGivenCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "itemOrCategory") String itemOrCategory) throws WebServiceException
    {
        authoriseMethodUse(pwd,"setWeightByItemOrCategoryForGradebookInGivenCourseId");
        return setWeightByItemOrCategoryForGradebookInGivenCourseId(courseId, itemOrCategory);
    }

    /**
     * Web service operation
     *
     * itemOrCategory = ITEM / CATEGORY
     *
     */
    @WebMethod
    public String setWeightByItemOrCategoryForGradebookInGivenCourseIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "itemOrCategory") String itemOrCategory) throws WebServiceException
    {
        authoriseMethodUse(pwd,"setWeightByItemOrCategoryForGradebookInGivenCourseId");
        return toXML(null,setWeightByItemOrCategoryForGradebookInGivenCourseId(courseId, itemOrCategory));
    }

@WebService()
public class BBGroupWebService
{
    private WebServiceProperties wsp = new WebServiceProperties("amnl","BBGroupWebService");

    /**
     * Web service operation
     */
    @WebMethod
    public String addGivenUserToGivenGroupXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "groupBbId") String groupId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"addGivenUserToGivenGroupXML");
        return toXML(null,addGivenUserToGivenGroup(userId,groupId));
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String addGroupToGivenCourseXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "title") String title,
					@WebParam(name = "description") String description, @WebParam(name = "descType") String descType,
					@WebParam(name = "available") Boolean available, @WebParam(name = "chatRoomAvailable") Boolean chatRoomAvailable,
					@WebParam(name = "discussionBoardAvailable") Boolean discussionBoardAvailable, @WebParam(name = "emailAvailable") Boolean emailAvailable,
					@WebParam(name = "transferAvailable") Boolean transferAvailable) throws WebServiceException
    {
        authoriseMethodUse(pwd,"addGroupToGivenCourseXML");
        return toXML(null,addGroupToGivenCourse(courseId, title, description, descType,
                                        available, chatRoomAvailable, discussionBoardAvailable,
                                        emailAvailable, transferAvailable));
    }


    /**
     * Web service operation
     */
    @WebMethod
    public String deleteGivenUserFromGivenGroupXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "groupBbId") String groupId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteGivenUserFromGivenGroupXML");
        return toXML(null,deleteGivenUserFromGivenGroup(userId,groupId));
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String deleteGroupXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteGroupXML");
        return toXML(null,deleteGroup(groupId));
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getGroupDetailsForGivenCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGroupDetailsForGivenCourseId");
        headerDesc = handleNullValue(headerDesc);
        List<String[]> rl = new ArrayList<String[]>();
        List<GroupDetails> gdl = getGroupDetailsForGivenCourseId(courseId);
        try
        {
            if(headerDesc)
            {
            rl.add(new GroupDetails().toStringArrayHeader());
            }
            Iterator i = gdl.iterator();
            while(i.hasNext())
            {
            rl.add(((GroupDetails)i.next()).toStringArray());
            }
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getGroupDetailsForGivenCourseIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGroupDetailsForGivenCourseIdXML");
        return toXML("Groups",getGroupDetailsForGivenCourseId(courseId));
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getGroupDetailsForGivenGroupBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupBbId, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGroupDetailsForGivenGroupBbId");
        headerDesc = handleNullValue(headerDesc);
        try
        {
            List<String[]> rl = new ArrayList<String[]>();
            if(headerDesc)
            {
                rl.add(new GroupDetails().toStringArrayHeader());
            }
            rl.add(new GroupDetails(GroupDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,groupBbId))).toStringArray());
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(KeyNotFoundException knfe)
        {
            throw new WebServiceException("Error: The given group does not exist");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve group for given Id "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getGroupDetailsForGivenGroupBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGroupDetailsForGivenGroupBbIdXML");
        return toXML("Groups",getGroupDetailsForGivenGroupBbId(groupBbId));
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[] getGroupMembersForGivenGroupBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGroupMembersForGivenGroupBbId");
        List<String> rl = new ArrayList<String>();
        List<GroupMemberDetails> gmdl = getGroupMembersForGivenGroupBbId(groupBbId);
        try
        {
            Iterator i = gmdl.iterator();
            while(i.hasNext())
            {
                rl.add(((GroupMemberDetails)i.next()).getUserId());
            }
            return (String[])rl.toArray(new String[rl.size()]);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getGroupMemberDetailsForGivenGroupBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGroupMemberDetailsForGivenGroupBbIdXML");
        return toXML("GroupMembers",getGroupMembersForGivenGroupBbId(groupBbId));
    }

    private Boolean handleNullValue(Boolean value)
    {
        if (value == null)
        {
            return false;
        }
        return value;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String modifyGroupXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupBbId,/* @WebParam(name = "courseId") String courseId,*/
				@WebParam(name = "title") String title, @WebParam(name = "description") String description,
				@WebParam(name = "descType") String descType, @WebParam(name = "available") Boolean available,
				@WebParam(name = "chatRoomAvailable") Boolean chatRoomAvailable, @WebParam(name = "discussionBoardAvailable") Boolean discussionBoardAvailable,
				@WebParam(name = "emailAvailable") Boolean emailAvailable, @WebParam(name = "transferAvailable") Boolean transferAvailable) throws WebServiceException
    {
        authoriseMethodUse(pwd,"modifyGroupXML");
        return toXML(null,modifyGroup(groupBbId, title, description, descType, available, chatRoomAvailable,
                                        discussionBoardAvailable, emailAvailable, transferAvailable));
    }

@WebService(name="BBUserWebService", serviceName="BBUserWebService", targetNamespace="http://www.ncl.ac.uk/BBUserWebService")
public class BBUserWebService
{
	private WebServiceProperties wsp = new WebServiceProperties("amnl","BBUserWebService");
	//private enum Verbosity{minimal,standard,extended}


	/**
	 *
	 * Web service operation
	 */
	@WebMethod
	public Boolean addDefaultStudent(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "firstName") String firstName,
					@WebParam(name = "middleName") String middleName, @WebParam(name = "lastName") String lastName,
					@WebParam(name = "emailAddress") String emailAddress, @WebParam(name = "studentId") String studentId,  @WebParam(name = "studentPassword") String studentPassword) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"addDefaultStudent");
	    //return addUser(userId,userId,firstName,middleName,lastName,emailAddress,studentId,"11111111",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"STUDENT","NONE",true);
	    return addAUser(pwd,userId,userId,firstName,middleName,lastName,emailAddress,studentId,studentPassword,
			    "","","","","","","","","","","","","","","","","","","","",
			    new String[]{},"",/*new String[]{""},*/true);
	}

	/**
	 *
	 * Web service operation
	 */
	@WebMethod
	public String addDefaultStudentXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "firstName") String firstName,
					@WebParam(name = "middleName") String middleName, @WebParam(name = "lastName") String lastName,
					@WebParam(name = "emailAddress") String emailAddress, @WebParam(name = "studentId") String studentId,  @WebParam(name = "studentPassword") String studentPassword) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"addDefaultStudentXML");
	    //return addUser(userId,userId,firstName,middleName,lastName,emailAddress,studentId,"11111111",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"STUDENT","NONE",true);
	    return toXML(null,addAUser(pwd,userId,userId,firstName,middleName,lastName,emailAddress,studentId,studentPassword,
                        "","","","","","","","","","","","","","","","","","","","",
                        new String[]{},"",/*new String[]{""},*/true));
	}

	/**
	 *
	 * Web service operation
	 */
	@WebMethod
	public String addUserXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "batchUId") String batchUId,
				@WebParam(name = "firstName") String firstName, @WebParam(name = "middleName") String middleName,
				@WebParam(name = "lastName") String lastName, @WebParam(name = "emailAddress") String emailAddress,
				@WebParam(name = "studentId") String studentId, @WebParam(name = "userPwd") String userPwd,
				@WebParam(name = "gender") String gender, @WebParam(name = "birthYear") String birthYear,
				@WebParam(name = "birthMonth") String birthMonth, @WebParam(name = "birthDay") String birthDay,
				@WebParam(name = "eduLevel") String eduLevel, @WebParam(name = "company") String company,
				@WebParam(name = "jobTitle") String jobTitle, @WebParam(name = "department") String department,
				@WebParam(name = "street1") String street1, @WebParam(name = "street2") String street2,
				@WebParam(name = "city") String city, @WebParam(name = "stateOrProvince") String stateOrProvince,
				@WebParam(name = "zipOrPostCode") String zipOrPostCode, @WebParam(name = "country") String country,
				@WebParam(name = "website") String website, @WebParam(name = "homePhone") String homePhone,
				@WebParam(name = "workPhone") String workPhone, @WebParam(name = "workFax") String workFax,
				@WebParam(name = "mobilePhone") String mobilePhone, @WebParam(name = "portalRole") String portalRole,
				@WebParam(name = "secPortalRole") String[] secPortalRoles, @WebParam(name = "systemRole") String systemRole,
				/*@WebParam(name = "secSystemRole") String[] secSystemRoles,*/ @WebParam(name = "available") Boolean available) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"addUserXML");
	    return toXML(null,addAUser(pwd,userId,batchUId,firstName,middleName,lastName,emailAddress,studentId,
			    userPwd,gender,birthYear,birthMonth,birthDay,eduLevel,company,jobTitle,
			    department,street1,street2,city,stateOrProvince,zipOrPostCode,
			    country,website,homePhone,workPhone,workFax,mobilePhone,
			    portalRole,secPortalRoles,systemRole,/*new String[]{""},*/available));
	}

	private String checkUserDetail(String userDetail) throws Exception
	{
	    if(userDetail!=null && !userDetail.equalsIgnoreCase("") && !containsSpecialCharacters(userDetail))
	    {
		return userDetail.trim();
	    }
	    throw new Exception("Invalid user detail: '"+userDetail+"'");
	}

	private Boolean containsSpecialCharacters(String toCheck)
	{
	    if(!toCheck.contains(" ") && /*!toCheck.contains("@") &&*/ !toCheck.contains("%") && !toCheck.contains("&") && !toCheck.contains("#") && !toCheck.contains("<")	&& !toCheck.contains(">") && !toCheck.contains("=") && !toCheck.contains("+"))
	    {
		return false;
	    }
	    return true;
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String deleteUserByUserBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userBbId") String userBbId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"deleteUserByUserBbIdXML");
	    try
	    {
            //You could just deleteById(bbPm.generateId()) but this way you also check if user exists
            UserDbPersister.Default.getInstance().deleteById(UserDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(User.DATA_TYPE,userBbId)).getId());
	    }
	    catch(Exception e)
	    {
            //return "Error: Please provide a valid Id for a user";
            throw new WebServiceException("Invalid userId");
	    }
	    return toXML(null,true);
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public Boolean deleteUserByUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"deleteUserByUserId");

	    try
	    {
		UserDbPersister.Default.getInstance().deleteById(UserDbLoader.Default.getInstance().loadByUserName(userId).getId());
	    }
	    catch(Exception e)
	    {
		//return "Error: Please provide a valid userId";
		throw new WebServiceException("Invalid userId");
	    }
	    return true;
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String deleteUserByUserIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"deleteUserByUserIdXML");

	    try
	    {
            UserDbPersister.Default.getInstance().deleteById(UserDbLoader.Default.getInstance().loadByUserName(userId).getId());
	    }
	    catch(Exception e)
	    {
            //return "Error: Please provide a valid userId";
            throw new WebServiceException("Invalid userId");
	    }
	    return toXML(null,true);
	}

	private Boolean doesUserExist(String userId)
	{
	    try
	    {
		UserDbLoader.Default.getInstance().loadByUserName(userId);
	    }
	    catch(KeyNotFoundException knfe)
	    {
		return false;
	    }
	    catch(Exception e)
	    {
		throw new WebServiceException("Error whilst searching to see if user exists: "+e.toString()+" "+e.getMessage());
	    }
	    return true;
	}
	
	/**
	 * Web service operation
	 */
	@WebMethod
	public Boolean doesUserExist(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"doesUserExist");
	    return doesUserExist(userId);
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String doesUserExistXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"doesUserExistXML");
	    return toXML(null,doesUserExist(userId));
	}

	/**
	 * Web service operation
	 */
	@Deprecated
	@WebMethod
	public String[] getAllUserIDsNoDetails(@WebParam(name = "pwd") String pwd) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getAllUserIDsNoDetails");
	    try
	    {
            List<String> sl = new ArrayList<String>();
            List<UserDetails> udsl = getAllUserObjsNoDetails();
            Iterator i = udsl.iterator();
            while(i.hasNext())
            {
                sl.add(((UserDetails)i.next()).getUserName());
            }
            return (String[])sl.toArray(new String[sl.size()]);
	    }
	    catch(Exception e)
	    {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String getAllUserIDsNoDetailsXML(@WebParam(name = "pwd") String pwd) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getAllUserIDsNoDetailsXML");
	    try
	    {
            return toXML("UserDetails",getAllUserObjsNoDetails());
	    }
	    catch(Exception e)
	    {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	private List<CourseMembership> getCourseMembershipFromCourseId(String courseId) throws Exception
	{
	    return CourseMembershipDbLoader.Default.getInstance().loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId(),null,true);
	}
	
	@WebMethod
	public String getEnrolledStudentsForGivenCourseIDXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getEnrolledStudentsForGivenCourseIDXML");
	    try
	    {
            return toXML("UserDetails",getEnrolledStudentsForGivenCourseIDWithNamedElements(courseId));
	    }
	    catch(Exception e)
	    {
    		throw new WebServiceException("Error while retrieving students: "+e.toString()+" "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String getEnrolledUserDetailsForGivenCourseXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getEnrolledUserDetailsForGivenCourseXML");
	    extendedDetails = handleNullValue(extendedDetails);
	    try
	    {
    		return toXML("UserDetails",getEnrolledUserObjsForGivenCourse(courseId,extendedDetails?UserDetails.Verbosity.extended:UserDetails.Verbosity.standard));
        }
        catch (KeyNotFoundException knfe)
        {
    		throw new WebServiceException("CourseId '"+courseId+"' not found or does not exist ("+knfe.getMessage()+")");
	    }
        catch (Exception e)
        {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	 /**
	 * Web service operation
	 */
	@WebMethod
	public String[][] getEnrolledUsersForGivenCourse(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "extendedDetails") Boolean extendedDetails, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getEnrolledUsersForGivenCourse");
	    headerDesc = handleNullValue(headerDesc);
	    extendedDetails = handleNullValue(extendedDetails);
	    try
	    {
            List<String[]> sl = new ArrayList<String[]>();
            List<UserDetails> udl = getEnrolledUserObjsForGivenCourse(courseId, extendedDetails?UserDetails.Verbosity.extended:UserDetails.Verbosity.standard);
            UserDetails.Verbosity verbosity = extendedDetails?UserDetails.Verbosity.extended:UserDetails.Verbosity.standard;
            if(headerDesc)
            {
                sl.add(new UserDetails(verbosity).toStringArrayHeader());
            }

            Iterator i = udl.iterator();
            while(i.hasNext())
            {
                sl.add(((UserDetails)i.next()).toStringArray());
            }

            return (String[][])sl.toArray(new String[sl.size()][]);
        }
	    catch(KeyNotFoundException knfe)
	    {
            throw new WebServiceException("CourseId '"+courseId+"' not found or does not exist ("+knfe.getMessage()+")");
	    }
	    catch(Exception e)
	    {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@Deprecated
	@WebMethod
	public String[][] getEnrollmentsByBatchUIDandCourseIDPattern(@WebParam(name = "pwd") String pwd, @WebParam(name = "userBatchUid") String userBatchUid, @WebParam(name = "regex") String regex, @WebParam(name ="isMemberOnly") Boolean isMemberOnly, @WebParam(name ="extendedDetails") Boolean extendedDetails, @WebParam(name ="headerDesc") Boolean headerDesc) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getEnrollmentsByBatchUIDandCourseIDPattern");
	    headerDesc = handleNullValue(headerDesc);
	    extendedDetails = handleNullValue(extendedDetails);
	    //When isMemberOnly is TRUE the service simply returns true or false,
	    //  otherwise it returns a list of course batch_uids and roles....
	    isMemberOnly = handleNullValue(isMemberOnly);
		    
	    List<String[]> enRec = new ArrayList<String[]>();
	    enRec.add(new String[]{"Error","No matching memberships found"});
	    Pattern pattern = Pattern.compile("");
	    try
	    {
		pattern = Pattern.compile(regex);
	    }
	    catch(Exception e)
	    {
		throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }

	    List matchesCourseUID = new ArrayList();
	    List matchesRoles = new ArrayList();
	    //take the user batch_uid and find their memberships
	    List membershipList = null;
	    Enrollment enrollment = new Enrollment();
	    enrollment.setPersonBatchUid(userBatchUid);
	    try{
		 membershipList = Default.getInstance().load(enrollment);
	    }catch(PersistenceException ex){}

	    String role = "";
	    if(membershipList.size()>0)
	    {
             for (int cnt = 0; cnt < membershipList.size(); cnt++)
             {
                 if(isAMatch(pattern,((Enrollment)membershipList.get(cnt)).getCourseSiteBatchUid()))
                 {
                 if(isMemberOnly) //found a match - good to go
                 {
                     enRec.clear();
                     if (headerDesc)
                     {
                    enRec.add(new String[]{"Is Member"});
                     }
                     enRec.add(new String[]{"true"});
                     return (String[][])enRec.toArray(new String[enRec.size()][]);
                 }
                 else
                 {
                    // build a list them all
                    role = ((Enrollment)membershipList.get(cnt)).getRole().toExternalString();
                    matchesCourseUID.add(((Enrollment)membershipList.get(cnt)).getCourseSiteBatchUid());
                    matchesRoles.add( role.substring(role.lastIndexOf(':')+1));
                 }
                 }
             }

             if (matchesCourseUID.size() > 0 && matchesRoles.size() >0)
             {
                 enRec.clear();
                 if (headerDesc)
                 {
                 enRec.add(new String[] {"Course_UID", "Role"});
                 }

                 Iterator imcuid = matchesCourseUID.iterator();
                 Iterator imr = matchesRoles.iterator();
                 while(imcuid.hasNext())
                 {
                 enRec.add(new String[]{(String)imcuid.next(),(String)imr.next()});
                 }
             }
	     }

	    return (String[][])enRec.toArray(new String[enRec.size()][]);
	}

	/**
	* Web service operation
	*/
	@Deprecated
	@WebMethod
	public String[][] getSecondaryPortalRolesForGivenUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getSecondaryPortalRolesForGivenUserId");
        headerDesc = handleNullValue(headerDesc);
	    try
	    {
            List<String[]> sl = new ArrayList<String[]>();
            List<bbuws.Role> prl = getSecondaryPortalRolesForGivenUserId(userId);
            Iterator i = prl.iterator();

            if(headerDesc)
            {
                sl.add(new bbuws.Role().toStringArrayHeader());
            }

            while(i.hasNext())
            {
                sl.add(((bbuws.Role)i.next()).toStringArray());
            }

            return (String[][])sl.toArray(new String[sl.size()][]);
	    }
	    catch(Exception e)
	    {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String getSecondaryPortalRolesForGivenUserIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getSecondaryPortalRolesForGivenUserIdXML");
	    try
	    {
            return toXML("Roles",getSecondaryPortalRolesForGivenUserId(userId));
	    }
	    catch(Exception e)
	    {
            //return new String[][]{{"Error"},{"Couldn't load roles: "+e.toString()}};
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@Deprecated
	@WebMethod
	public String[][] getSecondarySystemRolesForGivenUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getSecondarySystemRolesForGivenUserId");
	    //validateUserId(pwd,userId);
            headerDesc = handleNullValue(headerDesc);
	    try
	    {
		List<String[]> sl = new ArrayList<String[]>();
		List<bbuws.Role> prl = getSecondarySystemRolesForGivenUserId(userId);
		Iterator i = prl.iterator();

		if(headerDesc)
		{
		    sl.add(new bbuws.Role().toStringArrayHeader());
		}
		
		while(i.hasNext())
		{
		    sl.add(((bbuws.Role)i.next()).toStringArray());
		}

		return (String[][])sl.toArray(new String[sl.size()][]);
	    }
	    catch(Exception e)
	    {
		throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String getSecondarySystemRolesForGivenUserIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getSecondarySystemRolesForGivenUserIdXML");
	    try
	    {
    		return toXML("Roles",getSecondarySystemRolesForGivenUserId(userId));
	    }
	    catch(Exception e)
	    {
        	throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	* Web service getUserDetails
	*/
	@Deprecated
	@WebMethod
	public String[][] getUserDetails(@WebParam(name = "pwd") String pwd, @WebParam(name="userId") String userId, @WebParam(name = "extendedDetails") Boolean extendedDetails, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getUserDetails");
	    headerDesc = handleNullValue(headerDesc);
	    extendedDetails = handleNullValue(extendedDetails);
	    UserDetails.Verbosity verbosity = extendedDetails?UserDetails.Verbosity.extended:UserDetails.Verbosity.standard;
	    try
	    {
            if(headerDesc)
    		{
                return new String[][]{getUserDetailsFromUserId(userId,verbosity).toStringArrayHeader(),getUserDetailsFromUserId(userId,verbosity).toStringArray()};
            }
            else
            {
                return new String[][]{getUserDetailsFromUserId(userId,verbosity).toStringArray()};
            }
        }
	    catch(KeyNotFoundException knfe)
	    {
            //return new String[][]{{"Error"},{"userId '"+userId+"' not found or does not exist ("+knfe.getMessage()+")"}};
            throw new WebServiceException("userId '"+userId+"' not found or does not exist ("+knfe.getMessage()+")");
	    }
	    catch(Exception e)
	    {
            //return new String[][]{{"Error"},{e.getMessage()}};
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
  	}


	/**
	* Web service getUserDetailsExtendedWithNamedElements
	*/
	@WebMethod
    public String getUserDetailsXML(@WebParam(name = "pwd") String pwd, @WebParam(name="userId") String userId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getUserDetailsXML");
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            List<UserDetails> ud = new ArrayList<UserDetails>();
            if(extendedDetails)
            {
                ud.add(getUserDetailsFromUserId(userId,UserDetails.Verbosity.extended));
                return toXML("UserDetails",ud);
            }
            else
            {
                ud.add(getUserDetailsFromUserId(userId,UserDetails.Verbosity.standard));
                return toXML("UserDetails",ud);
            }
        }
        catch (KeyNotFoundException knfe)
        {
            //return new UserDetails(new Exception("No users found: "+e.getMessage()));
            throw new WebServiceException("No user found: "+knfe.getMessage());
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
        }
    }

	/**
	 * Web service operation
	 */
	@Deprecated
	@WebMethod
	public String getUserRoleInCourse(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "courseId") String courseId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getUserRoleInCourse");
	    try
	    {
            return CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId(),UserDbLoader.Default.getInstance().loadByUserName(userId).getId()).getRole().toFieldName();
	    }
	    catch(Exception e)
	    {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String getUserRoleInCourseXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "courseId") String courseId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"getUserRoleInCourseXML");
	    try
	    {
            List<bbuws.Role> role = new ArrayList<bbuws.Role>();
            role.add(new bbuws.Role(CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId(),UserDbLoader.Default.getInstance().loadByUserName(userId).getId())));
            return toXML("Roles",role);
	    }
	    catch(Exception e)
	    {
            throw new WebServiceException(e.toString()+": "+e.getMessage());
	    }
	}

	private Boolean handleNullValue(Boolean value)
    {
        if (value == null)
	    {
           return false;
	    }
        return value;
	}

	private static boolean isAMatch(Pattern pattern, String searchStr)
	{
	    boolean match = false;
	    Matcher matcher = pattern.matcher(searchStr);
	    while (matcher.find())
	    {
            match = true;
	    }
	    return match;
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public boolean isUserInCourse(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "courseId") String courseId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"isUserInCourse");
	    try
	    {
            if(CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId(),UserDbLoader.Default.getInstance().loadByUserName(userId).getId())!=null)
            {
                return true;
            }
	    }catch(Exception e){}
	    return false;
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String isUserInCourseXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "courseId") String courseId) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"isUserInCourseXML");
	    try
	    {
            if(CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId(),UserDbLoader.Default.getInstance().loadByUserName(userId).getId())!=null)
            {
                return toXML(null,true);
            }
	    }catch(Exception e){}
	    return toXML(null,false);
	}

	/**
	 * roles can be NULL (I think) or 0 length
	 *
	 * Web service operation
	 */
	@WebMethod
	public String setOrModifySecondaryPortalRolesForGivenUserIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "roles") String[] roles) throws WebServiceException
	{
	    authoriseMethodUse(pwd,"setOrModifySecondaryPortalRolesForGivenUserIdXML");
        return toXML(null,setOrModifySecondaryPortalRolesForGivenUserId(userId,parseSecondaryPortalRoles(roles)));
	}

    private String toXML(String xmlTopLevelType, Object data) throws WebServiceException
    {
        StringBuffer for_return = new StringBuffer("");
        if(data==null)
        {
            throw new WebServiceException("Error parsing to xml: Passed data is null/blank");
        }

        if(data instanceof List)
        {
            if(xmlTopLevelType==null || xmlTopLevelType.equalsIgnoreCase(""))
            {
                throw new WebServiceException("Error parsing to xml: xmlTopLevelType is null/blank");
            }

            List list = (List)data;
            Iterator itrtr = list.iterator();
            for_return.append("<" + xmlTopLevelType + ">");
            while(itrtr.hasNext())
            {
                for_return.append(toXMLAnObject((ReturnTypeInterface)itrtr.next(),xmlTopLevelType.substring(0,xmlTopLevelType.length()-1)));
            }
            for_return.append("</"+xmlTopLevelType+">");
        }
        else if(data instanceof ReturnTypeInterface)
        {
            if(xmlTopLevelType==null || xmlTopLevelType.equalsIgnoreCase(""))
            {
                throw new WebServiceException("Error parsing to xml: xmlTopLevelType is null/blank");
            }

            for_return.append("<"+xmlTopLevelType+">");
            for_return.append(toXMLAnObject((ReturnTypeInterface)data,xmlTopLevelType.substring(0,xmlTopLevelType.length()-1)));
            for_return.append("</"+xmlTopLevelType+">");
        }
        else
        {
            for_return = new StringBuffer("<![CData[" + data + "]]>");
        }
        return for_return.toString();
    }

    private StringBuffer toXMLAnObject(ReturnTypeInterface rti, String subElementName)
    {
        StringBuffer for_return = new StringBuffer("<"+subElementName+">");
        for(int i=0;i<rti.toStringArrayHeader().length;i++)
        {
            for_return.append("<"+rti.toStringArrayHeader()[i]+">"+rti.toStringArray()[i]+"</"+rti.toStringArrayHeader()[i]+">");
        }
        return for_return.append("<"+subElementName+">");
    }


}
