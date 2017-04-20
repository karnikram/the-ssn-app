package karnix.the.ssn.app.model;

/**
 * Created by vvvro on 4/2/2017.
 */

public class User {
    public String uid;
    public String userName;
    public String userImage;
    public String userEmail;

    public User() {

    }

    public User(String uid, String userName, String userImage, String userEmail) {
        this.uid = uid;
        this.userName = userName;
        this.userImage = userImage;
        this.userEmail = userEmail;
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

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", userName='" + userName + '\'' +
                ", userImage='" + userImage + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}