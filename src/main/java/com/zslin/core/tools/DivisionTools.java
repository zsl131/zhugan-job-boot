package com.zslin.core.tools;

import com.zslin.core.dao.IAreaDao;
import com.zslin.core.model.Area;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.text.DecimalFormat;

/**
 * 行政区划处理工具类
 * Created by zsl on 2018/11/20.
 */
@Component
public class DivisionTools {

    @Autowired
    private IAreaDao areaDao;

    public void initDivision() {
        try {
            File file = ResourceUtils.getFile("classpath:publicFile/xzqh.xlsx");
            InputStream is = new FileInputStream(file);
            Workbook wb = new XSSFWorkbook(is);
            Sheet sheet = wb.getSheetAt(0);
            int index = 0;
            for(Row row : sheet) {
                if(index++<=0) {continue;}
                String pn = "";
                Cell cell0 = row.getCell(0);
                if(cell0.getCellType()==Cell.CELL_TYPE_STRING) {
                    pn = cell0.getStringCellValue();
                } else if(cell0.getCellType()==Cell.CELL_TYPE_NUMERIC) {
                    DecimalFormat df = new DecimalFormat("#");
                    pn = ""+df.format(cell0.getNumericCellValue());
                }

                String pc = "";
                Cell cell1 = row.getCell(1);
                if(cell1.getCellType()==Cell.CELL_TYPE_STRING) {
                    pc = cell1.getStringCellValue();
                } else if(cell1.getCellType()==Cell.CELL_TYPE_NUMERIC) {
//                    pc = ""+cell1.getNumericCellValue();
                    DecimalFormat df = new DecimalFormat("#");
                    pc = ""+df.format(cell1.getNumericCellValue());
                }

                String cn = "";
                Cell cell2 = row.getCell(2);
                if(cell2.getCellType()==Cell.CELL_TYPE_STRING) {
                    cn = cell2.getStringCellValue();
                } else if(cell2.getCellType()==Cell.CELL_TYPE_NUMERIC) {
//                    cn = ""+cell2.getNumericCellValue();
                    DecimalFormat df = new DecimalFormat("#");
                    cn = ""+df.format(cell2.getNumericCellValue());
                }

                String cc = "";
                Cell cell3 = row.getCell(3);
                if(cell3.getCellType()==Cell.CELL_TYPE_STRING) {
                    cc = cell3.getStringCellValue();
                } else if(cell3.getCellType()==Cell.CELL_TYPE_NUMERIC) {
//                    cc = ""+cell3.getNumericCellValue();
                    DecimalFormat df = new DecimalFormat("#");
                    cc = ""+df.format(cell3.getNumericCellValue());
                }

                String an = "";
                Cell cell4 = row.getCell(4);
                if(cell4.getCellType()==Cell.CELL_TYPE_STRING) {
                    an = cell4.getStringCellValue();
                } else if(cell4.getCellType()==Cell.CELL_TYPE_NUMERIC) {
//                    an = ""+cell4.getNumericCellValue();
                    DecimalFormat df = new DecimalFormat("#");
                    an = ""+df.format(cell4.getNumericCellValue());
                }

                String ac = "";
                Cell cell5 = row.getCell(5);
                if(cell5.getCellType()==Cell.CELL_TYPE_STRING) {
                    ac = cell5.getStringCellValue();
                } else if(cell5.getCellType()==Cell.CELL_TYPE_NUMERIC) {
//                    ac = ""+cell5.getNumericCellValue();
                    DecimalFormat df = new DecimalFormat("#");
                    ac = ""+df.format(cell5.getNumericCellValue());
                }

//                String pn = row.getCell(0).getRichStringCellValue().toString();
//                String pc = row.getCell(1).getRichStringCellValue().toString();
//                String cn = row.getCell(2).getRichStringCellValue().toString();
//                String cc = row.getCell(3).getRichStringCellValue().toString();
//                String an = row.getCell(4).getRichStringCellValue().toString();
//                String ac = row.getCell(5).getRichStringCellValue().toString();
                Area a = areaDao.findByCountyCode(ac);
                if(a==null) {
                    a = new Area();
                    a.setProvinceName(pn);
                    a.setProvinceCode(pc);
                    a.setCityCode(cc);
                    a.setCityName(cn);
                    a.setCountyCode(ac);
                    a.setCountyName(an);
                    a.setStatus("0");
                    areaDao.save(a);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
