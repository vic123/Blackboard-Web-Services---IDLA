<%@ page language="java" pageEncoding="UTF-8" import="java.util.*,bbcws.BbWsProperty" %>
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
	<bbUI:titleBar>Calendar webservice security properties</bbUI:titleBar>
	<jsp:useBean id="PersistWSProperties" scope="session" class="bbcws.PropertiesBean.PropertiesBean"/>
<%
    String submit = request.getParameter("submit");
    if(submit==null) submit = "false";

    BbWsProperty usePwd = new BbWsProperty(PersistWSProperties.getProperty("pwd.use"));
    BbWsProperty pwdSet = new BbWsProperty(PersistWSProperties.getProperty("pwd.set"));

    if(!submit.equalsIgnoreCase("true"))
    {
%>
	<script type="text/javascript" src="md5.js"></script>
	<script type="text/javascript" src="hash.js"></script>
	    <form action="#" method="POST" name="aForm" onsubmit="preSubmit()">
	    <input type="hidden" name="submit" value="true" />
	    <bbUI:step number="1" title="Security options">
<%
		if(!usePwd.getSetting() || pwdSet.getSetting())
		{
%>
		<bbUI:dataElement label="Use passwords?" required="true">
		    <input type="radio" name="pwd.use" id="pwd.use" value="Yes" <%=usePwd.getSetting()?"checked":""%>> Yes 
		    <input type="radio" name="pwd.use" id="pwd.use" value="No"  <%=usePwd.getSetting()?"":"checked"%>> No
		</bbUI:dataElement>
<%
		if(pwdSet.getSetting())
		{
%>
		<bbUI:instructions><input type="submit" value="Change my password >>"/></bbUI:instructions>
		<input type="hidden" name="pwd.set" value="No" />
<%
		}
	}
	else
	{
%>

		<bbUI:instructions>Enter a password:</bbUI:instructions>
		    <input type="hidden" name="pwd.set" value="Yes" />
		<bbUI:dataElement label="Hash password?" required="true">
		    <input type="checkbox" id="pwd.isHashed" checked />
		</bbUI:dataElement>
		<bbUI:dataElement label="Password" required="true">
		    <input type="password" name="pwd.pwd" id="pwd.pwd" maxlength="32" size="20" value="" />
		</bbUI:dataElement>
		<bbUI:instructions><input type="submit" value="<< Use existing password" onclick="cancelPwdChange()" /></bbUI:instructions>
<%
	}
%>
	    </bbUI:step>
	    <bbUI:step number="2" title="Method access">
		<bbUI:dataElement><a href="methodAccess.jsp">Change >></a></bbUI:dataElement>
	    </bbUI:step>
	    <bbUI:stepSubmit number="3" cancelUrl="/webapps/blackboard/admin/manage_plugins.jsp" />
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