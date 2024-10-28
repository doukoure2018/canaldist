package com.dist.canal.mapper;

import org.springframework.beans.BeanUtils;
import com.dist.canal.entity.Role;
import com.dist.canal.entity.User;
import com.dist.canal.payload.RoleDto;
import com.dist.canal.payload.UserDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDTOMapper {

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    public static UserDto fromUser(User user, Role roles) {
        UserDto userDto  = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setRoleName(roles.getName());
        userDto.setPermissions(roles.getPermission());
        return userDto;
    }

    public static List<UserDto> fromUsers(List<User> users, Map<Long, RoleDto> rolesMap) {
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);

            // Get the RoleDto for the current user
            RoleDto roleDto = rolesMap.get(user.getId());
            if (roleDto != null) {
                userDto.setRoleName(roleDto.getName());
                userDto.setPermissions(roleDto.getPermission());
            }

            userDtos.add(userDto);
        }

        return userDtos;
    }

    public static User toUser(UserDto userDto) {
        User users = new User();
        BeanUtils.copyProperties(userDto, users);
        return users;
    }
}
