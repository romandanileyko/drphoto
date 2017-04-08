package ru.danileyko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danileyko.dao.RoleDAO;
import ru.danileyko.model.Role;

/**
 * Created by danil on 24.02.2017.
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDAO roleDAO;

    @Override
    public Role getRole(int id) {
            return roleDAO.getRole(id);
        }
}
