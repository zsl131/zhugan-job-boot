package com.zslin.core.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zslin.auth.model.AuthSource;
import com.zslin.auth.tools.AuthAnnotationTools;
import com.zslin.basic.tools.NormalTools;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.List;

/**
 * 开发文档工具类
 * Created by zsl on 2018/10/30.
 */
@Component
public class DocWordTools {

    @Autowired
    private AuthAnnotationTools authAnnotationTools;

    public void exportDoc(OutputStream os) throws Exception {
        //Blank Document
        XWPFDocument document= new XWPFDocument();

        //Write the Document in file system
//        FileOutputStream out = new FileOutputStream(outputFile);


        //添加标题
        XWPFParagraph titleParagraph = document.createParagraph();
        //设置段落居中
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun titleParagraphRun = titleParagraph.createRun();
        titleParagraphRun.setText("软件接口说明文档");
        titleParagraphRun.setColor("000000");
        titleParagraphRun.setFontSize(20);

        newLine(document);

        //段落
        XWPFParagraph firstParagraph = document.createParagraph();
        XWPFRun run = firstParagraph.createRun();
        firstParagraph.setAlignment(ParagraphAlignment.RIGHT); //右对齐
        run.setText("生成时间："+ NormalTools.curDate("yyyy-MM-dd HH:mm:ss"));
        run.setColor("696969");
        run.setFontSize(12);

        //换行
        newLine(document);

        buildCode(document);

        createDefaultHeader(document, "接口文档");
        createDefaultFooter(document);

        /*//两个表格之间加个换行
        newLine(document);

        //工作经历表格
        XWPFTable ComTable = document.createTable();

        //列宽自动分割
        CTTblWidth comTableWidth = ComTable.getCTTbl().addNewTblPr().addNewTblW();
        comTableWidth.setType(STTblWidth.DXA);
        comTableWidth.setW(BigInteger.valueOf(9072));

        //表格第一行
        XWPFTableRow comTableRowOne = ComTable.getRow(0);
        comTableRowOne.getCell(0).setText("开始时间");
        comTableRowOne.addNewTableCell().setText("结束时间");
        comTableRowOne.addNewTableCell().setText("公司名称");
        comTableRowOne.addNewTableCell().setText("title");

        //表格第二行
        XWPFTableRow comTableRowTwo = ComTable.createRow();
        comTableRowTwo.getCell(0).setText("2016-09-06");
        comTableRowTwo.getCell(1).setText("至今");
        comTableRowTwo.getCell(2).setText("seawater");
        comTableRowTwo.getCell(3).setText("Java开发工程师");

        //表格第三行
        XWPFTableRow comTableRowThree = ComTable.createRow();
        comTableRowThree.getCell(0).setText("2016-09-06");
        comTableRowThree.getCell(1).setText("至今");
        comTableRowThree.getCell(2).setText("seawater");
        comTableRowThree.getCell(3).setText("Java开发工程师");*/

//        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
//        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);

        document.write(os);
        os.close();
//        System.out.println("create_table document written success.");
    }

    public void exportDoc(String outputFile) throws Exception {
        FileOutputStream out = new FileOutputStream(outputFile);
        exportDoc(out);
    }

    private void newLine(XWPFDocument document) {
        //换行
        XWPFParagraph paragraph1 = document.createParagraph();
        XWPFRun paragraphRun1 = paragraph1.createRun();
        paragraphRun1.setText("\r");
    }

    private void buildCode(XWPFDocument document) {
        List<AuthSource> list = authAnnotationTools.buildList();
//        System.out.println("======"+list.size());
        for(AuthSource as : list) {
            buildSingleSource(document, as);
        }
    }

    private void buildSingleSource(XWPFDocument document, AuthSource as) {
        newLine(document);
        newSingleLine(document, "接口名称："+as.getName());
        newSingleLine(document, "接口代码："+as.getCode());
        newSingleLine(document, "接口参数："+as.getParams());
        newTable(document, as.getParams());
    }

    private void newSingleLine(XWPFDocument document, String text) {
//        newLine(document);
        //段落
        XWPFParagraph firstParagraph = document.createParagraph();
        XWPFRun run = firstParagraph.createRun();
        firstParagraph.setAlignment(ParagraphAlignment.LEFT); //右对齐
        run.setText(text);
        run.setFontSize(14);
    }

