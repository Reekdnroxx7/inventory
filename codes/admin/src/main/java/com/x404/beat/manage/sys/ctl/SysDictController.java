package com.x404.beat.manage.sys.ctl;

import com.x404.beat.core.controller.BaseController;
import com.x404.beat.core.page.ExPageList;
import com.x404.beat.manage.sys.entity.Dict;
import com.x404.beat.manage.sys.entity.Icon;
import com.x404.beat.manage.sys.entity.Org;
import com.x404.beat.manage.sys.utils.DictManager;
import com.x404.beat.manage.sys.utils.OrgUtils;
import com.x404.beat.manage.sys.utils.IconUtils;
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
    public ExPageList<Icon> iconList() {
        ExPageList<Icon> pageList = new ExPageList<Icon>();
        pageList.setResultList(IconUtils.getInstance().getIcons());
        return pageList;
    }

    @RequestMapping(params = "method=dictList")
    @ResponseBody
    public ExPageList<Dict> dictList(HttpServletRequest request) {
        String groupCode = request.getParameter("groupCode");
        List<Dict> dict = DictManager.getInstance().getDict(groupCode);
        ExPageList<Dict> pageList = new ExPageList<Dict>();
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
