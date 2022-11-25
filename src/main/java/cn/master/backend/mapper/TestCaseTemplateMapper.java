package cn.master.backend.mapper;

import cn.master.backend.entity.TestCaseTemplate;
import cn.master.backend.request.BaseQueryRequest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-24 01:07:50
 */
public interface TestCaseTemplateMapper extends BaseMapper<TestCaseTemplate> {

    IPage<TestCaseTemplate> listAll(@Param("request") BaseQueryRequest request, Page<TestCaseTemplate> producePage);
}
