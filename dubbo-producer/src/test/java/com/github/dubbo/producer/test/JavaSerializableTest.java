package com.github.dubbo.producer.test;

import com.github.dubbo.producer.model.UserVo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <p>java 序列化测试</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class JavaSerializableTest {
    public static void main(String[] args) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user.txt"));
             ObjectInputStream ios = new ObjectInputStream(new FileInputStream("user.txt"))) {
            //第一次序列化person
            UserVo user = new UserVo();
            user.setId(1);
            user.setAge(10);
            user.setName("娃哈哈");
            oos.writeObject(user);
            System.out.println(user);

            //修改name
            user.setName("海贼王");
            System.out.println(user);
            //第二次序列化person
            oos.writeObject(user);

            //依次反序列化出p1、p2
            UserVo u1 = (UserVo) ios.readObject();
            System.out.println(u1);
            UserVo u2 = (UserVo) ios.readObject();
            System.out.println(u2);
            System.out.println(u1 == u2);
            System.out.println(u1.getName().equals(u2.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
