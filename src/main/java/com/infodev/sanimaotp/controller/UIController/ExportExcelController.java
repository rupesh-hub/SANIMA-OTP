package com.infodev.sanimaotp.controller.UIController;

import com.infodev.sanimaotp.services.adminService.ListDigipassKeysService;
import com.infodev.sanimaotp.services.apiService.exportXcel.ExportExcel;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public final class ExportExcelController {

    private final ListDigipassKeysService listDigipassKeysService;
    private final ExportExcel exportExcel;

    @Autowired
    public ExportExcelController(ListDigipassKeysService listDigipassKeysService,
                                 ExportExcel exportExcel) {
        this.listDigipassKeysService = listDigipassKeysService;
        this.exportExcel = exportExcel;
    }

    @GetMapping("/export-excel")
    public void export(HttpServletResponse response) {
        try {
            Workbook workbook = exportExcel.convertToExcelFile(listDigipassKeysService.getAllDigipass());
            String fileName = "digipass-sheet.xlsx";
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= " + fileName);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
