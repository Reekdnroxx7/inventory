//package com.x404.beat.busi;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.mongodb.MongoClient;
//import com.mongodb.MongoCredential;
//import com.mongodb.ServerAddress;
//import com.x404.beat.dao.TeamDao;
//import com.x404.beat.busi.sbobet.client.SbobetUtils;
//import com.x404.beat.busi.sbobet.entity.SbobetMatch;
//import com.x404.beat.core.util.DateTimeUtils;
//import com.x404.beat.core.util.DateUtils;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//import java.io.IOException;
//import java.net.UnknownHostException;
//import java.util.*;
//
///**
// * Created by chaox on 7/6/2017.
// */
//public class TranslateTask {
//
//    static Map<String,SbobetMatch> parseMatchNode(JsonNode matchNode){
//        Iterator<JsonNode> iterator = matchNode.path(1).iterator();
//        Map<String,SbobetMatch> matchMap = new HashMap<>();
//        while (iterator.hasNext()) {
//            JsonNode match = iterator.next();
//            SbobetMatch sbobetMatch = processMatch(match);
//            if(sbobetMatch != null){
//                matchMap.put(sbobetMatch.getId(),sbobetMatch);
//            }
//        }
//        return matchMap;
//    }
//
//    private static SbobetMatch processMatch(JsonNode match) {
//        if (match instanceof ArrayNode) {
//            JsonNode matchInfo = match.get(2);
//            Integer sport_id = SbobetUtils.toInvalidInteger(match.get(0).asInt());
//            Integer league_id = SbobetUtils.toInvalidInteger(match.get(1).asInt());
//            Integer event_id = SbobetUtils.toInvalidInteger(matchInfo.get(0).asInt());
//            String homeName = SbobetUtils.toValidString(Optional.ofNullable(matchInfo.get(1)).map(JsonNode::asText).orElse(null));
//            String awayName = SbobetUtils.toValidString(Optional.ofNullable(matchInfo.get(2)).map(JsonNode::asText).orElse(null));
//            String dateStr = SbobetUtils.toValidString(Optional.ofNullable(matchInfo.get(5)).map(JsonNode::asText).orElse(null));
//
//            String match_id = String.valueOf(event_id);
//            SbobetMatch sbobetMatch = new SbobetMatch();
//            sbobetMatch.setId(match_id);
//            sbobetMatch.setHome(homeName);
//            sbobetMatch.setAway(awayName);
//            sbobetMatch.setSport_id(sport_id);
//            sbobetMatch.setLeague_id(league_id);
//            sbobetMatch.setEvent_id(event_id);
//            return sbobetMatch;
//        }
//        return  null;
//    }
//
//    public static void main(String[] args) {
//        try {
//
//
//            for(int i = 0; i< 7; i++){
//                String str = "";
//                if(i != 0){
//                    Date date1 = DateUtils.addDays(new Date(), i);
//                    str = "/"+ DateTimeUtils.formatDate(date1,"yyyy-MM-dd");
//                }
//                addTranslate(str);
//            }
//
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void addTranslate(String date) throws IOException {
//        TeamDao teamDao = new TeamDao();
//
//        MongoClient mongo = new MongoClient(new ServerAddress("118.184.18.67",27017),Collections.singletonList(
//                MongoCredential.createCredential("spinage","admin","1234Nm,.".toCharArray())));
//        MongoTemplate mongoTemplate = new MongoTemplate(mongo,"spinage");
//        teamDao.setMongoTemplate(mongoTemplate);
//        Document document = Jsoup.connect("https://www.sbobet.com/euro/football"+date).get();
//        Map<String, SbobetMatch> stringSbobetMatchMap = parseDocument(document);
//        document = Jsoup.connect("https://www.sbobet.com/zh-cn/euro/%E8%B6%B3%E7%90%83"+date).get();
//        Map<String, SbobetMatch> stringSbobetMatchMap1 = parseDocument(document);
//
//        stringSbobetMatchMap.entrySet().forEach(entry -> {
//            SbobetMatch value = entry.getValue();
//            value.getId();
//            SbobetMatch sbobetMatch = stringSbobetMatchMap1.get(value.getId());
//            if(sbobetMatch != null){
//                teamDao.addTranslate(value.getHome(),"zh-CN",sbobetMatch.getHome());
//                teamDao.addTranslate(value.getAway(),"zh-CN",sbobetMatch.getAway());
//            }
//        });
//    }
//
//    public static Map<String, SbobetMatch> parseDocument(Document document) throws IOException {
//        String html = document.outerHtml();
//        String scriptParams = SbobetUtils.getScriptParams("$P.onUpdate('od',", ");", html);
//        ArrayNode root = SbobetUtils.toArrayNode(scriptParams);
//        ArrayNode matchNode = (ArrayNode) root.get(2);
//
//        Map<String, SbobetMatch> stringSbobetMatchMap = parseMatchNode(matchNode.path(0));
//        Map<String, SbobetMatch> stringSbobetMatchMap1 = parseMatchNode(matchNode.path(1));
//        stringSbobetMatchMap.putAll(stringSbobetMatchMap1);
//
//
//        stringSbobetMatchMap.entrySet().forEach(entry -> {
//            System.out.println(entry.getKey()+"\t"+entry.getValue().getHome()+"\t"+entry.getValue().getAway());
//        });
//        return stringSbobetMatchMap;
//    }
//}
