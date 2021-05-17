package com.x404.beat.ctl;

import com.x404.beat.core.controller.BaseController;
import com.x404.beat.core.json.AjaxJson;
import com.x404.beat.dao.IsnTeamMapDao;
import com.x404.beat.models.IsnTeamMap;
import com.x404.beat.service.RabbitSync;
import com.xc350.web.base.mongo.query.MongoQueryHelper;
import com.xc350.web.base.query.PageList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/isnTeamMap")
public class IsnTeamMapCtl extends BaseController {
    private final static Log LOG = LogFactory.getLog(IsnTeamMapCtl.class);
    @Autowired
    private IsnTeamMapDao isnTeamMapDao;

    @Autowired
    private RabbitSync rabbitSync;


    @RequestMapping(params = "method=listTeamMap")
    @ResponseBody
    /**
     * 用mybatis实现页面的分页显示
     */
    public PageList<IsnTeamMap> listTeamMap(HttpServletRequest request) {
        Query query = MongoQueryHelper.generateFromRequest(request);

        PageList<IsnTeamMap> list = new PageList<>(isnTeamMapDao.countByQuery(query), isnTeamMapDao.selectByQuery(query));
        return list;
    }

    @RequestMapping(params = "method=saveTeamMap")
    @ResponseBody
    /*
	 * 用mybatis来增加表数据
	 */
    public AjaxJson saveTeamMap(HttpServletRequest request, IsnTeamMap teamMap) {

        String saveType = ServletRequestUtils.getStringParameter(request,
                "_saveType", "add");
        try {
            if ("add".equals(saveType)) {
                isnTeamMapDao.insertSelective(teamMap);
            } else {
                isnTeamMapDao.updateByKey(teamMap);
            }
            rabbitSync.sendSyncMessage();
            return new AjaxJson(true, teamMap);

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
    @RequestMapping(params = "method=deleteTeamMap")
    @ResponseBody
    public AjaxJson deleteTeamMap(@RequestParam("id") String id, HttpServletRequest request) {

        try {
            isnTeamMapDao.deleteByKey(id);
            rabbitSync.sendSyncMessage();
            return new AjaxJson(true, "操作成功");
        } catch (Exception e) {
            LOG.error(e);
            return new AjaxJson(false, "操作失败");
        }
    }


}