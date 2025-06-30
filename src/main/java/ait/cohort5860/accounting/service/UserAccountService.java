package ait.cohort5860.accounting.service;

import ait.cohort5860.accounting.dto.RolesDto;
import ait.cohort5860.accounting.dto.UserDto;
import ait.cohort5860.accounting.dto.UserEditDto;
import ait.cohort5860.accounting.dto.UserRegisterDto;

public interface UserAccountService {
    UserDto register(UserRegisterDto userRegisterDto);

    UserDto getUser(String login);

    UserDto removeUser(String login);

    UserDto updateUser(String login, UserEditDto userEditDto);

    RolesDto changeRolesList(String login, String role, boolean isAddRole);

    void changePassword(String login, String newPassword);
}
