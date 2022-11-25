package cn.master.backend.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author create by 11's papa on 2022-11-22
 */
@Setter
@Getter
public class QueryCustomFieldRequest extends BaseQueryRequest{
    private String templateId;
    private String workspaceId;
    private String scene;
    private List<String> templateContainIds;
}
