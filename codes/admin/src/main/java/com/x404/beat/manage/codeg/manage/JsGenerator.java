package com.x404.beat.manage.codeg.manage;

import com.x404.beat.manage.codeg.AbstractCodeGenerator;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsGenerator extends AbstractCodeGenerator {
    String templateFileName = "codeg/manage/jsTemplate.ftl";

    @Override
    protected File getOutputFile(Map<String, Object> data) throws IOException {
        String entityName = (String) data.get("entityName");
        StringBuilder sb = new StringBuilder(entityName);
        sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
        String pkg = (String) data.get("module");
        File baseDir = super.getBaseDir();
        pkg = "src/main/webapp/webpage/" + pkg.replace(".", "/") + "/" + sb.toString();
        File dir = new File(baseDir, pkg);
        FileUtils.forceMkdir(dir);
        return new File(dir, entityName + ".js");
    }

    @Override
    protected File getTemplateFile() {
        String file = getClass().getClassLoader().getResource(templateFileName).getFile();
        return new File(file);
    }


}
