/*
 *   bbGradebook Class Library - Blackboard Web Services
 *   $URL: https://idla.springloops.com/source/idla_api/trunk/BlackBoard/bbIDLA/bbGradebook.cs $
 *   Description: Provides C# wrapper methods on Blackboard Gradebook Web Service methods. Some
 *   of the resturn types are defined in the proxy classes while others were specifically
 *   developed for use in the application. These details classes are found in the BlackBoardWebServices.cs
 *   class file
 *  
 *   Created On          : February 2, 2010
 *   Created By          : Alex Loepp
 *   $LastChangedDate: 2010-02-26 12:05:04 -0700 (Fri, 26 Feb 2010) $
 *   $LastChangedBy: alex.loepp $
 *   $LastChangedRevision: 10 $       
 *
 *   Change History:   
 *   0.0.0    ATL   Initial Development.
 *
 */
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Text.RegularExpressions;

namespace bbIDLA
{
    public class BbGradebook
    {
        // Password must match Oscelot web services passwords in BB
        private const string bbPassword = "BbWsPassword";
 
        BBGradebookService.BBGradebookWebServiceService bbGradebook = null;
        
        public BbGradebook()
        {
            bbGradebook = new BBGradebookService.BBGradebookWebServiceService();
        }

        //Add Line item To Given CourseID
        public bool AddLineitemToGivenCourseID(string courseid, string name, string type,
            float pointspossible, bool pointspossiblespecified, float weight, bool weightspecified,
            bool available, bool availablespecified, out bool returnspecified)
        {
             bool IsAdded = false;

            try
            {
                bbGradebook.addLineitemToGivenCourseId(bbPassword, courseid, name, type, pointspossible, 
                    pointspossiblespecified, weight, weightspecified, available, 
                    availablespecified, out IsAdded, out returnspecified);

                return IsAdded;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.AddLineitemToGivenCourseID", ex);
                returnspecified = false;
                return IsAdded;
            }
        }

