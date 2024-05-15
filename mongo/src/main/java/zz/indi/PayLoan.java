package zz.indi;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayLoan {
    /**
     * 交易号
     */
    private String paymentNo;

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;


    /**
     * 联盟利润
     */
    private BigDecimal profitAmount;
    /**
     * 还款总金额(联盟还款给平安的总金额)
     */
    private BigDecimal repayTotalAmount;
    /**
     * 利息
     */
    private BigDecimal interest;
    /**
     * 本金
     */
    private BigDecimal principal;
    /**
     * 复利
     */
    private BigDecimal compoundInterest;


    private Long partyId;
    private Integer partyType;
    private String partyName;
    private String partyPhone;
    private PayAccountType partyAccountType;
    private Long partyCompanyId;

    private Long oppositeId;
    private Integer oppositeType;
    private String oppositeName;
    private String oppositePhone;
    private PayAccountType oppositeAccountType;
    private Long oppositeCompanyId;

    private String useCase;
    /**
     * 借据中;
     * 银行请求流水号, 3.2付款接口使用的orderNo
     */
    private String bankRequestNo;
    /**
     * 执行步骤
     */
    private Integer exeStep;


    public void setParty(PayPartyDTO party) {
        if (party == null) {
            return;
        }
        partyId = party.getPartyId();
        partyName = party.getPartyName();
        partyPhone = party.getPartyPhone();
        partyType = party.getPartyType().getType();
    }

    public void setOpposite(PayPartyDTO party) {

        if (party == null) {
            return;
        }
        oppositeId = party.getPartyId();
        oppositeName = party.getPartyName();
        oppositePhone = party.getPartyPhone();
        oppositeType = party.getPartyType().getType();
    }


    public PayPartyDTO getParty() {

        if (partyId == null) {
            return null;
        }
        return new PayPartyDTO().setPartyId(partyId).setPartyName(partyName)
                .setPartyPhone(partyPhone).setPartyType(PayPartyType.typeOf(partyType));
    }

    public PayPartyDTO getOpposite() {

        if (oppositeId == null) {
            return null;
        }
        return new PayPartyDTO().setPartyId(oppositeId).setPartyName(oppositeName)
                .setPartyPhone(oppositePhone).setPartyType(PayPartyType.typeOf(oppositeType));
    }
}
