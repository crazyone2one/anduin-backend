package cn.master.backend.service;

import cn.master.backend.config.ResponseInfo;
import cn.master.backend.entity.SysProject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-21 04:19:58
 */
public interface SysProjectService extends IService<SysProject> {

    /**
     * 查询列表数据
     *
     * @param project 查询条件
     * @param page    为null时返回list，有值时返回带分页的list
     * @return java.util.List<cn.master.backend.entity.SysProject>
     */
    IPage<SysProject> selectPageList(SysProject project, IPage<SysProject> page);

    /**
     * 添加项目数据
     *
     * @param project 参数
     * @return cn.master.backend.config.ResponseInfo<cn.master.backend.entity.SysProject>
     */
    ResponseInfo<SysProject> addProject(SysProject project);

    /**
     * 更新项目信息
     *
     * @param project 参数
     * @return int
     */
    int updateProject(SysProject project);
}
