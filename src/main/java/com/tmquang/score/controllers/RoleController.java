package com.tmquang.score.controllers;

import com.tmquang.score.models.Role;
import com.tmquang.score.security.services.RoleService;
import com.tmquang.score.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @GetMapping("/all")
    public ApiResponse<?> getAll(){
        List<Role> roleList = roleService.getAll();
        return new ApiResponse<Role>(true, roleList,  "Get role list successful.");
    }
}
