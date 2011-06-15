/*
 *   bbAnnouncement Class Library - Blackboard Web Services
 *   $URL: https://idla.springloops.com/source/idla_api/trunk/BlackBoard/bbIDLA/BbAnnouncement.cs $
 *   Description: Provides C# wrapper methods on Blackboard Announcement Web Service methods. Some
 *   of the resturn types are defined in the proxy classes while others were specifically
 *   developed for use in the application. These details classes are found in the BlackBoardWebServices.cs
 *   class file
 *  
 *   Created On          : March 12, 2010
 *   Created By          : Alex Loepp
 *   $LastChangedDate: 2010-03-12 17:11:05 -0700 (Fri, 12 Mar 2010) $
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
using bbIDLA.BBAnnouncementService;

namespace bbIDLA
{
    public class BbAnnouncement
    {
        // Password must match Oscelot web services passwords in BB
        private const string bbPassword = "BbWsPassword";
        BBAnnouncementsWebService bbAnnouncement = null;

        public BbAnnouncement()
        {
            bbAnnouncement = new BBAnnouncementsWebService();
        }

        //Delete Announcement
        public bool DeleteAnnouncement(string announcementBbId, out bool returnSpecified)
        {
            try
            {
                bool @return;
                bbAnnouncement.deleteAnnouncement(bbPassword, announcementBbId, out @return, out returnSpecified);
                return @return;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAnnouncement.DeleteAnnouncement", ex);
                returnSpecified = false;
                return false;
            }
        }

        //Delete Announcement (XML)
        public string DeleteAnnouncementXML(string announcementBbId)
        {
            try
            {
                return bbAnnouncement.deleteAnnouncementXML(bbPassword, announcementBbId);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAnnouncement.DeleteAnnouncementXML", ex);
                return null;
            }
        }

        //Get All Announcements For Given CourseId
        public stringArray[] GetAllAnnouncementsForGivenCourseId(string courseId, bool headerDesc, bool headerDescSpecified)
        {
            try
            {
                return bbAnnouncement.getAllAnnouncementsForGivenCourseId(bbPassword, courseId, headerDesc, headerDescSpecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAnnouncement.GetAllAnnouncementsForGivenCourseId", ex);
                return null;
            }
        }

        //Get All Announcements For Given CourseId With Named Elements
        public announcementDetails[] GetAllAnnouncementsForGivenCourseIdWithNamedElements(string courseId)
        {
            try
            {
                return bbAnnouncement.getAllAnnouncementsForGivenCourseIdWithNamedElements(bbPassword, courseId);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAnnouncement.GetAllAnnouncementsForGivenCourseIdWithNamedElements", ex);
                return null;
            }
        }

        //Get All Announcements For Given CourseId (XML)
        public string GetAllAnnouncementsForGivenCourseIdXML(string courseId)
        {
            try
            {
                return bbAnnouncement.getAllAnnouncementsForGivenCourseIdXML(bbPassword, courseId);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAnnouncement.GetAllAnnouncementsForGivenCourseIdXML", ex);
                return null;
            }
        }

        //Get All Available Announcements For Given UserId
        public stringArray[] GetAllAvailableAnnouncementsForGivenUserId(string userId, bool headerDesc, bool headerDescSpecified)
        {
            try
            {
                return bbAnnouncement.getAllAvailableAnnouncementsForGivenUserId(bbPassword, userId, headerDesc, headerDescSpecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAnnouncement.GetAllAvailableAnnouncementsForGivenUserId", ex);
                return null;
            }
        }

        //Get All Available Announcements For Given UserId With Named Elements
        public announcementDetails[] GetAllAvailableAnnouncementsForGivenUserIdWithNamedElements(string userId)
        {
            try
            {
                return bbAnnouncement.getAllAvailableAnnouncementsForGivenUserIdWithNamedElements(bbPassword, userId);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAnnouncement.GetAllAvailableAnnouncementsForGivenUserIdWithNamedElements", ex);
                return null;
            }
        }

        //Get All Available Announcements For Given UserId (XML)
        public string GetAllAvailableAnnouncementsForGivenUserIdXML(string userId)
        {
            try
            {
                return bbAnnouncement.getAllAvailableAnnouncementsForGivenUserIdXML(bbPassword, userId);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAnnouncement.GetAllAvailableAnnouncementsForGivenUserIdXML", ex);
                return null;
            }
        }

        //Get All System Announcements
        public stringArray[] GetAllSystemAnnouncements(bool headerDesc, bool headerDescSpecified)
        {
            try
            {
                return bbAnnouncement.getAllSystemAnnouncements(bbPassword, headerDesc, headerDescSpecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAnnouncement.GetAllSystemAnnouncements", ex);
                return null;
            }
        }

        //Get All System Announcements With Named Elements
        public announcementDetails[] GetAllSystemAnnouncementsWithNamedElements()
        {
            try
            {
                return bbAnnouncement.getAllSystemAnnouncementsWithNamedElements(bbPassword);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAnnouncement.GetAllSystemAnnouncementsWithNamedElements", ex);
                return null;
            }
        }

        //Get All System Announcements (XML)
        public string GetAllSystemAnnouncementsXML()
        {
            try
            {
                return bbAnnouncement.getAllSystemAnnouncementsXML(bbPassword);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAnnouncement.GetAllSystemAnnouncementsXML", ex);
                return null;
            }
        }

        //Modify Announcement
        public bool ModifyAnnouncement(string announcementBbId, string textType, string courseId, 
            string title, string body, int startDay, int startMonth, 
            int startYear, int endDay, int endMonth, int endYear, out bool returnSpecified)
        {
            try
            {
                bool @return;
                bbAnnouncement.modifyAnnouncement(bbPassword, announcementBbId, textType, courseId, title, body, 
                startDay, startMonth, startYear, endDay, endMonth, endYear, out @return, out returnSpecified);
                return @return;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAnnouncement.ModifyAnnouncement", ex);
                returnSpecified = false;
                return false;
            }
        }

        //Modify Announcement (XML)
        public string ModifyAnnouncementXML(string announcementBbId, string textType, string courseId,
            string title, string body, int startDay, int startMonth,
            int startYear, int endDay, int endMonth, int endYear)
        {
            try
            {
                return bbAnnouncement.modifyAnnouncementXML(bbPassword, announcementBbId, textType, courseId, title, body,
                startDay, startMonth, startYear, endDay, endMonth, endYear);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAnnouncement.ModifyAnnouncementXML", ex);
                return null;
            }
        }

        //Post Course Announcement
        public bool PostCourseAnnouncement(string textType, string courseID, bool permanent, 
            bool permanentSpecified, string title, string body, int startDay, int startMonth, 
            int startYear, int endDay, int endMonth, int endYear, out bool returnSpecified)
        {
            try
            {
                bool @return;
                bbAnnouncement.postCourseAnnouncement(bbPassword, textType, courseID, permanent, permanentSpecified,
                    title, body, startDay, startMonth, startYear, endDay, endMonth, endYear, out @return, out returnSpecified);
                return @return;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAnnouncement.PostCourseAnnouncement", ex);
                returnSpecified = false;
                return false;
            }
        }

        //Post Course Announcement (XML)
        public string PostCourseAnnouncementXML(string textType, string courseID, bool permanent, 
            bool permanentSpecified, string title, string body, int startDay, int startMonth, 
            int startYear, int endDay, int endMonth, int endYear)
        {
            try
            {
                return bbAnnouncement.postCourseAnnouncementXML(bbPassword, textType, courseID, permanent, 
                permanentSpecified, title, body, startDay, startMonth, startYear, endDay, endMonth, endYear);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAnnouncement.PostCourseAnnouncementXML", ex);
                return null;
            }
        }

        //Post System Announcement
        public bool PostSystemAnnouncement(string textType, bool permanent,
           bool permanentSpecified, string title, string body, int startDay, int startMonth,
           int startYear, int endDay, int endMonth, int endYear, out bool returnSpecified)
        {
            try
            {
                bool @return;
                bbAnnouncement.postSystemAnnouncement(bbPassword, textType, permanent, permanentSpecified,
                    title, body, startDay, startMonth, startYear, endDay, endMonth, endYear, 
                    out @return, out returnSpecified);
                return @return;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAnnouncement.PostSystemAnnouncement", ex);
                returnSpecified = false;
                return false;
            }
        }

        //Post System Announcement (XML)
        public string PostSystemAnnouncementXML(string textType, bool permanent,
           bool permanentSpecified, string title, string body, int startDay, int startMonth,
           int startYear, int endDay, int endMonth, int endYear)
        {
            try
            {
                return bbAnnouncement.postSystemAnnouncementXML(bbPassword, textType, permanent,permanentSpecified, 
                    title, body, startDay, startMonth, startYear, endDay, endMonth, endYear);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbAnnouncement.PostSystemAnnouncementXML", ex);
                return null;
            }
        }
    }
}