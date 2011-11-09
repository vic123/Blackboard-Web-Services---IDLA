/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbuws;

/**
 *
 * @author vic
 */
import bbwscommon.*;

import blackboard.persist.Id;
import blackboard.data.user.User;

import blackboard.persist.user.impl.BbObserverAssociation;


public class ObserverAssociationAccessPack<BbObserverAssociationType extends BbObserverAssociation>
        extends BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType<ObserverAssociationAccessPack.ObserverAssociationArguments_ADMIN_DATA,
            BbObserverAssociationType, ObserverAssociationDetails> {

    public static class ObserverAssociationArguments_ADMIN_DATA extends BbWsArguments<ObserverAssociationDetails, ObserverAssociationDetails> {
        protected void initializeDefaults() {
            super.initializeDefaults();
            getApiToUseList().clear();
            getApiToUseList().add(new ApiToUse(ApiToUseEnum.ADMIN_DATA, null));
        }
    }
    
//?? public void assignObserver(Id observerId, Id userId)
    
    @Override protected void setBbFields() throws Exception {
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getObserverId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getObserverBbId();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setObserverId(checkAndgenerateId(User.DATA_TYPE, newValue));
                }
            }.setBbField("observerId");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getUsersId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getUsersBbId();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setUsersId(checkAndgenerateId(User.DATA_TYPE, newValue));
                }
            }.setBbField("usersId");
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                        return bbObject.getDataSourceId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getDataSourceId();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                        bbObject.setDataSourceId(checkAndgenerateId(User.DATA_TYPE, newValue));
                }
            }.setBbField("dataSourceId");
    }

    @Override protected void setWsFields() throws Exception {
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getBbId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setBbId(newValue);
                }
            }.setWsField("bbId");	 	
        if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.MINIMAL) >= 0) {
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getObserverId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getObserverBbId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setObserverBbId(newValue);
                }
            }.setWsField("observerBbId");		
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getUsersId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getUsersBbId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setUsersBbId(newValue);
                }
            }.setWsField("usersBbId");		        }
        if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.EXTENDED) >= 0) {
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getDataSourceId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getDataSourceId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setDataSourceId(newValue);
                }
            }.setWsField("dataSourceId");		
        }
    }

}
