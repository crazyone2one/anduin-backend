package cn.master.backend.controller;

import cn.master.backend.controller.request.AuthenticateRequest;;
import cn.master.backend.service.MyUserDetailsService;
import cn.master.backend.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-18 01:46:40
 */
@RestController
@RequestMapping("/sys-user")
@RequiredArgsConstructor
public class SysUserController {
    final MyUserDetailsService userDetailsService;
    final AuthenticationManager authenticationManager;
    final JwtUtils jwtUtils;

    @PostMapping("/login")
    public String login(@RequestBody AuthenticateRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
        return jwtUtils.generateToken(userDetails);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
