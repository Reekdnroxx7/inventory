package com.x404.beat.ctl;

import com.x404.beat.core.json.AjaxJson;
import com.x404.beat.dao.TeamDao;
import com.x404.beat.models.Team;
import com.x404.beat.task.TranslateTask;
import com.xc350.web.base.mongo.query.MongoQueryHelper;
import com.xc350.web.base.query.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chaox on 7/3/2017.
 */
@Controller
@RequestMapping("/team")
public class TeamCtl {

    @Autowired
    private TeamDao teamDao;

//    @Autowired
//    private DefaultPS3838HttpClient defaultPS3838HttpClient;

    @Autowired
    private TranslateTask translateTask;

    @RequestMapping(params = "method=listTeam")
    @ResponseBody
    /**
     * 用mybatis实现页面的分页显示
     */
    public PageList<Team> listAccountInfo(HttpServletRequest request) {
        Query query = MongoQueryHelper.generateFromRequest(request);
        if("true".equals(request.getParameter("translate"))){
            query.addCriteria(Criteria.where("translations.zh-CN").exists(false));
        }
        query.with(new Sort(new Sort.Order(Sort.Direction.ASC,"_id")));

        PageList<Team> list = new PageList<>(teamDao.countByQuery(query),teamDao.selectByQuery(query));
        return list;
    }
    @RequestMapping(params = "method=translate")
    @ResponseBody
    public AjaxJson translate(HttpServletRequest request){
        translateTask.translate();
        return AjaxJson.SUCCESS;
    }
//
    @RequestMapping(params = "method=addTranslate")
    @ResponseBody
    public AjaxJson addLocale(String id,String locale,String value){
        teamDao.addTranslate(id,locale,value);
        return AjaxJson.SUCCESS;
    }
}
