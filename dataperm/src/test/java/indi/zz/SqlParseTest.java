package indi.zz;

import java.io.StringReader;
import java.util.UUID;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.values.ValuesStatement;
import org.junit.Test;

public class SqlParseTest {

    @Test
    public void test() throws Exception {
        CCJSqlParserManager pm = new CCJSqlParserManager();

        String sql = "select * from logi_order"
            + " lo join user_info u on lo.user_id = u.id "
            + " where del = 0 and id = 10"
            ;

        Statement statement = pm.parse(new StringReader(sql));
        if (statement instanceof Select) {
            Select select = (Select) statement;
            PlainSelect selectBody = (PlainSelect) select.getSelectBody();

            FromItem fromItem = selectBody.getFromItem();
            System.out.println("table: " + ((Table) fromItem).getName());

            System.out.println(fromItem.getAlias().getName());

        }
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
    }
}
