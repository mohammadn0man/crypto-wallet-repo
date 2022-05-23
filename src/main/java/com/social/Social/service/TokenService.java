package com.social.Social.service;

import com.social.Social.repository.ExpireTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final ExpireTokenRepository tokenRepository;

    /***
     * check if given token is amongst the invalidated once in the database
     * @param jwt token to check
     * @return true, if found else false
     */
    public boolean isExist(String jwt) {
        return tokenRepository.existsById(jwt);
    }
}
