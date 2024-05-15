package org.example;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class NestInfo {


    @Data
    public static class Trade {
        private Long id;
        private Long partyId;
        private String name;

        private BigDecimal amount;
    }

    @Data
    public static class TradeDTO {
        private BigDecimal amount;

        private PartyInfo party;
    }
    public boolean CheckPermutation(String s1, String s2) {
        Map<Character, Integer> map = new HashMap<>();


        if (s1.length() != s2.length()) {
            return false;
        }

        for (int i = 0; i < s1.length(); i++) {
            map.put(s1.charAt(i), map.getOrDefault(s1.charAt(i), 0) + 1);
        }

        for (int i = 0; i < s2.length(); i++) {
            if (!map.containsKey(s2.charAt(i))) {
                return false;
            }

            map.put(s2.charAt(i), map.get(s2.charAt(i)) - 1);

            if (map.get(s2.charAt(i)) == 0) {
                map.remove(s2.charAt(i));
            }
        }

        return map.isEmpty();
    }

    @Data
    public static class PartyInfo {
        private Long id;
        private String name;
    }

    @Mapper
    public static interface TradeMapper {

        @Mapping(target = "partyId", source = "party.id")
        @Mapping(target = ".", source = "party")
        Trade conv(TradeDTO dto);

    }

    public static void main(String[] args) {
        PartyInfo partyInfo = new PartyInfo();
        partyInfo.setId(1L);
        partyInfo.setName("AAA");

        TradeDTO dto = new TradeDTO();
        dto.setAmount(new BigDecimal("1.11"));
        dto.setParty(partyInfo);

        System.out.println(JSON.toJSONString(Mappers.getMapper(TradeMapper.class).conv(dto)));

    }
}
