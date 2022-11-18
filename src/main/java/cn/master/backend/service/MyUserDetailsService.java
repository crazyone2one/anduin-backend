package cn.master.backend.service;

import cn.master.backend.entity.SysUser;
import cn.master.backend.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author create by 11's papa on 2022-11-18
 */
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    final SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.loadUserByUsername(username);
        return new User(sysUser.getUsername(), sysUser.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
    }
}
