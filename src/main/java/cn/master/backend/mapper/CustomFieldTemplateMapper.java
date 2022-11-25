package cn.master.backend.mapper;

import cn.master.backend.dto.CustomFieldTemplateDao;
import cn.master.backend.entity.CustomFieldTemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-24 01:05:24
 */
public interface CustomFieldTemplateMapper extends BaseMapper<CustomFieldTemplate> {

    Long getLastOrder(@Param("templateId") String templateId, @Param("baseOrder") Long baseOrder);
    List<CustomFieldTemplateDao> listByRequest(@Param("request") CustomFieldTemplate request);
}
