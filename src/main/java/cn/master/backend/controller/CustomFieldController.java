package cn.master.backend.controller;

import cn.master.backend.config.ResponseInfo;
import cn.master.backend.entity.CustomField;
import cn.master.backend.request.QueryCustomFieldRequest;
import cn.master.backend.service.CustomFieldService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-22 04:17:38
 */
@RestController
@RequestMapping("/custom/field")
@RequiredArgsConstructor
public class CustomFieldController {
    final CustomFieldService service;

    @PostMapping("/add")
    public ResponseInfo<CustomField> add(HttpServletRequest httpServletRequest, @RequestBody CustomField customField) {
        CustomField field = service.add(httpServletRequest, customField);
        return ResponseInfo.success(field);
    }

    @PostMapping("/list/{page}/{limit}")
    public ResponseInfo<Map<String, Object>> list(@RequestBody QueryCustomFieldRequest request, @PathVariable long page, @PathVariable long limit) {
        Page<CustomField> producePage = new Page<>(page, limit);
        IPage<CustomField> iPage = service.selectPageList(producePage, request);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("total", iPage.getTotal());
        result.put("records", iPage.getRecords());
        return ResponseInfo.success(result);
    }

    @PostMapping("/list/relate/{page}/{limit}")
    public ResponseInfo<Map<String, Object>> listRelate(@RequestBody QueryCustomFieldRequest request, @PathVariable long page, @PathVariable long limit) {
        Page<CustomField> producePage = new Page<>(page, limit);
        IPage<CustomField> iPage = service.listRelate(producePage, request);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("total", iPage.getTotal());
        result.put("records", iPage.getRecords());
        return ResponseInfo.success(result);
    }

    @PostMapping("/update")
    public ResponseInfo<String> update(HttpServletRequest httpServletRequest,@RequestBody CustomField customField) {
        return service.updateCustomField(httpServletRequest, customField);
    }
}
