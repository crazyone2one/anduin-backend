package cn.master.backend.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;

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
    private String userId;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 用户昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    @TableField("status")
    private Boolean status;

    /**
     * 创建者ID
     */
    @TableField("create_user_id")
    private String createUserId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 状态  0：删除   1：未删除
     */
    @TableField("delete_flag")
    private Boolean deleteFlag;
}
