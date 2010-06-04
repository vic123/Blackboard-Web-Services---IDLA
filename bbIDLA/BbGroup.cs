/*
 *   bbGroup Class Library - Blackboard Web Services
 *   $URL: https://idla.springloops.com/source/idla_api/trunk/BlackBoard/bbIDLA/BbGroup.cs $
 *   Description: Provides C# wrapper methods on Blackboard Group Web Service methods. These are the
 *   additional methods added by Viktor and are supplemental in part to the Course and User methods as 
 *   well as adding portal and  observer specific methods.Some of the resturn types are defined in 
 *   the proxy classes while others were specifically developed for use in the application. 
 *   These details classes are found in the BlackBoardWebServices.cs class file
 *  
 *   Created On          : March 15, 2010
 *   Created By          : Alex Loepp
 *   $LastChangedDate: 2010-03-15 17:00:12 -0700 (Mon, 15 Mar 2010) $
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
using bbIDLA.BBGroupService;

namespace bbIDLA
{

    public class BbGroup
    {
        // Password must match Oscelot web services passwords in BB
        private const string bbPassword = "BbWsPassword";
        BBGroupWebServiceService bbGroup = null;

        public BbGroup()
        {
            bbGroup = new BBGroupWebServiceService();
        }

        //Add Given User To Given Group
        public bool AddGivenUserToGivenGroup(string userId, string groupBbId, out bool resturnSpecified)
        {
            try
            {
                bool @return;
                bbGroup.addGivenUserToGivenGroup(bbPassword, userId, groupBbId, out @return, out resturnSpecified);
                return @return;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbPassword.AddGivenUserToGivenGroup", ex);
                resturnSpecified = false;
                return false;
            }
        }

        //Add Given User To Given Group (XML)
        public string AddGivenUserToGivenGroupXML(string userId, string groupBbId)
        {
            try
            {
                return bbGroup.addGivenUserToGivenGroupXML(bbPassword, userId, groupBbId);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbPassword.AddGivenUserToGivenGroupXML", ex);
                return null;
            }
        }

        //Add Group To Given Course
        public bool AddGroupToGivenCourse(string courseId, string title, string description,
            string descType, bool available, bool availableSpecified, bool chatRoomAvailable, bool chatRoomAvailableSpecified,
            bool discussionBoardAvailable, bool discussionBoardAvailableSpecified, bool emailAvailable,
            bool emailAvailableSpecified, bool transferAvailable, bool transferAvailableSpecified,
            out bool returnSpecified)
        {
            try
            {
                bool @return;
                bbGroup.addGroupToGivenCourse(bbPassword, courseId, title, description, descType, available, availableSpecified, chatRoomAvailable, chatRoomAvailableSpecified, discussionBoardAvailable,
               discussionBoardAvailableSpecified, emailAvailable, emailAvailableSpecified,
               transferAvailable, transferAvailableSpecified, out @return, out returnSpecified);
                return @return;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbPassword.AddGroupToGivenCourse", ex);
                returnSpecified = false;
                return false;
            }
        }

        //Add Group To Given Course (XML)
        public string AddGroupToGivenCourseXML(string courseId, string title, string description,
            string descType, bool available, bool availableSpecified, bool chatRoomAvailable, bool chatRoomAvailableSpecified,
            bool discussionBoardAvailable, bool discussionBoardAvailableSpecified, bool emailAvailable,
            bool emailAvailableSpecified, bool transferAvailable, bool transferAvailableSpecified)
        {
            try
            {
                return bbGroup.addGroupToGivenCourseXML(bbPassword, courseId, title, description, descType, available, availableSpecified, chatRoomAvailable, chatRoomAvailableSpecified, discussionBoardAvailable,
               discussionBoardAvailableSpecified, emailAvailable, emailAvailableSpecified,
               transferAvailable, transferAvailableSpecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbPassword.AddGroupToGivenCourseXML", ex);
                return null;
            }
        }

        //Delete Given User From Given Group
        public bool DeleteGivenUserFromGivenGroup(string userId, string groupBbId, out bool returnSpecified)
        {
            try
            {
                bool @return;
                bbGroup.deleteGivenUserFromGivenGroup(bbPassword, userId, groupBbId, out @return, out returnSpecified);
                return @return;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbPassword.DeleteGivenUserFromGivenGroup", ex);
                returnSpecified = false;
                return false;
            }
        }

        //Delete Given User From Given Group (XML)
        public string DeleteGivenUserFromGivenGroupXML(string userId, string groupBbId)
        {
            try
            {
                return bbGroup.deleteGivenUserFromGivenGroupXML(bbPassword, userId, groupBbId);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbPassword.DeleteGivenUserFromGivenGroupXML", ex);
                return null;
            }
        }

        //Delete Group
         public bool DeleteGroup(string groupBbId, out bool returnSpecified)
        {
            try
            {
                bool @return;
                bbGroup.deleteGroup(bbPassword, groupBbId, out @return, out returnSpecified);
                return @return;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbPassword.DeleteGroup", ex);
                returnSpecified = false;
                return false;
            }
        }

         //Delete Group (XML)
         public string DeleteGroupXML(string groupBbId)
         {
             try
             {
                return bbGroup.deleteGroupXML(bbPassword, groupBbId);
             }
             catch (Exception ex)
             {
                 BlackBoardWebServices.LogError("bbPassword.DeleteGroupXML", ex);
                 return null;
             }
         }

         //Get Group Details For Given CourseId
         public stringArray[] GetGroupDetailsForGivenCourseId(string courseId, bool headerDesc, bool headerDescSpecified)
         {
             try
             {
                 return bbGroup.getGroupDetailsForGivenCourseId(bbPassword, courseId, headerDesc, headerDescSpecified);
             }
             catch (Exception ex)
             {
                 BlackBoardWebServices.LogError("bbPassword.GetGroupDetailsForGivenCourseId", ex);
                 return null;
             }
         }

         //Get Group Details For Given CourseId With Named Elements
         public groupDetails[] GetGroupDetailsForGivenCourseIdWithNamedElements(string courseId)
         {
             try
             {
                 return bbGroup.getGroupDetailsForGivenCourseIdWithNamedElements(bbPassword, courseId);
             }
             catch (Exception ex)
             {
                 BlackBoardWebServices.LogError("bbPassword.GetGroupDetailsForGivenCourseIdWithNamedElements", ex);
                 return null;
             }
         }

         //Get Group Details For Given CourseId (XML)
         public string GetGroupDetailsForGivenCourseIdXML(string courseId)
         {
             try
             {
                 return bbGroup.getGroupDetailsForGivenCourseIdXML(bbPassword, courseId);
             }
             catch (Exception ex)
             {
                 BlackBoardWebServices.LogError("bbPassword.GetGroupDetailsForGivenCourseIdXML", ex);
                 return null;
             }
         }

         //Get Group Details For Given GroupBbId
         public stringArray[] GetGroupDetailsForGivenGroupBbId(string groupBbId, bool headerDesc, bool headerDescSpecified)
         {
             try
             {
                 return bbGroup.getGroupDetailsForGivenGroupBbId(bbPassword, groupBbId, headerDesc, headerDescSpecified);
             }
             catch (Exception ex)
             {
                 BlackBoardWebServices.LogError("bbPassword.GetGroupDetailsForGivenGroupBbId", ex);
                 return null;
             }
         }

         //Get Group Details For Given GroupBbId With Named Elements
         public groupDetails GetGroupDetailsForGivenGroupBbIdWithNamedElements(string groupBbId)
         {
             try
             {
                 return bbGroup.getGroupDetailsForGivenGroupBbIdWithNamedElements(bbPassword, groupBbId);
             }
             catch (Exception ex)
             {
                 BlackBoardWebServices.LogError("bbPassword.GetGroupDetailsForGivenGroupBbIdWithNamedElements", ex);
                 return null;
             }
         }

         //Get Group Details For Given GroupBbId (XML)
         public string GetGroupDetailsForGivenGroupBbIdXML(string groupBbId)
         {
             try
             {
                 return bbGroup.getGroupDetailsForGivenGroupBbIdXML(bbPassword, groupBbId);
             }
             catch (Exception ex)
             {
                 BlackBoardWebServices.LogError("bbPassword.GetGroupDetailsForGivenGroupBbIdXML", ex);
                 return null;
             }
         }

         //Get Group Member Details For Given GroupBbId With Named Elements
         public groupMemberDetails[] GetGroupMemberDetailsForGivenGroupBbIdWithNamedElements(string groupBbId)
         {
             try
             {
                 return bbGroup.getGroupMemberDetailsForGivenGroupBbIdWithNamedElements(bbPassword, groupBbId);
             }
             catch (Exception ex)
             {
                 BlackBoardWebServices.LogError("bbPassword.GetGroupMemberDetailsForGivenGroupBbIdWithNamedElements", ex);
                 return null;
             }
         }

         //Get Group Member Details For Given GroupBbId (XML)
         public string GetGroupMemberDetailsForGivenGroupBbIdXML(string groupBbId)
         {
             try
             {
                 return bbGroup.getGroupMemberDetailsForGivenGroupBbIdXML(bbPassword, groupBbId);
             }
             catch (Exception ex)
             {
                 BlackBoardWebServices.LogError("bbPassword.GetGroupMemberDetailsForGivenGroupBbIdXML", ex);
                 return null;
             }
         }

         //Get Group Members For Given GroupBbId
         public string[] GetGroupMembersForGivenGroupBbId(string groupBbId)
         {
             try
             {
                 return bbGroup.getGroupMembersForGivenGroupBbId(bbPassword, groupBbId);
             }
             catch (Exception ex)
             {
                 BlackBoardWebServices.LogError("bbPassword.GetGroupMembersForGivenGroupBbId", ex);
                 return null;
             }
         }

         //Modify Group
        public bool ModifyGroup(string groupBbId, string title, string description, string descType,  bool available, bool availableSpecified, bool chatRoomAvailable, bool chatRoomAvailableSpecified,
            bool discussionBoardAvailable, bool discussionBoardAvailableSpecified, bool emailAvailable,
            bool emailAvailableSpecified, bool transferAvailable, bool transferAvailableSpecified,
            out bool returnSpecified)
         {
             try
             {
                 bool @return;
                 bbGroup.modifyGroup(bbPassword, groupBbId, title,description,descType,available,
                    availableSpecified,chatRoomAvailable,chatRoomAvailableSpecified,  discussionBoardAvailable, 
                    discussionBoardAvailableSpecified, emailAvailable,emailAvailableSpecified, 
                    transferAvailable, transferAvailableSpecified, out @return, out returnSpecified);
                 return @return;
             }
             catch (Exception ex)
             {
                 BlackBoardWebServices.LogError("bbPassword.ModifyGroup", ex);
                 returnSpecified = false;
                 return false;
             }
         }

        //Modify Group (XML)
        public string ModifyGroupXML(string groupBbId, string title, string description, string descType, bool available, bool availableSpecified, bool chatRoomAvailable, bool chatRoomAvailableSpecified,
            bool discussionBoardAvailable, bool discussionBoardAvailableSpecified, bool emailAvailable,
            bool emailAvailableSpecified, bool transferAvailable, bool transferAvailableSpecified)
        {
            try
            {
                   return bbGroup.modifyGroupXML(bbPassword, groupBbId, title, description, descType, available,
                   availableSpecified, chatRoomAvailable, chatRoomAvailableSpecified, discussionBoardAvailable,
                   discussionBoardAvailableSpecified, emailAvailable, emailAvailableSpecified,
                   transferAvailable, transferAvailableSpecified);
      
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbPassword.ModifyGroupXML", ex);
                return null;
            }
        }
    }
}
