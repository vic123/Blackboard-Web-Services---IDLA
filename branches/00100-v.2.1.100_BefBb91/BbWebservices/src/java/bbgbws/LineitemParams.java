/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbgbws;

/**
 *
 * @author vic
 */

public class LineitemParams extends CommonParams {
    
    private String courseId;
    private String lineitemBbId;
    //non-null values in requestedDetails indicate fields that should be returned
    //they also specify values that should be returned for unavailable (null) fields
//    private LineitemDetails requestedDetails;
    
    public LineitemParams () {
        super();
        this.courseId = null;
        this.lineitemBbId = null;
//        this.requestedDetails = null;
        if (libraryVersion.compareTo ("8.0") < 0)  apiToUse = "DATA_GB";        
    }
    public LineitemParams (LineitemParams params) {
        super();
        this.courseId = null;
        this.lineitemBbId = null;
//        this.requestedDetails = null;
        if (libraryVersion.compareTo ("8.0") < 0)  apiToUse = "DATA_GB";
    }
    
    public LineitemParams (String password, String courseId, String lineitemBbId) {
        this();
        this.password = password;
        this.courseId = courseId;
        this.lineitemBbId = lineitemBbId;
    }
    
    public LineitemParams (String password, String courseId, String lineitemBbId, CommonParams params) {
        super(params);
        //String lib_ver = BbServiceManager.getConfigurationService().getBbProperty(blackboard.platform.config.BbConfig.VERSION_NUMBER);
        if (params == null && libraryVersion.compareTo ("8.0") < 0)  apiToUse = "DATA_GB";
        this.password = password;
        this.courseId = courseId;
        this.lineitemBbId = lineitemBbId;
  /*      if (params instanceof LineitemParams) {
            this.requestedDetails = ((LineitemParams)params).requestedDetails;
        } else {
            this.requestedDetails = null;
        }*/
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getLineitemBbId() {
        return lineitemBbId;
    }

    public void setLineitemBbId(String lineitemBbId) {
        this.lineitemBbId = lineitemBbId;
    }

/*
    public LineitemDetails getRequestedDetails() {
        return requestedDetails;
    }

    public void setRequestedDetails(LineitemDetails requestedDetails) {
        this.requestedDetails = requestedDetails;
    }
  */  
}

