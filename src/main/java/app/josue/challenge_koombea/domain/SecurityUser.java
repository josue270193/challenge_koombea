package app.josue.challenge_koombea.domain;

import app.josue.challenge_koombea.domain.entity.UserEntity;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser implements UserDetails {

  private UserEntity userEntity;
  private List<GrantedAuthority> authorities;

  public SecurityUser(UserEntity userEntity, List<String> roles) {
    this.userEntity = userEntity;
    this.authorities = roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return userEntity.getPassword();
  }

  @Override
  public String getUsername() {
    return userEntity.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return userEntity.isEnabled();
  }

  @Override
  public boolean isAccountNonLocked() {
    return userEntity.isEnabled();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return userEntity.isEnabled();
  }

  @Override
  public boolean isEnabled() {
    return userEntity.isEnabled();
  }

}
