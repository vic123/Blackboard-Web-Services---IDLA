/*
 *   bbCalendar Class Library - Blackboard Web Services
 *   $URL: https://idla.springloops.com/source/idla_api/trunk/BlackBoard/bbIDLA/BbCalendar.cs $
 *   Description: Provides C# wrapper methods on Blackboard Calendar Web Service methods. Some
 *   of the resturn types are defined in the proxy classes while others were specifically
 *   developed for use in the application. These details classes are found in the BlackBoardWebServices.cs
 *   class file
 *  
 *   Created On          : March 13, 2010
 *   Created By          : Alex Loepp
 *   $LastChangedDate: 2010-03-13 09:14:45 -0700 (Sat, 13 Mar 2010) $
 *   $LastChangedBy: alex.loepp $
 *   $LastChangedRevision:  $       
 *
 *   Change History:   
 *   0.0.0    ATL   Initial Development.
 *
 */
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using bbIDLA.BBCalendarService;

namespace bbIDLA
{
    public class BbCalendar
    {
        // Password must match Oscelot web services passwords in BB
        private const string bbPassword = "BbWsPassword";
        BBCalendarWebService bbCalendar = null;

        public BbCalendar()
        {
            bbCalendar = new BBCalendarWebService();
        }

        //Add Calendar Entry
        public string AddCalendarEntry(string courseID, string userID, string description, 
            string title, string type, int startDay, int startMonth, int startYear, 
            int startHour, int startMinute, int startSecond, int endDay, int endMonth, 
            int endYear, int endHour, int endMinute, int endSecond)
        {
            try
            {
                return bbCalendar.addCalendarEntry(bbPassword, courseID, userID, description, title, 
                    type, startDay, startMonth, startYear, startHour, startMinute, startSecond, 
                    endDay, endMonth, endYear, endHour, endMinute, endSecond);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbCalendar.AddCalendarEntry", ex);
                return null;
            }
        }

        //Delete Calendar Entry By Calendar Entry BbId
        public string DeleteCalendarEntryByCalendarEntryBbId(string calendarEntryBbId)
        {
            try
            {
                return bbCalendar.deleteCalendarEntryByCalendarEntryBbId(bbPassword, calendarEntryBbId);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbCalendar.DeleteCalendarEntryByCalendarEntryBbId", ex);
                return null;
            }
        }

        //Get All Course Calendar Entries For Given CourseId
        public stringArray[] GetAllCourseCalendarEntriesForGivenCourseId(string courseId, bool headerDesc, bool headerDescSpecified)
        {
            try
            {
                return bbCalendar.getAllCourseCalendarEntriesForGivenCourseId(bbPassword, courseId, headerDesc, headerDescSpecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbCalendar.GetAllCourseCalendarEntriesForGivenCourseId", ex);
                return null;
            }
        }

        //Get All Course Calendar Entries For Given UserId
        public stringArray[] GetAllCourseCalendarEntriesForGivenUserId(string userId, bool headerDesc, bool headerDescSpecified)
        {
            try
            {
                return bbCalendar.getAllCourseCalendarEntriesForGivenUserId(bbPassword, userId, headerDesc, headerDescSpecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbCalendar.GetAllCourseCalendarEntriesForGivenUserId", ex);
                return null;
            }
        }

        //Get Course Calendar Entries Within Dates For Given CourseId
        public stringArray[] GetCourseCalendarEntriesWithinDatesForGivenCourseId(string courseId, 
            int startDay, int startMonth, int startYear, int endDay, int endMonth,
            int endYear, bool headerDesc,  bool headerDescSpecified)
        {
            try
            {
                return bbCalendar.getCourseCalendarEntriesWithinDatesForGivenCourseId(bbPassword, courseId,
                    startDay, startMonth, startYear, endDay, endMonth, endYear, headerDesc, headerDescSpecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbCalendar.GetCourseCalendarEntriesWithinDatesForGivenCourseId", ex);
                return null;
            }
        }

        //Get Course Calendar Entries Within Dates For Given UserId
        public stringArray[] GetCourseCalendarEntriesWithinDatesForGivenUserId(string userId, 
        int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear, bool headerDesc, bool headerDescSpecified)
        {
            try
            {
                return bbCalendar.getCourseCalendarEntriesWithinDatesForGivenUserId(bbPassword, userId,
            startDay, startMonth, startYear, endDay, endMonth, endYear, headerDesc, headerDescSpecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbCalendar.GetCourseCalendarEntriesWithinDatesForGivenUserId", ex);
                return null;
            }
        }

        //Get Personal Calendar Entries Within Dates For Given UserId
        public stringArray[] GetPersonalCalendarEntriesWithinDatesForGivenUserId(string userId, 
            int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear, 
            bool headerDesc, bool headerDescSpecified)
        {
            try
            {
                return bbCalendar.getPersonalCalendarEntriesWithinDatesForGivenUserId(bbPassword, userId, 
                    startDay, startMonth, startYear, endDay, endMonth, endYear, headerDesc, headerDescSpecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbCalendar.GetPersonalCalendarEntriesWithinDatesForGivenUserId", ex);
                return null;
            }
        }
    }
}