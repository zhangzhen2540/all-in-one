package zz.indi;

import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 机构类型
 */
@Getter
@AllArgsConstructor
public enum PayPartyType {

    /**
     * 货主
     */
    SHIPPER(1, "货主"){
        @Override
        public int flag() {
            return 1 << 0;
        }
    },
    
    /**
     * 联盟
     */
    BROKER(2, "联盟"){
        @Override
        public int flag() {
            return 1 << 1;
        }
    },
    
    /**
     * 司机
     */
    DRIVER(3, "司机"){
        @Override
        public int flag() {
            return 1 << 2;
        }
    },
    
    /**
     * 大易公司
     */
    COMPANY(4, "公司"){
        @Override
        public int flag() {
            return 1 << 4;
        }
    },

    /**
     * 船东
     */
    SHIPOWNER(6, "船东"){
        @Override
        public int flag() {
            return 1 << 5;
        }
    },
    /**
     * 持卡人
     */
    BANK_CARD_HOLDER(7, "持卡人"){
        @Override
        public int flag() {
            return 1 << 7;
        }
    },
    /**
     * 铁路
     */
    TIELU(8, "铁路"){
        @Override
        public int flag() {
            return 1 << 8;
        }
    },

    /**
     * 油供应商
     */
    OIL_SUPPLIER(9, "供应商"){
        @Override
        public int flag() {
            return 1 << 9;
        }
    },
    /**
     * 第三方
     */
    THIRD(10, "第三方"){
        @Override
        public int flag() {
            return 1 << 6;
        }
    },
    /**
     * 其他
     */
    OTHER(0, "其他"){
        @Override
        public int flag() {
            return 1 << 31;
        }
    },


    ;

    private Integer type;
    private String desc;
    
    public abstract int flag();
    
    public static boolean contains(Integer input, PayPartyType... types) {
        
        if(input == null || types == null || typeOf(input) == null) {
            return false;
        }
        int flag = 0;
        for(PayPartyType type : types) {
            flag |= type.flag();
        }
        return (typeOf(input).flag() & flag) > 0;
    }

    public static PayPartyType typeOf(Integer type) {
        
        for (PayPartyType value : values()) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return OTHER;
    }


}
