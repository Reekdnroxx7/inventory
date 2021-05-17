package com.x404.beat.ctl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.databind.util.ISO8601Utils;
import com.x404.beat.core.util.DateTimeUtils;
import com.xc350.web.base.mongo.query.MongoQueryHelper;
import com.xc350.web.base.query.PageList;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by chaox on 11/28/2017.
 */
@Controller()
@RequestMapping("spread")
public class SpreadCtl {


    @Autowired
    private MongoTemplate mongoTemplate;

//    @GetMapping(value = "/spread/list")
//    public CompletableFuture<Result<Page<Spread>>> pageList(HttpServletRequest httpServerRequest, Pageable pageable) {
//
//        Query query = MongoQueryHelper.generateFromRequest(httpServerRequest, Spread.class);
//        query.skip(pageable.getOffset());
//        query.limit(pageable.getPageSize());
//        return this.spreadStoreService.queryCurrentSpreads(query).thenCompose(spreads -> {
//            return this.spreadStoreService.countCurrentSpreads(query).thenApply(count -> {
//                return Result.ok(new PageImpl<>(spreads, pageable, count));
//            });
//        });
//    }

    private static class SpreadsOutput {
        public final String id;
        public final Date startTime;
        public final String home;
        public final String away;
        public final Double hdp;
        public Map<String, SpreadOutput> spreads = new HashMap<>();

        private SpreadsOutput(String id, Date startTime, String home, String away, Double hdp) {
            this.id = id;
            this.startTime = startTime;
            this.home = home;
            this.away = away;
            this.hdp = hdp;
        }
    }

