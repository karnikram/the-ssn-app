package karnix.the.ssn.app.model;

public class WebConsolePost {

    private String category;
    private String contactno;
    private String date;
    private String description;
    private String email;
    private String fileURL;
    private String postedby;
    private String title;

    public WebConsolePost() {

    }

    public WebConsolePost(String category, String contactno, String date, String description,
                          String email, String fileURL, String postedby, String title) {
        this.category = category;
        this.contactno = contactno;
        this.date = date;
        this.description = description;
        this.email = email;
        this.fileURL = fileURL;
        this.postedby = postedby;
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String getPostedby() {
        return postedby;
    }

    public void setPostedby(String postedby) {
        this.postedby = postedby;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "WebConsolePost{" +
                "category='" + category + '\'' +
                ", contactno='" + contactno + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", fileURL='" + fileURL + '\'' +
                ", postedby='" + postedby + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
