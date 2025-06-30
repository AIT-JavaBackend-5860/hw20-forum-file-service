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
    public AccountResponseDto register(@RequestBody RegisterDto dto) {
        return service.register(dto);
    }

    @GetMapping("/user/{login}")
    public AccountResponseDto getUser(@PathVariable String login) {
        return service.findByLogin(login);
    }

    @PatchMapping("/user/{login}")
    public AccountResponseDto updateUser(@PathVariable String login, @RequestBody UpdateUserDto dto) {
        return service.updateUser(login, dto);
    }

    @PatchMapping("/user/{login}/role/{role}")
    public AccountResponseDto addRole(@PathVariable String login, @PathVariable String role) {
        return service.addRole(login, role);
    }

    @DeleteMapping("/user/{login}/role/{role}")
    public AccountResponseDto deleteRole(@PathVariable String login, @PathVariable String role) {
        return service.removeRole(login, role);
    }

    @DeleteMapping("/user/{login}")
    public AccountResponseDto deleteUser(@PathVariable String login) {
        return service.delete(login);
    }

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@RequestHeader("X-Password") String oldPassword, @RequestBody String newPassword) {
        service.changePassword(oldPassword, newPassword);
    }
}
