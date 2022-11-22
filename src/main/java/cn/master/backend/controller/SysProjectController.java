package cn.master.backend.controller;

import cn.master.backend.config.ResponseInfo;
import cn.master.backend.entity.SysProject;
import cn.master.backend.service.SysProjectService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-21 04:19:58
 */
@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class SysProjectController {
    final SysProjectService sysProjectService;

    @PostMapping("/list/related")
    public ResponseInfo<List<SysProject>> getRelated(@RequestBody SysProject project) {
        Page<SysProject> producePage = new Page<>(1, 10000);
        IPage<SysProject> iPage = sysProjectService.selectPageList(project, producePage);
        return ResponseInfo.success(iPage.getRecords());
    }

    @PostMapping("/list/{page}/{limit}")
    public ResponseInfo<Map<String, Object>> getProjectList(@PathVariable long limit, @PathVariable long page, @RequestBody SysProject request) {
        Page<SysProject> producePage = new Page<>(page, limit);
        IPage<SysProject> iPage = sysProjectService.selectPageList(request, producePage);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("total",iPage.getTotal());
        result.put("records", iPage.getRecords());
        return ResponseInfo.success(result);
    }
    @PostMapping("/add")
    public ResponseInfo<SysProject> addProject(@RequestBody SysProject project) {
        return sysProjectService.addProject(project);
    }

    @PostMapping("/update")
    public ResponseInfo<Integer> updateProject(@RequestBody SysProject project) {
        return ResponseInfo.success(sysProjectService.updateProject(project));
    }
}
