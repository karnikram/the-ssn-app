package karnix.the.ssn.app.model;

/**
 * Created by adithya321 on 4/28/17.
 */

public class Node {

    private String pid;

    public Node() {
    }

    public Node(String pid) {
        this.pid = pid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "Node{" +
                "pid='" + pid + '\'' +
                '}';
    }
}
