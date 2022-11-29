package cn.master.backend.service;

import cn.master.backend.entity.SysUser;
import cn.master.backend.request.QueryUserRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    /**
     * 添加用户
     *
     * @param httpServletRequest httpServletRequest
     * @param sysUser            参数
     * @return cn.master.backend.entity.SysUser
     */
    SysUser addUser(HttpServletRequest httpServletRequest, SysUser sysUser);

    /**
     * 修改用户
     *
     * @param sysUser 参数
     * @return cn.master.backend.entity.SysUser
     */
    SysUser editUser(SysUser sysUser);

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
     * @param queryUserRequest 查询条件
     * @param page             入参的 IPage 可以为 null(为 null 则不分页)
     * @return java.util.List<cn.master.backend.entity.SysUser>
     */
    IPage<SysUser> selectPageVo(QueryUserRequest queryUserRequest, IPage<SysUser> page);

    /**
     * 下载
     *
     * @param httpServletResponse httpServletResponse
     * @param downloadUser        true表示导出用户信息， false表示下载模板
     * @param queryUserRequest    查询条件
     * @throws IOException IOException
     */
    void download(HttpServletResponse httpServletResponse, boolean downloadUser, QueryUserRequest queryUserRequest) throws IOException;

    String importUser(HttpServletRequest httpServletRequest, List<SysUser> cachedDataList, boolean updateSupport);

    String updateUserStatus(String userId);
}
