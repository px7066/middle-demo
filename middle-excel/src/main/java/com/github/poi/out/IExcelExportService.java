package com.github.poi.out;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>excel导出</p>
 *
 * @author <a href="mailto:xipan@bigvisiontech.com">panxi</a>
 * @version 1.0.0
 * @date 2020/5/12 14:09
 * @since 1.0
 */
public interface IExcelExportService {
    void export(List data, HttpServletResponse response);
}
