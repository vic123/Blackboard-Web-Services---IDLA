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
import blackboard.data.gradebook.impl.Attempt;

//java
import java.util.Calendar;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class AttemptDetails implements ReturnTypeInterface
{
    public enum Verbosity{standard,extended}

    private Verbosity verbosity;
    //standard details
    private String attemptBbId;
    private String attemptedDate;
    private String grade;
    private Float score;
    private String status;
    //extended details
    private String dataType;
    private String dateCreated;
    private String dateModified;
    private String instructorComments;
    private String instructorNotes;
    private String linkRefBbId;
    private String outcomeBbId;
    private Boolean publicComments;
    private String resultObjectBbId;
    private String studentComments;

    public AttemptDetails(){}
    public AttemptDetails(Verbosity verbosity)
    {
        this.verbosity = verbosity;
    }
    public AttemptDetails(Attempt a,Verbosity verbosity) throws Exception
    {
        this.verbosity = verbosity;

        switch(this.verbosity)
        {
            case extended:
            this.dataType = a.getDataType().getName();
            this.dateCreated = extractDate(a.getDateCreated());
            this.dateModified = extractDate(a.getDateModified());
            this.instructorComments = a.getInstructorComments();
            this.instructorNotes = a.getInstructorNotes();
            this.linkRefBbId = a.getLinkRefId();
            this.outcomeBbId = a.getOutcomeId().getExternalString();
            this.publicComments = a.getCommentIsPublic();
            try{this.resultObjectBbId = a.getResultObjectId().getExternalString();}catch(Exception e){this.resultObjectBbId = "";}
            this.studentComments = a.getStudentComments();
            case standard:
            this.attemptBbId = a.getId().getExternalString();
            this.attemptedDate = extractDate(a.getAttemptedDate());
            this.grade = a.getGrade();
            this.score = a.getScore();
            this.status = a.getStatus().getDisplayName();
            return;
        }
        throw new Exception("Undefined verbosity of attempt");
    }

    public String getAttemptBbId()
    {
	return this.attemptBbId;
    }
    
    public void setAttemptBbId(String attemptBbId)
    {
	this.attemptBbId = attemptBbId;
    }
    
    public String getAttemptedDate()
    {
	return this.attemptedDate;
    }
    
    public void setAttemptedDate(String attemptedDate)
    {
	this.attemptedDate = attemptedDate;
    }

    public String getGrade()
    {
	return this.grade;
    }
    
    public void setGrade(String grade)
    {
	this.grade = grade;
    }

    public Float getScore()
    {
	return this.score;
    }

    public void setScore(Float score)
    {
	this.score = score;
    }

    public String getStatus()
    {
	return this.status;
    }
    
    public void setStatus(String status)
    {
	this.status = status;
    }

    public String getDataType()
    {
	return this.dataType;
    }

    public void setDataType(String dataType)
    {
	this.dataType = dataType;
    }

    public String getDateCreated()
    {
	return this.dateCreated;
    }
    
    public void setDateCreated(String dateCreated)
    {
	this.dateCreated = dateCreated;
    }
    
    public String getDateModified()
    {
	return this.dateModified;
    }
    
    public void setDateModified(String dateModified)
    {
	this.dateModified = dateModified;
    }
    
    public String getInstructorComments()
    {
	return this.instructorComments;
    }
    
    public void setInstructorComments(String instructorComments)
    {
	this.instructorComments = instructorComments;
    }
    
    public String getInstructorNotes()
    {
	return this.instructorNotes;
    }
    
    public void setInstructorNotes(String instructorNotes)
    {
	this.instructorNotes = instructorNotes;
    }
    
    public String getLinkRefBbId()
    {
	return this.linkRefBbId;
    }
    
    public void setLinkRefBbId(String linkRefBbId)
    {
	this.linkRefBbId = linkRefBbId;
    }
    
    public String getOutcomeBbId()
    {
	return this.outcomeBbId;
    }
    
    public void setOutcomeBbId(String outcomeBbId)
    {
	this.outcomeBbId = outcomeBbId;
    }
    
    public Boolean getPublicComments()
    {
	return this.publicComments;
    }
    
    public void setPublicComments(Boolean publicComments)
    {
	this.publicComments = publicComments;
    }

    public String getResultObjectBbId()
    {
	return this.resultObjectBbId;
    }
    
    public void setResultObjectBbId(String resultObjectBbId)
    {
	this.resultObjectBbId = resultObjectBbId;
    }

    public String getStudentComments()
    {
	return this.studentComments;
    }
    
    public void setStudentComments(String studentComments)
    {
	this.studentComments = studentComments;
    }

    private String extractDate(Calendar cal)
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

    public String[] toStringArray()
    {
        switch(this.verbosity)
        {
            case standard:
                return new String[]{this.attemptBbId,
                        this.grade,
                        Float.toString(this.score),
                        this.status,
                        this.attemptedDate};
            case extended:
                return new String[]{this.attemptBbId,
                        this.grade,
                        Float.toString(this.score),
                        this.status,
                        this.attemptedDate,
                        this.outcomeBbId,
                        this.resultObjectBbId,
                        this.dataType,
                        Boolean.toString(this.publicComments),
                        this.instructorComments,
                        this.studentComments,
                        this.instructorNotes,
                        this.linkRefBbId,
                        this.dateCreated,
                        this.dateModified};
            default:
                return new String[]{};
        }
    }

    public String[] toStringArrayHeader()
    {
        switch(this.verbosity)
        {
            case standard:
                return new String[]{"AttemptBbId","Grade","Score",
                        "Status","Attempted Date"};
            case extended:
                return new String[]{"AttemptBbId","Grade","Score",
                        "Status","Attempted Date",
                        "Outcome BbId","Result Object BbId",
                        "Data Type","Public Comments",
                        "Instructor Comments","Student Comments",
                        "Instructor Notes","Link Ref BbId",
                        "Date Created","Date Modified"};
            default:
                return new String[]{};
        }
    }
}
