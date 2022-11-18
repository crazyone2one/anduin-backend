package cn.master.backend.controller.request;

import lombok.Data;
import lombok.Getter;

/**
 * @author create by 11's papa on 2022-11-18
 */
@Data
public class AuthenticateRequest {
    private String userName;
    private String password;
}
