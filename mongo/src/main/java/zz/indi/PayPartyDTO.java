package zz.indi;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 支付主体
 **/
@Data
@Accessors(chain = true)
public class PayPartyDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付主体对象ID
     */
    private Long partyId;

    /**
     * 支付主体名称
     */
    private String partyName;

    /**
     * 支付主体电话
     */
    private String partyPhone;

    /**
     * 支付主体类型
     */
    private PayPartyType partyType;

}
