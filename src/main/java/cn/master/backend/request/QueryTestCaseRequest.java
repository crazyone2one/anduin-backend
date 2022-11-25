package cn.master.backend.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author create by 11's papa on 2022-11-23
 */
@Setter
@Getter
public class QueryTestCaseRequest extends BaseQueryRequest{
    private String userId;
}
