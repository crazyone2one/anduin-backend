package cn.master.backend.service.impl;

import cn.master.backend.config.ResponseInfo;
import cn.master.backend.constants.TestCaseConstants;
import cn.master.backend.entity.TestCase;
import cn.master.backend.entity.TestCaseNode;
import cn.master.backend.exception.CustomException;
import cn.master.backend.mapper.TestCaseMapper;
import cn.master.backend.mapper.TestCaseNodeMapper;
import cn.master.backend.request.QueryTestCaseRequest;
import cn.master.backend.service.TestCaseNodeService;
import cn.master.backend.util.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-23 10:09:11
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class TestCaseNodeServiceImpl extends ServiceImpl<TestCaseNodeMapper, TestCaseNode> implements TestCaseNodeService {

    final JwtUtils jwtUtils;
    final TestCaseMapper testCaseMapper;
    @Override
    public List<TestCaseNode> queryNodeTreeByProjectId(HttpServletRequest httpServletRequest, String projectId, QueryTestCaseRequest request) {
        getDefaultNode(httpServletRequest, projectId);
        request.setProjectId(projectId);
//        String token = jwtUtils.getJwtTokenFromRequest(httpServletRequest);
//        request.setUserId(jwtUtils.extractAllClaims(token).getId());
        List<TestCaseNode> countNodes = baseMapper.getCountNodes(request);
        List<TestCaseNode> testCaseNodes = baseMapper.getNodeTreeByProjectId(projectId);
        return getNodeTrees(testCaseNodes, getCountMap(countNodes));
    }

    private List<TestCaseNode> getNodeTrees(List<TestCaseNode> nodes, Map<String, Integer> countMap) {
        List<TestCaseNode> nodeTreeList = new ArrayList<>();
        Map<Integer, List<TestCaseNode>> nodeLevelMap = new HashMap<>();
        nodes.forEach(node -> {
            Integer level = node.getLevel();
            if (nodeLevelMap.containsKey(level)) {
                nodeLevelMap.get(level).add(node);
            } else {
                List<TestCaseNode> testCaseNodes = new ArrayList<>();
                testCaseNodes.add(node);
                nodeLevelMap.put(node.getLevel(), testCaseNodes);
            }
        });
        List<TestCaseNode> rootNodes = Optional.ofNullable(nodeLevelMap.get(1)).orElse(new ArrayList<>());
        rootNodes.forEach(rootNode -> nodeTreeList.add(buildNodeTree(nodeLevelMap, rootNode, countMap)));
        return nodeTreeList;
    }

    private TestCaseNode buildNodeTree(Map<Integer, List<TestCaseNode>> nodeLevelMap, TestCaseNode rootNode, Map<String, Integer> countMap) {
        TestCaseNode nodeTree = new TestCaseNode();
        BeanUtils.copyProperties(rootNode, nodeTree);
        nodeTree.setLabel(rootNode.getName());
        setCaseNum(countMap, nodeTree);
        List<TestCaseNode> lowerNodes = nodeLevelMap.get(rootNode.getLevel() + 1);
        if (lowerNodes == null) {
            return nodeTree;
        }
        List<TestCaseNode> children = Optional.ofNullable(nodeTree.getChildren()).orElse(new ArrayList<>());
        lowerNodes.forEach(node -> {
            if (Objects.nonNull(node.getParentId()) && node.getParentId().equals(rootNode.getId())) {
                children.add(buildNodeTree(nodeLevelMap, node, countMap));
                if (Objects.nonNull(countMap)) {
                    Integer childrenCount = children.stream().map(TestCaseNode::getCaseNum).reduce(Integer::sum).get();
                    nodeTree.setCaseNum(nodeTree.getCaseNum() + childrenCount);
                }
                nodeTree.setChildren(children);
            }
        });
        return nodeTree;
    }
    private void setCaseNum(Map<String, Integer> countMap, TestCaseNode nodeTree) {
        if (countMap != null) {
            if (countMap.get(nodeTree.getId()) != null) {
                nodeTree.setCaseNum(countMap.get(nodeTree.getId()));
            } else {
                nodeTree.setCaseNum(0);
            }
        }
    }
    private Map<String,Integer> getCountMap(List<TestCaseNode> nodes) {
        return nodes.stream().collect(Collectors.toMap(TestCaseNode::getId, TestCaseNode::getCaseNum));
    }

    private void getDefaultNode(HttpServletRequest httpServletRequest, String projectId) {
        LambdaQueryWrapper<TestCaseNode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TestCaseNode::getProjectId, projectId)
                .eq(TestCaseNode::getName, "未规划用例").isNull(TestCaseNode::getParentId);
        List<TestCaseNode> list = baseMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(list)) {
            String token = jwtUtils.getJwtTokenFromRequest(httpServletRequest);
            Claims claims = jwtUtils.extractAllClaims(token);
            TestCaseNode testCaseNode = TestCaseNode.builder().name("未规划用例").pos(1.0).level(1).projectId(projectId)
                    .createUser((String) claims.get("id")).build();
            baseMapper.insert(testCaseNode);
//            return testCaseNode;
        }
