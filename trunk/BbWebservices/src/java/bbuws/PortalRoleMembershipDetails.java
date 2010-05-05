/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbuws;

/**
 *
 * @author vic
 */
import bbwscommon.BbWsArguments.DataVerbosity;
import blackboard.data.role.PortalRole;
import blackboard.data.user.User;
import blackboard.persist.BbPersistenceManager;
import blackboard.persist.Id;
import blackboard.persist.role.PortalRoleDbLoader;
import blackboard.platform.BbServiceManager;
import java.util.Calendar;


public class PortalRoleMembershipDetails extends bbwscommon.BbWsDataDetails<bbwscommon.BbWsArguments> { //!! implements ReturnTypeInterface {

    private String bbId;
    private String userId;
    private String portalRoleId;
    private String RowStatus;
//_bbAttributes.setBbEnum("RowStatus", blackboard.admin.data.IAdminObject.RowStatus.DEFAULT);
    private String DataSourceBatchUid;
    private String RecStatus;
    private String userBatchUid;
    private String portalRoleBatchUid;

    public PortalRoleMembershipDetails() {


    }
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the portalRoleId
     */
    public String getPortalRoleId() {
        return portalRoleId;
    }

    /**
     * @param portalRoleId the portalRoleId to set
     */
    public void setPortalRoleId(String portalRoleId) {
        this.portalRoleId = portalRoleId;
    }

    /**
     * @return the RowStatus
     */
    public String getRowStatus() {
        return RowStatus;
    }

    /**
     * @param RowStatus the RowStatus to set
     */
    public void setRowStatus(String RowStatus) {
        this.RowStatus = RowStatus;
    }

    /**
     * @return the DataSourceBatchUid
     */
    public String getDataSourceBatchUid() {
        return DataSourceBatchUid;
    }

    /**
     * @param DataSourceBatchUid the DataSourceBatchUid to set
     */
    public void setDataSourceBatchUid(String DataSourceBatchUid) {
        this.DataSourceBatchUid = DataSourceBatchUid;
    }

    /**
     * @return the RecStatus
     */
    public String getRecStatus() {
        return RecStatus;
    }

    /**
     * @param RecStatus the RecStatus to set
     */
    public void setRecStatus(String RecStatus) {
        this.RecStatus = RecStatus;
    }

    /**
     * @return the userBatchUid
     */
    public String getUserBatchUid() {
        return userBatchUid;
    }

    /**
     * @param userBatchUid the userBatchUid to set
     */
    public void setUserBatchUid(String userBatchUid) {
        this.userBatchUid = userBatchUid;
    }

    /**
     * @return the portalRoleBatchUid
     */
    public String getPortalRoleBatchUid() {
        return portalRoleBatchUid;
    }

    /**
     * @param portalRoleBatchUid the portalRoleBatchUid to set
     */
    public void setPortalRoleBatchUid(String portalRoleBatchUid) {
        this.portalRoleBatchUid = portalRoleBatchUid;
    }

    @Override
    public String getBbId() {
        return bbId;
    }

    @Override
    public void setBbId(String newId) {
        bbId = newId;
    }

}
