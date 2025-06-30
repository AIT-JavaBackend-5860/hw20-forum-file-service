package ait.cohort5860.accounting.service;

import ait.cohort5860.accounting.dao.UserAccountRepository;
import ait.cohort5860.accounting.dto.RolesDto;
import ait.cohort5860.accounting.dto.UserDto;
import ait.cohort5860.accounting.dto.UserEditDto;
import ait.cohort5860.accounting.dto.UserRegisterDto;
import ait.cohort5860.accounting.dto.exception.InvalidDataException;
import ait.cohort5860.accounting.dto.exception.UserExistsException;
import ait.cohort5860.accounting.dto.exception.UserNotFoundException;
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
        if (userAccountRepository.existsById(dto.getLogin())) {
            throw new UserExistsException();

        }

        UserAccount userAccount = mapper.map(dto, UserAccount.class);
        userAccount.addRole("USER");

        userAccountRepository.save(userAccount);
        return modelMapper.map(userAccount, UserDto.class);

    }

    @Override
    public UserDto getUser(String login) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public UserDto updateUser(String login, UserEditDto userEditDto) {

        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
        if (userEditDto.getFirstName() != null) {
            userAccount.setFirstName(userEditDto.getFirstName());
        }

        if (userEditDto.getLastName() != null) {
            userAccount.setLastName(userEditDto.getLastName());
        }

        userAccountRepository.save(userAccount);
        return modelMapper.map(userAccount, UserDto.class);

    }

    @Override
    public UserDto addRole(String login, String role) {
        return null;
    }


    @Override
    public UserDto removeRole(String login, String role) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
        userAccountRepository.delete(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public UserDto removeUser(String login) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);

        userAccountRepository.delete(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public void changePassword(String login, String newPassword) {

        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);

        userAccount.setPassword(newPassword);
        userAccountRepository.save(userAccount);

    }

    @Override
    public RolesDto changeRolesList(String login, String role, boolean isAddRole) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);

        try {
            if (isAddRole) {

                userAccount.addRole(role);
            } else {

                userAccount.removeRole(role);

            }
        } catch (Exception e) {
            throw new InvalidDataException();
        }
        userAccountRepository.save(userAccount);
        return modelMapper.map(userAccount, RolesDto.class);

    }
}
