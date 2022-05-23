package com.social.Social.controller;

import com.social.Social.dto.AuthRequestDto;
import com.social.Social.dto.AuthResponseDto;
import com.social.Social.dto.UserDto;
import com.social.Social.exception.RequestParameterException;
import com.social.Social.exception.UserException;
import com.social.Social.service.UserService;
import com.social.Social.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    /**
     * ignore Please just to chech if server is up and running
     * @return details of alpha test user
     */
    @GetMapping("/test")
    public String home() {
        return ("<p>"+userService.loadUserByUsername("alpha")+"</p>");
    }

    /***
     * signout or invalidate the token
     * @param token jwt to be invalidated for logout
     * @return true if success else false
     */
    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) throws RequestParameterException {
        return ResponseUtil.filterResponse(userService.logout(token));
    }

    @GetMapping("/all")
    public List<UserDto> getAll(){
        return  userService.allUser();
    }

    /***
     * create user with the input details
     * @param userDto user details obj
     * @return object with signing data like jwt etc if successful
     */
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) throws UserException {
        if (userService.checkUserExist(userDto.getUserName())) {
            throw new UserException("Registration Fail : user already exist");
        }
        return returnToken(userService.createUser(userDto));
    }

    /***
     * login validation service for given credentials
     * @param authRequestDto contains login creds like username password
     * @return object with signing data like jwt etc if successful
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequestDto authRequestDto) throws UserException {
        return returnToken(userService.authenticateUser(authRequestDto));
    }

    /***
     * @param authResponseDto response object
     * @return token if param have valid value else exception
     * @throws UserException when user is not found
     */
    public ResponseEntity<?> returnToken(AuthResponseDto authResponseDto) throws UserException {
        if (authResponseDto == null) {
            throw new UserException("You have entered invalid credentials");
        }
        return ResponseEntity.ok(authResponseDto);
    }

    @ExceptionHandler({UserException.class})
    public ResponseEntity<String> handleAuthenticationException(UserException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
    }

    @ExceptionHandler({RequestParameterException.class})
    public ResponseEntity<String> handleAuthenticationException(RequestParameterException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
    }
}

