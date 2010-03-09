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

package bbgrpws;

import blackboard.data.course.Group;
import java.util.Calendar;
/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class GroupDetails extends bbwscommon.BbWsDataDetails implements ReturnTypeInterface
{
    private Boolean available;
    private Boolean chatRoomsAvailable;
    private String description;
    private Boolean discussionBoardsAvailable;
    private Boolean emailAvailable;
    private String bbId;
    private String modifiedDate;
    private String title;
    private Boolean transferAreaAvailable;

    public GroupDetails(){}
    public GroupDetails(Group g)
    {
	this.available = g.getIsAvailable();
	this.chatRoomsAvailable = g.getIsChatRoomAvailable();
	this.description = g.getDescription().getText();
	this.discussionBoardsAvailable = g.getIsDiscussionBoardAvailable();
	this.emailAvailable = g.getIsEmailAvailable();
	this.bbId = g.getId().toExternalString();
	this.modifiedDate = extractDate(g.getModifiedDate());
	this.title = g.getTitle();
	this.transferAreaAvailable = g.getIsTransferAreaAvailable();
    }
    
    public Boolean getAvailable()
    {
	return this.available;
    }
    
    public void setAvailable(Boolean available)
    {
	this.available = available;
    }
    
    public Boolean getChatRoomsAvailable()
    {
	return this.chatRoomsAvailable;
    }
    
    public void setChatRoomsAvailable(Boolean chatRoomsAvailable)
    {
	this.chatRoomsAvailable = chatRoomsAvailable;
    }
    
    public String getDescription()
    {
	return this.description;
    }
    
    public void setDescription(String description)
    {
	this.description = description;
    }
    
    public Boolean getDiscussionBoardsAvailable()
    {
	return this.discussionBoardsAvailable;
    }
    
    public void setDiscussionBoardsAvailable(Boolean discussionBoardsAvailable)
    {
	this.discussionBoardsAvailable = discussionBoardsAvailable;
    }
    
    public Boolean getEmailAvailable()
    {
	return this.emailAvailable;
    }
    
    public void setEmailAvailable(Boolean emailAvailable)
    {
	this.emailAvailable = emailAvailable;
    }
    @Override
    public String getBbId()
    {
	return this.bbId;
    }
    @Override
    public void setBbId(String groupBbId)
    {
	this.bbId = groupBbId;
    }
    
    public String getModifiedDate()
    {
	return this.modifiedDate;
    }
    
    public void setModifiedDate(String modifiedDate)
    {
	this.modifiedDate = modifiedDate;
    }
    
    public String getTitle()
    {
	return this.title;
    }
    
    public void setTitle(String title)
    {
	this.title = title;
    }
    
    public Boolean getTransferAreaAvailable()
    {
	return this.transferAreaAvailable;
    }
    
    public void setTransferAreaAvailable(Boolean transferAreaAvailable)
    {
	this.transferAreaAvailable = transferAreaAvailable;
    }

    private String extractDate(Calendar cal)
    {
        try
        {
            return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
        }
        catch (Exception e)
        {
            return "Never";
        }
    }

    public String[] toStringArray()
    {
        return new String[]{
            this.bbId,
            this.title,
            this.description,
            Boolean.toString(this.available),
            Boolean.toString(this.chatRoomsAvailable),
            Boolean.toString(this.discussionBoardsAvailable),
            Boolean.toString(this.emailAvailable),
            Boolean.toString(this.transferAreaAvailable),
            this.modifiedDate
        };
    }
    
    public String[] toStringArrayHeader()
    {
        return new String[]{"GroupBbId","Title","Description",
                            "Available","Chat Rooms Available",
                            "Discussion Boards Available","Email Available",
                            "Transfer Area Available","Modified Date"};
    }

/**
 *
 * @author vic
 */
    /*
    @Override public String getId() {
        return getBbId();
    }
    @Override public void setId(String id) {
        setBbId(id);
    }

    @Override public String getBatchUid() {
        return getGroupBbId();
    }
    @Override public void setBatchUid(String batchUid) {
        setGroupBbId(batchUid);
    }*/

}
