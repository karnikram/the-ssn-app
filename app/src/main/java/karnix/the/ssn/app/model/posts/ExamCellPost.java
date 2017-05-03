package karnix.the.ssn.app.model.posts;

/**
 * Created by vvvro on 4/25/2017.
 */

public class ExamCellPost {
    public Post post;
    public String title;
    public String pdfLink;

    public ExamCellPost() {

    }

    public ExamCellPost(Post post, String title, String pdfLink) {
        this.post = post;
        this.title = title;
        this.pdfLink = pdfLink;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
