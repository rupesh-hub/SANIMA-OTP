package com.infodev.sanimaotp.controller.AdminController.UserLogController;

import com.infodev.sanimaotp.controller.base.BaseController;
import com.infodev.sanimaotp.enums.ModuleEnum;
import com.infodev.sanimaotp.services.adminService.userlog.UserLogService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/userLog_")
public class UserLogController extends BaseController {

    private final UserLogService userLogService;

    @Autowired
    public UserLogController(UserLogService userLogService) {
        this.userLogService = userLogService;
        this.module1 = ModuleEnum.USER_LOG.getAbbreviation();
    }

    @GetMapping
    @PreAuthorize("hasPermission(#this.this.module1,'')")
    public String getLogDetails(final Model model) {
        model.addAttribute("userLogs",userLogService.getAllUserLogs());
        return "pages/userLog";
    }

    @GetMapping("/export-excel")
    public void export(HttpServletResponse response) {
        try {
            Workbook workbook = userLogService.convertToExcelFile(userLogService.getAllUserLogs());
            String fileName = "user-log.xlsx";
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
