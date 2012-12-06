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
import blackboard.admin.persist.course.EnrollmentLoader.Default;
import blackboard.data.course.Course;
import blackboard.data.course.CourseMembership;
import blackboard.data.course.CourseMembership.Role;
import blackboard.data.course.CourseQuota;
import blackboard.data.registry.CourseRegistryEntry;
import blackboard.data.user.User;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseDbPersister;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.course.CourseMembershipDbPersister;
import blackboard.persist.Id;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;
import blackboard.persist.registry.CourseRegistryEntryDbLoader;
import blackboard.persist.registry.CourseRegistryEntryDbPersister;
import blackboard.persist.user.UserDbLoader;
import blackboard.platform.BbServiceManager;

import blackboard.admin.persist.course.CloneConfig;
import blackboard.admin.persist.course.CourseSitePersister;
import blackboard.admin.persist.course.impl.CourseSiteDbPersister;

//java
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
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
@WebService(name="BBCourseWebService", serviceName="BBCourseWebService", targetNamespace="http://www.ncl.ac.uk/BBCourseWebService")
public class BBCourseWebService
{
    private WebServiceProperties wsp = new WebServiceProperties("IDLA","BbWebservices");
    //private enum Verbosity{minimal,quota,standard,extended}

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

    /**
     *
     * Web service operation
     */
    @WebMethod
    public Boolean addCourse(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "batchUID") String batchUID,
			    @WebParam(name = "title") String title, @WebParam(name = "courseDescription") String courseDescription,
			    @WebParam(name = "available") Boolean available, @WebParam(name = "allowGuests") Boolean allowGuests,
			    @WebParam(name = "allowObservers") Boolean allowObservers) throws WebServiceException
    {
        authoriseMethodUse(pwd,"addCourse");
        return addCourse(courseId, batchUID, title, courseDescription, available, allowGuests, allowObservers);
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

    private void authoriseMethodUse(String methodPwd, String methodName) throws WebServiceException
    {
        if(!wsp.isMethodAccessible(methodName) || (wsp.usingPassword() && !wsp.passwordMatches(methodPwd)))
        {
            throw new WebServiceException("Access Denied");
        }
    }

    private String checkCourseDetail(String courseDetail) throws Exception
    {
	if(courseDetail!=null && !courseDetail.equalsIgnoreCase(""))
	{
	    return courseDetail.trim();
	}
	throw new Exception("Invalid course detail: '"+courseDetail+"'");
    }

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

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean deleteCourseByCourseBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseBbId") String courseBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteCourseByCourseBbId");
        return deleteCourseByCourseBbId(courseBbId);
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

    /**
     * Role (Case insensitive): COURSE_BUILDER, DEFAULT, GRADER, GUEST, INSTRUCTOR, TEACHING_ASSISTANT, NONE, STUDENT, 
     *
     * Web service operation
     */
    @WebMethod
    public Boolean enrollGivenUserOnGivenCourse(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "userId") String userId, @WebParam(name = "role") String role) throws WebServiceException
    {
        authoriseMethodUse(pwd,"enrollGivenUserOnGivenCourse");
        return enrollGivenUserOnGivenCourse(userId,courseId,role);
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

    /**
     * Web service operation
     */
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

    private CourseDetails getCourseDetailsObjFromCourseId(String courseId, Boolean extendedDetails) throws Exception
    {
        return new CourseDetails(CourseDbLoader.Default.getInstance().loadByCourseId(courseId),extendedDetails?CourseDetails.Verbosity.extended:CourseDetails.Verbosity.standard);
    }

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

    /**
     * Web service operation
     */
    @WebMethod
    public String getCourseMembershipBbIdForGivenUserIdAndCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getCourseMembershipBbIdForGivenUserIdAndCourseId");
        return getCourseMembershipBbIdForGivenUserIdAndCourseId(userId,courseId).getBbId();
    }

    /**
     * Web service operation
     */
    @WebMethod
    public CourseMembershipDetails getCourseMembershipBbIdForGivenUserIdAndCourseIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getCourseMembershipBbIdForGivenUserIdAndCourseIdWithNamedElements");
        return getCourseMembershipBbIdForGivenUserIdAndCourseId(userId,courseId);
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

    /**
     * Web service operation
     */
    @WebMethod
    public CourseMembershipDetails getCourseMembershipDetailsForGivenCourseMembershipBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembershipBbId") String courseMembershipBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getCourseMembershipDetailsForGivenCourseMembershipBbIdWithNamedElements");
        return getCourseMembershipDetailsForGivenCourseMembershipBbId(courseMembershipBbId);
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
    public CourseQuotaDetails getCourseQuotaDetailsWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getCourseQuotaDetailsWithNamedElements");
        return getCourseQuotaDetails(courseId);
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

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean modifyCourseQuotaDetails(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseID") String courseID, @WebParam(name = "overrideDiskQuota") Boolean overrideDiskQuota, @WebParam(name = "overrideUploadLimit") Boolean overrideUploadLimit, @WebParam(name = "absoluteLimit") Long absoluteLimit, @WebParam(name = "softLimit") Long softLimit, @WebParam(name = "uploadLimit") Long uploadLimit) throws WebServiceException
    {
        authoriseMethodUse(pwd,"modifyCourseQuotaDetails");
        return modifyCourseQuotaDetails(courseID, overrideDiskQuota, overrideUploadLimit, absoluteLimit, softLimit, uploadLimit);
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

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean unEnrollGivenUserFromGivenCourse(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"unenrollGivenUserFromGivenCourse");
        return unEnrollGivenUserFromGivenCourse(userId,courseId);
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

/**
 *
 * @author vic123, IDLA
 */

    @WebMethod
    public Boolean copyCourseExact(@WebParam(name = "pwd") String pwd, @WebParam(name = "sourceBatchUid") String sourceBatchUid,
                @WebParam(name = "targetBatchUid") String targetBatchUid) throws WebServiceException
    {
        authoriseMethodUse(pwd,"copyCourseExact");//!!
        try {
            bbwscommon.BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.INFORMATION, "copyCourseExact() - enter. "
                        + "sourceBatchUid: " + sourceBatchUid + "; targetBatchUid: " + targetBatchUid, this);
            //Course tgt_course, src_course;
            blackboard.admin.data.course.CourseSite tgt_course, src_course;
            try {
                //tgt_course = CourseDbLoader.Default.getInstance().loadByBatchUid(targetBatchUid);
                tgt_course = blackboard.admin.persist.course.impl.CourseSiteDbLoader.Default.getInstance().load(targetBatchUid);
                throw new WebServiceException("ID for the destination already exists (targetBatchUid: " + targetBatchUid
                        + "; tgt_course.courseId: " + tgt_course.getCourseId() + ")");
            }
            catch(KeyNotFoundException knf)
            {
                //src_course = CourseDbLoader.Default.getInstance().loadByBatchUid(sourceBatchUid);
                src_course = blackboard.admin.persist.course.impl.CourseSiteDbLoader.Default.getInstance().load(sourceBatchUid);
                String src_course_id = src_course.getCourseId();
                Id src_id = src_course.getId();
/*
 *
 *  //Code below causes blackboard.persist.PersistenceException: Can not perform clone where target and source are identical.
    //	at blackboard.admin.persist.course.impl.clone.AdminCourseCloneOperator.clone(AdminCourseCloneOperator.java:146)
    //after changing of Ids and persisting new class appears in Db, but reloading with old id returns newly inserted class, probably due to caching
 * //??!! then, don't understand what have changed, started causing (upon persist call):
 * blackboard.persist.KeyNotFoundException: The specified object was not found.
	at blackboard.persist.impl.NewBaseDbLoader.loadObject(NewBaseDbLoader.java:140)
	at blackboard.persist.course.impl.CourseDbLoaderImpl.loadByCourseId(CourseDbLoaderImpl.java:155)
	at blackboard.persist.course.impl.CourseDbLoaderImpl.loadByCourseId(CourseDbLoaderImpl.java:126)
	at blackboard.persist.course.impl.CourseDbLoaderImpl.loadByCourseId(CourseDbLoaderImpl.java:118)
	at blackboard.persist.course.impl.CourseDbPersisterImpl.persist(CourseDbPersisterImpl.java:114)
	at blackboard.persist.course.impl.CourseDbPersisterImpl.persist(CourseDbPersisterImpl.java:64)
*/
  
                src_course.setId(Id.UNSET_ID);
                src_course.setCourseId(targetBatchUid);
                src_course.setBatchUid(targetBatchUid);
                //CourseDbPersister persister = blackboard.persist.course.CourseDbPersister.Default.getInstance();
                //persister.persist(src_course);

                //with CourseSiteDbPersister seem to work fine
                CourseSiteDbPersister.Default.getInstance().insert(src_course);
 
                //I:\blackboard\content\locale\en_SM\messages\resource.csv
                //System.Web.Services.Protocols.SoapException: Error: Course copy failed: blackboard.persist.PersistenceException: Can not perform clone where target and source are identical. at System.Web.Services.Protocols
                bbwscommon.BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.DEBUG, "copyCourseExact() - insert(src_course). "
                        + " sourceBatchUid: " + sourceBatchUid + "; targetBatchUid: " + targetBatchUid
                        + "; src_course_id: " + src_course_id
                        + "; src_id: " + src_id.toExternalString(), this);

                Course src_course1 = CourseDbLoader.Default.getInstance().loadByBatchUid(sourceBatchUid);
                Course tgt_course1 = CourseDbLoader.Default.getInstance().loadByBatchUid(targetBatchUid);
                bbwscommon.BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.DEBUG,
                        "copyCourseExact(), loadByBatchUid(). src_course.getBatchUid(): " + src_course1.getBatchUid()
                        + "src_course.getCourseId(): " + src_course1.getCourseId()
                        + "; tgt_course.getBatchUid(): " + tgt_course1.getBatchUid()
                        + "; tgt_course.getCourseId(): " + tgt_course1.getCourseId() , this);


                src_course1 = CourseDbLoader.Default.getInstance().loadByCourseId(src_course_id);
                bbwscommon.BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.DEBUG,
                        "copyCourseExact(), loadByCourseId(), src_course.getBatchUid(): " + src_course1.getBatchUid()
                        + "src_course.getCourseId(): " + src_course1.getCourseId()
                        , this);

                src_course1 = CourseDbLoader.Default.getInstance().loadById(src_id);
                bbwscommon.BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.DEBUG,
                        "copyCourseExact(), loadById(), src_course.getBatchUid(): " + src_course1.getBatchUid()
                        + "src_course.getCourseId(): " + src_course1.getCourseId()
                        , this);

                src_course1 = CourseDbLoader.Default.getInstance().loadById(src_id, null, true);
                bbwscommon.BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.DEBUG,
                        "copyCourseExact(), loadById(Heavy), src_course.getBatchUid(): " + src_course1.getBatchUid()
                        + "src_course.getCourseId(): " + src_course1.getCourseId()
                        , this);

