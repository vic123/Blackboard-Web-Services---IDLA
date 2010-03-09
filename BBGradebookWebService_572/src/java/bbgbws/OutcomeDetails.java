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
import blackboard.data.gradebook.impl.Outcome;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class OutcomeDetails implements ReturnTypeInterface
{
    private String averageGrade;
    private Float averageScore;
    private String courseMembershipBbId;
    private String firstAttemptBbId;
    private String grade;
    private String gradebookStatus;
    private String highestAttemptBbId;
    private String instructorComments;
    private String lastAttemptBbId;
    private String lowestAttemptBbId;
    private String manualGrade;
    private Float manualScore;
    private String outcomeBbId;
    private String outcomeDefinitionBbId;
    private Float score;
    private String studentComments;
    private Float totalScore;
    
    public OutcomeDetails(){}
    public OutcomeDetails(Outcome o)
    {
        this.averageGrade = o.getAverageGrade(false);
        try{this.averageScore = o.getAverageScore();}catch(Exception e){this.averageScore = new Float(0);}
        this.courseMembershipBbId = o.getCourseMembershipId().toExternalString();
        this.firstAttemptBbId = o.getFirstAttemptId().toExternalString();
        this.grade = o.getGrade();
        this.gradebookStatus = o.getGradebookStatus().getDisplayName();
        try{this.highestAttemptBbId = o.getHighestAttemptId().toExternalString();}catch(Exception e){this.highestAttemptBbId = "";}
        this.instructorComments = o.getInstructorComments();
        this.lastAttemptBbId = o.getLastAttemptId().toExternalString();
        try{this.lowestAttemptBbId = o.getLowestAttemptId().toExternalString();}catch(Exception e){this.lowestAttemptBbId = "";}
        this.manualGrade = o.getManualGrade();
        try{this.manualScore = o.getManualScore();}catch(Exception e){this.manualScore = new Float(0);}
        this.outcomeBbId = o.getId().toExternalString();
        this.outcomeDefinitionBbId = o.getOutcomeDefinitionId().toExternalString();
        try{this.score = o.getScore();}catch(Exception e){this.score = new Float(0);}
        this.studentComments = o.getStudentComments();
        try{this.totalScore = o.totalScore();}catch(Exception e){this.totalScore = new Float(0);}
    }
    
    public String getAverageGrade()
    {
	return this.averageGrade;
    }
    
    public void setAverageGrade(String averageGrade)
    {
	this.averageGrade = averageGrade;
    }
    
    public Float getAverageScore()
    {
	return this.averageScore;
    }

    public void setAverageScore(Float averageScore)
    {
	this.averageScore = averageScore;
    }

    public String getCourseMembershipBbId()
    {
	return this.courseMembershipBbId;
    }

    public void setCourseMembershipBbId(String courseMembershipBbId)
    {
	this.courseMembershipBbId = courseMembershipBbId;
    }

    public String getFirstAttemptBbId()
    {
	return this.firstAttemptBbId;
    }

    public void setFirstAttemptBbId(String firstAttemptBbId)
    {
	this.firstAttemptBbId = firstAttemptBbId;
    }

    public String getGrade()
    {
	return this.grade;
    }

    public void setGrade(String grade)
    {
	this.grade = grade;
    }
    
    public String getGradebookStatus()
    {
	return this.gradebookStatus;
    }

    public void setGradebookStatus(String gradebookStatus)
    {
	this.gradebookStatus = gradebookStatus;
    }

    public String getHighestAttemptBbId()
    {
	return this.highestAttemptBbId;
    }

    public void setHighestAttemptBbId(String highestAttemptBbId)
    {
	this.highestAttemptBbId = highestAttemptBbId;
    }
	    
    public String getInstructorComments()
    {
	return this.instructorComments;
    }

    public void setInstructorComments(String instructorComments)
    {
	this.instructorComments = instructorComments;
    }

    public String getLastAttemptBbId()
    {
	return this.lastAttemptBbId;
    }

    public void setLastAttemptBbId(String lastAttemptBbId)
    {
	this.lastAttemptBbId = lastAttemptBbId;
    }

    public String getLowestAttemptBbId()
    {
	return this.lowestAttemptBbId;
    }

    public void setLowestAttemptBbId(String lowestAttemptBbId)
    {
	this.lowestAttemptBbId = lowestAttemptBbId;
    }

    public String getManualGrade()
    {
	return this.manualGrade;
    }

    public void setManualGrade(String manualGrade)
    {
	this.manualGrade = manualGrade;
    }

    public Float getManualScore()
    {
	return this.manualScore;
    }

    public void setManualScore(Float manualScore)
    {
	this.manualScore = manualScore;
    }

    public String getOutcomeBbId()
    {
	return this.outcomeBbId;
    }

    public void setOutcomeBbId(String outcomeBbId)
    {
	this.outcomeBbId = outcomeBbId;
    }
    
    public String getOutcomeDefinitionBbId()
    {
	return this.outcomeDefinitionBbId;
    }

    public void setOutcomeDefinitionBbId(String outcomeDefinitionBbId)
    {
	this.outcomeDefinitionBbId = outcomeDefinitionBbId;
    }
	    
    public Float getScore()
    {
	return this.score;
    }

    public void setScore(Float score)
    {
	this.score = score;
    }
    
    public String getStudentComments()
    {
	return this.studentComments;
    }

    public void setStudentComments(String studentComments)
    {
	this.studentComments = studentComments;
    }
    
    public Float getTotalScore()
    {
	return this.totalScore;
    }

    public void setTotalScore(Float totalScore)
    {
	this.totalScore = totalScore;
    }

    public String[] toStringArray()
    {
        return new String[]{this.averageGrade,
                    Float.toString(this.averageScore),
                    this.courseMembershipBbId,
                    this.firstAttemptBbId,
                    this.grade,
                    this.gradebookStatus,
                    this.highestAttemptBbId,
                    this.instructorComments,
                    this.lastAttemptBbId,
                    this.lowestAttemptBbId,
                    this.manualGrade,
                    Float.toString(this.manualScore),
                    this.outcomeBbId,
                    this.outcomeDefinitionBbId,
                    Float.toString(this.score),
                    this.studentComments,
                    Float.toString(this.totalScore)
        };
    }

    public String[] toStringArrayHeader()
    {
        return new String[]{"Average Grade",
                    "Average Score",
                    "Course Membership BbId",
                    "First Attempt BbId",
                    "Grade",
                    "Gradebook Status",
                    "Highest Attempt BbId",
                    "Instructor Comments",
                    "Last Attempt BbId",
                    "Lowest Attempt BbId",
                    "Manual Grade",
                    "Manual Score",
                    "Outcome BbId",
                    "Outcome Definition BbId",
                    "Score",
                    "Student Comments",
                    "totalScore"
        };
    }
}
