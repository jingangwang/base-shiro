package com.wjg.base.shiro.vo;

public class DataTableParams {
    private String sEcho = "";
    private Integer iColumns;
    private String sColumns;
    private Integer iDisplayStart = 0;
    private Integer iDisplayLength = 10;

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public Integer getiColumns() {
        return iColumns;
    }

    public void setiColumns(Integer iColumns) {
        this.iColumns = iColumns;
    }

    public String getsColumns() {
        return sColumns;
    }

    public void setsColumns(String sColumns) {
        this.sColumns = sColumns;
    }

    public Integer getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(Integer iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public Integer getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(Integer iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    @Override
    public String toString() {
        return "DataTableParams{" +
                "sEcho='" + sEcho + '\'' +
                ", iColumns=" + iColumns +
                ", sColumns='" + sColumns + '\'' +
                ", iDisplayStart=" + iDisplayStart +
                ", iDisplayLength=" + iDisplayLength +
                '}';
    }
}
