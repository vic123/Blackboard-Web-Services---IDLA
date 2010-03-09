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
package bbdbws;

import blackboard.base.FormattedText;
import blackboard.base.FormattedText.Type;
import blackboard.data.course.Course;
import blackboard.data.course.Group;
import blackboard.data.discussionboard.Conference;
import blackboard.data.discussionboard.ConferenceOwner;
import blackboard.data.discussionboard.Forum;
import blackboard.data.discussionboard.Message;
import blackboard.data.discussionboard.datamanager.DiscussionBoardManager;
//import blackboard.data.discussionboard.datamanager.ForumManager;
import blackboard.data.discussionboard.datamanager.MessageManager;
//import blackboard.data.registry.SystemRegistryEntry;
//import blackboard.data.user.User;
import blackboard.persist.BbPersistenceManager;
import blackboard.persist.Id;
//import blackboard.persist.KeyNotFoundException;
import blackboard.persist.course.GroupDbLoader;
import blackboard.persist.discussionboard.ConferenceDbLoader;
import blackboard.persist.discussionboard.ConferenceDbPersister;
import blackboard.persist.discussionboard.ConferenceOwnerDbLoader;
import blackboard.persist.discussionboard.ForumDbPersister;
import blackboard.persist.discussionboard.MessageDbLoader;
import blackboard.persist.discussionboard.MessageDbPersister;
//import blackboard.persist.registry.SystemRegistryEntryDbLoader;
import blackboard.platform.BbServiceManager;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.discussionboard.ForumDbLoader;
//import blackboard.persist.user.UserDbLoader;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
//import java.util.Random;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
@WebService()
public class BBDiscussionBoardWebService
{
    private WebServiceProperties wsp = new WebServiceProperties("amnl","BBDiscussionBoardWebService");

    private String[] getConferenceDetails(BbPersistenceManager bbPm,Conference conf)
    {
	String conferenceType = "";
	try
	{
	    ConferenceOwnerDbLoader col = (ConferenceOwnerDbLoader)bbPm.getLoader(ConferenceOwnerDbLoader.TYPE);
	    ConferenceOwner co = col.loadById(conf.getConferenceOwnerId());
	    conferenceType = co.getOwnerName();
	}catch(Exception e){}

	return new String[]{conf.getId().toExternalString(),conferenceType,""+conf.getIsAvailable(),""+conf.getPosition(),conf.getTitle(),conf.getDescription().getText()};
    }
    
    private Conference getConferenceByGroupBbId(BbPersistenceManager bbPm, Id groupBbId) throws Exception
    {
	ConferenceDbLoader confl = (ConferenceDbLoader)bbPm.getLoader(ConferenceDbLoader.TYPE);
	return confl.loadByGroupId(groupBbId);
    }

