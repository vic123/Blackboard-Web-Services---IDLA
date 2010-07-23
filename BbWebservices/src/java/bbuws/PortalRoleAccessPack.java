/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbuws;

/**
 *
 * @author vic
 */
import blackboard.persist.Id;
import blackboard.data.role.PortalRole;

import bbwscommon.*;

public class PortalRoleAccessPack <BbPortalRoleType extends PortalRole,
            ArgumentsType extends BbWsArguments<PortalRoleDetails, PortalRoleDetails>>
//            WsObjectType extends  CourseMembershipDetails>
        extends BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType
            <ArgumentsType, BbPortalRoleType, PortalRoleDetails> {

    //static class CourseMembershipArguments extends BbWsArguments<WsObjectType, WsObjectType> {
    public static class PortalRoleArguments extends BbWsArguments<PortalRoleDetails, PortalRoleDetails> {
    }
    public static class PortalRoleArgumentsWithUserInput extends PortalRoleArguments {
        private bbuws.UserDetails  inputUserRecord;
        public bbuws.UserDetails  getInputUserRecord() {
            return inputUserRecord;
        }
        public void setInputUserRecord(bbuws.UserDetails inputUserRecord) {
            this.inputUserRecord = inputUserRecord;
        }
    }


    @Override protected void setSafeResultRecordIds() {
        super.setSafeResultRecordIds();
        getArgs().getResultRecord().setRoleName(getArgs().getInputRecord().getRoleName());
    }

    @Override protected void setBbFields() throws Exception {

        new BbFieldSetter() {
            @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getId().toExternalString();
            }
            @Override public String getWsFieldValue() throws Exception {
                        return getArgs().getResultRecord().getBbId();
            }
            @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setId(checkAndgenerateId(PortalRole.DATA_TYPE, newValue));
            }
        }.setBbField("bbId");
        new BbFieldSetter() {
            @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getRoleID();
            }
            @Override public String getWsFieldValue() throws Exception {
                        return getArgs().getResultRecord().getRoleId();
            }
            @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setRoleID(newValue);
            }
        }.setBbField("roleId");

        new BbFieldSetter() {
            @Override public String getBbFieldValue() throws Exception {
                return bbObject.getRoleName();
            }
            @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getRoleName();
            }
            @Override public void setBbFieldImp(String newValue) throws Exception {
                bbObject.setRoleName(newValue);
            }
        }.setBbField("roleName");


        new BbFieldSetter() {
            @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.isGuest());
            }
            @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getIsGuest();
            }
            @Override public void setBbFieldImp(String newValue) throws Exception {
                bbObject.setIsGuest(Boolean.parseBoolean(newValue));
            }
        }.setBbField("isGuest");
        new BbFieldSetter() {
            @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.isRemovable());
            }
            @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getIsRemovable();
            }
            @Override public void setBbFieldImp(String newValue) throws Exception {
                bbObject.setIsRemovable(Boolean.parseBoolean(newValue));
            }
        }.setBbField("isRemovable");
        new BbFieldSetter() {
            @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.isSelfSelectable());
            }
            @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getIsSelfSelectable();
            }
            @Override public void setBbFieldImp(String newValue) throws Exception {
                bbObject.setIsSelfSelectable(Boolean.parseBoolean(newValue));
            }
        }.setBbField("isSelfSelectable");


        new BbFieldSetter() {
            @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getDataSourceId().toExternalString();
            }
            @Override public String getWsFieldValue() throws Exception {
                        return getArgs().getResultRecord().getDataSourceBbId();
            }
            @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setDataSourceId(checkAndgenerateId(PortalRole.DATA_TYPE, newValue));
            }
        }.setBbField("dataSourceId");
        new BbFieldSetter() {
            @Override public String getBbFieldValue() throws Exception {
                return bbObject.getDescription();
            }
            @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getDescription();
            }
            @Override public void setBbFieldImp(String newValue) throws Exception {
                bbObject.setDescription(newValue);
            }
        }.setBbField("description");

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
	    new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getRoleID();
                }
                @Override public String getWsFieldValue() throws Exception {
                        return getArgs().getResultRecord().getRoleId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setRoleId(newValue);
                }
            }.setWsField("roleId");
        if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.MINIMAL) >= 0) {
	    new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getRoleName();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getRoleName();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setRoleName(newValue);
                }
            }.setWsField("roleName");
        }
        if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.STANDARD) >= 0) {
	    new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.isGuest());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getIsGuest();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setIsGuest(newValue);
                }
            }.setWsField("isGuest");
	    new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.isRemovable());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getIsRemovable();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setIsRemovable(newValue);
                }
            }.setWsField("isRemovable");
	    new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return Boolean.toString(bbObject.isSelfSelectable());
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getIsSelfSelectable();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setIsSelfSelectable(newValue);
                }
            }.setWsField("isSelfSelectable");
        }
        if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.EXTENDED) >= 0) {
	    new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getDataSourceId().toExternalString();
                }
                @Override public String getWsFieldValue() throws Exception {
                        return getArgs().getResultRecord().getDataSourceBbId();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setDataSourceBbId(newValue);
                }
            }.setWsField("dataSourceId");
	    new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getDescription();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getDescription();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setDescription(newValue);
                }
            }.setWsField("description");
        }


    }
}
