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

import blackboard.data.discussionboard.Message;
/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class BBMessage
{
    private String forumBbId;
    private String messageBbId;
    private String parentMessageBbId;
    private String subject;
    private String text;

    public BBMessage(){}
    public BBMessage(Message m)
    {
	this.forumBbId = m.getForumId().toExternalString();
	this.messageBbId = m.getId().toExternalString();
	this.parentMessageBbId = m.getParentId().toExternalString();
	this.subject = m.getSubject();
	this.text = m.getBody().getText();
    }

    private String[] getMessageDetails()
    {
	return new String[]{
	    this.messageBbId,
	    this.parentMessageBbId,
	    this.forumBbId,
	    this.subject,
	    this.text
	};
    }

    public String getForumBbId()
    {
	return this.forumBbId;
    }
    
    public void setForumBbId(String forumBbId)
    {
	this.forumBbId = forumBbId;
    }
    
    public String getMessageBbId()
    {
	return this.messageBbId;
    }
    
    public void setMessageBbId(String messageBbId)
    {
	this.messageBbId = messageBbId;
    }

    public String getParentMessageBbId()
    {
	return this.parentMessageBbId;
    }
    
    public void setParentMessageBbId(String parentMessageBbId)
    {
	this.parentMessageBbId = parentMessageBbId;
    }

    public String getSubject()
    {
	return this.subject;
    }
    
    public void setSubject(String subject)
    {
	this.subject = subject;
    }

    public String getText()
    {
	return this.text;
    }
    
    public void setText(String text)
    {
	this.text = text;
    }

}
