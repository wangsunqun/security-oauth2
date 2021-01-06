package com.wsq.security.oauth2.security.dao;

import com.wsq.security.oauth2.security.entity.User;

/**
 * TODO
 *
 * @author wsq
 * 2021/1/6 19:50
 */
public interface UserMapper {
    User getUserByName(String userName);
}
