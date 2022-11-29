package cn.master.backend.service.impl;

import cn.master.backend.entity.SysUser;
import cn.master.backend.exception.CustomException;
import cn.master.backend.mapper.SysUserMapper;
import cn.master.backend.request.QueryUserRequest;
import cn.master.backend.service.SysUserService;
import cn.master.backend.util.JacksonUtils;
import cn.master.backend.util.JwtUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    final JwtUtils jwtUtils;
    private final static String DEFAULT_PASSWORD = "e10adc3949ba59abbe56e057f20f883e";

    @Override
    public SysUser addUser(HttpServletRequest httpServletRequest, SysUser sysUser) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, sysUser.getUsername());
        if (baseMapper.exists(wrapper)) {
            throw new CustomException("用户名已存在");
        }
        String password = passwordEncoder.encode(StringUtils.isBlank(sysUser.getPassword()) ? DEFAULT_PASSWORD : sysUser.getPassword());
        sysUser.setPassword(password);
        sysUser.setDeleteFlag(true);
        sysUser.setStatus(true);
        String token = jwtUtils.getJwtTokenFromRequest(httpServletRequest);
        sysUser.setCreateUserId((String) jwtUtils.extractAllClaims(token).get("id"));
        baseMapper.insert(sysUser);
        return sysUser;
    }

    @Override
    public SysUser editUser(SysUser sysUser) {
        SysUser selectById = baseMapper.selectById(sysUser.getUserId());
        SysUser sysUser1 = new SysUser();
        BeanUtils.copyProperties(selectById, sysUser1);
        sysUser1.setEmail(sysUser.getEmail());
        sysUser1.setNickname(sysUser.getNickname());
        sysUser1.setMobile(sysUser.getMobile());
        baseMapper.updateById(sysUser1);
        return sysUser1;
    }

    @Override
    public SysUser loadUserByName(String userName) {
        return baseMapper.loadUserByUsername(userName);
    }

    @Override
    public IPage<SysUser> selectPageVo(QueryUserRequest queryUserRequest, IPage<SysUser> page) {
        LambdaQueryWrapper<SysUser> wrapper = getLambdaQueryWrapper(queryUserRequest);
        return baseMapper.selectPage(page, wrapper);
    }

    /**
     * 构建查询参数
     *
     * @param queryUserRequest QueryUserRequest
     * @return com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<cn.master.backend.entity.SysUser>
     */
    private static LambdaQueryWrapper<SysUser> getLambdaQueryWrapper(QueryUserRequest queryUserRequest) {
        if (Objects.isNull(queryUserRequest)) {
            return new LambdaQueryWrapper<>();
        }
        return new LambdaQueryWrapper<SysUser>()
                .like(StringUtils.isNotBlank(queryUserRequest.getName()), SysUser::getUsername, queryUserRequest.getName())
                .in(CollectionUtils.isNotEmpty(queryUserRequest.getIds()), SysUser::getUserId, queryUserRequest.getIds())
                .eq(Objects.nonNull(queryUserRequest.getUserStatus()), SysUser::getStatus, queryUserRequest.getUserStatus())
                .eq(SysUser::getDeleteFlag, true)
                .orderByDesc(SysUser::getCreateTime);
    }

    @Override
    public void download(HttpServletResponse httpServletResponse, boolean downloadUser, QueryUserRequest queryUserRequest) throws IOException {
        List<SysUser> sysUsers = new ArrayList<>();
        String fileNamePrefix = downloadUser ? "导出用户" : "导入用户模板";
        if (downloadUser) {
            LambdaQueryWrapper<SysUser> wrapper = getLambdaQueryWrapper(queryUserRequest);
            sysUsers = baseMapper.selectList(wrapper);
        }
        try {
            httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            httpServletResponse.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode(fileNamePrefix + System.currentTimeMillis(), StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            httpServletResponse.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(httpServletResponse.getOutputStream(), SysUser.class).autoCloseStream(false).sheet("用户信息").doWrite(sysUsers);
        } catch (Exception e) {
            // 重置response
            httpServletResponse.reset();
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding("utf-8");
            Map<String, String> map = MapUtils.newHashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            httpServletResponse.getWriter().println(JacksonUtils.beanToJson(map));
        }
    }

    @Override
    public String importUser(HttpServletRequest httpServletRequest, List<SysUser> cachedDataList, boolean updateSupport) {
        if (Objects.isNull(cachedDataList) || CollectionUtils.isEmpty(cachedDataList)) {
            throw new CustomException("导入用户数据不能为空");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysUser sysUser : cachedDataList) {
            try {
                SysUser byUsername = baseMapper.loadUserByUsername(sysUser.getUsername());
                if (Objects.isNull(byUsername)) {
                    addUser(httpServletRequest, sysUser);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、账号 ").append(sysUser.getUsername()).append(" 导入成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、账号 ").append(sysUser.getUsername()).append(" 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + sysUser.getUsername() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public String updateUserStatus(String userId) {
        SysUser sysUser = baseMapper.selectById(userId);
        sysUser.setStatus(!sysUser.getStatus());
        baseMapper.updateById(sysUser);
        return "用户" + sysUser.getUsername() + "状态更新成功";
    }
}
