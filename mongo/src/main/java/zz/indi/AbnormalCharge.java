package zz.indi;


import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 异常挂账
 */
@Data
@Accessors(chain = true)
@Document("abnormal_charge")
public class AbnormalCharge implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    /**
     * 公司ID
     */
    private Long companyId;

    private String mainAccountNo;

    /**
     * 挂账账户账号
     */
    private String accountNo;

    /**
     * 挂账账户名称
     */
    private String accountName;

    /**
     * 银行类型 1: 中信  6：光大 7:微信
     * @see com.dywl.logistics.finance.base.enumeration.BankType
     */
    private Integer bankType;

    /**
     * 银行流水号
     */
    private String bankSerialNo;

    /**
     * 挂账时间
     */
    private Date chargeTime;

    /**
     * 挂账金额
     */
    private BigDecimal chargeAmount;

    /**
     * 付款方账号、名称、银行名称、联行号
     */
    private String fromAccountNo;
    private String fromAccountName;
    private String fromBankName;
    private String fromBankNo;

    /**
     * 处理类型, 0：未处理 1：清分 2：清退
     * @see cn.da156.dywl.pay.enums.AbnormalChargeOpType
     */
    private Integer operateType;

    /**
     * 操作流水号
     */
    private String operatePaymentNo;

    /**
     * 操作处理状态  0: 未处理 1: 处理中 2：成功　3: 失败
     * @see cn.da156.dywl.pay.enums.AbnormalChargeOpStatus
     */
    private Integer operateStatus;

    /**
     * 失败描述
     */
    private String failDesc;

    /**
     * 清分目标信息
     */
    private Long targetPartyId;
    private Integer targetPartyType;
    private String targetPartyName;
    private String targetPartyPhone;
    private Integer targetAccountType;
    private Long targetAccountId;
    private String targetAccountNo;
    private String targetAccountName;

    /**
     * 是否自动完成清分、清退(失败后重试）
     */
    private Boolean autoComplete;

    private Date createTime;
    private Date updateTime;


    public static final String ID = "id";
    public static final String COMPANY_ID = "companyId";
    public static final String MAIN_ACCOUNT_NO = "mainAccountNo";
    public static final String BANK_TYPE = "bankType";
    public static final String BANK_SERIAL_NO = "bankSerialNo";
    public static final String CHARGE_TIME = "chargeTime";
    public static final String CHARGE_AMOUNT = "chargeAmount";
    public static final String FROM_ACCOUNT_NO = "fromAccountNo";
    public static final String FROM_ACCOUNT_NAME = "fromAccountName";
    public static final String FROM_BANK_NAME = "fromBankName";
    public static final String FROM_BANK_NO = "fromBankNo";
    public static final String TO_ACCOUNT_NO = "toAccountNo";
    public static final String TO_ACCOUNT_NAME = "toAccountName";
    public static final String OPERATE_TYPE = "operateType";
    public static final String OPERATE_PAYMENT_NO = "operatePaymentNo";
    public static final String OPERATE_STATUS = "operateStatus";
    public static final String FAIL_DESC = "failDesc";
    public static final String TARGET_PARTY_ID = "targetPartyId";
    public static final String TARGET_PARTY_TYPE = "targetPartyType";
    public static final String TARGET_ACCOUNT_NO = "targetAccountNo";
    public static final String TARGET_ACCOUNT_NAME = "targetAccountName";
    public static final String AUTO_COMPLETE = "autoComplete";
    public static final String CREATE_TIME = "createTime";
    public static final String UPDATE_TIME = "updateTime";

}
