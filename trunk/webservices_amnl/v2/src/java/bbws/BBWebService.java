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
package bbws;

//Webservice
import bbws.announcement.BBAnnouncement;
import bbws.course.BBCourse;
import bbws.course.BBCourseQuota;
import bbws.course.coursemembership.BBCourseMembership;
import bbws.course.coursemembership.BBCourseMembershipRole;
import bbws.course.BBEnrollment;
import bbws.gradecentre.BBAttempt;
import bbws.gradecentre.BBGradeCentreSettings;
import bbws.gradecentre.BBLineitem;
import bbws.gradecentre.BBOutcome;
import bbws.gradecentre.BBOutcomeDefinition;
import bbws.gradecentre.BBScore;
import bbws.groups.BBGroup;
import bbws.groups.BBGroupMembership;
import bbws.user.BBUser;
import bbws.user.BBRole;

//blackboard
import blackboard.admin.data.course.Enrollment;
import blackboard.admin.data.IAdminObject.RecStatus;
import blackboard.admin.data.IAdminObject.RowStatus;
import blackboard.admin.data.user.Person;
import blackboard.admin.persist.course.EnrollmentLoader;
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
import blackboard.data.gradebook.impl.Outcome;
import blackboard.data.gradebook.impl.OutcomeDefinition;
import blackboard.data.gradebook.Lineitem;
import blackboard.data.gradebook.Score;
import blackboard.data.registry.CourseRegistryEntry;
import blackboard.data.role.PortalRole;
import blackboard.data.user.User;
import blackboard.data.user.User.EducationLevel;
import blackboard.data.user.User.Gender;
import blackboard.data.user.UserRole;
import blackboard.db.ConstraintViolationException;
import blackboard.persist.announcement.AnnouncementDbLoader;
import blackboard.persist.announcement.AnnouncementDbPersister;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
@WebService(name="BBWebService", serviceName="BBWebService", targetNamespace="")
public class BBWebService
{
    private WebServiceProperties wsp = new WebServiceProperties("amnl","BBWebService");

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
    private Boolean announcementCreate(BBAnnouncement announcement, BBCourse course, String textType) throws WebServiceException
    {
        try
        {
            Announcement a = new Announcement();
            //We can't use checkAnnouncementDetail as this mustn't throw an error as null or ""
            //is valid when posting message, but not when modifying them.
            if(announcement.getAnnouncementBbId()!=null && !announcement.getAnnouncementBbId().trim().equalsIgnoreCase(""))
            {
                announcement.setAnnouncementBbId(announcement.getAnnouncementBbId().trim());
                //We are modifying an announcement
                a.setId(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Announcement.DATA_TYPE,announcement.getAnnouncementBbId()));
            }
            //else we are creating an announcement
            a.setTitle(checkAndTrimParam(announcement.getTitle()));
            announcement.setBody(checkAndTrimParam(announcement.getBody()));
            FormattedText ft = null;
            if(textType.equalsIgnoreCase("HTML"))
            {
                ft = new FormattedText(announcement.getBody(),Type.HTML);
            }
            else
            {
               ft = new FormattedText(announcement.getBody(),Type.PLAIN_TEXT);
            }
            a.setBody(ft);
            //Assume type is course unless specifically set as SYSTEM
            a.setType(blackboard.data.announcement.Announcement.Type.COURSE);
            try
            {
                announcement.setType(checkAndTrimParam(announcement.getType()));
                //may be course or system
                if(announcement.getType().equalsIgnoreCase("SYSTEM"))
                {
                    //it's def a system
                    course.setCourseId("SYSTEM");
                    a.setType(blackboard.data.announcement.Announcement.Type.SYSTEM);
                }
                //it's def a course
            }catch(Exception e){/*it's def a course*/}
            course.setCourseId(checkAndTrimParam(course.getCourseId()));
            a.setCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseId()).getId());
            a.setCreatorUserId(UserDbLoader.Default.getInstance().loadByUserName("administrator").getId());
            if (announcement.getPermanent()==null)
            {
                announcement.setPermanent(Boolean.FALSE);
            }
            a.setIsPermanent(announcement.getPermanent());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(sdf.parse(announcement.getStartDate()));
            try{a.setRestrictionStartDate(gc);}catch(Exception e){}
            //else don't set a start date at all.
            gc.setTime(sdf.parse(announcement.getEndDate()));
            try{a.setRestrictionEndDate(gc);}catch(Exception e){}
            //else don't set an end date at all.
            AnnouncementDbPersister.Default.getInstance().persist(a);
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
        }
        return true;
    }

    private boolean announcementDelete(BBAnnouncement announcement) throws WebServiceException
    {
        try
        {
            AnnouncementDbPersister.Default.getInstance().deleteById(AnnouncementDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Announcement.DATA_TYPE,announcement.getAnnouncementBbId())).getId());
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Please provide a valid dbid "+e.getMessage());
        }
        return true;
    }

    private List<BBAnnouncement> announcementReadSearchByAvailableAnnouncementAndUserId(BBUser user) throws WebServiceException
    {
        List<Announcement> al = null;
        try
        {
            al = AnnouncementDbLoader.Default.getInstance().loadAvailableByUserId(UserDbLoader.Default.getInstance().loadByUserName(user.getUserName()).getId());
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
        }
        if(al==null || al.size()<1)
        {
            throw new WebServiceException("No announcements found");
        }
        List<BBAnnouncement> rl = new ArrayList<BBAnnouncement>();
        Iterator<Announcement> i = al.iterator();
        while(i.hasNext())
        {
            rl.add(new BBAnnouncement(i.next()));
        }
        return rl;
    }

    private List<BBAnnouncement> announcementReadSearchByCourseId(BBCourse course) throws WebServiceException
    {
        List<Announcement> al = null;
        try
        {
             AnnouncementDbLoader.Default.getInstance().loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseBbId()).getId());
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
        }
        if(al==null || al.size()<1)
        {
            throw new WebServiceException("No announcements found");
        }
        List<BBAnnouncement> rl = new ArrayList<BBAnnouncement>();
        Iterator<Announcement> i = al.iterator();
        while(i.hasNext())
        {
            rl.add(new BBAnnouncement(i.next()));
        }
        return rl;
    }

    private boolean announcementUpdate(BBAnnouncement announcement, BBCourse course, String textType) throws WebServiceException
    {
        try
        {
            announcement.setAnnouncementBbId(checkAndTrimParam(announcement.getAnnouncementBbId()));
            announcement.setType("COURSE");
            try
            {
                course.setCourseId(checkAndTrimParam(course.getCourseId()));
            }
            catch(Exception e)
            {
                course = null;
                announcement.setType("SYSTEM");
            }
            return announcementCreate(announcement,course,textType);
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

    /*
     * Not specifying a course will lead to a system annuoncement
     */
    @WebMethod
    public Boolean bbAnnouncementCreate(@WebParam(name = "pwd") String pwd, @WebParam(name = "announcement") BBAnnouncement announcement, @WebParam(name = "course") BBCourse course, @WebParam(name = "textType") String textType) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbAnnouncementCreate");
        return announcementCreate(announcement,course,textType);
    }

    @WebMethod
    public Boolean bbAnnouncementDelete(@WebParam(name = "pwd") String pwd, @WebParam(name = "announcement") BBAnnouncement announcement) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbAnnouncementDelete");
        return announcementDelete(announcement);
    }

    @WebMethod
    public List<BBAnnouncement> bbAnnouncementReadSearchByAvailableAnnouncementAndUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbAnnouncementReadSearchByAvailableAnnouncementAndUserId");
        return announcementReadSearchByAvailableAnnouncementAndUserId(user);
    }

    /*
     * System announcement can be retrieved by specifying "SYSTEM" as a courseId
     */
    @WebMethod
    public List<BBAnnouncement> bbAnnouncementReadSearchByCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbAnnouncementReadSearchByCourseId");
        return announcementReadSearchByCourseId(course);
    }

    /*
     * Not specifying a course will lead to a system annuoncement
     */
    @WebMethod(operationName = "bbAnnouncementUpdate")
    public boolean bbAnnouncementUpdate(@WebParam(name = "pwd") String pwd, @WebParam(name = "announcement") BBAnnouncement announcement, @WebParam(name = "course") BBCourse course, @WebParam(name = "textType") String textType) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbAnnouncementUpdate");
        return announcementUpdate(announcement,course,textType);
    }

    @WebMethod
    public Boolean bbCourseCreate(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbCourseCreate");
        return courseCreate(course);
    }

    @WebMethod
    public Boolean bbCourseDelete(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbCourseDelete");
        return courseDelete(course);
    }

    @WebMethod
    public Boolean bbCourseMembershipCreate(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembership") BBCourseMembership courseMembership) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbCourseMembershipCreate");
        return courseMembershipCreate(courseMembership);
    }

    @WebMethod
    public Boolean bbCourseMembershipDelete(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembership") BBCourseMembership courseMembership) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbCourseMembershipDelete");
        return courseMembershipDelete(courseMembership);
    }

    @WebMethod
    public BBCourseMembership bbCourseMembershipRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembership") BBCourseMembership courseMembership, @WebParam(name = "verbosity") BBCourseMembership.BBCourseMembershipVerbosity verbosity) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbCourseMembershipRead");
        return courseMembershipRead(courseMembership,verbosity);
    }

    @WebMethod
    public BBCourseMembership bbCourseMembershipReadSearchByUserIdAndCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course, @WebParam(name = "user") BBUser user, @WebParam(name = "verbosity") BBCourseMembership.BBCourseMembershipVerbosity verbosity, @WebParam(name = "loadUser") Boolean loadUser)
    {
        authoriseMethodUse(pwd,"bbCourseMembershipReadSearchByUserIdAndCourseId");
        return courseMembershipReadSearchByUserIdAndCourseId(user,course,verbosity,loadUser);
    }

    @WebMethod
    public BBCourseQuota bbCourseQuotaRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbCourseQuotaRead");
        return courseQuotaRead(course);
    }

    @WebMethod
    public Boolean bbCourseQuotaUpdate(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course, @WebParam(name = "courseQuota") BBCourseQuota courseQuota) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbCourseQuotaUpdate");
        return courseQuotaUpdate(course,courseQuota);
    }

    @WebMethod
    public BBCourse bbCourseRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course, @WebParam(name = "verbosity") BBCourse.BBCourseVerbosity verbosity) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbCourseRead");
        return courseRead(course,verbosity);
    }

    @WebMethod
    public List<BBCourse> bbCourseReadAll(@WebParam(name = "pwd") String pwd, @WebParam(name = "verbosity") BBCourse.BBCourseVerbosity verbosity) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbCourseReadAll");
        return courseReadAll(verbosity);
    }

    @WebMethod
    public List<BBCourse> bbCourseReadSearchByRegex(@WebParam(name = "pwd") String pwd, @WebParam(name = "regex") String regex, @WebParam(name = "verbosity") BBCourse.BBCourseVerbosity verbosity) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbCourseReadSearchByRegex");
        return courseReadSearchByRegex(regex, verbosity);
    }

    @WebMethod
    public List<BBCourse> bbCourseReadSearchByUserIdAndCMRole(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user, @WebParam(name = "") BBCourseMembershipRole cmRole, @WebParam(name = "verbosity") BBCourse.BBCourseVerbosity verbosity) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbCourseReadSearchByUserIdAndCMRole");
        return courseReadSearchByUserIdAndCMRole(user,cmRole,verbosity);
    }

    @WebMethod
    public List<BBEnrollment> bbEnrollmentReadSearchByUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbEnrollmentReadSearchByUserId");
        return enrollmentReadSearchByUserId(user);
    }

    @WebMethod
    public boolean bbGradeCentreAttemptDelete(@WebParam(name = "pwd") String pwd, @WebParam(name = "attempt") BBAttempt attempt) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGradeCentreAttemptDelete");
        return gradeCentreAttemptDelete(attempt);
    }

    @WebMethod
    public List<BBAttempt> bbGradeCentreAttemptReadSearchByOutcomeDefinitionId(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefinition") BBOutcomeDefinition outcomeDef, @WebParam(name = "verbosity") BBAttempt.BBAttemptVerbosity verbosity) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGradeCentreAttemptReadSearchByOutcomeDefinitionId");
        return gradeCentreAttemptReadSearchByOutcomeDefinitionId(outcomeDef,verbosity);
    }

    @WebMethod
    public boolean bbGradeCentreLineitemAdd(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitem") BBLineitem lineitem, @WebParam(name = "course") BBCourse course) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGradeCentreLineitemAdd");
        return gradeCentreLineitemAdd(lineitem,course);
    }

    @WebMethod
    public Boolean bbGradeCentreLineitemDelete(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitem") BBLineitem lineitem) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGradeCentreLineItemDelete");
        return gradeCentreLineitemOrOutcomeDefinitionDelete(lineitem.getLineItemBbId());
    }

    @WebMethod
    public BBLineitem bbGradeCentreLineitemRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitem") BBLineitem lineitem) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGradeCentreLineitemRead");
        return gradeCentreLineitemRead(lineitem);
    }

    @WebMethod
    public List<BBLineitem> bbGradeCentreLineitemReadSearchByCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGradeCentreLineitemReadSearchByCourseId");
        return gradeCentreLineitemReadSearchByCourseId(course);
    }

    @WebMethod
    public Boolean bbGradeCentreOutcomeDefinitionDelete(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDef") BBOutcomeDefinition outcomeDef) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGradeCentreOutcomeDefinitionDelete");
        return gradeCentreLineitemOrOutcomeDefinitionDelete(outcomeDef.getOutcomeDefinitionBbId());
    }

    @WebMethod
    public BBOutcomeDefinition bbGradeCentreOutcomeDefinitionRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDef") BBOutcomeDefinition outcomeDef) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGradeCentreOutcomeDefinitionRead");
        return gradeCentreOutcomeDefinitionRead(outcomeDef);
    }

    @WebMethod
    public List<BBOutcomeDefinition> bbGradeCentreOutcomeDefinitionReadSearchByCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGradeCentreOutcomeDefinitionReadSearchByCourseId");
        return gradeCentreOutcomeDefinitionReadSearchByCourseId(course);
    }

    @WebMethod
    public BBOutcome bbGradeCentreOutcomeRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcome") BBOutcome outcome) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGradeCentreOutcomeRead");
        return gradeCentreOutcomeRead(outcome);
    }

    @WebMethod
    public List<BBOutcome> bbGradeCentreOutcomeReadSearchByOutcomeDefinitionId(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBOutcomeDefinition outcomeDef) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGradeCentreOutcomeReadSearchByOutcomeDefinitionId");
        return gradeCentreOutcomeReadSearchByOutcomeDefinitionId(outcomeDef);
    }

    @WebMethod
    public BBScore bbGradeCentreScoreRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "score") BBScore score, @WebParam(name = "verbosity") BBScore.BBScoreVerbosity verbosity) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGradeCentreScoreRead");
        return gradeCentreScoreRead(score,verbosity);
    }

    @WebMethod
    public List<BBScore> bbGradeCentreScoreReadSearchByLineitemId(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitem") BBLineitem lineitem, @WebParam(name = "verbosity") BBScore.BBScoreVerbosity verbosity) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGradeCentreScoreReadSearchByLineitemId");
        return gradeCentreScoreReadSearchByLineitemId(lineitem,verbosity);
    }

    @WebMethod
    public BBScore bbGradeCentreScoreReadSearchByLineitemIdAndCourseMembershipId(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitem") BBLineitem lineitem, @WebParam(name = "courseMembership") BBCourseMembership courseMembership, @WebParam(name = "verbosity") BBScore.BBScoreVerbosity verbosity) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGradeCentreScoreReadSearchByLineitemIdAndCourseMembershipId");
        return gradeCentreScoreReadSearchByLineitemIdAndCourseMembershipId(lineitem,courseMembership,verbosity);
    }

    @WebMethod
    public BBScore bbGradeCentreScoreReadSearchByLineitemIdAndUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitem") BBLineitem lineitem, @WebParam(name = "user") BBUser user, @WebParam(name = "verbosity") BBScore.BBScoreVerbosity verbosity) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGradeCentreScoreReadSearchByLineitemIdAndUserId");
        return gradeCentreScoreReadSearchByLineitemIdAndUserId(lineitem,user,verbosity);
    }

    @WebMethod
    public Boolean bbGroupAdd(@WebParam(name = "pwd") String pwd, @WebParam(name = "group") BBGroup group, @WebParam(name = "course") BBCourse course, @WebParam(name = "") String descriptionTextType) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGroupAdd");
        return groupAdd(group,course,descriptionTextType);
    }

    @WebMethod
    public Boolean bbGroupDelete(@WebParam(name = "pwd") String pwd, @WebParam(name = "group") BBGroup group, @WebParam(name = "course") BBCourse course, @WebParam(name = "") String descriptionTextType) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGroupDelete");
        return groupDelete(group);
    }

    @WebMethod
    public BBGroup bbGroupRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "group") BBGroup group) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGroupRead");
        return groupRead(group);
    }

    @WebMethod
    public boolean bbGroupUpdate(@WebParam(name = "pwd") String pwd, @WebParam(name = "group") BBGroup group, @WebParam(name = "descriptionTextType") String descriptionTextType) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGroupUpdate");
        return groupUpdate(group, descriptionTextType);
    }

    @WebMethod
    public List<BBGroup> bbGroupReadByCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGroupReadByCourseId");
        return groupReadByCourseId(course);
    }

    @WebMethod
    public List<BBGroupMembership> bbGroupMembershipReadByGroupId(@WebParam(name = "pwd") String pwd, @WebParam(name = "group") BBGroup group) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGroupMembershipReadByGroupId");
        return groupMembershipReadByGroupId(group);
    }

    @WebMethod
    public boolean bbGroupMembershipCreateByUserIdAndGroupId(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user, @WebParam(name = "group") BBGroup group) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGroupMembershipCreateByUserIdAndGroupId");
        return groupMembershipCreateByUserIdAndGroupId(user,group);
    }

    @WebMethod
    public boolean bbGroupMembershipDeleteByUserIdAndGroupId(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user, @WebParam(name = "group") BBGroup group) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGroupMembershipCreateByUserIdAndGroupId");
        return groupMembershipDeleteByUserIdAndGroupId(user,group);
    }

    @WebMethod
    public BBGradeCentreSettings bbGradeCentreSettingsRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbGradeCentreScoreReadSearchByLineitemId");
        return gradeCentreSettingsRead(course);
    }

    @WebMethod
    public List<BBRole> bbRoleSecondaryPortalReadSearchByUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbRoleSecondaryPortalReadSearchByUserId");
        return roleSecondaryPortalReadSearchByUserId(user);
    }

    @WebMethod
    public Boolean bbRoleSecondaryPortalUpdate(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user, @WebParam(name = "roles") List<BBRole> roles) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbRoleSecondaryPortalUpdate");
        return roleSecondaryPortalUpdate(user,roles);
    }

    @WebMethod
    public List<BBRole> bbRoleSecondarySystemReadSearchByUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbRoleSecondarySystemReadSearchByUserId");
        return roleSecondarySystemReadSearchByUserId(user);
    }

    @WebMethod
    public BBRole bbRoleUserReadSearchByUserIdAndCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user, @WebParam(name = "course") BBCourse course) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbUserReadSearchByUserIdAndCourseId");
        return roleUserReadSearchByUserIdAndCourseId(user,course);
    }

    @WebMethod
    public Boolean bbUserCreate(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user, @WebParam(name = "portalRole") BBRole portalRole, @WebParam(name = "secondaryPortalRoles") List<BBRole> secondaryPortalRoles, @WebParam(name = "systemRole") BBRole systemRole) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbUserCreate");
        return userCreateOrUpdate(user,portalRole,secondaryPortalRoles,systemRole,false);
    }

    @WebMethod
    public Boolean bbUserDelete(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user)
    {
        authoriseMethodUse(pwd,"bbUserDelete");
        return userDelete(user);
    }

    @WebMethod
    public BBUser bbUserRead(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user, @WebParam(name = "vebrosity") BBUser.BBUserVerbosity verbosity) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbUserRead");
        return userRead(user,verbosity);
    }

    @WebMethod
    public List<BBUser> bbUserReadAll(@WebParam(name = "pwd") String pwd, @WebParam(name = "vebrosity") BBUser.BBUserVerbosity verbosity) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbUserReadAll");
        return userReadAll(verbosity);
    }

    @WebMethod
    public List<BBUser> bbUserReadSearchByCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course, @WebParam(name = "verbosity") BBUser.BBUserVerbosity verbosity) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbUserByCourseId");
        return userReadSearchByCourseId(course,verbosity);
    }

    @WebMethod
    public List<BBUser> bbUserReadSearchByCourseIdAndCMRole(@WebParam(name = "pwd") String pwd, @WebParam(name = "course") BBCourse course, @WebParam(name = "") BBCourseMembershipRole cmRole, @WebParam(name = "verbosity") BBUser.BBUserVerbosity verbosity) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbUserByCourseIdAndCMRole");
        return userReadSearchByCourseIdAndCMRole(course,cmRole,verbosity);
    }

    @WebMethod
    public Boolean bbUserUpdate(@WebParam(name = "pwd") String pwd, @WebParam(name = "user") BBUser user, @WebParam(name = "portalRole") BBRole portalRole, @WebParam(name = "secondaryPortalRoles") List<BBRole> secondaryPortalRoles, @WebParam(name = "systemRole") BBRole systemRole) throws WebServiceException
    {
        authoriseMethodUse(pwd,"bbUserAdd");
        return userCreateOrUpdate(user,portalRole,secondaryPortalRoles,systemRole,true);
    }

    private String checkAndTrimParam(String courseDetail) throws Exception
    {
	if(courseDetail!=null && !courseDetail.equalsIgnoreCase(""))
	{
	    return courseDetail.trim();
	}
	throw new Exception("Invalid course detail: '"+courseDetail+"'");
    }

    private Boolean checkParam(String param)
    {
	if(param!=null && !param.equalsIgnoreCase(""))
	{
	    return true;
	}
	return false;
    }

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

    private boolean courseCreate(BBCourse course)
    {
        try
        {
            CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseId());
            throw new WebServiceException("Error: Course may already exist");
        }
        catch(KeyNotFoundException knfe){}
        catch(Exception e)
        {
            throw new WebServiceException("Error while trying to check if course already exists: "+e.toString()+" "+e.getMessage());
        }

        Course c = new Course();
        try
        {
            c.setBatchUid(course.getBatchUId());
            c.setCourseId(course.getCourseId());
            c.setDescription(course.getDescription());
            c.setTitle(course.getTitle());
            c.setAllowGuests(course.getAllowGuests());
            c.setAllowObservers(course.getAllowObservers());
            c.setIsAvailable(course.getAvailable());
            CourseDbPersister.Default.getInstance().persist(c);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while trying to add course: "+e.getMessage());
        }
        return true;
    }

    private boolean courseDelete(BBCourse course)
    {
        String error = "";
        try
        {
            if(checkParam(course.getCourseId()))
            {
                CourseDbPersister.Default.getInstance().deleteById(CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseId()).getId());
                return true;
            }
            else if(checkParam(course.getCourseBbId()))
            {
                CourseDbPersister.Default.getInstance().deleteById(CourseDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Course.DATA_TYPE,course.getCourseBbId())).getId());
                return true;
            }
            error = "You must specify either courseId or courseBBId";
        }
        catch(KeyNotFoundException knfe)
        {
            error = "No matching course";
        }
        catch(Exception e)
        {
            error = "Error whilst deleting course: "+e.toString();
        }
        throw new WebServiceException(error);
    }

    private Boolean courseMembershipCreate(BBCourseMembership courseMembership) throws WebServiceException
    {
        Id uid = null;
        Id cid = null;

        try
        {
            uid = UserDbLoader.Default.getInstance().loadByUserName(courseMembership.getUser().getUserName()).getId();
        }
        catch(Exception e)
        {
            throw new WebServiceException("Please provide a valid username "+e.toString());
        }

        try
        {
            cid = CourseDbLoader.Default.getInstance().loadByCourseId(courseMembership.getCourse().getCourseId()).getId();
        }
        catch(Exception e)
        {
            throw new WebServiceException("Please provide a valid courseId "+e.toString());
        }

        try
        {
            CourseMembership cm = new CourseMembership();
            cm.setCourseId(cid);
            cm.setUserId(uid);
            cm.setIsAvailable(courseMembership.getAvailable());
            cm.setRole(Role.fromFieldName(courseMembership.getRole().name()));
            CourseMembershipDbPersister.Default.getInstance().persist(cm);
        }
        catch(IllegalArgumentException iae)
        {
            throw new WebServiceException("Problem while trying to set role, does role exist? "+iae.toString());
        }
        catch(PersistenceException e)
        {
            throw new WebServiceException("Enrollment may already exist?");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Problem while trying to update coursemembership details "+e.toString());
        }
        return true;
    }

    private Boolean courseMembershipDelete(BBCourseMembership courseMembership) throws WebServiceException
    {
        Course c = null;
        User u = null;
        CourseMembership cm = null;

        if(checkParam(courseMembership.getCourseMembershipBbId()))
        {
            try
            {
                //get enrollment id
                cm = CourseMembershipDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(CourseMembership.DATA_TYPE, courseMembership.getCourseMembershipBbId()));
            }
            catch(Exception e)
            {
                throw new WebServiceException("Error: Given courseMembership doesn't seem to exist "+e.getMessage());
            }
        }
        else
        {
            try
            {
                c = CourseDbLoader.Default.getInstance().loadByCourseId(courseMembership.getCourse().getCourseBbId());
            }
            catch(Exception e)
            {
                throw new WebServiceException("Error: Course Id is invalid or does not exist "+e.getMessage());
            }

            try
            {
                u = UserDbLoader.Default.getInstance().loadByUserName(courseMembership.getUser().getUserName());
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

    private BBCourseQuota courseQuotaRead(BBCourse course) throws WebServiceException
    {
        try
        {
            return new BBCourseQuota(CourseQuota.createInstance(CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseId())));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not find course with that Id "+e.getMessage()+" "+e.toString());
        }
    }

    private Boolean courseQuotaUpdate(BBCourse course, BBCourseQuota courseQuota) throws WebServiceException
    {
        if(courseQuota.getEnforceQuota()!=null || courseQuota.getEnforceUploadLimit()!=null || courseQuota.getSystemUploadLimit()!=null || courseQuota.getSystemSoftLimit()!=null || courseQuota.getSystemAbsoluteLimit()!=null)
        {
            Course c = null;
            try
            {
                c = CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseId());
            }
            catch(Exception e)
            {
                throw new WebServiceException("Error: Could not load course to modify quota for, does it exist? "+e.getMessage());
            }

            try
            {
                setOrModifyCourseRegistryValue(c.getId(),"quota_override",courseQuota.getEnforceQuota()?"Y":"N");
                setOrModifyCourseRegistryValue(c.getId(),"quota_upload_override",courseQuota.getEnforceUploadLimit()?"Y":"N");

                if(courseQuota.getCourseAbsoluteLimit()!=null)
                {
                    c.setAbsoluteLimit(courseQuota.getCourseAbsoluteLimit());
                }

                if(courseQuota.getCourseSoftLimit()!=null)
                {
                    c.setSoftLimit(courseQuota.getCourseSoftLimit());
                }

                if(courseQuota.getCourseUploadLimit()!=null)
                {
                    c.setUploadLimit(courseQuota.getCourseUploadLimit());
                }

                CourseDbPersister.Default.getInstance().persist(c);
            }
            catch(Exception e)
            {
                throw new WebServiceException( "Error: Could not modify course quota settings - "+e.getMessage());
            }
            return true;
        }
        return false;
    }

    private BBCourseMembership courseMembershipRead(BBCourseMembership courseMembership, BBCourseMembership.BBCourseMembershipVerbosity verbosity) throws WebServiceException
    {
        String error = "";
        try
        {
            if(checkParam(courseMembership.getUser().getBbId()) && checkParam(courseMembership.getCourse().getCourseBbId()))
            {
                //return new BBCourse(CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseId()),BBCourse.Verbosity.extended);
                return new BBCourseMembership(CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(CourseDbLoader.Default.getInstance().loadByCourseId(courseMembership.getCourse().getCourseBbId()).getId(),UserDbLoader.Default.getInstance().loadByUserName(courseMembership.getUser().getBbId()).getId()),verbosity);
            }
            else if(checkParam(courseMembership.getCourseMembershipBbId()))
            {
                //return new BBCourse(CourseDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Course.DATA_TYPE, course.getCourseBbId())),BBCourse.Verbosity.extended);
                return new BBCourseMembership(CourseMembershipDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(CourseMembership.DATA_TYPE, courseMembership.getCourseMembershipBbId())),verbosity);
            }
            error = "You must specify either user with userId and course with courseId, or, a courseMembershipBbId";
        }
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
    {
        try
        {
            return new BBCourseMembership(CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseId()).getId(),UserDbLoader.Default.getInstance().loadByUserName(user.getUserName()).getId(),null,loadUser),verbosity);
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
        }
    }

    private BBCourse courseRead(BBCourse course, BBCourse.BBCourseVerbosity verbosity) throws WebServiceException
    {
        String error = "";
        try
        {
            if(course.getCourseId()!=null && !course.getCourseId().equalsIgnoreCase(""))
            {
                return new BBCourse(CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseId()),verbosity);
            }
            else if(course.getCourseBbId()!=null && !course.getCourseBbId().equalsIgnoreCase(""))
            {
                return new BBCourse(CourseDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Course.DATA_TYPE, course.getCourseBbId())),verbosity);
            }
            error = "You must specify either courseId or courseBBId";
        }
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
    {
        try
        {
            return convertCourseListToBBCourseList(CourseDbLoader.Default.getInstance().loadAllCourses(),verbosity);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error whilst searching to see if course exists: "+e.toString());
        }
    }

    private List<BBCourse> courseReadSearchByUserIdAndCMRole(BBUser user, BBCourseMembershipRole cmRole, BBCourse.BBCourseVerbosity verbosity) throws WebServiceException
    {
        try
        {
            return convertCourseListToBBCourseList(CourseDbLoader.Default.getInstance().loadByUserIdAndCourseMembershipRole(UserDbLoader.Default.getInstance().loadByUserName(user.getUserName()).getId(),CourseMembership.Role.fromExternalString(cmRole.name())),verbosity);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error whilst searching to see if course exists: "+e.toString());
        }
    }

    private List<BBCourse> courseReadSearchByRegex(String regex, BBCourse.BBCourseVerbosity verbosity) throws WebServiceException
    {
        List<BBCourse> rl = new ArrayList<BBCourse>();
        List<Course> cl = null;
        try
        {
            cl = CourseDbLoader.Default.getInstance().loadAllCourses();
        }
        catch(Exception e)
        {
            throw new WebServiceException("Could not load all courses to search: "+e.getMessage());
        }

        if(cl!=null && cl.size()>0)
        {
            Pattern pattern =  Pattern.compile(regex);
            Course c = null;
            Iterator i = cl.iterator();

            while(i.hasNext())
            {
                c = ((Course)i.next());
                if(isAMatch(pattern,c.getCourseId()))
                {
                    //The verbosity exception here "should" NEVER happen
                    try{rl.add(new BBCourse(c,verbosity));}catch(Exception e){System.err.println("Error while instantiating course "+c.getCourseId()+": "+e.getMessage());}
                }
            }

            if(rl.size()<1)
            {
                throw new WebServiceException("No matches found");
            }
            return rl;
        }
        throw new WebServiceException("No courses found");
    }

    private List<BBEnrollment> enrollmentReadSearchByUserId(BBUser user) throws WebServiceException
    {
        List<BBEnrollment> rl = new ArrayList<BBEnrollment>();
        try
        {
            Enrollment enrollment = new Enrollment();
            enrollment.setPersonBatchUid(user.getUserName());
            List<Enrollment> membershipList = EnrollmentLoader.Default.getInstance().load(enrollment);
            if(membershipList.size()>1)
            {
                Iterator i = membershipList.iterator();
                while(i.hasNext())
                {
                    rl.add(new BBEnrollment((Enrollment)i.next()));
                }
            }
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
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

    private boolean gradeCentreAttemptDelete(BBAttempt attempt) throws WebServiceException
    {
        try
        {
            AttemptDbPersister.Default.getInstance().deleteById(AttemptDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Attempt.DATA_TYPE,attempt.getAttemptBbId())).getId());
        }
        catch(Exception e)
        {
            throw new WebServiceException( "Error: Could not delete attempt - "+e.getMessage());
        }
        return true;
    }

    private List<BBAttempt> gradeCentreAttemptReadSearchByOutcomeDefinitionId(BBOutcomeDefinition outcomeDef, BBAttempt.BBAttemptVerbosity verbosity) throws WebServiceException
    {
        List<Attempt> al = null;
        try
        {
            al = AttemptDbLoader.Default.getInstance().loadByOutcomeDefinitionId(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(OutcomeDefinition.DATA_TYPE,outcomeDef.getOutcomeDefinitionBbId()));
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
        }
        List<BBAttempt> rl = new ArrayList<BBAttempt>();
        Iterator<Attempt> i = al.iterator();
        while(i.hasNext())
        {
            try{rl.add(new BBAttempt(i.next(),verbosity));}catch(Exception e){}
        }
        return rl;
    }

    private boolean gradeCentreLineitemAdd(BBLineitem lineitem, BBCourse course) throws WebServiceException
    {
        try
        {
            Lineitem li = new Lineitem();
            li.setCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseId()).getId());
            //li.setAssessmentLocation(Lineitem.AssessmentLocation.INTERNAL);
            li.setName(lineitem.getName());
            li.setIsAvailable(lineitem.getAvailable());
            li.setPointsPossible(lineitem.getPointsPossible());
            li.setType(lineitem.getType());
            li.setWeight(lineitem.getWeight());
            ((LineitemDbPersister)BbServiceManager.getPersistenceService().getDbPersistenceManager().getPersister(LineitemDbPersister.TYPE)).persist(li);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not add lineitem "+e.toString());
        }
        return true;
    }

    private boolean gradeCentreLineitemOrOutcomeDefinitionDelete(String Id)
    {
    	try
        {
            OutcomeDefinitionDbPersister.Default.getInstance().deleteById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(OutcomeDefinition.DATA_TYPE,Id));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Does that item/outcome exist? "+e.toString());
        }
        return true;
    }

    private BBLineitem gradeCentreLineitemRead(BBLineitem lineitem) throws WebServiceException
    {
        try
        {
            return new BBLineitem(((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE, lineitem.getLineItemBbId())));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve lineitem details "+e.toString());
        }
    }

    private List<BBLineitem> gradeCentreLineitemReadSearchByCourseId(BBCourse course) throws WebServiceException
    {
        List<BBLineitem> rl = new ArrayList<BBLineitem>();
        //try{System.err.println(new blackboard.platform.plugin.PlugInManager().getPlatformVersion().toString());}catch(Exception e){System.err.println("Could not retrieve version info");}
        //if(true) - this should be a version check instead of (just) a try/catch block
        //{
            //try
            //{
            //    System.out.println("test");
            //    Class gradebookManagerFactoryClass = Class.forName("blackboard.platform.gradebook2.GradebookManagerFactory");
            //    Object gradebookManager = gradebookManagerFactoryClass.getDeclaredMethod("getInstanceWithoutSecurityCheck").invoke(gradebookManagerFactoryClass.newInstance());
                //blackboard.platform.gradebook2.GradebookManager gm = blackboard.platform.gradebook2.GradebookManagerFactory.getInstanceWithoutSecurityCheck();

            //    Class gbmClass = gradebookManager.getClass();
            //   List<blackboard.platform.gradebook2.GradableItem> l = (List)gbmClass.getDeclaredMethod("getGradebookItems",new Class[]{blackboard.persist.Id.class}).invoke(gradebookManager,new Object[]{((CourseDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(CourseDbLoader.TYPE)).loadByCourseId(courseId).getId()});
                //List<blackboard.platform.gradebook2.GradableItem> l = gm.getGradebookItems(((CourseDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(CourseDbLoader.TYPE)).loadByCourseId(courseId).getId());

            //    Iterator<blackboard.platform.gradebook2.GradableItem> i = l.iterator();
            //    System.out.println(l.size());
            //    while (i.hasNext())
            //    {
            //        blackboard.platform.gradebook2.GradableItem gi = i.next();
            //        System.out.println(gi.isVisibleInAllTerms());
            //        BBLineitem lids = null;
            //        try
            //        {
            //            lids = new BBLineitem((Object)gi);
            //        }
            //        catch(NullPointerException npe)
            //        {
            //            System.out.println(npe.getMessage());
            //        }
            //        System.out.println(lids.getAvailable());
            //        rl.add(new BBLineitem(i.next()));
            //    }
            //    System.out.println(rl.size());
                //or you will get an out of memory error...
            //    l.clear();
            //    l = null;
            //    System.out.println(rl.size());
            //    System.out.println("test1");
            //}
            //catch(ClassNotFoundException cnfe)
            //{
            try
            {
                //System.out.println("test2");
                List<Lineitem> lil = ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseId()).getId());
                if(lil.size()<1)
                {
                    throw new Exception("No lineitems found");
                }
                Iterator<Lineitem> i = lil.iterator();
                while(i.hasNext())
                {
                    rl.add(new BBLineitem(i.next()));
                }
                //System.out.println("test3");
            }
            catch(Exception e)
            {
                throw new WebServiceException(e.getMessage());
            }
            //}
        //}
        return rl;
    }

    private List<BBOutcome> gradeCentreOutcomeReadSearchByOutcomeDefinitionId(BBOutcomeDefinition outcomeDef) throws WebServiceException
    {
        List<BBOutcome> rl = new ArrayList<BBOutcome>();
        List<Outcome> ol = null;
        try
        {
            ol = Arrays.asList(((OutcomeDefinitionDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(OutcomeDefinitionDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(OutcomeDefinition.DATA_TYPE,outcomeDef.getOutcomeDefinitionBbId())).getOutcomes());
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
        }
        if(ol==null || ol.isEmpty())
        {
            throw new WebServiceException("No outcomes found for outcomeDefBbId");
        }
        Iterator<Outcome> i = ol.iterator();
        while(i.hasNext())
        {
            rl.add(new BBOutcome(i.next()));
        }
        return rl;
    }

    private BBOutcomeDefinition gradeCentreOutcomeDefinitionRead(BBOutcomeDefinition outcomeDef) throws WebServiceException
    {
        try
        {
            return new BBOutcomeDefinition(OutcomeDefinitionDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(OutcomeDefinition.DATA_TYPE,outcomeDef.getOutcomeDefinitionBbId())));
        }
        catch(KeyNotFoundException e)
        {
            throw new WebServiceException("Error: No outcomeDef found. Does outcomeDef exist?");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve outcomeDefinition details "+e.toString());
        }
    }

    private List<BBOutcomeDefinition> gradeCentreOutcomeDefinitionReadSearchByCourseId(BBCourse course) throws WebServiceException
    {
        List<OutcomeDefinition> ods = null;
        try
        {
            ods = ((OutcomeDefinitionDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(OutcomeDefinitionDbLoader.TYPE)).loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseId()).getId());
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
        }
        if(ods==null || ods.size()<1)
        {
            throw new WebServiceException("No outcome defs found");
        }
        List<BBOutcomeDefinition> rl = new ArrayList<BBOutcomeDefinition>();
        Iterator<OutcomeDefinition> i = ods.iterator();
        while(i.hasNext())
        {
            rl.add(new BBOutcomeDefinition(i.next()));
        }
        return rl;
    }

    private BBOutcome gradeCentreOutcomeRead(BBOutcome outcome) throws WebServiceException
    {
        try
        {
            return new BBOutcome(OutcomeDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Outcome.DATA_TYPE, outcome.getOutcomeBbId())));
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

    private BBScore gradeCentreScoreRead(BBScore score, BBScore.BBScoreVerbosity verbosity) throws WebServiceException
    {
        try
        {
            return new BBScore(((ScoreDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(ScoreDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Score.SCORE_DATA_TYPE,score.getScoreBbId())),verbosity);
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

    private List<BBScore> gradeCentreScoreReadSearchByLineitemId(BBLineitem lineitem, BBScore.BBScoreVerbosity verbosity) throws WebServiceException
    {
        List<Score> scores = null;
        try
        {
            scores = ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineitem.getLineItemBbId())).getScores();
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
        }
        if(scores==null || scores.size()<1)
        {
            throw new WebServiceException("No scores found");
        }
        List<BBScore> rl = new ArrayList<BBScore>();
        Iterator<Score> i = scores.iterator();
        while(i.hasNext())
        {
            rl.add(new BBScore(i.next(),verbosity));
        }
        return rl;
    }

    private BBScore gradeCentreScoreReadSearchByLineitemIdAndUserId(BBLineitem lineitem, BBUser user, BBScore.BBScoreVerbosity verbosity) throws WebServiceException
    {
        try
        {
            return new BBScore
            (
                getScoreObjForGivenCourseMembershipBbIdAndLineItemBbId
                (
                    CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId
                    (
                        ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineitem.getLineItemBbId())).getCourseId(),
                        UserDbLoader.Default.getInstance().loadByUserName(user.getUserName()).getId()
                    ).getId().toExternalString(),
                    lineitem.getLineItemBbId()
                ),
                verbosity
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

    private BBScore gradeCentreScoreReadSearchByLineitemIdAndCourseMembershipId(BBLineitem lineitem, BBCourseMembership courseMembership, BBScore.BBScoreVerbosity verbosity) throws WebServiceException
    {
        try
        {
            return new BBScore(getScoreObjForGivenCourseMembershipBbIdAndLineItemBbId(courseMembership.getCourseMembershipBbId(), lineitem.getLineItemBbId()),verbosity);
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

    private BBGradeCentreSettings gradeCentreSettingsRead(BBCourse course) throws WebServiceException
    {
        try
        {
            return new BBGradeCentreSettings(GradeBookSettingsDbLoader.Default.getInstance().loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseBbId()).getId()));
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
        }
    }

    private boolean groupAdd(BBGroup group, BBCourse course, String descType) throws WebServiceException
    {
        try
        {
            Group g = new Group();
            g.setCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseBbId()).getId());
            g.setDescription(new FormattedText(group.getDescription(),Type.fromFieldName(descType.trim().toUpperCase())));
            g.setIsAvailable(group.getAvailable());
            g.setIsChatRoomAvailable(group.getChatRoomsAvailable());
            g.setIsDiscussionBoardAvailable(group.getDiscussionBoardsAvailable());
            g.setIsEmailAvailable(group.getEmailAvailable());
            g.setIsTransferAreaAvailable(group.getTransferAreaAvailable());
            g.setTitle(group.getTitle());
            GroupDbPersister.Default.getInstance().persist(g);
        }
        catch(Exception e)
        {
            //return "Invalid description formatted text type, try: HTML/PLAIN_TEXT/SMART_TEXT";
            throw new WebServiceException("Error while trying to add group "+e.toString());
        }
        return true;
    }

    private boolean groupDelete(BBGroup group) throws WebServiceException
    {
        try
        {
            GroupDbPersister.Default.getInstance().deleteById(GroupDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,group.getGroupBbId())).getId());
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Please provide a valid Id for a group");
        }
        return true;
    }

    private List<BBGroup> groupReadByCourseId(BBCourse course) throws WebServiceException
    {
        List<BBGroup> rl = new ArrayList<BBGroup>();
        List<Group> gl = null;
        try
        {
            GroupDbLoader.Default.getInstance().loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseId()).getId());
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
        }
        if(gl==null || gl.size()<1)
        {
            throw new WebServiceException("No groups found");
        }
        Iterator<Group> i = gl.iterator();
        while(i.hasNext())
        {
            rl.add(new BBGroup(i.next()));
        }
        return rl;
    }

    private BBGroup groupRead(BBGroup group) throws WebServiceException
    {
        try
        {
            return new BBGroup(GroupDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,group.getGroupBbId())));
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

    private boolean groupUpdate(BBGroup group, String descType) throws WebServiceException
    {
        try
        {
            Group g = GroupDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,group.getGroupBbId()));
            g.setDescription(new FormattedText(group.getDescription(),Type.fromFieldName(descType)));
            g.setIsAvailable(group.getAvailable());
            g.setIsChatRoomAvailable(group.getChatRoomsAvailable());
            g.setIsDiscussionBoardAvailable(group.getDiscussionBoardsAvailable());
            g.setIsEmailAvailable(group.getEmailAvailable());
            g.setIsTransferAreaAvailable(group.getTransferAreaAvailable());
            g.setTitle(group.getTitle());
            GroupDbPersister.Default.getInstance().persist(g);
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
        }
        return true;
    }

    private List<BBGroupMembership> groupMembershipReadByGroupId(BBGroup group) throws WebServiceException
    {
        List<BBGroupMembership> rl = new ArrayList<BBGroupMembership>();
        List<GroupMembership> gml = null;
        try
        {
            gml = GroupMembershipDbLoader.Default.getInstance().loadByGroupId(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,group.getGroupBbId()));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: "+e.toString());
        }
        if(gml==null || gml.size()<1)
        {
            throw new WebServiceException("No group members found");
        }
        Iterator<GroupMembership> i = gml.iterator();
        while(i.hasNext())
        {
            try{rl.add(new BBGroupMembership(i.next()));}catch(Exception e){}
        }
        return rl;
    }
    
    private boolean groupMembershipCreateByUserIdAndGroupId(BBUser user, BBGroup group) throws WebServiceException
    {
        try
        {
            Group g = GroupDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,group.getGroupBbId()));
            GroupMembership gm = new GroupMembership();
            gm.setCourseMembershipId(CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(g.getCourseId(),UserDbLoader.Default.getInstance().loadByUserName(user.getBbId()).getId()).getId());
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

    private boolean groupMembershipDeleteByUserIdAndGroupId(BBUser user, BBGroup group) throws WebServiceException
    {
        try
        {
            GroupMembershipDbPersister.Default.getInstance().deleteById(GroupMembershipDbLoader.Default.getInstance().loadByGroupAndUserId(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,group.getGroupBbId()),UserDbLoader.Default.getInstance().loadByUserName(user.getBbId()).getId()).getId());
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

    private List<PortalRole> parseSecondaryPortalRoles(List<BBRole> roles) throws WebServiceException
    {
        List<PortalRole> pRoles = new ArrayList<PortalRole>();
        try
        {
            PortalRoleDbLoader prl = PortalRoleDbLoader.Default.getInstance();
            Iterator<BBRole> i = roles.iterator();
            BBRole r = null;
            while(i.hasNext())
            {
                r = i.next();
                if(r.getRoleId()!=null && !r.getRoleId().trim().equalsIgnoreCase(""))
                {
                    pRoles.add(prl.loadByRoleId(r.getRoleId().trim()));
                }
            }
        }
        catch(Exception e)
        {
            throw new WebServiceException("Invalid role specified "+e.toString());
        }
        return pRoles;
    }

    private List<BBRole> roleSecondaryPortalReadSearchByUserId(BBUser user) throws WebServiceException
    {
        List<BBRole> prl = new ArrayList<BBRole>();
        try
        {
            List<PortalRole> rl = PortalRoleDbLoader.Default.getInstance().loadSecondaryRolesByUserId(UserDbLoader.Default.getInstance().loadByUserName(user.getUserName()).getId());
            Iterator<PortalRole> i = rl.iterator();

            while(i.hasNext())
            {
                prl.add(new BBRole(i.next()));
            }
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
        }
        return prl;
    }

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
            while(i.hasNext())
            {
                ur = new UserRole();
                ur.setUser(u);
                pr = i.next();

                if(!pr.getRoleID().equalsIgnoreCase(priPR.getRoleID()))
                {
                    ur.setPortalRoleId(pr.getId());
                    prstr.persist(ur);
                }
            }
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
        }
        return true;
    }

    private List<BBRole> roleSecondarySystemReadSearchByUserId(BBUser user) throws WebServiceException
    {
        List<BBRole> srl = new ArrayList<BBRole>();
        try
        {
	    List<SystemRole> rl = DomainManagerFactory.getInstance().getDefaultDomainRolesForUser(user.getUserName());
            Iterator<SystemRole> i = rl.iterator();

            while(i.hasNext())
            {
                srl.add(new BBRole(i.next()));
            }
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
        }
        return srl;
    }

    private BBRole roleUserReadSearchByUserIdAndCourseId(BBUser user, BBCourse course) throws WebServiceException
    {
        try
        {
            return new BBRole(CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseId()).getId(),UserDbLoader.Default.getInstance().loadByUserName(user.getUserName()).getId()));
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
        }
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

    private Boolean setOrModifySecondaryPortalRolesForGivenUserId(String userId, List<PortalRole> roles) throws WebServiceException
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

    private Boolean userCreateOrUpdate(BBUser user,BBRole portalRole,List<BBRole> secPortalRoles,BBRole systemRole,Boolean isUpdate) throws WebServiceException
    {
        Person p = null;
        if(isUpdate)
        {
            try
            {
                p = PersonDbLoader.Default.getInstance().load(user.getUserName());
            }
            catch(KeyNotFoundException knfe)
            {
                //We need the user to exist
                throw new WebServiceException("User "+user.getUserName()+" does not exist");
            }
            catch(Exception e)
            {
                //return "Error while trying to check if user already exists: "+e;
                throw new WebServiceException(e.toString()+": "+e.getMessage());
            }

        }
        else
        {
            try
            {
                PersonDbLoader.Default.getInstance().load(user.getUserName());
                //return "Error: User may already exist";
                throw new WebServiceException("User may already exist");
            }
            catch(KeyNotFoundException knfe){} //We need the user to not exist
            catch(Exception e)
            {
                //return "Error while trying to check if user already exists: "+e;
                throw new WebServiceException(e.getMessage());
            }
            p = new Person();
        }
        List<PortalRole> secPRoles = null;
        String debug = "setting user name";
        try
	{
            p.setUserName(checkAndTrimParam(user.getUserName()));//userid
            debug = "setting batch uid";
            p.setBatchUid(checkAndTrimParam(user.getBatchUserBbId()));//batchuid
            debug = "setting given name";
            p.setGivenName(checkAndTrimParam(user.getGivenName()));//firstname
            debug = "setting middle name";
            try{p.setMiddleName(checkAndTrimParam(user.getMiddleName()));}catch(Exception e){}//middlename - Catch Exception as it's not a mandatory field
            debug = "setting family name";
            p.setFamilyName(checkAndTrimParam(user.getFamilyName()));//lastname
            debug = "setting email address";
            p.setEmailAddress(user.getEmailAddress());//emailaddress
            debug = "setting student id";
            try{p.setStudentId(checkAndTrimParam(user.getStudentId()));}catch(Exception e){}//studentid - Catch Exception as it's not a mandatory field
            debug = "setting password";
            p.setPassword(SecurityUtil.getHashValue(user.getPassword()));//password - The password in blackboard is irrelevant if you're using ldap
            debug = "setting gender";
            try//gender - Is this working?
            {
                p.setGender(Gender.fromExternalString(user.getGender().trim().toUpperCase()));
            }
            catch(Exception e)
            {
                p.setGender(Gender.UNKNOWN);
            }
            debug = "setting birthdate";
            try//birthdate
            {
                p.setBirthDate(new GregorianCalendar(Integer.parseInt(user.getBirthDate().substring(0,3)),Integer.parseInt(user.getBirthDate().substring(5, 7))-1,Integer.parseInt(user.getBirthDate().substring(9, 11))));
            }catch(Exception e){}
            debug = "setting education level";
            try//Education Level
            {
                p.setEducationLevel(EducationLevel.fromExternalString(user.getEducationLevel().trim().toUpperCase()));
            }
            catch(Exception e)
            {
                p.setEducationLevel(EducationLevel.UNKNOWN);
            }
            debug = "setting company";
            try{p.setCompany(checkAndTrimParam(user.getCompany()));}catch(Exception e){}//Company - Catch Exception as it's not a mandatory field
            debug = "setting job title";
            try{p.setJobTitle(checkAndTrimParam(user.getJobTitle()));}catch(Exception e){}//Job Title - Catch Exception as it's not a mandatory field
            debug = "setting department";
            try{p.setDepartment(checkAndTrimParam(user.getDepartment()));}catch(Exception e){}//Department - Catch Exception as it's not a mandatory field
            debug = "setting street1";
            try{p.setStreet1(checkAndTrimParam(user.getStreet1()));}catch(Exception e){}//Street 1 - Catch Exception as it's not a mandatory field
            debug = "setting street2";
            try{p.setStreet2(checkAndTrimParam(user.getStreet2()));}catch(Exception e){}//Street 2 - Catch Exception as it's not a mandatory field
            debug = "setting city";
            try{p.setCity(checkAndTrimParam(user.getCity()));}catch(Exception e){}//City - Catch Exception as it's not a mandatory field
            debug = "setting state or province";
            try{p.setState(checkAndTrimParam(user.getStateOrProvince()));}catch(Exception e){}//State / Province - Catch Exception as it's not a mandatory field
            debug = "setting zip or post code";
            try{p.setZipCode(checkAndTrimParam(user.getPostCode()));}catch(Exception e){}//Zip / Postal Code - Catch Exception as it's not a mandatory field
            debug = "setting country";
            try{p.setCountry(checkAndTrimParam(user.getCountry()));}catch(Exception e){}//Country - Catch Exception as it's not a mandatory field
            debug = "setting website";
            try{p.setWebPage(checkAndTrimParam(user.getWebPage()));}catch(Exception e){}//Website - Catch Exception as it's not a mandatory field
            debug = "setting home phone";
            try{p.setHomePhone1(checkAndTrimParam(user.getHomePhone1()));}catch(Exception e){}//Home Phone - Catch Exception as it's not a mandatory field
            debug = "setting work phone";
            try{p.setHomePhone2(checkAndTrimParam(user.getHomePhone2()));}catch(Exception e){}//Work Phone - Catch Exception as it's not a mandatory field
            debug = "setting work fax";
            try{p.setHomeFax(checkAndTrimParam(user.getHomeFax()));}catch(Exception e){}//Work Fax - Catch Exception as it's not a mandatory field
            debug = "setting mobile phone";
            try{p.setMobilePhone(checkAndTrimParam(user.getMobilePhone()));}catch(Exception e){}//Mobile Phone - Catch Exception as it's not a mandatory field
            debug = "setting portal role";
            //Portal Role
            PortalRole pr = null;
            if(portalRole.getRoleId()!=null && !portalRole.getRoleId().equalsIgnoreCase(""))
            {
                portalRole.setRoleId(portalRole.getRoleId().trim());
                pr = PortalRoleDbLoader.Default.getInstance().loadByRoleId(portalRole.getRoleId());

            }
            else
            {
                pr = PortalRoleDbLoader.Default.getInstance().loadByRoleId("STUDENT");
            }
            p.setPortalRole(pr);
            debug = "setting system role";
            //System Role
            if(systemRole.getRoleId()!=null && !systemRole.getRoleId().equalsIgnoreCase(""))
            {
                p.setSystemRole(blackboard.data.user.User.SystemRole.fromFieldName(systemRole.getRoleId().trim().toUpperCase()));
            }
            else
            {
                p.setSystemRole(blackboard.data.user.User.SystemRole.NONE);
            }
            debug = "setting available";
            if(user.getIsAvailable()!=null){p.setIsAvailable(user.getIsAvailable());}else{throw new Exception("Invalid availability");}//Available
            debug = "setting row status";
            p.setRowStatus(RowStatus.ENABLED);
            debug = "setting rec status";
            p.setRecStatus(RecStatus.ADD);
            //p.setReplacementBatchUid(userId);
            debug = "checking secondary portal roles, number specified="+secPortalRoles.size();
            Iterator<BBRole> i = secPortalRoles.iterator();
            while(i.hasNext())
            {
                debug += ", "+i.next();
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
            if (isUpdate)
            {
                PersonDbPersister.Default.getInstance().update(p);
            }
            else
            {
                PersonDbPersister.Default.getInstance().insert(p);
            }
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
                setOrModifySecondaryPortalRolesForGivenUserId(user.getUserName(),secPRoles);
            }
            catch(Exception e)
            {
                throw new WebServiceException(e.toString());
            }
        }
        return true;
    }

    private boolean userDelete(BBUser user)
    {
        String error = "";
        try
        {
            if(checkParam(user.getUserName()))
            {
                UserDbPersister.Default.getInstance().deleteById(UserDbLoader.Default.getInstance().loadByUserName(user.getUserName()).getId());
                return true;
            }
            else if(checkParam(user.getBbId()))
            {
                UserDbPersister.Default.getInstance().deleteById(UserDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(User.DATA_TYPE,user.getBbId())).getId());
                return true;
            }
            error = "You must specify either userId or userBBId";
        }
        catch(KeyNotFoundException knfe)
        {
            error = "No matching user";
        }
        catch(Exception e)
        {
            error = "Error whilst deleting user: "+e.toString();
        }
        throw new WebServiceException(error);
    }

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
    {
        try
        {
	    Person p = new Person();
	    p.setBatchUid("%%");
            return convertUserListToBBUserList(PersonDbLoader.Default.getInstance().load(p),verbosity);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error whilst searching to see if course exists: "+e.toString());
        }
    }

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
    {
        List<BBUser> al = new ArrayList<BBUser>();
        try
        {
            List<CourseMembership> membershipList = CourseMembershipDbLoader.Default.getInstance().loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(course.getCourseId()).getId(),null,true);
            Iterator<CourseMembership> i = membershipList.iterator();
            User u = null;
            while(i.hasNext())
            {
                u = i.next().getUser();
                try{al.add(new BBUser(u,verbosity));}catch(Exception e){System.out.println("Error while instantiating user "+u.getUserName()+": "+e.getMessage());}
            }
        }
        catch(Exception e)
        {
            throw new WebServiceException(e.getMessage());
        }
        return al;
    }
}