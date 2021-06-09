package com.x404.admin.manage.sys.ctl;

import com.x404.admin.core.controller.BaseController;
import com.x404.module.basedao.hibernate.HibernateQuery;
import com.x404.admin.core.json.AjaxJson;
import com.x404.module.basedao.query.PageList;
import com.x404.module.basedao.query.HibernateQueryHelper;
import com.x404.admin.core.util.UploadFile;
import com.x404.admin.manage.sys.entity.Icon;
import com.x404.admin.manage.sys.utils.IconUtils;
import com.x404.admin.manage.sys.service.IIconService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


/**
 * 图标信息处理类
 *
 * @author 张代浩
 */
@Controller
@RequestMapping("/icon")
public class IconController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(IconController.class);
    @Autowired
    private IIconService iconService;

    public IIconService getIconService() {
        return iconService;
    }

    public void setIconService(IIconService iconService) {
        this.iconService = iconService;
    }


    @RequestMapping(params = "method=list")
    @ResponseBody
    public PageList<Icon> list(HttpServletRequest request, PageList<Icon> page) {
        HibernateQuery cq = HibernateQueryHelper.generateFromRequest(request);
        return iconService.getPageList(cq, page);
    }


    @RequestMapping(params = "method=save")
    @ResponseBody
    public AjaxJson save(HttpServletRequest request, Icon icon) {
        String destPath = request.getSession().getServletContext().getRealPath("/plug-in/system/images");
        UploadFile uploadFile = new UploadFile(request);
        File save2web = uploadFile.save2web(destPath, false);
        String saveType = ServletRequestUtils.getStringParameter(request, "_saveType", "add");
        icon.setPath("/plug-in/system/images/" + save2web.getName());
        if ("add".equals(saveType)) {
            iconService.save(icon);
        } else {
            iconService.updateByIdSelective(icon);
        }
        IconUtils.getInstance().refresh();
        write(request);
        return new AjaxJson(true, "上传成功");
    }

    /**
     * 添加图标样式
     *
     * @param request
     * @param css
     */
    protected void write(HttpServletRequest request) {
        FileWriter out = null;
        try {
            String path = request.getSession().getServletContext().getRealPath("/plug-in/system/css/icons.css");
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new FileWriter(file);
            List<Icon> icons = IconUtils.getInstance().getIcons();
            StringBuilder buf = new StringBuilder(icons.size() * 80);
            for (Icon icon : icons) {
                buf.append('.').append(icon.getCss()).append("{background-image:url('../images/");
                String fileName = icon.getPath().substring(icon.getPath().lastIndexOf("/") + 1);
                buf.append(fileName);
                buf.append("')  !important;background-repeat:no-repeat;}\r\n");
            }
            out.write(buf.toString());
            out.close();
        } catch (Exception e) {
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.error("写文件异常", e);
                }
            }
        }

    }


    //	/**
//	 * 清空文件内容
//	 *
//	 * @param request
//	 * @param css
//	 */
    @RequestMapping(params = "method=refresh")
    @ResponseBody
    public AjaxJson refresh(HttpServletRequest request) {
        write(request);
        IconUtils.getInstance().refresh();
        return new AjaxJson(true, "操作成功");
    }

    /**
     * 删除图标
     *
     * @param icon
     * @param request
     * @return
     */
    @RequestMapping(params = "method=delete")
    @ResponseBody
    public AjaxJson del(Icon icon, HttpServletRequest request) {
        this.iconService.deleteById(icon.getCss());
        IconUtils.getInstance().refresh();
        return new AjaxJson(true, "操作成功");
    }


}
