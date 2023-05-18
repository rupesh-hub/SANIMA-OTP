package com.infodev.sanimaotp.services.apiService.exportXcel;

import com.infodev.sanimaotp.entities.DigipassStatus;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ExportExcel {

    public Workbook convertToExcelFile(List<DigipassStatus> recordList) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        int flag = 1000;
        int rowIndex = 1;
        for (int i = 0; i < recordList.size(); i++) {
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


            Row header = sheet.createRow(0);

            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("SerialNumber");

            headerCell = header.createCell(1);
            headerCell.setCellValue("MobileId");

            headerCell = header.createCell(2);
            headerCell.setCellValue("KioskId");

            headerCell = header.createCell(3);
            headerCell.setCellValue("AtmId");

            headerCell = header.createCell(4);
            headerCell.setCellValue("VbvId");

            headerCell = header.createCell(5);
            headerCell.setCellValue("InternetBankId");

            headerCell = header.createCell(6);
            headerCell.setCellValue("Vbv_CR_Id");

            headerCell = header.createCell(7);
            headerCell.setCellValue("Kiosk_CR_Id");

            headerCell = header.createCell(8);
            headerCell.setCellValue("Central");

            headerCell = header.createCell(9);
            headerCell.setCellValue("Status");

            Row row = sheet.createRow(rowIndex);
            Cell cell = row.createCell(0);
            cell.setCellValue(String.valueOf(recordList.get(i).getSerialNumber()));
            cell = row.createCell(1);
            cell.setCellValue(recordList.get(i).getApp1Mb());
            cell = row.createCell(2);
            cell.setCellValue(recordList.get(i).getApp2Kiosk());
            cell = row.createCell(3);
            cell.setCellValue(recordList.get(i).getApp3Atm());
            cell = row.createCell(4);
            cell.setCellValue(recordList.get(i).getApp4Vbv());
            cell = row.createCell(5);
            cell.setCellValue(recordList.get(i).getApp5Ibank());
            cell = row.createCell(6);
            cell.setCellValue(recordList.get(i).getApp6VbvCr());
            cell = row.createCell(7);
            cell.setCellValue(recordList.get(i).getApp7Kioskcr());
            cell = row.createCell(8);
            cell.setCellValue(recordList.get(i).getCentralId());
            cell = row.createCell(9);
            cell.setCellValue(recordList.get(i).getStatus());

            rowIndex++;
        }
        return workbook;
    }

}
