package com.infodev.sanimaotp.services.adminService.userlog;

import com.infodev.sanimaotp.dao.UserLogRepository;
import com.infodev.sanimaotp.dao.UserRepository;
import com.infodev.sanimaotp.entities.UserLog;
import com.infodev.sanimaotp.pojo.UserLogPojo;
import com.infodev.sanimaotp.pojo.UserLogResponse;
import com.infodev.sanimaotp.services.adminService.user.UserService;
import com.infodev.sanimaotp.services.utils.UserLogConvertor;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserLogServiceImpl implements UserLogService {

    private final UserLogRepository _uLogRepository;
    private final UserRepository _uRepository;

    @Override
    @Transactional
    public void saveUserLog(UserLogPojo userLogPojo) {
        UserLog userLog = UserLogConvertor.userLogPojoToUserLog(userLogPojo);
        _uLogRepository.save(userLog);
    }

    @Override
    public List<UserLogResponse> getAllUserLogs() {
        List<UserLogResponse> allLogs = _uLogRepository.findAllByOrderByRDateTimeDesc()
                .stream()
                .map(log ->
                        UserLogConvertor.userLogToUserLogRes(log))
                .collect(Collectors.toList());

        for (UserLogResponse response : allLogs) {
            response.setTaskBy(_uRepository.findById(Integer.parseInt(response.getTaskBy())).get().getUsername());
        }

        return allLogs;
    }

    @Override
    public Workbook convertToExcelFile(List<UserLogResponse> allUserLogs) {
        final Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Sheet1");
        int flag = 1000;
        int rowIndex = 1;

        for (int i = 0; i < allUserLogs.size(); i++) {
            if (flag == i) {
                flag += 1000;
                sheet = workbook.createSheet("sheet" + i / 1000);
                rowIndex = 1;
            }

            sheet.setColumnWidth(0, 4000);
            sheet.setColumnWidth(1, 4000);
            sheet.setColumnWidth(2, 4000);
            sheet.setColumnWidth(3, 4000);
            sheet.setColumnWidth(4, 4000);
            sheet.setColumnWidth(5, 4000);


            Row header = sheet.createRow(0);

            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("R Date Time");

            headerCell = header.createCell(1);
            headerCell.setCellValue("Task By");

            headerCell = header.createCell(2);
            headerCell.setCellValue("Task Type");

            headerCell = header.createCell(3);
            headerCell.setCellValue("Extra Info 1");

            headerCell = header.createCell(4);
            headerCell.setCellValue("Extra Info 2");

            headerCell = header.createCell(5);
            headerCell.setCellValue("Extra Info 3");


            final Row row = sheet.createRow(rowIndex);
            Cell cell = row.createCell(0);
            cell.setCellValue(String.valueOf(allUserLogs.get(i).getRDateTime()));

            cell = row.createCell(1);
            cell.setCellValue(allUserLogs.get(i).getTaskBy());

            cell = row.createCell(2);
            cell.setCellValue(allUserLogs.get(i).getTaskType());

            cell = row.createCell(3);
            cell.setCellValue(allUserLogs.get(i).getExtraInfo1());

            cell = row.createCell(4);
            cell.setCellValue(allUserLogs.get(i).getExtraInfo2());

            cell = row.createCell(5);
            cell.setCellValue(allUserLogs.get(i).getExtraInfo3());

            rowIndex++;
        }
        return workbook;
    }

}
