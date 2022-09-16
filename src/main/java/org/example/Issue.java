package org.example;

public class Issue {

    private String issue;

    private String severity;

    private String description;

    private String htmlTag;

    public Issue(String issue, String severity, String description, String htmlTag) {
        this.issue = issue;
        this.severity = severity;
        this.description = description;
        this.htmlTag = htmlTag;
    }

    public String getHtmlTag() {
        return htmlTag;
    }

    public String getIssue() {
        return issue;
    }

    public String getDescription() {
        return description;
    }

    public String getSeverity() {
        return severity;
    }
}
