package com.x404.beat.ctl;

import com.x404.beat.core.controller.BaseController;
import com.x404.beat.core.json.AjaxJson;
import com.x404.beat.dao.SbobetLeagueDao;
import com.x404.beat.models.League;
import com.x404.beat.service.RabbitSync;
import com.x404.beat.task.GetLeagueTask;
import com.xc350.web.base.query.PageList;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
@Controller
@RequestMapping("/league")
public class LeagueCtl extends BaseController {
	private final static Log LOG =  LogFactory.getLog(LeagueCtl.class);
	@Autowired
	private SbobetLeagueDao sbobetLeagueDao;
	@Autowired
	private RabbitSync rabbitSync;

	@Autowired
	private GetLeagueTask getLeagueTask;


	@RequestMapping(params="method=listLeague")
	@ResponseBody
	/**
	 * 用mybatis实现页面的分页显示
	 */
	public PageList<League> listLeague(HttpServletRequest request) {
//		Query query = MongoQueryHelper.generateFromRequest(request);
		String pattern = request.getParameter("pattern");
		Query query = new Query();
		if(StringUtils.isNotBlank(pattern)){
			query.addCriteria(new Criteria().orOperator(Criteria.where("_id").is(pattern),
					Criteria.where("league_cn_name").regex(pattern),
					Criteria.where("league_name").regex(pattern)));
		}
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int limit = ServletRequestUtils.getIntParameter(request, "limit", 20);
		query.skip(start);
		query.limit(limit);

		PageList<League> list=new PageList<>(this.sbobetLeagueDao.countByQuery(query), sbobetLeagueDao.selectByQuery(query));
		return  list;
	}



	@RequestMapping(params="method=saveLeague")
	@ResponseBody
	/*
	 * 用mybatis来增加表数据
	 */
	public AjaxJson saveLeague(HttpServletRequest request, League league) {

		String saveType = ServletRequestUtils.getStringParameter(request,
				"_saveType", "add");
		try {
			sbobetLeagueDao.updateByKeySelective(league);
			rabbitSync.sendSyncMessage();
			return new AjaxJson(true, league);

		} catch (Exception e) {
			LOG.error(e);
			return new AjaxJson(false,"操作失败");
		}
	}

	@RequestMapping(params="method=setAllStatus")
	@ResponseBody
	/*
	 * 用mybatis来增加表数据
	 */
	public AjaxJson setAllStatus(HttpServletRequest request) {

		boolean flag = ServletRequestUtils.getBooleanParameter(request,
				"flag", true);
		try {
			sbobetLeagueDao.setAllStatus(flag);
			rabbitSync.sendSyncMessage();
			return new AjaxJson(true, "操作成功");

		} catch (Exception e) {
			LOG.error(e);
			return new AjaxJson(false,"操作失败");
		}
	}

	@RequestMapping(params="method=refreshLeague")
	@ResponseBody
	public AjaxJson deleteLeague( HttpServletRequest request) {

		try {
			getLeagueTask.execute();
			return new AjaxJson(true, "操作成功");
		} catch (Exception e) {
			LOG.error(e);
			return new AjaxJson(false, "操作失败");
		}
	}



//	/**
//	 * 删除这个是用mybatis的方式
//	 *
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(params="method=deleteLeague")
//	@ResponseBody
//	public AjaxJson deleteLeague(@RequestParam("id")String id,HttpServletRequest request) {
//
//		try {
//			sbobetLeagueDao.deleteById(id);
//			return new AjaxJson(true, "操作成功");
//		} catch (Exception e) {
//			LOG.error(e);
//			return new AjaxJson(false, "操作失败");
//		}
//	}


}