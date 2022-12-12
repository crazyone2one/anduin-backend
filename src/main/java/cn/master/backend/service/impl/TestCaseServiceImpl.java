package cn.master.backend.service.impl;

import cn.master.backend.constants.TestCaseReviewStatus;
import cn.master.backend.entity.TestCase;
import cn.master.backend.entity.TestCaseFollow;
import cn.master.backend.entity.TestCaseNode;
import cn.master.backend.mapper.TestCaseFollowMapper;
import cn.master.backend.mapper.TestCaseMapper;
import cn.master.backend.mapper.TestCaseNodeMapper;
import cn.master.backend.request.EditTestCaseRequest;
import cn.master.backend.request.QueryTestCaseRequest;
import cn.master.backend.service.TestCaseService;
import cn.master.backend.util.JwtUtils;
import cn.master.backend.util.ServiceUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-23 09:38:23
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class TestCaseServiceImpl extends ServiceImpl<TestCaseMapper, TestCase> implements TestCaseService {

    final JwtUtils jwtUtils;
    final TestCaseNodeMapper testCaseNodeMapper;
    final TestCaseFollowMapper testCaseFollowMapper;
    @Override
    public TestCase addTestCase(EditTestCaseRequest request) {
        checkTestCaseExist(request);
        request.setNum(getNextNum(request.getProjectId()));
        if (StringUtils.isBlank(request.getCustomNum())) {
            request.setCustomNum(request.getNum().toString());
        }
        request.setReviewStatus(TestCaseReviewStatus.Prepare.name());
        request.setCreateUser(getCurrentUserId());
        setNode(request);
        request.setOrder(ServiceUtils.getNextOrder(request.getProjectId(), baseMapper::getLastOrder));
        request.setLatest(true);
        baseMapper.insert(request);
        saveFollows(request.getId(), request.getFollows());
        return request;
    }

    private void saveFollows(String id, List<String> follows) {
        testCaseFollowMapper.deleteByCaseId(id);
        if (CollectionUtils.isNotEmpty(follows)) {
            for (String follow : follows) {
                TestCaseFollow build = TestCaseFollow.builder().followId(follow).caseId(id).build();
                testCaseFollowMapper.insert(build);
            }
        }
    }

    @Override
    public IPage<TestCase> selectPageList(QueryTestCaseRequest request, Page<TestCase> producePage) {
        LambdaQueryWrapper<TestCase> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(CollectionUtils.isNotEmpty(request.getNodeIds()), TestCase::getNodeId, request.getNodeIds());
        return baseMapper.selectPage(Objects.nonNull(producePage) ? producePage : null, wrapper);
    }

    private void setNode(TestCase testCase) {
        if (StringUtils.isEmpty(testCase.getNodeId()) || "default-module".equals(testCase.getNodeId())) {
            List<TestCaseNode> nodes = testCaseNodeMapper.getAllByLabelList(testCase.getProjectId(), "未规划用例");
            if (CollectionUtils.isNotEmpty(nodes)) {
                testCase.setNodeId(nodes.get(0).getId());
                testCase.setNodePath("/" + nodes.get(0).getName());
            }
        }else {
            TestCaseNode testCaseNode = testCaseNodeMapper.selectById(testCase.getNodeId());
            if (StringUtils.isNotBlank(testCaseNode.getParentId())) {
                TestCaseNode parentNode = testCaseNodeMapper.selectById(testCaseNode.getParentId());
                testCase.setNodePath("/"+parentNode.getName()+"/"+testCaseNode.getName());
            }
        }
    }

    private String getCurrentUserId() {
        String token = jwtUtils.getJwtTokenFromRequest(ServiceUtils.getHttpServletRequest());
        return (String) jwtUtils.extractAllClaims(token).get("id");
    }
    private Integer getNextNum(String projectId) {
        TestCase testCase = baseMapper.getMaxNumByProjectId(projectId);
        if (Objects.isNull(testCase) || Objects.isNull(testCase.getNum())) {
            return 100001;
        } else {
            return Optional.of(testCase.getNum() + 1).orElse(100001);
        }
    }

    private TestCase checkTestCaseExist(TestCase testCase) {
        if (Objects.nonNull(testCase)) {
            String nodePath = testCase.getNodePath();
            if (!nodePath.startsWith("/")) {
                nodePath = "/" + nodePath;
            }
            if (nodePath.endsWith("/")) {
                nodePath = nodePath.substring(0, nodePath.length() - 1);
            }
            LambdaQueryWrapper<TestCase> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TestCase::getName, testCase.getName())
                    .eq(TestCase::getProjectId, testCase.getProjectId())
                    .eq(TestCase::getNodePath, nodePath)
                    .eq(TestCase::getType, testCase.getType())
                    .ne(TestCase::getStatus, "Trash");
            wrapper.eq(StringUtils.isNotBlank(testCase.getPriority()), TestCase::getPriority, testCase.getPriority());
            wrapper.ne(StringUtils.isNotBlank(testCase.getId()), TestCase::getId,testCase.getId());
            List<TestCase> caseList = baseMapper.selectList(wrapper);
            if (CollectionUtils.isNotEmpty(caseList)) {
                String caseRemark = testCase.getRemark() == null ? StringUtils.EMPTY : testCase.getRemark();
                String caseSteps = testCase.getSteps() == null ? StringUtils.EMPTY : testCase.getSteps();
                String casePrerequisite = testCase.getPrerequisite() == null ? StringUtils.EMPTY : testCase.getPrerequisite();
                for (TestCase tc : caseList) {
                    String steps = tc.getSteps() == null ? StringUtils.EMPTY : tc.getSteps();
                    String remark = tc.getRemark() == null ? StringUtils.EMPTY : tc.getRemark();
                    String prerequisite = tc.getPrerequisite() == null ? StringUtils.EMPTY : tc.getPrerequisite();
                    if (StringUtils.equals(steps, caseSteps) && StringUtils.equals(remark, caseRemark) && StringUtils.equals(prerequisite, casePrerequisite)) {
                        return tc;
                    }
                }
            }
        }
        return null;
    }
}
