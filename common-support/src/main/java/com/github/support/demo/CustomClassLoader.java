package com.github.support.demo;

import java.io.FileNotFoundException;

public class CustomClassLoader extends ClassLoader{
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] result = getClassFromCustomPath(name);
            if(result == null){
                throw new FileNotFoundException();
            }else{
                return defineClass(name, result, 0, result.length);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    private byte[] getClassFromCustomPath(String name){
        return null;
    }


    public static void main(String[] args) {
        CustomClassLoader customClassLoader = new CustomClassLoader();
        try{
            Class<?> clazz = Class.forName("One", true, customClassLoader);
            Object obj = clazz.newInstance();
            System.out.println(obj.getClass().getClassLoader());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
