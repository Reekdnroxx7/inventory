package com.x404.beat.manage.codeg.ctl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.x404.beat.core.controller.BaseController;
import com.x404.beat.core.json.AjaxJson;
import com.x404.beat.manage.sys.utils.DocumentUtils;
import com.x404.beat.manage.codeg.AbstractCodeGenerator;
import com.x404.beat.manage.codeg.CodeGenerator;
import com.x404.beat.manage.codeg.model.FieldConfig;
import com.x404.beat.manage.codeg.model.TableConfig;
import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public abstract class BaseCodegCtl extends BaseController {


//	public PageList<TableConfig> listCodegTable(HttpServletRequest request) {
//		List<TableConfig> allTable = CodegUtils.getAllTable();
//		PageList<TableConfig> pageList = new PageList<TableConfig>(allTable);
//		return pageList;
//	}
//
//	public PageList<FieldConfig> listCodegField(HttpServletRequest request,
//			String tableName) {
//		List<FieldConfig> codegFields = CodegUtils.getAllFields(tableName);
//
//		CodegUtils.decoreteFields(tableName,codegFields);
//		this.fieldDecker.deckFields(codegFields);
//		return new PageList<FieldConfig>(codegFields);
//	}


    public AjaxJson generate(HttpServletRequest request, HttpServletResponse response) {
        String[] fieldList = request.getParameterValues("fieldList");
        String module = request.getParameter("module");
        String description = request.getParameter("description");
        List<FieldConfig> fields = new ArrayList<FieldConfig>();
        String tableName = request.getParameter("tableName");
        String pkg = request.getParameter("pkg");
        ObjectMapper objectMapper = new ObjectMapper();
        for (String str : fieldList) {
            try {
                fields.add(objectMapper.readValue(str, FieldConfig.class));
            } catch (Exception e) {
                logger.error(e, e);
                return new AjaxJson(false, e.getMessage());
            }
        }
        List<CodeGenerator> gs = getGenerators();
        List<File> files = new ArrayList<File>();
        TableConfig table = new TableConfig(tableName, "", null);
        for (CodeGenerator cg : gs) {
            try {
                files.add(cg.generate(null, table, fields, pkg, module, description));
            } catch (Exception e) {
                logger.error(e, e);
                return new AjaxJson(false, e.getMessage());
            }
        }
        try {
            File zipFile = zipFile(tableName, files);
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(zipFile.getName().getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(zipFile.length()));
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(new FileInputStream(zipFile));
                bos = new BufferedOutputStream(response.getOutputStream());
                byte[] buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }
            return new AjaxJson(true, "操作成功");
        } catch (IOException e) {
            logger.error(e, e);
            return new AjaxJson(false, e.getMessage());
        }
    }

    private File zipFile(String tableName, List<File> files) throws IOException {
        File baseDir = AbstractCodeGenerator.getBaseDir();
        File documentDir = DocumentUtils.getDocumentDir("", DocumentUtils.PUBLIC_MODE);
        File zipFile = new File(documentDir, tableName + ".zip");
        org.apache.tools.zip.ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(zipFile);
            for (File file : files) {
                BufferedInputStream bis = null;
                try {
                    bis = new BufferedInputStream(
                            new FileInputStream(file));
                    ZipEntry entry = new ZipEntry(file.getAbsolutePath().substring(baseDir.getAbsolutePath().length() + 1));
                    out.putNextEntry(entry);
                    int count;
                    byte data[] = new byte[1024];
                    while ((count = bis.read(data, 0, 1024)) != -1) {
                        out.write(data, 0, count);
                    }
                    bis.close();
                } finally {
                    IOUtils.closeQuietly(bis);
                }
            }
        } finally {
            IOUtils.closeQuietly(out);
        }
        return zipFile;
    }

    protected abstract List<CodeGenerator> getGenerators();
}
