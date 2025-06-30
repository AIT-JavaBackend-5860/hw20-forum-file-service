package ait.cohort5860.accounting.service;

import ait.cohort5860.accounting.dto.AccountResponseDto;
import ait.cohort5860.accounting.dto.RegisterDto;
import ait.cohort5860.accounting.dto.UpdateUserDto;
import org.springframework.stereotype.Service;

@Service
public class AccountingServiceImpl implements AccountingService {

    @Override
    public AccountResponseDto register(RegisterDto dto) {
        return null;
    }

    @Override
    public AccountResponseDto findByLogin(String login) {
        return null;
    }

    @Override
    public AccountResponseDto updateUser(String login, UpdateUserDto dto) {
        return null;
    }

    @Override
    public AccountResponseDto addRole(String login, String role) {
        return null;
    }

    @Override
    public AccountResponseDto removeRole(String login, String role) {
        return null;
    }

    @Override
    public AccountResponseDto delete(String login) {
        return null;
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }
}
