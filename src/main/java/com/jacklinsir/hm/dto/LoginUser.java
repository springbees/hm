package com.jacklinsir.hm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jacklinsir.hm.model.SysPermission;
import com.jacklinsir.hm.model.SysUser;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author linSir
 */
@Data
public class LoginUser extends SysUser implements UserDetails {

    private static final long serialVersionUID = -1379274258881257107L;

    private List<SysPermission> permissions;
    private String token;
    /**
     * 登陆时间戳（毫秒）
     */
    private Long loginTime;
    /**
     * 过期时间戳
     */
    private Long expireTime;


    /**
     * 当前用户获得权限集合，封装
     *
     * @return
     * @JsonIgnore json序列化忽略
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.parallelStream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
                .map(p -> new SimpleGrantedAuthority(p.getPermission())).collect(Collectors.toSet());
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        // do nothing
    }

    // 账户是否未过期
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否未锁定
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return getStatus() != Status.LOCKED;
    }

    // 密码是否未过期
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 账户是否激活
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

}
