package com.pinyougou.solrutil;

public class Test01 {
    private int xuehao;
    private String xingming;
    private int shuxue;
    private int english;
    private int it;

    public Test01(int xuehao, String xingming, int shuxue, int english, int it) {
        this.xuehao = xuehao;
        this.xingming = xingming;
        this.shuxue = shuxue;
        this.english = english;
        this.it = it;
    }
    public int getXuehao() {
        return xuehao;
    }
    public void setXuehao(int xuehao) {
        this.xuehao = xuehao;
    }
    public String getXingming() {
        return xingming;
    }
    public void setXingming(String xingming) {
        this.xingming = xingming;
    }
    public int getShuxue() {
        return shuxue;
    }
    public void setShuxue(int shuxue) {
        this.shuxue = shuxue;
    }
    public int getEnglish() {
        return english;
    }
    public void setEnglish(int english) {
        this.english = english;
    }
    public int getIt() {
        return it;
    }
    public void setIt(int it) {
        this.it = it;
    }
    public double avg(){
        return (double)(shuxue+english+it)/3;
    }
    public static void main(String[] args) {
        Test01 aaa=new Test01(201603101,"小明",50,100,50);
        double avg=aaa.avg();
        System.out.println(avg);
    }
}
