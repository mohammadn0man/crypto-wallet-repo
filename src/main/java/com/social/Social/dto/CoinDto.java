package com.social.Social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoinDto {
    private int id;
    private int userId;
    private String name;
    private String symbol;
    private String price;
    private String quantity;
    private String transactionType;
}
