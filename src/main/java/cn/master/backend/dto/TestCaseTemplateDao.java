package cn.master.backend.dto;

import cn.master.backend.entity.TestCaseTemplate;
import lombok.Data;

import java.util.List;

/**
 * @author create by 11's papa on 2022-11-25
 */
@Data
public class TestCaseTemplateDao extends TestCaseTemplate {
    List<CustomFieldDao> customFields;
}
