package com.social.Social.service;

import com.social.Social.dto.CoinDto;
import com.social.Social.model.Coin;
import com.social.Social.repository.CoinRepository;
import com.social.Social.util.CustomUserDetails;
import com.social.Social.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CoinService {

    @Autowired
    private CoinRepository coinRepository;
    @Autowired
    private UserService userService;

    public boolean addCoin(CoinDto coinDto) {
        Coin coin = MapperUtil.getModelMapper()
                .map(coinDto, Coin.class);
        try {
            coinRepository.save(coin);
        } catch (Exception e) {
            log.error("Coin not saved");
            return false;
        }
        return true;
    }

    public List<CoinDto> getCoin(String userName) {
        CustomUserDetails user = userService.loadUserByUsername(userName);
        return getCoinDto(coinRepository.findAllByUser(userService.getUserById(user.getUserId()).orElseGet(null)));
    }

    @NotNull
    private List<CoinDto> getCoinDto(List<Coin> res) {
        List<CoinDto> ret = new ArrayList<>();
        for (Coin x : res) {
            ret.add(MapperUtil.getModelMapper().map(x, CoinDto.class));
        }
        return ret;
    }

}
