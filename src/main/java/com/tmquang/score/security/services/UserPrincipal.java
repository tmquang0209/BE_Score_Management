package com.tmquang.score.security.services;

import com.tmquang.score.enums.ERole;
import com.tmquang.score.models.Employee;
import com.tmquang.score.models.Teacher;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    @Getter
    private final Integer id;
    @Getter
    private final String email;

    public UserPrincipal(EmployeeImpl employee, Integer id, String email) {
        this.username = employee.getUsername();
        this.password = employee.getPassword();
        this.authorities = employee.getAuthorities();
        this.id = id;
        this.email = email;
    }

    public UserPrincipal(TeacherImpl teacher, Integer id, String email) {
        this.username = teacher.getUsername();
        this.password = teacher.getPassword();
        this.authorities = teacher.getAuthorities();
        this.id = id;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
