package com.secondstudio.letsrun.model;

public class Task {
    private int mT_No;
    private String mTitle;
    private String mDetail;
    private int mDone;
    
    private int mDoneSize;      //完成的次数

    public void setT_No(int t_No) {
        mT_No = t_No;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDetail(String detail) {
        mDetail = detail;
    }

    public void setDoneSize(int doneSize) {
        mDoneSize = doneSize;
    }

    public Task(int t_no, String title, String detail) {
        mT_No = t_no;
        mTitle = title;
        mDetail = detail;
    }

    public Task(int t_no, String name, String detail, int done, int doneSize) {
        mT_No = t_no;
        mTitle = name;
        mDetail = detail;
        mDone = done;
        mDoneSize = doneSize;
    }

    public void setDone(int done){
        mDone = done;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDetail(){
        return mDetail;
    }

    public int getDone() {
        return mDone;
    }

    public int getT_No() {
        return mT_No;
    }
    
    public int getDoneSize(){
        return mDoneSize;
    }
}
