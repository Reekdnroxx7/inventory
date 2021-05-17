package com.x404.beat.ctl;

import com.x404.beat.core.controller.BaseController;
import com.x404.beat.core.json.AjaxJson;
import com.x404.beat.dao.PS3838TeamMapDao;
import com.x404.beat.models.PS3838TeamMap;
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
@RequestMapping("/ps3838TeamMap")
public class PS3838TeamMapCtl extends BaseController {
    private final static Log LOG = LogFactory.getLog(PS3838TeamMapCtl.class);
    @Autowired
    private PS3838TeamMapDao ps3838TeamMapDao;
    @Autowired
    private RabbitSync rabbitSync;

    @RequestMapping(params = "method=listTeamMap")
    @ResponseBody
    /**
     * 用mybatis实现页面的分页显示
     */
    public PageList<PS3838TeamMap> listTeamMap(HttpServletRequest request) {
        Query query = MongoQueryHelper.generateFromRequest(request);

        PageList<PS3838TeamMap> list = new PageList<>(ps3838TeamMapDao.countByQuery(query), ps3838TeamMapDao.selectByQuery(query));
        return list;
    }

    @RequestMapping(params = "method=saveTeamMap")
    @ResponseBody
    /*
	 * 用mybatis来增加表数据
	 */
    public AjaxJson saveTeamMap(HttpServletRequest request, PS3838TeamMap teamMap) {

        String saveType = ServletRequestUtils.getStringParameter(request,
                "_saveType", "add");
        try {
            if ("add".equals(saveType)) {
                ps3838TeamMapDao.insertSelective(teamMap);
            } else {
                ps3838TeamMapDao.updateByKey(teamMap);
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
            ps3838TeamMapDao.deleteByKey(id);
            rabbitSync.sendSyncMessage();
            return new AjaxJson(true, "操作成功");
        } catch (Exception e) {
            LOG.error(e);
            return new AjaxJson(false, "操作失败");
        }
    }


}