package org.example;

import java.util.ArrayList;

public class Page {

    private String name;
    private ArrayList<Issue> issues = new ArrayList<>();

    public String getName() {
        return name;
    }


    public Page(String name, Issue issue) {
        this.name = name;
        this.issues.add(issue);
    }

    public void addIssue(Issue issue) {
        this.issues.add(issue);
    }

    public ArrayList<Issue> getIssues() {
        return issues;
    }

    public Issue findIssue(Issue issueToFind) {
        for (Issue issue : issues) {
            if (issue.getIssue().equals(issueToFind.getIssue()) && issue.getHtmlTag().equals(issueToFind.getHtmlTag())) return issue;
        }
        return null;
    }
}
