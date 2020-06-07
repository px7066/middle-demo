package com.github.support.demo;

import sun.misc.Launcher;

import java.net.URL;

public class                            ClassLoadDemo {
    public static void main(String[] args) {
        URL[] urls = Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urls) {
            System.out.println(url.toExternalForm());
        }
    }
}
