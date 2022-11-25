package cn.master.backend.service.impl;

import cn.master.backend.config.ResponseInfo;
import cn.master.backend.constants.TemplateConstants;
import cn.master.backend.dto.CustomFieldDao;
import cn.master.backend.dto.TestCaseTemplateDao;
import cn.master.backend.entity.SysProject;
import cn.master.backend.entity.TestCaseTemplate;
import cn.master.backend.exception.CustomException;
import cn.master.backend.mapper.TestCaseTemplateMapper;
import cn.master.backend.request.BaseQueryRequest;
import cn.master.backend.request.UpdateCaseFieldTemplateRequest;
import cn.master.backend.service.CustomFieldTemplateService;
import cn.master.backend.service.SysProjectService;
import cn.master.backend.service.TestCaseTemplateService;
import cn.master.backend.util.JwtUtils;
import cn.master.backend.util.ServiceUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-24 01:07:50
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class TestCaseTemplateServiceImpl extends ServiceImpl<TestCaseTemplateMapper, TestCaseTemplate> implements TestCaseTemplateService {

    final JwtUtils jwtUtils;
    final CustomFieldTemplateService customFieldTemplateService;
    final SysProjectService sysProjectService;

    @Override
    public IPage<TestCaseTemplate> selectPageList(BaseQueryRequest request, Page<TestCaseTemplate> producePage) {
        request.setOrders(ServiceUtils.getDefaultOrder(request.getOrders()));
        return baseMapper.listAll(request, producePage);
    }

    @Override
    public ResponseInfo<String> addTemplate(HttpServletRequest httpServletRequest, UpdateCaseFieldTemplateRequest request) {
        checkExist(request);
        TestCaseTemplate testCaseTemplate = new TestCaseTemplate();
        BeanUtils.copyProperties(request, testCaseTemplate);
        String token = jwtUtils.getJwtTokenFromRequest(httpServletRequest);
        testCaseTemplate.setCreateUser((String) jwtUtils.extractAllClaims(token).get("id"));
        testCaseTemplate.setGlobal(false);
        if (Objects.isNull(testCaseTemplate.getSystem())) {
            testCaseTemplate.setSystem(false);
        }
        baseMapper.insert(testCaseTemplate);
        customFieldTemplateService.create(request.getCustomFields(), testCaseTemplate.getId(), TemplateConstants.FieldTemplateScene.TEST_CASE.name());
        return ResponseInfo.success();
    }

    @Override
    public TestCaseTemplateDao getTemplate(String projectId) {
        SysProject sysProject = sysProjectService.getById(projectId);
        String caseTemplateId = sysProject.getCaseTemplateId();
        TestCaseTemplate caseTemplate;
        TestCaseTemplateDao caseTemplateDao = new TestCaseTemplateDao();
        if (StringUtils.isNotBlank(caseTemplateId)) {
            caseTemplate = baseMapper.selectById(caseTemplateId);
            if (Objects.nonNull(caseTemplate)) {
                caseTemplate = getDefaultTemplate(projectId);
            }
        } else {
            caseTemplate = getDefaultTemplate(projectId);
        }
        BeanUtils.copyProperties(caseTemplate, caseTemplateDao);
        List<CustomFieldDao> result = customFieldTemplateService.getCustomFieldByTemplateId(caseTemplate.getId());
        caseTemplateDao.setCustomFields(result);
        return caseTemplateDao;
    }

    private TestCaseTemplate getDefaultTemplate(String projectId) {
        LambdaQueryWrapper<TestCaseTemplate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TestCaseTemplate::getProjectId, projectId).eq(TestCaseTemplate::getSystem, true);
        List<TestCaseTemplate> testCaseTemplates = baseMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(testCaseTemplates)) {
            return testCaseTemplates.get(0);
        } else {
            wrapper.clear();
            wrapper.eq(TestCaseTemplate::getGlobal, true);
            return baseMapper.selectOne(wrapper);
        }
    }

    private void checkExist(UpdateCaseFieldTemplateRequest request) {
        if (Objects.nonNull(request.getName())) {
            LambdaQueryWrapper<TestCaseTemplate> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TestCaseTemplate::getName, request.getName())
                    .eq(TestCaseTemplate::getProjectId, request.getProjectId());
            wrapper.ne(StringUtils.isNotBlank(request.getId()), TestCaseTemplate::getId, request.getId());
            if (baseMapper.exists(wrapper)) {
                throw new CustomException("已存在该模板：" + request.getName());
            }
        }
    }
}
