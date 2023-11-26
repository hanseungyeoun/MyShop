package com.example.myshop.security;

import com.example.myshop.admin.domain.Admin;
import com.example.myshop.common.jpa.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminPrincipal implements UserDetails {

  private Long id;
  private String userName;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public AdminPrincipal(Long id, String userName, String password, Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.userName = userName;
    this.password = password;
    this.authorities = authorities;
  }

  public static AdminPrincipal fromEntity(Admin user) {
    Set<RoleType> roleTypes = Set.of(RoleType.ROLE_USER);

    return new AdminPrincipal(
        user.getId(),
        user.getUsername(),
        user.getPassword().getValue(),
        roleTypes.stream()
            .map(RoleType::getName)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toUnmodifiableSet())
    );
  }

  @Override
  public String getUsername() {
    return userName;
  }

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return true;
  }
}
