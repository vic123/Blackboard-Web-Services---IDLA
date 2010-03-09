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
package bbaws;

//blackboard
import blackboard.base.FormattedText;
import blackboard.base.FormattedText.Type;
import blackboard.data.announcement.Announcement;
import blackboard.persist.announcement.AnnouncementDbLoader;
import blackboard.persist.announcement.AnnouncementDbPersister.Default;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.user.UserDbLoader;
import blackboard.platform.BbServiceManager;

//java
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

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

    private void authoriseMethodUse(String methodPwd, String methodName) throws WebServiceException
    {
        if(!wsp.isMethodAccessible(methodName) || (wsp.usingPassword() && !wsp.passwordMatches(methodPwd)))
        {
            throw new WebServiceException("Access Denied");
        }
    }

    private String checkAnnouncementDetail(String announcementDetail) throws Exception
    {
        if(announcementDetail!=null && !announcementDetail.trim().equalsIgnoreCase(""))
        {
            return announcementDetail.trim();
        }
        throw new Exception("Invalid announcement detail (cAD): '"+announcementDetail+"'");
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

    /**
     * Web service operation
     */
    @WebMethod
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

    /**
     * Web service operation
     */
    @WebMethod
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

    /**
     * Web service operation
     */
    @WebMethod
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