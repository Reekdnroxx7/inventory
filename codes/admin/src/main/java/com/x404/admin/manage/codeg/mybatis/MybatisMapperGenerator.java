package com.x404.admin.manage.codeg.mybatis;

import com.x404.admin.manage.codeg.AbstractCodeGenerator;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class MybatisMapperGenerator extends AbstractCodeGenerator
{
    String templateFileName = "codeg/mybatis/mapperTemplate.ftl";

    @Override
    protected File getTemplateFile() {
        String file = getClass().getClassLoader().getResource(templateFileName).getFile();
        return new File(file);
    }

    @Override
    protected File getOutputFile(Map<String, Object> data) throws IOException {
        String entityName = (String) data.get("entityName");
        String pkg = (String) data.get("package");
        File baseDir = super.getBaseDir();
        pkg = "src/main/resources/mybatis";
        File dir = new File(baseDir, pkg);
        FileUtils.forceMkdir(dir);
        return new File(dir, entityName + "Dao.xml");
    }


}
