package cn.master.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @author create by 11's papa on 2022-12-02
 */
@Getter
@Setter
@TableName("test_case_follow")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestCaseFollow {
    @TableField("case_id")
    private String caseId;

    @TableField("follow_id")
    private String followId;
}
