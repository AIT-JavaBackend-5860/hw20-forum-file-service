package ait.cohort5860.accounting.service;

import ait.cohort5860.accounting.dto.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface AccountingService {

    @Transactional
    UserDto register(UserRegisterDto dto);

    UserDto getUser(String login);

    @Transactional
    UserDto updateUser(String login, UserEditDto dto);


    @Transactional
    UserDto removeUser(String login);

    @Transactional
    void changePassword(String oldPassword, String newPassword);

    @Transactional
    RolesDto changeRolesList(String login, String role, boolean isAddRole);

    void sendEmail(EmailDto emailDto);
}