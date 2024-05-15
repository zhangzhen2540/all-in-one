package zz.indi;

import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class App {

    private static String tempFiles = "/tmp/poundDoc";

    private static String takeGoodsTmpPdf = "/take_goods_poundDoc_%s.pdf";
    private static String takeGoodsTmpPic = "/take_goods_poundDoc_%s.png";

    private static String poundDocTemplate = "/template/poundDoc.pdf";
    private static BaseFont baseFont = null;

    static Resource resource;
    public static void main(String[] args) {
        try {
            baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            log.error("中文字体设置失败：" + e);
        }

        File fl = new File(tempFiles);
        if (!fl.exists()) {
            fl.mkdirs();
        }


        resource = new ClassPathResource(poundDocTemplate);

        int n = 2;
        while (true) {
            gen();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private static void gen() {

        Map<String, String> map = new HashMap<>();
        map.put("shipperCname", "SHipper");
        map.put("code", "Shipper-Code");
        map.put("billCode", "Shipper-Bill-code");
        map.put("noticeCode", "Shipper-Notice-code");

        try {

            InputStream configStream = resource.getInputStream();

            String goodsTmpPdf = tempFiles + String.format(takeGoodsTmpPdf, System.currentTimeMillis());
            FileOutputStream out = new FileOutputStream(goodsTmpPdf);
            //读取pdf模板
            PdfReader reader = new PdfReader(configStream);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(reader, bos);
            //获取模板中的 填充项
            AcroFields form = stamper.getAcroFields();
            /* 使用中文字体 */
            form.addSubstitutionFont(baseFont);
            if (ObjectUtils.isNotEmpty(map)) {
                for (String key : map.keySet()) {
                    String value = map.get(key);
                    form.setField(key, value);
                }
            }

            //设置为true 不允许编辑生成的pdf文件 默认为false
            stamper.setFormFlattening(true);
            stamper.close();

            com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfReader pdfReader = new PdfReader(bos.toByteArray());
            PdfImportedPage importpage = copy.getImportedPage(pdfReader, 1);

            copy.addPage(importpage);
            pdfReader.close();
            importpage.closePath();
            importpage.closePathStroke();

            copy.close();

            doc.close();
            File pdfTmp = new File(goodsTmpPdf);
            //pdf 生成图片
            File file = pdf2Picpng(goodsTmpPdf);
//            ResultDTO resultDTO = storageComponentService.uploadByOriginName(file, "order", file.getName());
            if (file.isFile()) { // 判断是否是文件夹
                file.delete();// 删除
            }

            if (pdfTmp.isFile()) {
                pdfTmp.delete();
            }

//            return JachBillDTO.builder().fileName(resultDTO.getKey()).imgDoc(resultDTO.getUrl()).build();

            configStream.close();
            out.close();
            bos.close();
            reader.close();


        } catch (Exception e) {
            log.error("生成磅单失败：" + e);
//            return JachBillDTO.builder().fileName("").imgDoc("").build();
        }
    }

    public static File pdf2Picpng(String PdfPath) {
        File file = new File(PdfPath);
        PDDocument pdDocument;
        try {
            String goodsTmpPic = tempFiles + String.format(takeGoodsTmpPic, System.currentTimeMillis());
            File picFile = new File(goodsTmpPic);

            pdDocument = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            /* 第二位参数越大转换后越清晰，相对转换速度越慢 */
            BufferedImage image = renderer.renderImageWithDPI(0, 300);
            ImageIO.write(image, "png", picFile);

            pdDocument.close();

            return picFile;
        } catch (IOException e) {
            log.error("pdf转图片失败：" + e);
            return null;
        }

    }
}
