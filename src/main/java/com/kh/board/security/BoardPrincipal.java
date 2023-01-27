package com.kh.board.security;

import com.kh.board.dto.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record BoardPrincipal(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String email,
        String nickname
) implements UserDetails {
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

    public static BoardPrincipal of(String username, String password, String email, String nickname){
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new BoardPrincipal(username, password, roleTypes.stream().map(RoleType::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toUnmodifiableSet()), email, nickname);
    }

    public static BoardPrincipal from(UserDto dto) {
        return BoardPrincipal.of(dto.userId(), dto.password(), dto.email(), dto.nickname());
    }

    public UserDto toDto() {
        return UserDto.of(username, password, email, nickname);
    }

    @Getter
    @RequiredArgsConstructor
    public enum RoleType {
        USER("ROLE_USER");

        private final String name;
    }


}
