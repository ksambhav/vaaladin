package com.samsoft.data.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "user_info")
public class UserInfo extends BaseEntity implements UserDetails {
    @NotBlank
    private String username;
    private String mobile;
    @NotBlank
    private String fullName;
    //    private Set<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private String password;

    public UserInfo(String username, String mobile, String fullName, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.fullName = fullName;
        this.mobile = mobile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
