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

package bbgbws;

import blackboard.data.gradebook.Lineitem;
import blackboard.data.gradebook.Lineitem.AssessmentLocation;
import java.util.Calendar;
import blackboard.platform.log.*;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class LineitemDetails
{
    private String assessmentBbId;
    private String assessmentLocation;
    private Boolean available;
    private Integer columnPosition;
    private String dateAdded;
    private String dateChanged;
    private String lineItemBbId;
    private String name;
    private String outcomeDefBbId;
    private String pointsPossible;
    private String type;
    private String weight;
    //?? gi.getDisplayColumnName()
    //determine the calculation type - AVERAGE, MINMAX, NON_CALCULATED, TOTAL, WEIGHTED_TOTAL
    //out.println(gi.getCalculatedInd());
    //get the schema type - COMPLETE, PERCENT, SCORE, TABULAR, TEXT
    //out.println(GradingSchemaDAO.get().loadById(gi.getGradingSchemaId()).getScaleType().name());
    //determine if is is calculated or non calculated
    //out.println(gi.isCalculated());
    //determine if the instructor manually entered the grade
    //out.println(gi.isManual());



	static Log log = LogServiceFactory.getInstance().getDefaultLog();
	void logForward(String message) {
                logForward(LogService.Verbosity.WARNING, message);
	}
        
	//log forwarding functions - intoroduced for easier production of log messages
	//without necessity of modifying of server log settings
	//assumes that active server log level is at least WARNING 
	void logForward(LogService.Verbosity verbosity, String message) {
		message = "BBGradebookWebService	" + verbosity.toExternalString() + "	" + message;
		//using higher severity log level for easier development testing, log is overfilled when all messages are of debug level
		//actual log.logWarning has to be commented out in production release, but may be uncommented for collecting of log messages  
		if (verbosity.getLevelAsInt() > LogService.Verbosity.WARNING.getLevelAsInt()) {
			log.logWarning(message);
		}
		log.log(message, verbosity);
		//log.log("strLogMessages: " + strLogMessages, verbosity);    
	}
	void logForward(LogService.Verbosity verbosity, java.lang.Throwable error, String message) {
		message = "BBGradebookWebService	" + message;
		//using higher severity log level for easier development testing, log is overfilled when all messages are of debug level
		//actual log.logWarning has to be commented out in production release, but may be uncommented for collecting of log messages  
		if (verbosity.getLevelAsInt() > LogService.Verbosity.WARNING.getLevelAsInt()) {
			log.logWarning(message, error);
		}
		log.log(message, error, verbosity);
	}
    
    public LineitemDetails(){}
    public LineitemDetails(Object gi)
    {
        logForward("LineitemDetails:    Class gradableItem = gi.getClass();");
        Class gradableItem = gi.getClass();
        logForward("LineitemDetails:    try{this.name = (String)...");
        try{this.name = (String)gradableItem.getDeclaredMethod("getTitle").invoke(gi);}catch(Exception e){}
        try{
            Object o = gradableItem.getDeclaredMethod("getAssessmentId").invoke(gi);
            if(o!=null)
            {
                this.assessmentBbId = o.getClass().getName();
                if(this.assessmentBbId.equalsIgnoreCase("java.lang.String"))
                {
                    this.assessmentBbId = o.toString();
                }
                else if(this.assessmentBbId.equalsIgnoreCase("blackboard.persist.Id") || this.assessmentBbId.equalsIgnoreCase("blackboard.persist.PkId"))
                {
                    this.assessmentBbId = ((blackboard.persist.Id)o).getExternalString();
                }
            }
        }catch(Exception e){}
        //if(gi.getAssessmentLocation().equals(AssessmentLocation.EXTERNAL)){this.assessmentLocation = "EXTERNAL";}
        //else if(gi.getAssessmentLocation().equals(AssessmentLocation.INTERNAL)){this.assessmentLocation = "INTERNAL";}
        //else if(gi.getAssessmentLocation().equals(AssessmentLocation.UNSET)){this.assessmentLocation = "UNSET";}

        //!!try{this.available = (Boolean)gradableItem.getDeclaredMethod("getIsVisibleInAllTerms").invoke(gi);}catch(Exception e){}
        try{this.available = (Boolean)gradableItem.getDeclaredMethod("isVisibleInAllTerms").invoke(gi);}catch(Exception e){}
        try{this.columnPosition = (Integer)gradableItem.getDeclaredMethod("getPosition").invoke(gi);}catch(Exception e){}
        try{this.dateAdded = extractDate((Calendar)gradableItem.getDeclaredMethod("getDateAdded").invoke(gi));}catch(Exception e){}
        try{this.dateChanged = extractDate((Calendar)gradableItem.getDeclaredMethod("getDateModified").invoke(gi));}catch(Exception e){}
        try{this.lineItemBbId = ((String)gradableItem.getDeclaredMethod("getExternalId").invoke(gi));}catch(Exception e){}
        try{this.name = (String)gradableItem.getDeclaredMethod("getTitle").invoke(gi);}catch(Exception e){}
        //this.outcomeDefBbId = gi.getOutcomeDefinition().getId().toExternalString();
        try{this.pointsPossible = Double.toString((Double)gradableItem.getDeclaredMethod("getPoints").invoke(gi));}catch(Exception e){}
        //this.type = gi.getType();
        try{this.weight = Double.toString((Double)gradableItem.getDeclaredMethod("getWeight").invoke(gi));}catch(Exception e){}
    }
    public LineitemDetails(Lineitem li)
    {
        Object o = li.getAssessmentId();
        if(o!=null)
        {
            this.assessmentBbId = o.getClass().getName();
            if(this.assessmentBbId.equalsIgnoreCase("java.lang.String"))
            {
                this.assessmentBbId = o.toString();
            }
            else if(this.assessmentBbId.equalsIgnoreCase("blackboard.persist.Id"))
            {
                this.assessmentBbId = ((blackboard.persist.Id)o).getExternalString();
            }
        }
        if(li.getAssessmentLocation().equals(AssessmentLocation.EXTERNAL)){this.assessmentLocation = "EXTERNAL";}
        else if(li.getAssessmentLocation().equals(AssessmentLocation.INTERNAL)){this.assessmentLocation = "INTERNAL";}
        else if(li.getAssessmentLocation().equals(AssessmentLocation.UNSET)){this.assessmentLocation = "UNSET";}
        this.available = li.getIsAvailable();
        this.columnPosition = li.getColumnOrder();
        this.dateAdded = extractDate(li.getDateAdded());
        this.dateChanged = extractDate(li.getDateChanged());
        this.lineItemBbId = li.getId().toExternalString();
        this.name = li.getName();
        this.outcomeDefBbId = li.getOutcomeDefinition().getId().toExternalString();
        this.pointsPossible = Float.toString(li.getPointsPossible());
        this.type = li.getType();
        this.weight = Float.toString(li.getWeight());
    }

    public String getAssessmentBbId()
    {
	return this.assessmentBbId;
    }

    public void setAssessmentBbId(String assessmentBbId)
    {
	this.assessmentBbId = assessmentBbId;
    }

    public String getAssessmentLocation()
    {
	return this.assessmentLocation;
    }

    public void setAssessmentLocation(String assessmentLocation)
    {
	this.assessmentLocation = assessmentLocation;
    }

    public Boolean getAvailable()
    {
	return this.available;
    }

    public void setAvailable(Boolean available)
    {
	this.available = available;
    }

    public Integer getColumnPosition()
    {
	return this.columnPosition;
    }

    public void setColumnPosition(Integer columnPosition)
    {
	this.columnPosition = columnPosition;
    }

    public String getDateAdded()
    {
	return this.dateAdded;
    }

    public void setDateAdded(String dateAdded)
    {
	this.dateAdded = dateAdded;
    }

    public String getDateChanged()
    {
	return this.dateChanged;
    }

    public void setDateChanged(String dateChanged)
    {
	this.dateChanged = dateChanged;
    }

    public String getLineItemBbId()
    {
	return this.lineItemBbId;
    }

    public void setLineItemBbId(String lineItemBbId)
    {
	this.lineItemBbId = lineItemBbId;
    }

    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public String getOutcomeDefBbId()
    {
	return this.outcomeDefBbId;
    }

    public void setOutcomeDefBbId(String outcomeDefBbId)
    {
	this.outcomeDefBbId = outcomeDefBbId;
    }

    public String getPointsPossible()
    {
        return this.pointsPossible;
    }

    public void setPointsPossible(String pointsPossible)
    {
        this.pointsPossible = pointsPossible;
    }

    public String getType()
    {
	return this.type;
    }

    public void setType(String type)
    {
	this.type = type;
    }

    public String getWeight()
    {
        return this.weight;
    }

    public void setWeight(String weight)
    {
        this.weight = weight;
    }

    protected String extractDate(Calendar cal)
    {
	try
	{
	    return cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
	}
	catch(Exception e)
	{
	    return "Never";
	}
    }

    private String[] getLineitemDetails()
    {
        logForward("getLineitemDetails():    String[] tst_str = new String[] {...");
        String[] tst_str = new String[] {"sdfsf", null};
        logForward("getLineitemDetails():    return new String[]");
        logForward("getLineitemDetails():    this.columnPosition:" + this.columnPosition);
        logForward("getLineitemDetails():    this.available:" + this.available);
        return new String[]
        {
            this.lineItemBbId,
            this.name,
            this.dateAdded,
            this.dateChanged,
            //!!Integer.toString(this.columnPosition),
            String.valueOf(this.columnPosition), 
            //!!Boolean.toString(this.available),
            String.valueOf(this.available),
            this.pointsPossible,
            this.type,
            this.weight,
            this.assessmentBbId,
            this.assessmentLocation
        };
    }

    public String[] toStringArray()
    {
    	return getLineitemDetails();
    }

    public String[] toStringArrayHeader()
    {
	return new String[]{"LineItemBbId","Name","Date Added",
		"Date Changed","Column position",
		"Available","Points Possible","Type",
		"Weight","to do:AssessmentBbId",
		"Assessment Location"};
    }
}