    private void newTable(XWPFDocument document, String params) {
        JSONObject jsonObj ;
        try {
            jsonObj = JSON.parseObject(params);
        } catch (Exception e) {
            return ;
        }
        //工作经历表格
        XWPFTable comTable = document.createTable();

        //列宽自动分割
        CTTblWidth comTableWidth = comTable.getCTTbl().addNewTblPr().addNewTblW();
        comTableWidth.setType(STTblWidth.DXA);
        comTableWidth.setW(BigInteger.valueOf(9072));

        //表格第一行
        XWPFTableRow comTableRowOne = comTable.getRow(0);
        XWPFTableCell cell0 = comTableRowOne.getCell(0);
        cell0.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        cell0.setText("参数名");

        XWPFTableCell cell1 = comTableRowOne.addNewTableCell();
        cell1.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.BOTH);
        cell1.setText("类型");

        XWPFTableCell cell2 = comTableRowOne.addNewTableCell();
        cell2.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.BOTH);
        cell2.setText("是否可空");

        XWPFTableCell cell3 = comTableRowOne.addNewTableCell();
        cell3.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.BOTH);
        cell3.setText("默认值");

        XWPFTableCell cell4 = comTableRowOne.addNewTableCell();
        cell4.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.BOTH);
        cell4.setText("示例值");

        XWPFTableCell cell5 = comTableRowOne.addNewTableCell();
        cell5.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.BOTH);
        cell5.setText("说明");

        /*comTableRowOne.addNewTableCell().setVerticalAlignment(XWPFTableCell.XWPFVertAlign.BOTH).setText("类型");
        comTableRowOne.addNewTableCell().setText("是否可空");
        comTableRowOne.addNewTableCell().setText("默认值");
        comTableRowOne.addNewTableCell().setText("示例值");
        comTableRowOne.addNewTableCell().setText("说明");*/

        for(String key : jsonObj.keySet()) {
            newTableRow(comTable, key, jsonObj.getString(key));
        }

    }
    private void newTableRow(XWPFTable comTable, String key, String value) {
        XWPFTableRow row = comTable.createRow();
//        row.getCell(0)
        row.getCell(0).setText(key);
        row.getCell(1).setText("");
        row.getCell(2).setText("");
        row.getCell(3).setText("");
        row.getCell(4).setText(value);
        row.getCell(5).setText("");
    }


    private void createDefaultHeader(final XWPFDocument docx, final String text){
        try {
            CTP ctp = CTP.Factory.newInstance();
            XWPFParagraph paragraph = new XWPFParagraph(ctp, docx);
            ctp.addNewR().addNewT().setStringValue(text);
            ctp.addNewR().addNewT().setSpace(SpaceAttribute.Space.PRESERVE);
            CTSectPr sectPr = docx.getDocument().getBody().isSetSectPr() ? docx.getDocument().getBody().getSectPr() : docx.getDocument().getBody().addNewSectPr();
            XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(docx, sectPr);
            XWPFHeader header = policy.createHeader(STHdrFtr.DEFAULT, new XWPFParagraph[] { paragraph });
            header.setXWPFDocument(docx);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlException e) {
            e.printStackTrace();
        }
    }


    private void createDefaultFooter(final XWPFDocument docx) {
        // TODO 设置页码起始值
        try {
            CTP pageNo = CTP.Factory.newInstance();
            XWPFParagraph footer = new XWPFParagraph(pageNo, docx);
            CTPPr begin = pageNo.addNewPPr();
            begin.addNewPStyle().setVal("123");
            begin.addNewJc().setVal(STJc.CENTER);
            pageNo.addNewR().addNewFldChar().setFldCharType(STFldCharType.BEGIN);
            pageNo.addNewR().addNewInstrText().setStringValue("PAGE   \\* MERGEFORMAT");
            pageNo.addNewR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);
            CTR end = pageNo.addNewR();
            CTRPr endRPr = end.addNewRPr();
            endRPr.addNewNoProof();
            endRPr.addNewLang().setVal("zh-cn");
            end.addNewFldChar().setFldCharType(STFldCharType.END);
            CTSectPr sectPr = docx.getDocument().getBody().isSetSectPr() ? docx.getDocument().getBody().getSectPr() : docx.getDocument().getBody().addNewSectPr();
            XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(docx, sectPr);
            policy.createFooter(STHdrFtr.DEFAULT, new XWPFParagraph[] { footer });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlException e) {
            e.printStackTrace();
        }
    }
}
