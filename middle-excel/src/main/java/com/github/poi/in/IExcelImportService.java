package com.github.poi.in;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>excel导入公共方法</p>
 *
 * @author <a href="mailto:xipan@bigvisiontech.com">panxi</a>
 * @version 1.0.0
 * @date 2020/5/28
 */
public interface IExcelImportService {
    <T>List<T> importExcelAsList(MultipartFile file, Class<T> tClass);

    <T> T importExcelAsEntity(MultipartFile file, Class<T> tClass);
}
