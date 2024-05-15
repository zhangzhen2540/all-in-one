package zz.indi;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author zz
 * 平台账户类型
 *
 *  每个公司具有两个虚拟账户
 *  货主在多个公司有虚拟账户(创建时选择开票公司)
 *  车主在母公司下有两个虚拟账户
 *  司机在母公司下有两个虚拟账户
 *
 *  公司:
 *      主账户: 客户支付时资金中转账户(包括代扣税费)
 *      油费账户: 为货主垫付运单油费, 司机油费消费时代收(为供应商代收)
 *
 *
 *
 *  货主:
 *      主账户: 支付运费, 授信还款, 充值/提现
 *
 *  车主:
 *      主账户: 收入运费, 提现
 *      垫资账户: 支付运费, 充值
 *
 *  司机:
 *      主账户: 收入运费, 提现
 *      油费账户: 油费消费, 油卡充值, 油费收入
 *
 */
@Getter
@AllArgsConstructor
public enum PayAccountType {
    /**
     * 运费账户(用于运费收支)
     */
    MAIN(1, "运费账户", false),

    /**
     * 垫资账户(用于运费垫付)
     */
    ADVANCE(2, "垫资账户", false),

    /**
     * 油费账户(司机收取油费, 公司垫付油费)
     */
    OIL(3, "油费账户", false),

    /**
     * 用于银行卡三要素校验的账户(通过向指定银行卡转账1分钱来校验)
     */
    VERIFY(4, "校验账户", true),

    /**
     * 保证金账户(用于联盟,司机大易调车接单)
     */
    DEPOSIT(5, "保证金账户", false),

    /**
     * 返现账户（用于支付服务费）
     */
    REBATE(6, "返现账户", false),
    
    /**
     * 代收代付账户
     */
    INTERMEDIARY(7, "代收代付账户", false),
    
    /**
     * 大易服务费账户（收取服务费）
     */
    SERVICE(8, "服务费账户", false),

    COMMON_JXSF(10, "公共计息收费", true),
    COMMON_DZ(11, "公共调帐", true),
    COMMON_ZJCSH(12, "公共资金初始化", true),
    COMMON_NAMESAKE(13, "公司同名账户", true),

    /**
     * 出金账户, 用于用户出金时的资金中转
     */
    WITHDRAWAL(20, "出金账户", true),
    
    COMPANY_INTERMEDIARY(21, "中转账户", true),

    COMPANY_SUB_MAIN(22, "服务费中转账户", true),

    COMPANY_SHARE(23, "公司分润收款账户", true),

    /**
     * 中信错账调帐清退中间账户
     * （中信不能直接退回错账，需要先转入调帐清退中间账户再提现）
     */
    COMMON_DZ_MID(30, "调帐清退中间账户", true),

    /**
     * 财务专用虚拟账户
     */
    FINANCE_ONLY(31, "财务专用虚拟账户", true),

    /**
     * 平安贷归集户
     */
    COLLECT(32, "平安贷归集户", true),

    /**
     * 助贷费账户
     */
    HELP_LOAN(33, "助贷费账户", true),

    /**
     * 还款过度账户
     */
    INTERMEDIARY_REPAYMENT(34, "还款过度账户", true),

    /**
     * 油费专用户
     * 平台自建油费户：
     *      银行下油费专用户-收取货主支付油费
     *      平台下油费专用户-向司机、联盟支付油费
     */
    SPECIAL_OIL(40, "油费专用户", true),

    /**
     * 易油宝专用户
     * 用于支持自营油站供应商收付款
     */
    SUPPLIER_SP_OIL(41, "易油宝专用户", true),

    /**
     * 用于银行
     *  货主在银行端资金池
     *  大易实资金
     */
    SPECIAL(100, "专用户", true),

    /**
     * 平台归集户
     * 用于平台自建账户资金的发放和收回
     */
    PLATFORM_COLLECT(200, "平台归集户", true),

    ;

    /**
     * 账户类型
     */
    private Integer type;
    /**
     * 账户描述
     */
    private String desc;
    /**
     * 内部使用类型(是否展示到前端)
     */
    private boolean onlyInnerUse;

    public static PayAccountType typeOf(Integer type) {
        
        for (PayAccountType value : values()) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return null;
    }
    
    public static PayAccountType typeOf(String name) {
        
        for (PayAccountType value : values()) {
            if (Objects.equals(value.name(), name)) {
                return value;
            }
        }
        return null;
    }

    public static boolean contains(Integer type, PayAccountType... types) {
        if (type == null || types == null || types.length == 0) {
            return false;
        }

        PayAccountType target = typeOf(type);
        for (PayAccountType payAccountType : types) {
            if (target == payAccountType) {
                return true;
            }
        }

        return false;
    }
}
