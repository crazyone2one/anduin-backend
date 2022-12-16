package cn.master.backend.service;

import cn.master.backend.config.ResponseInfo;
import cn.master.backend.entity.SysProject;
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

    /**
     * 添加节点
     *
     * @param httpServletRequest HttpServletRequest
     * @param node               节点参数
     * @return cn.master.backend.config.ResponseInfo<java.lang.String>
     */
    ResponseInfo<String> addNode(HttpServletRequest httpServletRequest, TestCaseNode node);

    /**
     * 编辑节点
     *
     * @param node 节点参数
     * @return cn.master.backend.entity.TestCaseNode
     */
    TestCaseNode editNode(TestCaseNode node);

    /**
     * 删除节点
     *
     * @param nodeIds 节点id
     * @return cn.master.backend.config.ResponseInfo<java.lang.String>
     */
    ResponseInfo<String> deleteNode(List<String> nodeIds);

    /**
     * 更新节点父级节点名称
     *
     * @param sysProject 项目参数
     */
    void updateNameByProject(SysProject sysProject);
}
