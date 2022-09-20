package org.example;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataChart {

    private static String fileSaveDirectory = "/Users/acharge/Downloads/";
    private static String fileExtension = ".jpeg";

    private static void singlePagePieChart(HashMap<Issue, Integer> hashMapData, Page page, DefaultPieDataset pieDataSet, String issueStatus) {
        for (Map.Entry<Issue, Integer> entry : hashMapData.entrySet()) {
            String issue = entry.getKey().getIssue();
            Integer count = entry.getValue();
            pieDataSet.setValue(issue + count, count);
        }

        JFreeChart chart = ChartFactory.createPieChart3D(
            page.getName() + " " + issueStatus ,
            pieDataSet,
            true,
            true,
            false
        );

        try {
            ChartUtilities.saveChartAsJPEG(
                    new File(fileSaveDirectory + page.getName() + issueStatus + fileExtension),
                    chart,
                    500,
                    500
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void singleProductPieChart(HashMap<Issue, Integer> hashMapData, Product product, DefaultPieDataset pieDataSet, String issueStatus) {
        for (Map.Entry<Issue, Integer> entry : hashMapData.entrySet()) {
            String issue = entry.getKey().getIssue();
            Integer count = entry.getValue();
            pieDataSet.setValue(issue + count, count);
        }

        JFreeChart chart = ChartFactory.createPieChart3D(
                product.getProductName() + " " + issueStatus ,
                pieDataSet,
                true,
                true,
                false
        );

        try {
            ChartUtilities.saveChartAsJPEG(
                    new File(fileSaveDirectory + product.getProductName() + issueStatus + fileExtension),
                    chart,
                    500,
                    500
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void totalFixedVsNewIssuesPieChart(int totalNewIssues, int totalFixedIssues, DefaultPieDataset pieDataSet) {
        pieDataSet.setValue("Total Fixed Issues " + totalFixedIssues, totalFixedIssues);
        pieDataSet.setValue("Total New Issues " + totalNewIssues, totalNewIssues);

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Total Fixed Vs New Issues",
                pieDataSet,
                true,
                true,
                false
        );
        try {
            ChartUtilities.saveChartAsJPEG(
                    new File(fileSaveDirectory + "total_fixed_vs_new_issues" + fileExtension),
                    chart,
                    500,
                    400
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void singlePageFixedVsNewIssuesPieChart(int fixedIssues, int newIssues, Page page, DefaultPieDataset pieDataSet) {
        pieDataSet.setValue("Total Fixed Issues", fixedIssues);
        pieDataSet.setValue("Total New Issues", newIssues);

        JFreeChart chart = ChartFactory.createPieChart3D(
                page.getName() + " Total Fixed Vs New Issues",
                pieDataSet,
                true,
                true,
                false
        );
        try {
            ChartUtilities.saveChartAsJPEG(
                    new File("src/" + page.getName() + "fixed_vs_new_issues.jpeg"),
                    chart,
                    500,
                    300
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void productSeverityGraph(HashMap<String, HashMap<String, Integer>> productSeverityMap){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, HashMap<String, Integer>> entry : productSeverityMap.entrySet()) {
            String productName = entry.getKey();
            HashMap<String, Integer> severityMap = entry.getValue();
            for (Map.Entry<String, Integer> severity : severityMap.entrySet()) {
                String severityLevel = severity.getKey();
                Integer severityCount = severity.getValue();
                dataset.addValue(severityCount, severityLevel, productName);
            }

        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Product Issue Severity Levels",
                "Severity",
                "Severity Issue Amount",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        try {
            ChartUtilities.saveChartAsJPEG(
                    new File(fileSaveDirectory + "product_severity_levels" + fileExtension),
                    chart,
                    2000,
                    800
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void productSeverityComparisonGraph(HashMap<String, Integer> product1SeverityMap, HashMap<String, Integer> product2SeverityMap, String severityLevel){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : product2SeverityMap.entrySet()) {
            String product2Name = entry.getKey();
            int product2SeverityCount = entry.getValue();
            if (product1SeverityMap.containsKey(product2Name)) {
                String product1Name = product2Name;

                dataset.addValue(product1SeverityMap.get(product1Name), "Document 1", product1Name);
                dataset.addValue(product2SeverityCount, "Document 2", product2Name);

            }

        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Product Issue " + severityLevel + " Severity Levels",
                "No. of " + severityLevel + " Level Issues",
                 "Document 1 vs Document 2 " + severityLevel + " Issues",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        try {
            ChartUtilities.saveChartAsJPEG(
                    new File(fileSaveDirectory + "product_severity_levels_comparison" + fileExtension),
                    chart,
                    2000,
                    800
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void productIssuesGraph(ProductHandler productHandler) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String productName = "";
        HashMap<String, Integer> issueMap = new HashMap<>();
        for (Product product : productHandler.getProducts()) {
            if (product.getProductName().equals("family")) {
                productName = product.productName;
                for (Page page : product.getPages()) {
                    for (Issue issue : page.getIssues()) {
                        String issueName = issue.getIssue();
                        issueMap.merge(issueName, 1, Integer::sum);
                    }
                }
                break;
            }
        }
        for (Map.Entry<String, Integer> entry : issueMap.entrySet()) {
            String issue = entry.getKey();
            Integer count = entry.getValue();
            dataset.setValue(count, issue, productName);
        }




        JFreeChart chart = ChartFactory.createBarChart(
                "product: " + productName +" Issues",
                "Issue",
                "Number of Issues",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        try {
            ChartUtilities.saveChartAsJPEG(
                    new File(fileSaveDirectory + "product_" + productName + "_issues" + fileExtension),
                    chart,
                    500,
                    300
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void singlePageFixedIssues(HashMap<Issue, Integer> fixedIssuesData, Page page) {
        DefaultPieDataset pieDataSet = new DefaultPieDataset();
        singlePagePieChart(fixedIssuesData, page, pieDataSet, "Fixed_Issues");
    }


    public static void singlePageNewIssues(HashMap<Issue, Integer> fixedIssuesData, Page page) {
        DefaultPieDataset pieDataSet = new DefaultPieDataset();
        singlePagePieChart(fixedIssuesData, page, pieDataSet, "New_Issues");
    }

    public static void totalFixedVsNewIssues(HashMap<String, HashMap<Issue, Integer>> fixedIssuesData, HashMap<String, HashMap<Issue, Integer>> newIssuesData) {
        DefaultPieDataset pieDataSet = new DefaultPieDataset();
        int totalFixedIssues = 0;
        int totalNewIssues = 0;
        for (Map.Entry<String, HashMap<Issue, Integer>> entry : fixedIssuesData.entrySet()) {
            HashMap<Issue, Integer> issues = entry.getValue();
            for (Map.Entry<Issue, Integer> issue : issues.entrySet()) {
                totalFixedIssues += issue.getValue();
            }
        }
        for (Map.Entry<String, HashMap<Issue, Integer>> entry : newIssuesData.entrySet()) {
            HashMap<Issue, Integer> issues = entry.getValue();
            for (Map.Entry<Issue, Integer> issue : issues.entrySet()) {
                totalNewIssues += issue.getValue();
            }
        }
        totalFixedVsNewIssuesPieChart(totalFixedIssues, totalNewIssues, pieDataSet);
    }

    public static void singlePageFixedVsNewIssues(HashMap<Issue, Integer> fixedIssuesData, HashMap<Issue, Integer> newIssuesData, Page page) {
        DefaultPieDataset pieDataSet = new DefaultPieDataset();
        singlePageFixedVsNewIssuesPieChart(fixedIssuesData.size(), newIssuesData.size(), page, pieDataSet);
    }

    public static void productFixedIssues(Product product, HashMap<Issue, Integer> fixedIssuesData) {
        DefaultPieDataset pieDataSet = new DefaultPieDataset();
        singleProductPieChart(fixedIssuesData, product, pieDataSet, "fixed_issues");
    }

    public static void productNewIssues(Product product, HashMap<Issue, Integer> newIssuesData) {
        DefaultPieDataset pieDataSet = new DefaultPieDataset();
        singleProductPieChart(newIssuesData, product, pieDataSet, "new_issues");
    }


}
