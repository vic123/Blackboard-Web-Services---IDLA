/*
 *   bbDiscussionBoard Class Library - Blackboard Web Services
 *   $URL: https://idla.springloops.com/source/idla_api/trunk/BlackBoard/bbIDLA/BbDiscussionBoard.cs $
 *   Description: Provides C# wrapper methods on Blackboard DiscussionBoard Web Service methods. These are the
 *   additional methods added by Viktor and are supplemental in part to the Course and User methods as 
 *   well as adding portal and  observer specific methods.Some of the resturn types are defined in 
 *   the proxy classes while others were specifically developed for use in the application. 
 *   These details classes are found in the BlackBoardWebServices.cs class file
 *  
 *   Created On          : March 14, 2010
 *   Created By          : Alex Loepp
 *   $LastChangedDate: 2010-03-14 10:44:16 -0700 (Sun, 14 Mar 2010) $
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
using bbIDLA.BBDiscussionBoardService;

namespace bbIDLA
{
    public class BbDiscussionBoard
    {
        // Password must match Oscelot web services passwords in BB
        private const string bbPassword = "BbWsPassword";
        BBDiscussionBoardWebServiceService bbDiscussion = null;

        public BbDiscussionBoard()
        {
            bbDiscussion = new BBDiscussionBoardWebServiceService();
        }

        //Add Conference
        public string AddConference(string courseId, string groupBbId, string conferenceType, bool available,  bool availableSpecified, string title, string description)
        {
            try
            {
                return bbDiscussion.addConference(bbPassword, courseId, groupBbId, conferenceType, available, availableSpecified, title, description);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbDiscussion.AddConference", ex);
                return null;
            }
        }

        //Add Forum To Given Conference BbId
        public string AddForumToGivenConferenceBbId(string confBbId, string title, string description, string descType, bool available, bool availableSpecified)
        {
            try
            {
                return bbDiscussion.addForumToGivenConferenceBbId(bbPassword, confBbId, title, description, descType, available, availableSpecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbDiscussion.AddForumToGivenConferenceBbId", ex);
                return null;
            }
        }

        //Add Message To Given Forum BbId Or MessageBbId
        public string AddMessageToGivenForumBbIdOrMessageBbId(string forumBbId, string parentMessageBbId, string subject, string body)
        {
            try
            {
                return bbDiscussion.addMessageToGivenForumBbIdOrMessageBbId(bbPassword, forumBbId, parentMessageBbId, subject, body);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbDiscussion.AddMessageToGivenForumBbIdOrMessageBbId", ex);
                return null;
            }
        }

        //Delete All Conferences In Given CourseBbId
       public string DeleteAllConferencesInGivenCourseBbId(string courseBbId)
        {
            try
            {
                return bbDiscussion.deleteAllConferencesInGivenCourseBbId(bbPassword, courseBbId);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbDiscussion.DeleteAllConferencesInGivenCourseBbId", ex);
                return null;
            }
        }

       //Delete All Forums In Given ConferenceBbId
       public string DeleteAllForumsInGivenConferenceBbId(string confBbId)
       {
           try
           {
               return bbDiscussion.deleteAllForumsInGivenConferenceBbId(bbPassword, confBbId);
           }
           catch (Exception ex)
           {
               BlackBoardWebServices.LogError("bbDiscussion.DeleteAllForumsInGivenConferenceBbId", ex);
               return null;
           }
       }

       //Delete All Threads In Given ForumBbId
        public string DeleteAllThreadsInGivenForumBbId(string forumBbId)
       {
           try
           {
               return bbDiscussion.deleteAllThreadsInGivenForumBbId(bbPassword, forumBbId);
           }
           catch (Exception ex)
           {
               BlackBoardWebServices.LogError("bbDiscussion.DeleteAllThreadsInGivenForumBbId", ex);
               return null;
           }
       }

        //Delete Given Conference And Sub Forums
        public string DeleteGivenConferenceAndSubForums(string confBbId)
        {
            try
            {
                return bbDiscussion.deleteGivenConferenceAndSubForums(bbPassword, confBbId);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbDiscussion.DeleteGivenConferenceAndSubForums", ex);
                return null;
            }
        }

        //Delete Given Forum And Sub Threads
        public string DeleteGivenForumAndSubThreads(string forumBbId)
        {
            try
            {
                return bbDiscussion.deleteGivenForumAndSubThreads(bbPassword, forumBbId);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbDiscussion.DeleteGivenForumAndSubThreads", ex);
                return null;
            }
        }

        //Delete Message And All Sub Messages
        public string DeleteMessageAndAllSubMessages(string messageBbId)
        {
            try
            {
                return bbDiscussion.deleteMessageAndAllSubMessages(bbPassword, messageBbId);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbDiscussion.DeleteMessageAndAllSubMessages", ex);
                return null;
            }
        }

        //Get All Group Conference Details For Given CourseId
        public stringArray[] GetAllGroupConferenceDetailsForGivenCourseId(string courseId, bool headerDesc, bool headerDescSpecified)
        {
            try
            {
                return bbDiscussion.getAllGroupConferenceDetailsForGivenCourseId(bbPassword, courseId, headerDesc, headerDescSpecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbDiscussion.GetAllGroupConferenceDetailsForGivenCourseId", ex);
                return null;
            }
        }

        //Get All Messages For Given Thread In Given ForumBbId
         public stringArray[] GetAllMessagesForGivenThreadInGivenForumBbId(string threadTopLevelMessageBbId, string forumBbId, bool headerDesc, bool headerDescSpecified)
         {
            try
            {
                return bbDiscussion.getAllMessagesForGivenThreadInGivenForumBbId(bbPassword, threadTopLevelMessageBbId, forumBbId, headerDesc, headerDescSpecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbDiscussion.GetAllMessagesForGivenThreadInGivenForumBbId", ex);
                return null;
            }
        }

         //Get All Types Of Conferences For Given CourseId
         public stringArray[] GetAllTypesOfConferencesForGivenCourseId(string courseID, bool headerDesc, bool headerDescSpecified)
         {
             try
             {
                 return bbDiscussion.getAllTypesOfConferencesForGivenCourseId(bbPassword, courseID, headerDesc, headerDescSpecified);
             }
             catch (Exception ex)
             {
                 BlackBoardWebServices.LogError("bbDiscussion.GetAllTypesOfConferencesForGivenCourseId", ex);
                 return null;
             }
         }

         //Get Conference Details For Given ConferenceBbId
         public stringArray[] GetConferenceDetailsForGivenConferenceBbId(string confBbId, bool headerDesc, bool headerDescSpecified)
         {
             try
             {
                 return bbDiscussion.getConferenceDetailsForGivenConferenceBbId(bbPassword, confBbId, headerDesc, headerDescSpecified);
             }
             catch (Exception ex)
             {
                 BlackBoardWebServices.LogError("bbDiscussion.GetConferenceDetailsForGivenConferenceBbId", ex);
                 return null;
             }
         }

         //Get Course Conference Details For Given CourseId
         public stringArray[] GetCourseConferenceDetailsForGivenCourseId(string courseId, bool headerDesc, bool headerDescSpecified)
         {
             try
             {
                 return bbDiscussion.getCourseConferenceDetailsForGivenCourseId(bbPassword, courseId, headerDesc, headerDescSpecified);
             }
             catch (Exception ex)
             {
                 BlackBoardWebServices.LogError("bbDiscussion.GetCourseConferenceDetailsForGivenCourseId", ex);
                 return null;
             }
         }

         //Get Forum Details For Given ConferenceBbId
         public stringArray[] GetForumDetailsForGivenConferenceBbId(string confBbIdD, bool headerDesc, bool headerDescSpecified)
         {
             try
             {
                 return bbDiscussion.getForumDetailsForGivenConferenceBbId(bbPassword, confBbIdD, headerDesc, headerDescSpecified);
             }
             catch (Exception ex)
             {
                 BlackBoardWebServices.LogError("bbDiscussion.GetForumDetailsForGivenConferenceBbId", ex);
                 return null;
             }
         }

         //Get Group Conference Details For Given GroupBbId
         public stringArray[] GetGroupConferenceDetailsForGivenGroupBbId(string groupBbId, bool headerDesc, bool headerDescSpecified)
         {
             try
             {
                 return bbDiscussion.getGroupConferenceDetailsForGivenGroupBbId(bbPassword, groupBbId, headerDesc, headerDescSpecified);
             }
             catch (Exception ex)
             {
                 BlackBoardWebServices.LogError("bbDiscussion.GetGroupConferenceDetailsForGivenGroupBbId", ex);
                 return null;
             }
         }

         //Get Immediate Child Messages For Given MessageBbId
        public stringArray[] GetImmediateChildMessagesForGivenMessageBbId(string messageBbId, bool headerDesc, bool headerDescSpecified)
         {
             try
             {
                 return bbDiscussion.getImmediateChildMessagesForGivenMessageBbId(bbPassword, messageBbId, headerDesc, headerDescSpecified);
             }
             catch (Exception ex)
             {
                 BlackBoardWebServices.LogError("bbDiscussion.GetGroupConferenceDetailsForGivenGroupBbId", ex);
                 return null;
             }
         }

        //Get Top Level Message For Given ForumBbId
        public stringArray[] GetTopLevelMessageForGivenForumBbId(string forumBbID, bool headerDesc, bool headerDescSpecified)
        {
            try
            {
                return bbDiscussion.getTopLevelMessageForGivenForumBbId(bbPassword, forumBbID, headerDesc, headerDescSpecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbDiscussion.GetGroupConferenceDetailsForGivenGroupBbId", ex);
                return null;
            }
        }
    }
}