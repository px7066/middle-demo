package com.github.concurrent.model.user;

import java.io.Serializable;

public class DubboUser implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dubbo_user.id
     *
     * @mbg.generated Thu Oct 31 15:00:43 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dubbo_user.name
     *
     * @mbg.generated Thu Oct 31 15:00:43 CST 2019
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dubbo_user.age
     *
     * @mbg.generated Thu Oct 31 15:00:43 CST 2019
     */
    private Integer age;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table dubbo_user
     *
     * @mbg.generated Thu Oct 31 15:00:43 CST 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dubbo_user.id
     *
     * @return the value of dubbo_user.id
     *
     * @mbg.generated Thu Oct 31 15:00:43 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dubbo_user.id
     *
     * @param id the value for dubbo_user.id
     *
     * @mbg.generated Thu Oct 31 15:00:43 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dubbo_user.name
     *
     * @return the value of dubbo_user.name
     *
     * @mbg.generated Thu Oct 31 15:00:43 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dubbo_user.name
     *
     * @param name the value for dubbo_user.name
     *
     * @mbg.generated Thu Oct 31 15:00:43 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dubbo_user.age
     *
     * @return the value of dubbo_user.age
     *
     * @mbg.generated Thu Oct 31 15:00:43 CST 2019
     */
    public Integer getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dubbo_user.age
     *
     * @param age the value for dubbo_user.age
     *
     * @mbg.generated Thu Oct 31 15:00:43 CST 2019
     */
    public void setAge(Integer age) {
        this.age = age;
    }
}