package cn.master.backend.service;

import cn.master.backend.config.ResponseInfo;
import cn.master.backend.entity.TestCaseNode;
import cn.master.backend.request.QueryTestCaseRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-23 10:09:11
 */
public interface TestCaseNodeService extends IService<TestCaseNode> {

    /**
     * 根据项目查询对于的node数据
     *
     * @param httpServletRequest HttpServletRequest
     * @param projectId          项目id
     * @param request            其他参数
     * @return java.util.List<cn.master.backend.entity.TestCaseNode>
     */
    List<TestCaseNode> queryNodeTreeByProjectId(HttpServletRequest httpServletRequest, String projectId, QueryTestCaseRequest request);

    ResponseInfo<String> addNode(HttpServletRequest httpServletRequest, TestCaseNode node);

    TestCaseNode editNode(TestCaseNode node);

    ResponseInfo<String> deleteNode(List<String> nodeIds);
}
