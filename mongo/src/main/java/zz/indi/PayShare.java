package zz.indi;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayShare {
    /**
     * 分润交易号
     */
    private String paymentNo;

    /**
     * 分润金额
     */
    private BigDecimal transactionAmount;

    private Long oppositeId;
    private Integer oppositeType;
    private String oppositeName;
    private String oppositePhone;
    private PayAccountType oppositeAccountType;
    private Long oppositeCompanyId;

    private Long nextPartyId;
    private Integer nextPartyType;
    private String nextPartyName;
    private String nextPartyPhone;

    /**
     * 是否自动提现
     */
    private Boolean autoCashOut;

    // 指定分润付款方式
    private String bizId;
    private String useCase;
}
