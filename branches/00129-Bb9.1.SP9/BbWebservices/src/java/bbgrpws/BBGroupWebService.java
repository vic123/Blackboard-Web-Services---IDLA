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

//blackboard
import blackboard.base.FormattedText.Type;
import blackboard.base.FormattedText;
import blackboard.data.course.Group;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;
import blackboard.persist.user.UserDbLoader;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.course.GroupDbLoader;
import blackboard.persist.course.GroupMembershipDbLoader;
import blackboard.persist.course.GroupMembershipDbPersister;
import blackboard.data.course.GroupMembership;
import blackboard.persist.course.GroupDbPersister;
import blackboard.platform.BbServiceManager;

//java
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

//javax
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.ws.WebServiceException;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
@WebService()
public class BBGroupWebService
{
    private WebServiceProperties wsp = new WebServiceProperties("IDLA","BbWebservices");

    private Boolean addGivenUserToGivenGroup(String userId, String groupId) throws WebServiceException
    {
        try
        {
            Group g = GroupDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,groupId));
            GroupMembership gm = new GroupMembership();
            gm.setCourseMembershipId(CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(g.getCourseId(),UserDbLoader.Default.getInstance().loadByUserName(userId).getId()).getId());
            gm.setGroupId(g.getId());
            GroupMembershipDbPersister.Default.getInstance().persist(gm);
        }
        catch(PersistenceException pe)
        {
            throw new WebServiceException("Error: Is user already part of this group?");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while trying to add user to group: "+e.toString());
        }
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean addGivenUserToGivenGroup(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "groupBbId") String groupId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"addGivenUserToGivenGroup");
        return addGivenUserToGivenGroup(userId,groupId);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String addGivenUserToGivenGroupXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "groupBbId") String groupId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"addGivenUserToGivenGroupXML");
        return toXML(null,addGivenUserToGivenGroup(userId,groupId));
    }

    /**
     * Web service operation
     */
    @WebMethod
    private Boolean addGroupToGivenCourse(String courseId, String title, String description, String descType,
					Boolean available, Boolean chatRoomAvailable, Boolean discussionBoardAvailable,
                    Boolean emailAvailable, Boolean transferAvailable) throws WebServiceException
    {
        try
        {
            Group g = new Group();
            g.setCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId());
            g.setDescription(new FormattedText(description,Type.fromFieldName(descType.trim().toUpperCase())));
            g.setIsAvailable(available);
            g.setIsChatRoomAvailable(chatRoomAvailable);
            g.setIsDiscussionBoardAvailable(discussionBoardAvailable);
            g.setIsEmailAvailable(emailAvailable);
            g.setIsTransferAreaAvailable(transferAvailable);
            g.setTitle(title);
            GroupDbPersister.Default.getInstance().persist(g);
        }
        catch(Exception e)
        {
            //return "Invalid description formatted text type, try: HTML/PLAIN_TEXT/SMART_TEXT";
            throw new WebServiceException("Error while trying to add group "+e.toString());
        }
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean addGroupToGivenCourse(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "title") String title,
					@WebParam(name = "description") String description, @WebParam(name = "descType") String descType,
					@WebParam(name = "available") Boolean available, @WebParam(name = "chatRoomAvailable") Boolean chatRoomAvailable,
					@WebParam(name = "discussionBoardAvailable") Boolean discussionBoardAvailable, @WebParam(name = "emailAvailable") Boolean emailAvailable,
					@WebParam(name = "transferAvailable") Boolean transferAvailable) throws WebServiceException
    {
        authoriseMethodUse(pwd,"addGroupToGivenCourse");
        return addGroupToGivenCourse(courseId, title, description, descType,
                                        available, chatRoomAvailable, discussionBoardAvailable,
                                        emailAvailable, transferAvailable);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String addGroupToGivenCourseXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "title") String title,
					@WebParam(name = "description") String description, @WebParam(name = "descType") String descType,
					@WebParam(name = "available") Boolean available, @WebParam(name = "chatRoomAvailable") Boolean chatRoomAvailable,
					@WebParam(name = "discussionBoardAvailable") Boolean discussionBoardAvailable, @WebParam(name = "emailAvailable") Boolean emailAvailable,
					@WebParam(name = "transferAvailable") Boolean transferAvailable) throws WebServiceException
    {
        authoriseMethodUse(pwd,"addGroupToGivenCourseXML");
        return toXML(null,addGroupToGivenCourse(courseId, title, description, descType,
                                        available, chatRoomAvailable, discussionBoardAvailable,
                                        emailAvailable, transferAvailable));
    }

    private void authoriseMethodUse(String methodPwd, String methodName) throws WebServiceException
    {
        if(!wsp.isMethodAccessible(methodName) || (wsp.usingPassword() && !wsp.passwordMatches(methodPwd)))
        {
            throw new WebServiceException("Access Denied");
        }
    }

    private Boolean deleteGivenUserFromGivenGroup(String userId, String groupId) throws WebServiceException
    {
        try
        {
            GroupMembershipDbPersister.Default.getInstance().deleteById(GroupMembershipDbLoader.Default.getInstance().loadByGroupAndUserId(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,groupId),UserDbLoader.Default.getInstance().loadByUserName(userId).getId()).getId());
        }
        catch(KeyNotFoundException knfe)
        {
            throw new WebServiceException("Error: User is not a member of this group");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while attempting to delete group membership: "+e.toString());
        }
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean deleteGivenUserFromGivenGroup(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "groupBbId") String groupId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteGivenUserFromGivenGroup");
        return deleteGivenUserFromGivenGroup(userId,groupId);
    }
    /**
     * Web service operation
     */
    @WebMethod
    public String deleteGivenUserFromGivenGroupXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "groupBbId") String groupId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteGivenUserFromGivenGroupXML");
        return toXML(null,deleteGivenUserFromGivenGroup(userId,groupId));
    }

    private Boolean deleteGroup(String groupId) throws WebServiceException
    {
        try
        {
            GroupDbPersister.Default.getInstance().deleteById(GroupDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,groupId)).getId());
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Please provide a valid Id for a group");
        }
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean deleteGroup(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteGroup");
        return deleteGroup(groupId);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String deleteGroupXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteGroupXML");
        return toXML(null,deleteGroup(groupId));
    }

    private List<GroupDetails> getGroupDetailsForGivenCourseId(String courseId) throws WebServiceException
    {
        try
        {
            List<GroupDetails> rl = new ArrayList<GroupDetails>();
            List<Group> gl = GroupDbLoader.Default.getInstance().loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId());
            if(gl.size()<1)
            {
                throw new Exception("No groups found");
            }
            Iterator i = gl.iterator();
            while(i.hasNext())
            {
                rl.add(new GroupDetails((Group)i.next()));
            }
            return rl;
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getGroupDetailsForGivenCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGroupDetailsForGivenCourseId");
        headerDesc = handleNullValue(headerDesc);
        List<String[]> rl = new ArrayList<String[]>();
        List<GroupDetails> gdl = getGroupDetailsForGivenCourseId(courseId);
        try
        {
            if(headerDesc)
            {
            rl.add(new GroupDetails().toStringArrayHeader());
            }
            Iterator i = gdl.iterator();
            while(i.hasNext())
            {
            rl.add(((GroupDetails)i.next()).toStringArray());
            }
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public List<GroupDetails> getGroupDetailsForGivenCourseIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGroupDetailsForGivenCourseIdWithNamedElements");
        return getGroupDetailsForGivenCourseId(courseId);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getGroupDetailsForGivenCourseIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGroupDetailsForGivenCourseIdXML");
        return toXML("Groups",getGroupDetailsForGivenCourseId(courseId));
    }

    private GroupDetails getGroupDetailsForGivenGroupBbId(String groupBbId) throws WebServiceException
    {
        try
        {
            return new GroupDetails(GroupDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,groupBbId)));
        }
        catch(KeyNotFoundException knfe)
        {
            throw new WebServiceException("Error: The given group does not exist");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve group for given Id "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getGroupDetailsForGivenGroupBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupBbId, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGroupDetailsForGivenGroupBbId");
        headerDesc = handleNullValue(headerDesc);
        try
        {
            List<String[]> rl = new ArrayList<String[]>();
            if(headerDesc)
            {
                rl.add(new GroupDetails().toStringArrayHeader());
            }
            rl.add(new GroupDetails(GroupDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,groupBbId))).toStringArray());
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(KeyNotFoundException knfe)
        {
            throw new WebServiceException("Error: The given group does not exist");
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve group for given Id "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public GroupDetails getGroupDetailsForGivenGroupBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGroupDetailsForGivenGroupBbIdWithNamedElements");
        return getGroupDetailsForGivenGroupBbId(groupBbId);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getGroupDetailsForGivenGroupBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGroupDetailsForGivenGroupBbIdXML");
        return toXML("Groups",getGroupDetailsForGivenGroupBbId(groupBbId));
    }

    private List<GroupMemberDetails> getGroupMembersForGivenGroupBbId(String groupBbId) throws WebServiceException
    {
        try
        {
            List<GroupMemberDetails> rl = new ArrayList<GroupMemberDetails>();
            List<GroupMembership> gml = GroupMembershipDbLoader.Default.getInstance().loadByGroupId(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,groupBbId));
            if(gml.size()<1)
            {
                throw new Exception("No group members found");
            }
            Iterator i = gml.iterator();
            while(i.hasNext())
            {
                rl.add(new GroupMemberDetails((GroupMembership)i.next()));
            }
            return rl;
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: "+e.toString());
        }
    }
    
    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[] getGroupMembersForGivenGroupBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGroupMembersForGivenGroupBbId");
        List<String> rl = new ArrayList<String>();
        List<GroupMemberDetails> gmdl = getGroupMembersForGivenGroupBbId(groupBbId);
        try
        {
            Iterator i = gmdl.iterator();
            while(i.hasNext())
            {
                rl.add(((GroupMemberDetails)i.next()).getUserId());
            }
            return (String[])rl.toArray(new String[rl.size()]);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod
    public List<GroupMemberDetails> getGroupMemberDetailsForGivenGroupBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGroupMemberDetailsForGivenGroupBbIdWithNamedElements");
        return getGroupMembersForGivenGroupBbId(groupBbId);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getGroupMemberDetailsForGivenGroupBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGroupMemberDetailsForGivenGroupBbIdXML");
        return toXML("GroupMembers",getGroupMembersForGivenGroupBbId(groupBbId));
    }

    private Boolean handleNullValue(Boolean value)
    {
        if (value == null)
        {
            return false;
        }
        return value;
    }

    private Boolean modifyGroup(String groupBbId, String title, String description,
                                String descType, Boolean available, Boolean chatRoomAvailable,
                                Boolean discussionBoardAvailable, Boolean emailAvailable,
                                Boolean transferAvailable) throws WebServiceException
    {
        try
        {
            Group g = GroupDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Group.DATA_TYPE,groupBbId));
            g.setDescription(new FormattedText(description,Type.fromFieldName(descType)));
            g.setIsAvailable(available);
            g.setIsChatRoomAvailable(chatRoomAvailable);
            g.setIsDiscussionBoardAvailable(discussionBoardAvailable);
            g.setIsEmailAvailable(emailAvailable);
            g.setIsTransferAreaAvailable(transferAvailable);
            g.setTitle(title);
            GroupDbPersister.Default.getInstance().persist(g);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while trying to add group "+e.toString());
        }
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean modifyGroup(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupBbId,/* @WebParam(name = "courseId") String courseId,*/
				@WebParam(name = "title") String title, @WebParam(name = "description") String description,
				@WebParam(name = "descType") String descType, @WebParam(name = "available") Boolean available,
				@WebParam(name = "chatRoomAvailable") Boolean chatRoomAvailable, @WebParam(name = "discussionBoardAvailable") Boolean discussionBoardAvailable,
				@WebParam(name = "emailAvailable") Boolean emailAvailable, @WebParam(name = "transferAvailable") Boolean transferAvailable) throws WebServiceException
    {
        authoriseMethodUse(pwd,"modifyGroup");
        return modifyGroup(groupBbId, title, description, descType, available, chatRoomAvailable,
                                discussionBoardAvailable, emailAvailable, transferAvailable);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String modifyGroupXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "groupBbId") String groupBbId,/* @WebParam(name = "courseId") String courseId,*/
				@WebParam(name = "title") String title, @WebParam(name = "description") String description,
				@WebParam(name = "descType") String descType, @WebParam(name = "available") Boolean available,
				@WebParam(name = "chatRoomAvailable") Boolean chatRoomAvailable, @WebParam(name = "discussionBoardAvailable") Boolean discussionBoardAvailable,
				@WebParam(name = "emailAvailable") Boolean emailAvailable, @WebParam(name = "transferAvailable") Boolean transferAvailable) throws WebServiceException
    {
        authoriseMethodUse(pwd,"modifyGroupXML");
        return toXML(null,modifyGroup(groupBbId, title, description, descType, available, chatRoomAvailable,
                                        discussionBoardAvailable, emailAvailable, transferAvailable));
    }

    private String toXML(String xmlTopLevelType, Object data) throws WebServiceException
    {
        StringBuffer for_return = new StringBuffer("");
        if(data==null)
        {
            throw new WebServiceException("Error parsing to xml: Passed data is null/blank");
        }

        if(data instanceof List)
        {
            if(xmlTopLevelType==null || xmlTopLevelType.equalsIgnoreCase(""))
            {
                throw new WebServiceException("Error parsing to xml: xmlTopLevelType is null/blank");
            }

            List list = (List)data;
            Iterator itrtr = list.iterator();
            for_return.append("<" + xmlTopLevelType + ">");
            while(itrtr.hasNext())
            {
                for_return.append(toXMLAnObject((ReturnTypeInterface)itrtr.next(),xmlTopLevelType.substring(0,xmlTopLevelType.length()-1)));
            }
            for_return.append("</"+xmlTopLevelType+">");
        }
        else if(data instanceof ReturnTypeInterface)
        {
            if(xmlTopLevelType==null || xmlTopLevelType.equalsIgnoreCase(""))
            {
                throw new WebServiceException("Error parsing to xml: xmlTopLevelType is null/blank");
            }

            for_return.append("<"+xmlTopLevelType+">");
            for_return.append(toXMLAnObject((ReturnTypeInterface)data,xmlTopLevelType.substring(0,xmlTopLevelType.length()-1)));
            for_return.append("</"+xmlTopLevelType+">");
        }
        else
        {
            for_return = new StringBuffer("<![CData[" + data + "]]>");
        }
        return for_return.toString();
    }

    private StringBuffer toXMLAnObject(ReturnTypeInterface rti, String subElementName)
    {
        StringBuffer for_return = new StringBuffer("<"+subElementName+">");
        for(int i=0;i<rti.toStringArrayHeader().length;i++)
        {
            for_return.append("<"+rti.toStringArrayHeader()[i]+">"+rti.toStringArray()[i]+"</"+rti.toStringArrayHeader()[i]+">");
        }
        return for_return.append("<"+subElementName+">");
    }
}
