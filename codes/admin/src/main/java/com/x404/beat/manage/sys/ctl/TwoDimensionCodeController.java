package com.x404.beat.manage.sys.ctl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.x404.beat.core.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * 获取验证码
 *
 * @author wangjianjun
 */
@Controller
@RequestMapping("/twodimenCode")
public class TwoDimensionCodeController extends BaseController {

    // 下面的方法即为二维码生成方法
    @ResponseBody
    @RequestMapping(params = "method=getTwoDimCode")
    public void getCheckcode(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException,
            Exception {
        String text = "http://192.168.1.21:8080/jeecp";
        int width = 100;
        int height = 100;
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
                BarcodeFormat.QR_CODE, width, height, hints);
        response.setContentType("image/jpeg");
        OutputStream ops = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "jpeg", ops);

    }

    public final static class MatrixToImageWriter {

        private static final int BLACK = 0xFF000000;
        private static final int WHITE = 0xFFFFFFFF;

        private MatrixToImageWriter() {
        }

        public static BufferedImage toBufferedImage(BitMatrix matrix) {
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
                }
            }
            return image;
        }

        public static void writeToFile(BitMatrix matrix, String format,
                                       File file) throws IOException {
            BufferedImage image = toBufferedImage(matrix);
            if (!ImageIO.write(image, format, file)) {
                throw new IOException("Could not write an image of format "
                        + format + " to " + file);
            }
        }

        public static void writeToStream(BitMatrix matrix, String format,
                                         OutputStream stream) throws IOException {
            BufferedImage image = toBufferedImage(matrix);
            ImageIO.write(image, format, stream);
        }

    }

}
