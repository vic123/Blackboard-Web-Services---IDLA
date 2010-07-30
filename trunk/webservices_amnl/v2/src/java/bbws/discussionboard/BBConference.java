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

package bbws.discussionboard;

import blackboard.data.discussionboard.Conference;
import blackboard.persist.discussionboard.ConferenceOwnerDbLoader;
/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class BBConference
{
    private Boolean available;
    private String conferenceBbId;
    private String conferenceType;
    private String description;
    private Integer position;
    private String title;

    public BBConference(){}
    public BBConference(Conference c)
    {
	this.available = c.getIsAvailable();
	this.conferenceBbId = c.getId().toExternalString();
	try{this.conferenceType = ConferenceOwnerDbLoader.Default.getInstance().loadById(c.getConferenceOwnerId()).getOwnerName();}catch(Exception e){this.conferenceType = "";}
	this.description = c.getDescription().getText();
	this.position = c.getPosition();
	this.title = c.getTitle();
    }
    
    public Boolean getAvailable()
    {
	return this.available;
    }
    
    public void setAvailable(Boolean available)
    {
	this.available = available;
    }
    
    public String getConferenceBbId()
    {
	return this.conferenceBbId;
    }
    
    public void setConferenceBbId(String conferenceBbId)
    {
	this.conferenceBbId = conferenceBbId;
    }
    
    public String getConferenceType()
    {
	return this.conferenceType;
    }
    
    public void setConferenceType(String conferenceType)
    {
	this.conferenceType = conferenceType;
    }
    
    public String getDescription()
    {
	return this.description;
    }
    
    public void setDescription(String description)
    {
	this.description = description;
    }
    
    public Integer getPosition()
    {
	return this.position;
    }
    
    public void setPosition(Integer position)
    {
	this.position = position;
    }
    
    public String getTitle()
    {
	return this.title;
    }
    
    public void setTitle(String title)
    {
	this.title = title;
    }

    private String[] getConferenceDetails()
    {
	return new String[]{
	    this.conferenceBbId,
	    this.conferenceType,
	    Boolean.toString(this.available),
	    Integer.toString(this.position),
	    this.title,
	    this.description
	};
    }
}
