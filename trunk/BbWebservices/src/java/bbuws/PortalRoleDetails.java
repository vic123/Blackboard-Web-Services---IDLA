/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbuws;

/**
 *
 * @author vic
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
