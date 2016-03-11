package com.ai.opt.sdk.web.model;

import java.io.Serializable;

/**
 * 用户信息entity
 * 
 *
 */
public class UserLoginVo implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1425662695110613675L;

    /****************** 租户信息 *************/
    // 租户id
    private String tenantId;

    private String tenantName;

    /**
     * 行业code
     */
    private String industryCode;

    /**
     * 租户LOGO地址
     */
    private String logo;

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    /****************** 用户信息 *************/
    private String username;

    private String userEmail;

    private String userPhone;

    /****************** 员工信息 *************/
    // 员工id
    private String staffId;

    // 员工姓名
    private String staffName;

    /****************** 工号信息 *************/
    // 工号id
    private long Staffnoid;

    // 员工工号
    private String staffNo;

    /****************** 操作员信息 *************/
    private long operId;

    private String operCode;

    private String orgType;

    private String orgId;
    
    //工号（操作员）所属省份
  	private String provinceCode;
  	//工号（操作员）所属地市
  	private String cityCode;
  	//工号（操作员）所属具体区域编码
  	private String areaCode;
  	//工号（操作员）所属区域级别 0：全国  1：省 2：地市  3：区县 4：街道 5：小区
  	private String areaLevel;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getOperCode() {
        return operCode;
    }

    public void setOperCode(String operCode) {
        this.operCode = operCode;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public long getStaffnoid() {
        return Staffnoid;
    }

    public void setStaffnoid(long staffnoid) {
        Staffnoid = staffnoid;
    }

    public long getOperId() {
        return operId;
    }

    public void setOperId(long operId) {
        this.operId = operId;
    }

    
    public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaLevel() {
		return areaLevel;
	}

	public void setAreaLevel(String areaLevel) {
		this.areaLevel = areaLevel;
	}

	@Override
    public String toString() {
        return "UserLoginVo [tenantId=" + tenantId + ", username=" + username + ", userEmail="
                + userEmail + ", userPhone=" + userPhone + ", staffId=" + staffId + ", staffName="
                + staffName + ", Staffnoid=" + Staffnoid + ", staffNo=" + staffNo + ", operId="
                + operId + ", operCode=" + operCode + ", orgType=" + orgType + ", orgId=" + orgId
                + "]";
    }

}
