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
import blackboard.data.gradebook.Lineitem;
import blackboard.data.gradebook.Lineitem.AssessmentLocation;
import blackboard.platform.log.*;

//java
import java.util.Calendar;
import java.util.List;
import blackboard.platform.gradebook2.*;
import blackboard.platform.BbServiceManager;
import blackboard.persist.Id;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.gradebook.LineitemDbLoader;
import blackboard.persist.BbPersistenceManager;
/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class LineitemDetails extends DataDetails implements ReturnTypeInterface
{
    private String assessmentBbId;
    private String assessmentLocation;
    private String available;
    private String columnPosition;
    private String dateAdded;
    private String dateChanged;
    private String lineItemBbId;
    private String name;
    private String outcomeDefBbId;
    private String pointsPossible;
    private String type;
    private String weight;
    //??Additional fields available in gb2:
    //?? gi.getDisplayColumnName()
    //determine the calculation type - AVERAGE, MINMAX, NON_CALCULATED, TOTAL, WEIGHTED_TOTAL
    //out.println(gi.getCalculatedInd());
    //get the schema type - COMPLETE, PERCENT, SCORE, TABULAR, TEXT
    //out.println(GradingSchemaDAO.get().loadById(gi.getGradingSchemaId()).getScaleType().name());
    //determine if is is calculated or non calculated
    //out.println(gi.isCalculated());
    //determine if the instructor manually entered the grade
    //out.println(gi.isManual());

    public LineitemDetails(){
        super();
    }
    public String getId() {
        return lineItemBbId;
    }
    
    @Deprecated
    public LineitemDetails(Object gi)
    {
        Class gradableItem = gi.getClass();
        try{this.name = (String)gradableItem.getDeclaredMethod("getTitle").invoke(gi);}catch(Exception e){}
        try{
            Object o = gradableItem.getDeclaredMethod("getAssessmentId").invoke(gi);
            if(o!=null)
            {
                this.assessmentBbId = o.getClass().getName();
                if(this.assessmentBbId.equalsIgnoreCase("java.lang.String"))
                {
                    this.assessmentBbId = o.toString();
                }
                else if(this.assessmentBbId.equalsIgnoreCase("blackboard.persist.Id") || this.assessmentBbId.equalsIgnoreCase("blackboard.persist.PkId"))
                {
                    this.assessmentBbId = ((blackboard.persist.Id)o).getExternalString();
                }
            }
        }catch(Exception e){}
        //if(gi.getAssessmentLocation().equals(AssessmentLocation.EXTERNAL)){this.assessmentLocation = "EXTERNAL";}
        //else if(gi.getAssessmentLocation().equals(AssessmentLocation.INTERNAL)){this.assessmentLocation = "INTERNAL";}
        //else if(gi.getAssessmentLocation().equals(AssessmentLocation.UNSET)){this.assessmentLocation = "UNSET";}

        try{this.available = Boolean.toString((Boolean)gradableItem.getDeclaredMethod("getIsVisibleInAllTerms").invoke(gi));}catch(Exception e){}
        try{this.columnPosition = Integer.toString((Integer)gradableItem.getDeclaredMethod("getPosition").invoke(gi));}catch(Exception e){}
        try{this.dateAdded = extractDate((Calendar)gradableItem.getDeclaredMethod("getDateAdded").invoke(gi));}catch(Exception e){}
        try{this.dateChanged = extractDate((Calendar)gradableItem.getDeclaredMethod("getDateModified").invoke(gi));}catch(Exception e){}
        try{this.lineItemBbId = ((String)gradableItem.getDeclaredMethod("getExternalId").invoke(gi));}catch(Exception e){}
        try{this.name = (String)gradableItem.getDeclaredMethod("getTitle").invoke(gi);}catch(Exception e){}
        //this.outcomeDefBbId = gi.getOutcomeDefinition().getId().toExternalString();
        try{this.pointsPossible = Double.toString((Double)gradableItem.getDeclaredMethod("getPoints").invoke(gi));}catch(Exception e){}
        //this.type = gi.getType();
        try{this.weight = Double.toString((Double)gradableItem.getDeclaredMethod("getWeight").invoke(gi));}catch(Exception e){}
    }     
    
    @Deprecated
    public LineitemDetails(Lineitem li)
    {
        Object o = li.getAssessmentId();
        if(o!=null)
        {
            this.assessmentBbId = o.getClass().getName();
            if(this.assessmentBbId.equalsIgnoreCase("java.lang.String"))
            {
                this.assessmentBbId = o.toString();
            }
            else if(this.assessmentBbId.equalsIgnoreCase("blackboard.persist.Id"))
            {
                this.assessmentBbId = ((blackboard.persist.Id)o).getExternalString();
            }
        }
        if(li.getAssessmentLocation().equals(AssessmentLocation.EXTERNAL)){this.assessmentLocation = "EXTERNAL";}
        else if(li.getAssessmentLocation().equals(AssessmentLocation.INTERNAL)){this.assessmentLocation = "INTERNAL";}
        else if(li.getAssessmentLocation().equals(AssessmentLocation.UNSET)){this.assessmentLocation = "UNSET";}
        this.available = Boolean.toString(li.getIsAvailable());
        this.columnPosition = Integer.toString(li.getColumnOrder());
        this.dateAdded = extractDate(li.getDateAdded());
        this.dateChanged = extractDate(li.getDateChanged());
        this.lineItemBbId = li.getId().toExternalString();
        this.name = li.getName();
        this.outcomeDefBbId = li.getOutcomeDefinition().getId().toExternalString();
        this.pointsPossible = Float.toString(li.getPointsPossible());
        this.type = li.getType();
        this.weight = Float.toString(li.getWeight());
    } 

    static public class RecordListLoader_DATA_GB 
            extends RecordListLoader<Lineitem, LineitemParams, LineitemDetails, RecordLoader_DATA_GB> {
        RecordListLoader_DATA_GB (LineitemParams params, String apiUsed, 
                java.util.LinkedHashMap<String, LineitemDetails> resultLHash) {
            super(params, apiUsed, resultLHash);
        }
        public static void loadByCourseIdStarter(LineitemParams params, 
                java.util.LinkedHashMap<String, LineitemDetails> resultLHash) throws Exception {
            RecordListLoader_DATA_GB loader = new RecordListLoader_DATA_GB(params, "DATA_GB", resultLHash);
            loader.load();
        }
        
        protected RecordLoader_DATA_GB createRecordLoader() {
            return new RecordLoader_DATA_GB(params, apiUsed);
        }
        
        public List<Lineitem> loadList () throws Exception {
                BbPersistenceManager pm 
                        = blackboard.platform.BbServiceManager.getPersistenceService().getDbPersistenceManager();
                //Id courseId = pm.generateId(blackboard.data.course.Course.DATA_TYPE, params.getCourseId());
                Id courseId = CourseDbLoader.Default.getInstance().loadByCourseId(params.getCourseId()).getId();
                LineitemDbLoader lidbl = (LineitemDbLoader)pm.getLoader(blackboard.persist.gradebook.LineitemDbLoader.TYPE);
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "List<Lineitem> lil = lidbl.loadByCourseId(courseId); courseId: " + courseId.toExternalString() + "params.getCourseId(): " + params.getCourseId(), this);
                List<Lineitem> lil = lidbl.loadByCourseId(courseId);
                return lil; 
        }
    }
    
    static public class RecordLoader_DATA_GB 
            extends RecordLoader<Lineitem, 
                LineitemParams,
                LineitemDetails> {

        RecordLoader_DATA_GB (LineitemParams params, String apiUsed) {
            super(params, apiUsed);
        }
        
        protected void createWsObject() {
            record = new LineitemDetails();
        }
       
        protected Lineitem loadObj() throws Exception {
            BbPersistenceManager pm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
            LineitemDbLoader li_db_loader = (LineitemDbLoader)pm.getLoader(LineitemDbLoader.TYPE);
            Id li_id = pm.generateId(Lineitem.LINEITEM_DATA_TYPE, params.getLineitemBbId());
            Lineitem li = li_db_loader.loadById(li_id);
            return li;
        }
        
        public static void loadByGivenLineitemBbIdStarter(LineitemParams params, LineitemDetails resultObj) throws Exception {
            RecordLoader_DATA_GB loader = new RecordLoader_DATA_GB(params, "DATA_GB");
            loader.record = resultObj;
            loader.bbObject = loader.loadObj();
            loader.load();
        }
        
                
        public void loadImp() {

            record.assessmentBbId = loadValue(record.assessmentBbId, "assessmentBbId",
                new ValueLoader<Lineitem>() {
                    public String loadImp() {
                        Object obj = bbObject.getAssessmentId();
                        if (obj instanceof Id) return ((Id)obj).toExternalString();
                        else return (String)obj;
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!

            record.assessmentLocation = loadValue(record.assessmentLocation, "assessmentLocation",
                new ValueLoader<Lineitem>() {
                    public String loadImp() {
                        AssessmentLocation al = bbObject.getAssessmentLocation();
                        if (al.equals(AssessmentLocation.EXTERNAL)) return "EXTERNAL";
                        if (al.equals(AssessmentLocation.INTERNAL)) return "INTERNAL";
                        if (al.equals(AssessmentLocation.UNSET)) return "UNSET";
                        return null;
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!

            record.available = loadValue(record.available, "available",
                new ValueLoader<Lineitem>() {
                    public String loadImp() {
                        return Boolean.toString(bbObject.getIsAvailable());
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!

            record.columnPosition = loadValue(record.columnPosition, "columnPosition",
                new ValueLoader<Lineitem>() {
                    public String loadImp() {
                        return Integer.toString(bbObject.getColumnOrder());
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!
        
            record.dateAdded = loadValue(record.dateAdded, "dateAdded",
                new ValueLoader<Lineitem>() {
                    public String loadImp() {
                        return BbWsUtil.extractDate(bbObject.getDateAdded());
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!

            record.dateAdded = loadValue(record.dateChanged, "dateChanged",
                new ValueLoader<Lineitem>() {
                    public String loadImp() {
                        return BbWsUtil.extractDate(bbObject.getDateChanged());
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!

            record.lineItemBbId = loadValue(record.lineItemBbId, "lineItemBbId",
                new ValueLoader<Lineitem>() {
                    public String loadImp() {
                        return bbObject.getId().toExternalString();
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!
            
            record.name = loadValue(record.name, "name",
                new ValueLoader<Lineitem>() {
                    public String loadImp() {
                        return bbObject.getName();
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!

            record.outcomeDefBbId = loadValue(record.outcomeDefBbId, "outcomeDefBbId",
                new ValueLoader<Lineitem>() {
                    public String loadImp() {
                        return bbObject.getOutcomeDefinition().getId().toExternalString();
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!

            record.pointsPossible = loadValue(record.pointsPossible, "pointsPossible",
                new ValueLoader<Lineitem>() {
                    public String loadImp() {
                        return Float.toString(bbObject.getPointsPossible());
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!

            record.type = loadValue(record.type, "type",
                new ValueLoader<Lineitem>() {
                    public String loadImp() {
                        return bbObject.getType();
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!

            record.weight = loadValue(record.weight, "weight",
                new ValueLoader<Lineitem>() {
                    public String loadImp() {
                        return Float.toString(bbObject.getWeight());
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!
            record.incApiPassedCount(); //!! - move to base
        }    
    }
    
    
    static public class RecordListLoader_PLATFORM_GB2 
            extends RecordListLoader<GradableItem, LineitemParams, LineitemDetails, RecordLoader_PLATFORM_GB2> {
        RecordListLoader_PLATFORM_GB2 (LineitemParams params, String apiUsed,
                java.util.LinkedHashMap<String, LineitemDetails> resultLHash) {
            super(params, apiUsed, resultLHash);
        }
        public static void loadByCourseIdStarter(LineitemParams params, java.util.LinkedHashMap<String, 
                LineitemDetails> resultLHash) throws Exception {
            RecordListLoader_PLATFORM_GB2 loader = new RecordListLoader_PLATFORM_GB2(params, "PLATFORM_GB2", resultLHash);
            loader.load();
        }
        
        protected RecordLoader_PLATFORM_GB2 createRecordLoader() {
            return new RecordLoader_PLATFORM_GB2(params, apiUsed);
        }
        
        public List<GradableItem> loadList () throws Exception {
            BbWsLog.logForward(LogService.Verbosity.DEBUG, "GradebookManager gradebookManager = GradebookManagerFactory.getInstanceWithoutSecurityCheck();", this);            
            GradebookManager gradebookManager = GradebookManagerFactory.getInstanceWithoutSecurityCheck();
            //	bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
            //blackboard.persist.Id courseId = ((LineitemDbLoader)BbServiceManager.getPersistenceService().getDbPersistenceManager()
                //.getLoader(LineitemDbLoader.TYPE)).loadById(BbServiceManager.getPersistenceService().getDbPersistenceManager().generateId(Lineitem.LINEITEM_DATA_TYPE,lineItemBbId)).getCourseId();                
            BbWsLog.logForward(LogService.Verbosity.DEBUG, "Id courseId = CourseDbLoader.Default.getInstance().loadByCourseId(params.getCourseId()).getId();", this);            
            Id courseId = CourseDbLoader.Default.getInstance().loadByCourseId(params.getCourseId()).getId();
            BbWsLog.logForward(LogService.Verbosity.DEBUG, "List<GradableItem> l = (List)gradebookManager.getGradebookItems(courseId);", this);
            List<GradableItem> l = (List)gradebookManager.getGradebookItems(courseId);
            return l;
        }
    }
    static public class RecordLoader_PLATFORM_GB2 
            extends RecordLoader<GradableItem, LineitemParams, LineitemDetails>  {
//            extends RecordListLoader<GradableItem, LineitemParams, LineitemDetails, RecordLoader_DATA_GB>  {            

        RecordLoader_PLATFORM_GB2 (LineitemParams params, String apiUsed) {
            super(params, apiUsed);
            
        }
        
        public GradableItem loadObj () throws Exception {
            BbWsLog.logForward(LogService.Verbosity.DEBUG, "GradebookManager gradebookManager = GradebookManagerFactory.getInstanceWithoutSecurityCheck();", this);
            GradebookManager gradebookManager = GradebookManagerFactory.getInstanceWithoutSecurityCheck();
            BbWsLog.logForward(LogService.Verbosity.DEBUG, "blackboard.persist.BbPersistenceManager bbPm ...", this);            
            blackboard.persist.BbPersistenceManager bbPm 
                    = blackboard.platform.persistence.PersistenceServiceFactory.getInstance().getDbPersistenceManager();
            BbWsLog.logForward(LogService.Verbosity.DEBUG, "blackboard.persist.Id courseId = bbPm.generateId(blackboard.data.course.Course.DATA_TYPE, params.courseId);", this);
            blackboard.persist.Id liId = bbPm.generateId(GradableItem.DATA_TYPE, params.getLineitemBbId());
            BbWsLog.logForward(LogService.Verbosity.DEBUG, "GradableItem l = gradebookManager.getGradebookItem(liId);", this);
            GradableItem l = gradebookManager.getGradebookItem(liId);
            return l;
        }

        public static void loadByGivenLineitemBbIdStarter(LineitemParams params, LineitemDetails resultObj) throws Exception {
            BbWsLog.logForward(LogService.Verbosity.DEBUG, "RecordLoader_DATA_GB loader = new RecordLoader_DATA_GB(params, ...");
            RecordLoader_DATA_GB loader = new RecordLoader_DATA_GB(params, "PLATFORM_GB2");
            loader.record = resultObj;
            BbWsLog.logForward(LogService.Verbosity.DEBUG, "loader.bbObject = loader.loadObj()");
            loader.bbObject = loader.loadObj();
            loader.load();
        }
        
        protected void createWsObject() {
            record = new LineitemDetails();
        }
        
        public void loadImp() {
            
            record.assessmentBbId = loadValue(record.assessmentBbId, "assessmentBbId",
                new ValueLoader<GradableItem>() {
                    public String loadImp() {
                        return (bbObject.getAssessmentId().toExternalString());
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!
            
            record.name = loadValue(record.name, "name",
                new ValueLoader<GradableItem>() {
                    public String loadImp() {
                        return (bbObject.getTitle());
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!
            
            record.available = loadValue(record.available, "available",
                new ValueLoader<GradableItem>() {
                    public String loadImp() {
                        return Boolean.toString(bbObject.isVisibleInAllTerms());
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!

            record.columnPosition = loadValue(record.columnPosition, "columnPosition",
                new ValueLoader<GradableItem>() {
                    public String loadImp() {
                        return Integer.toString(bbObject.getPosition());
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!

            record.dateAdded = loadValue(record.dateAdded, "dateAdded",
                new ValueLoader<GradableItem>() {
                    public String loadImp() {
                        return BbWsUtil.extractDate(bbObject.getDateAdded());
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!

            record.dateChanged = loadValue(record.dateChanged, "dateChanged",
                new ValueLoader<GradableItem>() {
                    public String loadImp() {
                        return BbWsUtil.extractDate(bbObject.getDateModified());
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!

            record.lineItemBbId = loadValue(record.lineItemBbId, "lineItemBbId",
                new ValueLoader<GradableItem>() {
                    public String loadImp() {
                        return (bbObject.getExternalId());
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!

            record.pointsPossible = loadValue(record.pointsPossible, "pointsPossible",
                new ValueLoader<GradableItem>() {
                    public String loadImp() {
                        return Double.toString(bbObject.getPoints());
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!

            record.weight = loadValue(record.weight, "weight",
                new ValueLoader<GradableItem>() {
                    public String loadImp() {
                        return Double.toString(bbObject.getWeight());
                    }
            }); // Don't forget the parenthesis and semicolon that end the method call!
            record.incApiPassedCount(); //!! - move to base
        }    
    }
            
            
            
/*
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
  */  
    
    public String getAssessmentBbId()
    {
	return this.assessmentBbId;
    }

    public void setAssessmentBbId(String assessmentBbId)
    {
	this.assessmentBbId = assessmentBbId;
    }

    public String getAssessmentLocation()
    {
	return this.assessmentLocation;
    }

    public void setAssessmentLocation(String assessmentLocation)
    {
	this.assessmentLocation = assessmentLocation;
    }

    public String getAvailable()
    {
	return this.available;
    }

    public void setAvailable(String available)
    {
	this.available = available;
    }

    public String getColumnPosition()
    {
	return this.columnPosition;
    }

    public void setColumnPosition(String columnPosition)
    {
	this.columnPosition = columnPosition;
    }

    public String getDateAdded()
    {
	return this.dateAdded;
    }

    public void setDateAdded(String dateAdded)
    {
	this.dateAdded = dateAdded;
    }

    public String getDateChanged()
    {
	return this.dateChanged;
    }

    public void setDateChanged(String dateChanged)
    {
	this.dateChanged = dateChanged;
    }

    public String getLineItemBbId()
    {
	return this.lineItemBbId;
    }

    public void setLineItemBbId(String lineItemBbId)
    {
	this.lineItemBbId = lineItemBbId;
    }

    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public String getOutcomeDefBbId()
    {
	return this.outcomeDefBbId;
    }

    public void setOutcomeDefBbId(String outcomeDefBbId)
    {
	this.outcomeDefBbId = outcomeDefBbId;
    }

    public String getPointsPossible()
    {
        return this.pointsPossible;
    }

    public void setPointsPossible(String pointsPossible)
    {
        this.pointsPossible = pointsPossible;
    }

    public String getType()
    {
	return this.type;
    }

    public void setType(String type)
    {
	this.type = type;
    }

    public String getWeight()
    {
        return this.weight;
    }

    public void setWeight(String weight)
    {
        this.weight = weight;
    }

    public String[] toStringArray()
    {
        BbWsLog.logForward("getLineitemDetails():    String[] tst_str = new String[] {...");
        String[] tst_str = new String[] {"sdfsf", null};
        BbWsLog.logForward("getLineitemDetails():    return new String[]");
        BbWsLog.logForward("getLineitemDetails():    this.columnPosition:" + this.columnPosition);
        BbWsLog.logForward("getLineitemDetails():    this.available:" + this.available);
        return new String[]
        {
            this.lineItemBbId,
            this.name,
            this.dateAdded,
            this.dateChanged,
            //!!Integer.toString(this.columnPosition),
            String.valueOf(this.columnPosition), 
            //!!Boolean.toString(this.available),
            String.valueOf(this.available),
            this.pointsPossible,
            this.type,
            this.weight,
            this.assessmentBbId,
                            this.assessmentLocation};
    }

    public String[] toStringArrayHeader()
    {
	return new String[]{"LineItemBbId","Name","Date Added",
		"Date Changed","Column position",
		"Available","Points Possible","Type",
		"Weight","to do:AssessmentBbId",
		"Assessment Location"};
    }
    protected String extractDate(Calendar cal)
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
}
