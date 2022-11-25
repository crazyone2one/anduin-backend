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
 * @since 2022-11-24 01:07:50
 */
@Getter
@Setter
@TableName("test_case_template")
public class TestCaseTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * Field template name
     */
    @TableField("`name`")
    private String name;

    /**
     * Field template type
     */
    @TableField("type")
    private String type;

    /**
     * Field template description
     */
    @TableField("description")
    private String description;

    /**
     * Test Case Name
     */
    @TableField("case_name")
    private String caseName;

    /**
     * Is system field template 
     */
    @TableField("system")
    private Boolean system;

    /**
     * Is global template
     */
    @TableField("global")
    private Boolean global;

    /**
     * Test case prerequisite condition
     */
    @TableField("prerequisite")
    private String prerequisite;

    /**
     * Test case steps desc
     */
    @TableField("step_description")
    private String stepDescription;

    /**
     * Test case expected result
     */
    @TableField("expected_result")
    private String expectedResult;

    /**
     * Test case actual result
     */
    @TableField("actual_result")
    private String actualResult;

    /**
     * Create timestamp
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * Update timestamp
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * Step model
     */
    @TableField("step_model")
    private String stepModel;

    /**
     * Test case step
     */
    @TableField("steps")
    private String steps;

    @TableField("create_user")
    private String createUser;

    @TableField("project_id")
    private String projectId;
}
