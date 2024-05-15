package zz.indi;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 银行充值明细
 */
@Data
@Accessors(chain = true)
@Document("fin_bank_recharge_detail")
public class BankRechargeDetail {

    public static final String DOCUMENT_NAME = "fin_bank_recharge_detail";

    @Id
    private Long id;

    /**
     * 入账账号公司
     */
    private Long companyId;

    /**
     * 入账账号ID
     */
    private Long accountId;

    /**
     * 入账账号公司
     */
    private String accountNo;

    /**
     * 入账账号名称
     */
    private String accountName;

    /**
     * 入账账号类型
     */
    private Integer accountType;

    /**
     * 入账账户客户
     */
    private Long partyId;
    private Integer partyType;

    /**
     * 充值来源银行
     */
    private Integer bankType;

    /**
     * 充值明细银行流水号
     */
    private String bankSerialNo;

    /**
     * 充值时间
     */
    private Date rechargeTime;

    /**
     * 充值金额
     */
    private BigDecimal amount;

    /**
     * 充值银行账号，账户名，银行名，联行号信息
     */
    private String bankAccountNo;
    private String bankAccountName;
    private String bankName;
    private String bankNo;

    /**
     * 充值备注
     */
    private String remark;

    /**
     * 状态, -1-异常 0-默认状态 1-待处理, 2-处理中 3-充值处理完成
     */
    private Integer status;

    /**
     * 处理成功时间
     */
    private Date finishTime;

    /**
     * 处理流水号
     */
    private String paymentNo;

    /**
     * 充值业务流水号
     */
    private String bizNo;

    /**
     * 触发
     */
    private Integer fireFreq;
    private Date fireTime;

    private Date createTime;
    private Date updateTime;

    public static final String ID = "_id";
    public static final String BANK_TYPE = "bankType";
    public static final String BANK_SERIAL_NO = "bankSerialNo";
    public static final String STATUS = "status";
    public static final String PAYMENT_NO = "paymentNo";
    public static final String BIZ_NO = "bizNo";
    public static final String FIRE_FREQ = "fireFreq";
    public static final String FIRE_TIME = "fireTime";
    public static final String FINISH_TIME = "finishTime";
    public static final String CREATE_TIME = "createTime";
    public static final String UPDATE_TIME = "updateTime";








    /**
     * 借贷标记：0-借  1-贷
     */
    private String addReduce;

    /**
     * 资金余额
     */
    private BigDecimal balance;

    /**
     * 可用余额
     */
    private BigDecimal useableBalance;

    /**
     * 是否跨行: 01-行内   02-跨行
     */
    private String isCrossLine;

    /**
     * 业务类型: 0-入金
     * 1-出金
     * 2-交易
     * 8-作废
     */
    private String busType;

    /**
     * 核心流水号: 业务类型是0-入金、1-出金时有值
     */
    private String coreTelSerialNum;

    private String filed1;
    private String filed2;
    private String filed3;










    private String outVendorId;

    /**
     * 交易序号，入账通知交易流水号
     */
    private Integer origin;

    /**
     * :买方的用户外部编号:71810240115
     */
    private String outUserId;













    /**
     * 是否为平安融资放款
     */
    private Boolean isLoanDraw;

    /**
     * @see InAcctTypeEnum
     * 入账类型 "02：会员充值/退票入账 03：资金挂账"
     */
    private Integer inAcctType;

    /**
     * 挂账记录是否已清分
     */
    private Boolean chargeIn;

    /**
     * 挂账清分时间
     */
    private Date chargeInTime;

    /**
     * 会计日期
     */
    private String accountingDate;

}
