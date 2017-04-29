package karnix.the.ssn.app.model.posts;

/**
 * Created by vvvro on 4/2/2017.
 */

public class Post {
    public String pid;
    public String uid;
    public String userName;
    public Long postedDate;
    public String content;
    public String userProfileURL;
    public String imageURL;

    public int type;

    public Post() {

    }

    public Post(String postID, String userName, String userID, Long postDate, String profileImageURL, String postContent) {
        pid = postID;
        uid = userID;
        this.userName = userName;
        postedDate = postDate;
        userProfileURL = profileImageURL;
        content = postContent;
        type = 1;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Long postedDate) {
        this.postedDate = postedDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserProfileURL() {
        return userProfileURL;
    }

    public void setUserProfileURL(String userProfileURL) {
        this.userProfileURL = userProfileURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Post{" +
                "pid='" + pid + '\'' +
                ", uid='" + uid + '\'' +
                ", userName='" + userName + '\'' +
                ", postedDate=" + postedDate +
                ", content='" + content + '\'' +
                ", userProfileURL='" + userProfileURL + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", type=" + type +
                '}';
    }
}
