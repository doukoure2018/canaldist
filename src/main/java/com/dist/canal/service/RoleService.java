package com.dist.canal.service;

import com.dist.canal.payload.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto getRoleByUserId(Long id);
    List<RoleDto> getRoles();
}
