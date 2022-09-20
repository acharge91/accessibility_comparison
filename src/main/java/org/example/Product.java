package org.example;

import java.util.ArrayList;

public class Product {
    String productName;
    ArrayList<Page> pages = new ArrayList<>();

    public Product(String productName, Page page) {
        this.productName = productName;
        this.pages.add(page);
    }

    public void addPage(Page page) {
        pages.add(page);
    }

    public String getProductName() {
        return productName;
    }

    public ArrayList<Page> getPages() {
        return pages;
    }

    public Page findPage(String pageToFind) {
        for(Page page : pages) {
            if (page.getName().equals(pageToFind)) return page;
        }
        return null;
    }


}
