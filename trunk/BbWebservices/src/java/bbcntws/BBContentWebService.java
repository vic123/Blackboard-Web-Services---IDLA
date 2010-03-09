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
package bbcntws;

import blackboard.data.content.Content;
import blackboard.data.content.ContentFile;
//import blackboard.data.content.ContentStatus;
import blackboard.data.course.Course;
import blackboard.data.navigation.CourseToc;
//import blackboard.data.registry.SystemRegistryEntry;
import blackboard.persist.BbPersistenceManager;
import blackboard.persist.Id;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;
import blackboard.persist.content.ContentDbLoader;
import blackboard.persist.content.ContentDbPersister;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.navigation.CourseTocDbLoader;
//import blackboard.persist.registry.SystemRegistryEntryDbLoader;
import blackboard.platform.BbServiceManager;
import java.util.ArrayList;
import java.util.Calendar;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.WebParam;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
@WebService(name = "BBContentWebService", serviceName = "BBContentWebService", targetNamespace = "http://www.ncl.ac.uk/BBContentWebService")
public class BBContentWebService {

    private WebServiceProperties wsp = new WebServiceProperties("amnl", "BBContentWebService");

    /**
     * Web service operation
     *
     * Only deletes link to file, doesn't actually remove anything on the filesytem
     */
    @WebMethod
    public String deleteContentByContentBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "contentBbId") String contentBbId) {
        if (!wsp.isMethodAccessible("deleteContentByContentBbId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd))) {
            return "Error: Access denied to method";
        }

        BbPersistenceManager bbPm = new BbServiceManager().getPersistenceService().getDbPersistenceManager();
        Id cid = null;

        try {
            ContentDbLoader cl = (ContentDbLoader) bbPm.getLoader(ContentDbLoader.TYPE);
            Content c = cl.loadById(bbPm.generateId(Content.DATA_TYPE, contentBbId), null, true, true);
            cid = c.getId();
        } catch (Exception e) {
            return "Error: Invalid content contentBbId";
        }

        try {
            ContentDbPersister cp = (ContentDbPersister) bbPm.getPersister(ContentDbPersister.TYPE);
            cp.deleteById(cid);
        } catch (Exception e) {
            return "Error: Could not delete content item " + e.toString();
        }

        return "OK";
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String[][] getContentTocDetailsForGivenCourse(@WebParam(name = "pwd") String pwd, @WebParam(name = "courseId") String courseId, @WebParam(name = "headerDesc") Boolean headerDesc) {
        if (!wsp.isMethodAccessible("getContentTocDetailsForGivenCourse") || (wsp.usingPassword() && !wsp.passwordMatches(pwd))) {
            return new String[][]{{"Error"}, {"Access denied to method"}};
        }

        BbPersistenceManager bbPm = new BbServiceManager().getPersistenceService().getDbPersistenceManager();
        String values[][] = new String[][]{{"Error"}, {"No course toc's found in course"}};

        if (headerDesc == null) {
            headerDesc = false;
        }

        try {
            CourseDbLoader col = (CourseDbLoader) bbPm.getLoader(CourseDbLoader.TYPE);
            CourseTocDbLoader ctl = (CourseTocDbLoader) bbPm.getLoader(CourseTocDbLoader.TYPE);
            CourseToc ct = null;
            Course c = col.loadByCourseId(courseId, null, true);
            ArrayList tocs = ctl.loadByCourseId(c.getId());


            int j = 0;
            if (headerDesc) {
                values = new String[tocs.size() + 1][];
                values[0] = new String[]{"ContentBbId", "Data Type", "Internal Handle", "Available", "Entry Point", "Label", "Position", "Target Type", "Url"};
                j = 1;
            } else {
                values = new String[tocs.size()][];
            }

            for (int i = 0; i < tocs.size(); i++) {
                values[j] = getCourseTocDetails((CourseToc) tocs.get(i));
                j += 1;
            }
        } catch (PersistenceException pe) {
            values = new String[][]{{"Error"}, {"Valid course not found/specified"}};
        } catch (Exception e) {
            values = new String[][]{{"Error"}, {e.toString()}};
        }

        return values;
    }

    private String[] getCourseTocDetails(CourseToc ct) {
        String result[] = new String[9];
        result[0] = ct.getContentId().toExternalString();
        result[1] = ct.getDataType().toString();
        result[2] = ct.getInternalHandle();
        result[3] = ct.getIsEnabled() ? "Yes" : "No";
        result[4] = ct.getIsEntryPoint() ? "Yes" : "No";
        result[5] = ct.getLabel();
        result[6] = "" + ct.getPosition();
        result[7] = ct.getTargetType().toExternalString();
        result[8] = ct.getUrl();
        return result;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String[][] getChildContentFromParentContentBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "parentContentBbId") String parentContentBbId, @WebParam(name = "headerDesc") Boolean headerDesc) {
        if (!wsp.isMethodAccessible("getChildContentFromParentContentBbId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd))) {
            return new String[][]{{"Error"}, {"Access denied to method"}};
        }

        BbPersistenceManager bbPm = new BbServiceManager().getPersistenceService().getDbPersistenceManager();
        String values[][] = new String[][]{{"Error"}, {"No content found"}};

        if (headerDesc == null) {
            headerDesc = false;
        }

        try {
            ContentDbLoader cl = (ContentDbLoader) bbPm.getLoader(ContentDbLoader.TYPE);
            //ArrayList content = cl.loadListById(bbPm.generateId(Content.DATA_TYPE,contentID),null,true);
            ArrayList content = (ArrayList) cl.loadChildren(bbPm.generateId(Content.DATA_TYPE, parentContentBbId), true, null);

            int j = 0;
            if (headerDesc) {
                values = new String[content.size() + 1][];
                values[0] = new String[]{"ContentBbId", "ParentContentBbId", "Position", "Folder", "Described",
                            "Lesson", "Available", "Start Date", "Modified Date",
                            "End Date", "Content Handler", "Data Type", "Render Type",
                            "Offline Path", "Offline Name", "Url", "Title",
                            "Title Colour", "Persistent Title", "Body", "Num. Of Files"};
                j = 1;
            } else {
                values = new String[content.size()][];
            }

            for (int i = 0; i < content.size(); i++) {
                values[j] = getCourseContentDetails((Content) content.get(i));
                j += 1;
            }
        } catch (PersistenceException pe) {
            values = new String[][]{{"Error"}, {"Valid content item not found/specified"}};
        } catch (Exception e) {
            values = new String[][]{{"Error"}, {e.toString()}};
        }

        return values;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String[][] getContentDetailsFromContentBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "contentId") String contentBbId, @WebParam(name = "headerDesc") Boolean headerDesc) {
        if (!wsp.isMethodAccessible("getContentDetailsFromContentBbId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd))) {
            return new String[][]{{"Error"}, {"Access denied to method"}};
        }

        BbPersistenceManager bbPm = new BbServiceManager().getPersistenceService().getDbPersistenceManager();
        String values[][] = new String[][]{{"Error"}, {"No content found"}};

        if (headerDesc == null) {
            headerDesc = false;
        }

        try {
            ContentDbLoader cl = (ContentDbLoader) bbPm.getLoader(ContentDbLoader.TYPE);
            //ArrayList content = cl.loadListById(bbPm.generateId(Content.DATA_TYPE,contentID),null,true);
            Content c = cl.loadById(bbPm.generateId(Content.DATA_TYPE, contentBbId), null, false, true);
            int i = 0;

            if (headerDesc) {
                values = new String[2][];
                values[0] = new String[]{"Id", "Parent Id", "Position", "Folder", "Described",
                            "Lesson", "Available", "Start Date", "Modified Date",
                            "End Date", "Content Handler", "Data Type", "Render Type",
                            "Offline Path", "Offline Name", "Url", "Title",
                            "Title Colour", "Persistent Title", "Body", "Num. Of Files"};
                i = 1;
            } else {
                values = new String[1][];
            }

            values[i] = getCourseContentDetails(c);
        } catch (PersistenceException pe) {
            values = new String[][]{{"Error"}, {"Valid content item not found/specified"}};
        } catch (Exception e) {
            values = new String[][]{{"Error"}, {e.toString()}};
        }

        return values;
    }

    private String[] getCourseContentDetails(Content c) {
        String result[] = new String[21];
        Calendar cal = null;
        String date = null;

        //c.getRemovedFiles()

        result[0] = c.getId().getExternalString();
        result[1] = c.getParentId().toExternalString();
        result[2] = "" + c.getPosition();
        result[3] = c.getIsFolder() ? "Yes" : "No";
        result[4] = c.getIsDescribed() ? "Yes" : "No";
        result[5] = c.getIsLesson() ? "Yes" : "No";
        result[6] = c.getIsAvailable() ? "Yes" : "No";
        cal = c.getStartDate();
        date = "";
        if (cal != null) {
            date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        }
        result[7] = date;
        cal = c.getModifiedDate();
        date = "";
        if (cal != null) {
            date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        }
        result[8] = date;
        cal = c.getEndDate();
        date = "";
        if (cal != null) {
            date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        }
        result[9] = date;
        result[10] = c.getContentHandler();
        result[11] = c.getDataType().toString();
        result[12] = c.getRenderType().toFieldName();
        result[13] = c.getOfflinePath();
        result[14] = c.getOfflineName();
        result[15] = c.getUrl();
        result[16] = c.getTitle();
        result[17] = c.getTitleColor();
        result[18] = c.getPersistentTitle();
        result[19] = c.getBody().getText();
        ArrayList files = c.getContentFiles();
        int numberOfFiles = 0;
        if (files != null && files.size() > 0) {
            numberOfFiles = files.size();
        }
        result[20] = "" + numberOfFiles;

        return result;
    }

    /**
     * Web service operation
     */
    @WebMethod
    public String[][] getFileDetailsFromContentBbId(@WebParam(name = "pwd") String pwd, @WebParam(name = "contentBbId") String contentBbId, @WebParam(name = "headerDesc") Boolean headerDesc) {
        if (!wsp.isMethodAccessible("getFileDetailsFromContentBbId") || (wsp.usingPassword() && !wsp.passwordMatches(pwd))) {
            return new String[][]{{"Error"}, {"Access denied to method"}};
        }

        BbPersistenceManager bbPm = new BbServiceManager().getPersistenceService().getDbPersistenceManager();
        String values[][] = new String[][]{{"Error"}, {"No files associated with this contentBbID"}};
        //String contentItem[][] = getContentDetailsFromContentBbId(pwd,contentBbId,headerDesc);

        if (headerDesc == null) {
            headerDesc = false;
        }

        try {
            ContentDbLoader cl = (ContentDbLoader) bbPm.getLoader(ContentDbLoader.TYPE);
            Content c = cl.loadById(bbPm.generateId(Content.DATA_TYPE, contentBbId), null, false, true);
            ArrayList files = c.getContentFiles();

            if (files != null && files.size() > 0) {
                ContentFile file = null;

                int j = 0;
                if (headerDesc) {
                    values = new String[files.size() + 1][];
                    values[0] = new String[]{"Id", "Action", "Data Type", "Link Name",
                                "Modified Date", "Name", "Size", "Storage Type"};
                    j = 1;
                } else {
                    values = new String[files.size()][];
                }

                for (int i = 0; i < files.size(); i++) {
                    values[j] = getContentFileDetails((ContentFile) files.get(i));
                    j += 1;
                }
            }
        } catch (KeyNotFoundException knfe) {
            values = new String[][]{{"Error"}, {"No such contentBbId found"}};
        } catch (Exception e) {
            values = new String[][]{{"Error"}, {e.toString()}};
        }

        return values;
    }

    private String[] getContentFileDetails(ContentFile cf) {
        String result[] = new String[8];
        Calendar cal = null;
        String date = null;

        //cf.getChildFiles()

        result[0] = cf.getId().toExternalString();
        result[1] = cf.getAction().toFieldName();
        result[2] = cf.getDataType().toString();
        result[3] = cf.getLinkName();
        cal = cf.getModifiedDate();
        date = "";
        if (cal != null) {
            date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        }
        result[4] = date;
        result[5] = cf.getName();
        result[6] = "" + cf.getSize();
        result[7] = cf.getStorageType().toFieldName();

        return result;
    }
}
