<%@ Page Language="C#" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>JSP Page</title>
    </head>
    <body>
<%
    //Author: Andrew.Martin@ncl.ac.uk

    //bbcrsws is my chosen webreference name
    bbcrsws.BBCourseWebService ws = new bbcrsws.BBCourseWebService();
    //If you have passwords enabled on the webservice methods, insert it here...
    String wsPassword = "";

    try
    {
        //Our details for a new course, feel free to change...
        String courseId = "WEBSERVICE_Testcourse0001";
        String batchUid = "TESTTESTTEST";
        String title = "WS-TEST-101";
        String description = "A Webservice created test course";
        bool available = true;
        bool allowGuests = false;
        bool allowObservers = false;


        Boolean returnSpecified = true;
        String returnString = "";        
        Boolean returnBool = false;
        ws.doesCourseExist(wsPassword, courseId, out returnBool, out returnSpecified);
        if (!returnBool)
        {   
            ws.addCourse(wsPassword,courseId,batchUid,title,description,available,true,allowGuests,true,allowObservers,true, out returnBool, out returnSpecified);
%>
	        <%= "New course " + courseId + " added<BR />"%>
<%
        }

        //Does it exist now?
        returnBool = false;
        ws.doesCourseExist(wsPassword, courseId, out returnBool, out returnSpecified);
        if (returnBool)
        {
            bbcrsws.stringArray[] courseDetails = ws.getCourseDetails(wsPassword,courseId,false,true,true,true);
            bbcrsws.stringArray keys = (bbcrsws.stringArray)courseDetails.GetValue(0);
            bbcrsws.stringArray values = (bbcrsws.stringArray)courseDetails.GetValue(1);

            for (int inc = 0; inc < keys.item.Length; inc++)
            {
%>
                <%= keys.item.GetValue(inc)%> : <%= values.item.GetValue(inc)%> <br />
<%
            }

            //Uncomment one of below to delete courses if you so wish
            //ws.deleteCourseById(values.item.GetValue(0));
            //ws.deleteCourseByCourseId(values.item.GetValue(1));
        }
    }
    catch (Exception e)
    {
%>
	    <%= "Error: "+e.ToString() %>
<%
    }
%>
    </body>
</html>