//                src_course1 = CourseDbLoader.Default.getInstance().loadb  loadByIdIgnoreRowStatus(src_id);

                src_course1 = CourseDbLoader.Default.getInstance().loadByBatchUid(sourceBatchUid);
                bbwscommon.BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.DEBUG,
                        "copyCourseExact(), loadByBatchUid(). src_course.getBatchUid(): " + src_course1.getBatchUid()
                        + "src_course.getCourseId(): " + src_course1.getCourseId(), this);


/* Alternative approach is manual copy of course attributes/settings
 * //blackboard.webapps.blackboard.struts.CopyAction.duplicateSettings();
//blackboard.admin.persist.course.impl.clone.operator.SettingCloneOperator.duplicateSettings();

                tgt_course.setBatchUid(targetBatchUid);
                tgt_course.setBatchUid(targetBatchUid);
                tgt_course.setAbsoluteLimit(src_course.getAbsoluteLimit());
                tgt_course.setAllowGuests(src_course.getAllowGuests());
                tgt_course.setAllowObservers(src_course.getAllowObservers());
                tgt_course.setClassificationId(src_course.getClassificationId());
                tgt_course.setDescription(src_course.getDescription());
                tgt_course.setDurationType(src_course.getDurationType());
                tgt_course.setEndDate(src_course.getEndDate());
                tgt_course.setEnrollmentAccessCode(src_course.getEnrollmentAccessCode());
                tgt_course.setEnrollmentEndDate(src_course.getEnrollmentEndDate());
                tgt_course.setEnrollmentStartDate(src_course.getEnrollmentStartDate());
                tgt_course.setEnrollmentType(src_course.getEnrollmentType());
                tgt_course.setFee(src_course.getFee());
                tgt_course.setHasDescriptionPage(src_course.getHasDescriptionPage());
                tgt_course.setInstitutionName(src_course.getInstitutionName());
                tgt_course.setIsAvailable(src_course.getIsAvailable());
                tgt_course.setIsLockedOut(src_course.getIsLockedOut());
                tgt_course.setNumDaysOfUse(src_course.getNumDaysOfUse());
                tgt_course.setPaceType(src_course.getPaceType());
                tgt_course.setServiceLevelType(src_course.getServiceLevelType());
                tgt_course.setSoftLimit(src_course.getSoftLimit());
                tgt_course.setStartDate(src_course.getStartDate());
                tgt_course.setTitle(src_course.getTitle());
                tgt_course.setUploadLimit(src_course.getUploadLimit());

                tgt_course.setButtonStyleId(src_course.getButtonStyleId());
                tgt_course.setNavStyle(src_course.getNavStyle());
                tgt_course.setNavColorFg(src_course.getNavColorFg());
                tgt_course.setNavColorBg(src_course.getNavColorBg());
                tgt_course.setIsNavCollapsible(src_course.getIsNavCollapsible());
                tgt_course.setBannerImageFile(src_course.getBannerImageFile());
                tgt_course.setIsLocaleEnforced(src_course.getIsLocaleEnforced());
                tgt_course.setLocale(src_course.getLocale());
                    */

                CloneConfig cfg = new CloneConfig();
                cfg.includeArea(CloneConfig.Area.ALL);
                CourseSitePersister site_persister = CourseSiteDbPersister.Default.getInstance();
                site_persister.clone(sourceBatchUid, targetBatchUid, cfg);
                return true;
            }   
        }
        catch(Exception e)
        {
            bbwscommon.BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.ERROR, e, "copyCourseExact(). ", this);
            throw new WebServiceException("Error: Course copy failed: " + e.getMessage());
        }
    }

    @WebMethod
    public boolean updateCourse(String courseId, String batchUID, String title, String courseDescription,
                              Boolean available, Boolean allowGuests, Boolean allowObservers) throws WebServiceException
    {
        available = handleNullValue(available);
        allowGuests = handleNullValue(allowGuests);
        allowObservers = handleNullValue(allowObservers);
        try {
            Course c = CourseDbLoader.Default.getInstance().loadByCourseId(courseId);
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
            bbwscommon.BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.ERROR, e, "updateCourse(). ", this);
            throw new WebServiceException("Error while trying to update course: "+e.toString());
        }
        return true;
    }

}