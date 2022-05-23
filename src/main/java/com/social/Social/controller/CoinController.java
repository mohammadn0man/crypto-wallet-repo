package com.social.Social.controller;

import com.social.Social.dto.CoinDto;
import com.social.Social.exception.RequestParameterException;
import com.social.Social.service.CoinService;
import com.social.Social.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coin")
public class CoinController {

    @Autowired
    private CoinService coinService;
    /**
     * post tweet to the feed
     * @param coinDto post's content and other details
     * @return 202 for success else error
     * @throws RequestParameterException for invalid params
     */
    @PostMapping("/add")
    public ResponseEntity<?> addPost(@RequestBody CoinDto coinDto) throws RequestParameterException {
        return ResponseUtil.filterResponse(coinService.addCoin(coinDto));
    }

    /**
     * get all post from user which is followed
     * @param userName name of user for whom we want to get feed
     * @return list of posts to show on feeds
     */
    @GetMapping("/get/{userName}")
    public List<CoinDto> getPost(@PathVariable String userName){
        return coinService.getCoin(userName);
    }



}
