<%@ Page Language="C#" %>
<%@ Import Namespace="System.Collections.Generic" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script language="C#" runat="server">
    private void ShowLineItemsArray(bbgbws.lineitemDetails[] li_array)
    {
        if (li_array == null) return;
        for (int i = 0; i < li_array.Length; i++)
        {
            Response.Write("<br>");
            Response.Write("lineItemBbId: " + li_array[i].lineItemBbId + "<br>");
            Response.Write("name: " + li_array[i].name + "<br>");
            Response.Write("pointsPossible: " + li_array[i].pointsPossible + "<br>");
                Response.Write("::::::DATA LOG:::::::: " + "<br>");
                if (li_array[i].dataLog == null) Response.Write("No data log records" + "<br>");
                else
                {
                    for (int j = 0; j < li_array[i].dataLog.Length; j++)
                    {
                        bbgbws.dataLogRecord lr;
                        lr = li_array[i].dataLog[j];
                        Response.Write(j + " dateTime: " + lr.dateTime + "<br>");
                        Response.Write(j + " severityLevel: " + lr.severityLevel + "<br>");
                        Response.Write(j + " apiUsed: " + lr.apiUsed + "<br>");
                        Response.Write(j + " fieldName: " + lr.fieldName + "<br>");
                        Response.Write(j + " value: " + lr.value + "<br>");
                        Response.Write(j + " message: " + lr.message + "<br>");
                    }
                }
        }
    }                    

</script>

<html xmlns="http://www.w3.org/1999/xhtml" >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>C#.NET Page</title>
        
</head>
<body>
<div>


<%
%>
<%            
    //Author: Andrew.Martin@ncl.ac.uk

    try
    {
        //bbgbws is my chosen webreference name
        bbgbws.BBGradebookWebServiceService ws = new bbgbws.BBGradebookWebServiceService();
        
        //If you have passwords enabled on the webservice methods, insert it here...
        //String wsPassword = "bighairycats";
        String wsPassword = "";

        //Change these variable as you see fit :-)
        //String courseId = "WebService_TEST101";
        String courseId = "TestClass_003_ID";
        
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
	    //!!returnStringArray = ws.getGradebookSettingsForGivenCourseID(wsPassword,courseId,true,true);
        returnStringArray = ws.getGradebookSettingsForGivenCourseId(wsPassword,courseId,true,true);
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
        //!!returnStringArray = ws.getAllOutcomeDefinitionsForGivenCourse(wsPassword,courseId,true,true);

        //??returnStringArray = ws.getAllOutcomeDefinitionsForGivenCourse(wsPassword, courseId, false, false);
        bbgbws.outcomeDefinitionDetails outdd;
        //List<bbgbws.outcomeDefinitionDetails> outdd_lst;
        bbgbws.outcomeDefinitionDetails[] outdd_arr;
        //??outdd_arr = ws.getAllOutcomeDefinitionsForGivenCourseWithNamedElements(wsPassword, courseId);
        //OutcomeDefinitionDetails outdd;
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
        //ws.addLineitemToGivenCourseId (
        //!!
        Boolean returnString1 = false;
        returnString = "";
        Boolean returnString1_spec;
        //ws.addLineitemToGivenCourseId(wsPassword, courseId, name, type, pointsPossible, true, weight, true, available, true, out returnString1, out returnString1_spec);
        Boolean ret = true;
        Boolean ret_spec = true;
        //??ws.addLineitemToGivenCourseId(wsPassword, courseId, name, type, pointsPossible, true, weight, true, available, true, out ret, out ret_spec);
        //??returnString = ret.ToString() ;
        
%>
    <hr />Adding line item to gradebook in course "<%= courseId %>"..returnString: <%= returnString %> returnString1: <%= returnString1 %> 
<%
	    /*******************************/

	    //****get all line items for a course****
	    returnStringArray = ws.getAllLineItemsForCourseId(wsPassword,courseId,true,true);
%>
    <hr />Line items in course "<%= courseId %>"...<br />
<%
        keys = (bbgbws.stringArray)returnStringArray.GetValue(0);
        values = null;

        //!!for (int i = 1; i < returnStringArray.Length; i++)
        for (int i = 0; i < returnStringArray.Length; i++)
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
            //!!values = (bbgbws.stringArray)returnStringArray.GetValue(1);
            //values = (bbgbws.stringArray)returnStringArray.GetValue(1); //-default Weighted Total
            //values = (bbgbws.stringArray)returnStringArray.GetValue(2); //-default Total
            //values = (bbgbws.stringArray)returnStringArray.GetValue(6); //-custom Total
            //values = (bbgbws.stringArray)returnStringArray.GetValue(3); //-regular col
            //values = (bbgbws.stringArray)returnStringArray.GetValue(7); //-custom weight col

            //local:   
            //values = (bbgbws.stringArray)returnStringArray.GetValue(1); //-default Total
            //values = (bbgbws.stringArray)returnStringArray.GetValue(2); //-default Weighted Total
            values = (bbgbws.stringArray)returnStringArray.GetValue(3); //-regular col
            
            
            lineItemId = values.item.GetValue(0).ToString();
            
            
        }
	    else
	    {
	        lineItemId = "Sorry couldn't find a conference Id to set this variable :-(";
	    }
        /******************/
