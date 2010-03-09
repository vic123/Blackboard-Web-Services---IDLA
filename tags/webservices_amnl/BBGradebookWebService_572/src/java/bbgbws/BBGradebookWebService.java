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

//java
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

//javax
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
@WebService()
public class BBGradebookWebService
{
    private WebServiceProperties wsp = new WebServiceProperties("amnl","BBGradebookWebService");

    private Boolean addLineitemToGivenCourseId(String courseId, String name,
                                                String type, Float pointsPossible, Float weight,
                                                Boolean available) throws WebServiceException
    {
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

    /**
     * Web service operation
     */
    @WebMethod
    public Boolean addLineitemToGivenCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "Name") String name,
						@WebParam(name = "type") String type, @WebParam(name = "pointsPossible") Float pointsPossible,
						@WebParam(name = "weight") Float weight, @WebParam(name = "available") Boolean available) throws WebServiceException
    {
        authoriseMethodUse(pwd,"addLineitemToGivenCourseId");
        return addLineitemToGivenCourseId(courseId, name, type, pointsPossible, weight, available);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String addLineitemToGivenCourseIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "Name") String name,
						@WebParam(name = "type") String type, @WebParam(name = "pointsPossible") Float pointsPossible,
						@WebParam(name = "weight") Float weight, @WebParam(name = "available") Boolean available) throws WebServiceException
    {
        authoriseMethodUse(pwd,"addLineitemToGivenCourseIdXML");
        return toXML(null,addLineitemToGivenCourseId(courseId, name, type, pointsPossible, weight, available));
    }

    private void authoriseMethodUse(String methodPwd, String methodName) throws WebServiceException
    {
        if(!wsp.isMethodAccessible(methodName) || (wsp.usingPassword() && !wsp.passwordMatches(methodPwd)))
        {
            throw new WebServiceException("Access Denied");
        }
    }

    private Boolean deleteGivenAttemptByAttemptBbId(String attemptBbId) throws WebServiceException
    {
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
    /**
     * Web service operation
     */
    @WebMethod
    public Boolean deleteGivenAttemptByAttemptBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "attemptBbId") String attemptBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteGivenAttemptByAttemptBbId");
        return deleteGivenAttemptByAttemptBbId(attemptBbId);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String deleteGivenAttemptByAttemptBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "attemptBbId") String attemptBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteGivenAttemptByAttemptBbIdXML");
        return toXML(null,deleteGivenAttemptByAttemptBbId(attemptBbId));
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
    public String deleteLineItemByLineItemBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitemBbId") String lineItemBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteLineItemByLineItemBbIdXML");
        return toXML(null,deleteOutcomeDefinitionByOutcomeDefBbId(lineItemBbId));
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

    /**
     * Web service operation
     */
    @WebMethod
    public String deleteOutcomeDefinitionByOutcomeDefBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"deleteOutcomeDefinitionByOutcomeDefBbIdXML");
        return toXML(null,deleteOutcomeDefinitionByOutcomeDefBbId(outcomeDefBbId));
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
                Class gradebookManagerFactoryClass = Class.forName("blackboard.platform.gradebook2.GradebookManagerFactory");
                Object gradebookManager = gradebookManagerFactoryClass.getDeclaredMethod("getInstanceWithoutSecurityCheck").invoke(gradebookManagerFactoryClass.newInstance());
                //blackboard.platform.gradebook2.GradebookManager gm = blackboard.platform.gradebook2.GradebookManagerFactory.getInstanceWithoutSecurityCheck();

                Class gbmClass = gradebookManager.getClass();
                List l = (List)gbmClass.getDeclaredMethod("getGradebookItems",new Class[]{blackboard.persist.Id.class}).invoke(gradebookManager,new Object[]{((CourseDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(CourseDbLoader.TYPE)).loadByCourseId(courseId).getId()});
                //List<blackboard.platform.gradebook2.GradableItem> l = gm.getGradebookItems(((CourseDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(CourseDbLoader.TYPE)).loadByCourseId(courseId).getId());

                i = l.iterator();
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
            List<LineitemDetails> lil = getAllLineitemObjsForCourseId(courseId);
            if(headerDesc)
            {
            rl.add(new LineitemDetails().toStringArrayHeader());
            }
            Iterator i = lil.iterator();
            while(i.hasNext())
            {
            rl.add(((LineitemDetails)i.next()).toStringArray());
            }
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(Exception e)
        {
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

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getAllLineItemsForCourseIdXML")
    public String getAllLineItemsForCourseIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId)
    {
        authoriseMethodUse(pwd,"getAllLineItemsForCourseIdXML");
        try
        {
            return toXML("LineItems",getAllLineitemObjsForCourseId(courseId));
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

    /**
     * Web service operation
     */
    @WebMethod
    public String getAllOutcomeDefinitionsForGivenCourseXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllOutcomeDefinitionsForGivenCourseXML");
        try
        {
            return toXML("OutcomeDefinitions",getAllOutcomeDefinitionsForGivenCourse(courseId));
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

    @WebMethod
    public String getAllOutcomesForGivenOutcomeDefBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllOutcomesForGivenOutcomeDefBbIdXML");
        try
        {
            return toXML("Outcomes",getAllOutcomesForGivenOutcomeDefBbId(outcomeDefBbId));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error while getting outcomes: "+e.toString());
        }
    }

    private List<ScoreDetails> getAllScoreObjsForGivenLineItemBbId(String lineItemBbId, Boolean extendedDetails) throws Exception
    {
        List<Score> scores = ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId)).getScores();
        if(scores.size()<1)
        {
            throw new Exception("No scores found");
        }
        List<ScoreDetails> rl = new ArrayList<ScoreDetails>();
        Iterator i = scores.iterator();
        while(i.hasNext())
        {
            rl.add(getScoreDetailsObjForGivenScoreObj((Score)i.next(),extendedDetails));
        }
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
    @WebMethod
    public String getAllScoreDetailsForGivenLineItemBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name="extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAllScoreDetailsForGivenLineItemBbIdXML");
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            return toXML("Scores",getAllScoreObjsForGivenLineItemBbId(lineItemBbId, extendedDetails));
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
            List<ScoreDetails> sdsl = getAllScoreObjsForGivenLineItemBbId(lineItemBbId, extendedDetails);
            List<String[]> rl = new ArrayList<String[]>();
            if(headerDesc)
            {
                rl.add(new ScoreDetails(extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard).toStringArrayHeader());
            }
            Iterator i = sdsl.iterator();
            while(i.hasNext())
            {
                rl.add(((ScoreDetails)i.next()).toStringArray());
            }
            return (String[][])rl.toArray(new String[rl.size()][]);
        }
        catch(Exception e)
        {
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

    /**
     * Web service operation
     */
    @WebMethod
    public String getAttemptDetailsByGivenOutcomeDefBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getAttemptDetailsByGivenOutcomeDefBbIdXML");
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            return toXML("Attempts",getAttemptDetailsObjsForGivenOutcomeDefBbId(outcomeDefBbId, extendedDetails));
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

    /**
     * Web service operation
     */
    @WebMethod
    public String getGradebookSettingsForGivenCourseIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getGradebookSettingsForGivenCourseIDXML");
        try
        {
            return toXML("GradeBookSetting",getGradeBookSettingDetailsObjForGivenCourseId(courseId));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve gradebook settings for this course"+e.toString());
        }
    }

    private LineitemDetails getLineitemDetailsForGivenLineitemBbId(String lineitemBbId) throws WebServiceException
    {
        try
        {
            return new LineitemDetails(((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE, lineitemBbId)));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve lineitem details "+e.toString());
        }
    }

    @WebMethod
    public LineitemDetails getLineitemDetailsForGivenLineitemBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitemBbId") String lineitemBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getLineitemDetailsForGivenLineitemBbIdWithNamedElements");
        return getLineitemDetailsForGivenLineitemBbId(lineitemBbId);
    }

    @WebMethod
    public String getLineitemDetailsForGivenLineitemBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineitemBbId") String lineitemBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getLineitemDetailsForGivenLineitemBbIdXML");
        return toXML("LineItems",getLineitemDetailsForGivenLineitemBbId(lineitemBbId));
    }

    private OutcomeDefinitionDetails getOutcomeDefinitionDetailsFromOutcomeDefinitionBbId(String outcomeDefBbId) throws WebServiceException
    {
        try
        {
            return new OutcomeDefinitionDetails(OutcomeDefinitionDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(OutcomeDefinition.DATA_TYPE,outcomeDefBbId)));
        }
        catch(Exception e)
        {
            throw new WebServiceException("Error: Could not retrieve outcomeDefinition details "+e.toString());
        }
    }

    @WebMethod
    public OutcomeDefinitionDetails getOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdWithNamedElements");
        return getOutcomeDefinitionDetailsFromOutcomeDefinitionBbId(outcomeDefBbId);
    }

    @WebMethod
    public String getOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeDefBbId") String outcomeDefBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getOutcomeDefinitionDetailsFromOutcomeDefinitionBbIdWithNamedElements");
        return toXML("OutcomeDefinitions",getOutcomeDefinitionDetailsFromOutcomeDefinitionBbId(outcomeDefBbId));
    }

    private ScoreDetails getScoreDetailsForGivenScoreBbId(String scoreBbId, Boolean extendedDetails) throws WebServiceException
    {
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            return new ScoreDetails(((ScoreDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager().getLoader(ScoreDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Score.SCORE_DATA_TYPE,scoreBbId)),extendedDetails?ScoreDetails.Verbosity.extended:ScoreDetails.Verbosity.standard);
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
    public ScoreDetails getScoreDetailsForGivenScoreBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "scoreBbId") String scoreBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreForGivenCourseMembershipBbIdAndLineItemBbIdWithNamedElements");
        return getScoreDetailsForGivenScoreBbId(scoreBbId, extendedDetails);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getScoreDetailsForGivenScoreBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "scoreBbId") String scoreBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreForGivenCourseMembershipBbIdAndLineItemBbIdXML");
        return toXML("ScoreDetails",getScoreDetailsForGivenScoreBbId(scoreBbId, extendedDetails));
    }

    private ScoreDetails getScoreDetailsForGivenUserIdAndLineItemBbId(String userId, String lineItemBbId, Boolean extendedDetails) throws WebServiceException
    {
        extendedDetails = handleNullValue(extendedDetails);
        try
        {
            return getScoreDetailsObjForGivenScoreObj
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

    @WebMethod
    public ScoreDetails getScoreDetailsForGivenUserIdAndLineItemBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreDetailsForGivenUserIdAndLineItemBbIdWithNamedElements");
        return getScoreDetailsForGivenUserIdAndLineItemBbId(userId, lineItemBbId, extendedDetails);
    }

    @WebMethod
    public String getScoreDetailsForGivenUserIdAndLineItemBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "userId") String userId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreDetailsForGivenUserIdAndLineItemBbIdXML");
        return toXML("Scores",getScoreDetailsForGivenUserIdAndLineItemBbId(userId, lineItemBbId, extendedDetails));
    }

    private ScoreDetails getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbId(String courseMembershipBbId, String lineItemBbId, Boolean extendedDetails) throws WebServiceException
    {
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
    @WebMethod
    public ScoreDetails getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembershipBbId") String courseMembershipBbId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdWithNamedElements");
        return getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbId(courseMembershipBbId, lineItemBbId, extendedDetails);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseMembershipBbId") String courseMembershipBbId, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "extendedDetails") Boolean extendedDetails) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbIdXML");
        return toXML("Scores",getScoreDetailsForGivenCourseMembershipBbIdAndLineItemBbId(courseMembershipBbId, lineItemBbId, extendedDetails));
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

    private OutcomeDetails getOutcomeDetailsFromOutcomeBbId(String outcomeBbId) throws WebServiceException
    {
        try
        {
            return new OutcomeDetails(OutcomeDbLoader.Default.getInstance().loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Outcome.DATA_TYPE, outcomeBbId)));
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

    @WebMethod
    public OutcomeDetails getOutcomeDetailsFromOutcomeBbIdWithNamedElements(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeBbId") String outcomeBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getOutcomeDetailsFromOutcomeBbIdWithNamedElements");
        return getOutcomeDetailsFromOutcomeBbId(outcomeBbId);
    }

    @WebMethod
    public String getOutcomeDetailsFromOutcomeBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "outcomeBbId") String outcomeBbId) throws WebServiceException
    {
        authoriseMethodUse(pwd,"getOutcomeDetailsFromOutcomeBbIdXML");
        return toXML("Outcomes",getOutcomeDetailsFromOutcomeBbId(outcomeBbId));
    }

    private Boolean handleNullValue(Boolean value)
    {
        if (value == null)
        {
            return false;
        }
        return value;
    }

    private Boolean setLineItemWeightByLineItemBbId(String lineItemBbId, String weight) throws WebServiceException
    {
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
     */
    @WebMethod
    public Boolean setLineItemWeightByLineItemBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "weight") String weight) throws WebServiceException
    {
        authoriseMethodUse(pwd,"setLineItemWeightByLineItemBbId");
        return setLineItemWeightByLineItemBbId(lineItemBbId, weight);
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String setLineItemWeightByLineItemBbIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "lineItemBbId") String lineItemBbId, @WebParam(name = "weight") String weight) throws WebServiceException
    {
        authoriseMethodUse(pwd,"setLineItemWeightByLineItemBbId");
        return toXML(null,setLineItemWeightByLineItemBbId(lineItemBbId, weight));
    }

    private Boolean setWeightByItemOrCategoryForGradebookInGivenCourseId(String courseId, String itemOrCategory) throws WebServiceException
    {
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

    /**
     * Web service operation
     *
     * itemOrCategory = ITEM / CATEGORY
     *
     */
    @WebMethod
    public Boolean setWeightByItemOrCategoryForGradebookInGivenCourseId(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "itemOrCategory") String itemOrCategory) throws WebServiceException
    {
        authoriseMethodUse(pwd,"setWeightByItemOrCategoryForGradebookInGivenCourseId");
        return setWeightByItemOrCategoryForGradebookInGivenCourseId(courseId, itemOrCategory);
    }

    /**
     * Web service operation
     *
     * itemOrCategory = ITEM / CATEGORY
     *
     */
    @WebMethod
    public String setWeightByItemOrCategoryForGradebookInGivenCourseIdXML(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "itemOrCategory") String itemOrCategory) throws WebServiceException
    {
        authoriseMethodUse(pwd,"setWeightByItemOrCategoryForGradebookInGivenCourseId");
        return toXML(null,setWeightByItemOrCategoryForGradebookInGivenCourseId(courseId, itemOrCategory));
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
