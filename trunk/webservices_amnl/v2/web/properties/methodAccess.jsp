<%@ page language="java" pageEncoding="UTF-8" import="java.util.*,bbws.BbWsProperty" %>
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
	<jsp:useBean id="PersistWSProperties" scope="session" class="bbws.PropertiesBean.PropertiesBean"/>
<%
    String submit = request.getParameter("submit");
    if(submit==null) submit = "false";

    List<String> methods = new ArrayList<String>();
    methods.add("bbAnnouncementCreate");
    methods.add("bbAnnouncementDelete");
    methods.add("bbAnnouncementReadSearchByAvailableAnnouncementAndUserId");
    methods.add("bbAnnouncementReadSearchByCourseId");
    methods.add("bbAnnouncementUpdate");
    methods.add("bbCourseCreate");
    methods.add("bbCourseDelete");
    methods.add("bbCourseMembershipCreate");
    methods.add("bbCourseMembershipDelete");
    methods.add("bbCourseMembershipRead");
    methods.add("bbCourseMembershipReadSearchByUserIdAndCourseId");
    methods.add("bbCourseQuotaRead");
    methods.add("bbCourseQuotaUpdate");
    methods.add("bbCourseRead");
    methods.add("bbCourseReadAll");
    methods.add("bbCourseReadSearchByRegex");
    methods.add("bbCourseReadSearchByUserIdAndCMRole");
    methods.add("bbEnrollmentReadSearchByUserId");
    methods.add("bbGradeCentreAttemptDelete");
    methods.add("bbGradeCentreAttemptReadSearchByOutcomeDefinitionId");
    methods.add("bbGradeCentreLineitemAdd");
    methods.add("bbGradeCentreLineitemDelete");
    methods.add("bbGradeCentreLineitemRead");
    methods.add("bbGradeCentreLineitemReadSearchByCourseId");
    methods.add("bbGradeCentreOutcomeDefinitionDelete");
    methods.add("bbGradeCentreOutcomeDefinitionRead");
    methods.add("bbGradeCentreOutcomeDefinitionReadSearchByCourseId");
    methods.add("bbGradeCentreOutcomeRead");
    methods.add("bbGradeCentreOutcomeReadSearchByOutcomeDefinitionId");
    methods.add("bbGradeCentreScoreRead");
    methods.add("bbGradeCentreScoreReadSearchByLineitemId");
    methods.add("bbGradeCentreScoreReadSearchByLineitemIdAndCourseMembershipId");
    methods.add("bbGradeCentreScoreReadSearchByLineitemIdAndUserId");
    methods.add("bbGradeCentreSettingsRead");
    methods.add("bbGroupAdd");
    methods.add("bbGroupDelete");
    methods.add("bbGroupMembershipCreateByUserIdAndGroupId");
    methods.add("bbGroupMembershipDeleteByUserIdAndGroupId");
    methods.add("bbGroupMembershipReadByGroupId");
    methods.add("bbGroupRead");
    methods.add("bbGroupUpdate");
    methods.add("bbRoleSecondaryPortalReadSearchByUserId");
    methods.add("bbRoleSecondaryPortalUpdate");
    methods.add("bbRoleSecondarySystemReadSearchByUserId");
    methods.add("bbRoleUserReadSearchByUserIdAndCourseId");
    methods.add("bbUserCreate");
    methods.add("bbUserDelete");
    methods.add("bbUserRead");
    methods.add("bbUserReadAll");
    methods.add("bbUserReadSearchByCourseId");
    methods.add("bbUserReadSearchByCourseIdAndCMRole");
    methods.add("bbUserUpdate");

    if(!submit.equalsIgnoreCase("true"))
    {
%>
    <form action="#" method="POST" name="aForm">
	<input type="hidden" name="submit" value="true" />
	<bbUI:step title="Allow access to method?">
<%
    Iterator<String> i = methods.iterator();
    String currentMethod = "";
    while(i.hasNext())
    {
        currentMethod = i.next();
            BbWsProperty methodProperty = new BbWsProperty(PersistWSProperties.getProperty(currentMethod+".access"));
%>
                <bbUI:dataElement label="<%= currentMethod %>" required="true">
		    <input type="radio" name="<%= currentMethod+".access" %>" id="<%= currentMethod+".access" %>" value="Yes" <%= methodProperty.getSetting()?"checked":""%>> Yes
		    <input type="radio" name="<%= currentMethod+".access" %>" id="<%= currentMethod+".access" %>" value="No"  <%= methodProperty.getSetting()?"":"checked"%>> No
		</bbUI:dataElement>
<%
    }
%>
	</bbUI:step>
	<bbUI:stepSubmit cancelUrl="properties.jsp" />
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
    
