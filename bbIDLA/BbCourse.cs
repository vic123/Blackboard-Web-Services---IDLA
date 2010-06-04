/*
 *   bbCourse Class Library - Blackboard Web Services
 *   $URL: https://idla.springloops.com/source/idla_api/trunk/BlackBoard/bbIDLA/BbCourse.cs $
 *   Description: Provides C# wrapper methods on Blackboard Course Web Service methods. Some
 *   of the resturn types are defined in the proxy classes while others were specifically
 *   developed for use in the application. These details classes are found in the BlackBoardWebServices.cs
 *   class file
 *  
 *   Created On          : February 2, 2010
 *   Created By          : Alex Loepp
 *   $LastChangedDate: 2010-02-24 14:51:05 -0700 (Wed, 24 Feb 2010) $
 *   $LastChangedBy: alex.loepp $
 *   $LastChangedRevision: 9 $       
 *
 *   Change History:   
 *   0.0.0    ATL   Initial Development.
 *
 */
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace bbIDLA
{
    public class BbCourse
    {
        // Password must match Oscelot web services passwords in BB
        private const string bbPassword = "BbWsPassword";
        BBCourseService.BBCourseWebService bbCourse = null;
      

        // Constructor
        public BbCourse()
        {
            bbCourse = new BBCourseService.BBCourseWebService();
           
        }

        //Add Course
        public bool AddCourse(string courseid, string batchuid, string title, 
            string coursedescription, bool available, bool availablespecified, 
            bool allowguests, bool allowguestsspecified, bool allowobservers, 
            bool allowobserversspecified, out bool returnspecified)
        {
            try
            {
               bool @return = false;

               bbCourse.addCourse(bbPassword, courseid, batchuid, title, 
                   coursedescription, available, availablespecified, 
                   allowguests, allowguestsspecified, allowobservers, 
                   allowobserversspecified, out @return, out returnspecified);

               return @return;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.AddCourse", ex);
                returnspecified = false;
                return false;
            }
        }

        //Add Course (XML)
        public string AddCourseXML(string courseid, string batchuid, string title, 
            string coursedescription, bool available, bool availablespecified, 
            bool allowguests, bool allowguestsspecified, bool allowobservers, 
            bool allowobserversspecified)
        {
            try
            {
                return bbCourse.addCourseXML(bbPassword, courseid, batchuid, title,
                   coursedescription, available, availablespecified,
                   allowguests, allowguestsspecified, allowobservers,
                   allowobserversspecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.AddCourseXML", ex);
                return null;
            }
        }

        //Add Course (XML)
        public bool CopyCourseExact(string sourcebatchuid, string targetbatchuid, out bool returnspecified)
        {
            try
            {
                bool @return = false;
                bbCourse.copyCourseExact(bbPassword, sourcebatchuid, targetbatchuid, out @return, out returnspecified);
                return @return;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.CopyCourseExact", ex);
                returnspecified = false;
                return false;
            }
        }

        //Delete Course By CourseBbID
        public bool DeleteCourseByCourseBbID(string coursebbid, out bool returnspecified)
        {
            try
            {
                bool @return = false;
                bbCourse.deleteCourseByCourseBbId(bbPassword, coursebbid, out @return, out returnspecified);
                return @return;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.DeleteCourseByCourseBbID", ex);
                returnspecified = false;
                return false;
            }
        }

        //Delete Course By CourseBbID (XML)
        public string DeleteCourseByCourseBbIDXML(string coursebbid)
        {
            try
            {
                return bbCourse.deleteCourseByCourseBbIdXML(bbPassword, coursebbid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.DeleteCourseByCourseBbIDXML", ex);
                return null;
            }
        }

        //Delete Course By CourseID
        public bool DeleteCourseByCourseID(string courseid, out bool returnspecified)
        {
            try
            {
                bool @return = false;
                bbCourse.deleteCourseByCourseId(bbPassword, courseid, out @return, out returnspecified);
                return @return;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.DeleteCourseByCourseID", ex);
                returnspecified = false;
                return false;
            }
        }

        //Delete Course By CourseID (XML)
        public string DeleteCourseByCourseIDXML(string courseid)
        {
            try
            {
                return bbCourse.deleteCourseByCourseIdXML(bbPassword, courseid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.DeleteCourseByCourseIDXML", ex);
                return null;
            }
        }

        //Does Course Exist
        public bool DoesCourseExist(string courseid, out bool returnspecified)
        {
            try
            {
                bool @return = false;
                bbCourse.doesCourseExist(bbPassword, courseid, out @return, out returnspecified);
                return @return;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.DoesCourseExist", ex);
                returnspecified = false;
                return false;
            }
        }

        //Does Course Exist (XML)
        public string DoesCourseExistXML(string courseid)
        {
            try
            {
                return bbCourse.doesCourseExistXML(bbPassword, courseid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.DoesCourseExistXML", ex);
                return null;
            }
        }

        //Enroll Given User On Given Course
        public bool EnrollGivenUserOnGivenCourse(string courseid, string userid, string role, out bool returnspecified)
        {
            try
            {
                bool @return = false;
                bbCourse.enrollGivenUserOnGivenCourse(bbPassword, courseid, userid, role, out @return, out returnspecified);
                return @return;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.EnrollGivenUserOnGivenCourse", ex);
                returnspecified = false;
                return false;
            }
        }

        //Enroll Given User On Given Course (XML)
        public string EnrollGivenUserOnGivenCourseXML(string courseid, string userid, string role)
        {
            try
            {
                return bbCourse.enrollGivenUserOnGivenCourseXML(bbPassword, courseid, userid, role);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.EnrollGivenUserOnGivenCourseXML", ex);
                return null;
            }
        }

        //Get All CourseIDs No Details
        public string[] GetAllCourseIDsNoDetails()
        {
            try
            {
                return bbCourse.getAllCourseIDsNoDetails(bbPassword);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetAllCourseIDsNoDetails", ex);
                return null;
            }
        }

        //Get All CourseIDs No Details With Named Elements
        public BBCourseService.courseDetails[] GetAllCourseIDsNoDetailsWithNamedElements()
        {
            try
            {
                return bbCourse.getAllCourseIDsNoDetailsWithNamedElements(bbPassword);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetAllCourseIDsNoDetailsWithNamedElements", ex);
                return null;
            }
        }

        //Get All CourseIDs No Details (XML)
        public string GetAllCourseIDsNoDetailsXML()
        {
            try
            {
                return bbCourse.getAllCourseIDsNoDetailsXML(bbPassword);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetAllCourseIDsNoDetailsXML", ex);
                return null;
            }
        }

        //Get All CourseIDs Where CourseId Contains Given Search String
        public string[] GetAllCourseIDsWhereCourseIdContainsGivenSearchString(string searchstring)
        {
            try
            {
                return bbCourse.getAllCourseIDsWhereCourseIdContainsGivenSearchString(bbPassword, searchstring);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetAllCourseIDsWhereCourseIdContainsGivenSearchString", ex);
                return null;
            }
        }

        //Get All CourseIDs Where CourseId Contains Given Search String With Named Elements
        public BBCourseService.courseDetails[] GetAllCourseIDsWhereCourseIdContainsGivenSearchStringWithNamedElements(string searchstring)
        {
            try
            {
                return bbCourse.getAllCourseIDsWhereCourseIdContainsGivenSearchStringWithNamedElements(bbPassword, searchstring);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetAllCourseIDsWhereCourseIdContainsGivenSearchStringWithNamedElements", ex);
                return null;
            }
        }

        //Get All CourseIDs Where CourseId Contains Given Search String (XML)
        public string GetAllCourseIDsWhereCourseIdContainsGivenSearchStringXML(string searchstring)
        {
            try
            {
                return bbCourse.getAllCourseIDsWhereCourseIdContainsGivenSearchStringXML(bbPassword, searchstring);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetAllCourseIDsWhereCourseIdContainsGivenSearchStringXML", ex);
                return null;
            }
        }

        //Get All CourseIDs Where Given UserId Is An Instructor With Named Elements
        public BBCourseService.courseDetails[] GetAllCourseIDsWhereGivenUserIdIsAnInstructorWithNamedElements(string userid)
        {
            try
            {
                return bbCourse.getAllCourseIDsWhereGivenUserIdIsAnInstructorWithNamedElements(bbPassword, userid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetAllCourseIDsWhereGivenUserIdIsAnInstructorWithNamedElements", ex);
                return null;
            }
        }

        //Get All CourseIDs Where Given UserId Is An Instructor (XML)
        public string GetAllCourseIDsWhereGivenUserIdIsAnInstructorXML(string userid)
        {
            try
            {
                return bbCourse.getAllCourseIDsWhereGivenUserIdIsAnInstructorXML(bbPassword, userid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetAllCourseIDsWhereGivenUserIdIsAnInstructorXML", ex);
                return null;
            }
        }

        //Get Course Details
        public BbCourseDetails GetCourseDetails(string courseid, bool extendeddetails, 
            bool extendeddetailsspecified, bool headerdesc, bool headerdescspecified)
        {
            try
            {
                var data = bbCourse.getCourseDetails(bbPassword, courseid, extendeddetails, 
                    extendeddetailsspecified, headerdesc, headerdescspecified);

                return BlackBoardWebServices.ConvertStringArrayToObjectDefinition<BbCourseDetails>(data[0].item, data[1].item);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetCourseDetails", ex);
                return null;
            }
        }

        //Get Course Details With Named Elements
        public BBCourseService.courseDetails GetCourseDetailsWithNamedElements(string courseid, bool extendeddetails, bool extendeddetailsspecified)
        {
            try
            {
                return bbCourse.getCourseDetailsWithNamedElements(bbPassword, courseid, extendeddetails, extendeddetailsspecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetCourseDetailsWithNamedElements", ex);
                return null;
            }
        }

        //Get Course Details (XML)
        public string GetCourseDetailsXML(string courseid, bool extendeddetails,
            bool extendeddetailsspecified, bool headerdesc, bool headerdescspecified)
        {
            try
            {
               return bbCourse.getCourseDetailsXML(bbPassword, courseid, extendeddetails, extendeddetailsspecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetCourseDetailsXML", ex);
                return null;
            }
        }

        //Get Course Membership BbId For Given UserId And CourseId
        public string GetCourseMembershipBbIdForGivenUserIdAndCourseId(string userid, string courseid)
        {
            try
            {
                return bbCourse.getCourseMembershipBbIdForGivenUserIdAndCourseId(bbPassword, userid, courseid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetCourseMembershipBbIdForGivenUserIdAndCourseId", ex);
                return null;
            }
        }

        //Get Course Membership BbId For Given UserId And CourseId With Named Elements
        public BBCourseService.courseMembershipDetails GetCourseMembershipBbIdForGivenUserIdAndCourseIdWithNamedElements(string userid, string courseid)
        {
            try
            {
                return bbCourse.getCourseMembershipBbIdForGivenUserIdAndCourseIdWithNamedElements(bbPassword, userid, courseid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetCourseMembershipBbIdForGivenUserIdAndCourseIdWithNamedElements", ex);
                return null;
            }
        }

        //Get Course Membership BbId For Given UserId And CourseId (XML)
        public string GetCourseMembershipBbIdForGivenUserIdAndCourseIdXML(string userid, string courseid)
        {
            try
            {
                return bbCourse.getCourseMembershipBbIdForGivenUserIdAndCourseIdXML(bbPassword, userid, courseid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetCourseMembershipBbIdForGivenUserIdAndCourseIdXML", ex);
                return null;
            }
        }

        //Get Course Membership Details For Given Course Membership BbId With Named Elements
        public BBCourseService.courseMembershipDetails GetCourseMembershipDetailsForGivenCourseMembershipBbIdWithNamedElements(string coursemembershipbbid)
        {
            try
            {
                return bbCourse.getCourseMembershipDetailsForGivenCourseMembershipBbIdWithNamedElements(bbPassword, coursemembershipbbid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetCourseMembershipDetailsForGivenCourseMembershipBbIdWithNamedElements", ex);
                return null;
            }
        }

        //Get Course Membership Details For Given Course Membership BbId (XML)
        public string GetCourseMembershipDetailsForGivenCourseMembershipBbIdXML(string coursemembershipbbid)
        {
            try
            {
                return bbCourse.getCourseMembershipDetailsForGivenCourseMembershipBbIdXML(bbPassword, coursemembershipbbid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetCourseMembershipDetailsForGivenCourseMembershipBbIdXML", ex);
                return null;
            }
        }

        //Get Course Quota Details
        public BbCourseQuotaDetails GetCourseQuotaDetails(string courseid, bool headerdesc, bool headerdescspecified)
        {
            try
            {
                var results = bbCourse.getCourseQuotaDetails(bbPassword, courseid, headerdesc, headerdescspecified);
                return BlackBoardWebServices.ConvertStringArrayToObjectDefinition<BbCourseQuotaDetails>(results[0].item, results[1].item);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetCourseQuotaDetails", ex);
                return null;
            }
        }

        //Get Course Quota Details With Named Elements
        public BBCourseService.courseQuotaDetails GetCourseQuotaDetailsWithNamedElements(string courseid)
        {
            try
            {
                return bbCourse.getCourseQuotaDetailsWithNamedElements(bbPassword, courseid);                
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetCourseQuotaDetailsWithNamedElements", ex);
                return null;
            }
        }

        //Get Course Quota Details (XML)
        public string GetCourseQuotaDetailsXML(string courseid)
        {
            try
            {
                return bbCourse.getCourseQuotaDetailsXML(bbPassword, courseid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetCourseQuotaDetailsXML", ex);
                return null;
            }
        }

        //Get Enrolled Courses For Given User
        public List<EnrolledCourseDetails> GetEnrolledCoursesForGivenUser(string userid, bool headerdesc, bool headerdescspecified)
        {
            try
            {
                var results = bbCourse.getEnrolledCoursesForGivenUser(bbPassword, userid, headerdesc, headerdescspecified);
                List<EnrolledCourseDetails> list = new List<EnrolledCourseDetails>();

                for (int i = 1; i < results.Length; i++)
                {
                    var data = BlackBoardWebServices.ConvertStringArrayToObjectDefinition<EnrolledCourseDetails>(results[0].item, results[i].item);
                    list.Add(data);
                }

                return list;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetEnrolledCoursesForGivenUser", ex);
                return null;
            }
        }

        //Get Enrolled Courses For Given User With Named Elements
        public BBCourseService.enrollmentDetails[] GetEnrolledCoursesForGivenUserWithNamedElements(string userid)
        {
            try
            {
                return bbCourse.getEnrolledCoursesForGivenUserWithNamedElements(bbPassword, userid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetEnrolledCoursesForGivenUserWithNamedElements", ex);
                return null;
            }
        }

        //Get Enrolled Courses For Given User (XML)
        public string GetEnrolledCoursesForGivenUserXML(string userid)
        {
            try
            {
                return bbCourse.getEnrolledCoursesForGivenUserXML(bbPassword, userid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.GetEnrolledCoursesForGivenUserXML", ex);
                return null;
            }
        }

        //Modify Course Quota Details
        public bool ModifyCourseQuotaDetails(string courseid, bool overridediskquota, 
            bool overrridediskquotaspecified, bool ovverrideuploadlimit, 
            bool overrideuploadlimitspecified, long absolutelimit, 
            bool absolutelimitspecified, long softlimit,bool softlimitspecified,
            long uploadlimit, bool uploadlimitspecified, out bool returnspecified)
        {
            try
            {
                bool @return = false;
                bbCourse.modifyCourseQuotaDetails(bbPassword, courseid, 
                    overridediskquota, overrridediskquotaspecified,ovverrideuploadlimit,
                    overrideuploadlimitspecified,absolutelimit,absolutelimitspecified,
                    softlimit,softlimitspecified,uploadlimit,uploadlimitspecified,
                    out @return, out returnspecified);
                return @return;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.ModifyCourseQuotaDetails", ex);
                returnspecified = false;
                return false;
            }
        }

        //Modify Course Quota Details (XML)
        public string ModifyCourseQuotaDetailsXML(string courseid, bool overridediskquota,
           bool overrridediskquotaspecified, bool ovverrideuploadlimit,
           bool overrideuploadlimitspecified, long absolutelimit,
           bool absolutelimitspecified, long softlimit, bool softlimitspecified,
           long uploadlimit, bool uploadlimitspecified)
        {
            try
            {
                 return bbCourse.modifyCourseQuotaDetailsXML(bbPassword, courseid,
                    overridediskquota, overrridediskquotaspecified, ovverrideuploadlimit,
                    overrideuploadlimitspecified, absolutelimit, absolutelimitspecified,
                    softlimit, softlimitspecified, uploadlimit, uploadlimitspecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.ModifyCourseQuotaDetailsXML", ex);
                return null;
            }
        }

        //UnEnroll Given User From Given Course
        public bool UnEnrollGivenUserFromGivenCourse(string userid, string courseid, out bool returnspecified)
        {
            try
            {
                bool @return = false;
                bbCourse.unEnrollGivenUserFromGivenCourse(bbPassword, userid, courseid, out @return, out returnspecified);
                return @return;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.UnEnrollGivenUserFromGivenCourse", ex);
                returnspecified = false;
                return false;
            }
        }

        //UnEnroll Given User From Given Course (XML)
        public string UnEnrollGivenUserFromGivenCourseXML(string userid, string courseid)
        {
            try
            {
                return bbCourse.unEnrollGivenUserFromGivenCourseXML(bbPassword, userid, courseid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.UnEnrollGivenUserFromGivenCourseXML", ex);
                return null;
            }
        }

        //Update Course
        public bool UpdateCourse(string arg1, string arg2, string arg3, 
            bool arg4, bool arg4specified, bool arg5, bool arg5specified, 
            bool arg6, bool arg6specified)
        {
            try
            {
                return bbCourse.updateCourse(bbPassword, arg1, arg2, arg3, arg4, 
                    arg4specified, arg5, arg5specified, arg6, arg6specified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("BbCourse.UpdateCourse", ex);
                return false;
            }
        }
    }
}