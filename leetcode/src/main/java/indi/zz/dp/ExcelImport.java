package indi.zz.dp;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import java.io.File;
import java.nio.file.Paths;
import java.util.Map;
import lombok.Data;

public class ExcelImport {
// 序号	客户名称（必填）	统一社会信用代码（必填）	负责人	客户级别	客户类型	企业性质	1级行业	详细地址（必填）
// 是否战略客户	客户关键人（必填）	电话（必填）	来源	归属代理商	是否需要支持	预计签约费率（%）	预计签约月度体量（万）	创建人
// 创建时间	负责人变更时间	所属公海（名称）	是否有商机	是否已成交	实际部门


    public static class MyListener extends AnalysisEventListener<ExcelData> {

        @Override
        public void invoke(ExcelData data, AnalysisContext context) {
            System.out.println(data);
        }

        @Override
        public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
            System.out.println("head: " + headMap);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {

        }
    }

    public static void main(String[] args) {

        File file = Paths.get("/home/zz/Desktop/纷享销客客户信息4.3-demo.xlsx").toFile();
        EasyExcel.read(file, ExcelData.class, new MyListener())
            .sheet()
            .doRead();

        System.out.println(">>>>>>>>>>>> 已成交 <<<<<<<<<<<<<<<<<");
        EasyExcel.read(file, ExcelData.class, new MyListener())
            .sheet("已成交-4996")
            .doRead();
    }

    @Data
    public static class ExcelData {
        @ExcelProperty("序号")
        private Long id;

        @ExcelProperty("客户名称（必填）")
        private String name;

        @ExcelProperty("统一社会信用代码（必填）")
        private String creditCode;

        @ExcelProperty("负责人")
        private String managerName;
    }

}
