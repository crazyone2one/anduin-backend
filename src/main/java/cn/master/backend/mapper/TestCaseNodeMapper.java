package cn.master.backend.mapper;

import cn.master.backend.entity.TestCaseNode;
import cn.master.backend.request.QueryTestCaseRequest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-23 10:09:11
 */
@Mapper
public interface TestCaseNodeMapper extends BaseMapper<TestCaseNode> {

    /**
     * 查询node关联测试用例数量
     *
     * @param request 查询参数
     * @return java.util.List<cn.master.backend.entity.TestCaseNode>
     */
    List<TestCaseNode> getCountNodes(@Param("request") QueryTestCaseRequest request);

    /**
     * 根据项目id查询对应的模块
     *
     * @param projectId 项目id
     * @return java.util.List<cn.master.security.entity.TestCaseNode>
     */
    @Select("select id, project_id, `name`, parent_id, `level`, create_time, update_time, pos, create_user from test_case_node where test_case_node.project_id = #{projectId}  order by pos asc")
    List<TestCaseNode> getNodeTreeByProjectId(@Param("projectId") String projectId);

    @Select("select * from test_case_node where test_case_node.project_id = #{projectId} and test_case_node.name=#{nodeName} order by pos asc")
    List<TestCaseNode> getAllByLabelList(@Param("projectId") String projectId, @Param("nodeName") String nodeName);
}
