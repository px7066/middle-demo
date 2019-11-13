package com.github.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>计算hash值</p>
 *
 * @author <a href="7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class HashUtil {
    /**
     * @param key key
     * @param prime 质数
     * @return int
     * @see com.github.common.util.HashUtil#addHash(String, int)
     */         
    public static int addHash(String key, int prime){
        int hash, i;
        for (hash = key.length(), i = 0; i < key.length(); i++){
            hash += key.charAt(i);
        }
        return (hash % prime);
    }

    /**
     * 固定左移16位。末位不填充0
     * @param key key
     * @param prime 质数
     * @return int
     * @see com.github.common.util.HashUtil#rotatingHash(String, int)#rotatingHash(String, int)
     */         
    public static int rotatingHash(String key, int prime) {
        int hash, i;
        for (hash=key.length(), i=0; i<key.length(); ++i){
            hash = (hash<<16)^(hash>>16)^key.charAt(i);
        }
        return (hash % prime);
    }


    /**
     * FNV1a 哈希
     * @param data
     * @return int
     */
    public static int FNV1aHash(byte[] data) {
        final int p = 16777619;
        int hash = (int)2166136261L;
        for(byte b:data){
            hash = (hash ^ b) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return hash;
    }

}
