package zz.indi;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

/**
 * @author 李娟平
 */
@Data
@Document(collection = "bank_statement")
public class BankStatement {

    @Id
    private Long id;

    // 交易版本
    private String bizVersion;

    /**
     * 支付单号
     */
    private String paymentNo;

    /**
     * 关联运单号
     */
    private String orderNo;

    /**
     * 业务流水号
     */
    private String businessSerialNo;

    /**
     * 请求流水号
     */
    private String reqSerialNo;

    /**
     * 银行流水号
     */
    private String bankSerialNo;

    /**
     * 交易序号(银行提供)
     */
    private String hostSeq;

    /**
     * 流水发起关联主账户
     */
    private Long fromMainAccountId;

    /**
     * 流水发起关联银行虚户ID
     */
    private Long fromVirtualAccountId;

    /**
     * 流水发起关联银行虚户名称
     */
    private String fromVirtualAccountName;

    /**
     * 流水发起关联银行虚户编号
     */
    private String fromVirtualAccountNo;

    /**
     * 流水接受关联主账户
     */
    private Long toMainAccountId;

    /**
     * 流水接受关联银行虚户ID
     */
    private Long toVirtualAccountId;

    /**
     * 流水接受关联银行虚户名称
     */
    private String toVirtualAccountName;

    /**
     * 流水接受关联银行虚户编号
     */
    private String toVirtualAccountNo;

    /**
     * 银行账户账号
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
     * 渠道银行类型
     */
    private Integer bankType;

    /**
     * 请求发起时间
     */
    private Date reqTime;

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;

    /**
     * 状态, 1: 待处理, 2: 成功, 3: 失败, 4: 其他
     */
    private Integer status;

    /**
     * 响应描述
     */
    private String respMessage;

    /**
     * 响应时间
     */
    private Date respTime;

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
     * 自建油费户兼容处理标识: 标识该笔资金将要走平台油费户的充值/提现（用于连接平台自建油费户和银行虚户）
     * from 或 to 一方必须为 实际银行账户，另一方必须为平台-大易油费专用户（实际发生交易为平台-大易油费专用户充值/提现）
     */
    @Deprecated
    private Boolean bankBridgeDy;

    /**
     * 前后账户银行通道切换, 标记为true的业务, 不发生实际交易, 直接成功
     */
    private Boolean bankChannelChange;

    /**
     * 前后账户银行通道切换到该通道, 标记为true的业务, 不发生实际交易, 直接成功
     */
    private Integer bankChannelChangeTo;

    /**
     * 银行流水已对账
     */
    private Boolean bankChecked;

    /**
     * 退款号
     */
    private String refundReqNo;

    private Boolean batchHandle;
    /**
     * 支付流程交易步骤
     */
    private Integer payStep;

    /**
     * 回单地址
     */
    private String receiptUrl;

    public static final String ID = "_id";

    public static final String PAYMENT_NO = "paymentNo";

    public static final String BUSINESS_SERIAL_NO = "businessSerialNo";

    public static final String BANK_SERIAL_NO = "bankSerialNo";

    public static final String REQ_SERIAL_NO = "reqSerialNo";

    public static final String FROM_MAIN_ACCOUNT_ID = "fromMainAccountId";

    public static final String FROM_VIRTUAL_ACCOUNT_ID = "fromVirtualAccountId";

    public static final String FROM_VIRTUAL_ACCOUNT_NO = "fromVirtualAccountNo";

    public static final String STATUS = "status";

    public static final String RESP_MESSAGE = "respMessage";

    public static final String RESP_TIME = "respTime";

    public static final String BANK_CHECKED = "bankChecked";

    public static final String BANK_TYPE = "bankType";

    public static final String UPDATE_TIME = "updateTime";

    public static final String CREATE_TIME = "createTime";

    public static final String RECEIPT_URL = "receiptUrl";
}
