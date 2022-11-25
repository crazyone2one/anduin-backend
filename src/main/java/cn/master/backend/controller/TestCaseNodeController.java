package cn.master.backend.controller;

import cn.master.backend.config.ResponseInfo;
import cn.master.backend.entity.TestCaseNode;
import cn.master.backend.request.QueryTestCaseRequest;
import cn.master.backend.service.TestCaseNodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-23 10:09:11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/case/node")
public class TestCaseNodeController {
    final TestCaseNodeService service;

    @PostMapping("/list/{projectId}")
    public ResponseInfo<List<TestCaseNode>> getNodeByCondition(HttpServletRequest httpServletRequest, @PathVariable String projectId, @RequestBody(required = false) QueryTestCaseRequest request) {
        List<TestCaseNode> nodes = service.queryNodeTreeByProjectId(httpServletRequest, projectId, Optional.ofNullable(request).orElse(new QueryTestCaseRequest()));
        return ResponseInfo.success(nodes);
    }

    @PostMapping("/add")
    public ResponseInfo<String> addNode(HttpServletRequest httpServletRequest, @RequestBody TestCaseNode node) {
        return service.addNode(httpServletRequest, node);
    }

    @PostMapping("/edit")
    public ResponseInfo<TestCaseNode> editNode(@RequestBody TestCaseNode node) {
        TestCaseNode testCaseNode = service.editNode(node);
        return ResponseInfo.success(testCaseNode);
    }

    @PostMapping("/delete")
    public ResponseInfo<String> deleteNode(@RequestBody List<String> nodeIds) {
        return service.deleteNode(nodeIds);
    }
}
