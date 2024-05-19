package com.tmquang.score.security.services;

import com.tmquang.score.models.Role;
import com.tmquang.score.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public List<Role> getAll(){
        List<Role> roleList = roleRepository.findAll();
        return roleList;
    }
}
