package cn.master.backend.service;

import cn.master.backend.config.ResponseInfo;
import cn.master.backend.dto.TestCaseTemplateDao;
import cn.master.backend.entity.TestCaseTemplate;
import cn.master.backend.request.BaseQueryRequest;
import cn.master.backend.request.UpdateCaseFieldTemplateRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-24 01:07:50
 */
public interface TestCaseTemplateService extends IService<TestCaseTemplate> {

    IPage<TestCaseTemplate> selectPageList(BaseQueryRequest request, Page<TestCaseTemplate> producePage);

    ResponseInfo<String> addTemplate(HttpServletRequest httpServletRequest, UpdateCaseFieldTemplateRequest request);

    TestCaseTemplateDao getTemplate(String projectId);
}
