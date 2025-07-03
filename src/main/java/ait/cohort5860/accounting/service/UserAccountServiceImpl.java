package ait.cohort5860.accounting.service;

import ait.cohort5860.accounting.dao.UserAccountRepository;
import ait.cohort5860.accounting.dto.*;
import ait.cohort5860.accounting.dto.exception.InvalidDataException;
import ait.cohort5860.accounting.dto.exception.UserExistsException;
import ait.cohort5860.accounting.dto.exception.UserNotFoundException;
import ait.cohort5860.accounting.model.Role;
import ait.cohort5860.accounting.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements AccountingService, CommandLineRunner { //CommandLineRunner - будет выполнено раньше всего

    private final UserAccountRepository userAccountRepository;
    private final ModelMapper mapper;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;


    @Override
    public UserDto register(UserRegisterDto dto) {
        if (userAccountRepository.existsById(dto.getLogin())) {
            throw new UserExistsException();

        }

        UserAccount userAccount = mapper.map(dto, UserAccount.class);
        userAccount.addRole("USER"); // добавили роль

        String encodedPassword = passwordEncoder.encode(dto.getPassword()); // скодировали пароль
        userAccount.setPassword(encodedPassword); // установили пароль

        userAccountRepository.save(userAccount); // записали
        return modelMapper.map(userAccount, UserDto.class); // вернули ляля

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
    public void sendEmail(EmailDto emailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDto.getTo());
        message.setSubject(emailDto.getSubject());
        message.setText(emailDto.getMessage());
        mailSender.send(message);
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

    // Если не существует пользователя админ - создать его
    @Override
    public void run(String... args) throws Exception {
        if (!userAccountRepository.existsById("admin")) {

            UserAccount admin = UserAccount.builder()

                    .login("admin")
                    .password(passwordEncoder.encode("admin"))
                    .firstName("Admin")
                    .lastName("Admin")
                    .role(Role.USER)
                    .role(Role.MODERATOR)
                    .role(Role.ADMINISTRATOR)
                    .build();

            userAccountRepository.save(admin);
        }
    }
}
