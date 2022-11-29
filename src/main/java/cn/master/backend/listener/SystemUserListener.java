package cn.master.backend.listener;

import cn.master.backend.entity.SysUser;
import cn.master.backend.service.SysUserService;
import cn.master.backend.util.JacksonUtils;
import cn.master.backend.util.ServiceUtils;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author create by 11's papa on 2022-11-21
 */
@Slf4j
@RequiredArgsConstructor
public class SystemUserListener implements ReadListener<SysUser> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    private List<SysUser> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    final SysUserService sysUserService;
    final boolean updateSupport;

    @Override
    public void invoke(SysUser sysUser, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JacksonUtils.beanToJson(sysUser));
        cachedDataList.add(sysUser);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    private void saveData() {
        HttpServletRequest httpServletRequest = ServiceUtils.getHttpServletRequest();
        sysUserService.importUser(httpServletRequest, cachedDataList, updateSupport);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }
}
