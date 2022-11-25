package cn.master.backend.controller;

import cn.master.backend.config.ResponseInfo;
import cn.master.backend.dto.TestCaseTemplateDao;
import cn.master.backend.entity.TestCaseTemplate;
import cn.master.backend.request.BaseQueryRequest;
import cn.master.backend.request.UpdateCaseFieldTemplateRequest;
import cn.master.backend.service.TestCaseTemplateService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-24 01:07:50
 */
@RestController
@RequestMapping("/field/template/case")
@RequiredArgsConstructor
public class TestCaseTemplateController {

    final TestCaseTemplateService service;

    @PostMapping("/list/{page}/{limit}")
    public ResponseInfo<Map<String, Object>> list(@RequestBody BaseQueryRequest request,@PathVariable long page, @PathVariable long limit) {
        Page<TestCaseTemplate> producePage = new Page<>(page, limit);
        IPage<TestCaseTemplate> iPage = service.selectPageList(request, producePage);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("total",iPage.getTotal());
        result.put("records", iPage.getRecords());
        return ResponseInfo.success(result);
    }

    @PostMapping("/add")
    public ResponseInfo<String> add(HttpServletRequest httpServletRequest, @RequestBody UpdateCaseFieldTemplateRequest request) {
        return service.addTemplate(httpServletRequest,request);
    }

    @GetMapping("/get/relate/{projectId}")
    public ResponseInfo<TestCaseTemplateDao> getTemplate(@PathVariable String projectId) {
        TestCaseTemplateDao templateDao = service.getTemplate(projectId);
        return ResponseInfo.success(templateDao);
    }
}
