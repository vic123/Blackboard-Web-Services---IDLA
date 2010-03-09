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
package bbuws;

import blackboard.data.course.CourseMembership;
import blackboard.data.role.PortalRole;
import blackboard.platform.security.SystemRole;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class Role extends bbwscommon.BbWsDataDetails implements ReturnTypeInterface {

    private String roleId;
    private String roleName;

    public Role() {
    }

    public Role(PortalRole pr) {
        this.roleId = pr.getRoleID();
        this.roleName = pr.getRoleName();
        this.bbId = pr.getId().toExternalString();
    }

    public Role(SystemRole sr) {
        this.roleId = sr.getIdentifier();
        this.roleName = sr.getName();
        this.bbId = sr.getId().toExternalString();
    }

    public Role(CourseMembership cm) {
        this.roleName = cm.getRole().toFieldName();
    }

    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    private String[] getUserDetailsArray() {
        return new String[]{this.roleId, this.roleName};
    }

    public String[] toStringArray() {
        return getUserDetailsArray();
    }

    public String[] toStringArrayHeader() {
        return new String[]{"Role Id", "Role Name"};
    }
    /**
     *
     * @author vic
     */
    private String bbId;

    @Override
    public String getBbId() {
        return this.bbId;
    }

    @Override
    public void setBbId(String id) {
        this.bbId = id;
    }
    /*
    @Override public String getBatchUid() {
    return this.bbId;
    }
    @Override public void setBatchUid(String batchUid) {
    this.bbId = batchUid;
    }*/
}
