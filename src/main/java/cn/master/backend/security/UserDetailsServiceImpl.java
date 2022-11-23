package cn.master.backend.security;

import cn.master.backend.entity.SysUser;
import cn.master.backend.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author create by 11's papa on 2022-11-18
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    final SysUserMapper sysUserMapper;

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.loadUserByUsername(username);
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户名错误/不存在");
        }
        // TODO: 2022/11/19 先指定role，后期优化
        SecurityUser securityUser = new SecurityUser(sysUser.getUsername(), sysUser.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
        securityUser.setNickname(sysUser.getNickname());
        securityUser.setUserId(sysUser.getUserId());
        return securityUser;
    }
}
