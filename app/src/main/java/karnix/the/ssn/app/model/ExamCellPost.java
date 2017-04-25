package karnix.the.ssn.app.model;

/**
 * Created by vvvro on 4/25/2017.
 */

public class ExamCellPost {
    public Post post;
    public String pdfLink;

    public ExamCellPost() {

    }

    public ExamCellPost(Post post, String pdfLink) {
        this.post = post;
        this.pdfLink = pdfLink;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink;
    }
}
