package com.x404.beat.task;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.x404.beat.models.League;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.PropertyHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by chaox on 8/3/2017.
 */
@Component("getLeagueTask")
public class GetLeagueTask {

    private final static Logger logger = LoggerFactory.getLogger(TranslateTask.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    private static class Region {
        private String id;
        private String name;
        private String cn_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCn_name() {
            return cn_name;
        }

        public void setCn_name(String cn_name) {
            this.cn_name = cn_name;
        }
    }

    public void execute() {
        try {
            String zhBody = Unirest.get("https://www.sbobet.com/zh-cn/resource/e/euro-dynamic.js").asString().getBody();
            parseZW(zhBody);
            String enBody = Unirest.get("https://www.sbobet.com/en/resource/e/euro-dynamic.js").asString().getBody();
            parseEnglish(enBody);

        } catch (UnirestException e) {
            logger.error(e.getMessage(), e);
        }
    }


    private void parseZW(String str) {
        String scriptParams = SbobetUtils.getScriptParams("$P.setElement('tournaments',", ");", str);
        try {

            List<Region> regions = getRegions(scriptParams, false);
            Map<String, Region> regionMap = regions.stream().collect(Collectors.toMap(Region::getId, region -> region));

            List<League> leagues = getLeagues(scriptParams, false);
            for (League league : leagues) {
                Region region = regionMap.get(league.getRegionId());
                if (region != null) {
                    league.setRegionId(region.getId());
                    league.setRegion_name(region.getName());
                    league.setRegion_cn_name(region.getCn_name());
                }
            }
            for (League league : leagues) {
                Update update = this.buildUpdate(league);
                update.setOnInsert("disable", true);
                this.mongoTemplate.upsert(Query.query(Criteria.where("_id").is(league.getId())), update, League.class);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void parseEnglish(String str) {
        String scriptParams = SbobetUtils.getScriptParams("$P.setElement('tournaments',", ");", str);
        try {
            List<Region> regions = getRegions(scriptParams, true);
            Map<String, Region> regionMap = regions.stream().collect(Collectors.toMap(Region::getId, region -> region));

            List<League> leagues = getLeagues(scriptParams, true);
            for (League league : leagues) {
                Region region = regionMap.get(league.getRegionId());
                if (region != null) {
                    league.setRegionId(region.getId());
                    league.setRegion_name(region.getName());
                    league.setRegion_cn_name(region.getCn_name());
                }
            }
            for (League league : leagues) {
                Update update = this.buildUpdate(league);
                update.setOnInsert("disable", true);
                this.mongoTemplate.upsert(Query.query(Criteria.where("_id").is(league.getId())), update, League.class);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private List<Region> getRegions(String scriptParams, boolean en) throws IOException {
        List<Region> regions = new ArrayList<>();
        ArrayNode arrayNode = SbobetUtils.toArrayNode(scriptParams);
        if (arrayNode != null) {
            Iterator<JsonNode> iterator = arrayNode.iterator();
            while (iterator.hasNext()) {
                //[8,'FRANCE LIGUE 1',1,24,'e1.e001817080401',10,0,0,0,'','']
                JsonNode next = iterator.next();
                int i = next.get(2).asInt();
                if (i != 1) {
                    continue;
                }
                Region region = new Region();
                region.setId(next.get(0).asText());
                if (en) {
                    region.setName(next.get(1).asText());
                } else {
                    region.setCn_name(next.get(1).asText());
                }
                regions.add(region);
            }
        }
        return regions;
    }

    public List<League> getLeagues(String scriptParams, boolean en) throws IOException {
        List<League> leagues = new ArrayList<>();
        ArrayNode arrayNode = SbobetUtils.toArrayNode(scriptParams);
        if (arrayNode != null) {
            Iterator<JsonNode> iterator = arrayNode.iterator();
            while (iterator.hasNext()) {
                //[8,'FRANCE LIGUE 1',1,24,'e1.e001817080401',10,0,0,0,'','']

                JsonNode next = iterator.next();
                int i = next.get(2).asInt();
                if (i != 1) {
                    continue;
                }
                League league = new League();
                league.setId(next.get(0).asText());
                if (en) {
                    league.setLeague_name(next.get(1).asText());
                } else {
                    league.setLeague_cn_name(next.get(1).asText());
                }
                league.setRegionId(next.get(3).asText());
                leagues.add(league);
            }
        }
        return leagues;
    }

    private Update buildUpdate(final Object entity) {
        Update update = new Update();
        MongoPersistentEntity<?> persistentEntity = mongoTemplate.getConverter().getMappingContext().getPersistentEntity(entity.getClass());
        persistentEntity.doWithProperties(new PropertyHandler<MongoPersistentProperty>() {
            @Override
            public void doWithPersistentProperty(MongoPersistentProperty persistentProperty) {
                Object value = ReflectionUtils.getField(persistentProperty.getField(), entity);
                if (value == null && persistentProperty.getGetter() != null) {
                    value = ReflectionUtils.invokeMethod(persistentProperty.getGetter(), entity);
                }
                if (value != null) {
                    update.set(persistentProperty.getName(), value);
                }
            }
        });
        return update;
    }
}
