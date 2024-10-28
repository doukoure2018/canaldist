package com.dist.canal.payload;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import com.dist.canal.entity.Role;
import com.dist.canal.entity.User;

import java.util.Collection;

import static com.dist.canal.mapper.UserDTOMapper.fromUser;

@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {

    private final User user;
    private final Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(role.getPermission());
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // Assuming UserDto has a getPassword() method
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getNonLocked(); // Assuming UserDto has a getNonLocked() method
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Or return based on your application's logic
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

    public UserDto getUser() {
        return fromUser(this.user, role);
    }
}

