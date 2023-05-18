package com.infodev.sanimaotp.services.utils;

import com.infodev.sanimaotp.dao.ActivityLogRepository;
import com.infodev.sanimaotp.entities.DataLogger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogActivityService {
    @Autowired
    private ActivityLogRepository activityLogRepository;

    public void saveActivity(DataLogger dataLogger) {
        try {
            activityLogRepository.save(dataLogger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<DataLogger> getActivityLogs() {
        return activityLogRepository.findAll();
    }

    public Workbook convertToExcelFile(List<DataLogger> activityLogs) {
        final Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Sheet1");
        int flag = 1000;
        int rowIndex = 1;

        for (int i = 0; i < activityLogs.size(); i++) {
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
            sheet.setColumnWidth(6, 4000);
            sheet.setColumnWidth(7, 4000);
            sheet.setColumnWidth(8, 4000);
            sheet.setColumnWidth(9, 4000);
            sheet.setColumnWidth(10, 4000);
            sheet.setColumnWidth(11, 4000);


            Row header = sheet.createRow(0);


            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("Central Id");

            headerCell = header.createCell(1);
            headerCell.setCellValue("Task By");

            headerCell = header.createCell(2);
            headerCell.setCellValue("Channel Id");

            headerCell = header.createCell(3);
            headerCell.setCellValue("Application Id");

            headerCell = header.createCell(4);
            headerCell.setCellValue("Activity Description");

            headerCell = header.createCell(5);
            headerCell.setCellValue("Activity Status");

            headerCell = header.createCell(6);
            headerCell.setCellValue("Log Type");

            headerCell = header.createCell(7);
            headerCell.setCellValue("Extra Info 1");

            headerCell = header.createCell(8);
            headerCell.setCellValue("Extra Info 2");

            headerCell = header.createCell(9);
            headerCell.setCellValue("Extra Info 3");

            headerCell = header.createCell(10);
            headerCell.setCellValue("R Date");

            headerCell = header.createCell(11);
            headerCell.setCellValue("R Time");

            final Row row = sheet.createRow(rowIndex);
            Cell cell = row.createCell(0);
            cell.setCellValue(String.valueOf(activityLogs.get(i).getCentralId()));

            cell = row.createCell(1);
            cell.setCellValue(activityLogs.get(i).getTaskBy());

            cell = row.createCell(2);
            cell.setCellValue(activityLogs.get(i).getChannelId());

            cell = row.createCell(3);
            cell.setCellValue(activityLogs.get(i).getApplicationId());

            cell = row.createCell(4);
            cell.setCellValue(activityLogs.get(i).getActivityDescription());

            cell = row.createCell(5);
            cell.setCellValue(activityLogs.get(i).getActivityStatus());

            cell = row.createCell(6);
            cell.setCellValue(activityLogs.get(i).getLogType());

            cell = row.createCell(7);
            cell.setCellValue(activityLogs.get(i).getExtraInfo1());

            cell = row.createCell(8);
            cell.setCellValue(activityLogs.get(i).getExtraInfo2());

            cell = row.createCell(9);
            cell.setCellValue(activityLogs.get(i).getExtraInfo3());

            cell = row.createCell(10);
            cell.setCellValue(activityLogs.get(i).getrDate());

            cell = row.createCell(11);
            cell.setCellValue(activityLogs.get(i).getrTime());

            rowIndex++;
        }
        return workbook;
    }
}
