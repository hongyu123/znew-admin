package com.hfw.admin.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author farkle
 * @date 2022-12-16
 */
public class CodeGrantedAuthority implements GrantedAuthority {
    private String authority;

    public CodeGrantedAuthority(){
    }
    public CodeGrantedAuthority(String authority){
        this.authority = authority;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
