package cn.master.backend.service;

import cn.master.backend.dto.CustomFieldDao;
import cn.master.backend.dto.CustomFieldTemplateDao;
import cn.master.backend.entity.CustomFieldTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-24 01:05:24
 */
public interface CustomFieldTemplateService extends IService<CustomFieldTemplate> {

    List<CustomFieldTemplateDao> listTemplate(CustomFieldTemplate request);

    void create(List<CustomFieldTemplate> customFields, String templateId, String scene);
    List<CustomFieldTemplate> getCustomFields(@Param("templateId") String templateId);
    List<CustomFieldDao> getCustomFieldByTemplateId(String templateId);
}
