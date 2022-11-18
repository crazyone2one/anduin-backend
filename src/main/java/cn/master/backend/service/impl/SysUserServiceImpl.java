package cn.master.backend.service.impl;

import cn.master.backend.entity.SysUser;
import cn.master.backend.mapper.SysUserMapper;
import cn.master.backend.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-18 01:46:40
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}
