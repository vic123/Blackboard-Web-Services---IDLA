<%@ page language="java" pageEncoding="UTF-8" import="java.util.*,bbcrsws.BbWsProperty" %>
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
	<jsp:useBean id="PersistWSProperties" scope="session" class="bbcrsws.PropertiesBean.PropertiesBean"/>
<%
    String submit = request.getParameter("submit");
    if(submit==null) submit = "false";

    ArrayList methods = new ArrayList();
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
    
