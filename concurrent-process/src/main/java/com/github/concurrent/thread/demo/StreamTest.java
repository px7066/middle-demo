package com.github.concurrent.thread.demo;


import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StreamTest {

    private static List<Employee> employeeList = new ArrayList<>(5);

    public static void before() {
        employeeList.add(new Employee("张三", "D001", 5000d));
        employeeList.add(new Employee("李四", "D001", 6000d));
        employeeList.add(new Employee("王五", "D002", 5000d));
        employeeList.add(new Employee("赵六", "D003", 5500d));
        employeeList.add(new Employee("孙七", "D002", 4500d));
    }


    public static void test1() {
        // TODO 获取每个部门薪资总和，拼成Map<String, Double>（优先考虑stream流式实现）
        before();
        Map<String, Double> result = employeeList.stream().collect(Collectors.groupingBy(Employee::getDept, Collectors.summingDouble(Employee::getSalary)));
        System.out.println(result);

    }

    public static void test2() {
        // TODO 请筛选出每个部门工资最高的员工（优先考虑stream流式实现）
        before();
        Map<String, Optional<Employee>> result =  employeeList.stream().collect(Collectors.groupingBy(Employee::getDept, Collectors.maxBy((e1, e2) -> (int) (e1.getSalary() - e2.getSalary()))));
        System.out.println(result);

    }

    public static void main(String[] args) {
        test1();
        test2();
    }

}
