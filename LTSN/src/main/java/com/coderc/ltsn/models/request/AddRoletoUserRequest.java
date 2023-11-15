package com.coderc.ltsn.models.request;

import lombok.Data;

public class AddRoletoUserRequest {
    private String username;
    private String rolename;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
