package com.x404.admin.manage.sys.ctl;

import com.x404.admin.core.controller.BaseController;
import com.x404.module.basedao.query.PageList;
import com.x404.admin.manage.sys.entity.Dict;
import com.x404.admin.manage.sys.entity.Icon;
import com.x404.admin.manage.sys.entity.Org;
import com.x404.admin.manage.sys.utils.DictManager;
import com.x404.admin.manage.sys.utils.IconUtils;
import com.x404.admin.manage.sys.utils.OrgUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiechao
 *         字典处理类
 *         系统级别的字典处理
 *         如：orgTree
 *         如：dictComBox
 *         如：iconBox
 *         由于字典处理需要跳过权限检测的，所以暴露的内容越少越好。
 *         禁止创建 从前台传递table的查询
 */
@Controller
@RequestMapping("/sysDict")
public class SysDictController extends BaseController {
//	private final static Logger LOGGER = LoggerFactory.getLogger(SysDictController.class);


    @RequestMapping(params = "method=iconList")
    @ResponseBody
    public PageList<Icon> iconList() {
        PageList<Icon> pageList = new PageList<Icon>();
        pageList.setResultList(IconUtils.getInstance().getIcons());
        return pageList;
    }

    @RequestMapping(params = "method=dictList")
    @ResponseBody
    public PageList<Dict> dictList(HttpServletRequest request) {
        String groupCode = request.getParameter("groupCode");
        List<Dict> dict = DictManager.getInstance().getDict(groupCode);
        PageList<Dict> pageList = new PageList<Dict>();
        pageList.setResultList(dict);
        return pageList;
    }

    @RequestMapping(params = "method=orgTree")
    @ResponseBody
    public List<Org> orgTree(HttpServletRequest request) {
        String orgId = OrgUtils.getInstance().getRequestOrg().getId();
        ArrayList<Org> arrayList = new ArrayList<Org>(1);
        arrayList.add(OrgUtils.getInstance().getExOrg(orgId));
        return arrayList;
    }


    public void refresh() {
        OrgUtils.getInstance().refresh();
        DictManager.getInstance().refresh();
        IconUtils.getInstance();
    }


}
