/*
 *   bbUser Class Library - Blackboard Web Services
 *   $URL: https://idla.springloops.com/source/idla_api/trunk/BlackBoard/bbIDLA/BbUser.cs $
 *   Description: Provides C# wrapper methods on Blackboard User Web Service methods. Some
 *   of the resturn types are defined in the proxy classes while others were specifically
 *   developed for use in the application. These details classes are found in the BlackBoardWebServices.cs
 *   class file
 *  
 *   Created On          : February 2, 2010
 *   Created By          : Alex Loepp
 *   $LastChangedDate: 2010-02-02 13:45:05 -0700 (Tue, 02 Feb 2010) $
 *   $LastChangedBy: alex.loepp $
 *   $LastChangedRevision: 0 $       
 *
 *   Change History:   
 *   0.0.0    ATL   Initial Development.
 *
 */
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Collections.Specialized;

namespace bbIDLA
{
    public class BbUser
    {
        // Password must match Oscelot web services passwords in BB
        private const string bbPassword = "BbWsPassword";

        BBUserService.BBUserWebService bbUser = null;

        public BbUser()
        {
            bbUser = new BBUserService.BBUserWebService();
        }

        //add the default student
        public bool AddDefaultStudent(string userid, string firstname, string middlename, string lastname, 
            string email, string studentid, string studentpassword, out bool returnspecified)
        {
            bool IsAdded = false;

            try
            {
                bbUser.addDefaultStudent(bbPassword, userid, firstname, middlename, lastname,
                email, studentid, studentpassword, out IsAdded, out returnspecified);

                return IsAdded;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.AddDefaultStudent", ex);
                returnspecified = false;
                return IsAdded;
            }
        }

        //Add Default Student (XML)
        public string AddDefaultStudentXML(string userid, string firstname, string middlename, string lastname,
           string email, string studentid, string studentpassword)
        {
            try
            {
                return bbUser.addDefaultStudentXML(bbPassword, userid, firstname, middlename, lastname,
                email, studentid, studentpassword);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.AddDefaultStudentXML", ex);
                return null;
            }
        }

        //add user
        public bool AddUser(string userid, string batchuid, string firstname, string middlename, string lastname,
            string email, string studentid, string userpwd, string gender, string birthyear, string birthmonth,
            string birthday, string edulevel, string company, string jobtitle, string department, string street1,
            string street2, string city, string stateorprovince, string ziporpostalcode, string country, string website,
            string homephone, string workphone, string workfax, string mobilephone, string portalrole, string[] secportalrole,
            string systemrole, bool available, bool availablespecified, out bool returnspecified)
        {
            bool IsAdded = false;

            try
            {
                bbUser.addUser(bbPassword, userid, batchuid, firstname, middlename, lastname,
                email, studentid, userpwd, gender, birthyear, birthmonth, birthday, edulevel, company, jobtitle,
                department, street1, street2, city, stateorprovince, ziporpostalcode, country, website, homephone,
                workphone, workfax, mobilephone, portalrole, secportalrole, systemrole, available, availablespecified,
                out IsAdded, out returnspecified);

                return IsAdded;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.AddUser", ex);
                returnspecified = false;
                return IsAdded;
            }
        }

