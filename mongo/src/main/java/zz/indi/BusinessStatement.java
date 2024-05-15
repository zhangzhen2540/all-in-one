package zz.indi;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author 李娟平
 */
@Data
@Accessors(chain = true)
@Document(collection = "business_statement")
public class BusinessStatement {

    @Id
    private Long id;

    /**
     * 交易版本
     */
    private String bizVersion;

    /**
     * 开票公司id
     */
    private Long companyId;

    /**
     * 支付类型
     */
    private Integer payType;
    private Integer originPayType;
    private Integer oppositePayType;

    /**
     * 业务类型, -1: 提现 -2: 转账支出 -3: 支付运费 -4: 授信垫付油费 -5: 授信还款 -6: 油卡充值 -7: 油费消费，油费支付 -8: 缴纳税费 -9: 油费撤销  -10: 支出保险
     * 1: 充值 2: 转账转入 3: 收取运费 4: 收入油费 5: 还款收取 6: 代收税费 7: 油费回退 8: 收入保险
     */
    private Integer businessType;

    /**
     * 相关计划类型 1: 长期计划 2: 指派运力 3: 调度计划
     */
    private Integer planType;

    /**
     * 收付款单ID
     */
    private Long paymentBillId;

    /**
     * 是否线下支付
     */
    private Boolean isOfflinePay;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 工行贷款申请唯一编号
     */
    private String loanApplyNo;

    /**
     * 业务流水号
     */
    private String businessSerialNo;

    /**
     * 银行流水号(充值, 提现)
     */
    private String bankSerialNo;

    /**
     * 支付单号
     */
    private String paymentNo;

    /**
     * 关联运单号
     */
    private String orderNo;

    /**
     * 自营油站加油订单号
     */
    private String oilOrderNo;

    /**
     * 标识在线加油场景下, 是否大易自营油站
     * 当 PayCostType=OIL 有效
     */
    private Boolean isDyOilSupplier;

    /**
     * 调车任务号(中标单号)
     */
    private String shuntNo;

    /**
     * 油卡编号(账户)[油卡充值, 油费消费]
     */
    private String oilCardNo;

    /**
     * 票据号码
     */
    private String draftNo;

    /**
     * 流水主体对象ID
     */
    private Long partyId;

    /**
     * 流水主体对象类型, 1: 货主 2: 车主 3: 司机 4: 公司
     */
    private Integer partyType;

    /**
     * 流水主体名称
     */
    private String partyName;

    /**
     * 流水主体电话
     */
    private String partyPhone;

    /**
     * 流水主体账户类型
     */
    private PayAccountType partyAccountType;

    /**
     * 流水主体公司id
     */
    private Long partyCompanyId;

    /**
     * 公司提现/充值账户类型
     */
    private PayAccountType cashOutAccountType;

    /**
     * 流水另一方主体对象ID
     */
    private Long oppositeId;

    /**
     * 流水另一方主体对象类型, 1: 货主 2: 车主 3: 司机 4: 公司
     */
    private Integer oppositeType;

    /**
     * 流水另一方主体名称
     */
    private String oppositeName;

    /**
     * 流水另一方主体电话
     */
    private String oppositePhone;

    /**
     * 流水对手账户类型
     */
    private PayAccountType oppositeAccountType;

    /**
     * 流水对手公司id
     */
    private Long oppositeCompanyId;

    /**
     * 交易状态, 0: 处理中, 1: 成功, 2: 失败
     */
    private Integer transactionStatus;

    /**
     * 交易状态说明
     */
    private String transactionMessage;

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;

    /**
     * 交易产生税费
     */
    private BigDecimal transactionTaxAmount;

    /**
     * 交易时间
     */
    private Date transactionTime;

    /**
     * 交易完成时间
     */
    private Date transactionFinishTime;

    /**
     * 交易是否已取消
     */
    private Boolean transactionCanceled;

    /**
     * 银行账户账号(充值, 提现)
     */
    private String bankAccountNo;

    /**
     * 银行账户名称
     */
    private String bankAccountName;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 开户支行
     */
    private String subBankName;

