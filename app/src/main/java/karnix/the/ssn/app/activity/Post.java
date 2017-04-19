package karnix.the.ssn.app.activity;

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
}
