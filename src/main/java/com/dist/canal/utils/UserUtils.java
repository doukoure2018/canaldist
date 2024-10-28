package com.dist.canal.utils;

import org.springframework.security.core.Authentication;
import com.dist.canal.payload.UserDto;
import com.dist.canal.payload.UserPrincipal;

public class UserUtils {
    public static UserDto getAuthenticatedUser(Authentication authentication){
         return ((UserDto) authentication.getPrincipal());
    }

    public static UserDto getLoggedInUser(Authentication authentication) {
        return ((UserPrincipal) authentication.getPrincipal()).getUser();
    }
}
