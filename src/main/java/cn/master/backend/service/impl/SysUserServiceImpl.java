package cn.master.backend.service.impl;

import cn.master.backend.entity.SysUser;
import cn.master.backend.mapper.SysUserMapper;
import cn.master.backend.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-18 01:46:40
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    final PasswordEncoder passwordEncoder;
    private final static String DEFAULT_PASSWORD = "e10adc3949ba59abbe56e057f20f883e";
    @Override
    public SysUser addUser(SysUser sysUser) {
        String password = passwordEncoder.encode(StringUtils.isBlank(sysUser.getPassword()) ? DEFAULT_PASSWORD : sysUser.getPassword());
        sysUser.setPassword(password);
        sysUser.setDeleteFlag(true);
        sysUser.setStatus(true);
        baseMapper.insert(sysUser);
        return sysUser;
    }
}
