package com.x404.beat.ctl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.x404.beat.core.controller.BaseController;
import com.x404.beat.core.json.AjaxJson;
import com.x404.beat.dao.SbobetLeagueDao;
import com.x404.beat.dao.TeamDao;
import com.x404.beat.models.League;
import com.x404.beat.models.Team;
import com.xc350.web.base.mongo.query.MongoQueryHelper;
import com.xc350.web.base.query.PageList;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/spreadsorder")
public class SpreadsOrderCtl extends BaseController {
    private final static Log LOG =  LogFactory.getLog(SpreadsOrderCtl.class);
    public static final String SPREADS_ORDER = "spreadsOrder";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SbobetLeagueDao sbobetLeagueDao;

    @Autowired
    private TeamDao teamDao;

    public static class OrderInput{
        public Integer status;
        public Date updateTime;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }
    }

	@RequestMapping(params="method=listSpreadOrder")
	@ResponseBody
	public PageList<ObjectNode> listSpreadOrder(HttpServletRequest request) {
//		Query query = MongoQueryHelper.generateFromRequest(request);
//		query.with(new Sort(Sort.Direction.DESC,"update_time"));
        Query query = MongoQueryHelper.generateFromRequest(request,OrderInput.class);
        query.with(new Sort(Sort.Direction.DESC, "updateTime"));
        List<ObjectNode> collect = mongoTemplate.find(query, JsonNode.class,SPREADS_ORDER).stream().map(jsonNode -> {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            String leagueId = objectNode.path("initSpread").path("match").path("league_id").textValue();

            String uniqueKey = objectNode.path("initSpread").path("uniqueKey").textValue();
            if (StringUtils.isNotBlank(leagueId)) {

                League league = sbobetLeagueDao.selectByKey(leagueId);
                if (league != null) {
                    objectNode.put("league_name", league.getLeague_name());
                    objectNode.put("league_cn_name", league.getLeague_cn_name());
                }
            }

            String[] split = uniqueKey.split("_");
            Team stdHome = teamDao.selectByKey(split[0]);
            if (stdHome != null) {
                objectNode.put("home_cn_name", stdHome.getTranslations().get("zh-CN"));
            }
            Team stdAway = teamDao.selectByKey(split[1]);
            if (stdAway != null) {
                objectNode.put("away_cn_name", stdAway.getTranslations().get("zh-CN"));
            }
            return objectNode;

        }).collect(Collectors.toList());

        return new PageList<>((int)mongoTemplate.count(query, SPREADS_ORDER),collect);
	}


	@RequestMapping(params="method=stopReverse")
	@ResponseBody
	public AjaxJson stopReverse(String id) {

        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("pauseErrorHandler",true);
        mongoTemplate.updateFirst(query,update,SPREADS_ORDER);
		return AjaxJson.SUCCESS;
	}

	@RequestMapping(params="method=resumeReverse")
	@ResponseBody
	public AjaxJson resumeReverse(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("pauseErrorHandler",false);
        mongoTemplate.updateFirst(query,update,SPREADS_ORDER);

		return AjaxJson.SUCCESS;

	}




}