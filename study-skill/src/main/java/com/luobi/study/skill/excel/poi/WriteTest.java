package com.luobi.study.skill.excel.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.FileOutputStream;

public class WriteTest {

    private static final String PATH = "./src/main/resources/excel/";  // 相对路径

    @Test
    public void writeExcel2003() throws Exception {
        // 1.创建一个工作簿  2003版本
        Workbook workbook = new HSSFWorkbook();
        // 2.创建一个工作表
        Sheet sheet = workbook.createSheet("图书销售表");
        // 3.创建第一行
        Row row1 = sheet.createRow(0);

        // 4.创建单元格
        // (1,1)
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue("三国演义");
        // (1,2)
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue(88);

        // 创建第二行
        Row row2 = sheet.createRow(1);
        // (2,1)
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("销售时间");
        // (2,2)
        Cell cell22 = row2.createCell(1);
        String time = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        cell22.setCellValue(time);

        // 生成一张Excel表（IO流），2003版本是使用xls结尾
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "图书销售表03.xls");
        // 输出
        workbook.write(fileOutputStream);
        // 关流
        fileOutputStream.close();

        System.out.println("图书销售表 生成完毕！");
    }

    @Test
    public void writeExcel2007() throws Exception {
        // 1.创建一个工作簿  2007版本
        Workbook workbook = new XSSFWorkbook();
        // 2.创建一个工作表
        Sheet sheet = workbook.createSheet("图书销售表");
        // 3.创建第一行
        Row row1 = sheet.createRow(0);

        // 4.创建单元格
        // (1,1)
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue("水浒传");
        // (1,2)
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue(76);

        // 创建第二行
        Row row2 = sheet.createRow(1);
        // (2,1)
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("销售日期");
        // (2,2)
        Cell cell22 = row2.createCell(1);
        String time = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        cell22.setCellValue(time);

        // 生成一张Excel表（IO流），2007版本是使用xlsx结尾
        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "图书销售表07.xlsx");
        // 输出
        workbook.write(fileOutputStream);
        // 关流
        fileOutputStream.close();

        System.out.println("图书销售表 生成完毕！");
    }

    @Test
    public void writeExcel2003BigData() throws Exception {
        // 开始时间
        long begin = System.currentTimeMillis();

        // 创建工作簿
        Workbook workbook = new HSSFWorkbook();

        // 创建工作表
        Sheet sheet = workbook.createSheet();

        // 写入数据
        int totalRowNum = 65536;
        for (int rowNum = 0; rowNum < totalRowNum; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10; cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }
        System.out.println("over");

        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "writeExcel2003BigData.xls");
        workbook.write(fileOutputStream);
        fileOutputStream.close();

        // 结束时间
        long end = System.currentTimeMillis();

        // 批量写入65536条数据耗时：1.35s
        System.out.println("批量写入" + totalRowNum + "条数据耗时：" + (double) (end - begin) / 1000 + "s");
    }

    // 耗时较长！
    @Test
    public void writeExcel2007BigData() throws Exception {
        // 开始时间
        long begin = System.currentTimeMillis();

        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();

        // 创建工作表
        Sheet sheet = workbook.createSheet();

        // 写入数据
        int totalRowNum = 100000;
        for (int rowNum = 0; rowNum < 100000; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10; cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }
        System.out.println("over");

        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "writeExcel2007BigData.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();

        // 结束时间
        long end = System.currentTimeMillis();

        // 批量写入100000条数据耗时：11.229s
        System.out.println("批量写入" + totalRowNum + "条数据耗时：" + (double) (end - begin) / 1000 + "s");
    }

    // 使用缓存优化！
    @Test
    public void writeExcel2007BigDataS() throws Exception {
        // 开始时间
        long begin = System.currentTimeMillis();

        // 创建工作簿
        Workbook workbook = new SXSSFWorkbook();

        // 创建工作表
        Sheet sheet = workbook.createSheet();

        // 写入数据
        int totalRowNum = 500000;
        for (int rowNum = 0; rowNum < 500000; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10; cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }
        System.out.println("over");

        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "writeExcel2007BigDataS.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();

        // 清除临时文件
        ((SXSSFWorkbook) workbook).dispose();

        // 结束时间
        long end = System.currentTimeMillis();

        // 批量写入500000条数据耗时：4.664s
        System.out.println("批量写入" + totalRowNum + "条数据耗时：" + (double) (end - begin) / 1000 + "s");
    }

}
