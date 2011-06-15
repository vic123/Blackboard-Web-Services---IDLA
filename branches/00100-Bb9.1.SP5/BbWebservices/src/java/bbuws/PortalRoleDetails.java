/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbuws;

/**
 *
 * @author vic
 * 
 * PortalRole specific issues with duplicated names of built-in and custom portal roles.
 * Names of built-in roles have suffix taken from the name of table's column (in case of institution_roles table is is role_name)
 * Explained on sample scenario:
 * 1. System role Alumni (role_id = ALUMNI). Stored in database as role_name = ALUMNI.role_name 
 * 2. Creating custom role Alumni.role_name (role_id = Alumni_id4). ERROR - duplicated names
 * 3. Creating custom role Alumni (role_id = Alumni_id1). Stored in database as role_name = ALUMNI.role_name
 * 4. Creating custom role Alumni (role_id = Alumni_id2). Stored in database as role_name = ALUMNI.role_name
 * 5. Creating custom role ALumni (different case of L) (role_id = Alumni_id3). Stored in database as role_name = ALumni
 * 6. Creating custom role Alumni (role_id = Alumni_id4). ERROR - duplicated names
 * 
 * This shows that it is possible to create roles with same names.
 * Also, when using Bb method LoadByRoleName, it is always necessary to provide "full" role name 
 * - the one that is stored in database, i.e. ALUMNI.role_name, otherwise we get blackboard.persist.KeyNotFoundException. 
 * And it ALWAYS returns system role, even if custom duplicates (of Alumni type) exist.
 * If ALumni (or any other letter case-different combination), then load by just "Alumni" (case insensetive) works and loads custom role.
 * And Bb's returned value of built-in role names does not contain ".role_name" suffix.
 * 
 * Webservices use following logic when loading portal role by name:
 * 1. First try to load portal role directly by name that was provided. In case of success returns loaded record.
 * 2. In case of blackboard.persist.KeyNotFoundException complements name with ".role_name" suffix and returns obtained result or exception.
  */

/*
 PortalRoleDbPersisterImpl.persist(PortalRole role, Connection con):    super.doPersist(PortalRoleDbMap.MAP, role, con);
 NewBaseDbPersister.doPersist(DbObjectMap map, Object target, Connection con): doInsert(map, target, con);
 NewBaseDbPersister.doInsert(DbObjectMap map, Object target, Connection con): query = new InsertProcedureQuery(map, target);runQuery(query, con);
 InsertProcedureQuery.InsertProcedureQuery(DbObjectMap map, Object obj): super(map, obj, (new StringBuilder()).append(map.getTableName()).append("_cr").toString());
 AbstractBaseDbPersister.runQuery(Query query, Connection con): query.init(_bbDatabase, _pm.getContainer()); AccessController.doPrivileged(new DbConnectivityPrivilege(query, con, null));
 AbstractBaseDbPersister$DbConnectivityPrivilege :
     public Object run()
        throws Exception
    {
        _query.executeQuery(_con);
        return null;
    }
 Query.executeQuery(con): doExecute(con);
 StoredProcedureQuery.doExecute(Connection con): processResults(rst);
 */ 
public class PortalRoleDetails extends RoleDetails { //!! implements ReturnTypeInterface {
    private String dataSourceBbId;
    private String description;
    private String isGuest;
    private String isRemovable;
    private String isSelfSelectable;

    /**
     * @return the dataSourceId
     */
    public String getDataSourceBbId() {
        return dataSourceBbId;
    }

    /**
     * @param dataSourceId the dataSourceId to set
     */
    public void setDataSourceBbId(String dataSourceId) {
        this.dataSourceBbId = dataSourceId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the isGuest
     */
    public String getIsGuest() {
        return isGuest;
    }

    /**
     * @param isGuest the isGuest to set
     */
    public void setIsGuest(String isGuest) {
        this.isGuest = isGuest;
    }

    /**
     * @return the isRemovable
     */
    public String getIsRemovable() {
        return isRemovable;
    }

    /**
     * @param isRemovable the isRemovable to set
     */
    public void setIsRemovable(String isRemovable) {
        this.isRemovable = isRemovable;
    }

    /**
     * @return the isSelfSelectable
     */
    public String getIsSelfSelectable() {
        return isSelfSelectable;
    }

    /**
     * @param isSelfSelectable the isSelfSelectable to set
     */
    public void setIsSelfSelectable(String isSelfSelectable) {
        this.isSelfSelectable = isSelfSelectable;
    }
}
