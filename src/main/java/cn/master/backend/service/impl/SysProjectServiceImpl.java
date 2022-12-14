package cn.master.backend.service.impl;

import cn.master.backend.config.ResponseInfo;
import cn.master.backend.entity.SysProject;
import cn.master.backend.exception.CustomException;
import cn.master.backend.mapper.SysProjectMapper;
import cn.master.backend.service.SysProjectService;
import cn.master.backend.service.TestCaseNodeService;
import cn.master.backend.util.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-21 04:19:58
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SysProjectServiceImpl extends ServiceImpl<SysProjectMapper, SysProject> implements SysProjectService {
    final TestCaseNodeService testCaseNodeService;
    final JwtUtils jwtUtils;
    @Override
    public IPage<SysProject> selectPageList(SysProject project, IPage<SysProject> page) {
        LambdaQueryWrapper<SysProject> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(project.getName()), SysProject::getName, project.getName());
        return baseMapper.selectPage(Objects.nonNull(page) ? page : null, wrapper);
    }

    @Override
    public ResponseInfo<SysProject> addProject(HttpServletRequest httpServletRequest, SysProject project) {
        LambdaQueryWrapper<SysProject> wrapper = new LambdaQueryWrapper<SysProject>().eq(SysProject::getName, project.getName());
        if (baseMapper.exists(wrapper)) {
            throw new CustomException("已存在相同项目");
        }
        String token = jwtUtils.getJwtTokenFromRequest(httpServletRequest);
        Claims claims = jwtUtils.extractAllClaims(token);
        project.setCreateUser((String) claims.get("name"));
        baseMapper.insert(project);
        return ResponseInfo.success(project);
    }

    @Override
    public int updateProject(HttpServletRequest httpServletRequest, SysProject project) {
        LambdaQueryWrapper<SysProject> wrapper = new LambdaQueryWrapper<SysProject>().eq(SysProject::getName, project.getName());
        wrapper.ne(StringUtils.isNotBlank(project.getId()), SysProject::getId, project.getId());
        if (baseMapper.exists(wrapper)) {
            throw new CustomException("已存在相同项目");
        }
        int updateById = baseMapper.updateById(project);
        testCaseNodeService.updateNameByProject(project);
        return updateById;
    }
}
