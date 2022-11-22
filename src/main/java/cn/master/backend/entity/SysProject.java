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
 * @since 2022-11-21 04:19:58
 */
@Getter
@Setter
@TableName("sys_project")
public class SysProject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Project ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * Workspace ID this project belongs to
     */
    @TableField("workspace_id")
    private String workspaceId;

    /**
     * Project name
     */
    @TableField("name")
    private String name;

    /**
     * Project description
     */
    @TableField("description")
    private String description;

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

    /**
     * Relate test case template id
     */
    @TableField("case_template_id")
    private String caseTemplateId;

    /**
     * Relate test issue template id
     */
    @TableField("issue_template_id")
    private String issueTemplateId;

    @TableField("create_user")
    private String createUser;

    @TableField("system_id")
    private String systemId;
}
