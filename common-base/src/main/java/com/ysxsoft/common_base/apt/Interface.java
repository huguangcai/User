package com.ysxsoft.common_base.apt;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * create by Sincerly on 2019/3/29 0029
 **/
public class Interface {

    public void test(){
       Annotation[] annotation=this.getClass().getAnnotations();
    }

    public static void main(String[] args){
        Method[] a=Interface.class.getMethods();
        Annotation[] a2=a.getClass().getAnnotations();
        for (int i = 0; i <a2.length; i++) {
            System.out.println(a2[i]);
        }
    }

    @Test
    public void success(){
    }
}
