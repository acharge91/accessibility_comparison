package org.example;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;

public class ProductHandler {

    private ArrayList<Product> products = new ArrayList<>();

    public ProductHandler(XSSFSheet mySheet) {
        dataTransferObject(mySheet);
    }

    private void dataTransferObject(XSSFSheet mySheet) {
        int rows = mySheet.getLastRowNum();
        int cols = mySheet.getRow(1).getLastCellNum();

        ArrayList<String> headers = new ArrayList<>();
        XSSFRow headerRow = mySheet.getRow(0);
        for (int c = 0; c < cols; c++) {
            headers.add(headerRow.getCell(c).getStringCellValue());
        }

        for (int r = 1; r <= rows; r++) {
            XSSFRow row = mySheet.getRow(r);
            Issue newIssue = createIssue(headers, row);

            Page newPage = createNewPage(headers, row, newIssue);

            if (newPage != null) {

                Product newProduct = createProduct(headers, row, newPage);
                if (newProduct != null) {
                    products.add(newProduct);
                }
            }
        }
    }

    private Product createProduct(ArrayList<String> headers, XSSFRow row, Page newPage) {
        String productName = row.getCell(headers.indexOf("Product")).getStringCellValue();

        if (findProduct(productName) == null) {
            return new Product(row.getCell(headers.indexOf("Product")).getStringCellValue(), newPage);
        } else {
            findProduct(productName).addPage(newPage);
            return null;
        }
    }

    private Page createNewPage(ArrayList<String> headers, XSSFRow row, Issue newIssue) {

        String pageName = row.getCell(headers.indexOf("Page")).getStringCellValue();
        for (Product product : products) {

            if (product.findPage(pageName) != null) {
                product.findPage(pageName).addIssue(newIssue);
                return null;
            }
        }
        return new Page(pageName, newIssue);
    }

    private Issue createIssue(ArrayList<String> headers, XSSFRow row) {

        String issue = row.getCell(headers.indexOf("Issue")).getStringCellValue();
        String severity = row.getCell(headers.indexOf("Severity")).getStringCellValue();
        String description = row.getCell(headers.indexOf("Description")).getStringCellValue();
        String htmlTag = row.getCell(headers.indexOf("Html Tag")).getStringCellValue();
        return new Issue(issue, severity, description, htmlTag);
    }

    public Product findProduct(String productToFind) {
        for (Product product : products) {
            if (productToFind.equals(product.getProductName())) return product;
        }
        return null;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

}
