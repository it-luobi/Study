package com.luobi.study.skill.excel.poi;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.Date;

public class ReadTest {

    private static final String PATH = "./src/main/resources/excel/";  // 相对路径

    @Test
    public void readExcel2003() throws Exception {
        // 获取文件流
        FileInputStream inputStream = new FileInputStream(PATH + "图书销售表03.xls");

        // 1.创建一个工作簿。使用Excel能操作的，这个类都可以操作
        Workbook workbook = new HSSFWorkbook(inputStream);
        // 2.得到工作表
        Sheet sheet = workbook.getSheetAt(0);
        // 3.得到行
        Row row = sheet.getRow(0);
        // 4.得到列
        Cell cell = row.getCell(1);

        // 读取值的时候，要注意类型相匹配
        System.out.println(cell.getNumericCellValue());
        inputStream.close();
    }

    @Test
    public void readExcel2007() throws Exception {
        // 获取文件流
        FileInputStream inputStream = new FileInputStream(PATH + "图书销售表07.xlsx");

        // 1.创建一个工作簿。使用Excel能操作的，这个类都可以操作
        Workbook workbook = new XSSFWorkbook(inputStream);
        // 2.得到工作表
        Sheet sheet = workbook.getSheetAt(0);
        // 3.得到行
        Row row = sheet.getRow(0);
        // 4.得到列
        Cell cell = row.getCell(1);

        // 读取值的时候，要注意类型相匹配
        System.out.println(cell.getNumericCellValue());
        inputStream.close();
    }

    // 读取不同的数据类型
    @Test
    public void testCellType() throws Exception {
        FileInputStream inputStream = new FileInputStream(PATH + "明细表.xlsx");

        // 创建一个工作簿。使用Excel能操作的，这个类都可以操作
        Workbook workbook = new XSSFWorkbook(inputStream);
        // 得到工作表
        Sheet sheet = workbook.getSheetAt(0);

        // 读取标题内容
        Row rowTitle = sheet.getRow(0);
        if (rowTitle != null) {
            int cellCount = rowTitle.getPhysicalNumberOfCells();
            for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                Cell cell = rowTitle.getCell(cellNum);
                if (cell != null) {
                    int cellType = cell.getCellType();
                    String cellValue = cell.getStringCellValue();
                    System.out.print(cellValue + " | ");
                }
            }
            System.out.println();
        }

        // 获取表中的内容
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < rowCount; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {
                // 读取列
                int cellCount = rowTitle.getPhysicalNumberOfCells();
                for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                    System.out.print("[" + (rowNum + 1) + "-" + (cellNum + 1) + "]");

                    Cell cell = rowData.getCell(cellNum);
                    // 匹配列的数据类型
                    if (cell != null) {
                        int cellType = cell.getCellType();
                        String cellValue = "";

                        switch (cellType) {
                            case XSSFCell.CELL_TYPE_STRING:  // 字符串
                                System.out.print("【STRING】");
                                cellValue = cell.getStringCellValue();
                                break;
                            case XSSFCell.CELL_TYPE_BOOLEAN:  // 布尔
                                System.out.print("【BOOLEAN】");
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case XSSFCell.CELL_TYPE_BLANK:  // 空
                                System.out.print("【BLANK】");
                                break;
                            case XSSFCell.CELL_TYPE_NUMERIC:  // 数字（日期、普通数字）
                                System.out.print("【NUMERIC】");
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {  // 日期
                                    System.out.print("【日期】");
                                    Date date = cell.getDateCellValue();
                                    cellValue = new DateTime(date).toString("yyyy-MM-dd");
                                } else {
                                    // 不是日期格式，防止数字过长无法显示
                                    System.out.print("【数字转换成字符串输出】");
                                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                                    cellValue = cell.toString();
                                }
                                break;
                            case XSSFCell.CELL_TYPE_ERROR:
                                System.out.print("【数据类型错误】");
                                break;
                        }
                        System.out.println(cellValue);
                    }
                }
            }
        }

        inputStream.close();
    }

    // 公式计算
    @Test
    public void testFormula() throws Exception {
        FileInputStream inputStream = new FileInputStream(PATH + "公式.xlsx");
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        // 单元格位置
        Row row = sheet.getRow(5);
        Cell cell = row.getCell(1);

        // 拿到计算公式
        FormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);

        // 计算结果
        CellValue evaluate = formulaEvaluator.evaluate(cell);
        String cellValue = evaluate.formatAsString();
        System.out.println(cellValue);

        // 输出单元格的内容（即输出计算公式）
        int cellType = cell.getCellType();
        switch (cellType) {
            case Cell.CELL_TYPE_FORMULA:  // 公式
                String cellFormula = cell.getCellFormula();
                System.out.println(cellFormula);
                break;
        }
    }

}
