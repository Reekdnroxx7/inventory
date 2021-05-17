//package com.x404.beat.busi;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.mongodb.MongoClient;
//import com.mongodb.MongoCredential;
//import com.mongodb.ServerAddress;
//import com.x404.beat.busi.sbobet.client.SbobetUtils;
//import com.x404.beat.busi.sbobet.dao.SbobetLeagueDao;
//import com.x404.beat.busi.sbobet.entity.League;
//import org.apache.commons.io.FileUtils;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * Created by chaox on 8/3/2017.
// */
//public class GetLeagueTask
//{
//
//    private static class Region{
//        private String id;
//        private String name;
//        private String cn_name;
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getCn_name() {
//            return cn_name;
//        }
//
//        public void setCn_name(String cn_name) {
//            this.cn_name = cn_name;
//        }
//    }
//
//    public static void main(String[] args) {
//        try {
//            String s = FileUtils.readFileToString(new File("E:\\league.txt"));
//            parseEnglish(s);
//            s = FileUtils.readFileToString(new File("E:\\league_cn.txt"),"unicode");
//            parseZW(s);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void parseZW(String str) {
//        String scriptParams = SbobetUtils.getScriptParams("$P.setElement('tournaments',", ");", str);
//        try {
//            MongoClient mongo = new MongoClient(new ServerAddress("118.184.18.67",27017), Collections.singletonList(
//                    MongoCredential.createCredential("spinage","admin","1234Nm,.".toCharArray())));
//            MongoTemplate mongoTemplate = new MongoTemplate(mongo,"spinage");
//            SbobetLeagueDao leagueDao = new SbobetLeagueDao();
//            leagueDao.setMongoTemplate(mongoTemplate);
//            List<Region> regions = getRegions(scriptParams,false);
//            Map<String, Region> regionMap = regions.stream().collect(Collectors.toMap(region -> region.getId(), region -> region));
//
//            List<League> leagues = getLeagues(scriptParams, false);
//            for(League league : leagues){
//                Region region = regionMap.get(league.getRegionId());
//                if(region != null){
//                    league.setRegionId(region.getId());
//                    league.setRegion_name(region.getName());
//                    league.setRegion_cn_name(region.getCn_name());
//                }
//            }
//            for(League league : leagues){
//                System.out.println(league.getLeague_cn_name());
//                leagueDao.upsert(league);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void parseEnglish(String str){
//        String scriptParams = SbobetUtils.getScriptParams("$P.setElement('tournaments',", ");", str);
//        try {
//            MongoClient mongo = new MongoClient(new ServerAddress("118.184.18.67",27017),Collections.singletonList(
//                    MongoCredential.createCredential("spinage","admin","1234Nm,.".toCharArray())));
//            MongoTemplate mongoTemplate = new MongoTemplate(mongo,"spinage");
//            SbobetLeagueDao leagueDao = new SbobetLeagueDao();
//            leagueDao.setMongoTemplate(mongoTemplate);
//            List<Region> regions = getRegions(scriptParams,true);
//            Map<String, Region> regionMap = regions.stream().collect(Collectors.toMap(region -> region.getId(), region -> region));
//
//            List<League> leagues = getLeagues(scriptParams, true);
//            for(League league : leagues){
//                Region region = regionMap.get(league.getRegionId());
//                if(region != null){
//                    league.setRegionId(region.getId());
//                    league.setRegion_name(region.getName());
//                    league.setRegion_cn_name(region.getCn_name());
//                }
//            }
//            for(League league : leagues){
//                System.out.println(league.getLeague_name());
//                leagueDao.upsert(league);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static List<Region> getRegions(String scriptParams, boolean en) throws IOException {
//        List<Region> regions = new ArrayList<>();
//        ArrayNode arrayNode = SbobetUtils.toArrayNode(scriptParams);
//        if(arrayNode != null){
//            Iterator<JsonNode> iterator = arrayNode.iterator();
//            while (iterator.hasNext()){
//                //[8,'FRANCE LIGUE 1',1,24,'e1.e001817080401',10,0,0,0,'','']
//                JsonNode next = iterator.next();
//                int i = next.get(2).asInt();
//                if(i != 1){
//                    continue;
//                }
//                Region region = new Region();
//                region.setId(next.get(0).asText());
//                if(en){
//                    region.setName(next.get(1).asText());
//                }else {
//                    region.setCn_name(next.get(1).asText());
//                }
//                regions.add(region);
//            }
//        }
//        return regions;
//    }
//
//    public static List<League> getLeagues(String scriptParams, boolean en) throws IOException {
//        List<League> leagues = new ArrayList<>();
//        ArrayNode arrayNode = SbobetUtils.toArrayNode(scriptParams);
//        if(arrayNode != null){
//            Iterator<JsonNode> iterator = arrayNode.iterator();
//            while (iterator.hasNext()){
//                //[8,'FRANCE LIGUE 1',1,24,'e1.e001817080401',10,0,0,0,'','']
//
//                JsonNode next = iterator.next();
//                int i = next.get(2).asInt();
//                if(i != 1){
//                    continue;
//                }
//                League league = new League();
//                league.setId(next.get(0).asText());
//                if(en){
//                    league.setLeague_name(next.get(1).asText());
//                }else {
//                    league.setLeague_cn_name(next.get(1).asText());
//                }
//                league.setRegionId(next.get(3).asText());
//                leagues.add(league);
//            }
//        }
//        return leagues;
//    }
//}
