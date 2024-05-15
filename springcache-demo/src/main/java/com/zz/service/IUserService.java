package com.zz.service;

import com.zz.service.impl.UserServiceImpl.User;
import java.util.List;

public interface IUserService {

    Long add(User user);

    User get(Long id);

    List<User> list(Long... ids);

    List<User> list(List<Long> ids);

    List<User> listAll();

    User getById(Long id);

    void updateById(User user);
}
