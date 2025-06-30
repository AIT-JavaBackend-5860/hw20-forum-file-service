package ait.cohort5860.accounting.controller;

import ait.cohort5860.accounting.dto.*;
import ait.cohort5860.accounting.service.AccountingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountingController {

    // TODO
    private final AccountingService service;

    @PostMapping("/register")
    public UserDto register(@RequestBody UserRegisterDto dto) {
        return service.register(dto);
    }

    @GetMapping("/user/{login}")
    public UserDto getUser(@PathVariable String login) {
        return service.findByLogin(login);
    }

    @PatchMapping("/user/{login}")
    public UserDto updateUser(@PathVariable String login, @RequestBody UserEditDto dto) {
        return service.updateUser(login, dto);
    }

    @PatchMapping("/user/{login}/role/{role}")
    public UserDto addRole(@PathVariable String login, @PathVariable String role) {
        return service.addRole(login, role);
    }

    @DeleteMapping("/user/{login}/role/{role}")
    public UserDto deleteRole(@PathVariable String login, @PathVariable String role) {
        return service.removeRole(login, role);
    }

    @DeleteMapping("/user/{login}")
    public UserDto deleteUser(@PathVariable String login) {
        return service.delete(login);
    }

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@RequestHeader("X-Password") String oldPassword, @RequestBody String newPassword) {
        service.changePassword(oldPassword, newPassword);
    }
}
