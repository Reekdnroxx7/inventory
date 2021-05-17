package com.x404.beat.manage.sys.utils;

import com.x404.beat.core.util.SpringContextHolder;
import com.x404.beat.manage.sys.entity.Dict;
import com.x404.beat.manage.sys.service.IDictService;
import com.x404.beat.manage.sys.tools.DictConfig;
import com.x404.beat.manage.sys.tools.DictTable;
import com.x404.beat.manage.sys.tools.DictTables;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author xiechao
 *         字典单例
 *         使用本地HashMap作为缓存。不支持分布式。
 */
public class DictManager {

    //	/**
//	 * 保存 dict.xml 表中注册过的 table;
//	 */
//	private Map<String,List<DictEntry>> otherDicts = new HashMap<String, List<DictEntry>>();
    private Map<String, DictTable> tableMap = new HashMap<String, DictTable>();
    private DictConfig dictConfig;

    private static DictManager instance = new DictManager();
    private IDictService dictService;
    private Map<String, List<Dict>> dicts;

    public static DictManager getInstance() {
        return instance;
    }

    private DictManager() {
        this.dictService = SpringContextHolder.getBean(IDictService.class);
        afterPropertiesSet();
    }

    public synchronized void refresh() {
        dicts = new HashMap<String, List<Dict>>();
        refreshDDict();
        refreshFDict();
    }

    private void refreshDDict() {
        List<Dict> allDicts = dictService.findAll();
        for (Dict d : allDicts) {
            put(d);
        }
    }

    private void refreshFDict() {
        Set<Entry<String, DictTable>> entrySet = tableMap.entrySet();
        for (Entry<String, DictTable> entry : entrySet) {
            addOtherDict(entry.getKey());
        }
    }

    private void put(Dict d) {
        List<Dict> list = dicts.get(d.getGroupCode());
        if (list == null) {
            list = new ArrayList<Dict>();
            dicts.put(d.getGroupCode(), list);
        }
        list.add(d);
    }

    public List<Dict> getDict(String groupCode) {
        if (dicts == null) {  // 非线程安全
            refresh();
        }
        List<Dict> ds = dicts.get(groupCode);
        if (ds == null) {
            return addOtherDict(groupCode);
        }
        return ds == null ? new ArrayList<Dict>() : ds;
    }

    private List<Dict> addOtherDict(String id) {
        DictTable dictTable = tableMap.get(id);
        if (dictTable == null) {
            return null;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("select ").
                append(dictTable.getKeyColumn()).append(",")
                .append(dictTable.getValueColumn()).
                append(" from ").
                append(dictTable.getTableName());
        if (StringUtils.isNotBlank(dictTable.getCondition())) {
            sql.append(" where ").append(dictTable.getCondition());
        }
        if (StringUtils.isNotBlank(dictTable.getOrderBy())) {
            sql.append(" order by ").append(dictTable.getOrderBy());
        }
        JdbcTemplate jt = new JdbcTemplate();
        DataSource dataSource = DataSourceUtils.getDataSourceById("dataSource");
        jt.setDataSource(dataSource);
        List<Map<String, Object>> queryForList = jt.queryForList(sql.toString());
        List<Dict> dictEntries = new ArrayList<Dict>(queryForList.size());
        if (queryForList != null) {
            for (Map<String, Object> map : queryForList) {
                Dict entry = new Dict();
                entry.setDictCode(String.valueOf(map.get(dictTable.getKeyColumn())));
                entry.setDictValue(String.valueOf(map.get(dictTable.getValueColumn())));
                dictEntries.add(entry);
            }
            dicts.put(dictTable.getId(), dictEntries);
        }
        return dictEntries;
    }


    public final void afterPropertiesSet() {
        this.dictConfig = new DictConfig();
        dictConfig.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                DictTables dictTables = dictConfig.getDictTables();
                if (dictTables != null && dictTables.getDictTables() != null) {
                    tableMap.clear();
                    for (DictTable dictTable : dictTables.getDictTables()) {
                        tableMap.put(dictTable.getId(), dictTable);
                    }
                }
            }
        });
    }
}
