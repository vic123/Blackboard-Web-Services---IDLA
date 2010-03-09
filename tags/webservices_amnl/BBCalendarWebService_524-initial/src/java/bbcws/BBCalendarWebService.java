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
package bbcws;

import blackboard.base.FormattedText;
//import blackboard.data.registry.SystemRegistryEntry;
import blackboard.data.user.User;
//import blackboard.persist.KeyNotFoundException;
//import blackboard.persist.registry.SystemRegistryEntryDbLoader;
import blackboard.persist.user.UserDbLoader;
import blackboard.data.calendar.CalendarEntry;
import blackboard.persist.calendar.CalendarEntryDbLoader;
import blackboard.persist.calendar.CalendarEntryDbPersister;
import blackboard.data.course.Course;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.BbPersistenceManager;
import blackboard.platform.BbServiceManager;
import blackboard.persist.Id;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
@WebService(name="BBCalendarWebService", serviceName="BBCalendarWebService", targetNamespace="http://www.ncl.ac.uk/BBCalendarWebService")
public class BBCalendarWebService
{
        private WebServiceProperties wsp = new WebServiceProperties("amnl","BBCalendarWebService");

	 /**
	 * Web service operation
	 */
	@WebMethod
	public String addCalendarEntry(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseID") String courseId, @WebParam(name = "userID") String userId,
					@WebParam(name = "description") String description, @WebParam(name = "title") String title,
					@WebParam(name = "type") String type, @WebParam(name = "startDay") int startDay,
					@WebParam(name = "startMonth") int startMonth, @WebParam(name = "startYear") int startYear,
					@WebParam(name = "startHour") int startHour, @WebParam(name = "startMinute") int startMinute,
					@WebParam(name = "startSecond") int startSecond, @WebParam(name = "endDay") int endDay,
					@WebParam(name = "endMonth") int endMonth, @WebParam(name = "endYear") int endYear,
					@WebParam(name = "endHour") int endHour, @WebParam(name = "endMinute") int endMinute,
					@WebParam(name = "endSecond") int endSecond)
	{
	    if(!wsp.isMethodAccessible("addCalendarEntry") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	    {
		return "Error: Access denied to method";
	    }
	    //course - course,user,desc,title,type
	    //inst - user,desc,title,type (course must not be set? is this system wide calendar?)
	    //personal - user,desc,title,type (course must not be set)
	    
	    BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	    CalendarEntry.Type ceType = null;
	    Calendar startCal = null;
	    Calendar endCal = null;
	    Id cid  = null;
	    Id uid = null;

	    if(type!=null && !type.trim().equalsIgnoreCase(""))
	    {
		type = type.toUpperCase();

		if(type.equals("COURSE"))
		{
		    ceType = CalendarEntry.Type.COURSE;
		}
		else if(type.equals("INSTITUTION"))
		{
		    ceType = CalendarEntry.Type.INSTITUTION;
		}
		else if(type.equals("PERSONAL"))
		{
		    ceType = CalendarEntry.Type.PERSONAL;
		}
		else
		{
		    return "Error: Invalid calendar type - COURSE/INSTITUTION/PERSONAL";
		}
	    }

	    if(type.equals("COURSE"))
	    {
		try
		{
		    CourseDbLoader cl = (CourseDbLoader)bbPm.getLoader(CourseDbLoader.TYPE);
		    Course c = cl.loadByCourseId(courseId);
		    cid = c.getId();
		}
		catch(Exception e)
		{
		    return "Error: Invalid course";
		}
	    }
	    
	    try
	    {
		UserDbLoader ul = (UserDbLoader)bbPm.getLoader(UserDbLoader.TYPE);
		User u = ul.loadByUserName(userId);
		uid = u.getId();
	    }
	    catch(Exception e)
	    {
		return "Error: Invalid user";
	    }
	    
	    if(startDay!=0 && startMonth!=0 && startYear!=0)
	    {
		try
		{
		    //Remember the gc object month field is 0-11
		    startCal = new GregorianCalendar();
		    startCal.set(startYear,startMonth-1,startDay,startHour,startMinute,startSecond);
		}
		catch(Exception e)
		{
		    return "Error: Invalid starting date "+startDay+"/"+startMonth+"/"+startYear+" "+startHour+":"+startMinute+":"+startSecond;
		}
	    }
	    //Else start date is today, starting this hour.

	    if(endDay!=0 && endMonth!=0 && endYear!=0)
	    {
		try
		{
		    //Remember the gc object month field is 0-11
		    endCal = new GregorianCalendar();
		    endCal.set(endYear,endMonth-1,endDay,endHour,endMinute,endSecond);
		}
		catch(Exception e)
		{
		    return "Error: Invalid starting date "+endDay+"/"+endMonth+"/"+endYear+" "+endHour+":"+endMinute+":"+endSecond;
		}
	    }
	    //Else end date is +1 hour after start date???
    
	    try
	    {
		CalendarEntryDbPersister cep = (CalendarEntryDbPersister)bbPm.getPersister(CalendarEntryDbPersister.TYPE);
		CalendarEntry ce = new CalendarEntry();
		ce.setCourseId(cid);
		ce.setCreatorUserId(uid);
		ce.setDescription(new FormattedText(description,FormattedText.Type.PLAIN_TEXT));
		if(endCal!=null)
		{
		    ce.setEndDate(endCal);
		}
		//ce.setId();
		//ce.setModifiedDate();
		if(startCal!=null)
		{
		    ce.setStartDate(startCal);
		}
		ce.setTitle(title);
		//ce.setType(CalendarEntry.Type.fromExternalString(type));
		ce.setType(ceType);
		//Course Institution personal
		cep.persist(ce);
	    }
	    catch(Exception e)
	    {
		return "Error while trying to add calendar entry: "+e.toString();
	    }

	    return "OK";
	}
    
	/**
	 * Web service operation
	 */
	@WebMethod
	public String[][] getAllCourseCalendarEntriesForGivenCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "headerDesc") Boolean headerDesc)
	{
	    if(!wsp.isMethodAccessible("getAllCourseCalendarEntriesForGivenCourseId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	    {
		return new String[][]{{"Error"},{"Access denied to method"}};
	    }

	    BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	    String calendarEntries[][] = new String[][]{{"Error"},{"No calendar entries found for this course"}};

	    if(headerDesc==null)
	    {
		headerDesc=false;
	    }

	    try
	    {
		CalendarEntryDbLoader calLoad = (CalendarEntryDbLoader)bbPm.getLoader(CalendarEntryDbLoader.TYPE);
		CourseDbLoader cL = (CourseDbLoader)bbPm.getLoader(CourseDbLoader.TYPE);
		Course course = cL.loadByCourseId(courseId);
		ArrayList cal = calLoad.loadByCourseId(course.getId());
		calendarEntries = new String[cal.size()+1][];
		CalendarEntry calEnt = null;

		int j = 0;
		if(headerDesc)
		{
		    calendarEntries[0] = new String[]{"CalendarEntryBbId","Start Date","End Date","Description","External Type"};
		    j=1;
		}
		else
		{
		    calendarEntries = new String[cal.size()][];
		}

		for(int i=0; i<cal.size(); i++)
		{
		    calEnt = (CalendarEntry)cal.get(i);
		    calendarEntries[j] = getCalendarEntryDetails(calEnt);
		    j+=1;
		}
	    }
	    catch(Exception e)
	    {
		//calendarEntries[0] = new String[]{e.toString()};
	    }
	    finally
	    {
		return calendarEntries;
	    }
	}

	private String getDateTimeFromCalendar(Calendar cal)
	{
	    return cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
	}
	
	/**
	 *
	 * Web service operation
	 */
	@WebMethod
	public String[][] getCourseCalendarEntriesWithinDatesForGivenCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId,
					@WebParam(name = "startDay") int startDay, @WebParam(name = "startMonth") int startMonth,
					@WebParam(name = "startYear") int startYear, @WebParam(name = "endDay") int endDay,
					@WebParam(name = "endMonth") int endMonth, @WebParam(name = "endYear") int endYear,
					@WebParam(name = "headerDesc") Boolean headerDesc)
	{
	    if(!wsp.isMethodAccessible("getCourseCalendarEntriesWithinDatesForGivenCourseId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	    {
		return new String[][]{{"Error"},{"Access denied to method"}};
	    }

	    BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	    String calendarEntries[][] = new String[][]{{"Error"},{"No calendar entries found for this course"}};

	    if(headerDesc==null)
	    {
		headerDesc=false;
	    }

	    try
	    {
		CalendarEntryDbLoader calLoad = (CalendarEntryDbLoader)bbPm.getLoader(CalendarEntryDbLoader.TYPE);
		CourseDbLoader cL = (CourseDbLoader)bbPm.getLoader(CourseDbLoader.TYPE);
		Course course = cL.loadByCourseId(courseId); 
		Calendar startCal = new GregorianCalendar();
		startCal.setLenient(false);
		if(startDay!=0 && startMonth!=0 && startYear!=0)
		{
		    try
		    {
			//Remember the gc object month field is 0-11
			startCal.set(startYear,startMonth-1,startDay);
		    }
		    catch(Exception e)
		    {
			return new String[][]{{"Error"},{"Invalid starting date "+startDay+"/"+startMonth+"/"+startYear}};
		    }
		}
		//else today

		Calendar endCal = new GregorianCalendar();
		endCal.setLenient(false);
		if(endDay!=0 && endMonth!=0 && endYear!=0)
		{
		    try
		    {
			//Remember the gc object month field is 0-11
			endCal.set(endYear,endMonth-1,endDay);
		    }
		    catch(Exception e)
		    {
			return new String[][]{{"Error"},{"Invalid ending date "+endDay+"/"+endMonth+"/"+endYear}};
		    }
		}
		//else today

		ArrayList cal = calLoad.loadByCourseId(course.getId(),startCal,endCal);
		calendarEntries = new String[cal.size()+1][];
		CalendarEntry calEnt = null;

		int j=0;
		if(headerDesc)
		{
		    calendarEntries[0] = new String[]{"CalendarEntryBbId","Start Date","End Date","Description","External Type"};
		    j=1;
		}
		else
		{
		    calendarEntries = new String[cal.size()][];
		}

		for(int i=0; i<cal.size(); i++)
		{
		    calEnt = (CalendarEntry)cal.get(i);
		    calendarEntries[j] = getCalendarEntryDetails(calEnt);
		    j+=1;
		}
	    }
	    catch(Exception e)
	    {
		//calendarEntries[0] = new String[]{e.toString()};
	    }
	    finally
	    {
		return calendarEntries;
	    }
	}

	private String[] getCalendarEntryDetails(CalendarEntry ce)
	{
	    return new String[]{ce.getId().toExternalString(),getDateTimeFromCalendar(ce.getStartDate()),getDateTimeFromCalendar(ce.getEndDate()).toString(),ce.getDescription().getText(),ce.getType().toExternalString()};
	}
	
	/**
	 * Web service operation
	 */
	@WebMethod
	public String deleteCalendarEntryByCalendarEntryBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "calendarEntryBbId") String calendarEntryBbId)
	{
	    if(!wsp.isMethodAccessible("deleteCalendarEntryByCalendarEntryBbId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	    {
		return "Error: Access denied to method";
	    }

	    BbPersistenceManager bbPm = new BbServiceManager().getPersistenceService().getDbPersistenceManager();

	    try
	    {
		CalendarEntryDbLoader cel = (CalendarEntryDbLoader)bbPm.getLoader(CalendarEntryDbLoader.TYPE);
		CalendarEntry ce = cel.loadById(bbPm.generateId(CalendarEntry.DATA_TYPE,calendarEntryBbId));
		//You could just deleteById(bbPm.generateId()) but this way you also check if calendar entry exists
		CalendarEntryDbPersister.Default.getInstance().deleteById(ce.getId());
	    }
	    catch(Exception e)
	    {
		return "Error: Please provide a valid CalendarEntryBbId for a calendar entry";//+e.toString();
	    }

	    return "OK";
	}

	/**
	 * Web service operation
	 */
	@WebMethod
	public String[][] getAllCourseCalendarEntriesForGivenUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "headerDesc") Boolean headerDesc)
	{
	    if(!wsp.isMethodAccessible("getAllCourseCalendarEntriesForGivenUserId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	    {
		return new String[][]{{"Error"},{"Access denied to method"}};
	    }

	    BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	    String calendarEntries[][] = new String[][]{{"Error"},{"No calendar entries found on enrolled courses for given user"}};

	    if(headerDesc==null)
	    {
		headerDesc=false;
	    }

	    try
	    {
		CalendarEntryDbLoader calLoad = (CalendarEntryDbLoader)bbPm.getLoader(CalendarEntryDbLoader.TYPE);
		UserDbLoader ul = (UserDbLoader)bbPm.getLoader(UserDbLoader.TYPE);
		User u = ul.loadByUserName(userId);
		ArrayList cal = calLoad.loadByUserId(u.getId());
		calendarEntries = new String[cal.size()+1][];
		CalendarEntry calEnt = null;

		int j=0;
		if(headerDesc)
		{
		    calendarEntries[0] = new String[]{"CalendarEntryBbId","Start Date","End Date","Description","External Type"};
		    j=1;
		}
		else
		{
		    calendarEntries = new String[cal.size()][];
		}

		for(int i=0; i<cal.size(); i++)
		{
		    calEnt = (CalendarEntry)cal.get(i);
		    calendarEntries[j] = getCalendarEntryDetails(calEnt);
		    j+=1;
		}
	    }
	    catch(Exception e)
	    {
		//calendarEntries[0] = new String[]{e.toString()};
	    }
	    finally
	    {
		return calendarEntries;
	    }
	}
	
	/**
	 *
	 * Web service operation
	 */
	@WebMethod
	public String[][] getCourseCalendarEntriesWithinDatesForGivenUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId,
					@WebParam(name = "startDay") int startDay, @WebParam(name = "startMonth") int startMonth,
					@WebParam(name = "startYear") int startYear, @WebParam(name = "endDay") int endDay,
					@WebParam(name = "endMonth") int endMonth, @WebParam(name = "endYear") int endYear,
					@WebParam(name = "headerDesc") Boolean headerDesc)
	{
	    if(!wsp.isMethodAccessible("getCourseCalendarEntriesWithinDatesForGivenUserId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	    {
		return new String[][]{{"Error"},{"Access denied to method"}};
	    }

	    BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	    String calendarEntries[][] = new String[][]{{"Error"},{"No calendar entries found on enrolled courses for given user"}};

	    if(headerDesc==null)
	    {
		headerDesc=false;
	    }

	    try
	    {
		CalendarEntryDbLoader calLoad = (CalendarEntryDbLoader)bbPm.getLoader(CalendarEntryDbLoader.TYPE);
		UserDbLoader ul = (UserDbLoader)bbPm.getLoader(UserDbLoader.TYPE);
		User u = ul.loadByUserName(userId);
		Calendar startCal = new GregorianCalendar();
		startCal.setLenient(false);
		if(startDay!=0 && startMonth!=0 && startYear!=0)
		{
		    try
		    {
			//Remember the gc object month field is 0-11
			startCal.set(startYear,startMonth-1,startDay);
		    }
		    catch(Exception e)
		    {
			return new String[][]{{"Error"},{"Invalid starting date "+endDay+"/"+endMonth+"/"+endYear}};
		    }
		}
		//else today

		Calendar endCal = new GregorianCalendar();
		endCal.setLenient(false);
		if(endDay!=0 && endMonth!=0 && endYear!=0)
		{
		    try
		    {
			//Remember the gc object month field is 0-11
			endCal.set(endYear,endMonth-1,endDay);
		    }
		    catch(Exception e)
		    {
			return new String[][]{{"Error"},{"Invalid ending date "+endDay+"/"+endMonth+"/"+endYear}};
		    }
		}
		//else today

		ArrayList cal = calLoad.loadByUserId(u.getId(),startCal,endCal);
		calendarEntries = new String[cal.size()+1][];
		CalendarEntry calEnt = null;

		int j=0;
		if(headerDesc)
		{
		    calendarEntries[0] = new String[]{"CalendarEntryBbId","Start Date","End Date","Description","External Type"};
		    j=1;
		}
		else
		{
		    calendarEntries = new String[cal.size()][];
		}

		for(int i=0; i<cal.size(); i++)
		{
		    calEnt = (CalendarEntry)cal.get(i);
		    calendarEntries[j] = getCalendarEntryDetails(calEnt);
		    j+=1;
		}
	    }
	    catch(Exception e)
	    {
		//calendarEntries[0] = new String[]{e.toString()};
	    }
	    finally
	    {
		return calendarEntries;
	    }
	}
	
	/**
	 *
	 * Web service operation
	 */
	@WebMethod
	public String[][] getPersonalCalendarEntriesWithinDatesForGivenUserId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId,
					@WebParam(name = "startDay") int startDay, @WebParam(name = "startMonth") int startMonth,
					@WebParam(name = "startYear") int startYear, @WebParam(name = "endDay") int endDay,
					@WebParam(name = "endMonth") int endMonth, @WebParam(name = "endYear") int endYear,
					@WebParam(name = "headerDesc") Boolean headerDesc)
	{
	    if(!wsp.isMethodAccessible("getPersonalCalendarEntriesWithinDatesForGivenUserId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	    {
		return new String[][]{{"Error"},{"Access denied to method"}};
	    }

	    BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	    String calendarEntries[][] = new String[][]{{"Error"},{"No calendar entries found for this user"}};

	    if(headerDesc==null)
	    {
		headerDesc=false;
	    }

	    try
	    {
		CalendarEntryDbLoader calLoad = (CalendarEntryDbLoader)bbPm.getLoader(CalendarEntryDbLoader.TYPE);
		UserDbLoader ul = (UserDbLoader)bbPm.getLoader(UserDbLoader.TYPE);
		User u = ul.loadByUserName(userId);
		Calendar startCal = new GregorianCalendar();
		startCal.setLenient(false);
		if(startDay!=0 && startMonth!=0 && startYear!=0)
		{
		    try
		    {
			//Remember the gc object month field is 0-11
			startCal.set(startYear,startMonth-1,startDay);
		    }
		    catch(Exception e)
		    {
			return new String[][]{{"Error"},{"Invalid starting date "+endDay+"/"+endMonth+"/"+endYear}};
		    }
		}
		//else today

		Calendar endCal = new GregorianCalendar();
		endCal.setLenient(false);
		if(endDay!=0 && endMonth!=0 && endYear!=0)
		{
		    try
		    {
			//Remember the gc object month field is 0-11
			endCal.set(endYear,endMonth-1,endDay);
		    }
		    catch(Exception e)
		    {
			return new String[][]{{"Error"},{"Invalid ending date "+endDay+"/"+endMonth+"/"+endYear}};
		    }
		}
		//else today

		ArrayList cal = calLoad.loadPersonalByUserId(u.getId(),startCal,endCal);
		calendarEntries = new String[cal.size()+1][];
		CalendarEntry calEnt = null;

		int j=0;
		if(headerDesc)
		{
		    calendarEntries[0] = new String[]{"CalendarEntryBbId","Start Date","End Date","Description","External Type"};
		    j=1;
		}
		else
		{
		    calendarEntries = new String[cal.size()][];
		}

		for(int i=0; i<cal.size(); i++)
		{
		    calEnt = (CalendarEntry)cal.get(i);
		    calendarEntries[j] = getCalendarEntryDetails(calEnt);
		    j+=1;
		}
	    }
	    catch(Exception e)
	    {
		//calendarEntries[0] = new String[]{e.toString()};
	    }
	    finally
	    {
		return calendarEntries;
	    }
	}
}