        //Add Lineitem To Given CourseId (XML)
        public string AddLineitemToGivenCourseIdXML(string courseid, string name, string type,
            float pointspossible, bool pointspossiblespecified, float weight, bool weightspecified,
            bool available, bool availablespecified)
        {
            try
            {
               return bbGradebook.addLineitemToGivenCourseIdXML(bbPassword, courseid, name, type, pointspossible,
                    pointspossiblespecified, weight, weightspecified, available, availablespecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.AddLineitemToGivenCourseIdXML", ex);
                return null;
            }
        }

        //Delete Given Attempt By AttemptBbID
        public bool DeleteGivenAttemptByAttemptBbID(string attemptbbid, out bool returnspecified)
        {
            bool IsDeleted = false;

            try
            {
                bbGradebook.deleteGivenAttemptByAttemptBbId(bbPassword, attemptbbid, out IsDeleted, out returnspecified);

                return IsDeleted;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.DeleteGivenAttemptByAttemptBbID", ex);
                returnspecified = false;
                return IsDeleted;
            }
        }

        //Delete Given Attempt By AttemptBbId (XML)
        public string DeleteGivenAttemptByAttemptBbIdXML(string attemptbbid)
        {
            try
            {
                return bbGradebook.deleteGivenAttemptByAttemptBbIdXML(bbPassword, attemptbbid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.DeleteGivenAttemptByAttemptBbIdXML", ex);
                return null;
            }
        }

        //Delete Line Item By LineItemBbID
        public bool DeleteLineItemByLineItemBbID(string lineitembbid, out bool returnspecified)
        {
            bool IsDeleted = false;

            try
            {
                bbGradebook.deleteLineItemByLineItemBbId(bbPassword, lineitembbid, out IsDeleted, out returnspecified);

                return IsDeleted;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.DeleteLineItemByLineItemBbID", ex);
                returnspecified = false;
                return IsDeleted;
            }
        }

        //Delete Line Item By LineItemBbId (XML)
        public string DeleteLineItemByLineItemBbIdXML(string lineitembbid)
        {
            try
            {
                return bbGradebook.deleteLineItemByLineItemBbIdXML(bbPassword, lineitembbid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.DeleteLineItemByLineItemBbIdXML", ex);
                return null;
            }
        }

        //Delete Outcome Definition By OutcomeDefBbID
        public bool DeleteOutcomeDefinitionByOutcomeDefBbID(string outcomedefbbid, out bool returnspecified)
        {
            bool IsDeleted = false;

            try
            {
                bbGradebook.deleteOutcomeDefinitionByOutcomeDefBbId(bbPassword, outcomedefbbid, out IsDeleted, out returnspecified);

                return IsDeleted;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.DeleteOutcomeDefinitionByOutcomeDefBbID", ex);
                returnspecified = false;
                return IsDeleted;
            }
        }

        //Delete Outcome Definition By OutcomeDefBbId (XML)
        public string DeleteOutcomeDefinitionByOutcomeDefBbIdXML(string outcomedefbbid)
        {
            try
            {
                return bbGradebook.deleteOutcomeDefinitionByOutcomeDefBbIdXML(bbPassword, outcomedefbbid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.DeleteOutcomeDefinitionByOutcomeDefBbIdXML", ex);
                return null;
            }
        }

        //Get All Line Items For CourseID
        public List<GradebookDetails> GetAllLineItemsForCourseID(string courseid, bool headerdesc, bool headerdescspecified)
        {
            try
            {
               var results =  bbGradebook.getAllLineItemsForCourseId(bbPassword, courseid, headerdesc, headerdescspecified);

               List<GradebookDetails> list = new List<GradebookDetails>();

               for (int i = 1; i < results.Length; i++)
               {
                   var data = BlackBoardWebServices.ConvertStringArrayToObjectDefinition<GradebookDetails>(results[0].item, results[i].item);
                   list.Add(data);
               }

               return list;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetAllLineItemsForCourseID", ex);
                return null;
            }
        }

        //Get All Line Items For CourseID With Named Elements
        public BBGradebookService.lineitemDetails[] GetAllLineItemsForCourseIDWithNamedElements(string courseid,  BBGradebookService.commonParams pars)
        {
            try
            {
                var results = bbGradebook.getAllLineItemsForCourseIdWithNamedElements(bbPassword, courseid, pars);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetAllLineItemsForCourseIDWithNamedElements", ex);
                return null;
            }
        }

        //Get All Line Items For CourseId With Named Elements And Params
        public BBGradebookService.lineitemDetails[] GetAllLineItemsForCourseIdWithNamedElementsAndParams(BBGradebookService.lineitemParams pars)
        {
            try
            {
                var results = bbGradebook.getAllLineItemsForCourseIdWithNamedElementsAndParams(pars);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetAllLineItemsForCourseIdWithNamedElementsAndParams", ex);
                return null;
            }
        }

        //Get All Line Items For CourseId (XML)
        public string GetAllLineItemsForCourseIdXML(string courseid)
        {
            try
            {
                return bbGradebook.getAllLineItemsForCourseIdXML(bbPassword, courseid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetAllLineItemsForCourseIdXML", ex);
                return null;
            }
        }

        //TODO: need results to create definition class
        //Get All Outcome Definitions For Given Course
        public BBGradebookService.stringArray[] GetAllOutcomeDefinitionsForGivenCourse(string courseid, bool headerdesc, bool headerdescspecified)
        {
            try
            {
                var results = bbGradebook.getAllOutcomeDefinitionsForGivenCourse(bbPassword, courseid, headerdesc, headerdescspecified);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetAllOutcomeDefinitionsForGivenCourse", ex);
                return null;
            }
        }

        //Get All Outcome Definitions For Given Course With Named Elements
        public BBGradebookService.outcomeDefinitionDetails[] GetAllOutcomeDefinitionsForGivenCourseWithNamedElements(string courseid)
        {
            try
            {
                var results = bbGradebook.getAllOutcomeDefinitionsForGivenCourseWithNamedElements(bbPassword, courseid);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetAllOutcomeDefinitionsForGivenCourseWithNamedElements", ex);
                return null;
            }
        }

        //Get All Outcome Definitions For Given Course (XML)
        public string GetAllOutcomeDefinitionsForGivenCourseXML(string courseid)
        {
            try
            {
                var results = bbGradebook.getAllOutcomeDefinitionsForGivenCourseXML(bbPassword, courseid);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetAllOutcomeDefinitionsForGivenCourseXML", ex);
                return null;
            }
        }

        //Get All Outcomes For Given OutcomeDefBbId With Named Elements
        public BBGradebookService.outcomeDetails[] GetAllOutcomesForGivenOutcomeDefBbIdWithNamedElements(string outcomedefbbid)
        {
            try
            {
                var results = bbGradebook.getAllOutcomesForGivenOutcomeDefBbIdWithNamedElements(bbPassword, outcomedefbbid);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetAllOutcomesForGivenOutcomeDefBbIdWithNamedElements", ex);
                return null;
            }
        }

        //Get All Outcomes For Given OutcomeDefBbId (XML)
        public string GetAllOutcomesForGivenOutcomeDefBbIdXML(string outcomedefbbid)
        {
            try
            {
                var results = bbGradebook.getAllOutcomesForGivenOutcomeDefBbIdXML(bbPassword, outcomedefbbid);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetAllOutcomesForGivenOutcomeDefBbIdXML", ex);
                return null;
            }
        }

        //Get All Score Details For Given LineItemBbId With Named Elements
        public BBGradebookService.scoreDetails[] GetAllScoreDetailsForGivenLineItemBbIdWithNamedElements(string lineitembbid, bool extendeddetails, bool extendeddetailsspecified)
        {
            try
            {
                var results = bbGradebook.getAllScoreDetailsForGivenLineItemBbIdWithNamedElements(bbPassword, lineitembbid, extendeddetails, extendeddetailsspecified);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetAllScoreDetailsForGivenLineItemBbIdWithNamedElements", ex);
                return null;
            }
        }

        //Get All Score Details For Given LineItemBbId (XML)
        public string GetAllScoreDetailsForGivenLineItemBbIdXML(string lineitembbid, bool extendeddetails, bool extendeddetailsspecified)
        {
            try
            {
                return bbGradebook.getAllScoreDetailsForGivenLineItemBbIdXML(bbPassword, lineitembbid, extendeddetails, extendeddetailsspecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetAllScoreDetailsForGivenLineItemBbIdXML", ex);
                return null;
            }
        }

        //TODO: need results to create definition class
        //Get All Scores For Given LineItemBbID
        public BBGradebookService.stringArray[] GetAllScoresForGivenLineItemBbID(string lineitembbid, bool extendeddetails, bool extendeddetailsspecified, bool headerdesc, bool headerdescspecified)
        {
            try
            {
                var results = bbGradebook.getAllScoresForGivenLineItemBbId(bbPassword, lineitembbid,
                    extendeddetails, extendeddetailsspecified, headerdesc, headerdescspecified);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetAllScoresForGivenLineItemBbID", ex);
                return null;
            }
        }

        //Get Attempt Details By Given OutcomeDefBbId With Named Elements
        public BBGradebookService.attemptDetails[] GetAttemptDetailsByGivenOutcomeDefBbIdWithNamedElements(string outcomedefbbid, bool extendeddetails, bool extendeddetailsspecified)
        {
            try
            {
                var results = bbGradebook.getAttemptDetailsByGivenOutcomeDefBbIdWithNamedElements(bbPassword, outcomedefbbid,
                    extendeddetails, extendeddetailsspecified);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetAttemptDetailsByGivenOutcomeDefBbIdWithNamedElements", ex);
                return null;
            }
        }

        //Get Attempt Details By Given OutcomeDefBbId (XML)
        public string GetAttemptDetailsByGivenOutcomeDefBbIdXML(string outcomedefbbid, bool extendeddetails, bool extendeddetailsspecified)
        {
            try
            {
                var results = bbGradebook.getAttemptDetailsByGivenOutcomeDefBbIdXML(bbPassword, outcomedefbbid,
                    extendeddetails, extendeddetailsspecified);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetAttemptDetailsByGivenOutcomeDefBbIdXML", ex);
                return null;
            }
        }

        //TODO: need results to create definition class
        //Get Attempts By Given OutcomeDefBbID
        public BBGradebookService.stringArray[] GetAttemptsByGivenOutcomeDefBbID(string outcomedefbbid, bool extendeddetails, 
            bool extendeddetailsspecified, bool headerdesc, bool headerdescspecified)
        {
            try
            {
                var results = bbGradebook.getAttemptsByGivenOutcomeDefBbId(bbPassword, outcomedefbbid,
                    extendeddetails, extendeddetailsspecified,headerdesc,headerdescspecified);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetAttemptsByGivenOutcomeDefBbID", ex);
                return null;
            }
        }

        //TODO: need results to create definition class
        //Get Gradebook Settings For Given CourseID
        public BBGradebookService.stringArray[] GetGradebookSettingsForGivenCourseID(string courseid, bool headerdesc, bool headerdescspecified)
        {
            try
            {
                var results = bbGradebook.getGradebookSettingsForGivenCourseId(bbPassword, courseid, headerdesc, headerdescspecified);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetGradebookSettingsForGivenCourseID", ex);
                return null;
            }
        }

        //Get Gradebook Settings For Given CourseId With Named Elements
        public BBGradebookService.gradeBookSettingsDetails GetGradebookSettingsForGivenCourseIdWithNamedElements(string courseid)
        {
            try
            {
                var results = bbGradebook.getGradebookSettingsForGivenCourseIdWithNamedElements(bbPassword, courseid);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetGradebookSettingsForGivenCourseIdWithNamedElements", ex);
                return null;
            }
        }

        //Get Gradebook Settings For Given CourseId (XML)
        public string GetGradebookSettingsForGivenCourseIdXML(string courseid)
        {
            try
            {
                var results = bbGradebook.getGradebookSettingsForGivenCourseIdXML(bbPassword, courseid);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetGradebookSettingsForGivenCourseIdXML", ex);
                return null;
            }
        }

        //Get LineitemDetails For Given LineitemBbId With Named Elements
        public BBGradebookService.lineitemDetails GetLineitemDetailsForGivenLineitemBbIdWithNamedElements(string lineitembbid, BBGradebookService.lineitemParams pars)
        {
            try
            {
                var results = bbGradebook.getLineitemDetailsForGivenLineitemBbIdWithNamedElements(bbPassword, lineitembbid, pars);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetLineitemDetailsForGivenLineitemBbIdWithNamedElements", ex);
                return null;
            }
        }

        //Get Lineitem Details For Given LineitemBbId With Named Elements And Params
        public BBGradebookService.lineitemDetails GetLineitemDetailsForGivenLineitemBbIdWithNamedElementsAndParams(BBGradebookService.lineitemParams pars)
        {
            try
            {
                var results = bbGradebook.getLineitemDetailsForGivenLineitemBbIdWithNamedElementsAndParams(pars);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetLineitemDetailsForGivenLineitemBbIdWithNamedElementsAndParams", ex);
                return null;
            }
        }

        //Get Lineitem Details For Given LineitemBbId (XML)
        public string GetLineitemDetailsForGivenLineitemBbIdXML(string lineitembbid)
        {
            try
            {
                return bbGradebook.getLineitemDetailsForGivenLineitemBbIdXML(bbPassword, lineitembbid);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetLineitemDetailsForGivenLineitemBbIdXML", ex);
                return null;
            }
        }

        //Get Outcome Definition Details From OutcomeDefinitionBbId With Named Elements
        public BBGradebookService.outcomeDefinitionDetails GetOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdWithNamedElements(string outcomedefbbid)
        {
            try
            {
                var results = bbGradebook.getOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdWithNamedElements(bbPassword, outcomedefbbid);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdWithNamedElements", ex);
                return null;
            }
        }

        //Get Score Details For Given CourseMembershipBbId And LineItemBbId With Named Elements
        public BBGradebookService.scoreDetails GetScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdWithNamedElements(string coursemembershipbbid, string lineitembbid, bool extendeddetails, bool extendeddetailsspecified)
        {
            try
            {
                var results = bbGradebook.getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdWithNamedElements(
                    bbPassword, coursemembershipbbid, lineitembbid, extendeddetails, extendeddetailsspecified);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdWithNamedElements", ex);
                return null;
            }
        }

        //Get Score Details For Given Course MembershipBbId And LineItemBbId (XML)
        public string GetScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdXML(string coursemembershipbbid, string lineitembbid, bool extendeddetails, bool extendeddetailsspecified)
        {
            try
            {
                var results = bbGradebook.getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdXML(
                    bbPassword, coursemembershipbbid, lineitembbid, extendeddetails, extendeddetailsspecified);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdXML", ex);
                return null;
            }
        }

        //Get Score Details For Given ScoreBbId With Named Elements
        public BBGradebookService.scoreDetails GetScoreDetailsForGivenScoreBbIdWithNamedElements(string scorebbid, bool extendeddetails, bool extendeddetailsspecified)
        {
            try
            {
                var results = bbGradebook.getScoreDetailsForGivenScoreBbIdWithNamedElements(bbPassword, scorebbid, extendeddetails, extendeddetailsspecified);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetScoreDetailsForGivenScoreBbIdWithNamedElements", ex);
                return null;
            }
        }

        //Get Score Details For Given ScoreBbId (XML)
        public string GetScoreDetailsForGivenScoreBbIdXML(string scorebbid, bool extendeddetails, bool extendeddetailsspecified)
        {
            try
            {
                var results = bbGradebook.getScoreDetailsForGivenScoreBbIdXML(bbPassword, scorebbid, extendeddetails, extendeddetailsspecified);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetScoreDetailsForGivenScoreBbIdXML", ex);
                return null;
            }
        }

        //Get Score Details For Given UserId And LineItemBbId With Named Elements
        public BBGradebookService.scoreDetails GetScoreDetailsForGivenUserIdAndLineItemBbIdWithNamedElements(string userid, string lineitembbid, bool extendeddetails, bool extendeddetailsspecified)
        {
            try
            {
                var results = bbGradebook.getScoreDetailsForGivenUserIdAndLineItemBbIdWithNamedElements(bbPassword, userid, lineitembbid, extendeddetails, extendeddetailsspecified);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetScoreDetailsForGivenUserIdAndLineItemBbIdWithNamedElements", ex);
                return null;
            }
        }

        //Get Score Details For Given UserId And LineItemBbId (XML)
        public string GetScoreDetailsForGivenUserIdAndLineItemBbIdXML(string userid, string lineitembbid, bool extendeddetails, bool extendeddetailsspecified)
        {
            try
            {
                var results = bbGradebook.getScoreDetailsForGivenUserIdAndLineItemBbIdXML(bbPassword, userid, lineitembbid, extendeddetails, extendeddetailsspecified);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetScoreDetailsForGivenUserIdAndLineItemBbIdXML", ex);
                return null;
            }
        }

        //TODO: need results to create definition class
        //Get Score For Given CourseMembershipBbId And LineItemBbID
        public BBGradebookService.stringArray[] GetScoreForGivenCourseMembershipBbIdAndLineItemBbID(
            string coursemembershipbbid, string lineitembbid,
            bool extendeddetails, bool extendeddetailsspecified,
            bool headerdesc, bool headerdescspecified)
        {
            try
            {
                var results = bbGradebook.getScoreForGivenCourseMembershipBbIdAndLineItemBbId(bbPassword,
                    coursemembershipbbid, lineitembbid, extendeddetails, extendeddetailsspecified, headerdesc, headerdescspecified);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetScoreForGivenCourseMembershipBbIdAndLineItemBbID", ex);
                return null;
            }
        }

        //TODO: nned results to create definition class
        //Get Score For Given UserId And LineItemBbID
        public BBGradebookService.stringArray[] GetScoreForGivenUserIdAndLineItemBbID(
            string userid, string lineitembbid,
            bool extendeddetails, bool extendeddetailsspecified,
            bool headerdesc, bool headerdescspecified)
        {
            try
            {
                var results = bbGradebook.getScoreForGivenUserIdAndLineItemBbId(bbPassword,
                    userid, lineitembbid, extendeddetails, extendeddetailsspecified, headerdesc, headerdescspecified);
                return results;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.GetScoreForGivenUserIdAndLineItemBbID", ex);
                return null;
            }
        }

        //Set Line Item Weight By LineItemBbID
        public bool SetLineItemWeightByLineItemBbID(string lineitembbid, string weight, out bool returnspecified)
        {
            bool IsSet = false;

            try
            {
                bbGradebook.setLineItemWeightByLineItemBbId(bbPassword, lineitembbid, weight, out IsSet, out returnspecified);
                return IsSet;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.SetLineItemWeightByLineItemBbID", ex);
                returnspecified = false;
                return IsSet;
            }
        }

        //Set Line Item Weight By Line ItemBbID
        public string SetLineItemWeightByLineItemBbID(string lineitembbid, string weight)
        {
            try
            {
                return bbGradebook.setLineItemWeightByLineItemBbIdXML(bbPassword, lineitembbid, weight);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.SetLineItemWeightByLineItemBbID", ex);
                return null;
            }
        }

        //Set Weight By Item Or Category For Gradebook In Given CourseID
        public bool SetWeightByItemOrCategoryForGradebookInGivenCourseID(string courseid, string itemorcategory, out bool returnspecified)
        {
            bool IsSet = false;

            try
            {
                bbGradebook.setWeightByItemOrCategoryForGradebookInGivenCourseId(bbPassword, courseid, itemorcategory, out IsSet, out returnspecified);
                return IsSet;
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.SetWeightByItemOrCategoryForGradebookInGivenCourseID", ex);
                returnspecified = false;
                return IsSet;
            }
        }

        //Set Weight By Item Or Category For Gradebook In Given CourseId (XML)
        public string SetWeightByItemOrCategoryForGradebookInGivenCourseIdXML(string courseid, string itemorcategory)
        {
            try
            {
                return bbGradebook.setWeightByItemOrCategoryForGradebookInGivenCourseIdXML(bbPassword, courseid, itemorcategory);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbGradebook.SetWeightByItemOrCategoryForGradebookInGivenCourseIdXML", ex);
                return null;
            }
        }
    }
}