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

package bbws.gradecentre;

//blackboard
import blackboard.data.gradebook.Lineitem;
import blackboard.data.gradebook.Lineitem.AssessmentLocation;

//java
import java.util.Calendar;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class BBLineitem
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
    private Float pointsPossible;
    private String title;
    private String type;
    private Float weight;

    public BBLineitem()
    {
        System.out.println("Creating from nowt");
    }
    public BBLineitem(Object gi)
    {
        System.out.println("Creating from gi");
        Class gradableItem = gi.getClass();
        try
        {
            this.name = (String)gradableItem.getDeclaredMethod("getTitle").invoke(gi);
        }
        catch(Exception e)
        {
            this.title = "";
        }
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
        }
        catch(Exception e)
        {
            this.assessmentBbId = "";
        }
        //if(gi.getAssessmentLocation().equals(AssessmentLocation.EXTERNAL)){this.assessmentLocation = "EXTERNAL";}
        //else if(gi.getAssessmentLocation().equals(AssessmentLocation.INTERNAL)){this.assessmentLocation = "INTERNAL";}
        //else if(gi.getAssessmentLocation().equals(AssessmentLocation.UNSET)){this.assessmentLocation = "UNSET";}

        try{this.available = (Boolean)gradableItem.getDeclaredMethod("getIsVisibleInAllTerms").invoke(gi);}catch(Exception e){this.available = false;}
        try{this.columnPosition = (Integer)gradableItem.getDeclaredMethod("getPosition").invoke(gi);}catch(Exception e){}
        try{this.dateAdded = extractDate((Calendar)gradableItem.getDeclaredMethod("getDateAdded").invoke(gi));}catch(Exception e){}
        try{this.dateChanged = extractDate((Calendar)gradableItem.getDeclaredMethod("getDateModified").invoke(gi));}catch(Exception e){}
        try{this.lineItemBbId = ((String)gradableItem.getDeclaredMethod("getExternalId").invoke(gi));}catch(Exception e){}
        try{this.name = (String)gradableItem.getDeclaredMethod("getTitle").invoke(gi);}catch(Exception e){}
        //this.outcomeDefBbId = gi.getOutcomeDefinition().getId().toExternalString();
        try{this.pointsPossible = Float.parseFloat(((Double)gradableItem.getDeclaredMethod("getPoints").invoke(gi)).toString());}catch(Exception e){}
        //this.type = gi.getType();
        try{this.weight = Float.parseFloat(((Double)gradableItem.getDeclaredMethod("getWeight").invoke(gi)).toString());}catch(Exception e){}
    }
    public BBLineitem(Lineitem li)
    {
        System.out.println("Creating from li");
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
        this.pointsPossible = li.getPointsPossible();
        this.type = li.getType();
        this.weight = li.getWeight();
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

    public Float getPointsPossible()
    {
        return this.pointsPossible;
    }

    public void setPointsPossible(Float pointsPossible)
    {
        this.pointsPossible = pointsPossible;
    }

    public String getTitle()
    {
	return this.title;
    }

    public void setTitle(String title)
    {
	this.title = title;
    }

    public String getType()
    {
	return this.type;
    }

    public void setType(String type)
    {
	this.type = type;
    }

    public Float getWeight()
    {
        return this.weight;
    }

    public void setWeight(Float weight)
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

}
