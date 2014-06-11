<%@ page language="java" pageEncoding="UTF-8" import="java.util.*,bbgbws.BbWsProperty" %>
<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="/bbData" prefix="bbData"%>
<%
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
%>
<bbData:context>
    <bbUI:docTemplate title="Blackboard Administrator Tools">
	<bbUI:titleBar>Gradebook webservice security properties</bbUI:titleBar>
	<jsp:useBean id="PersistWSProperties" scope="session" class="bbgbws.PropertiesBean.PropertiesBean"/>
<%
    String submit = request.getParameter("submit");
    if(submit==null) submit = "false";

    ArrayList methods = new ArrayList();

    methods.add("deleteAnnouncement");
    methods.add("deleteAnnouncementXML");
    methods.add("getAllAnnouncementsForGivenCourseId");
    methods.add("getAllAnnouncementsForGivenCourseIdWithNamedElements");
    methods.add("getAllAnnouncementsForGivenCourseIdXML");
    methods.add("getAllAvailableAnnouncementsForGivenUserId");
    methods.add("getAllAvailableAnnouncementsForGivenUserIdWithNamedElements");
    methods.add("getAllAvailableAnnouncementsForGivenUserIdXML");
    methods.add("getAllSystemAnnouncements");
    methods.add("getAllSystemAnnouncementsWithNamedElements");
    methods.add("getAllSystemAnnouncementsXML");
    methods.add("modifyAnnouncement");
    methods.add("modifyAnnouncementXML");
    methods.add("postCourseAnnouncement");
    methods.add("postCourseAnnouncementXML");
    methods.add("postSystemAnnouncement");
    methods.add("postSystemAnnouncementXML");
	
    
    methods.add("addCalendarEntry");
    methods.add("deleteCalendarEntryByCalendarEntryBbId");
    methods.add("getAllCourseCalendarEntriesForGivenCourseId");
    methods.add("getAllCourseCalendarEntriesForGivenUserId");
    methods.add("getCourseCalendarEntriesWithinDatesForGivenUserId");
    methods.add("getCourseCalendarEntriesWithinDatesForGivenCourseId");
    methods.add("getPersonalCalendarEntriesWithinDatesForGivenUserId");


    methods.add("deleteContentByContentBbId");
    methods.add("getContentTocDetailsForGivenCourse");
    methods.add("getChildContentFromParentContentBbId");
    methods.add("getContentDetailsFromContentBbId");
    methods.add("getFileDetailsFromContentBbId");
    
    
    methods.add("addCourse");
    methods.add("addCourseXML");
    methods.add("deleteCourseByCourseBbId");
    methods.add("deleteCourseByCourseBbIdXML");
    methods.add("deleteCourseByCourseId");
    methods.add("deleteCourseByCourseIdXML");
    methods.add("doesCourseExist");
    methods.add("doesCourseExistXML");
    methods.add("enrollGivenUserOnGivenCourse");
    methods.add("enrollGivenUserOnGivenCourseXML");
    methods.add("getAllCourseIDsNoDetails");
    methods.add("getAllCourseIDsNoDetailsWithNamedElements");
    methods.add("getAllCourseIDsNoDetailsXML");
    methods.add("getAllCourseIDsWhereCourseIdContainsGivenSearchString");
    methods.add("getAllCourseIDsWhereCourseIdContainsGivenSearchStringWithNamedElements");
    methods.add("getAllCourseIDsWhereCourseIdContainsGivenSearchStringXML");
    methods.add("getAllCourseIDsWhereGivenUserIdIsAnInstructorWithNamedElements");
    methods.add("getAllCourseIDsWhereGivenUserIdIsAnInstructorXML");
    methods.add("getCourseDetails");
    methods.add("getCourseDetailsWithNamedElements");
    methods.add("getCourseDetailsXML");
    methods.add("getCourseMembershipBbIdForGivenUserIdAndCourseId");
    methods.add("getCourseMembershipBbIdForGivenUserIdAndCourseIdWithNamedElements");
    methods.add("getCourseMembershipBbIdForGivenUserIdAndCourseIdXML");
    methods.add("getCourseMembershipDetailsForGivenCourseMembershipBbIdWithNamedElements");
    methods.add("getCourseMembershipDetailsForGivenCourseMembershipBbIdXML");
    methods.add("getCourseQuotaDetails");
    methods.add("getCourseQuotaDetailsWithNamedElements");
    methods.add("getCourseQuotaDetailsXML");
    methods.add("getEnrolledCoursesForGivenUser");
    methods.add("getEnrolledCoursesForGivenUserWithNamedElements");
    methods.add("getEnrolledCoursesForGivenUserXML");
    methods.add("modifyCourseQuotaDetails");
    methods.add("modifyCourseQuotaDetailsXML");
    methods.add("unEnrollGivenUserFromGivenCourse");
    methods.add("unEnrollGivenUserFromGivenCourseXML");
    methods.add("copyCourseExact");


    methods.add("addConference");
    methods.add("addForumToGivenConferenceBbId");
    methods.add("addMessageToGivenForumBbIdOrMessageBbId");
    methods.add("deleteAllConferencesInGivenCourseBbId");
    methods.add("deleteAllForumsInGivenConferenceBbId");
    methods.add("deleteAllThreadsInGivenForumBbId");
    methods.add("deleteGivenConferenceAndSubForums");
    methods.add("deleteGivenForumAndSubThreads");
    methods.add("deleteMessageAndAllSubMessages");
    methods.add("getAllGroupConferenceDetailsForGivenCourseId");
    methods.add("getAllMessagesForGivenThreadInGivenForumBbId");
    methods.add("getAllTypesOfConferencesForGivenCourseId");
    methods.add("getConferenceDetailsForGivenConferenceBbId");
    methods.add("getCourseConferenceDetailsForGivenCourseId");
    methods.add("getForumDetailsForGivenConferenceBbId");
    methods.add("getGroupConferenceDetailsForGivenGroupBbId");
    methods.add("getImmediateChildMessagesForGivenMessageBbId");
    methods.add("getTopLevelMessageForGivenForumBbId");

    methods.add("addLineitemToGivenCourseId");
    methods.add("addLineitemToGivenCourseIdXML");
    methods.add("deleteGivenAttemptByAttemptBbId");
    methods.add("deleteGivenAttemptByAttemptBbIdXML");
    methods.add("deleteLineItemByLineItemBbId");
    methods.add("deleteLineItemByLineItemBbIdXML");
    methods.add("deleteOutcomeDefinitionByOutcomeDefBbId");
    methods.add("deleteOutcomeDefinitionByOutcomeDefBbIdXML");
    methods.add("getAllLineItemsForCourseId");
    methods.add("getAllLineItemsForCourseIdWithNamedElements");
    methods.add("getAllLineItemsForCourseIdXML");
    methods.add("getAllOutcomeDefinitionsForGivenCourse");
    methods.add("getAllOutcomeDefinitionsForGivenCourseWithNamedElements");
    methods.add("getAllOutcomeDefinitionsForGivenCourseXML");
    methods.add("getAllOutcomesForGivenOutcomeDefBbIdWithNamedElements");
    methods.add("getAllOutcomesForGivenOutcomeDefBbIdXML");
    methods.add("getAllScoreDetailsForGivenLineItemBbIdWithNamedElements");
    methods.add("getAllScoreDetailsForGivenLineItemBbIdXML");
    methods.add("getAllScoresForGivenLineItemBbId");
    methods.add("getAttemptsByGivenOutcomeDefBbId");
    methods.add("getAttemptDetailsByGivenOutcomeDefBbIdWithNamedElements");
    methods.add("getAttemptDetailsByGivenOutcomeDefBbIdXML");
    methods.add("getGradebookSettingsForGivenCourseID");
    methods.add("getGradebookSettingsForGivenCourseIDWithNamedElements");
    methods.add("getGradebookSettingsForGivenCourseIDXML");
    methods.add("getLineitemDetailsForGivenLineitemBbIdWithNamedElements");
    methods.add("getLineitemDetailsForGivenLineitemBbIdXML");
    methods.add("getOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdWithNamedElements");
    methods.add("getOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdXML");
    methods.add("getScoreDetailsForGivenScoreBbIdWithNamedElements");
    methods.add("getScoreDetailsForGivenScoreBbIdWithXML");
    methods.add("getScoreDetailsForGivenUserIdAndLineItemBbIdWithNamedElements");
    methods.add("getScoreDetailsForGivenUserIdAndLineItemBbIdXML");
    methods.add("getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdWithNamedElements");
    methods.add("getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdXML");
    methods.add("getScoreForGivenCourseMembershipBbIdAndLineItemBbId");
    methods.add("getScoreForGivenUserIdAndLineItemBbId");
    methods.add("getOutcomeDetailsFromOutcomeBbIdWithNamedElements");
    methods.add("getOutcomeDetailsFromOutcomeBbIdWithNamedElementsXML");
    methods.add("setLineItemWeightByLineItemBbId");
    methods.add("setLineItemWeightByLineItemBbIdXML");
    methods.add("setWeightByItemOrCategoryForGradebookInGivenCourseId");
    methods.add("setWeightByItemOrCategoryForGradebookInGivenCourseIdXML");


    methods.add("addGivenUserToGivenGroup");
    methods.add("addGivenUserToGivenGroupXML");
    methods.add("addGroupToGivenCourse");
    methods.add("addGroupToGivenCourseXML");
    methods.add("deleteGivenUserFromGivenGroup");
    methods.add("deleteGivenUserFromGivenGroupXML");
    methods.add("deleteGroup");
    methods.add("deleteGroupXML");
    methods.add("getGroupDetailsForGivenCourseId");
    methods.add("getGroupDetailsForGivenCourseIdWithNamedElements");
    methods.add("getGroupDetailsForGivenCourseIdXML");
    methods.add("getGroupDetailsForGivenGroupBbId");
    methods.add("getGroupDetailsForGivenGroupBbIdWithNamedElements");
    methods.add("getGroupDetailsForGivenGroupBbIdXML");
    methods.add("getGroupMembersForGivenGroupBbId");
    methods.add("getGroupMemberDetailsForGivenGroupBbIdWithNamedElements");
    methods.add("getGroupMemberDetailsForGivenGroupBbIdXML");
    methods.add("modifyGroup");
    methods.add("modifyGroupXML");


    methods.add("addDefaultStudent");
    methods.add("addDefaultStudentXML");
    methods.add("addUser");
    methods.add("addUserXML");
    methods.add("deleteUserByUserBbId");
    methods.add("deleteUserByUserBbIdXML");
    methods.add("deleteUserByUserId");
    methods.add("deleteUserByUserIdXML");
    methods.add("doesUserExist");
    methods.add("doesUserExistXML");
    methods.add("getAllUserIDsNoDetails");
    methods.add("getAllUserIDsNoDetailsWithNamedElements");
    methods.add("getAllUserIDsNoDetailsXML");
    methods.add("getEnrolledStudentsForGivenCourseIDWithNamedElements");
    methods.add("getEnrolledStudentsForGivenCourseIDXML");
    methods.add("getEnrolledUserDetailsForGivenCourseWithNamedElements");
    methods.add("getEnrolledUserDetailsForGivenCourseXML");
    methods.add("getEnrolledUsersForGivenCourse");
    methods.add("getEnrollmentsByBatchUIDandCourseIDPattern");
    methods.add("getSecondaryPortalRolesForGivenUserId");
    methods.add("getSecondaryPortalRolesForGivenUserIdWithNamedElements");
    methods.add("getSecondaryPortalRolesForGivenUserIdXML");
    methods.add("getSecondarySystemRolesForGivenUserId");
    methods.add("getSecondarySystemRolesForGivenUserIdWithNamedElements");
    methods.add("getSecondarySystemRolesForGivenUserIdXML");
    methods.add("getUserDetails");
    methods.add("getUserDetailsWithNamedElements");
    methods.add("getUserDetailsXML");
    methods.add("getUserRoleInCourse");
    methods.add("getUserRoleInCourseWithNamedElements");
    methods.add("getUserRoleInCourseXML");
    methods.add("isUserInCourse");
    methods.add("isUserInCourseXML");
    methods.add("setOrModifySecondarySystemRolesForGivenUserId");
    methods.add("setOrModifySecondarySystemRolesForGivenUserIdXML");
    
    


    if(!submit.equalsIgnoreCase("true"))
    {
%>
    <form action="#" method="POST" name="aForm">
	<input type="hidden" name="submit" value="true" />
	<bbUI:step number="1" title="Allow access to method?">
<%
    for(int i=0;i<methods.size();i++)
    {
	BbWsProperty methodProperty = new BbWsProperty(PersistWSProperties.getProperty(methods.get(i)+".access"));
%>
		<bbUI:dataElement label="<%= methods.get(i).toString() %>" required="true">
		    <input type="radio" name="<%= methods.get(i)+".access" %>" id="<%= methods.get(i)+".access" %>" value="Yes" <%= methodProperty.getSetting()?"checked":""%>> Yes 
		    <input type="radio" name="<%= methods.get(i)+".access" %>" id="<%= methods.get(i)+".access" %>" value="No"  <%= methodProperty.getSetting()?"":"checked"%>> No
		</bbUI:dataElement>
<%
    }
%>
	</bbUI:step>
	<bbUI:stepSubmit number="2" cancelUrl="properties.jsp" />
    </form>
<%
    }
    else
    {
	try
	{
	    PersistWSProperties.setProperties(request);
	}catch(Exception e){}
%>
	<form action="#" method="POST" name="aForm" />
	<script type="text/javascript">
	    document.aForm.submit();
	</script>
	<bbUI:receipt recallUrl = "../properties/properties.jsp">Your settings have been saved.</bbUI:receipt>
<%
    }
%>
    </bbUI:docTemplate>
</bbData:context>
    
