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

import blackboard.data.discussionboard.Forum;
/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class BBForum
{
    private Boolean available;
    private String description;
    private String forumBbId;
    private Integer position;
    private String title;

    public BBForum(){}
    public BBForum(Forum f)
    {
	this.available = f.getIsAvailable();
	this.description = f.getDescription().getText();
	this.forumBbId = f.getId().toExternalString();
	this.position = f.getPosition();
	this.title = f.getTitle();
    }

    private String[] getForumDetails()
    {
	return new String[]{
	    this.forumBbId,
	    Boolean.toString(this.available),
	    Integer.toString(this.position),
	    this.title,
	    this.description
	};
    }

    public Boolean getAvailable()
    {
	return this.available;
    }

    public void setAvailable(Boolean available)
    {
	this.available = available;
    }
    
    public String getDescription()
    {
	return this.description;
    }
    
    public void setDescription(String description)
    {
	this.description = description;
    }

    public String getForumBbId()
    {
	return this.forumBbId;
    }
    
    public void setForumBbId(String forumBbId)
    {
	this.forumBbId = forumBbId;
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

}
