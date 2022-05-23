package com.social.Social.service;

import com.social.Social.dto.AuthRequestDto;
import com.social.Social.dto.AuthResponseDto;
import com.social.Social.dto.UserDto;
import com.social.Social.model.ExpireToken;
import com.social.Social.model.User;
import com.social.Social.repository.ExpireTokenRepository;
import com.social.Social.repository.UserRepository;
import com.social.Social.util.CustomUserDetails;
import com.social.Social.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtTokenUtil;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ExpireTokenRepository expireTokenRepository;

    /***
     * used by userDetails service for validation and token generation
     * @param userName user name for which token is generated
     * @return object type CostomUserDetails
     * @throws UsernameNotFoundException when user is not found
     */
    @Override
    public CustomUserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
        return user.map(CustomUserDetails::new).get();
    }

    /***
     * check if user exist with given name
     * @param username username to check
     * @return true if found else false
     */
    public boolean checkUserExist(String username) {
        return userRepository.existsByUserName(username);
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    /***
     * create user with the input details
     * @param userDto user details obj
     * @return object with signing data like jwt etc if successful
     */
    public AuthResponseDto createUser(UserDto userDto) {
        User userModel = MapperUtil.getModelMapper().map(userDto, User.class);
        userModel.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        try {
            userRepository.save(userModel);
        } catch (Exception e) {
            log.error("signup exception : " + e);
            return null;
        }

        final CustomUserDetails userDetails = loadUserByUsername(userModel.getUserName());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return getAuthResponseDto(userDetails, jwt);
    }

    @NotNull
    private AuthResponseDto getAuthResponseDto(CustomUserDetails userDetails, String jwt) {
        return new AuthResponseDto(
                userDetails.getUsername(),
                userDetails.getFullName(),
                userDetails.getUserId(),
                jwt);
    }

    /***
     * login validation service for given credentials
     * @param authRequestDto contains login creds like username password
     * @return object with signing data like jwt etc if successful
     */
    public AuthResponseDto authenticateUser(AuthRequestDto authRequestDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(),
                            authRequestDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            log.error("Login with bad Credentials " + e);
            return null;
        }

        final CustomUserDetails userDetails = loadUserByUsername(authRequestDto.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return getAuthResponseDto(userDetails, jwt);
    }

    /***
     * signout or invalidate the token
     * @param token jwt to be invalidated for logout
     * @return true if success else false
     */
    public boolean logout(String token) {
        try {
            expireTokenRepository.save(new ExpireToken(token.substring(7)));
        } catch (Exception e) {
            log.error("logout fail : " + e);
            return false;
        }
        return true;
    }

    /**
     * list all users to follow
     *
     * @return returns iterable list
     */
    public List<UserDto> allUser() {
        List<User> list = (List<User>) userRepository.findAll();
        List<UserDto> result = new ArrayList<UserDto>();
        for (User i : list) {
            result.add(
                    MapperUtil.getModelMapper().map(i, UserDto.class)
            );
        }
        return result;
    }

    /***
     * simple service for giving user count for stats api
     * @return integer value of count
     */
    public long getCount() {
        return userRepository.count();
    }
}
