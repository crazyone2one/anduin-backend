package cn.master.backend.mapper;

import cn.master.backend.entity.TestCaseFollow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author create by 11's papa on 2022-12-02
 */
@Mapper
public interface TestCaseFollowMapper extends BaseMapper<TestCaseFollow> {

    /**
     * 删除记录
     *
     * @param caseId 测试用例id
     */
    @Delete("delete from test_case_follow where case_id=#{caseId}")
    void deleteByCaseId(String caseId);
}
