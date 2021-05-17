package com.x404.beat.manage.sys.ctl;

import com.x404.beat.core.controller.BaseController;
import com.x404.beat.core.json.AjaxJson;
import com.x404.beat.core.query.HibernateQueryHelper;
import com.x404.beat.manage.sys.entity.Operation;
import com.x404.beat.core.hibernate.query.HibernateQuery;
import com.x404.beat.core.page.ExPageList;
import com.x404.beat.manage.sys.entity.Menu;
import com.x404.beat.manage.sys.service.IMenuService;
import com.x404.beat.manage.sys.service.IOperationService;
import com.x404.beat.manage.sys.utils.MenuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * 图标信息处理类
 *
 * @author 张代浩
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {
    //	private final static Logger LOGGER = LoggerFactory
//			.getLogger(MenuController.class);
    @Autowired
    private IMenuService menuService;

    public IMenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    @Autowired
    private IOperationService operationService;


    public IOperationService getOperationService() {
        return operationService;
    }

    public void setOperationService(IOperationService operationService) {
        this.operationService = operationService;
    }


//	@RequestMapping(params="method=init")
//	public String init(HttpServletRequest request) {
//		String menuHref = ResourceUtil.getRequestPath(request);
//		Menu menu = MenuUtils.getInstance().getMenuByUrl(menuHref);
//		return menu.getTarget();
//	}

    @RequestMapping(params = "method=listMenu")
    @ResponseBody
    public List<? extends Menu> listMenu(HttpServletRequest request) {
        String parentId = ServletRequestUtils.getStringParameter(request, "menuId", "1");
        return MenuUtils.getInstance().getChildList(parentId);
    }

    @RequestMapping(params = "method=saveMenu")
    @ResponseBody
    public AjaxJson save(HttpServletRequest request, Menu menu) {

        String saveType = ServletRequestUtils.getStringParameter(request,
                "_saveType", "add");
        if ("add".equals(saveType)) {
            Menu parent = menuService.findById(menu.getParentId());
            menu.setParentIds(parent.getParentId() + "," + parent.getId());
            Serializable id = this.menuService.save(menu);
            menu.setId((String) id);
        } else {
            this.menuService.updateByIdSelective(menu);
        }
        MenuUtils.getInstance().refresh();  //刷新menu缓存;
        AjaxJson ajaxJson = new AjaxJson(true, "操作成功");
        ajaxJson.setData(menu);
        return ajaxJson;
    }


    /**
     * 删除
     *
     * @param icon
     * @param request
     * @return
     */
    @RequestMapping(params = "method=deleteMenu")
    @ResponseBody
    public AjaxJson delMenu(Menu menu, HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        List<Menu> childList = (List<Menu>) MenuUtils.getInstance().getChildList(menu.getId());
        if (childList != null && childList.size() > 0) {
            return new AjaxJson(false, "目录下有其他菜单，不能删除！");
        }
        this.menuService.deleteById(menu.getId());
        MenuUtils.getInstance().refresh();
        return new AjaxJson(true, "操作成功");
    }


    @RequestMapping(params = "method=listOperation")
    @ResponseBody
    public ExPageList<Operation> listOperation(HttpServletRequest request, ExPageList<Operation> page) {
        HibernateQuery cq = HibernateQueryHelper.generateFromRequest(request, Operation.class);
        return operationService.getPageList(cq, page);
    }


    @RequestMapping(params = "method=saveOperation")
    @ResponseBody
    public AjaxJson saveOperation(HttpServletRequest request, Operation operation) {
        String saveType = ServletRequestUtils.getStringParameter(request,
                "_saveType", "add");
        if ("add".equals(saveType)) {
            this.operationService.save(operation);
        } else {
            this.operationService.updateByIdSelective(operation);
        }
        AjaxJson ajaxJson = new AjaxJson(true, "操作成功");
        ajaxJson.setData(operation);
        return ajaxJson;
    }


    /**
     * 删除
     *
     * @param icon
     * @param request
     * @return
     */
    @RequestMapping(params = "method=deleteOperation")
    @ResponseBody
    public AjaxJson delOperation(Operation operation, HttpServletRequest request) {
        this.operationService.deleteById(operation.getId());
        return new AjaxJson(true, "操作成功");
    }

    /**
     * 刷新缓存
     *
     * @param icon
     * @param request
     * @return
     */
    @RequestMapping(params = "method=refresh")
    @ResponseBody
    public AjaxJson refresh(HttpServletRequest request) {
        MenuUtils.getInstance().refresh();
        return new AjaxJson(true, "操作成功");
    }
}
