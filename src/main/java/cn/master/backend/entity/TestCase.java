package cn.master.backend.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-23 09:38:23
 */
@Getter
@Setter
@TableName("test_case")
@AllArgsConstructor
@NoArgsConstructor
public class TestCase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Test case ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * Node ID this case belongs to
     */
    @TableField("node_id")
    private String nodeId;

    /**
     * Test ID relation to
     */
    @TableField("test_id")
    private String testId;

    /**
     * Node path this case belongs to
     */
    @TableField("node_path")
    private String nodePath;

    /**
     * Project ID this test belongs to
     */
    @TableField("project_id")
    private String projectId;

    /**
     * Test case name
     */
    @TableField("name")
    private String name;

    /**
     * Test case type
     */
    @TableField("type")
    private String type;

    /**
     * Test case maintainer
     */
    @TableField("maintainer")
    private String maintainer;

    /**
     * Test case priority
     */
    @TableField("priority")
    private String priority;

    /**
     * Test case method type
     */
    @TableField("method")
    private String method;

    /**
     * Test case prerequisite condition
     */
    @TableField("prerequisite")
    private String prerequisite;

    /**
     * Test case remark
     */
    @TableField("remark")
    private String remark;

    /**
     * Test case steps (JSON format)
     */
    @TableField("steps")
    private String steps;

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
     * Manually controlled growth identifier
     */
    @TableField("num")
    private Integer num;

    @TableField("review_status")
    private String reviewStatus;

    @TableField("tags")
    private String tags;

    @TableField("follow_people")
    private String followPeople;

    @TableField("status")
    private String status;

    @TableField("step_description")
    private String stepDescription;

    @TableField("expected_result")
    private String expectedResult;

    /**
     * CustomField
     */
    @TableField("custom_fields")
    private String customFields;

    /**
     * Test case step model
     */
    @TableField("step_model")
    private String stepModel;

    /**
     * custom num
     */
    @TableField("custom_num")
    private String customNum;

    @TableField("create_user")
    private String createUser;

    @TableField("original_status")
    private String originalStatus;

    /**
     * Delete timestamp
     */
    @TableField("delete_time")
    private LocalDateTime deleteTime;

    /**
     * Delete user id
     */
    @TableField("delete_user_id")
    private String deleteUserId;

    /**
     * 自定义排序，间隔5000
     */
    @TableField("`order`")
    private Long order;

    /**
     * 是否是公共用例
     */
    @TableField("case_public")
    private Boolean casePublic;

    /**
     * 版本ID
     */
    @TableField("version_id")
    private String versionId;

    /**
     * 指向初始版本ID
     */
    @TableField("ref_id")
    private String refId;

    /**
     * 是否为最新版本 0:否，1:是
     */
    @TableField("latest")
    private Boolean latest;

    /**
     * 最近一次的测试计划的执行结果
     */
    @TableField("last_execute_result")
    private String lastExecuteResult;
}
