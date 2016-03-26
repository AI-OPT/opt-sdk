package com.ai.opt.sdk.test.paas.validator;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.ai.opt.sdk.validator.constraints.MobilePhone;

public class TestVo {

    @NotBlank(message = "客户名称不能为空")
    private String custName;

    @Email(message = "邮箱格式不正确")
    private String email;

    @MobilePhone(message = "手机号码格式不正确")
    private String mobilePhone;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

}
