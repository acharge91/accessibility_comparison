package org.example;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReader {

    public static XSSFSheet getSheet(File selectedFile) throws IOException {
        FileInputStream fis = new FileInputStream(selectedFile);

        XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);

        return myWorkBook.getSheetAt(0);
    }


}
