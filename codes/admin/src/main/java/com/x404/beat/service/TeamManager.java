package com.x404.beat.service;


import com.x404.beat.dao.TeamDao;
import com.x404.beat.models.Team;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaox on 4/26/2017.
 */
@Service
public final class TeamManager implements InitializingBean
{

    private static Map<String, Team> teamMap = new HashMap<>();
    @Autowired
    private TeamDao teamDao;

    private TeamManager() {

    }


    public void refresh() {
        teamMap.clear();
        teamDao.selectAll().forEach(team -> {
            teamMap.put(team.getName(),team);
        });
    }

    public static boolean containsTeam(String name) {
        return teamMap.containsKey(name);
    }

    public static Team getTeam(String name) {
        return teamMap.get(name);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        refresh();
    }

    public void addTeam(Team team) {
        if( teamMap.containsKey(team) ) {
            return;
        }
        teamMap.put(team.getName(),team);
        this.teamDao.insertSelective(team);
    }
}