    /**
     * 支付联行号
     */
    private String subBankNo;

    /**
     * 业务已结束(整个业务交易流程是否已结束)
     */
    private Boolean businessEnd;

    /**
     * 回单名称
     */
    private String receiptName;

    /**
     * 回单地址
     */
    private String receiptUrl;

    /**
     * 创建人id
     */
    private Long createUid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人id
     */
    private Long updateUid;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 任务执行次数
     */
    private Integer fireFreq;

    /**
     * 任务执行时间
     */
    private Date fireTime;

    /**
     * 资金流水对账
     */
    private Boolean billCheck;

    /**
     * 收支平衡对账
     */
    private Boolean balanceCheck;

    /**
     * 微信流水对账
     */
    private Boolean wechatCheck;

    /**
     * 忽略完成前后处理
     */
    private Boolean ignoreFinish;

    /**
     * 是否自动提现
     */
    private Boolean autoCashOut;

    /**
     * 下一个收款账户
     */
    private Long nextPartyId;
    private Integer nextPartyType;
    private String nextPartyName;
    private String nextPartyPhone;


    /**
     * 是否不使用自建账户交易
     */
    private Boolean isNotUseDy;
    /**
     * 分润信息
     */
    private Boolean isPayShare;
    private List<PayShare> payShares;

    /**
     * 被冲正的流水号
     */
    private String reversedNo;
    /**
     * 记录修复流水修复时间
     */
    private Date repairTime;

    /**
     * 持卡人id
     */
    private Long owerId;
    /**
     * 提款pdf承诺书地址
     */
    private String drawSignUrl;

    private Boolean failback;

    private String refundedNo;
    /**
     * 批次号
     */
    private String batchNo;

    private String agreementNo;
    // 优先返现
    private Boolean rebateFirst;
    // 强制取消
    private Boolean forceCancel;
    /**
     * 微信退款单号
     */
    private String outRefundNo;
    /**
     * 中信支付用光大退款
     */
    private Boolean zxRefundByGd;
    /**
     * 是否推送联盟,结算是司机
     */
    private Boolean pushBrokerSettleDriver;
    /**
     * 是否联盟垫付的还款
     */
    private Boolean isAdvanceRepayment;

    /**
     * 是否车辆资产管理系统的车牌
     */
    private Boolean vehAllocation;
    /**
     * 车辆id
     */
    private Long vehicleId;
    /**
     * 车牌号
     */
    private String vehicleNo;

    /**
     * 大易价
     */
    private BigDecimal dyAmount;

    /**
     * 平安融资提款
     */
    private Boolean isLoanDraw;
    /**
     * 平安融资提款编号
     */
    private String loanReqNo;

    /**
     * 是否是平安融资
     */
    private Boolean isLoan;
    private List<PayLoan> payLoans;

    /**
     * 迭代周期
     */
    private Integer term;
    /**
     * 融资退款种类:
     */
    private Integer repaymentKind;
    /**
     * 借据号
     */
    private String loanNo;

    /**
     * 自建油费户兼容处理标识: 交易双方类似收款方/付款方为银行通道，另一方为大易平台通道
     */
    private Boolean bankBridgeDy;

    /**
     * 大易自建油费，初始化用户自建账户资金
     */
    private Boolean isOilInit;

    /**
     * 大易自建平台账户初始化资金
     */
    private Boolean isSelfAccountInit;

    /**
     * 执行步骤
     */
    private Integer exeStep;
    /**
     * 交易结束后运费余额
     */
    private BigDecimal endAmount;

    /**
     * 是否资金划拨
     */
    private Boolean reAllocation;

    /**
     * 划拨接受消息手机号
     */
    private Boolean allocationMobile;

    /**
     * 划拨执行记录ID
     */
    private String alloExecRecordId;

    // 提现选择的充值流水编号
    private String rechargeBizNo;
    // 提现选择的充值流水对应的实际的银行流水编号
    private String rechargeBankBizNo;

