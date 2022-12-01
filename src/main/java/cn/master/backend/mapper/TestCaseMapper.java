package cn.master.backend.mapper;

import cn.master.backend.entity.TestCase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-23 09:38:23
 */
public interface TestCaseMapper extends BaseMapper<TestCase> {
    @Select("SELECT * FROM test_case WHERE test_case.project_id = #{projectId} ORDER BY num DESC LIMIT 1")
    TestCase getMaxNumByProjectId(@Param("projectId") String projectId);

    @Select("select `order` from test_case where project_id = #{projectId} and `order` > #{baseOrder} order by `order` desc limit 1;")
    Long getLastOrder(@Param("projectId") String projectId, @Param("baseOrder") Long baseOrder);
}
