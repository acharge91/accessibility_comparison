package org.example;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ExcelGui extends JFrame {
    private JButton importFile1;
    private JPanel panelMain;
    private JButton importFile2;
    private JButton runProgramButton;
    private JButton productIssuesButton;
    private JButton totalFixedVsNewButton;
    private JButton productIssueSeverityButton;
    private ProductHandler productHandler1;
    private ProductHandler productHandler2;
    private File selectedFile1;
    private File selectedFile2;

    public ExcelGui(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelMain);
        this.pack();
        importFile1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));
                fileChooser.setAcceptAllFileFilterUsed(true);
                int result = fileChooser.showOpenDialog(panelMain);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile1 = fileChooser.getSelectedFile();
                    System.out.println("Selected file 1: " + selectedFile1.getAbsolutePath());
                }
            }
        });
        importFile2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));
                fileChooser.setAcceptAllFileFilterUsed(true);
                int result = fileChooser.showOpenDialog(panelMain);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile2 = fileChooser.getSelectedFile();
                    System.out.println("Selected file 2: " + selectedFile2.getAbsolutePath());
                }
            }
        });
        runProgramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                XSSFSheet mySheet1 = null;
                try {
                    mySheet1 = FileReader.getSheet(selectedFile1);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                XSSFSheet mySheet2 = null;
                try {
                    mySheet2 = FileReader.getSheet(selectedFile2);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                productHandler1 = new ProductHandler(mySheet1);
                productHandler2 = new ProductHandler(mySheet2);
            }
        });
        productIssuesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataChart.productIssuesGraph(productHandler2);
            }
        });
        totalFixedVsNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, HashMap<Issue, Integer>> totalFixedIssues = DataVisualisation.totalFixedIssues(
                        productHandler1, productHandler2
                );
                HashMap<String, HashMap<Issue, Integer>> totalNewIssues = DataVisualisation.totalNewIssues(
                        productHandler1, productHandler2
                );
                DataChart.totalFixedVsNewIssues(totalFixedIssues, totalNewIssues);
            }
        });

        productIssueSeverityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, Integer> product1SeverityMap = DataVisualisation.productSpecificSeverityCounter(
                        productHandler1, "critical"
                );
                HashMap<String, Integer> product2SeverityMap = DataVisualisation.productSpecificSeverityCounter(
                        productHandler2, "critical"
                );
                DataChart.productSeverityComparisonGraph(product1SeverityMap, product2SeverityMap, "critical");
            }
        });
    }



}

