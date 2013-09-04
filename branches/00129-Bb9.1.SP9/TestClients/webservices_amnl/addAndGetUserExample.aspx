<%@ Page Language="C#" %>
<%
    //Author: Andrew.Martin@ncl.ac.uk

    //bbuws is my chosen webreference name
    bbuws.BBUserWebService ws = new bbuws.BBUserWebService();
    //If you have passwords on, set this to whatever your password is.
    String methodPwd = "";

    try
    {
        String userId = "Test";
        String batchUId = "Test";
        String firstName = "Test";
        String middleName = "Test";
        String lastName = "Test";
        String emailAddress = "Testing@Test.Test";
        String studentId = "T3577357";
        String pwd = "11111111";
        String gender = "";
        String birthYear = "";
        String birthMonth = "";
        String birthDay = "";
        String eduLevel = "";
        String company = "";
        String jobTitle = "";
        String department = "";
        String street1 = "";
        String street2 = "";
        String city = "";
        String stateOrProvince = "";
        String zipOrPostCode = "";
        String country = "";
        String website = "";
        String homePhone = "";
        String workPhone = "";
        String workFax = "";
        String mobilePhone = "";
        String portalRole = "STAFF";
        String[] secPortalRoles = new String[] { "student", "Alumni" };
        String systemRole = "SYSTEM_ADMIN";
        Boolean available = true;

        Boolean returnSpecified = true;
        String returnString = "";
        Boolean returnBool = false;
        //String rsponse = 
        ws.addUser(methodPwd, userId, batchUId, firstName, middleName, lastName, emailAddress, studentId, pwd, gender, birthYear, birthMonth, birthDay,
					    eduLevel, company, jobTitle, department, street1, street2, city, stateOrProvince, zipOrPostCode, country, website,
					    homePhone, workPhone, workFax, mobilePhone, portalRole, secPortalRoles, systemRole, available, true, out returnBool, out returnSpecified);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
    <title>Untitled Page</title>
</head>
<body>
    <form id="form1">
    <div>
<%
	    //if(rsponse.StartsWith("Error"))
        if(!returnBool)
	    {
%>
            <h1>returnBool: <%=returnBool %></h1>
<%
	    }
	    else
	    {
%>
            <h1>User added</h1>
<%  
	        bbuws.stringArray[] detailsList = ws.getUserDetails(methodPwd,userId,true,true,true,true);
            bbuws.stringArray keys = (bbuws.stringArray)detailsList.GetValue(0);
            bbuws.stringArray values = (bbuws.stringArray)detailsList.GetValue(1);

	         for(int inc=0; inc<keys.item.Length; inc++)
	         {
%>
		            <%= keys.item.GetValue(inc) %> : <%= values.item.GetValue(inc) %> <br />
<%
	        }
	    }
    }
    catch(Exception e)
    {
%>
	    <%= "Error: "+e.ToString() %>
<%
    }
%>   
    </div>
    </form>
</body>
</html>
