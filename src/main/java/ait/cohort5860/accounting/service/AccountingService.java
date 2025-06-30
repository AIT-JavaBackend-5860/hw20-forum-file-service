package ait.cohort5860.accounting.service;

import ait.cohort5860.accounting.dto.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface AccountingService {

    @Transactional
    UserDto register(UserRegisterDto dto);

    UserDto findByLogin(String login);

    @Transactional
    UserDto updateUser(String login, UserEditDto dto);

    @Transactional
    UserDto addRole(String login, String role);

    @Transactional
    UserDto removeRole(String login, String role);

    @Transactional
    UserDto delete(String login);

    @Transactional
    void changePassword(String oldPassword, String newPassword);
}