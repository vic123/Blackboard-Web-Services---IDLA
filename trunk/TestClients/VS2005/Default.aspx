<%@ Page Language="C#" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>C#.NET Page</title>
    </head>
    <body>
<%
    //Author: Andrew.Martin@ncl.ac.uk

    try
    {
        //bbgbws is my chosen webreference name
        bbgbws.BBGradebookWebServiceService ws = new bbgbws.BBGradebookWebServiceService();
        //If you have passwords enabled on the webservice methods, insert it here...
        String wsPassword = "bighairycats";

        //Change these variable as you see fit :-)
        String courseId = "WebService_TEST101";
        String lineItemId = "";
        String name = "WS Created test via C#";
        String type = "Exam";
        float pointsPossible = 0;
        float weight = 0;
        Boolean available = false;
        //*************************************/        

	    bbgbws.stringArray[] returnStringArray = null;
	    String returnString = "";
	    //****get gradebook setting for a course****/
	    returnStringArray = ws.getGradebookSettingsForGivenCourseID(wsPassword,courseId,true,true);
%>
   Gradebook settings for course "<%= courseId %>"...<br />
<%
        bbgbws.stringArray keys = (bbgbws.stringArray)returnStringArray.GetValue(0);
        bbgbws.stringArray values = null;

        for (int i = 1; i < returnStringArray.Length; i++)
        {
%>
--------<br />
<%
            values = (bbgbws.stringArray)returnStringArray.GetValue(i);

            for (int inc = 0; inc < keys.item.Length; inc++)
            {
%>
                <%= keys.item.GetValue(inc)%> : <%= values.item.GetValue(inc)%> <br />
<%
            }
        }
        //******************/

	    //****get all outcome definitions for a course****/
	    returnStringArray = ws.getAllOutcomeDefinitionsForGivenCourse(wsPassword,courseId,true,true);
%>
    <hr />Outcome Definitions in course "<%= courseId %>"...<br />
<%
        keys = (bbgbws.stringArray)returnStringArray.GetValue(0);
        values = null;

        for (int i = 1; i < returnStringArray.Length; i++)
        {
%>
--------<br />
<%
            values = (bbgbws.stringArray)returnStringArray.GetValue(i);

            for (int inc = 0; inc < keys.item.Length; inc++)
            {
%>
                <%= keys.item.GetValue(inc)%> : <%= values.item.GetValue(inc)%> <br />
<%
            }
        }

        /******************/

	    //****add line item to course****
        returnString = ws.addLineitemToGivenCourseId(wsPassword,courseId,name,type,pointsPossible,true,weight,true,available,true);
%>
    <hr />Adding line item to gradebook in course "<%= courseId %>"...<%= returnString %>
<%
	    /*******************************/

	    //****get all line items for a course****
	    returnStringArray = ws.getAllLineItemsForCourseId(wsPassword,courseId,true,true);
%>
    <hr />Line items in course "<%= courseId %>"...<br />
<%
        keys = (bbgbws.stringArray)returnStringArray.GetValue(0);
        values = null;

        for (int i = 1; i < returnStringArray.Length; i++)
        {
%>
--------<br />
<%
            values = (bbgbws.stringArray)returnStringArray.GetValue(i);

            for (int inc = 0; inc < keys.item.Length; inc++)
            {
%>
                <%= keys.item.GetValue(inc)%> : <%= values.item.GetValue(inc)%> <br />
<%
            }
        }

        values = (bbgbws.stringArray)returnStringArray.GetValue(0);
        if(!values.item.GetValue(0).Equals("Error"))
	    {
            values = (bbgbws.stringArray)returnStringArray.GetValue(1);
            lineItemId = values.item.GetValue(0).ToString();
        }
	    else
	    {
	        lineItemId = "Sorry couldn't find a conference Id to set this variable :-(";
	    }
        /******************/

        //****get outcome definitions for outcome definition id****
        returnStringArray = ws.getAllScoresForGivenLineItemBbId(wsPassword,lineItemId,true,true);
%>
    <hr />Outcome Definitions in course "<%= courseId %>"...<br />
<%
        for (int i = 1; i < returnStringArray.Length; i++)
        {
%>
--------<br />
<%
            values = (bbgbws.stringArray)returnStringArray.GetValue(i);

            for (int inc = 0; inc < keys.item.Length; inc++)
            {
%>
                <%= keys.item.GetValue(inc)%> : <%= values.item.GetValue(inc)%> <br />
<%
            }
        }
        /******************/

        //Various deletion methods
        /*returnString = ws.deleteLineItemById(lineItemId);
        returnString = ws.deleteOutcomeDefinitionById(outcomeDefId);*/
        /********************/
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