    public static final String ID = "_id";
    public static final String PAY_TYPE = "payType";
    public static final String OPPOSITE_ID = "oppositeId";
    public static final String PAYMENT_BILL_ID = "paymentBillId";
    public static final String OPPOSITE_TYPE = "oppositeType";
    public static final String OPPOSITE_NAME = "oppositeName";
    public static final String OPPOSITE_PHONE = "oppositePhone";
    public static final String OPPOSITE_COMPANY_ID = "oppositeCompanyId";
    public static final String OPPOSITE_ACCOUNT_TYPE = "oppositeAccountType";
    public static final String NEXT_PARTY_ID = "nextPartyId";
    public static final String NEXT_PARTY_TYPE = "nextPartyType";
    public static final String NEXT_PARTY_NAME = "nextPartyName";
    public static final String NEXT_PARTY_PHONE = "nextPartyPhone";


    public static final String PARTY_ID = "partyId";
    public static final String PARTY_TYPE = "partyType";
    public static final String PARTY_NAME = "partyName";
    public static final String PARTY_PHONE = "partyPhone";
    public static final String PARTY_ACCOUNT_TYPE = "partyAccountType";
    public static final String PARTY_COMPANY_ID = "partyCompanyId";
    public static final String COMPANY_ID = "companyId";
    public static final String ORDER_NO = "orderNo";
    public static final String SHUNT_NO = "shuntNo";
    public static final String BUSINESS_TYPE = "businessType";
    public static final String PLAN_TYPE = "planType";
    public static final String PAYMENT_NO = "paymentNo";
    public static final String DRAFT_NO = "draftNo";
    public static final String TRANSACTION_TIME = "transactionTime";
    public static final String IS_OFFLINE_PAY = "isOfflinePay";
    public static final String BUSINESS_SERIAL_NO = "businessSerialNo";
    public static final String BANK_SERIAL_NO = "bankSerialNo";
    public static final String TRANSACTION_STATUS = "transactionStatus";
    public static final String TRANSACTION_MESSAGE = "transactionMessage";
    public static final String TRANSACTION_FINISH_TIME = "transactionFinishTime";
    public static final String TRANSACTION_CANCELED = "transactionCanceled";
    public static final String CREATE_TIME = "createTime";
    public static final String UPDATE_TIME = "updateTime";
    public static final String UPDATE_UID = "updateUid";
    public static final String FIRE_TIME = "fireTime";
    public static final String FIRE_FREQ = "fireFreq";
    public static final String BUSINESS_END = "businessEnd";
    public static final String BANK_NAME = "bankName";
    public static final String BANK_ACCOUNT_NAME = "bankAccountName";
    public static final String BANK_ACCOUNT_NO = "bankAccountNo";
    public static final String RECEIPT_NAME = "receiptName";
    public static final String RECEIPT_URL = "receiptUrl";
    public static final String BILL_CHECK = "billCheck";
    public static final String BALANCE_CHECK = "balanceCheck";
    public static final String WECHAT_CHECK = "wechatCheck";
    public static final String REVERSED_NO = "reversedNo";
    public static final String REPAIR_TIME = "repairTime";
    public static final String OWERID = "owerId";
    public static final String DRAW_SIGN_URL = "drawSignUrl";
    public static final String TRANSACTION_AMOUNT = "transactionAmount";
    public static final String ZX_REFUND_BY_GD = "zxRefundByGd";
    public static final String IS_PAY_SHARE = "isPayShare";
    public static final String PUSH_BROKER_SETTLE_DRIVER = "pushBrokerSettleDriver";
    public static final String OUT_REFUND_NO = "outRefundNo";
    public static final String VEH_ALLOCATION = "vehAllocation";
    public static final String VEHICLE_ID = "vehicleId";
    public static final String VEHICLE_NO = "vehicleNo";
    public static final String DY_AMOUNT = "dyAmount";
    public static final String IS_LOAN = "isLoan";
    public static final String LOAN_NO = "loanNo";
    public static final String EXE_STEP = "exeStep";
    public static final String TERM = "term";
    public static final String REPAYMENT_KIND = "repaymentKind";
    public static final String WITHDRAW_STATUS = "withdrawStatus";
    public static final String RELATE_BS_NO = "relateBsNo";

}
