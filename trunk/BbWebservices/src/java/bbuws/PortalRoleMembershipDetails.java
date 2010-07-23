/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbuws;

/**
 *
 * @author vic

Row Status values: ENABLED, SOFT_DELETE, DISABLED, DELETE_PENDING, COPY_PENDING, DEFAULT. 
DEFAULT = (DEFAULT)defineDefault(ENABLED); 

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
//MINIMAL:    
    private String userId;
    private String portalRoleId;
//STANDARD:    
    private String RowStatus;
//EXTENDED:    
    private String userBatchUid;    
//unavailable (problems with ADMIN API)
    private String DataSourceBatchUid;
    private String RecStatus;

    private String portalRoleBatchUid;

    public PortalRoleMembershipDetails() {


    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPortalRoleId() {
        return portalRoleId;
    }

    public void setPortalRoleId(String portalRoleId) {
        this.portalRoleId = portalRoleId;
    }

    public String getRowStatus() {
        return RowStatus;
    }

    public void setRowStatus(String RowStatus) {
        this.RowStatus = RowStatus;
    }

    public String getDataSourceBatchUid() {
        return DataSourceBatchUid;
    }

    public void setDataSourceBatchUid(String DataSourceBatchUid) {
        this.DataSourceBatchUid = DataSourceBatchUid;
    }

    public String getRecStatus() {
        return RecStatus;
    }

    public void setRecStatus(String RecStatus) {
        this.RecStatus = RecStatus;
    }

    public String getUserBatchUid() {
        return userBatchUid;
    }

    public void setUserBatchUid(String userBatchUid) {
        this.userBatchUid = userBatchUid;
    }

    public String getPortalRoleBatchUid() {
        return portalRoleBatchUid;
    }

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
