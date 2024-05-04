package com.tmquang.score.security.services;

import java.io.Serial;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.tmquang.score.models.Employee;
import com.tmquang.score.repositories.RoleRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EmployeeImpl implements UserDetails {
  @Serial
  private static final long serialVersionUID = 1L;

  @Getter
  private Integer id;

  private final String username;

  @Getter
  private String email;

  @JsonIgnore
  private String password;

  private final Collection<? extends GrantedAuthority> authorities;

  @Autowired
  static RoleRepository roleRepository = null;

  public EmployeeImpl(Integer id, String username, String email, String password,
                      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  public static EmployeeImpl build(Employee user) {
    Set<GrantedAuthority> authorities = new HashSet<>();
    authorities.add(new SimpleGrantedAuthority(user.getRole().getName().name()));

    return new EmployeeImpl(
            user.getId(),
            user.getCode(),
            user.getEmail(),
            user.getPassword(),
            authorities);
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

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    EmployeeImpl user = (EmployeeImpl) o;
    return Objects.equals(id, user.id);
  }
}
