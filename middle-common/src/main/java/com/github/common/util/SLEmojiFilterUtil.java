/**************************************************************************
 * Copyright (c) 2018-2022 ZheJiang Electronic Port, Inc.
 * All rights reserved.
 *
 * 项目名称：海蛛
 * 版权说明：本软件属浙江电子口岸有限公司所有，在未获得浙江电子口岸有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.github.common.util;

/**
  * <p>emoji工具过滤类 </p>
  * @author <a href="mailto:7066450@qq.com">panxi</a>
  * @version 1.0.0
  * @since 1.0
  */
public class SLEmojiFilterUtil {
	/** 
     * 检测是否有emoji字符 
     * 
     * @param source  原字符
     * @return 一旦含有就抛出 
     */  
    private static boolean containsEmoji(String source) {
        if (source == null || source.trim().length() == 0) {
            return false;  
        }  
        int len = source.length();  
        for (int i = 0; i < len; i++) {  
            char codePoint = source.charAt(i);  
            if (isEmojiCharacter(codePoint)) {  
                //do nothing，判断到了这里表明，确认有表情字符  
                return true;  
            }  
        }  
        return false;  
    }  
  
    private static boolean isEmojiCharacter(char codePoint) {  
        return codePoint == 0x0 || codePoint == 0x9 || codePoint == 0xA || codePoint == 0xD || codePoint >= 0x20 && codePoint <= 0xD7FF || codePoint >= 0xE000 && codePoint <= 0xFFFD;
    }  
  
    /** 
     * 过滤emoji 或者 其他非文字类型的字符 
     * 
     * @param source 原字符
     * @return String 过滤后的字符
     */  
    public static String filterEmoji(String source) {  
        source = source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");  
        if (!containsEmoji(source)) {  
            return source;//如果不包含，直接返回  
        }  
        //到这里铁定包含  
        StringBuilder buf = null;  
  
        int len = source.length();  
  
        for (int i = 0; i < len; i++) {  
            char codePoint = source.charAt(i);
            if (buf == null) {
                buf = new StringBuilder(source.length());
            }
            if (isEmojiCharacter(codePoint)) {  
                buf.append(codePoint);
            } else {
                buf.append("*");
            }  
        }  
  
        if (buf == null) {  
            return source;//如果没有找到 emoji表情，则返回源字符串  
        } else {  
            if (buf.length() == len) {//这里的意义在于尽可能少的toString，因为会重新生成字符串  
                buf = null;  
                return source;  
            } else {  
                return buf.toString();  
            }  
        }  
    }  
    
    public static void main(String[] args) {
    	System.out.println("✋");
    	System.out.println(SLEmojiFilterUtil.filterEmoji("老胡对天立誓，此代码绝无bug，123456zxcvbbnSDFSDF@#$%^&&*!!!***&&&朱富贵"));
		System.out.println(SLEmojiFilterUtil.filterEmoji("💪👈👉👆👇华盛顿👌👍👎👊"));
	}
}
