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

import blackboard.persist.gradebook.impl.OutcomeDefinitionDbLoader;
import blackboard.persist.gradebook.impl.OutcomeDefinitionDbPersister;
import blackboard.data.gradebook.Lineitem;
import blackboard.data.course.CourseMembership;
import blackboard.data.gradebook.Score;
import blackboard.data.gradebook.impl.Attempt;
import blackboard.data.gradebook.impl.GradeBookSettings;
import blackboard.data.gradebook.impl.Outcome;
import blackboard.data.gradebook.impl.OutcomeDefinition;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.gradebook.LineitemDbLoader;
import blackboard.persist.gradebook.LineitemDbPersister;
import blackboard.persist.gradebook.ScoreDbLoader;
import blackboard.persist.gradebook.impl.AttemptDbLoader;
import blackboard.persist.gradebook.impl.AttemptDbPersister;
import blackboard.persist.gradebook.impl.GradeBookSettingsDbLoader;
import blackboard.persist.gradebook.impl.GradeBookSettingsDbPersister;
import blackboard.persist.gradebook.impl.OutcomeDbLoader;
import blackboard.persist.user.UserDbLoader;
import blackboard.platform.BbServiceManager;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;


import blackboard.platform.gradebook2.*;
/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
@WebService()
public class BBGradebookWebService
{
    private WebServiceProperties wsp = new WebServiceProperties("amnl","BBGradebookWebService");
    //private enum Verbosity{standard,extended}

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean addLineitemToGivenCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "Name") String name,
						@WebParam(name = "type") String type, @WebParam(name = "pointsPossible") Float pointsPossible,
						@WebParam(name = "weight") Float weight, @WebParam(name = "available") Boolean available) throws WebServiceException
    {
	authoriseMethodUse(pwd,"addLineitemToGivenCourseId");
	try
	{
	    Lineitem li = new Lineitem();
	    li.setCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId());
	    //li.setAssessmentLocation(Lineitem.AssessmentLocation.INTERNAL);
	    li.setName(name);
	    li.setIsAvailable(available);
	    li.setPointsPossible(pointsPossible);
	    li.setType(type);
	    li.setWeight(weight);
	    ((LineitemDbPersister)BbServiceManager.getPersistenceService().getDbPersistenceManager().getPersister(LineitemDbPersister.TYPE)).persist(li);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: Could not add lineitem "+e.toString());
	}
	return true;
    }

    private void authoriseMethodUse(String methodPwd, String methodName) throws WebServiceException
    {
	if(!wsp.isMethodAccessible(methodName) || (wsp.usingPassword() && !wsp.passwordMatches(methodPwd)))
	{
	    throw new WebServiceException("Access Denied");
	}
    }

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean deleteGivenAttemptByAttemptBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "attemptBbId") String attemptBbId) throws WebServiceException
    {
	authoriseMethodUse(pwd,"deleteGivenAttemptByAttemptBbId");
	try
	{
	    AttemptDbPersister.Default.getInstance().deleteById(AttemptDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Attempt.DATA_TYPE,attemptBbId)).getId());
	}
	catch(Exception e)
	{
	    throw new WebServiceException( "Error: Could not delete attempt - "+e.getMessage());
	}
	return true;
    }

    private Boolean deleteOutcomeDefinitionByOutcomeDefBbId(String outcomeDefBbId) throws WebServiceException
    {
    	try
	{
	    OutcomeDefinitionDbPersister.Default.getInstance().deleteById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(OutcomeDefinition.DATA_TYPE,outcomeDefBbId));
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: Does that item/outcome exist? "+e.toString());
	}
	return true;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean deleteLineItemByLineItemBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitemBbId") String lineItemBbId) throws WebServiceException
    {
	authoriseMethodUse(pwd,"deleteLineItemByLineItemBbId");
	return deleteOutcomeDefinitionByOutcomeDefBbId(lineItemBbId);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean deleteOutcomeDefinitionByOutcomeDefBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
	authoriseMethodUse(pwd,"deleteOutcomeDefinitionByOutcomeDefBbId");
	return deleteOutcomeDefinitionByOutcomeDefBbId(outcomeDefBbId);
    }

    private List<LineitemDetails> getAllLineitemObjsForCourseId(String courseId) throws Exception
    {
        List<LineitemDetails> rl = new ArrayList<LineitemDetails>();
        Iterator i = null;
        //try{System.err.println(new blackboard.platform.plugin.PlugInManager().getPlatformVersion().toString());}catch(Exception e){System.err.println("Could not retrieve version info");}
        //if(true) - this should be a version check instead of (just) a try/catch block
        //{
            try
            {
                BbWsLog.logForward("getAllLineitemObjsForCourseId(String courseId): Class gradebookManagerFactoryClass = Class.forName(\"blackboard.platform.gradebook2.GradebookManagerFactory\");");
                Class gradebookManagerFactoryClass = Class.forName("blackboard.platform.gradebook2.GradebookManagerFactory");
                BbWsLog.logForward("getAllLineitemObjsForCourseId(String courseId): Object gradebookManager = gradebookManagerFactoryClass.getDeclaredMethod(\"getInstanceWithoutSecurityCheck\").invoke(gradebookManagerFactoryClass.newInstance());");
                Object gradebookManager = gradebookManagerFactoryClass.getDeclaredMethod("getInstanceWithoutSecurityCheck").invoke(gradebookManagerFactoryClass.newInstance());
                //blackboard.platform.gradebook2.GradebookManager gm = blackboard.platform.gradebook2.GradebookManagerFactory.getInstanceWithoutSecurityCheck();

                BbWsLog.logForward("getAllLineitemObjsForCourseId(String courseId): Class gbmClass = gradebookManager.getClass();");
                Class gbmClass = gradebookManager.getClass();
                BbWsLog.logForward("getAllLineitemObjsForCourseId(String courseId): List l = (List)gbmClass.getDeclaredMethod(...");
                List l = (List)gbmClass.getDeclaredMethod("getGradebookItems",new Class[]{blackboard.persist.Id.class}).invoke(gradebookManager,new Object[]{((CourseDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(CourseDbLoader.TYPE)).loadByCourseId(courseId).getId()});
                //List<blackboard.platform.gradebook2.GradableItem> l = gm.getGradebookItems(((CourseDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(CourseDbLoader.TYPE)).loadByCourseId(courseId).getId());
                BbWsLog.logForward("getAllLineitemObjsForCourseId(String courseId): i = l.iterator();"); 
                i = l.iterator();
                BbWsLog.logForward("getAllLineitemObjsForCourseId(String courseId): while (i.hasNext())"); 
                while (i.hasNext())
                {
                    rl.add(new LineitemDetails(i.next()));
                }
                //or you will get an out of memory error...
                l.clear();
                l = null;
            }
            catch(ClassNotFoundException cnfe)
            {
                BbWsLog.logForward("getAllLineitemObjsForCourseId(String courseId): Catched, List<Lineitem> lil = ((LineitemDbLoader)BbServiceManager..."); 
                List<Lineitem> lil = ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId());
                if(lil.size()<1)
                {
                    throw new Exception("No lineitems found");
                }
                i = lil.iterator();
                while(i.hasNext())
                {
                    rl.add(new LineitemDetails((Lineitem)i.next()));
                }
            }
        //}
        return rl;
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getAllLineItemsForCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "headerDesc") Boolean headerDesc)
    {
        authoriseMethodUse(pwd,"getAllLineItemsForCourseId");
        headerDesc = handleNullValue(headerDesc);
        try
        {
            List<String[]> rl = new ArrayList<String[]>();
            BbWsLog.logForward("getAllLineItemsForCourseId(@WebParam(name =: List<LineitemDetails> lil = getAllLineitemObjsForCourseId(courseId);");
            List<LineitemDetails> lil = getAllLineitemObjsForCourseId(courseId);
            if(headerDesc)
            {
                BbWsLog.logForward("getAllLineItemsForCourseId(@WebParam(name =:    rl.add(new LineitemDetails().toStringArrayHeader());");
            rl.add(new LineitemDetails().toStringArrayHeader());
            }
            BbWsLog.logForward("getAllLineItemsForCourseId(@WebParam(name =:    Iterator i = lil.iterator();");
            Iterator i = lil.iterator();
            BbWsLog.logForward("getAllLineItemsForCourseId(@WebParam(name =:    while(i.hasNext())");
            while(i.hasNext())
            {
                BbWsLog.logForward("getAllLineItemsForCourseId(@WebParam(name =:    rl.add(((LineitemDetails)i.next()).toStringArray());");
            rl.add(((LineitemDetails)i.next()).toStringArray());
            }
            BbWsLog.logForward("getAllLineItemsForCourseId(@WebParam(name =:    return (String[][])rl.toArray(new String[rl.size()][]);");
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(Exception e)
        {
            BbWsLog.logForward(e, "getAllLineItemsForCourseId(@WebParam(name =:    throw new WebServiceException(...");
            throw new WebServiceException("Error: Could not retrieve line items for this course "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getAllLineItemsForCourseIdWithNamedElements")
    public List<LineitemDetails> getAllLineItemsForCourseIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId)
    {
        authoriseMethodUse(pwd,"getAllLineItemsForCourseIdWithNamedElements");
        try
        {
            return getAllLineitemObjsForCourseId(courseId);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve line items for this course "+e.toString());
        }
    }

    private List<OutcomeDefinitionDetails> getAllOutcomeDefinitionsForGivenCourse(String courseId) throws Exception
    {
        List<OutcomeDefinition> ods = ((OutcomeDefinitionDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(OutcomeDefinitionDbLoader.TYPE)).loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId());
        if(ods.size()<1)
        {
            throw new Exception("No outcome defs found");
        }
        List<OutcomeDefinitionDetails> rl = new ArrayList<OutcomeDefinitionDetails>();
        Iterator i = ods.iterator();
        while(i.hasNext())
        {
            rl.add(new OutcomeDefinitionDetails((OutcomeDefinition)i.next()));
        }
        return rl;
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getAllOutcomeDefinitionsForGivenCourse(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getAllOutcomeDefinitionsForGivenCourse");
	headerDesc = handleNullValue(headerDesc);
	try
	{
	    List<OutcomeDefinitionDetails> odl = getAllOutcomeDefinitionsForGivenCourse(courseId);
	    List<String[]> rl = new ArrayList<String[]>();
	    if(headerDesc)
	    {
		rl.add(new OutcomeDefinitionDetails().toStringArrayHeader());
	    }
	    Iterator i = odl.iterator();
	    while(i.hasNext())
	    {
		rl.add(((OutcomeDefinitionDetails)i.next()).toStringArray());
	    }
	    return (String[][])rl.toArray(new String[rl.size()][]);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error while getting outcome definitions for course '"+courseId+"': "+e.toString());
	}
    }

    /**
     * Web service operation
     */
    @WebMethod
    public List<OutcomeDefinitionDetails> getAllOutcomeDefinitionsForGivenCourseWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getAllOutcomeDefinitionsForGivenCourseWithNamedElements");
	try
	{
	    return getAllOutcomeDefinitionsForGivenCourse(courseId);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error while getting outcome definitions for course '"+courseId+"': "+e.toString());
	}
    }

    private List<OutcomeDetails> getAllOutcomesForGivenOutcomeDefBbId(String outcomeDefBbId) throws Exception
    {
	List<OutcomeDetails> rl = new ArrayList<OutcomeDetails>();
	List<Outcome> ol = Arrays.asList(((OutcomeDefinitionDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(OutcomeDefinitionDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(OutcomeDefinition.DATA_TYPE,outcomeDefBbId)).getOutcomes());
	if(ol.isEmpty())
	{
	    throw new Exception("No outcomes found for outcomeDefBbId");
	}
	Iterator i = ol.iterator();
	while(i.hasNext())
	{
	    rl.add(new OutcomeDetails((Outcome)i.next()));
	}
	return rl;
    }

    @WebMethod
    public List<OutcomeDetails> getAllOutcomesForGivenOutcomeDefBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getAllOutcomesForGivenOutcomeDefBbIdWithNamedElements");
	try
	{
	    return getAllOutcomesForGivenOutcomeDefBbId(outcomeDefBbId);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error while getting outcomes: "+e.toString());
	}
    }
    
    private List<ScoreDetails> getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, Boolean extendedDetails) throws Exception
    {
        List<ScoreDetails> rl = new ArrayList<ScoreDetails>();
        try 
        {
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  BbConfig.LIBRARY_VERSION:" + blackboard.platform.config.BbConfig.LIBRARY_VERSION);
            BbWsLog.logForward(BbServiceManager.getConfigurationService().getBbProperty(blackboard.platform.config.BbConfig.LIBRARY_VERSION));
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  BbConfig.VERSION_NUMBER:" + blackboard.platform.config.BbConfig.VERSION_NUMBER);
            BbWsLog.logForward(BbServiceManager.getConfigurationService().getBbProperty(blackboard.platform.config.BbConfig.VERSION_NUMBER));
            
            
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Class gradebookManagerFactoryClass = Class.forName");
            Class gradebookManagerFactoryClass = Class.forName("blackboard.platform.gradebook2.GradebookManagerFactory");
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Object gradebookManager = gradebookManagerFactoryClass.getDeclaredMethod(");
            Object gradebookManager = gradebookManagerFactoryClass.getDeclaredMethod("getInstanceWithoutSecurityCheck").invoke(gradebookManagerFactoryClass.newInstance());
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Class gbmClass = gradebookManager.getClass();");
            Class gbmClass = gradebookManager.getClass();
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  blackboard.persist.Id courseId = ((LineitemDbLoader)");
            blackboard.persist.Id courseId = ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId)).getCourseId();
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Class gradableItemClass = Class.forName");
            Class gradableItemClass = Class.forName("blackboard.platform.gradebook2.GradableItem");
            //gradableItemClass.getDeclaredField("DATA_TYPE").
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  blackboard.persist.Id gradableItemId = BbServiceManager.getPersistenceService()");
            blackboard.persist.Id gradableItemId = BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId((blackboard.persist.DataType)gradableItemClass.getField("DATA_TYPE").get(null),lineItemBbId);
            
            //Object gradableItem = gbmClass.getDeclaredMethod("getGradebookItem", parameterTypes)    public abstract GradableItem getGradebookItem(Id id)
            //blackboard.persist.Id courseId = ((CourseDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(CourseDbLoader.TYPE)).loadByCourseId(courseId).getId();
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Class bdrClass = Class.forName");    
            Class bdrClass = Class.forName("blackboard.platform.gradebook2.BookDataRequest");
            //Object bdr = bdrClass.getConstructor(new Class[]{blackboard.persist.Id.class}).
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Object bdr = bdrClass.getConstructor(blackboard.persist.Id.class).newInstance(courseId);");
            Object bdr = bdrClass.getConstructor(blackboard.persist.Id.class).newInstance(courseId);
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Object bookData = gbmClass.getDeclaredMethod(");
            Object bookData = gbmClass.getDeclaredMethod("getBookData", bdrClass).invoke(gradebookManager, bdr);
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  bookData.getClass().getDeclaredMethod(");
            bookData.getClass().getDeclaredMethod("addParentReferences").invoke(bookData);
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  bookData.getClass().getDeclaredMethod(");
            bookData.getClass().getDeclaredMethod("runCumulativeGrading").invoke(bookData);

            //load the list of all student enrollments in the course
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  List<CourseMembership> cmlist = ");
            List<CourseMembership> cmlist = CourseMembershipDbLoader.Default.getInstance().loadByCourseIdAndRole(courseId, CourseMembership.Role.STUDENT);
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Iterator<CourseMembership> cm_iter = cmlist.iterator();");
            Iterator<CourseMembership> cm_iter = cmlist.iterator();
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  while (cm_iter.hasNext())");
            while (cm_iter.hasNext())
            {
                BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  CourseMembership cm = cm_iter.next();");
                CourseMembership cm = cm_iter.next();
                BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Object gradeWithAttemptScore = bookData.getClass().getDeclaredMethod(");
                Object gradeWithAttemptScore = bookData.getClass().getDeclaredMethod("get", blackboard.persist.Id.class, blackboard.persist.Id.class).invoke(bookData, cm.getId(), gradableItemId);
                BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  rl.add(new ScoreDetails(gradeWithAttemptScore, ");
                rl.add(new ScoreDetails(gradeWithAttemptScore, extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard, lineItemBbId));
                //gradeWithAttemptScore gwas = bookData.get(cm.getId(), gradableItemId);
            }
            //or you will get an out of memory error...
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  cmlist.clear();");
            cmlist.clear();
            cmlist = null;
        }
        catch (ClassNotFoundException cnfe) 
        {
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  Caught catch (ClassNotFoundException cnfe)");
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  rl.clear();");
            rl.clear();
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :  List<Score> scores = ((");
            List<Score> scores = ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId)).getScores();
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    if(scores.size()<1)");
            if(scores.size()<1)
            {
                BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    throw new WebServiceException(");
                throw new WebServiceException("No scores found");
            }
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    Iterator i = scores.iterator();");
            Iterator i = scores.iterator();
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    while(i.hasNext())");
            while(i.hasNext())
            {
                BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    rl.add(getScoreDetailsObjForGivenScoreObj((Score)i.next(),extendedDetails));");
                rl.add(getScoreDetailsObjForGivenScoreObj((Score)i.next(),extendedDetails));
            }
            BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    scores.clear();");
            scores.clear();
            scores = null;
        }
        BbWsLog.logForward("getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, :    return rl;");
        return rl;
    }

        /**
         * Web service operation
         */
        @WebMethod
        public List<ScoreDetails> getAllScoreDetailsForGivenLineItemBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name="extendedDetails") Boolean extendedDetails) throws WebServiceException
        {
        authoriseMethodUse(pwd,"getAllScoreDetailsForGivenLineItemBbIdWithNamedElements");
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            return getAllScoreObjsForGivenLineItemBbId(lineItemBbId, extendedDetails);
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve scores for this line item "+e.toString());
        }
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getAllScoresForGivenLineItemBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name="extendedDetails") Boolean extendedDetails, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getAllScoresForGivenLineItemBbId");
	headerDesc = handleNullValue(headerDesc);
	extendedDetails = handleNullValue(extendedDetails);
	try
	{
            BbWsLog.logForward("getAllScoresForGivenLineItemBbId(@WebParam(name = :    List<ScoreDetails> sdsl = getAllScoreObjsForGivenLineItemBbId(lineItemBbId, extendedDetails);");
	    List<ScoreDetails> sdsl = getAllScoreObjsForGivenLineItemBbId(lineItemBbId, extendedDetails);
	    List<String[]> rl = new ArrayList<String[]>();
            BbWsLog.logForward("getAllScoresForGivenLineItemBbId(@WebParam(name = :    if(headerDesc)");
	    if(headerDesc)
	    {
		rl.add(new ScoreDetails(extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard).toStringArrayHeader());
	    }
	    Iterator i = sdsl.iterator();
            BbWsLog.logForward("getAllScoresForGivenLineItemBbId(@WebParam(name = :    while(i.hasNext())");
	    while(i.hasNext())
	    {
                BbWsLog.logForward("getAllScoresForGivenLineItemBbId(@WebParam(name = :    rl.add(((ScoreDetails)i.next()).toStringArray());");
		rl.add(((ScoreDetails)i.next()).toStringArray());
	    }
	    return (String[][])rl.toArray(new String[rl.size()][]);
	}
	catch(Exception e)
	{
            BbWsLog.logForward(e, "getAllScoresForGivenLineItemBbId(@WebParam(name = :    throw new WebServiceException(...");
	    throw new WebServiceException("Error: Could not retrieve scores for this line item "+e.toString());
	}
    }

    private List<AttemptDetails> getAttemptDetailsObjsForGivenOutcomeDefBbId(String outcomeDefBbId, Boolean extendedDetails) throws Exception
    {
	List<Attempt> al = AttemptDbLoader.Default.getInstance().loadByOutcomeDefinitionId(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(OutcomeDefinition.DATA_TYPE,outcomeDefBbId));
	List<AttemptDetails> rl = new ArrayList<AttemptDetails>();
	Iterator i = al.iterator();
	while(i.hasNext())
	{
	    rl.add(new AttemptDetails((Attempt)i.next(),extendedDetails?AttemptDetails.Verbosity.extended:AttemptDetails.Verbosity.standard));
	}
	return rl;
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getAttemptsByGivenOutcomeDefBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getAttemptsByGivenOutcomeDefBbId");
	headerDesc = handleNullValue(headerDesc);
	extendedDetails = handleNullValue(extendedDetails);
	try
	{
	    List<AttemptDetails> adsl = getAttemptDetailsObjsForGivenOutcomeDefBbId(outcomeDefBbId, extendedDetails);
	    List<String[]> rl = new ArrayList<String[]>();
	    if(headerDesc)
	    {
		rl.add(new AttemptDetails(extendedDetails?AttemptDetails.Verbosity.extended:AttemptDetails.Verbosity.standard).toStringArrayHeader());
	    }
	    Iterator i = adsl.iterator();
	    while(i.hasNext())
	    {
		rl.add(((AttemptDetails)i.next()).toStringArray());
	    }
	    return (String[][])rl.toArray(new String[rl.size()][]);
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
    public List<AttemptDetails> getAttemptDetailsByGivenOutcomeDefBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getAttemptDetailsByGivenOutcomeDefBbIdWithNamedElements");
	extendedDetails = handleNullValue(extendedDetails);
	try
	{
	    return getAttemptDetailsObjsForGivenOutcomeDefBbId(outcomeDefBbId, extendedDetails);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: "+e.toString());
	}
    }

    private GradeBookSettingsDetails getGradeBookSettingDetailsObjForGivenCourseId(String courseId) throws Exception
    {
	return new GradeBookSettingsDetails(GradeBookSettingsDbLoader.Default.getInstance().loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId()));
    }
    
    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod 
    public String[][] getGradebookSettingsForGivenCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "headerDesc") Boolean headerDesc) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getGradebookSettingsForGivenCourseID");
	headerDesc = handleNullValue(headerDesc);
	try
	{
	    List<String[]> rl = new ArrayList<String[]>();
	    if(headerDesc)
	    {
		rl.add(new GradeBookSettingsDetails().toStringArrayHeader());
	    }
	    rl.add(getGradeBookSettingDetailsObjForGivenCourseId(courseId).toStringArray());
	    return (String[][])rl.toArray(new String[rl.size()][]);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: Could not retrieve gradebook settings for this course"+e.toString());
	}
    }

    /**
     * Web service operation
     */
    @WebMethod 
    public GradeBookSettingsDetails getGradebookSettingsForGivenCourseIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getGradebookSettingsForGivenCourseIDWithNamedElements");
	try
	{
	    return getGradeBookSettingDetailsObjForGivenCourseId(courseId);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: Could not retrieve gradebook settings for this course"+e.toString());
	}
    }

    private LineitemDetails getLineitemDetailsForGivenLineitemBbId(String lineitemBbId) throws Exception
    {
	return new LineitemDetails(((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE, lineitemBbId)));
    }

    @WebMethod
    public LineitemDetails getLineitemDetailsForGivenLineitemBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitemBbId") String lineitemBbId) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getLineitemDetailsForGivenLineitemBbIdWithNamedElements");
	try
	{
	    return getLineitemDetailsForGivenLineitemBbId(lineitemBbId);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: Could not retrieve lineitem details "+e.toString());
	}
    }
    
    private OutcomeDefinitionDetails getOutcomeDefinitionDetailsFromOutcomeDefinitionBbId(String outcomeDefBbId) throws Exception
    {
	return new OutcomeDefinitionDetails(OutcomeDefinitionDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(OutcomeDefinition.DATA_TYPE,outcomeDefBbId)));
    }

    @WebMethod
    public OutcomeDefinitionDetails getOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdWithNamedElements");
	try
	{
	    return getOutcomeDefinitionDetailsFromOutcomeDefinitionBbId(outcomeDefBbId);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: Could not retrieve outcomeDefinition details "+e.toString());
	}
    }
    
    private ScoreDetails getScoreDetailsForGivenScoreBbId(String scoreBbId, Boolean extendedDetails) throws Exception
    {
	return new ScoreDetails(((ScoreDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(ScoreDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Score.SCORE_DATA_TYPE,scoreBbId)),extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public ScoreDetails getScoreDetailsForGivenScoreBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "scoreBbId") String scoreBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getScoreForGivenCourseMembershipBbIdAndLineItemBbIdWithNamedElements");
	extendedDetails = handleNullValue(extendedDetails);
	try
	{
	    return getScoreDetailsForGivenScoreBbId(scoreBbId, extendedDetails);
	}
	catch(KeyNotFoundException e)
	{
	    throw new WebServiceException("Error: No score found, has user taken test and has it been marked? Is user on course? Does lineitem exist?");
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: While retrieving score... "+e.toString());
	}
    }

    private ScoreDetails getScoreDetailsObjForGivenScoreObj(Score s, Boolean extendedDetails) throws Exception
    {
	return new ScoreDetails(s,extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard);
    }

    private Score getScoreObjForGivenCourseMembershipBbIdAndLineItemBbId(String courseMembershipBbId, String lineItemBbId) throws Exception
    {
	    return ((ScoreDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(ScoreDbLoader.TYPE)).loadByCourseMembershipIdAndLineitemId
		(
		    CourseMembershipDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(CourseMembership.DATA_TYPE,courseMembershipBbId)).getId(),
		    ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId)).getId()
		);
    }

    @WebMethod
    public ScoreDetails getScoreDetailsForGivenUserIdAndLineItemBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getScoreDetailsForGivenUserIdAndLineItemBbIdWithNamedElements");
	extendedDetails = handleNullValue(extendedDetails);
	try
	{
	    return  getScoreDetailsObjForGivenScoreObj
		    (
			getScoreObjForGivenCourseMembershipBbIdAndLineItemBbId
			(
			    CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId
			    (
				    ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId)).getCourseId(),
				    UserDbLoader.Default.getInstance().loadByUserName(userId).getId()
			    ).getId().toExternalString(),
			    lineItemBbId
			),
			extendedDetails
		    );
	}
	catch(KeyNotFoundException e)
	{
	    throw new WebServiceException("Error: No score found, has user taken test and has it been marked? Is user on course? Does lineitem exist?");
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: While retrieving score... "+e.toString());
	}
    }

    /**
     * Web service operation
     */
    @WebMethod
    public ScoreDetails getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembershipBbId") String courseMembershipBbId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdWithNamedElements");
	extendedDetails = handleNullValue(extendedDetails);
	try
	{
	    return getScoreDetailsObjForGivenScoreObj(getScoreObjForGivenCourseMembershipBbIdAndLineItemBbId(courseMembershipBbId, lineItemBbId),extendedDetails);
	}
	catch(KeyNotFoundException e)
	{
	    throw new WebServiceException("Error: No score found, has user taken test and has it been marked? Is user on course? Does lineitem exist?");
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: While retrieving score... "+e.toString());
	}
    }

    /**
     * Web service operation
     */
    @Deprecated
    @WebMethod
    public String[][] getScoreForGivenCourseMembershipBbIdAndLineItemBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembershipBbId") String courseMembershipBbId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails, @WebParam(name="headerDesc") Boolean headerDesc) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getScoreForGivenCourseMembershipBbIdAndLineItemBbId");
	headerDesc = handleNullValue(headerDesc);
	extendedDetails = handleNullValue(extendedDetails);
	try
	{
	    List<String[]> rl = new ArrayList<String[]>();
	    if(headerDesc)
	    {
		rl.add(new ScoreDetails(extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard).toStringArrayHeader());
	    }
	    rl.add(getScoreDetailsObjForGivenScoreObj(getScoreObjForGivenCourseMembershipBbIdAndLineItemBbId(courseMembershipBbId, lineItemBbId),extendedDetails).toStringArray());
	    return (String[][])rl.toArray(new String[rl.size()][]);
	}
	catch(KeyNotFoundException e)
	{
	    throw new WebServiceException("Error: No score found, has user taken test and has it been marked? Is user on course? Does lineitem exist?");
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: While retrieving score... "+e.toString());
	}
    }

    @Deprecated
    @WebMethod
    public String[][] getScoreForGivenUserIdAndLineItemBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails, @WebParam(name="headerDesc") Boolean headerDesc) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getScoreForGivenUserIdAndLineItemBbId");
	headerDesc = handleNullValue(headerDesc);
	extendedDetails = handleNullValue(extendedDetails);
	try
	{
	    List<String[]> rl = new ArrayList<String[]>();
	    if(headerDesc)
	    {
		rl.add(new ScoreDetails(extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard).toStringArrayHeader());
	    }
	    rl.add(
		    getScoreDetailsObjForGivenScoreObj
		    (
			getScoreObjForGivenCourseMembershipBbIdAndLineItemBbId
			(
			    CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId
			    (
				    ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId)).getCourseId(),
				    UserDbLoader.Default.getInstance().loadByUserName(userId).getId()
			    ).getId().toExternalString(),
			    lineItemBbId
			),
			extendedDetails
		    ).toStringArray()
	    );
	    return (String[][])rl.toArray(new String[rl.size()][]);
	}
	catch(KeyNotFoundException e)
	{
	    throw new WebServiceException("Error: No score found, has user taken test and has it been marked? Is user on course? Does lineitem exist?");
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: While retrieving score... "+e.toString());
	}
    }

    private OutcomeDetails getOutcomeDetailsFromOutcomeBbId(String outcomeBbId) throws Exception
    {
	return new OutcomeDetails(OutcomeDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Outcome.DATA_TYPE, outcomeBbId)));
    }

    @WebMethod
    public OutcomeDetails getOutcomeDetailsFromOutcomeBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeBbId") String outcomeBbId) throws WebServiceException
    {
	authoriseMethodUse(pwd,"getOutcomeDetailsFromOutcomeBbIdWithNamedElements");
	try
	{
	    return getOutcomeDetailsFromOutcomeBbId(outcomeBbId);
	}
	catch(KeyNotFoundException e)
	{
	    throw new WebServiceException("Error: No outcome found. Does outcome exist?");
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: While retrieving outcome... "+e.toString());
	}
    }

    private Boolean handleNullValue(Boolean value)
    {
	if (value == null)
	{
	    return false;
	}
	return value;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean setLineItemWeightByLineItemBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "weight") String weight) throws WebServiceException
    {
	authoriseMethodUse(pwd,"setLineItemWeightByLineItemBbId");
	try
	{
	    Lineitem li = ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId));
	    li.setWeight(Float.parseFloat(weight));
	    ((LineitemDbPersister)BbServiceManager.getPersistenceService().getDbPersistenceManager().getPersister(LineitemDbPersister.TYPE)).persist(li);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: While setting or persisting weight for lineitem "+e.toString());
	}
	return true;
    }

    /**
     * Web service operation
     *
     * itemOrCategory = ITEM / CATEGORY
     *
     */
    @WebMethod
    public Boolean setWeightByItemOrCategoryForGradebookInGivenCourseId(@WebParam(name = "password") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "itemOrCategory") String itemOrCategory) throws WebServiceException
    {
	authoriseMethodUse(pwd,"setWeightByItemOrCategoryForGradebookInGivenCourseId");
	try
	{
	    if(itemOrCategory!=null && itemOrCategory.length()>0)
	    {
		itemOrCategory = itemOrCategory.toLowerCase().trim();
		String firstChar = itemOrCategory.substring(0,1).toUpperCase();
		itemOrCategory = firstChar+itemOrCategory.substring(1);
	    }
	    else
	    {
		throw new Exception("Invalid item or category");
	    }
	    GradeBookSettings gbs = GradeBookSettingsDbLoader.Default.getInstance().loadByCourseId(CourseDbLoader.Default.getInstance().loadByCourseId(courseId).getId());
	    gbs.setWeightType(GradeBookSettings.WeightType.fromFieldName(itemOrCategory));
	    GradeBookSettingsDbPersister.Default.getInstance().persist(gbs);
	}
	catch(Exception e)
	{
	    throw new WebServiceException("Error: While setting or persisting weightings type for gradebook "+e.toString());
	}
	return true;
    }
} 