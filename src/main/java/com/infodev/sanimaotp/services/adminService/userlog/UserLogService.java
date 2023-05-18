package com.infodev.sanimaotp.services.adminService.userlog;

import com.infodev.sanimaotp.entities.UserLog;
import com.infodev.sanimaotp.pojo.UserLogPojo;
import com.infodev.sanimaotp.pojo.UserLogResponse;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface UserLogService {

    void saveUserLog(UserLogPojo userLogPojo);
    List<UserLogResponse> getAllUserLogs();
    Workbook convertToExcelFile(List<UserLogResponse> allUserLogs);
}
