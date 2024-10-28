package com.dist.canal.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.dist.canal.entity.Role;
import com.dist.canal.payload.RoleDto;
import com.dist.canal.repository.RoleRepository;
import com.dist.canal.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository rolesRepository;
    private final ModelMapper mapper;

    @Override
    public RoleDto getRoleByUserId(Long id) {
        log.info("Fetching role for user Id : "+ id);
        return rolesRepository.getRoleByUserId(id);
    }

    @Override
    public List<RoleDto> getRoles() {
        List<Role> roles=rolesRepository.findAll();
        return roles.stream().map(roles1 -> mapper.map(roles1,RoleDto.class)).collect(Collectors.toList());
    }


}

