package cn.master.backend.service;

import cn.master.backend.entity.TestCase;
import cn.master.backend.request.EditTestCaseRequest;
import cn.master.backend.request.QueryTestCaseRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-23 09:38:23
 */
public interface TestCaseService extends IService<TestCase> {

    /**
     * 添加测试用例数据
     *
     * @param request 参数
     * @return cn.master.backend.entity.TestCase
     */
    TestCase addTestCase(EditTestCaseRequest request);
    IPage<TestCase> selectPageList(QueryTestCaseRequest request, Page<TestCase> producePage);
}
