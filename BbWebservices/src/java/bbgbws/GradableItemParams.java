/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbgbws;

/**
 *
 * @author Administrator
 */
public class GradableItemParams {
    private String bbId;
    private String courseId;
    private String gradingPeriodId;
    private String bookVersion;
    private String assessmentId;
    private String contentId;

    /**
     * @return the courseId
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * @param courseId the courseId to set
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * @return the gradingPeriodId
     */
    public String getGradingPeriodId() {
        return gradingPeriodId;
    }

    /**
     * @param gradingPeriodId the gradingPeriodId to set
     */
    public void setGradingPeriodId(String gradingPeriodId) {
        this.gradingPeriodId = gradingPeriodId;
    }

    /**
     * @return the bookVersionNumber
     */
    public String getBookVersion() {
        return bookVersion;
    }

    /**
     * @param bookVersionNumber the bookVersionNumber to set
     */
    public void setBookVersion(String bookVersionNumber) {
        this.bookVersion = bookVersionNumber;
    }

    /**
     * @return the assessmentId
     */
    public String getAssessmentId() {
        return assessmentId;
    }

    /**
     * @param assessmentId the assessmentId to set
     */
    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    /**
     * @return the contentId
     */
    public String getContentId() {
        return contentId;
    }

    /**
     * @param contentId the contentId to set
     */
    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

}
