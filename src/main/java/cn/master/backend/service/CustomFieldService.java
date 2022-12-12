package cn.master.backend.service;

import cn.master.backend.config.ResponseInfo;
import cn.master.backend.entity.CustomField;
import cn.master.backend.request.QueryCustomFieldRequest;
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
 * @since 2022-11-22 04:17:38
 */
public interface CustomFieldService extends IService<CustomField> {

    CustomField add(HttpServletRequest httpServletRequest, CustomField customField);

    /**
     * 分页查询
     *
     * @param page    分页条件
     * @param request 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<cn.master.backend.entity.CustomField>
     */
    IPage<CustomField> selectPageList(IPage<CustomField> page, QueryCustomFieldRequest request);

    ResponseInfo<String> updateCustomField(HttpServletRequest httpServletRequest, CustomField customField);

    IPage<CustomField> listRelate(Page<CustomField> producePage, QueryCustomFieldRequest request);
}
