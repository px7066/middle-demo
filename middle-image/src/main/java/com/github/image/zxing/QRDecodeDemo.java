package com.github.image.zxing;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>二维码解码</p>
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class QRDecodeDemo {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\7066450\\Desktop\\";
        String fileName = "zxing.png";
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filePath+fileName));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hintTypeObjectMap = new HashMap<>();
            hintTypeObjectMap.put(DecodeHintType.CHARACTER_SET,"UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hintTypeObjectMap);
            JSONObject jsonObject = JSONObject.parseObject(result.getText());
            System.out.println("二维码的内容:" + jsonObject);
            System.out.println("encode:"+ result.getBarcodeFormat());
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
        }
    }
}
