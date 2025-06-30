package ait.cohort5860.accounting.service;

import ait.cohort5860.accounting.dto.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface AccountingService {

    @Transactional
    AccountResponseDto register(RegisterDto dto);

    AccountResponseDto findByLogin(String login);

    @Transactional
    AccountResponseDto updateUser(String login, UpdateUserDto dto);

    @Transactional
    AccountResponseDto addRole(String login, String role);

    @Transactional
    AccountResponseDto removeRole(String login, String role);

    @Transactional
    AccountResponseDto delete(String login);

    @Transactional
    void changePassword(String oldPassword, String newPassword);
}