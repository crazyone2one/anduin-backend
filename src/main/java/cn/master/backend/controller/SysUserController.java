package cn.master.backend.controller;

import cn.master.backend.config.ResponseInfo;
import cn.master.backend.controller.request.AuthenticateRequest;
import cn.master.backend.entity.SysUser;
import cn.master.backend.security.UserDetailsServiceImpl;
import cn.master.backend.service.SysUserService;
import cn.master.backend.util.JwtUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author 11's papa
 * @since 2022-11-18 01:46:40
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SysUserController {
    final UserDetailsServiceImpl userDetailsService;
    final AuthenticationManager authenticationManager;
    final JwtUtils jwtUtils;
    final SysUserService sysUserService;

    @PostMapping("/login")
    public String login(@RequestBody AuthenticateRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getName());
        return jwtUtils.generateToken(userDetails);
    }

    @PostMapping("/register")
    public ResponseInfo<SysUser> registerUser(@RequestBody SysUser sysUser) {
        SysUser user = sysUserService.addUser(sysUser);
        return ResponseInfo.success(user);
    }

    @PostMapping("/list/{page}/{limit}")
    public ResponseInfo<Map<String, Object>> loadUserList(@RequestBody SysUser user, @PathVariable long page, @PathVariable long limit) {
        Page<SysUser> producePage = new Page<>(page, limit);
        List<SysUser> sysUserList = sysUserService.selectPageVo(user, producePage);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("total", sysUserList.size());
        result.put("records", sysUserList);
        return ResponseInfo.success(result);
    }
}
