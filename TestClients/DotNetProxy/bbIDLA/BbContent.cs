/*
 *   bbContent Class Library - Blackboard Web Services
 *   $URL: https://idla.springloops.com/source/idla_api/trunk/BlackBoard/bbIDLA/BbContent.cs $
 *   Description: Provides C# wrapper methods on Blackboard Content Web Service methods. These are the
 *   additional methods added by Viktor and are supplemental in part to the Course and User methods as 
 *   well as adding portal and  observer specific methods.Some of the resturn types are defined in 
 *   the proxy classes while others were specifically developed for use in the application. 
 *   These details classes are found in the BlackBoardWebServices.cs class file
 *  
 *   Created On          : March 13, 2010
 *   Created By          : Alex Loepp
 *   $LastChangedDate: 2010-03-13 15:32:09 -0700 (Sat, 13 Mar 2010) $
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
using bbIDLA.BBContentService;

namespace bbIDLA
{
    public class BbContent
    {
        // Password must match Oscelot web services passwords in BB
        private const string bbPassword = "BbWsPassword";
        BBContentWebService bbContent = null;

        public BbContent()
        {
            bbContent = new BBContentWebService();
        }

        //Delete Content By ContentBbId
        public string DeleteContentByContentBbId(string contentBbId)
        {
            try
            {
                return bbContent.deleteContentByContentBbId(bbPassword, contentBbId);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbContent.DeleteContentByContentBbId", ex);
                return null;
            }
        }

        //Get Child Content From Parent ContentBbId
        public stringArray[] GetChildContentFromParentContentBbId(string parentContentBbId, bool headerDesc, bool headerDescSpecified)
        {
            try
            {
                return bbContent.getChildContentFromParentContentBbId(bbPassword, parentContentBbId, headerDesc, headerDescSpecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbContent.GetChildContentFromParentContentBbId", ex);
                return null;
            }
        }

        //Get Content Details From Content BbId
        public stringArray[] GetContentDetailsFromContentBbId(string contentId, bool headerDesc, bool headerDescSpecified)
        {
            try
            {
                return bbContent.getContentDetailsFromContentBbId(bbPassword, contentId, headerDesc, headerDescSpecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbContent.GetChildContentFromParentContentBbId", ex);
                return null;
            }
        }

        //Get Content Toc Details For Given Course
        public stringArray[] GetContentTocDetailsForGivenCourse(string courseId, bool headerDesc, bool headerDescSpecified)
        {
            try
            {
                return bbContent.getContentTocDetailsForGivenCourse(bbPassword, courseId, headerDesc, headerDescSpecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbContent.GetContentTocDetailsForGivenCourse", ex);
                return null;
            }
        }

        //Get File Details From ContentBbId
        public stringArray[] GetFileDetailsFromContentBbId(string contentBbId, bool headerDesc, bool headerDescSpecified)
        {
            try
            {
                return bbContent.getFileDetailsFromContentBbId(bbPassword, contentBbId, headerDesc, headerDescSpecified);
            }
            catch (Exception ex)
            {
                BlackBoardWebServices.LogError("bbContent.GetFileDetailsFromContentBbId", ex);
                return null;
            }
        }
    }
}