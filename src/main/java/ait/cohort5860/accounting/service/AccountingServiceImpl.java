package ait.cohort5860.accounting.service;

import ait.cohort5860.accounting.dao.UserAccountRepository;
import ait.cohort5860.accounting.dto.*;
import ait.cohort5860.accounting.dto.exception.UserExistsException;
import ait.cohort5860.accounting.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountingServiceImpl implements AccountingService {

    private final UserAccountRepository userAccountRepository;
    private final ModelMapper mapper;
    private final ModelMapper modelMapper;

    @Override
    public UserDto register(UserRegisterDto dto) {
        if(userAccountRepository.existsById(dto.getLogin())){
            throw new UserExistsException();

        }

        UserAccount userAccount = mapper.map(dto, UserAccount.class);
        userAccount.addRole("USER");

        userAccountRepository.save(userAccount);
        return  modelMapper.map(userAccount, UserDto.class);

        //return null;
    }

    @Override
    public UserDto findByLogin(String login) {
        return null;
    }

    @Override
    public UserDto updateUser(String login, UserEditDto dto) {
        return null;
    }

    @Override
    public UserDto addRole(String login, String role) {
        return null;
    }

    @Override
    public UserDto removeRole(String login, String role) {
        return null;
    }

    @Override
    public UserDto delete(String login) {
        return null;
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }
}
