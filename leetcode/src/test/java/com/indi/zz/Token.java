package com.indi.zz;

import cn.hutool.core.util.StrUtil;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
public class Token {
    private String accessToken;
    private String refreshToken;
//
//    public Token(String accessToken, String refreshToken) {
//        this.accessToken = accessToken;
//        this.refreshToken = refreshToken;
//    }
//
//
//    @Getter
//    @Setter
//    @Builder
//    public static class TokenInfo extends Token {
//        protected Boolean hasLoginPassword;
//
//        @Builder(toBuilder = true)
//        public TokenInfo(String accessToken, String refreshToken, Boolean hasLoginPassword) {
//            super(accessToken, refreshToken);
//        }
//    }
//
//
//    @Getter
//    @Setter
//    @Builder
//    public static class BrokerTokenDTO extends TokenInfo {
//        private Boolean isRentCompany;
//
//
//        @Builder(toBuilder = true)
//        public BrokerTokenDTO(String accessToken, String refreshToken, Boolean hasLoginPassword, Boolean isRentCompany) {
//            super(accessToken, refreshToken, false);
//        }
//    }

    public static void main(String[] args) {
//        TokenInfo token = TokenInfo.builder().accessToken("ab").refreshToken("bb").hasLoginPassword(true).build();
        System.out.println(StrUtil.format("{1}{2}", "a", "b"));
        System.out.println(StrUtil.format("{}{}", "a", "b"));
//        System.out.println(token);
//
//        BrokerTokenDTO bt = BrokerTokenDTO.builder()
//            .accessToken("bbb")
//            .refreshToken("bcx")
//            .isRentCompany(true)
//            .hasLoginPassword(false).build();
//        System.out.println(bt);
    }
}
