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

//blackboard
import blackboard.data.gradebook.Score;
import blackboard.data.gradebook.Score.AttemptLocation;

//java
import java.util.Calendar;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class ScoreDetails implements ReturnTypeInterface
{
    public enum Verbosity{standard,extended}

    private Verbosity verbosity;
    //standard details
    private String dateAdded;
    private String dateChanged;
    private String dateModified;
    private String grade;
    private String outcomeDefBbId;
    private String scoreBbId;
    //extended details
    private String attemptBbId;
    private String attemptLocation;
    private String courseMembershipBbId;
    private String dataType;
    private String lineItemBbId;

    public ScoreDetails(){}
    public ScoreDetails(Verbosity verbosity)
    {
        this.verbosity = verbosity;
    }
    public ScoreDetails(Score s,Verbosity verbosity)
    {
        this.verbosity = verbosity;

        switch(this.verbosity)
        {
            case extended:
            this.attemptBbId = "";
            Object o = s.getAttemptId();
            if(o!=null)
            {
                if(o.getClass().getName().equalsIgnoreCase("java.lang.String"))
                {
                 this.attemptBbId = o.toString();
                }
                //Need to test
                /*if(attmptId=="blackboard.persist.Id")
                {
                scr[5] = ((Id)o).getExternalString();
                }*/
            }
            AttemptLocation al = s.getAttemptLocation();
            if(al.equals(AttemptLocation.EXTERNAL))	{this.attemptLocation = "EXTERNAL";}
            else if(al.equals(AttemptLocation.INTERNAL)){this.attemptLocation = "INTERNAL";}
            else{this.attemptLocation = "UNSET";}
            this.courseMembershipBbId = s.getCourseMembershipId().toExternalString();
            this.dataType = s.getDataType().getName();
            this.lineItemBbId = s.getLineitemId().toExternalString();
            case standard:
            this.dateAdded = extractDate(s.getDateAdded());
            this.dateChanged = extractDate(s.getDateChanged());
            this.dateModified = extractDate(s.getModifiedDate());
            this.grade = s.getGrade();
            this.outcomeDefBbId = s.getOutcome().getOutcomeDefinitionId().getExternalString();
            this.scoreBbId = s.getId().getExternalString();
            return;
        }
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

    public String getDateModified()
    {
	return this.dateModified;
    }

    public void setDateModified(String dateModified) {
	this.dateModified = dateModified;
    }

    public String getGrade()
    {
	return this.grade;
    }

    public void setGrade(String grade)
    {
	this.grade = grade;
    }

    public String getOutcomeDefBbId()
    {
	return this.outcomeDefBbId;
    }

    public void setOutcomeDefBbId(String outcomeDefBbId) {
	this.outcomeDefBbId = outcomeDefBbId;
    }

    public String getScoreBbId()
    {
	return this.scoreBbId;
    }

    public void setScoreBbId(String scoreBbId)
    {
	this.scoreBbId = scoreBbId;
    }

    public String getAttemptBbId()
    {
	return this.attemptBbId;
    }
    
    public void setAttemptBbId(String attemptBbId)
    {
	this.attemptBbId = attemptBbId;
    }
    
    public String getAttemptLocation()
    {
	return this.attemptLocation;
    }
    
    public void setAttemptLocation(String attemptLocation)
    {
	this.attemptLocation = attemptLocation;
    }
    
    public String getCourseMembershipBbId()
    {
	return this.courseMembershipBbId;
    }

    public void setCourseMembershipBbId(String courseMembershipBbId)
    {
	this.courseMembershipBbId = courseMembershipBbId;
    }
    
    public String getDataType()
    {
	return this.dataType;
    }
    
    public void setDataType(String dataType)
    {
	this.dataType = dataType;
    }
    
    public String getLineItemBbId()
    {
	return this.lineItemBbId;
    }
    
    public void setLineItemBbId(String lineItemBbId)
    {
	this.lineItemBbId = lineItemBbId;
    }

    private String extractDate(Calendar cal)
    {
        try {
            return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
        } catch (Exception e) {
            return "Never";
        }
    }

    public String[] toStringArray()
    {
        switch(this.verbosity)
        {
            case standard:
            return new String[]{this.scoreBbId,
                        this.grade,
                        this.outcomeDefBbId,
                        this.dateAdded,
                        this.dateChanged,
                        this.dateModified};
            case extended:
            return new String[]{this.scoreBbId,
                        this.grade,
                        this.outcomeDefBbId,
                        this.dateAdded,
                        this.dateChanged,
                        this.dateModified,
                        this.courseMembershipBbId,
                        this.lineItemBbId,
                        this.attemptBbId,
                        this.attemptLocation,
                        this.dataType};
            default:
            return new String[]{};
        }
    }

    public String[] toStringArrayHeader()
    {
        switch(this.verbosity)
        {
            case standard:
            return new String[]{"ScoreBbId","Grade",
                        "OutcomeDefBbId","Date Added",
                        "Date Changed","Date Modified"};
            case extended:
            return new String[]{"ScoreBbId","Grade",
                        "OutcomeDefBbId","Date Added",
                        "Date Changed","Date Modified",
                        "Course Membership BbId","lineItemBbId",
                        "Attempt BbId","Attempt Location",
                        "Data Type"};
            default:
            return new String[]{};
        }
    }
}
