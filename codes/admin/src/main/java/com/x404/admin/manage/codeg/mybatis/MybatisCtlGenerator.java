package com.x404.admin.manage.codeg.mybatis;

import com.x404.admin.manage.codeg.AbstractCodeGenerator;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class MybatisCtlGenerator extends AbstractCodeGenerator
{
    String templateFileName = "codeg/mybatis/CtlTemplate.ftl";

    @Override
    protected File getTemplateFile() {
        String file = getClass().getClassLoader().getResource(templateFileName).getFile();
        return new File(file);
    }

    @Override
    protected File getOutputFile(Map<String, Object> data) throws IOException {
        String entityName = (String) data.get("entityName");
        String pkg = (String) data.get("package");
        File baseDir = getBaseDir();
        pkg = "src/main/java/" + pkg.replace(".", "/") + "/ctl";
        File dir = new File(baseDir, pkg);
        FileUtils.forceMkdir(dir);
        return new File(dir, entityName + "Ctl.java");
    }


}
