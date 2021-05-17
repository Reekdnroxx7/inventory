package com.x404.beat.service;


import com.x404.beat.dao.SbobetLeagueDao;
import com.x404.beat.models.League;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaox on 8/3/2017.
 */
@Lazy(false)
@Service
public class LeagueManager implements InitializingBean
{
    private static Map<String, League> leagueMap = new HashMap<>();

    @Autowired
    private  SbobetLeagueDao sbobetLeagueDao;

    public LeagueManager() {

    }


    public void refresh() {

        leagueMap.clear();
        sbobetLeagueDao.selectAll().forEach(league -> {
            leagueMap.put(league.getId(), league);
        });
    }

    public static League getLeague(String id) {
        return leagueMap.get(id);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        refresh();
    }
}
