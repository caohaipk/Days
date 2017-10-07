package com.wordpress.grayfaces.days.models;

/**
 * Project Days
 * Created by Kin on 2/22/2017.
 */

public class Anniversary {
    private int ID;
    private String Person1, Person2, Title, StartDate, TopText, BottomText;
    public Anniversary(){}
    public Anniversary(int id,String person1,String person2,String title, String startDate, String topText, String bottomText){
        ID = id;
        Person1 = person1;
        Person2 = person2;
        Title = title;
        StartDate = startDate;
        TopText = topText;
        BottomText = bottomText;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPerson1() {
        return Person1;
    }

    public void setPerson1(String person1) {
        Person1 = person1;
    }

    public String getPerson2() {
        return Person2;
    }

    public void setPerson2(String person2) {
        Person2 = person2;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getTopText() {
        return TopText;
    }

    public void setTopText(String topText) {
        TopText = topText;
    }

    public String getBottomText() {
        return BottomText;
    }

    public void setBottomText(String bottomText) {
        BottomText = bottomText;
    }
}
