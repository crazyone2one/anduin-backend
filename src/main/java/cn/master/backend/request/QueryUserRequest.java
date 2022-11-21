package cn.master.backend.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author create by 11's papa on 2022-11-21
 */
@Getter
@Setter
public class QueryUserRequest extends BaseQueryRequest {
    private List<Long> ids;
    private Boolean userStatus;
}
