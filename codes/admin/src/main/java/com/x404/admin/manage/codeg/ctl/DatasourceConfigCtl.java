package com.x404.admin.manage.codeg.ctl;

import com.alibaba.druid.pool.DruidDataSource;
import com.x404.admin.core.controller.BaseController;
import com.x404.admin.core.json.AjaxJson;
import com.x404.admin.core.util.IpUtil;
import com.x404.admin.core.util.SpringContextHolder;
import com.x404.module.utils.UUIDUtils;
import com.x404.admin.manage.codeg.FieldDecker;
import com.x404.admin.manage.codeg.entity.DatasourceConfig;
import com.x404.admin.manage.codeg.model.FieldConfig;
import com.x404.admin.manage.codeg.model.TableConfig;
import com.x404.admin.manage.codeg.service.impl.DatasourceConfigService;
import com.x404.admin.manage.codeg.utils.CodegUtils;
import com.x404.module.basedao.query.PageList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/datasourceConfig")
public class DatasourceConfigCtl extends BaseController {
    private final static Log LOG = LogFactory.getLog(DatasourceConfigCtl.class);
    private DatasourceConfigService datasourceConfigService;

    @Autowired
    public void setDatasourceConfigService(DatasourceConfigService datasourceConfigService) {
        this.datasourceConfigService = datasourceConfigService;
    }

    @Autowired
    protected FieldDecker fieldDecker;


    public FieldDecker getFieldDecker() {
        return fieldDecker;
    }

    public void setFieldDecker(FieldDecker fieldDecker) {
        this.fieldDecker = fieldDecker;
    }

    @RequestMapping(params = "method=listDatasourceConfig")
    @ResponseBody
    /**
     * 用mybatis实现页面的分页显示
     */
    public PageList<DatasourceConfig> listDatasourceConfig(HttpServletRequest request) {
        PageList<DatasourceConfig> pageList = new PageList<DatasourceConfig>();
        Map<String, DruidDataSource> dataSourceMap = SpringContextHolder.getBeansOfType(DruidDataSource.class);
        List<DatasourceConfig> findAll = datasourceConfigService.selectAll();
        for (Map.Entry<String, DruidDataSource> entry : dataSourceMap.entrySet()) {
            DatasourceConfig datasourceConfig = new DatasourceConfig();
            datasourceConfig.setId(entry.getKey());
            datasourceConfig.setUrl(entry.getValue().getUrl());
            findAll.add(datasourceConfig);
        }
        pageList.setTotalCount(findAll.size());
        pageList.setResultList(findAll);
        return pageList;
    }


    @RequestMapping(params = "method=saveDatasourceConfig")
    @ResponseBody
    /*
	 * 用mybatis来增加表数据
	 */
    public AjaxJson saveDatasourceConfig(HttpServletRequest request, DatasourceConfig datasourceConfig) {

        String saveType = ServletRequestUtils.getStringParameter(request,
                "_saveType", "add");

        try {
            if ("add".equals(saveType)) {
                datasourceConfig.setId(UUIDUtils.next());
                datasourceConfig.setHost(IpUtil.getIpAddr(request));
                datasourceConfigService.insert(datasourceConfig);
            } else {
                Map<String, DruidDataSource> dataSourceMap = SpringContextHolder.getBeansOfType(DruidDataSource.class);
                if (dataSourceMap.containsKey(datasourceConfig.getId())) {
                    return new AjaxJson(false, "该配置为系统数据库，无法更新");
                }
                datasourceConfig.setHost(IpUtil.getIpAddr(request));
                datasourceConfigService.updateByKeySelective(datasourceConfig);
            }
            return new AjaxJson(true, datasourceConfig);

        } catch (Exception e) {
            LOG.error(e);
            return new AjaxJson(false, "操作失败");
        }
    }


    /**
     * 删除这个是用mybatis的方式
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "method=deleteDatasourceConfig")
    @ResponseBody
    public AjaxJson deleteDatasourceConfig(@RequestParam("id") String id, HttpServletRequest request) {

        try {
            Map<String, DruidDataSource> dataSourceMap = SpringContextHolder.getBeansOfType(DruidDataSource.class);
            if (dataSourceMap.containsKey(id)) {
                return new AjaxJson(false, "该配置为系统数据库，无法删除");
            }
            datasourceConfigService.deleteByKey(id);
            return new AjaxJson(true, "操作成功");
        } catch (Exception e) {
            LOG.error(e);
            return new AjaxJson(false, "操作失败");
        }
    }

    @RequestMapping(params = "method=listCodegTable")
    @ResponseBody
    public PageList<TableConfig> listCodegTable(HttpServletRequest request, @RequestParam("dataSourceId") String dataSourceId) {
        DatasourceConfig config = getDataSourceConfig(dataSourceId);
        List<TableConfig> allTable = CodegUtils.getAllTable(config);
        PageList<TableConfig> pageList = new PageList<TableConfig>(allTable);
        return pageList;
    }

    private DatasourceConfig getDataSourceConfig(String dataSourceId) {
        Map<String, DruidDataSource> dataSourceMap = SpringContextHolder.getBeansOfType(DruidDataSource.class);
        DatasourceConfig config = null;
        if (dataSourceMap.containsKey(dataSourceId)) {
            config = getSystemDatasourceConfig(dataSourceId, dataSourceMap.get(dataSourceId));
        } else {
            config = this.datasourceConfigService.selectByKey(dataSourceId);
        }
        return config;
    }

    @RequestMapping(params = "method=listCodegField")
    @ResponseBody
    public PageList<FieldConfig> listCodegField(@RequestParam("dataSourceId") String dataSourceId, HttpServletRequest request,
                                                String tableName) {
        DatasourceConfig dataSourceConfig = getDataSourceConfig(dataSourceId);
        List<FieldConfig> codegFields = CodegUtils.getAllFields(dataSourceConfig, tableName);
        CodegUtils.decoreteFields(dataSourceConfig, tableName, codegFields);
        this.fieldDecker.deckFields(codegFields);
        return new PageList<FieldConfig>(codegFields);
    }


    private DatasourceConfig getSystemDatasourceConfig(
            String key, DruidDataSource dataSource) {
        DatasourceConfig datasourceConfig = new DatasourceConfig();
        datasourceConfig.setId(key);
        datasourceConfig.setUrl(dataSource.getUrl());
        datasourceConfig.setDbType(dataSource.getDbType());
        datasourceConfig.setUserName(dataSource.getUsername());
        datasourceConfig.setPassword(dataSource.getPassword());
        return datasourceConfig;
    }


}