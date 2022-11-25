package cn.master.backend.controller;

import cn.master.backend.config.ResponseInfo;
import cn.master.backend.dto.CustomFieldTemplateDao;
import cn.master.backend.entity.CustomFieldTemplate;
import cn.master.backend.service.CustomFieldTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-24 01:05:24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/custom/field/template")
public class CustomFieldTemplateController {

    final CustomFieldTemplateService service;

    @PostMapping("/list")
    public ResponseInfo<List<CustomFieldTemplateDao>> list(@RequestBody CustomFieldTemplate request) {
        List<CustomFieldTemplateDao> result =  service.listTemplate(request);
        return ResponseInfo.success(result);
    }
}
