package com.social.Social.util;

import com.social.Social.exception.RequestParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    private ResponseUtil() {
    }

    public static ResponseEntity<String> filterResponse(boolean flag) throws RequestParameterException {
        if (!flag) {
            throw new RequestParameterException("There is some problem in serving your request." +
                    " Please check your parameter in request.");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("RESPONSE_SUBMITTED");
    }

}
