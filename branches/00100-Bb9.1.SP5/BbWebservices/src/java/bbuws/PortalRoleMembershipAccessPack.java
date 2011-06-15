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

import blackboard.data.user.User;
import blackboard.data.Identifiable;
import blackboard.data.user.UserRole;

import blackboard.admin.data.role.PortalRoleMembership;
import blackboard.admin.persist.role.impl.PortalRoleMembershipDbLoader;
import blackboard.admin.persist.role.PortalRoleMembershipLoader;
import blackboard.admin.persist.role.impl.PortalRoleMembershipDbPersister;
import blackboard.admin.persist.role.PortalRoleMembershipPersister;

import blackboard.persist.Id;
import blackboard.data.role.PortalRole;


import java.util.*;

//public class UserAccessPack <BbUserType extends User, WsObjectType extends BbWsDataDetails>
public class PortalRoleMembershipAccessPack <BbPortalRoleMembershipType extends UserRole,
            ArgumentsType extends PortalRoleMembershipAccessPack.PortalRoleMembershipArguments>
        extends BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType
            <ArgumentsType, BbPortalRoleMembershipType, bbuws.PortalRoleMembershipDetails> {


    public static class PortalRoleMembershipArguments extends BbWsArguments<PortalRoleMembershipDetails, PortalRoleMembershipDetails> {
    }
    public static class PortalRoleMembershipArguments_DATA extends PortalRoleMembershipArguments {
    }

    public static class PortalRoleMembershipArguments_ADMIN_DATA extends PortalRoleMembershipArguments_DATA {
        protected void initializeDefaults() {
            super.initializeDefaults();
            getApiToUseList().clear();
            getApiToUseList().add(new ApiToUse(ApiToUseEnum.ADMIN_DATA, null));
        }
    }


    @Override protected void setSafeResultRecordIds() {
        super.setSafeResultRecordIds();
        getArgs().getResultRecord().setUserId(getArgs().getInputRecord().getUserId());
        getArgs().getResultRecord().setPortalRoleId(getArgs().getInputRecord().getPortalRoleId());
        //getArgs().getResultRecord().setUserBatchUid(getArgs().getInputRecord().getUserBatchUid());
        //getArgs().getResultRecord().setPortalRoleBatchUid(getArgs().getInputRecord().getPortalRoleBatchUid());
    }



}
