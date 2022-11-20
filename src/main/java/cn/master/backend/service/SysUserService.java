package cn.master.backend.service;

import cn.master.backend.entity.SysUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-18 01:46:40
 */
public interface SysUserService extends IService<SysUser> {

    SysUser addUser(SysUser sysUser);

    /**
     * 根据用户名获取用户
     *
     * @param userName 用户名
     * @return cn.master.backend.entity.SysUser
     */
    SysUser loadUserByName(String userName);

    /**
     * 查询用户
     *
     * @param sysUser 查询条件
     * @param page    入参的 IPage 可以为 null(为 null 则不分页)
     * @return java.util.List<cn.master.backend.entity.SysUser>
     */
    List<SysUser> selectPageVo(SysUser sysUser, IPage<SysUser> page);
}
