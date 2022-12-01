package cn.master.backend.controller;

import cn.master.backend.config.ResponseInfo;
import cn.master.backend.entity.TestCase;
import cn.master.backend.request.EditTestCaseRequest;
import cn.master.backend.service.TestCaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-23 09:38:23
 */
@RestController
@RequestMapping("/test/case/")
@RequiredArgsConstructor
public class TestCaseController {

    final TestCaseService service;
    @PostMapping("/save")
    public ResponseInfo<TestCase> saveTestCase(@RequestBody EditTestCaseRequest request) {
        TestCase testCase = service.addTestCase(request);
        return ResponseInfo.success(testCase);
    }

    @PostMapping("/list/{page}/{limit}")
    public ResponseInfo<Map<String, Object>> getProjectList(@PathVariable long limit, @PathVariable long page, @RequestBody TestCase request) {
        Page<TestCase> producePage = new Page<>(page, limit);
        IPage<TestCase> iPage = service.selectPageList(request, producePage);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("total",iPage.getTotal());
        result.put("records", iPage.getRecords());
        return ResponseInfo.success(result);
    }
}
