package cn.master.backend.service.impl;

import cn.master.backend.config.ResponseInfo;
import cn.master.backend.constants.TemplateConstants;
import cn.master.backend.dto.CustomFieldDao;
import cn.master.backend.entity.CustomField;
import cn.master.backend.exception.CustomException;
import cn.master.backend.mapper.CustomFieldMapper;
import cn.master.backend.request.QueryCustomFieldRequest;
import cn.master.backend.security.JwtProperties;
import cn.master.backend.service.CustomFieldService;
import cn.master.backend.util.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-22 04:17:38
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CustomFieldServiceImpl extends ServiceImpl<CustomFieldMapper, CustomField> implements CustomFieldService {

    final JwtUtils jwtUtils;
    final JwtProperties jwtProperties;
    private void checkExist(CustomField customField) {
        if (Objects.nonNull(customField.getName())) {
            LambdaQueryWrapper<CustomField> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CustomField::getName, customField.getName());
            wrapper.eq(CustomField::getProjectId, customField.getProjectId());
            wrapper.eq(CustomField::getScene, customField.getScene());
            wrapper.ne(StringUtils.isNotBlank(customField.getId()), CustomField::getId, customField.getId());
            if (baseMapper.exists(wrapper)) {
                throw new CustomException("custom_field_already");
            }
        }
    }

    @Override
    public CustomField add(HttpServletRequest httpServletRequest, CustomField customField) {
        checkExist(customField);
        customField.setGlobal(false);
        String token = jwtUtils.getJwtTokenFromRequest(httpServletRequest);
        if (StringUtils.isNotBlank(token)) {
            Claims claims = jwtUtils.extractAllClaims(token);
            customField.setCreateUser((String) claims.get("id"));
        }
        baseMapper.insert(customField);
        return customField;
    }

    @Override
    public IPage<CustomField> selectPageList(IPage<CustomField> page, QueryCustomFieldRequest request) {
        LambdaQueryWrapper<CustomField> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(request.getName()), CustomField::getName, request.getName());
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public ResponseInfo<String> updateCustomField(HttpServletRequest httpServletRequest, CustomField customField) {
        if (Objects.nonNull(customField.getGlobal()) && customField.getGlobal()) {
            CustomFieldDao customFieldDao = new CustomFieldDao();
            BeanUtils.copyProperties(customField, customFieldDao);
            customFieldDao.setOriginGlobalId(customField.getId());
            add(httpServletRequest, customFieldDao);
            // TODO: 2022/11/24 待完善
            if (StringUtils.equals(customField.getScene(), TemplateConstants.FieldTemplateScene.TEST_CASE.name())) {
                log.info("测试用例模板");
            } else if (StringUtils.equals(customField.getScene(), TemplateConstants.FieldTemplateScene.API.name())) {
                log.info("api模板");
            } else {
                log.info("issue模板");
            }
        } else {
            checkExist(customField);
            baseMapper.updateById(customField);
        }
        return null;
    }

    @Override
    public IPage<CustomField> listRelate(Page<CustomField> producePage, QueryCustomFieldRequest request) {
        List<String> templateContainIds = request.getTemplateContainIds();
        if (CollectionUtils.isEmpty(templateContainIds)) {
            templateContainIds = new ArrayList<>();
        }
        request.setTemplateContainIds(templateContainIds);
        LambdaQueryWrapper<CustomField> wrapper = new LambdaQueryWrapper<>();
        return baseMapper.selectPage(producePage, wrapper);
    }
}
