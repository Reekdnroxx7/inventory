package com.x404.beat.task;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.x404.beat.core.util.DateTimeUtils;
import com.x404.beat.models.SbobetMatch;
import com.x404.beat.models.SbobetSpread;
import com.x404.beat.utils.Platform;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.x404.beat.task.SbobetUtils.toInvalidLong;
import static com.x404.beat.task.SbobetUtils.toValidDoubleValue;
import static com.x404.beat.task.SbobetUtils.toValidString;


/**
 * Created by chaox on 11/14/2017.
 */
public class ParseOddsHelper
{
    private final static Logger LOGGER = LoggerFactory.getLogger(ParseOddsHelper.class);

    public static class GetOddsResult
    {
        public List<SbobetMatch> sbobetMatches;
        public Long oddsToken;

        public GetOddsResult(List<SbobetMatch> sbobetMatches, Long oddsToken) {
            this.sbobetMatches = sbobetMatches;
            this.oddsToken = oddsToken;
        }
    }

    public static GetOddsResult parseSbobetOdds(String requestId, String resp, String uid, Platform platform) {
        Date init_time = new Date();
        String scriptParams = SbobetUtils.getScriptParams("$P.onUpdate('od',", ");", resp);
        if( StringUtils.isBlank(scriptParams) ) {
            return new GetOddsResult(null, null);
        }
        ArrayNode root = SbobetUtils.toArrayNode(scriptParams);
        Long siteToken = toInvalidLong(root.path(0));

        JsonNode matchesNode = root.path(2);
        JsonNode liveMatches = matchesNode.get(0);
        List<SbobetMatch> matchOutputs = new ArrayList<>();
        if( liveMatches instanceof ArrayNode) {
            matchOutputs.addAll(parseMatchesNode(liveMatches, requestId, init_time, uid, platform));
        }
        JsonNode earlyMatches = matchesNode.get(1);
        if( earlyMatches instanceof ArrayNode) {
            matchOutputs.addAll(parseMatchesNode(earlyMatches, requestId, init_time, uid, platform));
        }
        return new GetOddsResult(matchOutputs, siteToken);
    }

    private static List<SbobetMatch> parseMatchesNode(JsonNode matchesNode, String requestId, Date init_time, String uid, Platform platform) {
        List<SbobetMatch> matchOutputs = new ArrayList<>();
        try {
            for( JsonNode match : matchesNode.path(1) ) {
                SbobetMatch matchOutput = parseMatch(match, requestId, init_time, uid, platform);
                if( matchOutput != null ) {
                    matchOutputs.add(matchOutput);
                }
            }

        } catch( Exception e ) {
            LOGGER.error(e.getMessage(), e);
        }
        return matchOutputs;
    }


    private static SbobetMatch parseMatch(JsonNode match, String requestId, Date init_time, String uid, Platform platform) {
        if( match instanceof ArrayNode) {
            JsonNode matchInfo = match.get(2);
            Long sport_id = toInvalidLong(match.path(0));
            Long league_id = toInvalidLong(match.path(1));
            Long event_id = toInvalidLong(matchInfo.path(0));
            if( event_id == null ) {
                return null;
            }
            String homeName = toValidString(matchInfo.path(1));
            String awayName = toValidString(matchInfo.path(2));
            String dateStr = toValidString(matchInfo.path(5));

            String match_id = String.valueOf(event_id);

            SbobetMatch sbobetMatch = new SbobetMatch();
            sbobetMatch.setId(match_id);
            Long total = toInvalidLong(match.path(4).path(0).path(0));
            if( total == null || total <= 1 ) {
                return null;
            }
            sbobetMatch.setHome(homeName);
            sbobetMatch.setAway(awayName);
            sbobetMatch.setSport_id(sport_id);
            sbobetMatch.setLeague_id(league_id);
            sbobetMatch.setEvent_id(event_id);
            sbobetMatch.setPlatform(platform);
            if( StringUtils.isNotBlank(dateStr) ) {
                Date date = DateTimeUtils.parseDate(dateStr, "MM/dd/yyyy HH:mm");
                sbobetMatch.setStartTime(date);
            }

            JsonNode spreads = match.path(4);
            if( spreads == null || spreads instanceof NullNode) {
                return null;
            }
            List<SbobetSpread> sbobetSpreads = new ArrayList<>();
            for( JsonNode spread : spreads ) {
                SbobetSpread sbobetSpread = parseSpread(spread, requestId, init_time, uid, platform);
                if( sbobetSpread != null ) {
                    sbobetSpread.setMatch(sbobetMatch);
                    sbobetSpreads.add(sbobetSpread);
                }
            }

            sbobetMatch.setSbobetSpreads(sbobetSpreads);
            return sbobetMatch;
        }
        return null;
    }

    private static SbobetSpread parseSpread(JsonNode spread, String requestId, Date init_time, String uid, Platform platform) {
        JsonNode oddNode = spread.get(2);
        if( oddNode != null && oddNode.size() == 2 ) {
            Long lineId = toInvalidLong(spread.path(0));

            if( lineId == null || lineId == 0 ) {
                return null;
            }
            Double maxMoney ;
            try {
                maxMoney = toValidDoubleValue(spread.path(1).path(4));
            } catch( Exception e ) {
                LOGGER.error(spread.toString(), e);
                return null;
            }
            Double hdp = toValidDoubleValue(spread.path(1).path(5));
            Long type = toInvalidLong(spread.path(1).path(0));
            if( type != null && type != 1 ) {
                return null;
            }
            Double home = toValidDoubleValue(spread.path(2).path(0));
            Double away = toValidDoubleValue(spread.path(2).path(1));
            String id = String.valueOf(lineId);
            SbobetSpread sbobetSpread = new SbobetSpread();
            sbobetSpread.setId(id);
            sbobetSpread.setLine_id(lineId);
            if(hdp != null){
                if( hdp != 0 ) {
                    sbobetSpread.setHdp(0 - hdp);
                } else {
                    sbobetSpread.setHdp(hdp);
                }
            }
            sbobetSpread.setType(type);
            sbobetSpread.setHomeOdds(home);
            sbobetSpread.setAwayOdds(away);
            sbobetSpread.setMax_money(maxMoney);
            sbobetSpread.setInitTime(init_time);
            sbobetSpread.setLogId(requestId);
            sbobetSpread.setUid(uid);
            sbobetSpread.setPlatform(platform);
            return sbobetSpread;
        }
        return null;
    }
}
