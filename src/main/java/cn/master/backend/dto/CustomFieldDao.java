package cn.master.backend.dto;

import cn.master.backend.entity.CustomField;
import lombok.Data;

/**
 * @author create by 11's papa on 2022-11-24
 */
@Data
public class CustomFieldDao extends CustomField {
    private Boolean required;

    private Integer order;

    private String defaultValue;

    private String textValue;

    private String value;

    private String customData;

    private String originGlobalId;

    private String key;
}
