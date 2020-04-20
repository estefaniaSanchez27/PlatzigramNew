package platzi.com.platzigramnew.model;

public class Picture {
    private String picture;
    private String username;
    private String time;
    private String likenumber = "0";


    public Picture(String picture, String username, String time, String likenumber) {
        this.picture = picture;
        this.username = username;
        this.time = time;
        this.likenumber = likenumber;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLikenumber() {
        return likenumber;
    }

    public void setLikenumber(String likenumber) {
        this.likenumber = likenumber;
    }
}
