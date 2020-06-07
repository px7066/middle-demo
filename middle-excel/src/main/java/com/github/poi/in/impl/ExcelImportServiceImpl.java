package com.github.poi.in.impl;

import com.github.common.utils.CopyUtil;
import com.github.poi.annotation.ExcelImportColumn;
import com.github.poi.enums.ExcelType;
import com.github.poi.in.IExcelImportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>excel导入</p>
 *
 * @author <a href="mailto:xipan@bigvisiontech.com">panxi</a>
 * @version 1.0.0
 * @date 2020/5/28
 */
@Slf4j
public class ExcelImportServiceImpl implements IExcelImportService {

    @Override
    public <T> List<T> importExcelAsList(MultipartFile file, Class<T> tClass) {
        try{
            if(file == null || StringUtils.isEmpty(file)){
                throw new IllegalArgumentException("文件不能为空");
            }
            String fileName = file.getOriginalFilename();
            if(fileName != null && (fileName.endsWith("xls") || fileName.endsWith("xlsx"))){
                ExcelType excelType = ExcelType.HSSF;
                if(fileName.endsWith("xlsx")){
                    excelType = ExcelType.XSSF;
                }
                Workbook wk = null;
                switch (excelType){
                    case HSSF:
                        wk = new HSSFWorkbook(file.getInputStream());
                        break;
                    case XSSF:
                        wk = new XSSFWorkbook(file.getInputStream());
                        break;
                }
                Sheet sheet = wk.getSheetAt(0);
                return readSheetAsList(sheet, tClass);
            }else {
                throw new IllegalArgumentException("文件格式错误，仅支持后缀为xls或xlsx");
            }
        }catch (Exception e){
            log.info(e.getMessage(), e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public <T> T importExcelAsEntity(MultipartFile file, Class<T> tClass) {
        try {
            if(file == null || StringUtils.isEmpty(file)){
                throw new IllegalArgumentException("文件不能为空");
            }
            String fileName = file.getOriginalFilename();
            if(fileName != null && (fileName.endsWith("xls") || fileName.endsWith("xlsx"))){
                ExcelType excelType = ExcelType.HSSF;
                if(fileName.endsWith("xlsx")){
                    excelType = ExcelType.XSSF;
                }
                Workbook wk = null;
                switch (excelType){
                    case HSSF:
                        wk = new HSSFWorkbook(file.getInputStream());
                        break;
                    case XSSF:
                        wk = new XSSFWorkbook(file.getInputStream());
                        break;
                }
                Sheet sheet = wk.getSheetAt(0);
                return readSheetAsEntity(sheet, tClass);
            }else {
                throw new IllegalArgumentException("文件格式错误，仅支持后缀为xls或xlsx");
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private <T> List<T> readSheetAsList(Sheet sheet, Class<T> tClass) {
        List<T> list = new ArrayList<>();
        int lastRow = sheet.getLastRowNum();
        for(int i=0; i< lastRow; i++){
            Row row = sheet.getRow(i);
            T t = readRowAsList(row, tClass);
            list.add(t);
        }
        return list;
    }

    private <T> T readRowAsList(Row row, Class<T> tClass) {
        try {
            Constructor<T> constructor = tClass.getConstructor();
            constructor.setAccessible(true);
            T t = tClass.newInstance();
            PropertyDescriptor[] targetPd = CopyUtil.getPropertyDescriptors(tClass);
            if(targetPd != null){
                for (PropertyDescriptor propertyDescriptor : targetPd) {
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    Field field = null;
                    Class<?> zClass = tClass;
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
                    if(field.isAnnotationPresent(ExcelImportColumn.class)){
                        ExcelImportColumn excelImportColumn = field.getAnnotation(ExcelImportColumn.class);
                        if(excelImportColumn.need()){
                            setValue(row, t, writeMethod, excelImportColumn);
                        }

                    }
                }
            }
            return t;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private <T> void setValue(Row row, T t, Method writeMethod, ExcelImportColumn excelImportColumn) throws IllegalAccessException, InvocationTargetException {
        Cell cell = row.getCell(excelImportColumn.colNum());
        if(cell != null){
            Parameter[] parameters = writeMethod.getParameters();
            Parameter parameter = parameters[0];
            switch (cell.getCellType()){
                case _NONE:
                case BLANK:
                    writeMethod.invoke(t, (Object) null);
                    break;
                case STRING:
                    String sValue = cell.getStringCellValue();
                    try{
                        if(String.class.isAssignableFrom(parameter.getType())){
                            writeMethod.invoke(t, sValue);
                        }else if (Integer.class.isAssignableFrom(parameter.getType())) {
                            writeMethod.invoke(t, Integer.valueOf(sValue));
                        }else if(Long.class.isAssignableFrom(parameter.getType())){
                            writeMethod.invoke(t, Long.valueOf(sValue));
                        }else if(Float.class.isAssignableFrom(parameter.getType())){
                            writeMethod.invoke(t, Float.valueOf(sValue));
                        }else if(Double.class.isAssignableFrom(parameter.getType())){
                            writeMethod.invoke(t, Double.valueOf(sValue));
                        }else if(Date.class.isAssignableFrom(parameter.getType())){
                            String format = excelImportColumn.format().format();
                            SimpleDateFormat sdf = new SimpleDateFormat(format);
                            writeMethod.invoke(t, sdf.parse(sValue));
                        }
                    }catch (Exception e){
                        log.error(e.getMessage(), e);
                    }
                    break;
                case BOOLEAN:
                    writeMethod.invoke(t, cell.getBooleanCellValue());
                    break;
                case FORMULA:
                    writeMethod.invoke(t, cell.getCellFormula());
                    break;
                case NUMERIC:
                    double dValue = cell.getNumericCellValue();
                    if(String.class.isAssignableFrom(parameter.getType())){
                        writeMethod.invoke(t, String.valueOf(dValue));
                    }else if (Integer.class.isAssignableFrom(parameter.getType())) {
                        writeMethod.invoke(t, Double.valueOf(dValue).intValue());
                    }else if(Long.class.isAssignableFrom(parameter.getType())){
                        writeMethod.invoke(t, Double.valueOf(dValue).longValue());
                    }else if(Float.class.isAssignableFrom(parameter.getType())){
                        writeMethod.invoke(t, Double.valueOf(dValue).floatValue());
                    }else if(Double.class.isAssignableFrom(parameter.getType())){
                        writeMethod.invoke(t, dValue);
                    }
                    break;
            }
        }
    }

    private <T> T readSheetAsEntity(Sheet sheet, Class<T> tClass) {
        try{
            Constructor<T> constructor = tClass.getConstructor();
            constructor.setAccessible(true);
            T t = tClass.newInstance();
            PropertyDescriptor[] targetPd = CopyUtil.getPropertyDescriptors(tClass);
            if(targetPd != null){
                for (PropertyDescriptor propertyDescriptor : targetPd) {
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    Field field = null;
                    Class<?> zClass = tClass;
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
                    if(field.isAnnotationPresent(ExcelImportColumn.class)){
                        ExcelImportColumn excelImportColumn = field.getAnnotation(ExcelImportColumn.class);
                        if(excelImportColumn.need()){
                            int rowNum = excelImportColumn.rowNum();
                            Row row = sheet.getRow(rowNum);
                            if(row != null){
                                setValue(row, t, writeMethod, excelImportColumn);
                            }
                        }

                    }
                }
            }
            return t;
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return null;
    }

}
