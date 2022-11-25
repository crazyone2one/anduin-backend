package cn.master.backend.dto;

import cn.master.backend.entity.CustomFieldTemplate;
import lombok.Data;

/**
 * @author create by 11's papa on 2022-11-24
 */
@Data
public class CustomFieldTemplateDao extends CustomFieldTemplate {
    private String name;

    private String scene;

    private String type;

    private String remark;

    private String options;

    private Boolean system;
}
