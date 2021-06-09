package com.x404.admin.manage.sys.ctl;

import com.x404.admin.core.controller.BaseController;
import com.x404.module.basedao.hibernate.HibernateQuery;
import com.x404.admin.core.json.AjaxJson;
import com.x404.module.basedao.query.PageList;
import com.x404.module.basedao.query.HibernateQueryHelper;
import com.x404.admin.manage.sys.entity.Dict;
import com.x404.admin.manage.sys.entity.DictGroup;
import com.x404.admin.manage.sys.entity.Operation;
import com.x404.admin.manage.sys.service.IDictGroupService;
import com.x404.admin.manage.sys.service.IDictService;
import com.x404.admin.manage.sys.utils.DictManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 字典处理类
 *
 * @author 谢超
 */
@Controller
@RequestMapping("/dictGroup")
public class DictController extends BaseController {

    @Autowired
    private IDictGroupService dictGroupService;


    public IDictGroupService getDictGroupService() {
        return dictGroupService;
    }

    public void setDictGroupService(IDictGroupService dictGroupService) {
        this.dictGroupService = dictGroupService;
    }


    @Autowired
    private IDictService dictService;


    public IDictService getDictService() {
        return dictService;
    }

    public void setDictService(IDictService dictService) {
        this.dictService = dictService;
    }

//	@RequestMapping(params="method=init")
//	public String init(HttpServletRequest request) {
//		String menuHref = ResourceUtil.getRequestPath(request);
//		Menu menu = MenuUtils.getInstance().getMenuByUrl(menuHref);
//		return menu.getTarget();
//	}

    @RequestMapping(params = "method=listDictGroup")
    @ResponseBody
    public PageList<DictGroup> listDictGroup(HttpServletRequest request, PageList<DictGroup> page) {
        HibernateQuery cq = HibernateQueryHelper.generateFromRequest(request, Operation.class);
        return dictGroupService.getPageList(cq, page);
    }

    @RequestMapping(params = "method=saveDictGroup")
    @ResponseBody
    public AjaxJson saveDictGroup(HttpServletRequest request, DictGroup dictGroup) {

        String saveType = ServletRequestUtils.getStringParameter(request,
                "_saveType", "add");
        if ("add".equals(saveType)) {
            this.dictGroupService.save(dictGroup);
        } else {
            this.dictGroupService.updateByIdSelective(dictGroup);
        }
        DictManager.getInstance().refresh();
        AjaxJson ajaxJson = new AjaxJson(true, "操作成功");
        ajaxJson.setData(dictGroup);
        return ajaxJson;
    }

    /**
     * 删除
     *
     * @param icon
     * @param request
     * @return
     */
    @RequestMapping(params = "method=deleteDictGroup")
    @ResponseBody
    public AjaxJson delDictGroup(DictGroup dictGroup, HttpServletRequest request) {
        this.dictGroupService.deleteById(dictGroup.getId());
        DictManager.getInstance().refresh();
        return new AjaxJson(true, "操作成功");
    }

    @RequestMapping(params = "method=listDict")
    @ResponseBody
    public PageList<Dict> listDict(HttpServletRequest request, PageList<Dict> page) {
        HibernateQuery cq = HibernateQueryHelper.generateFromRequest(request, Dict.class);
        return dictService.getPageList(cq, page);
    }

    @RequestMapping(params = "method=saveDict")
    @ResponseBody
    public AjaxJson saveDict(HttpServletRequest request, Dict dict) {

        String saveType = ServletRequestUtils.getStringParameter(request,
                "_saveType", "add");
        if ("add".equals(saveType)) {
            this.dictService.save(dict);
        } else {
            this.dictService.updateByIdSelective(dict);
        }
        DictManager.getInstance().refresh();
        AjaxJson ajaxJson = new AjaxJson(true, "操作成功");
        ajaxJson.setData(dict);
        return ajaxJson;
    }

    /**
     * 删除
     *
     * @param icon
     * @param request
     * @return
     */
    @RequestMapping(params = "method=deleteDict")
    @ResponseBody
    public AjaxJson delDict(Dict dict, HttpServletRequest request) {
        this.dictService.deleteById(dict.getId());
        DictManager.getInstance().refresh();
        return new AjaxJson(true, "操作成功");
    }

    @RequestMapping(params = "method=refresh")
    @ResponseBody
    public AjaxJson refresh(HttpServletRequest request) {
        DictManager.getInstance().refresh();
        return new AjaxJson(true, "操作成功");
    }
}
