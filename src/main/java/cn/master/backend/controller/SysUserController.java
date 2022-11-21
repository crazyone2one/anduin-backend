package cn.master.backend.controller;

import cn.master.backend.config.ResponseInfo;
import cn.master.backend.controller.request.AuthenticateRequest;
import cn.master.backend.entity.SysUser;
import cn.master.backend.listener.SystemUserListener;
import cn.master.backend.request.QueryUserRequest;
import cn.master.backend.security.SecurityUser;
import cn.master.backend.security.UserDetailsServiceImpl;
import cn.master.backend.service.SysUserService;
import cn.master.backend.util.JwtUtils;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseInfo<SecurityUser> login(@RequestBody AuthenticateRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword()));
        final SecurityUser userDetails = userDetailsService.loadUserByUsername(request.getName());
        userDetails.setToken(jwtUtils.generateToken(userDetails));
        return ResponseInfo.success(userDetails);
    }

    @PostMapping("/register")
    public ResponseInfo<SysUser> registerUser(@RequestBody SysUser sysUser) {
        SysUser user = sysUserService.addUser(sysUser);
        return ResponseInfo.success(user);
    }

    @PostMapping("/list/{page}/{limit}")
    public ResponseInfo<Map<String, Object>> loadUserList(@RequestBody QueryUserRequest user, @PathVariable long page, @PathVariable long limit) {
        Page<SysUser> producePage = new Page<>(page, limit);
        List<SysUser> sysUserList = sysUserService.selectPageVo(user, producePage);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("total", sysUserList.size());
        result.put("records", sysUserList);
        return ResponseInfo.success(result);
    }

    @PostMapping("/download")
    public void downloadUser(HttpServletResponse response, QueryUserRequest queryUserRequest) throws IOException {
        sysUserService.download(response, true, queryUserRequest);
    }

    @PostMapping("/download/template")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        sysUserService.download(response, false, null);
    }

    @PostMapping("/import")
    public ResponseInfo<String> upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), SysUser.class, new SystemUserListener(sysUserService)).sheet().doRead();
        return ResponseInfo.success();
    }
}
