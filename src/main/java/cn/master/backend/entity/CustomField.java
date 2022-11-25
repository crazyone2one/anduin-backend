package cn.master.backend.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-22 04:17:38
 */
@Getter
@Setter
@TableName("custom_field")
public class CustomField implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * Custom field name
     */
    @TableField("`name`")
    private String name;

    /**
     * Custom field use scene
     */
    @TableField("scene")
    private String scene;

    /**
     * Custom field type
     */
    @TableField("type")
    private String type;

    /**
     * Custom field remark
     */
    @TableField("remark")
    private String remark;

    /**
     * Test resource pool status
     */
    @TableField("`options`")
    private String options;

    /**
     * Is system custom field
     */
    @TableField("system")
    private Boolean system;

    /**
     * Is global custom field
     */
    @TableField("`global`")
    private Boolean global;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField("create_user")
    private String createUser;

    @TableField("project_id")
    private String projectId;
}
