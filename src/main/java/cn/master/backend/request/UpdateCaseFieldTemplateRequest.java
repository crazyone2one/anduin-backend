package cn.master.backend.request;

import cn.master.backend.entity.CustomFieldTemplate;
import cn.master.backend.entity.TestCaseTemplate;
import lombok.Data;

import java.util.List;

/**
 * @author create by 11's papa on 2022-11-24
 */
@Data
public class UpdateCaseFieldTemplateRequest extends TestCaseTemplate {
    List<CustomFieldTemplate> customFields;
}