        //Add User (XML)
        public string AddUserXML(string userid, string batchuid, string firstname, string middlename, string lastname,
           string email, string studentid, string userpwd, string gender, string birthyear, string birthmonth,
           string birthday, string edulevel, string company, string jobtitle, string department, string street1,
           string street2, string city, string stateorprovince, string ziporpostalcode, string country, string website,
           string homephone, string workphone, string workfax, string mobilephone, string portalrole, string[] secportalrole,
           string systemrole, bool available, bool availablespecified)
        {
            try
            {
                return bbUser.addUserXML(bbPassword, userid, batchuid, firstname, middlename, lastname,
                email, studentid, userpwd, gender, birthyear, birthmonth, birthday, edulevel, company, jobtitle,
                department, street1, street2, city, stateorprovince, ziporpostalcode, country, website, homephone,
                workphone, workfax, mobilephone, portalrole, secportalrole, systemrole, available, availablespecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.AddUserXML", ex);
                return null;
            }
        }

        //delete user by User's BB ID
        public bool DeleteUserByUserBbID(string userbbid, out bool returnspecified)
        {
            bool IsDeleted = false;

            try
            {
                bbUser.deleteUserByUserBbId(bbPassword, userbbid, out IsDeleted, out returnspecified);
                return IsDeleted;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.DeleteUserByUserBbID", ex);
                returnspecified = false;
                return IsDeleted;
            }
        }

        //Delete User By UserBbId (XML)
        public string DeleteUserByUserBbIdXML(string userbbid)
        {
            try
            {
                return bbUser.deleteUserByUserBbIdXML(bbPassword, userbbid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.DeleteUserByUserBbIdXML", ex);
                return null;
            }
        }

        //delete user by User's ID
        public bool DeleteUserByUserID(string userid, out bool returnspecified)
        {
            bool IsDeleted = false;

            try
            {
                bbUser.deleteUserByUserId(bbPassword, userid, out IsDeleted, out returnspecified);
                return IsDeleted;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.DeleteUserByUserID", ex);
                returnspecified = false;
                return IsDeleted;
            }
        }

        //Delete User By UserId (XML)
        public string DeleteUserByUserIdXML(string userid)
        {
            try
            {
               return bbUser.deleteUserByUserIdXML(bbPassword, userid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.DeleteUserByUserIdXML", ex);
                return null;
            }
        }

        //verify that a specific user exists
        public bool DoesUserExist(string userid, out bool returnspecified)
        {
            bool IsExistent = false;

            try
            {
                bbUser.doesUserExist(bbPassword, userid, out IsExistent, out returnspecified);
                return IsExistent;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.DoesUserExist", ex);
                returnspecified = false;
                return IsExistent;
            }
        }

        //Does User Exist (XML)
        public string DoesUserExistXML(string userid)
        {
            try
            {
                return bbUser.doesUserExistXML(bbPassword, userid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.DoesUserExistXML", ex);
                return null;
            }
        }

        //get all the users w/o details
        public string[] GetAllUserIDsNoDetails()
        {
            try
            {
                return bbUser.getAllUserIDsNoDetails(bbPassword);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetAllUserIDsNoDetails", ex);
                return null;
            }
        }

        //get all the users w/o details with named elements
        public BBUserService.userDetails[] GetAllUserIDsNoDetailsWithNamedElements()
        {
            try
            {
                return bbUser.getAllUserIDsNoDetailsWithNamedElements(bbPassword);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetAllUserIDsNoDetailsWithNamedElements", ex);
                return null;
            }
        }

        //Get All UserIDs No Details (XML)
        public string GetAllUserIDsNoDetailsXML()
        {
            try
            {
                return bbUser.getAllUserIDsNoDetailsXML(bbPassword);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetAllUserIDsNoDetailsXML", ex);
                return null;
            }
        }


        //get all the enrolled students for given courseid with named elements
        public BBUserService.userDetails[] GetEnrolledStudentsForGivenCourseIDWithNamedElements(string courseid)
        {
            try
            {
                return bbUser.getEnrolledStudentsForGivenCourseIDWithNamedElements(bbPassword, courseid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetEnrolledStudentsForGivenCourseIDWithNamedElements", ex);
                return null;
            }
        }

        //Get Enrolled Students For Given CourseID (XML)
        public string GetEnrolledStudentsForGivenCourseIDXML(string courseid)
        {
            try
            {
                return bbUser.getEnrolledStudentsForGivenCourseIDXML(bbPassword, courseid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetEnrolledStudentsForGivenCourseIDXML", ex);
                return null;
            }
        }

        //Get Enrolled User Details For Given Course With Named Elements
        public BBUserService.userDetails[] GetEnrolledUserDetailsForGivenCourseWithNamedElements(string courseid, bool extendeddetails, bool extendeddetailsspecified)
        {
            try
            {
                return bbUser.getEnrolledUserDetailsForGivenCourseWithNamedElements(bbPassword, courseid, extendeddetails, extendeddetailsspecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetEnrolledUserDetailsForGivenCourseWithNamedElements", ex);
                return null;
            }
        }

        //Get Enrolled User Details For Given Course (XML)
        public string GetEnrolledUserDetailsForGivenCourseXML(string courseid, bool extendeddetails, bool extendeddetailsspecified)
        {
            try
            {
                return bbUser.getEnrolledUserDetailsForGivenCourseXML(bbPassword, courseid, extendeddetails, extendeddetailsspecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.getEnrolledUserDetailsForGivenCourseAsXml", ex);
                return null;
            }
        }     

        //get enrolled users for a given course
        public List<EnrolledUserDetails> GetEnrolledUsersForGivenCourse(string courseid, bool extendeddetails, 
            bool extendeddetailsspecified, bool headerdesc, bool headerdescspecified)
        {
            try
            {
               var results = bbUser.getEnrolledUsersForGivenCourse(bbPassword, courseid, 
                    extendeddetails, extendeddetailsspecified, headerdesc, headerdescspecified);

               List<EnrolledUserDetails> list = new List<EnrolledUserDetails>();

               for (int i = 1; i < results.Length; i++)
               {
                   var data = BlackBoardWebServices.ConvertStringArrayToObjectDefinition<EnrolledUserDetails>(results[0].item, results[i].item);
                   list.Add(data);
               }

               return list;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetEnrolledUsersForGivenCourse", ex);
                return null;
            }
        }

        //TODO: need results to create definition class
        //get enrollments by batch UID and courseID pattern
        public BBUserService.stringArray[] GetEnrollmentsByBatchUIDAndCourseIDPattern(string userbatchuid, string regex, 
            bool extendeddetails, bool extendeddetailsspecified, bool ismemberonly, bool ismemberonlyspecified,
            bool headerdesc, bool headerdescspecified)
        {
            try
            {
                var results = bbUser.getEnrollmentsByBatchUIDandCourseIDPattern(bbPassword, userbatchuid, regex,
                     ismemberonly, ismemberonlyspecified, extendeddetails, extendeddetailsspecified,
                      headerdesc, headerdescspecified);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetEnrollmentsByBatchUIDAndCourseIDPattern", ex);
                return null;
            }
        }

        //Get Secondary Portal Roles For Given UserID
        public List<RoleDefinition> GetSecondaryPortalRolesForGivenUserID(string userid, bool headerdesc, bool headerdescspecified)
        {
            try
            {
                var results = bbUser.getSecondaryPortalRolesForGivenUserId(bbPassword, userid, headerdesc, headerdescspecified);
                List<RoleDefinition> list = new List<RoleDefinition>();

                for (int i = 1; i < results.Length; i++)
                {
                    var data = BlackBoardWebServices.ConvertStringArrayToObjectDefinition<RoleDefinition>(results[0].item, results[i].item);
                    list.Add(data);
                }

                return list;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetSecondaryPortalRolesForGivenUserID", ex);
                return null;
            }
        }

        //Get Secondary Portal Roles For Given UserID With Named Elements
        public BBUserService.role[] GetSecondaryPortalRolesForGivenUserIDWithNamedElements(string userid, bool headerdesc, bool headerdescspecified)
        {
            try
            {
                return bbUser.getSecondaryPortalRolesForGivenUserIdWithNamedElements(bbPassword, userid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetSecondaryPortalRolesForGivenUserIDWithNamedElements", ex);
                return null;
            }
        }

        //Get Secondary Portal Roles For Given UserId (XML)
        public string GetSecondaryPortalRolesForGivenUserIdXML(string userid)
        {
            try
            {
                return bbUser.getSecondaryPortalRolesForGivenUserIdXML(bbPassword, userid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetSecondaryPortalRolesForGivenUserIdXML", ex);
                return null;
            }
        }

        //Get Secondary System Roles For Given UserID
        public List<RoleDefinition> GetSecondarySystemRolesForGivenUserID(string userid, bool headerdesc, bool headerdescspecified)
        {
            try
            {
                var results = bbUser.getSecondarySystemRolesForGivenUserId(bbPassword, userid, headerdesc, headerdescspecified);
                List<RoleDefinition> list = new List<RoleDefinition>();

                for (int i = 1; i < results.Length; i++)
                {
                    var data = BlackBoardWebServices.ConvertStringArrayToObjectDefinition<RoleDefinition>(results[0].item, results[i].item);
                    list.Add(data);
                }

                return list;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetSecondarySystemRolesForGivenUserID", ex);
                return null;
            }
        }

        //Get Secondary System Roles For Given UserID With Named Elements
        public BBUserService.role[] GetSecondarySystemRolesForGivenUserIDWithNamedElements(string userid)
        {
            try
            {
                return bbUser.getSecondarySystemRolesForGivenUserIdWithNamedElements(bbPassword, userid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.getSecondarySystemRolesForGivenUserIdWithNamedElements", ex);
                return null;
            }
        }

        //Get Secondary System Roles For Given UserId (XML)
        public string GetSecondarySystemRolesForGivenUserIdXML(string userid)
        {
            try
            {
                return bbUser.getSecondarySystemRolesForGivenUserIdXML(bbPassword, userid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetSecondarySystemRolesForGivenUserIdXML", ex);
                return null;
            }
        }

        //Get User Details
        public EnrolledUserDetails GetUserDetails(string userid, bool extendeddetails, bool extendeddetailsspecified, bool headerdesc, bool headerdescspecified)
        {
            try
            {
                var results = bbUser.getUserDetails(bbPassword, userid,
                    extendeddetails, extendeddetailsspecified, headerdesc, headerdescspecified);

                return BlackBoardWebServices.ConvertStringArrayToObjectDefinition<EnrolledUserDetails>(results[0].item, results[1].item);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetUserDetails", ex);
                return null;
            }
        }
        
        //Get User Details With Named Elements
        public BBUserService.userDetails GetUserDetailsWithNamedElements(string userid, bool extendeddetails, bool extendeddetailsspecified)
        {
            try
            {
                return bbUser.getUserDetailsWithNamedElements(bbPassword, userid, extendeddetails, extendeddetailsspecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetUserDetailsWithNamedElements", ex);
                return null;
            }
        }

        //Get User Details (Xml)
        public string GetUserDetailsXml(string userid, bool extendeddetails, bool extendeddetailsspecified)
        {
            try
            {
                return bbUser.getUserDetailsXML(bbPassword, userid, extendeddetails, extendeddetailsspecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetUserDetailsXml", ex);
                return null;
            }
        }

        //Get User Role In Course
        public string GetUserRoleInCourse(string userid, string courseid)
        {
            try
            {
                return bbUser.getUserRoleInCourse(bbPassword, userid, courseid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetUserRoleInCourse", ex);
                return null;
            }
        }

        //Get User Role In Course With Named Elements
        public BBUserService.role GetUserRoleInCourseWithNamedElements(string userid, string courseid)
        {
            try
            {
                return bbUser.getUserRoleInCourseWithNamedElements(bbPassword, userid, courseid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetUserRoleInCourseWithNamedElements", ex);
                return null;
            }
        }

        //Get User Role In Course (XML)
        public string GetUserRoleInCourseXML(string userid, string courseid)
        {
            try
            {
                return bbUser.getUserRoleInCourseXML(bbPassword, userid, courseid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.GetUserRoleInCourseXML", ex);
                return null;
            }
        }


        //Is User In Course
        public bool IsUserInCourse(string userid, string courseid)
        {
            try
            {
                return bbUser.isUserInCourse(bbPassword, userid, courseid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.IsUserInCourse", ex);
                return false;
            }
        }

        //Is User In Course (XML)
        public string IsUserInCourseXML(string userid, string courseid)
        {
            try
            {
                return bbUser.isUserInCourseXML(bbPassword, userid, courseid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.IsUserInCourseXML", ex);
                return null;
            }
        }


        //Set Or Modify Secondary Portal Roles For Given UserID
        public bool SetOrModifySecondaryPortalRolesForGivenUserID(string userid, string[] roles, out bool returnspecified)
        {
            bool IsAdded = false;

            try
            {
                bbUser.setOrModifySecondaryPortalRolesForGivenUserId(bbPassword, userid, roles, out IsAdded, out returnspecified);

                return IsAdded;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.SetOrModifySecondaryPortalRolesForGivenUserID", ex);
                returnspecified = false;
                return IsAdded;
            }
        }

        //Set Or Modify Secondary Portal Roles For Given UserId (XML)
        public string SetOrModifySecondaryPortalRolesForGivenUserIdXML(string userid, string[] roles)
        {
            try
            {
                return bbUser.setOrModifySecondaryPortalRolesForGivenUserIdXML(bbPassword, userid, roles);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbUser.SetOrModifySecondaryPortalRolesForGivenUserID", ex);
                return null;
            }
        }      
    }
}