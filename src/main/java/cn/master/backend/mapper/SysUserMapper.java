package cn.master.backend.mapper;

import cn.master.backend.entity.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-18 01:46:40
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return cn.master.backend.entity.SysUser
     */

    @Select("select * from sys_user where username=#{username}")
    SysUser loadUserByUsername(String username);

    @Select("select * from sys_user ${ew.customSqlSegment}")
    List<SysUser> loadAllByCondition(IPage<SysUser> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<SysUser> wrapper);
}
