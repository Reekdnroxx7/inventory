package com.x404.beat.manage.sys.ctl;

import com.x404.beat.core.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 获取验证码
 *
 * @author wangjianjun
 */
@Controller
@RequestMapping("/checkcode")
public class CheckcodeController extends BaseController {
    @ResponseBody
    @RequestMapping(params = "method=getcheckcode")
    public void getCheckcode(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 绘制一张图片的过程
        // 1.准备一张空白的有尺寸的图片
        BufferedImage image = new BufferedImage(100, 30,
                BufferedImage.TYPE_INT_RGB);
        // 2.获取图片的画笔对象
        Graphics g = image.getGraphics();
        // 3.设置画笔颜色
        Random r = new Random();
        g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
        // 4.绘制矩形实心的背景
        g.fillRect(0, 0, 100, 30);
        // 5.更改画笔颜色
        g.setColor(new Color(255, 255, 255));
        // 5.1设置画笔字体
        g.setFont(new Font(null, Font.BOLD, 24));
        // 6.绘制字符串
        String number = getNumber(5);
        // 生成的验证码存到session中
        HttpSession session = request.getSession();
        session.setAttribute("checkcode", number);
        g.drawString(number, 10, 25);
        // 7.设置响应流的数据格式
        response.setContentType("image/jpeg");
        // 8.获取输出流
        OutputStream ops = response.getOutputStream();
        // 8.保存图片到输出流中
        ImageIO.write(image, "jpeg", ops);
        ops.close();
        // 练习:绘制长度为5的随机字母+数字的组合图片
    }

    public String getNumber(int size) {
        String cs = "ABCDEFGHIJKLMNOPQ" + "RSTUVWXYZ0123456789";
        String number = "";
        Random r = new Random();
        for (int i = 0; i < size; i++) {
            number += cs.charAt(r.nextInt(cs.length()));
        }
        return number;
    }


}
