package com.x404.beat.manage.codeg;

import com.x404.beat.core.util.ContextHolderUtils;
import com.x404.beat.manage.codeg.entity.DatasourceConfig;
import com.x404.beat.manage.codeg.model.TableConfig;
import com.x404.beat.manage.codeg.utils.CodegUtils;
import com.x404.beat.manage.codeg.model.FieldConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCodeGenerator implements CodeGenerator {
    private final Log logger = LogFactory.getLog(AbstractCodeGenerator.class);


    public static File getBaseDir() throws IOException {
        String dir = ContextHolderUtils.getSession().getServletContext().getRealPath("/codeg");
        File fileDir = new File(dir);
        FileUtils.forceMkdir(fileDir);
        return fileDir;
    }


    public File generate(DatasourceConfig dataSource, TableConfig table, List<FieldConfig> fields,
                         String pkg, String module, String description) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        cfg.setDefaultEncoding("UTF-8");
        FileOutputStream fos = null;
        Map<String, Object> data = new HashMap<String, Object>();
        decorate(dataSource, table, fields, pkg, module, description, data);
        try {
            File templateFile = getTemplateFile();
            cfg.setDirectoryForTemplateLoading(templateFile.getParentFile());
            Template template = cfg.getTemplate(templateFile.getName());
            File file = getOutputFile(data);
//			File file = new File(fileDir,getGenerateFileName(table));
            fos = new FileOutputStream(file);
            Writer out = new OutputStreamWriter(fos,
                    "utf-8");

            template.process(data, out);
            return file;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.error(e, e);
                }
            }
        }
    }


    protected void decorate(DatasourceConfig dataSource,
                            TableConfig table, List<FieldConfig> fields,
                            String pkg, String module, String description,
                            Map<String, Object> data) {
        data.put("package", pkg);
        data.put("dataSource", dataSource);
        data.put("table", table);
        for (FieldConfig fieldConfig : fields) {  // 只支持一个单个主键
            if (fieldConfig.isPrimaryKey()) {
                data.put("primaryKey", fieldConfig);
                break;
            }
        }
        data.put("fields", fields);
        String entityName = ContextHolderUtils.getRequest().getParameter("entityName");
        if (StringUtils.isBlank(entityName)) {
            entityName = getEntityName(table.getName());
        }
//		entityName = "OrderStatistics";
        data.put("entityName", entityName);
        data.put("entityFieldName", getEntityFieldName(entityName));
        data.put("description", description);
        data.put("module", module);
    }


    private String getEntityFieldName(String entityName) {
        char[] charArray = entityName.toCharArray();
        for (int i = 0; i < charArray.length - 1; i++) {
            if (Character.isLowerCase(charArray[i + 1])) {
                break;
            }
            charArray[i] = Character.toLowerCase(charArray[i]);
        }
        return new String(charArray);
    }


    protected String getEntityName(String tableName) {
        String entityName = tableName;
        if (entityName.indexOf("_") > 0) {
            entityName = entityName.substring(entityName.indexOf("_") + 1);
        }
        entityName = CodegUtils.getPascalName(entityName);
        return entityName;
    }

    protected abstract File getTemplateFile();

    protected abstract File getOutputFile(Map<String, Object> data) throws IOException;


}
