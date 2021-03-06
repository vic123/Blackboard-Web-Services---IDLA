/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbuws;

import bbwscommon.BbWsArguments;
import bbwscommon.BbWsDataDetails;

/**
 *
 * @author vic
 * 
 * 
 * (a) Observer system role is incompatible with any other secondary system roles (they should not exist according to checks performed by administrative GUI), 
 * (b) Portal role does not seem to influence permissions of observer
 * (c) that an observer without assigned "Observer" (Parent in GUI) role cannot see courses of a user he is assosiated to. 
 * 
 * RowStatus does not influence visibility/functionality of observer.
 */
public class ObserverAssociationDetails extends BbWsDataDetails<BbWsArguments> {
    private String bbId;
    private String observerBbId;
    private String usersBbId;
    private String dataSourceId;
    private String observerBatchUid;
    private String usersBatchUid;
    private String dataSourceBatchUid;
    private String RowStatus;

    @Override
    public String getBbId() {
        return this.bbId;
    }

    @Override
    public void setBbId(String id) {
        this.bbId = id;
    }

    public String getObserverBbId() {
        return observerBbId;
    }

    public void setObserverBbId(String observerId) {
        this.observerBbId = observerId;
    }

    public String getUsersBbId() {
        return usersBbId;
    }

    public void setUsersBbId(String usersId) {
        this.usersBbId = usersId;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getObserverBatchUid() {
        return observerBatchUid;
    }

    public void setObserverBatchUid(String observerBatchUid) {
        this.observerBatchUid = observerBatchUid;
    }

    public String getUsersBatchUid() {
        return usersBatchUid;
    }

    public void setUsersBatchUid(String userBatchUid) {
        this.usersBatchUid = userBatchUid;
    }

    public String getDataSourceBatchUid() {
        return dataSourceBatchUid;
    }

    public void setDataSourceBatchUid(String dataSourceBatchUid) {
        this.dataSourceBatchUid = dataSourceBatchUid;
    }
    public String getRowStatus() {
        return RowStatus;
    }

    public void setRowStatus(String RowStatus) {
        this.RowStatus = RowStatus;
    }
}