%>        
        <hr />Line item: "<%= lineItemId %>"...<br />
<%    
        //****get outcome definitions for outcome definition id****
        Boolean b1 = true;
        Boolean b2 = true;
        //ws.getAllScoresForGivenLineItemBbId (
        returnStringArray = ws.getAllScoresForGivenLineItemBbId(wsPassword, lineItemId, b1, b2, b1, b2);
        //!!returnStringArray = ws.getAllScoresForGivenLineItemBbId(wsPassword, lineItemId, true, true);
        //returnStringArray = ws.getAllScoresForGivenLineItemBbId(wsPassword, lineItemId);
        //returnStringArray = ws.getAllScoresForGivenLineItemBbId(wsPassword, lineItemId, true, "sdfsd", "sdf");
        //returnStringArray = ws.getAllScoresForGivenLineItemBbId(wsPassword);
%>
    <hr />Scores in course "<%= courseId %>"...<br />
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
                String s1 = (String) values.item.GetValue(inc); 
                    
%>
                <%= keys.item.GetValue(inc)%> : <%= s1%> <br />
<%
            }
        }
%>
    <hr />Scores in course "<%= courseId %>" obtained with getAllScoreDetailsForGivenLineItemBbIdWithNamedElements <br />
<%
    bbgbws.scoreDetails score_details1;
    //score_details1.dateAdded = null;
    bbgbws.commonParams params1;
    //params1.apiToUse = "dfsdAdf";
    //score_details1.dateAdded = null;
        
        
        bbgbws.scoreDetails[] score_details = ws.getAllScoreDetailsForGivenLineItemBbIdWithNamedElements(wsPassword, lineItemId, true, true);
        for (int i = 0; i < score_details.Length; i++)
        {
%>
--------<br />
            attemptBbId: <%= score_details[i].attemptBbId %> <br />
            <%
                String s1 = score_details[i].attemptLocation;
             %>
            attemptLocation: <%= s1%> <br />        
            courseMembershipBbId: <%= score_details[i].courseMembershipBbId%> <br />
            dataType: <%= score_details[i].dataType%> <br />
            dateAdded: <%= score_details[i].dateAdded%> <br />
            dateChanged: <%= score_details[i].dateChanged%> <br />
            dateModified: <%= score_details[i].dateModified%> <br />
            grade: <%= score_details[i].grade%> <br />
            lineItemBbId: <%= score_details[i].lineItemBbId%> <br />
            outcomeDefBbId: <%= score_details[i].outcomeDefBbId%> <br />
            scoreBbId: <%= score_details[i].scoreBbId%> <br />
            userId: <%= score_details[i].userId%> <br />
<%
        }
%>        
        
        
        
        
        
    <hr /> ============== Line items in course with NamedElements/Params ============= courseId: "<%= courseId %>"...<br />
<%
	    //****get all line items for a course****
        bbgbws.commonParams cp = new bbgbws.commonParams();
        //cp.apiToUse = "DATA_GB, PLATFORM_GB2";
        //cp.apiToUse = "DATA_GB";
        cp.apiToUse = "PLATFORM_GB2";
        cp.logVerbosity = "DEBUG";
        cp.password = wsPassword;

        bbgbws.lineitemDetails[] li_array;
        li_array = ws.getAllLineItemsForCourseIdWithNamedElements(wsPassword, courseId, cp);
        //li_array = ws.getAllLineItemsForCourseIdWithNamedElements(wsPassword, courseId, (bbgbws.commonParams)null);
        //ShowLineItemsArray(li_array);
    
        
        bbgbws.lineitemParams lip = new bbgbws.lineitemParams();
        lip.apiToUse = "DATA_GB";
        lip.logVerbosity = "DEBUG";
        lip.password = wsPassword;
        lip.courseId = courseId;
        //li_array = ws.getAllLineItemsForCourseIdWithNamedElementsAndParams(lip);
        //li_array = ws.getAllLineItemsForCourseIdWithNamedElements(wsPassword, courseId, lip);
        //li_array = ws.getAllLineItemsForCourseIdWithNamedElementsAndParams(cp); Error	2	Argument '1': cannot convert from 'bbgbws.commonParams' to 'bbgbws.lineitemParams'	W:\BB-webservice\src\gradebookConsumerLocal\Default.aspx	289	76	W:\...\gradebookConsumerLocal\

        ShowLineItemsArray(li_array);
        
        
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


 </div>
    </body>
</html>