    @RequestMapping(params = "method=listSpread")
    @ResponseBody
    public PageList<SpreadsOutput> spreadsList(HttpServletRequest request, String regex) {

//        Query query = new Query();
//        if (StringUtils.isNotBlank(regex)) {
//            query.addCriteria(Criteria.where("_id").regex(regex));
//        }
        Query query = MongoQueryHelper.generateFromRequest(request);
        query.addCriteria(Criteria.where("match.startTime").gt(DateUtils.addHours(new Date(), -2)));
//        query.skip(pageable.getOffset());
//        query.limit(pageable.getPageSize());
        query.with(new Sort("match.startTime", "_id"));
        List<JsonNode> maxSpreads = this.queryMaxSpreads(query);


        Map<String, SpreadsOutput> outputMap = new HashMap<>();
        List<String> keys = new ArrayList<>();
        List<SpreadsOutput> outputs = new ArrayList<>();
        maxSpreads.forEach(spread -> {
            String id = spread.path("_id").asText();
            keys.add(id);
            String dStr = spread.path("match").path("startTime").path("$date").asText();


            Date startDate = null;
            try {
                startDate = ISO8601Utils.parse(dStr, new ParsePosition(0));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            SpreadsOutput spreadsOutput = new SpreadsOutput(id,
                    startDate, spread.path("match").path("home").asText(),
                    spread.path("match").path("away").asText(), spread.path("hdp").asDouble());
            if (spread.get("maxHome") != null) {
                spreadsOutput.spreads.put("maxHome", new SpreadOutput(spread.get("maxHome")));
            }
            if (spread.get("maxAway") != null) {
                spreadsOutput.spreads.put("maxAway", new SpreadOutput(spread.get("maxHome")));
            }
            outputMap.put(id, spreadsOutput);
            outputs.add(spreadsOutput);
        });

        long count = this.countMaxSpreads(query);
        Query detailQ = Query.query(Criteria.where("uniqueKey").in(keys));
        List<JsonNode> jsonNodes = this.queryCurrentSpreads(detailQ);

        jsonNodes.forEach(spread -> {
            SpreadsOutput spreadsOutput = outputMap.get(spread.path("uniqueKey").asText());
            if (spreadsOutput != null) {
                spreadsOutput.spreads.put(spread.path("platform").asText(), new SpreadOutput(spread));
            }
        });
        return new PageList<>((int) count, outputs);
    }

    private List<JsonNode> queryCurrentSpreads(Query detailQ) {
        return this.mongoTemplate.find(detailQ, JsonNode.class, "currentSpread");
    }

    private long countMaxSpreads(Query query) {

        return this.mongoTemplate.count(query, "maxSpread");
    }

    private List<JsonNode> queryMaxSpreads(Query query) {
        return this.mongoTemplate.find(query, JsonNode.class, "maxSpread");
    }


//    @GetMapping(value = "/spreads/{uniqueKey}/history")
//    @ResponseBody
//    public CompletableFuture<Result<EChart>> spreadHistory(HttpServletRequest request, @PathVariable String uniqueKey, Date startTime, Date endTime) {
//        if (startTime == null) {
//            startTime = DateUtils.addHours(new Date(), -24);
//        }
//        if (endTime == null) {
//            endTime = new Date();
//        }
//
//        Query query = new Query();
//        query.addCriteria(Criteria.where("uniqueKey").is(uniqueKey))
//
////                .addCriteria(Criteria.where("initTime").gt(startTime).lte(endTime))
//                .with(Sort.by("initTime"));
////
//        return this.spreadStoreService.queryHistorySpreads(query).thenApply(spreads -> {
//
//            Map<Platform, List<Spread>> spreadGroup = spreads.stream().collect(Collectors.groupingBy(Spread::getPlatform));
//
//            Optional<List<EChart.Series>> series = spreadGroup.entrySet().stream().map(entry -> {
//                Platform platform = entry.getKey();
//                List<Spread> value = entry.getValue();
//                List<EChart.Data> homeOdds = new ArrayList<>();
//                List<EChart.Data> awayOdds = new ArrayList<>();
//                value.forEach(spread -> {
//                    if (spread.getHomeOdds() != null) {
//                        double revert = 1 / spread.getHomeOdds();
//                        homeOdds.add(new EChart.Data(null, new Object[]{spread.getInitTime(), platform, "home", spread.getHomeOdds(), revert, 1 - revert}));
//                    }
//                    if (spread.getAwayOdds() != null) {
//                        double revert = 1 / spread.getAwayOdds();
//                        awayOdds.add(new EChart.Data(null, new Object[]{spread.getInitTime(), platform, "away", spread.getAwayOdds(), revert, 1 - revert}));
//                    }
//                });
//                return Arrays.asList(new EChart.Series(true, platform.toString() + "主队赔率", homeOdds), new EChart.Series(false, platform.toString() + "客队赔率", awayOdds));
//            }).reduce((a, b) -> {
//                List<EChart.Series> arrayList = new ArrayList<>();
//                if (a != null) {
//                    arrayList.addAll(a);
//                }
//                if (b != null) {
//                    arrayList.addAll(b);
//                }
//                return arrayList;
//            });
//            return series.map(series1 -> Result.ok(new EChart(series1))).orElseGet(Result::NoContent);
//        });
//    }

//    @GetMapping(value = "/spreads/{uniqueKey}/max/history")
//    @ResponseBody
//    public CompletableFuture<Result<EChart>> maxSpreadHistory(HttpServletRequest request, @PathVariable String uniqueKey, Date startTime, Date endTime, boolean reverse){
//        if(startTime == null){
//            startTime = DateUtils.addHours(new Date(),-5);
//        }
//        if(endTime == null){
//            endTime = new Date();
//        }
//
//        Query query = new Query();
//        query.addCriteria(Criteria.where("uniqueKey").is(uniqueKey)).addCriteria(Criteria.where("initTime").gt(startTime).lte(endTime)).with(Sort.by("initTime"));
////
//        return this.spreadStoreService.queryHistorySpreads(query).thenApply(spreads -> {
//
//            Map<Platform, List<Spread>> spreadGroup = spreads.stream().collect(Collectors.groupingBy(Spread::getPlatform));
//
//            Optional<List<EChart.Series>> series = spreadGroup.entrySet().stream().map(entry -> {
//                Platform platform = entry.getId();
//                List<Spread> value = entry.getValue();
//                List<EChart.Data> homeOdds = new ArrayList<>();
//                List<EChart.Data> awayOdds = new ArrayList<>();
//                value.forEach(spread -> {
//
//                    homeOdds.add(new EChart.Data(null, new Object[]{spread.getInitTime(), spread.getHomeOdds()}));
//                    awayOdds.add(new EChart.Data(null, new Object[]{spread.getInitTime(), spread.getAwayOdds()}));
//                });
//                return Arrays.asList(new EChart.Series(platform.toString() + "客队赔率", awayOdds), new EChart.Series(platform.toString() + "主队赔率", homeOdds));
//            }).collect(Collectors.reducing((a, b) -> {
//                a.addAll(b);
//                return a;
//            }));
//            if(series.isPresent()){
//                return  Result.ok(new EChart(series.get()));
//            }else {
//                return Result.NoContent();
//            }
//        });
//    }
}
