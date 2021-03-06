package com.x404.admin.manage.sys.ctl;

import com.x404.admin.core.controller.BaseController;
import com.x404.module.basedao.hibernate.HibernateQuery;
import com.x404.admin.core.json.AjaxJson;
import com.x404.module.basedao.query.PageList;
import com.x404.module.basedao.query.HibernateQueryHelper;
import com.x404.admin.manage.sys.entity.Config;
import com.x404.admin.manage.sys.tools.ConfigManager;
import com.x404.admin.manage.sys.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/config")
public class ConfigController extends BaseController {

    @Autowired
    private IConfigService configService;


    public IConfigService getConfigService() {
        return configService;
    }

    public void setConfigService(IConfigService configService) {
        this.configService = configService;
    }

//	@RequestMapping(params="method=init")
//	public String init(HttpServletRequest request) {
//		String menuHref = ResourceUtil.getRequestPath(request);
//		Menu menu = MenuUtils.getInstance().getMenuByUrl(menuHref);
//		return menu.getTarget();
//	}

    @RequestMapping(params = "method=listConfig")
    @ResponseBody
    public PageList<Config> listConfig(HttpServletRequest request, PageList<Config> page) {
        HibernateQuery cq = HibernateQueryHelper.generateFromRequest(request);
        return configService.getPageList(cq, page);
    }

    @RequestMapping(params = "method=saveConfig")
    @ResponseBody
    public AjaxJson saveConfig(HttpServletRequest request, Config config) {
        String saveType = ServletRequestUtils.getStringParameter(request,
                "_saveType", "add");

        if (ConfigManager.getInstance().containsKey(config.getKey())) {
//            this.configService.updateByIdSelective(config);
            this.configService.updateByKey(config);
        } else {
            this.configService.save(config);
        }
        ConfigManager.getInstance().refresh();
        AjaxJson ajaxJson = new AjaxJson(true, "????????????");
        ajaxJson.setData(config);
        return ajaxJson;
    }

    /**
     * ??????
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "method=deleteConfig")
    @ResponseBody
    public AjaxJson delConfig(Config config, HttpServletRequest request) {
        this.configService.deleteById(config.getId());
        ConfigManager.getInstance().refresh();
        return new AjaxJson(true, "????????????");
    }

    /**
     * ????????????
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "method=refresh")
    @ResponseBody
    public AjaxJson refresh(HttpServletRequest request) {
        ConfigManager.getInstance().refresh();
        return new AjaxJson(true, "????????????");
    }


    @RequestMapping(params = "method=allConfig")
    @ResponseBody
    public AjaxJson listConfig() {

        return new AjaxJson(true,ConfigManager.getInstance().getAllConfig());
    }

}

