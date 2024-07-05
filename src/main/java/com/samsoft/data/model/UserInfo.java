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
@Table(name = "USER_INFO")
public class UserInfo extends BaseEntity implements UserDetails {
    @NotBlank
    private String username;
    private String profilePicture;
    @NotBlank
    private String fullName;
    //    private Set<GrantedAuthority> authorities;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled;
    private String password;

    public UserInfo(String username, String profilePicture, String fullName, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.fullName = fullName;
        this.profilePicture = profilePicture;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
