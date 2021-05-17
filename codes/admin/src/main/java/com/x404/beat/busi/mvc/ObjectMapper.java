//package com.x404.beat.busi.mvc;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.x404.beat.busi.entity.Match;
//import com.x404.beat.manage.sys.utils.UserUtils;
//import com.x404.beat.busi.tools.TeamManager;
//import com.x404.beat.core.util.JsonUtils;
//
//import java.io.IOException;
//import java.util.Optional;
//
///**
// * Created by chaox on 7/3/2017.
// */
//public class ObjectMapper extends com.fasterxml.jackson.databind.ObjectMapper {
//
//    public ObjectMapper() {
//        SimpleModule module = new SimpleModule();
//        module.addSerializer(Match.class, new JsonSerializer<Match>() {
//            @Override
//            public void serialize(Match value, JsonGenerator gen, SerializerProvider provider) throws IOException {
//                ObjectNode jsonNode = JsonUtils.valueToTree(value);
//                String locale = UserUtils.getCurrentUser().getLocale();
//                String stdHome = value.getStdHome();
//                String stdAway = value.getStdAway();
//
//                if(locale != null){
//                    if(stdHome != null){
//                        String tempHome = Optional.ofNullable(TeamManager.getInstance().getTeam(stdHome)).map(team -> team.getTranslations().get(locale)).orElse(null);
//                        if(tempHome != null){
//                            stdHome = tempHome;
//                            jsonNode.put("home", stdHome);
//                        }
//                    }
//                    if(stdAway != null){
//                        String tempAway = Optional.ofNullable(TeamManager.getInstance().getTeam(stdAway)).map(team -> team.getTranslations().get(locale)).orElse(null);
//                        if(tempAway != null){
//                            stdAway = tempAway;
//                            jsonNode.put("away", stdAway);
//                        }
//                    }
//                }
//                gen.writeTree(jsonNode);
//            }
//        });
////                .addSerializer(AllSpread.class, new JsonSerializer<AllSpread>() {
////            @Override
////            public void serialize(AllSpread value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
////                ObjectNode jsonNode = JsonUtils.valueToTree(value);
////                String stdHome = value.getHome();
////                String stdAway = value.getAway();
////                String locale = UserUtils.getCurrentUser().getLocale();
////                if(locale != null){
////                    if(stdHome != null){
////                        String tempHome = Optional.ofNullable(TeamManager.getInstance().getTeam(stdHome)).map(team -> team.getTranslations().get(locale)).orElse(null);
////                        if(tempHome != null){
////                            stdHome = tempHome;
////                        }
////                    }
////                    if(stdAway != null){
////                        String tempAway = Optional.ofNullable(TeamManager.getInstance().getTeam(stdAway)).map(team -> team.getTranslations().get(locale)).orElse(null);
////                        if(tempAway != null){
////                            stdAway = tempAway;
////                        }
////                    }
////                }
////                if(stdHome != null){
////                    jsonNode.put("home", stdHome);
////                }
////                if(stdAway != null){
////                    jsonNode.put("away", stdAway);
////                }
////                gen.writeTree(jsonNode);
////            }
////        });
//        this.registerModule(module);
//    }
//}
