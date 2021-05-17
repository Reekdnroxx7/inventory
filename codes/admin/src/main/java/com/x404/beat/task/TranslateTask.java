package com.x404.beat.task;

import com.x404.beat.core.util.DateTimeUtils;
import com.x404.beat.models.SbobetMatch;
import com.x404.beat.models.Team;
import com.x404.beat.utils.Platform;
import com.xc350.web.base.mongo.MongoTemplate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/31.
 */
@Component("translateTask")
public class TranslateTask {

    private final static Logger logger = LoggerFactory.getLogger(TranslateTask.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    public void translate() {
        for (int i = 0; i < 7; i++) {
            String str = "";
            if (i != 0) {
                Date date1 = DateUtils.addDays(new Date(), i);
                str = "" + DateTimeUtils.formatDate(date1, "yyyy-MM-dd");
            }
            translateDate(str);
        }
    }

    public void translateDate(String date) {
        Document document;
        try {
            if (StringUtils.isNotBlank(date)) {
                date = "/" + date;
            }
            document = Jsoup.connect("https://www.sbobet.com/euro/football" + date).get();
            Map<String, SbobetMatch> enMap = parseDocument(document);
            document = Jsoup.connect("https://www.sbobet.com/zh-cn/euro/%E8%B6%B3%E7%90%83" + date).get();
            Map<String, SbobetMatch> cnMap = parseDocument(document);

            enMap.forEach((key, value) -> {
                SbobetMatch sbobetMatch = cnMap.get(value.getId());
                if (sbobetMatch != null) {
                    addTranslate(value.getHome(), "zh-CN", sbobetMatch.getHome());
                    addTranslate(value.getAway(), "zh-CN", sbobetMatch.getAway());
                }
            });
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }


    private Map<String, SbobetMatch> parseDocument(Document document) {
        ParseOddsHelper.GetOddsResult getOddsResult = ParseOddsHelper.parseSbobetOdds("",  document.outerHtml(),"", Platform.Sbobet);

        HashMap<String, SbobetMatch> retMap = new HashMap<>();
        if (getOddsResult != null && getOddsResult.sbobetMatches != null) {
            getOddsResult.sbobetMatches.forEach(sbobetMatch -> {
                retMap.put(sbobetMatch.getId(), sbobetMatch);
            });
        }
        return retMap;
    }

    private void addTranslate(String srcName, String locale, String stdName) {
        Update update = new Update();
        update.set(locale, stdName);
        update.set("_id",srcName);
        Query query = Query.query(Criteria.where("_id").is(srcName));
        mongoTemplate.upsert(query, update, Team.class);
    }

}
