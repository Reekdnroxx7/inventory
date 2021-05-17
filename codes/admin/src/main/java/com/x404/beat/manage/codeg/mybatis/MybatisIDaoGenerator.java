package com.x404.beat.manage.codeg.mybatis;

import com.x404.beat.manage.codeg.AbstractCodeGenerator;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class MybatisIDaoGenerator extends AbstractCodeGenerator {
    String templateFileName = "codeg/mybatis/IDaoTemplate.ftl";

    @Override
    protected File getOutputFile(Map<String, Object> data) throws IOException {
        String entityName = (String) data.get("entityName");
        String pkg = (String) data.get("package");
        File baseDir = super.getBaseDir();
        pkg = "src/main/java/" + pkg.replace(".", "/") + "/dao";
        File dir = new File(baseDir, pkg);
        FileUtils.forceMkdir(dir);
        return new File(dir, "I" + entityName + "Dao.java");
    }

    @Override
    protected File getTemplateFile() {
        String file = getClass().getClassLoader().getResource(templateFileName).getFile();
        return new File(file);
    }
}