     /**
     * Web service operation
     */
    @WebMethod
    public String[][] getConferenceDetailsForGivenConferenceBbId(@WebParam(name = "pwd") String pwd, @WebParam(name="confBbId") String confBbId, @WebParam(name = "headerDesc") Boolean headerDesc)
    {
	if(!wsp.isMethodAccessible("getConferenceDetailsForGivenConferenceBbId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	{
	    return new String[][]{{"Error"},{"Access denied to method"}};
	}

	BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	String cs[][] = new String[][]{{"Error"},{"No conference found"}}; 

	if(headerDesc==null)
	{
	    headerDesc = false;
	}

	try
	{
	    ConferenceDbLoader cl = (ConferenceDbLoader)bbPm.getLoader(ConferenceDbLoader.TYPE);
	    Conference conf = cl.loadById(bbPm.generateId(Conference.DATA_TYPE,confBbId));

	    if(conf!=null)
	    {
		int j=0;
		if(headerDesc)
		{
		    cs = new String[2][];
		    cs[0] = new String[]{"conferenceBbId","conference type","available","position","title","description"};
		    j=1;
		}
		else
		{
		    cs = new String[1][];
		}
		cs[j] = getConferenceDetails(bbPm,conf);
	    }
	}
	catch(Exception e)
	{
	    return new String[][]{{"Error"},{/*e.toString()*/"Could not find conference with that Id"}};
	}

	return cs;
    }

     /**
     * Web service operation
     */
    @WebMethod
    public String[][] getGroupConferenceDetailsForGivenGroupBbId(@WebParam(name = "pwd") String pwd, @WebParam(name="groupBbId") String groupBbId, @WebParam(name = "headerDesc") Boolean headerDesc)
    {
	if(!wsp.isMethodAccessible("getGroupConferenceDetailsForGivenGroupBbId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	{
	    return new String[][]{{"Error"},{"Access denied to method"}};
	}

	BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	String cs[][] = new String[][]{{"Error"},{"No conference found in group"}}; 
	
	if(headerDesc==null)
	{
	    headerDesc = false;
	}

	try
	{
	    GroupDbLoader gl = (GroupDbLoader)bbPm.getLoader(GroupDbLoader.TYPE);
	    Group g = gl.loadById(bbPm.generateId(Group.DATA_TYPE,groupBbId));
	    Conference conf = getConferenceByGroupBbId(bbPm,g.getId());

	    if(conf!=null)
	    {
		int j=0;
		if(headerDesc)
		{
		    cs = new String[2][];
		    cs[0] = new String[]{"conferenceBbId","conference type","available","position","title","description"};
		    j=1;
		}
		else
		{
		    cs = new String[1][];
		}
		cs[j] = getConferenceDetails(bbPm,conf);
	    }
	}
	catch(Exception e)
	{
	    return new String[][]{{"Error"},{/*e.toString()*/"Could not find group with that Id"}};
	}

	return cs;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String[][] getCourseConferenceDetailsForGivenCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name="courseId") String courseId, @WebParam(name = "headerDesc") Boolean headerDesc)
    {
	if(!wsp.isMethodAccessible("getCourseConferenceDetailsForGivenCourseId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	{
	    return new String[][]{{"Error"},{"Access denied to method"}};
	}

	BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	String cs[][] = new String[][]{{"Error"},{"No conference found in course"}}; 
	
	if(headerDesc==null)
	{
	    headerDesc = false;
	}

	try
	{
	    CourseDbLoader cl = (CourseDbLoader)bbPm.getLoader(CourseDbLoader.TYPE);
	    Course c = cl.loadByCourseId(courseId);
	    ConferenceDbLoader confl = (ConferenceDbLoader)bbPm.getLoader(ConferenceDbLoader.TYPE);
	    Conference conf = confl.loadByCourseId(c.getId());

	    if(conf!=null)
	    {
		int j=0;
		if(headerDesc)
		{
		    cs = new String[2][];
		    cs[0] = new String[]{"conferenceBbId","conference type","available","position","title","description"};
		    j=1;
		}
		else
		{
		    cs = new String[1][];
		}
		cs[j] = getConferenceDetails(bbPm,conf);
	    }
	}
	catch(Exception e)
	{
	    return new String[][]{{"Error"},{/*e.toString()*/"Could not find course with that Id"}};
	}
	
	return cs;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String[][] getAllGroupConferenceDetailsForGivenCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name="courseId") String courseId, @WebParam(name = "headerDesc") Boolean headerDesc)
    {
	if(!wsp.isMethodAccessible("getAllGroupConferenceDetailsForGivenCourseId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	{
	    return new String[][]{{"Error"},{"Access denied to method"}};
	}

	BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	String cs[][] = new String[][]{{"Error"},{"No conferences found in groups"}}; 

	if(headerDesc==null)
	{
	    headerDesc = false;
	}

	try
	{
	    CourseDbLoader cl = (CourseDbLoader)bbPm.getLoader(CourseDbLoader.TYPE);
	    Course c = cl.loadByCourseId(courseId);
	    GroupDbLoader gl = (GroupDbLoader)bbPm.getLoader(GroupDbLoader.TYPE);
	    ArrayList groups = gl.loadByCourseId(c.getId());

	    if(groups!=null && groups.size()>0)
	    {
		Conference conf = null;
		Group g = null;
		int j=0;

		if(headerDesc)
		{
		    cs = new String[groups.size()+1][];
		    cs[0] = new String[]{"conferenceBbId","conference type","available","position","title","description"};
		    j=1;
		}
		else
		{
		    cs = new String[groups.size()][];
		}

		for(int i=0; i<groups.size(); i++)
		{
		    g = (Group)groups.get(i);
		    conf = getConferenceByGroupBbId(bbPm,g.getId());
		    cs[j] = getConferenceDetails(bbPm,conf);
		    j+=1;
		}
	    }
	}
	catch(Exception e)
	{
	    return new String[][]{{"Error"},{/*e.toString()*/"Could not find course with that Id"}};
	}
	
	return cs;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String[][] getAllTypesOfConferencesForGivenCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name="courseID") String courseID, @WebParam(name = "headerDesc") Boolean headerDesc)
    {
	if(!wsp.isMethodAccessible("getAllTypesOfConferencesForGivenCourseId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	{
	    return new String[][]{{"Error"},{"Access denied to method"}};
	}

	BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	String cs[][] = new String[][]{{"Error"},{"No conferences found in course"}}; 

	if(headerDesc==null)
	{
	    headerDesc = false;
	}

	try
	{
	    CourseDbLoader cl = (CourseDbLoader)bbPm.getLoader(CourseDbLoader.TYPE);
	    Course c = cl.loadByCourseId(courseID);
	    ConferenceDbLoader confl = (ConferenceDbLoader)bbPm.getLoader(ConferenceDbLoader.TYPE);
	    ArrayList confs = confl.loadAllByCourseId(c.getId());

	    if(confs!=null && confs.size()>0)
	    {
		Conference conf = null;
		int j=0;

		if(headerDesc)
		{
		    cs = new String[confs.size()+1][];
		    cs[0] = new String[]{"conferenceBbId","conference type","available","position","title","description"};
		    j=1;
		}
		else
		{
		    cs = new String[confs.size()][];
		}

		for(int i=0; i<confs.size(); i++)
		{
		    conf = (Conference)confs.get(i);
		    cs[j] = getConferenceDetails(bbPm,conf);
		    j+=1;
		}
	    }
	}
	catch(Exception e)
	{
	    return new String[][]{{"Error"},{/*e.toString()*/"Could not find course with that Id"}};
	}
	
	return cs;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String[][] getForumDetailsForGivenConferenceBbId(@WebParam(name = "pwd") String pwd, @WebParam(name="confBbIdD") String conferenceBbId, @WebParam(name = "headerDesc") Boolean headerDesc)
    {
	if(!wsp.isMethodAccessible("getForumDetailsForGivenConferenceBbId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	{
	    return new String[][]{{"Error"},{"Access denied to method"}};
	}

	BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	String fs[][] = new String[][]{{"Error"},{"No forums found in conference"}}; 

	if(headerDesc==null)
	{
	    headerDesc = false;
	}
	
	try
	{
	    ConferenceDbLoader confl = (ConferenceDbLoader)bbPm.getLoader(ConferenceDbLoader.TYPE);
	    Conference conf = confl.loadById(bbPm.generateId(Conference.DATA_TYPE,conferenceBbId));
	    ForumDbLoader fl = (ForumDbLoader)bbPm.getLoader(ForumDbLoader.TYPE);
	    ArrayList forums = fl.loadByConferenceId(conf.getId());

	    if(forums!=null && forums.size()>0)
	    {
		Forum f = null;

		int j=0;
		if(headerDesc)
		{
		    fs = new String[forums.size()+1][];
		    fs[0] = new String[]{"forumBbId","available","position","title","description"};
		    j=1;
		}
		else
		{
		    fs = new String[forums.size()][];
		}

		for(int i=0; i<forums.size(); i++)
		{
		    f = (Forum)forums.get(i);
		    fs[j] = new String[]{f.getId().toExternalString(),""+f.getIsAvailable(),""+f.getPosition(),f.getTitle(),f.getDescription().getText()};
		    j+=1;
		}
	    }
	}
	catch(Exception e)
	{
	    return new String[][]{{"Error"},{e.toString()}};
	}
	
	return fs;
    }
    
    /**
     * Web service operation
     */
    @WebMethod
    public String[][] getTopLevelMessageForGivenForumBbId(@WebParam(name = "pwd") String pwd, @WebParam(name="forumBbID") String forumBbId, @WebParam(name = "headerDesc") Boolean headerDesc)
    {
	if(!wsp.isMethodAccessible("getTopLevelMessageForGivenForumBbId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	{
	    return new String[][]{{"Error"},{"Access denied to method"}};
	}

	BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	String ms[][] = new String[][]{{"Error"},{"No messages found in forum"}}; 

	if(headerDesc==null)
	{
	    headerDesc = false;
	}
	
	try
	{
	    ForumDbLoader fl = (ForumDbLoader)bbPm.getLoader(ForumDbLoader.TYPE);
	    Forum f = fl.loadById(bbPm.generateId(Forum.DATA_TYPE,forumBbId));
	    MessageDbLoader ml = (MessageDbLoader)bbPm.getLoader(MessageDbLoader.TYPE);
	    ArrayList messages = ml.loadByForumId(f.getId());

	    if(messages!=null && messages.size()>0)
	    {
		Message m = null;

		int j=0;
		if(headerDesc)
		{
		    ms = new String[messages.size()+1][];
		    ms[0] = new String[]{"messageBbId","parent messageBbId","forumBbId","subject","text"};
		    j=1;
		}
		else
		{
		    ms = new String[messages.size()][];
		}

		for(int i=0; i<messages.size(); i++)
		{
		    m = (Message)messages.get(i);
		    ms[j] = getMessageDetails(m);
		    j+=1;
		}
	    }
	    //Else ms = Error, No messages... (As above)
	}
	catch(Exception e)
	{
	    return new String[][]{{"Error"},{/*e.toString()*/"Could not find forum with that Id"}};
	}
	
	return ms;
    }
 
    /**
     * Web service operation
     *
     */
    @WebMethod
    public String[][] getImmediateChildMessagesForGivenMessageBbId(@WebParam(name = "pwd") String pwd, @WebParam(name="messageBbId") String messageBbId, @WebParam(name = "headerDesc") Boolean headerDesc)
    {
	if(!wsp.isMethodAccessible("getImmediateChildMessagesForGivenMessageBbId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	{
	    return new String[][]{{"Error"},{"Access denied to method"}};
	}

	BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	String ms[][] = new String[][]{{"Error"},{"No messages found"}}; 
	
	if(headerDesc==null)
	{
	    headerDesc = false;
	}

	try
	{
	    MessageDbLoader ml = (MessageDbLoader)bbPm.getLoader(MessageDbLoader.TYPE);
	    //List<Message> messages = ml.loadChildrenByMsgId(bbPm.generateId(Message.DATA_TYPE,messageID));
	    Message topLevMess = ml.loadById(bbPm.generateId(Message.DATA_TYPE,messageBbId),null,true,true);
	    ArrayList messages = topLevMess.getResponses();

	    if(messages!=null && messages.size()>0)
	    {
		Message m = null;

		int j=0;
		if(headerDesc)
		{
		    ms = new String[messages.size()+1][];
		    ms[0] = new String[]{"messageBbId","parent messageBbId","forumBbId","subject","text"};
		    j=1;
		}
		else
		{
		    ms = new String[messages.size()][];
		}

		for(int i=0; i<messages.size(); i++)
		{
		    m = (Message)messages.get(i);
		    ms[j] = getMessageDetails(m);
		    j+=1;
		}
	    }
	}
	catch(Exception e)
	{
	    return new String[][]{{"Error"},{/*e.toString()*/"Could not find message with that Id"}};
	}
	
	return ms;
    }
    
    /**
     * Web service operation
     *
     */
    @WebMethod
    public String[][] getAllMessagesForGivenThreadInGivenForumBbId(@WebParam(name = "pwd") String pwd, @WebParam(name="threadTopLevelMessageBbId") String threadTopLevelMessageBbId, @WebParam(name="forumBbId") String forumBbId, @WebParam(name = "headerDesc") Boolean headerDesc)
    {
	if(!wsp.isMethodAccessible("getAllMessagesForGivenThreadInGivenForumBbId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	{
	    return new String[][]{{"Error"},{"Access denied to method"}};
	}

	BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	ArrayList messages = new ArrayList();
	String ms[][] = new String[][]{{"Error"},{"No messages found in forum"}}; 
	
	if(headerDesc==null)
	{
	    headerDesc = false;
	}

	try
	{
	    ForumDbLoader fl = (ForumDbLoader)bbPm.getLoader(ForumDbLoader.TYPE);
	    Forum f = fl.loadById(bbPm.generateId(Forum.DATA_TYPE,forumBbId));
	    MessageManager mm = DiscussionBoardManager.getMessageManager();
	    List threads = mm.loadAllByForum(f.getId());
	    //Message topLevelMessage = mm.loadTopThreadById(bbPm.generateId(Message.DATA_TYPE,messageID));

	    Message topLevelMessage = null;
	    for(int i=0; i<threads.size(); i++)
	    {
		topLevelMessage = (Message)threads.get(i);
		if(topLevelMessage!=null && threadTopLevelMessageBbId.equals(topLevelMessage.getId().toExternalString()))
		{
		    break;
		}
		else
		{
		    topLevelMessage = null;
		}
	    }
	    
	    if(topLevelMessage!=null)
	    {
		messages.add(getMessageDetails(topLevelMessage));
		ArrayList toCopy = getAllMessages(topLevelMessage);
		if(toCopy!=null && toCopy.size()>0)
		{
		    messages.addAll(toCopy);
		}
	    }
	}
	catch(Exception e)
	{
	    return new String[][]{{"Error"},{e.toString()}};
	}
	
	try
	{
	    Message m = null;

	    int j=0;
	    if(headerDesc)
	    {
		ms = new String[messages.size()+1][];
		ms[0] = new String[]{"messageBbId","parent messageBbId","forumBbId","subject","text"};
		j=1;
	    }
	    else
	    {
		ms = new String[messages.size()][];
	    }

	    for(int i=0; i<messages.size(); i++)
	    {
		m = (Message)messages.get(i);
		ms[j] = getMessageDetails(m);
		j+=1;
	    }
	}
	catch(Exception e)
	{
	    return new String[][]{{"Error"},{e.toString()}};
	}

	return ms;
    }
    
    private ArrayList getAllMessages(Message m)
    {
	ArrayList messages = new ArrayList();

	//iterate over the messages
	Iterator iy = null;
	try
	{
		List topLevelMessages = m.getResponses();
		iy = topLevelMessages.iterator();
	}
	catch(Exception e)
	{
		//no messages here
	}

	while(null != iy && iy.hasNext())
	{
		Message reply = (Message)iy.next();
		messages.add(getMessageDetails(reply));
		
		ArrayList toCopy = getAllMessages(reply);
		if(toCopy!=null && toCopy.size()>0)
		{
		    messages.addAll(toCopy);
		}
	}

	return messages;
    }
    
    private String[] getMessageDetails(Message m)
    {
	//Id, parentId, forumId, subject, body
	return new String[]{m.getId().toExternalString(),m.getParentId().toExternalString(),m.getForumId().toExternalString(),m.getSubject(),m.getBody().getText()};
    }
    
    /**
     * Web service operation
     *
     *	forum ID - required
     *	parent Message ID - null/blank for new thread in forum
     *
     */
    @WebMethod
    public String addMessageToGivenForumBbIdOrMessageBbId(@WebParam(name = "pwd") String pwd, @WebParam(name="forumBbId") String forumBbId, @WebParam(name="parentMessageBbId") String parentMessageBbId,
				@WebParam(name="subject") String subject, @WebParam(name="body") String body)
    {
	if(!wsp.isMethodAccessible("addMessageToGivenForumBbIdOrMessageBbId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	{
	    return "Error: Access denied to method";
	}

	BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	
	try
	{
	    MessageDbPersister mp = (MessageDbPersister)bbPm.getPersister(MessageDbPersister.TYPE);
	    Message m = new Message();

	    if(forumBbId!=null && !forumBbId.equals(""))
	    {
		forumBbId = forumBbId.trim();

		try
		{
		    m.setForumId(bbPm.generateId(Message.DATA_TYPE,forumBbId));
		}
		catch(Exception e)
		{
		    return "Error: Invalid forumBbId";
		}
	    }
	    else
	    {
		return "Error: You \"must\" provide a forumBbId";
	    }
	    
	    if(parentMessageBbId!=null && !parentMessageBbId.equals(""))
	    {
		try
		{
		    m.setParentId(bbPm.generateId(Message.DATA_TYPE,parentMessageBbId));
		}
		catch(Exception e)
		{
		    return "Error: Please provide a valid Id for the parent message";
		}
	    }
	    //else ?

	    if(subject!=null)
	    {
		subject = subject.trim();
		if(!subject.equalsIgnoreCase("") && !containsSpecialCharacters(subject))
		{
		    //m.setSubject("Testing subject: "+(new Random().nextInt()));
		    m.setSubject(subject);
		}
	    }

	    if(body!=null)
	    {
		body = body.trim();
		if(!body.equalsIgnoreCase("") && !containsSpecialCharacters(body))
		{
		    //m.setBody(new FormattedText("Testing body"+(new Random().nextInt()),FormattedText.Type.PLAIN_TEXT));
		    m.setBody(new FormattedText(body,Type.PLAIN_TEXT));
		}
	    }

	    m.setPostDate(Calendar.getInstance());
	    mp.persist(m);
	}
	catch(Exception e)
	{
	    return e.toString();
	}

	return "OK";
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String deleteAllForumsInGivenConferenceBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "confBbId") String confBbId)
    {
	if(!wsp.isMethodAccessible("deleteAllForumsInGivenConferenceBbId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	{
	    return "Error: Access denied to method";
	}

	    BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();

	    try
	    {
		//Loading it first proves it exists
		ConferenceDbLoader cl = (ConferenceDbLoader)bbPm.getLoader(ConferenceDbLoader.TYPE);
		Conference c = cl.loadById(bbPm.generateId(Conference.DATA_TYPE,confBbId));

		ForumDbPersister fp = (ForumDbPersister)bbPm.getPersister(ForumDbPersister.TYPE);
		fp.deleteByConferenceId(c.getId());
	    }
	    catch(Exception e)
	    {
		return "Error: Could not delete all forums by given conferenceBbId, is the Id valid? Does it exist?";
	    }

	    return "OK";
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String deleteGivenForumAndSubThreads(@WebParam(name = "pwd") String pwd, @WebParam(name = "forumBbId") String forumBbId)
    {
	if(!wsp.isMethodAccessible("deleteGivenForumAndSubThreads") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	{
	    return "Error: Access denied to method";
	}

	    BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();

	    try
	    {
		//Loading it first proves it exists
		ForumDbLoader fl = (ForumDbLoader)bbPm.getLoader(ForumDbLoader.TYPE);
		Forum f = fl.loadById(bbPm.generateId(Forum.DATA_TYPE,forumBbId));

		ForumDbPersister fp = (ForumDbPersister)bbPm.getPersister(ForumDbPersister.TYPE);
		fp.deleteById(f.getId());
	    }
	    catch(Exception e)
	    {
		return "Error: Could not delete given forum, is the Id valid? Does it exist?";
	    }

	    return "OK";
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String deleteAllThreadsInGivenForumBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "forumBbId") String forumBbId)
    {
	if(!wsp.isMethodAccessible("deleteAllThreadsInGivenForumBbId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	{
	    return "Error: Access denied to method";
	}

	BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();

	try
	{
	    //Loading it first proves it exists
	    ForumDbLoader fl = (ForumDbLoader)bbPm.getLoader(ForumDbLoader.TYPE);
	    Forum f = fl.loadById(bbPm.generateId(Forum.DATA_TYPE,forumBbId));

	    MessageDbPersister mp = (MessageDbPersister)bbPm.getPersister(MessageDbPersister.TYPE);
	    mp.deleteByForumId(f.getId());
	}
	catch(Exception e)
	{
	    return "Error: Could not delete all threads by given forum Id, is the Id valid? Does it exist?";
	}

	return "OK";
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String deleteMessageAndAllSubMessages(@WebParam(name = "pwd") String pwd, @WebParam(name = "messageBbId") String messageBbId)
    {
	if(!wsp.isMethodAccessible("deleteMessageAndAllSubMessages") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	{
	    return "Error: Access denied to method";
	}

	BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();

	try
	{
	    //Loading it first proves it exists
	    MessageDbLoader ml = (MessageDbLoader)bbPm.getLoader(MessageDbLoader.TYPE);
	    Message m = ml.loadById(bbPm.generateId(Message.DATA_TYPE,messageBbId));

	    MessageDbPersister mp = (MessageDbPersister)bbPm.getPersister(MessageDbPersister.TYPE);
	    mp.deleteById(m.getId());
	}
	catch(Exception e)
	{
	    return "Error: Could not delete given message(s), is the Id valid? Does it exist?";
	}

	return "OK";
    }
    
    private Boolean containsSpecialCharacters(String toCheck)
    {
	if(/*!toCheck.contains(" ") && */!toCheck.contains("@")
	    && !toCheck.contains("%") && !toCheck.contains("&")
	    && !toCheck.contains("#") && !toCheck.contains("<")
	    && !toCheck.contains(">") && !toCheck.contains("=")
	    && !toCheck.contains("+"))
	{
	    //doesn't contain any of the checked characters
	    return false;
	}

	return true;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String addForumToGivenConferenceBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "confBbId") String confBbId, @WebParam(name = "title") String title,
						@WebParam(name = "description") String description, @WebParam(name = "descType") String descType,
						@WebParam(name = "available") Boolean available)
    {
	if(!wsp.isMethodAccessible("addForumToGivenConferenceBbId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	{
	    return "Error: Access denied to method";
	}

	BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	
	try
	{
	    ForumDbPersister fp = (ForumDbPersister)bbPm.getPersister(ForumDbPersister.TYPE);
	    Forum f = new Forum();
	    
	    Conference conf = null;
	    try
	    {
		//Proves the conference exists
		ConferenceDbLoader cl = (ConferenceDbLoader)bbPm.getLoader(ConferenceDbLoader.TYPE);
		conf = cl.loadById(bbPm.generateId(Conference.DATA_TYPE,confBbId));
	    }
	    catch(Exception e)
	    {
		return "Error: The conferenceBbId seems to be invalid or does not exist";
	    }
	    f.setConferenceId(conf.getId());

	    try
	    {
		f.setDescription(new FormattedText(description,Type.fromFieldName(descType)));
	    }
	    catch(Exception e)
	    {
		return "Error: Invalid description formatted text type, try: HTML/PLAIN_TEXT/SMART_TEXT";
	    }
	    f.setIsAvailable(available);
	    f.setTitle(title);

	    fp.persist(f);
	}
	catch(Exception e)
	{
	    return "Error: "+e.toString();
	}

	return "OK";
    }

    /**
     * Web service operation
     *
     * 3 types -
     *		    system conference - 1:1 relationship
     *		    course conference - 1:1 relationship 
     *		    group conference - created on creation of group + discussion board enabled.
     *
     *	conferenceType - COURSE_MAIN/ORANIZATION?/SYSTEM?/GROUPS
     *
     */
    @WebMethod
    public String addConference(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "groupBbId") String groupId,
						@WebParam(name = "conferenceType") String conferenceType, @WebParam(name = "available") Boolean available,
						@WebParam(name = "title") String title, @WebParam(name = "description") String description)
    {
	if(!wsp.isMethodAccessible("addConference") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	{
	    return "Error: Access denied to method";
	}

	BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();

	try
	{
	    ConferenceDbPersister cp = (ConferenceDbPersister)bbPm.getPersister(ConferenceDbPersister.TYPE);
	    Conference c = new Conference();

	    //Should this encompass most of the code? - possibly
	    if(conferenceType!=null && !conferenceType.equals(""))
	    {
		if(conferenceType.equalsIgnoreCase("GROUPS") && groupId!=null && !groupId.equals(""))
		{
		    Group g = null;
		    try
		    {
			//Proves the group exists
			GroupDbLoader gl = (GroupDbLoader)bbPm.getLoader(GroupDbLoader.TYPE);
			g = gl.loadById(bbPm.generateId(Group.DATA_TYPE,groupId));
		    }
		    catch(Exception e)
		    {
			return "Error: The group id seems to be invalid or does not exist";
		    }

		    c.setGroupId(g.getId());
		}

		if(courseId!=null && !courseId.equals(""))
		{
		    Course crse = null;
		    try
		    {
			//Proves the course exists
			CourseDbLoader cl = (CourseDbLoader)bbPm.getLoader(CourseDbLoader.TYPE);
			crse = cl.loadByCourseId(courseId);
		    }
		    catch(Exception e)
		    {
			return "Error: The course id seems to be invalid or does not exist";
		    }
		    c.setCourseId(crse.getId());
		}
	    }

	    ConferenceOwner co = null;
	    if(conferenceType!=null && !conferenceType.equals(""))
	    {
		ArrayList confOwners = new ArrayList();
		confOwners.add("GROUPS");
		confOwners.add("COURSE_MAIN");
		confOwners.add("ORGANIZATION");
		confOwners.add("SYSTEM");
		conferenceType = conferenceType.trim().toUpperCase();

		if(confOwners.contains(conferenceType))
		{
		    co = new ConferenceOwner();
		    co.setOwnerName(conferenceType);
		}
	    }
	    c.setConferenceOwnerId(co.getId());

    	if(description!=null)
	    {
            description = description.trim();
            if(!description.equalsIgnoreCase("") && !containsSpecialCharacters(description))
            {
                c.setDescription(new FormattedText(description,Type.PLAIN_TEXT));
            }
            else
            {
                return "Error: Invalid description";
            }
	    }
	    else
	    {
            return "Error: Invalid description";
	    }

	    if(title!=null)
	    {
		title = title.trim();
		if(!title.equalsIgnoreCase("") && !containsSpecialCharacters(title))
		{
		    c.setTitle(title);
		}
		else
		{
		    return "Error: Invalid title";
		}
	    }
	    else
	    {
		return "Error: Invalid title";
	    }

	    c.setIsAvailable(true);
	    
	    cp.persist(c);
	}
	catch(Exception e)
	{
	    return e.toString();
	}

	return "OK";
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String deleteAllConferencesInGivenCourseBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseBbId") String courseBbId)
    {
	if(!wsp.isMethodAccessible("deleteAllConferencesInGivenCourseBbId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	{
	    return "Error: Access denied to method";
	}

	BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
	int i=0;

	/*try
	{
	    //Loading it first proves it exists
	    ConferenceDbLoader cl = (ConferenceDbLoader)bbPm.getLoader(ConferenceDbLoader.TYPE);
	    ArrayList cs = cl.loadAllByCourseId(bbPm.generateId(Course.DATA_TYPE,courseId));
	    cl.

	    ConferenceDbPersister cp = (ConferenceDbPersister)bbPm.getPersister(ConferenceDbPersister.TYPE);
	    Conference c = null;

	    for(int i=0; i<cs.size();i++)
	    {
		c = (Conference)cs.get(i);
		cp.deleteById(c.getId());
	    }
	}
	catch(Exception e)
	{
	    return "Error: Could not delete all conferences by given course Id, is the Id valid? Does it exist?"+i;
	}*/

	return "todo"+i;
    }

    /**
     * Web service operation
     *
     * Effectively disables the discussion board, new one created on enabling? - probably
     *
     */
    @WebMethod
    public String deleteGivenConferenceAndSubForums(@WebParam(name = "pwd") String pwd, @WebParam(name = "confBbId") String confBbId)
    {
	if(!wsp.isMethodAccessible("deleteGivenConferenceAndSubForums") || (wsp.usingPassword() && !wsp.passwordMatches(pwd)))
	{
	    return "Error: Access denied to method";
	}

	BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();

	try
	{
	    //Loading it first proves it exists
	    ConferenceDbLoader cl = (ConferenceDbLoader)bbPm.getLoader(ConferenceDbLoader.TYPE);
	    Conference c = cl.loadById(bbPm.generateId(Conference.DATA_TYPE,confBbId));

	    ConferenceDbPersister cp = (ConferenceDbPersister)bbPm.getPersister(ConferenceDbPersister.TYPE);
	    cp.deleteById(c.getId());
	}
	catch(Exception e)
	{
	    return "Error: Could not delete given conference, is the Id valid? Does it exist?";
	}

	return "OK";
    }
}
