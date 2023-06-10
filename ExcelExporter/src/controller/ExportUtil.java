package controller;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import annotation.Details;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;

public class ExportUtil <T>{

    /**
     * 导出 Excel
     * @param o List<T> 中的 T
     * @param list 要导出的数据
     * @param fileName 导出文件名
     */
    public void exportDate(Class o, List<T> list, String fileName) {
        try {
//            HSSFWorkbook wb = getHSSFWorkbook(o, list);
            XSSFWorkbook wb = getHSSFWorkbook(o,list);
            if (wb == null) {
                return;
            }
            //写入response
//            response.reset();
//            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
////            response.setContentType("application/vnd.ms-excel;charset=utf-8");
//            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859_1"));
//            ServletOutputStream fileOut = response.getOutputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            wb.write(byteArrayOutputStream);
            wb.close();


            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public XSSFWorkbook getHSSFWorkbook(Class c, List<T> list) throws NoSuchFieldException, IllegalAccessException {
        //滤空
        if (list == null || list.isEmpty()) {
            return null;
        }
        //反射获取属性
        Field[] fields = c.getDeclaredFields();
        //创建备用集合,用来存放需要展示的字段
        ArrayList<Field> fields1 = new ArrayList<>();
        for (Field field : fields) {
            //获取自定义注解对象
            Details annotation = c.getDeclaredField(field.getName()).getAnnotation(Details.class);
            //判断当前字段是否需要导出
            if (annotation.isExport()) {
                //新增进备用集合
                fields1.add(field);
            }
        }
        // 封装excel
        XSSFWorkbook hssfWorkbook = new XSSFWorkbook();
        //创建sheet页
        XSSFSheet sheet = hssfWorkbook.createSheet();
        Row row = sheet.createRow(0);
        XSSFCellStyle style = hssfWorkbook.createCellStyle();
        XSSFFont font = hssfWorkbook.createFont();
        font.setBold(true);
        //font.setFontHeightInPoints((short) 16);
        style.setFont(font);
        if (row != null) {
            for (int i = 0; i < fields1.size(); i++) {
                //获取字段详情解释
                Details annotation = c.getDeclaredField(fields1.get(i).getName()).getAnnotation(Details.class);
                //添加标题并赋值
                row.createCell(i).setCellValue(annotation.value());
                row.getCell(i).setCellStyle(style);
                //row.setRowStyle(style);
            }
        }
        //设置变量,便于后续方便给文本框更改样式
        int rowIndex = 0;
        //滤空
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                T p = list.get(i);
                rowIndex++;
                //新增sheet页
                Row rowF = sheet.createRow(rowIndex);
                for (int j = 0; j < fields1.size(); j++) {
                    //获取反射过来的属性对象
                    Field field = fields1.get(j);
                    //把成员变量变为公有化
                    field.setAccessible(true);
                    //获取集合中成员变量的值field.toString()
                    Object o1 = field.get(p);
                    if ("class java.util.Date".equals(field.getGenericType().toString())){
                        String format = new SimpleDateFormat("yyyy-MM-dd").format(o1);
                        o1=format;
                    }
                    //滤空赋值
                    if (o1 != null) {
                        rowF.createCell(j).setCellValue(o1.toString());
                    } else {
                        rowF.createCell(j).setCellValue("");
                    }
                }
            }
        }
        //设置样式
        for (int i = 0; i <= rowIndex; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }

        return hssfWorkbook;
    }
}
