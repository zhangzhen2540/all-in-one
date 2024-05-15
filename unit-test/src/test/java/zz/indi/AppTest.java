package zz.indi;

import com.dywl.utils.DateUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest {
    public static void main(String[] args) {
        String desc = "FCS5853:对账日期必须等于当前营业日期20230918";
        System.out.println(desc.startsWith("FCS5853:对账日期必须等于当前营业日期"));
        System.out.println(desc.substring("FCS5853:对账日期必须等于当前营业日期".length()));

        // FCS5853:对账日期必须等于当前营业日期20230918
        if (desc.startsWith("FCS5853:对账日期必须等于当前营业日期")) {
            Date correctDate = DateUtil.parseDate(desc.substring("FCS5853:对账日期必须等于当前营业日期".length()), "yyyyMMdd");

            System.out.println(correctDate);
            System.out.println(DateUtil.format(correctDate));
            return;
        }
    }

}
