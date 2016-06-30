package in.msomu.materialstackoverflow.models;

import android.text.format.DateUtils;

/**
 * Created by msomu on 30/06/16.
 * Question Model for stack overflow
 */
public class Question {
    private String question;
    private String userName;
    private String timeStamp;
    private int upvotes;
    private String[] tags;

    public Question(String question, String userName, String timeStamp, int upvotes, String[] tags) {
        this.question = question;
        this.userName = userName;
        this.timeStamp = timeStamp;
        this.upvotes = upvotes;
        this.tags = tags;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTimeStamp() {
        long time = Long.parseLong(timeStamp) * 1000;
        long now = System.currentTimeMillis();
        CharSequence relativeTimeSpanString = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.DAY_IN_MILLIS);
        return relativeTimeSpanString.toString();
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
