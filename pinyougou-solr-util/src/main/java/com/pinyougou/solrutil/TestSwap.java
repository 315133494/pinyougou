package com.pinyougou.solrutil;

import java.lang.reflect.Field;

public class TestSwap {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Integer a=1;
        Integer b=2;
        System.out.println("**********转换前**********");
        System.out.println("a="+a);
        System.out.println("b="+b);

        swapChangeAwithB(a,b);
        System.out.println("**********转换后**********");
        System.out.println("a="+a);
        System.out.println("b="+b);
    }

    //声明
    // 以下是java.lang.Class.getDeclaredField()方法的声明
    // public Field getDeclaredField(String name) throws NoSuchFieldException, SecurityException
    // 参数
    // name -- 这是字段的名字。
    // 返回值
    // 这个方法返回这个类中的指定域的域对象。

    public static  void swapChangeAwithB(Integer a ,Integer b ) throws NoSuchFieldException, IllegalAccessException {
        //通过反射获取Integer对象中的私有域
        //value这个值就对应的是Integer对象中的value属性
        Field field = Integer.class.getDeclaredField("value");
        field.setAccessible(true);

        int temp = a.intValue();

        //调用set(obj,value)方法
        //obj表示要修改的对象，value表示要给修改对象赋予的值
        field.set(a,b);
        field.set(b,new Integer(temp));
    }
}
