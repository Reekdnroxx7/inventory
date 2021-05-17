package com.x404.beat.ctl;

import com.x404.beat.core.controller.BaseController;
import com.x404.beat.core.json.AjaxJson;
import com.x404.beat.core.util.UUIDUtils;
import com.x404.beat.models.AccountInfo;
import com.x404.beat.service.AccountInfoService;
import com.x404.beat.service.RabbitSync;
import com.xc350.web.base.mybatis.dao.MybatisExample;
import com.xc350.web.base.mybatis.query.MybatisCriteriaHelper;
import com.xc350.web.base.query.PageList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/accountInfo")
public class AccountInfoCtl extends BaseController {


    private final static Log LOG = LogFactory.getLog(AccountInfoCtl.class);
    @Autowired
    private  AccountInfoService accountInfoService;

    @Autowired
    private RabbitSync rabbitSync;

    public AccountInfoCtl() {
    }


    @RequestMapping(params = "method=listAccountInfo")
    @ResponseBody
    /**
     * 用mybatis实现页面的分页显示
     */
    public PageList<AccountInfo> listAccountInfo(HttpServletRequest request) {
        MybatisExample mybatisExample = MybatisCriteriaHelper.generateFromRequest(request);

        PageList<AccountInfo> list = accountInfoService.pageList(mybatisExample);
        return list;
    }


    @RequestMapping(params = "method=saveAccountInfo")
    @ResponseBody
    /*
	 * 用mybatis来增加表数据
	 */
    public AjaxJson saveAccountInfo(HttpServletRequest request, AccountInfo accountInfo) {

        String saveType = ServletRequestUtils.getStringParameter(request,
                "_saveType", "add");
        try {

            if ("add".equals(saveType)) {
                accountInfo.setId(UUIDUtils.next());
                accountInfoService.insert(accountInfo);
            } else {
                accountInfoService.updateByKeySelective(accountInfo);
            }
            rabbitSync.sendSyncMessage();
            return new AjaxJson(true, accountInfo);

        } catch (Exception e) {
            LOG.error(e);
            return new AjaxJson(false, "操作失败");
        }


    }


    /**
     * 删除这个是用mybatis的方式
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "method=deleteAccountInfo")
    @ResponseBody
    public AjaxJson deleteAccountInfo(@RequestParam("id") String id, HttpServletRequest request) {

        try {
            accountInfoService.deleteByKey(id);
            rabbitSync.sendSyncMessage();
            return new AjaxJson(true, "操作成功");
        } catch (Exception e) {
            LOG.error(e);
            return new AjaxJson(false, "操作失败");
        }
    }


    @RequestMapping(params = "method=getAvailableBalance")
    @ResponseBody
    public AjaxJson getAvailableBalance(@RequestParam("id") String id, HttpServletRequest request) {
        AccountInfo accountInfo = accountInfoService.selectByKey(id);
        this.accountInfoService.updateByKeySelective(accountInfo);
        return new AjaxJson(true, accountInfo);
    }

}