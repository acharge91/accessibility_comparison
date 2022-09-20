package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class DataVisualisation {

    public static HashMap<String, HashMap<Issue, Integer>> totalFixedIssues(ProductHandler productHandler1, ProductHandler productHandler2) {
        HashMap<String, HashMap<Issue, Integer>> productFixedIssuesMap = new HashMap<>();
        for (Product product1 : productHandler1.getProducts()) {
            Product product2 = productHandler2.findProduct(product1.getProductName());
            if (product2 != null) {
                for (Page page : product1.getPages()) {
                    if (product2.findPage(page.getName()) != null) {
                        HashMap<Issue, Integer> fixedIssuesMap = fixedIssues(page, product2.findPage(page.getName()));
                        if (!fixedIssuesMap.isEmpty() && productFixedIssuesMap.containsKey(product2.getProductName())) {
                            fixedIssuesMap.forEach((key, value) -> productFixedIssuesMap.get(product2.getProductName()).merge(key, value, Integer::sum));
                        } else if (!fixedIssuesMap.isEmpty() && !productFixedIssuesMap.containsKey(product2.productName)) {
                            productFixedIssuesMap.put(product2.getProductName(), fixedIssuesMap);
                        }
                    }
                }
            }
        }
        return productFixedIssuesMap;
    }

    public static HashMap<String, HashMap<Issue, Integer>> totalNewIssues(ProductHandler productHandler1, ProductHandler productHandler2) {
        HashMap<String, HashMap<Issue, Integer>> productNewIssuesMap = new HashMap<>();
        for (Product product1 : productHandler1.getProducts()) {
            Product product2 = productHandler2.findProduct(product1.getProductName());
            if (product2 != null) {
                for (Page page : product1.getPages()) {
                    if (product2.findPage(page.getName()) != null) {
                        HashMap<Issue, Integer> newIssuesMap = newIssues(page, product2.findPage(page.getName()));
                        if (!newIssuesMap.isEmpty() && productNewIssuesMap.containsKey(product1.productName)) {
                            newIssuesMap.forEach((key, value) -> productNewIssuesMap.get(product1.productName).merge(key, value, Integer::sum));
                        } else if (!newIssuesMap.isEmpty() && !productNewIssuesMap.containsKey(product1.getProductName())) {
                            productNewIssuesMap.put(product1.getProductName(), newIssuesMap);
                        }
                    }
                }
            }
        }
        return productNewIssuesMap;
    }

    public static HashMap<Issue, Integer> fixedIssues(Page page1, Page page2) {
        HashMap<Issue, Integer> fixedIssuesMap = new HashMap<>();
        for(Issue issue : page1.getIssues()) {
            if (page2.findIssue(issue) == null ) fixedIssuesMap.merge(issue, 1, Integer::sum);
        }
        return fixedIssuesMap;
    }

    public static HashMap<Issue, Integer> newIssues(Page page1, Page page2) {
        HashMap<Issue, Integer> newIssuesMap = new HashMap<>();
        for(Issue issue : page2.getIssues()) {
            if (page1.findIssue(issue) == null ) newIssuesMap.merge(issue, 1, Integer::sum);
        }
        return newIssuesMap;
    }

    public static HashMap<String, HashMap<Issue, Integer>> allProductIssues(ProductHandler productHandler) {
        HashMap<String, HashMap<Issue, Integer>> allProductIssuesMap = new HashMap<>();
        for (Product product : productHandler.getProducts()) {
            HashMap<Issue, Integer> issuesMap = new HashMap<>();
            for (Page page : product.getPages()) {
                for (Issue issue : page.getIssues()) {
                    issuesMap.merge(issue, 1, Integer::sum);
                }
            }
            allProductIssuesMap.put(product.getProductName(), issuesMap);
        }
        return allProductIssuesMap;
    }

    public static HashMap<String, HashMap<String, Integer>> productSeverityCounter(ProductHandler productHandler) {
        HashMap<String, HashMap<String, Integer>> severityMap = new HashMap<>();
        for (Product product : productHandler.getProducts()) {
            HashMap<String, Integer> severityCountMap = new HashMap<>();
            for (Page page : product.getPages()) {
                for (Issue issue : page.getIssues()) {
                    severityCountMap.merge(issue.getSeverity(), 1, Integer::sum);
                }
            }
            severityMap.put(product.getProductName(), severityCountMap);
        }
        return severityMap;
    }
    public static HashMap<String, Integer> productSpecificSeverityCounter(ProductHandler productHandler, String severity) {
        HashMap<String, Integer> severityMap = new HashMap<>();
        for (Product product : productHandler.getProducts()) {
            for (Page page : product.getPages()) {
                for (Issue issue : page.getIssues()) {
                    if (issue.getSeverity().equals(severity)) {
                        severityMap.merge(product.getProductName(), 1, Integer::sum);
                    }

                }
            }
        }
        return severityMap;
    }


}
