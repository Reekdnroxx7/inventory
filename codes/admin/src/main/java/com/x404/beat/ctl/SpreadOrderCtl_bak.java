//package com.x404.beat.ctl;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.x404.beat.busi.entity.AllSpread;
//import com.x404.beat.busi.sbobet.entity.League;
//import com.x404.beat.busi.sbobet.tools.LeagueManager;
//import com.x404.beat.core.controller.BaseController;
//import com.x404.beat.core.json.AjaxJson;
//import com.xc350.web.base.mongo.query.MongoQueryHelper;
//import com.xc350.web.base.query.PageList;
//import com.x404.beat.dao.AllSpreadOrderDao;
//import com.x404.beat.busi.entity.AllSpreadOrder;
//import com.x404.beat.busi.entity.Team;
//import com.x404.beat.busi.tools.TeamManager;
//import com.x404.beat.core.util.JsonUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("/spreadorder")
//public class SpreadOrderCtl extends BaseController {
//	private final static Log LOG =  LogFactory.getLog(SpreadOrderCtl.class);
//    @Autowired
//	private AllSpreadOrderDao allSpreadOrderDao;
//
//	@RequestMapping(params="method=listSpreadOrder")
//	@ResponseBody
//	public PageList<JsonNode> listSpreadOrder(HttpServletRequest request) {
//		Query query = MongoQueryHelper.generateFromRequest(request,AllSpreadOrder.class);
//		query.with(new Sort(Sort.Direction.DESC,"update_time"));
//
//		PageList<JsonNode> list= new PageList<>(allSpreadOrderDao.countByQuery(query),
//				allSpreadOrderDao.selectByQuery(query).stream().map(allSpreadOrder -> {
//					ObjectNode objectNode = JsonUtils.valueToTree(allSpreadOrder);
//					AllSpread allSpread = allSpreadOrder.getAllSpread();
//					if(allSpread.getLeague_id() != null){
//
//						League league = LeagueManager.getInstance().getLeague(allSpread.getLeague_id());
//						if(league != null){
//
//							objectNode.put("league_name", league.getLeague_name());
//							objectNode.put("league_cn_name",league.getLeague_cn_name());
//						}
//						Team team = TeamManager.getInstance().getTeam(allSpread.getHome());
//						if(team != null){
//							objectNode.put("home_cn_name",team.getTranslations().get("zh-CN"));
//						}
//						team = TeamManager.getInstance().getTeam(allSpread.getAway());
//						if(team != null){
//							objectNode.put("away_cn_name",team.getTranslations().get("zh-CN"));
//						}
//					}
//					return objectNode;
//
//				}).collect(Collectors.toList())
//
//		);
//
//		return  list;
//	}
//
//
//	@RequestMapping(params="method=stopReverse")
//	@ResponseBody
//	public AjaxJson stopReverse(String id) {
//		AllSpreadOrder order = allSpreadOrderDao.selectByKey(id);
//		if(order.getStatus() != 1){
//			return new AjaxJson(false,"订单状态有误");
//		}
//		AllSpreadOrder update = new AllSpreadOrder();
//		update.setStatus(9);
//		update.setId(id);
//		this.allSpreadOrderDao.updateByKeySelective(update);
//
//		return AjaxJson.SUCCESS;
//	}
//
//	@RequestMapping(params="method=resumeReverse")
//	@ResponseBody
//	public AjaxJson resumeReverse(String id) {
//		AllSpreadOrder order = allSpreadOrderDao.selectByKey(id);
//		if(order.getStatus() != 9){
//			return new AjaxJson(false,"订单状态有误");
//		}
//		AllSpreadOrder update = new AllSpreadOrder();
//		update.setStatus(1);
//		update.setId(id);
//		this.allSpreadOrderDao.updateByKeySelective(update);
//
//		return AjaxJson.SUCCESS;
//
//	}
//
//
//
//
//}