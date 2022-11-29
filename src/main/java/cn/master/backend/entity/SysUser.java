package cn.master.backend.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-18 01:46:40
 */
@Getter
@Setter
@TableName("sys_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    @ExcelIgnore
    private String userId;

    /**
     * 用户名
     */
    @TableField("username")
    @ExcelProperty(value = "账号",index = 0)
    private String username;

    /**
     * 用户昵称
     */
    @TableField("nickname")
    @ExcelProperty(value = "昵称",index = 1)
    private String nickname;

    /**
     * 密码
     */
    @TableField("password")
    @ExcelIgnore
    private String password;

    /**
     * 邮箱
     */
    @TableField("email")
    @ExcelProperty(value = "邮箱",index = 2)
    private String email;

    /**
     * 手机号
     */
    @TableField("mobile")
    @ExcelProperty(value = "手机号",index = 3)
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    @TableField("status")
    @ExcelIgnore
    private Boolean status;

    /**
     * 创建者ID
     */
    @TableField("create_user_id")
    @ExcelIgnore
    private String createUserId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @ExcelIgnore
    private LocalDateTime createTime;

    /**
     * 状态  0：删除   1：未删除
     */
    @TableField("delete_flag")
    @ExcelIgnore
    private Boolean deleteFlag;
}
