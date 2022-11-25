package cn.master.backend.service.impl;

import cn.master.backend.dto.CustomFieldDao;
import cn.master.backend.dto.CustomFieldTemplateDao;
import cn.master.backend.entity.CustomField;
import cn.master.backend.entity.CustomFieldTemplate;
import cn.master.backend.mapper.CustomFieldMapper;
import cn.master.backend.mapper.CustomFieldTemplateMapper;
import cn.master.backend.service.CustomFieldTemplateService;
import cn.master.backend.util.ServiceUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-24 01:05:24
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CustomFieldTemplateServiceImpl extends ServiceImpl<CustomFieldTemplateMapper, CustomFieldTemplate> implements CustomFieldTemplateService {

    final CustomFieldMapper customFieldMapper;

    @Override
    public List<CustomFieldTemplateDao> listTemplate(CustomFieldTemplate request) {
        return baseMapper.listByRequest(request);
    }

    @Override
    public void create(List<CustomFieldTemplate> customFields, String templateId, String scene) {
        if (CollectionUtils.isNotEmpty(customFields)) {
            Long nextOrder = ServiceUtils.getNextOrder(templateId, baseMapper::getLastOrder);
            for (CustomFieldTemplate item : customFields) {
                item.setTemplateId(templateId);
                item.setScene(scene);
                if (item.getRequired() == null) {
                    item.setRequired(false);
                }
                nextOrder += ServiceUtils.ORDER_STEP;
                item.setOrder((int) nextOrder.longValue());
                baseMapper.insert(item);
            }
        }
    }

    @Override
    public List<CustomFieldTemplate> getCustomFields(String templateId) {
        LambdaQueryWrapper<CustomFieldTemplate> wrapper =
                new LambdaQueryWrapper<CustomFieldTemplate>().eq(CustomFieldTemplate::getTemplateId, templateId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<CustomFieldDao> getCustomFieldByTemplateId(String templateId) {
        List<CustomFieldTemplate> customFields = getCustomFields(templateId);
        List<String> fieldIds = customFields.stream()
                .map(CustomFieldTemplate::getFieldId)
                .collect(Collectors.toList());
        List<CustomField> fields = getFieldByIds(fieldIds);
        Map<String, CustomField> fieldMap = fields.stream()
                .collect(Collectors.toMap(CustomField::getId, item -> item));
        List<CustomFieldDao> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(customFields)) {
            customFields.forEach((item) -> {
                CustomFieldDao customFieldDao = new CustomFieldDao();
                CustomField customField = fieldMap.get(item.getFieldId());
                BeanUtils.copyProperties(customField, customFieldDao);
                BeanUtils.copyProperties(item, customFieldDao);
                customFieldDao.setId(item.getFieldId());
                result.add(customFieldDao);
            });
        }
        return result.stream().distinct().collect(Collectors.toList());
    }

    private List<CustomField> getFieldByIds(List<String> fieldIds) {
        if (CollectionUtils.isNotEmpty(fieldIds)) {
            LambdaQueryWrapper<CustomField> wrapper = new LambdaQueryWrapper<CustomField>().in(CustomField::getId, fieldIds);
            return customFieldMapper.selectList(wrapper);
        }
        return new ArrayList<>();
    }
}
