package com.github.poi.out.impl;

import com.github.common.utils.CopyUtil;
import com.github.common.utils.NameGeneratorUtil;
import com.github.poi.annotation.Excel;
import com.github.poi.annotation.ExcelColumn;
import com.github.poi.annotation.ExcelImage;
import com.github.poi.enums.ExcelType;
import com.github.poi.enums.ImageType;
import com.github.poi.out.IExcelExportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

/**
 * <p>excel导出公共服务</p>
 *
 * @author <a href="mailto:xipan@bigvisiontech.com">panxi</a>
 * @version 1.0.0
 * @date 2020/5/12 14:11
 * @since 1.0
 */
@Slf4j
public class ExcelExportServiceImpl implements IExcelExportService {

    public static final ThreadLocal<ExcelType> typeThreadLocal = new ThreadLocal<>();

    public static final ThreadLocal<String> cacheFileName = new ThreadLocal<>();

    public static final ThreadLocal<Integer> columnSize = new ThreadLocal<>();

    @Override
    public void export(List data, HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        if(data == null || data.isEmpty()){
            return;
        }
        Class clazz = data.get(0).getClass();
        Excel excel = null;
        if(clazz.isAnnotationPresent(Excel.class)){
            excel = (Excel) clazz.getAnnotation(Excel.class);
        }
        String sheetName = NameGeneratorUtil.generateByDate("");
        ExcelType excelType = ExcelType.HSSF;
        if(excel != null){
            if(!StringUtils.isEmpty(excel.name())){
                sheetName = excel.name();
            }
            excelType = excel.type();
        }
        Workbook wk = null;
        typeThreadLocal.set(excelType);
        switch (excelType){
            case HSSF:
                wk = new HSSFWorkbook();
                break;
            case XSSF:
                wk = new XSSFWorkbook();
                break;
        }
        CellStyle cellStyle = setCellStyle(wk);
        CellStyle titleStyle = setTitleStyle(wk);

        Sheet sheet = wk.createSheet(sheetName);
        setRowData(sheet, data, cellStyle, titleStyle);
        //自动调正列宽
        autoSize(sheet);
        setSizeColumn(sheet);
        try {
            String fileName = NameGeneratorUtil.generateByTime(null);
            response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xls");
            wk.write(response.getOutputStream());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void setSizeColumn(Sheet sheet) {
        log.info(Charset.defaultCharset().name());
        log.info(System.getProperty("file.encoding"));
        for (int columnNum = 0; columnNum < columnSize.get(); columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                Row currentRow;
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    Cell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 2 * 256);
        }
    }

    private void autoSize(Sheet sheet) {
        for(int i=0; i< columnSize.get(); i++){
            sheet.autoSizeColumn(i);
        }
    }

    private void setRowData(Sheet sheet, List data, CellStyle cellStyle, CellStyle titleStyle) {
        int rowNum = 0;
        setColTitle(data.get(rowNum), sheet.createRow(rowNum), titleStyle);
        rowNum++;
        for (Object datum : data) {
            Row row = sheet.createRow(rowNum);
            setColData(datum, row, cellStyle);
            rowNum++;
        }
    }

    private void setColTitle(Object obj, Row row, CellStyle style) {
        PropertyDescriptor[] targetPd = CopyUtil.getPropertyDescriptors(obj.getClass());
        if(targetPd != null){
            int colNum = 0;
            for (PropertyDescriptor propertyDescriptor : targetPd) {
                Field field = null;
                Class<?> zClass = obj.getClass();
                while (zClass != null){
                    try {
                        field = zClass.getDeclaredField(propertyDescriptor.getName());
                        break;
                    }catch (NoSuchFieldException e){
                        zClass = zClass.getSuperclass();
                    }
                }
                if(field == null){
                    continue;
                }
                if(field.isAnnotationPresent(ExcelColumn.class)){
                    ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                    Cell cell = row.createCell(colNum);
                    cell.setCellStyle(style);
                    cell.setCellValue(excelColumn.name());
                    colNum++;
                }
            }
            columnSize.set(++colNum);
        }
    }

    private void setColData(Object datum, Row row, CellStyle style) {
        PropertyDescriptor[] targetPd = CopyUtil.getPropertyDescriptors(datum.getClass());
        if(targetPd != null){
            int colNum = 0;
            for (PropertyDescriptor propertyDescriptor : targetPd) {
                Field field = null;
                Class<?> zClass = datum.getClass();
                while (zClass != null){
                    try {
                        field = zClass.getDeclaredField(propertyDescriptor.getName());
                        break;
                    }catch (NoSuchFieldException e){
                        zClass = zClass.getSuperclass();
                    }
                }
                if(field == null){
                    continue;
                }
                if(field.isAnnotationPresent(ExcelColumn.class)){
                    ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                    Method read = propertyDescriptor.getReadMethod();
                    Cell cell = row.createCell(colNum);
                    cell.setCellStyle(style);
                    try {
                        Object val = read.invoke(datum);
                        if(val == null){
                            cell.setBlank();
                        }else if(Number.class.isAssignableFrom(val.getClass())){
                            cell.setCellValue((Double) val);
                        }else if(Date.class.isAssignableFrom(val.getClass())){
                            cell.setCellValue((Date) val);
                        }else if(Boolean.class.isAssignableFrom(val.getClass())){
                            cell.setCellValue((Boolean) val);
                        }else if(excelColumn.image().type() != ImageType.NONE) {
                            switch (excelColumn.image().type()){
                                case URL:
                                    break;
                                case BYTE:
                                    if(val.getClass() == excelColumn.image().type().getAClass()){
                                        setImage((byte[]) val, row.getRowNum(), colNum, row, excelColumn.image());
                                    }
                                    break;
                                case PATH:
                                    break;
                                default:
                                    throw new IllegalArgumentException(excelColumn.image().type().name());
                            }
                        } else {
                            cell.setCellValue((String) val);
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        log.error(e.getMessage(), e);
                    }
                    colNum++;
                }
            }
        }
    }

    private void setImage(byte[] val, int rowNum, int colNum, Row row, ExcelImage image) {
        Cell cell = row.createCell(colNum);
        cell.setBlank();
        row.setHeightInPoints(image.height());
        int pictureIdx = row.getSheet().getWorkbook().addPicture(val, Workbook.PICTURE_TYPE_PNG);
        CreationHelper helper = row.getSheet().getWorkbook().getCreationHelper();
        Drawing drawing = row.getSheet().getWorkbook().getSheetAt(0).createDrawingPatriarch();
        ClientAnchor anchor = helper.createClientAnchor();
        // 图片插入坐标
        anchor.setCol1(colNum);
        anchor.setRow1(rowNum);
        anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
        Picture pict = drawing.createPicture(anchor, pictureIdx);
        pict.resize(1, 1);
    }


    private CellStyle setCellStyle(Workbook wk) {
        CellStyle cellStyle = wk.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置下边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        //设置上边框
        cellStyle.setBorderTop(BorderStyle.THIN);
        //设置左边框
        cellStyle.setBorderLeft(BorderStyle.THIN);
        //设置右边框
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFont(getFont(wk, 38,38,38, IndexedColors.BLACK.index));
        switch (typeThreadLocal.get()){
            case HSSF:
                HSSFWorkbook workbook = (HSSFWorkbook) wk;
                HSSFPalette customPalette = workbook.getCustomPalette();
                customPalette.setColorAtIndex(IndexedColors.DARK_BLUE.index,(byte) 189,(byte) 215,(byte) 238);
                cellStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.index);
                break;
            case XSSF:
                ((XSSFCellStyle) cellStyle).setFillForegroundColor(getColor(189,215,238));
                break;
        }
        return cellStyle;
    }

    private CellStyle setTitleStyle(Workbook wk) {
        CellStyle titleStyle = wk.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置下边框
        titleStyle.setBorderBottom(BorderStyle.THIN);
        //设置上边框
        titleStyle.setBorderTop(BorderStyle.THIN);
        //设置左边框
        titleStyle.setBorderLeft(BorderStyle.THIN);
        //设置右边框
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font = getFont(wk, 255,255,255, IndexedColors.WHITE.index);
        titleStyle.setFont(font);
        font.setFontHeightInPoints((short) 20);
        switch (typeThreadLocal.get()){
            case HSSF:
                HSSFWorkbook workbook = (HSSFWorkbook) wk;
                HSSFPalette customPalette = workbook.getCustomPalette();
                customPalette.setColorAtIndex(IndexedColors.BLUE_GREY.index,(byte) 31,(byte) 78,(byte) 120);
                titleStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.index);
                break;
            case XSSF:
                ((XSSFCellStyle) titleStyle).setFillForegroundColor(getColor(31,78,120));
                break;
        }
        return titleStyle;
    }

    private Font getFont(Workbook wk,int r, int g, int b, short index){
        //设置字体
        Font font = wk.createFont();
        //设置字体名称
        font.setFontName("微软雅黑");
        //设置字号
        font.setFontHeightInPoints((short) 15);
        //设置是否为斜体
        font.setItalic(false);
        //设置是否加粗
        font.setBold(true);
        switch (typeThreadLocal.get()){
            case HSSF:
                HSSFWorkbook workbook = (HSSFWorkbook) wk;
                HSSFPalette customPalette = workbook.getCustomPalette();
                customPalette.setColorAtIndex(index ,(byte) r,(byte) g,(byte) b);
                font.setColor(index);//设置字体颜色
                break;
            case XSSF:
                ((XSSFFont) font).setColor(getColor(r,g,b));
                break;
        }
        return font;

    }




    private XSSFColor getColor(int r, int g, int b){
        byte[] rgb = new byte[3];
        rgb[0] = (byte) r;
        rgb[1] = (byte) g;
        rgb[2] = (byte) b;
        XSSFColor xssfColor = new XSSFColor(rgb,new DefaultIndexedColorMap());
        return xssfColor;
    }
}