//        return list.get(0);
    }

    @Override
    public ResponseInfo<String> addNode(HttpServletRequest httpServletRequest, TestCaseNode node) {
        validateNode(node);
        String token = jwtUtils.getJwtTokenFromRequest(httpServletRequest);
        node.setCreateUser(jwtUtils.extractAllClaims(token).getId());
        double pos = getNextLevelPos(node.getProjectId(), node.getLevel(), node.getParentId());
        node.setPos(pos);
        baseMapper.insert(node);
        return ResponseInfo.success(node.getId());
    }

    private double getNextLevelPos(String projectId, Integer level, String parentId) {
        List<TestCaseNode> list = getPos(projectId, level, parentId);
        if (!CollectionUtils.isEmpty(list) && Objects.nonNull(list.get(0))) {
            return list.get(0).getPos() + TestCaseConstants.DEFAULT_POS;
        } else {
            return TestCaseConstants.DEFAULT_POS;
        }
    }

    private List<TestCaseNode> getPos(String projectId, Integer level, String parentId) {
        LambdaQueryWrapper<TestCaseNode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TestCaseNode::getProjectId, projectId);
        if (level != 1 && StringUtils.isNotBlank(parentId)) {
            wrapper.eq(TestCaseNode::getParentId, parentId);
        }
        wrapper.orderByDesc(TestCaseNode::getPos);
        return baseMapper.selectList(wrapper);
    }

    private void validateNode(TestCaseNode node) {
        if (node.getLevel() > TestCaseConstants.MAX_NODE_DEPTH) {
            throw new RuntimeException("模块树最大深度为" + TestCaseConstants.MAX_NODE_DEPTH + "层");
        }
        checkTestCaseNodeExist(node);
    }

    private void checkTestCaseNodeExist(TestCaseNode node) {
        if (Objects.nonNull(node.getName())) {
            LambdaQueryWrapper<TestCaseNode> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TestCaseNode::getName, node.getName())
                    .eq(TestCaseNode::getProjectId, node.getProjectId());
            if (StringUtils.isNotBlank(node.getParentId())) {
                wrapper.eq(TestCaseNode::getParentId, node.getParentId());
            } else {
                wrapper.eq(TestCaseNode::getLevel, node.getLevel());
            }
            wrapper.ne(StringUtils.isNotBlank(node.getId()), TestCaseNode::getId, node.getId());
            if (baseMapper.exists(wrapper)) {
                throw new CustomException("同层级下已存在该模块名称");
            }
        }
    }

    @Override
    public TestCaseNode editNode(TestCaseNode node) {
        checkTestCaseNodeExist(node);
        if (CollectionUtils.isNotEmpty(node.getNodeIds())) {
            LambdaQueryWrapper<TestCase> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(TestCase::getId, TestCase::getNodeId, TestCase::getNodePath).in(TestCase::getNodeId, node.getNodeIds());
            List<TestCase> testCases = testCaseMapper.selectList(wrapper);
            testCases.forEach(testCase -> {
                StringBuilder path = new StringBuilder(testCase.getNodePath());
                List<String> pathLists = Arrays.asList(path.toString().split("/"));
                pathLists.set(node.getLevel(), node.getName());
                path.delete(0, path.length());
                for (int i = 1; i < pathLists.size(); i++) {
                    path.append("/").append(pathLists.get(i));
                }
                testCase.setNodePath(path.toString());
            });
            testCases.forEach(testCaseMapper::updateById);
        }
        baseMapper.updateById(node);
        return node;
    }

    @Override
    public ResponseInfo<String> deleteNode(List<String> nodeIds) {
        return null;
    }
}
