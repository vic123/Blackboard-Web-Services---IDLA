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
 */
public class ObserverAssociationDetails extends BbWsDataDetails<BbWsArguments> {
    private String bbId;
    private String observerBbId;
    private String usersBbId;
    private String dataSourceId;
    private String observerBatchUid;
    private String usersBatchUid;
    private String dataSourceBatchUid;

    @Override
    public String getBbId() {
        return this.bbId;
    }

    @Override
    public void setBbId(String id) {
        this.bbId = id;
    }

    /**
     * @return the observerId
     */
    public String getObserverBbId() {
        return observerBbId;
    }

    /**
     * @param observerId the observerId to set
     */
    public void setObserverBbId(String observerId) {
        this.observerBbId = observerId;
    }

    /**
     * @return the usersId
     */
    public String getUsersBbId() {
        return usersBbId;
    }

    /**
     * @param usersId the usersId to set
     */
    public void setUsersBbId(String usersId) {
        this.usersBbId = usersId;
    }

    /**
     * @return the dataSourceId
     */
    public String getDataSourceId() {
        return dataSourceId;
    }

    /**
     * @param dataSourceId the dataSourceId to set
     */
    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    /**
     * @return the observerBatchUid
     */
    public String getObserverBatchUid() {
        return observerBatchUid;
    }

    /**
     * @param observerBatchUid the observerBatchUid to set
     */
    public void setObserverBatchUid(String observerBatchUid) {
        this.observerBatchUid = observerBatchUid;
    }

    /**
     * @return the userBatchUid
     */
    public String getUsersBatchUid() {
        return usersBatchUid;
    }

    /**
     * @param userBatchUid the userBatchUid to set
     */
    public void setUsersBatchUid(String userBatchUid) {
        this.usersBatchUid = userBatchUid;
    }

    /**
     * @return the dataSourceBatchUid
     */
    public String getDataSourceBatchUid() {
        return dataSourceBatchUid;
    }

    /**
     * @param dataSourceBatchUid the dataSourceBatchUid to set
     */
    public void setDataSourceBatchUid(String dataSourceBatchUid) {
        this.dataSourceBatchUid = dataSourceBatchUid;
    }


}
