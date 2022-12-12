package cn.master.backend.request;

import lombok.Data;

import java.util.List;

/**
 * @author create by 11's papa on 2022-11-21
 */
@Data
public class BaseQueryRequest {
    private String projectId;
    private String name;

    /**
     * 排序条件
     */
    private List<OrderRequest> orders;
    private List<String> nodeIds;
}
