package com.x404.beat.manage.sys.utils;

import com.x404.beat.core.entity.IdEntity;
import com.x404.beat.core.util.ContextHolderUtils;
import com.x404.beat.core.util.UploadFile;
import com.x404.beat.manage.sys.tools.ConfigManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


public class DocumentUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(DocumentUtils.class);
    public static final String upload_path = "WEB-INF/documents";
    public static int PUBLIC_MODE = 0;
    public static int PRIVATE_MODE = 1;

    public static String upload(HttpServletRequest request, String dir, int mod) {
        File realDir = getDocumentDir(dir, mod);
        UploadFile uploadFile = new UploadFile(request);
        File file = uploadFile.save2web(realDir.getAbsolutePath(), true);
        return generateDownloadUrl(file);
    }


    public static String generateDownloadUrl(File file) {
        String absolutePath = file.getAbsolutePath();

        return ConfigManager.getInstance().getProperty("image_server_host") +
                absolutePath.substring(file.getParentFile().getParent().length());

    }


    public static File getDocumentDir(String dir, int mode) {
        HttpServletRequest request = ContextHolderUtils.getRequest();
        if (StringUtils.isBlank(dir)) {
            dir = getUserId();
        }
        if (dir == null) {
            throw new RuntimeException("用户未登录，禁止上传");
        }
        String defualtPath = request.getSession().getServletContext().getRealPath("/") + upload_path;
        defualtPath = ConfigManager.getInstance().getProperty("UPLOAD_DIR", defualtPath).trim();
        File person = new File(defualtPath, dir);
        File result = null;
        if (mode == PUBLIC_MODE) {
            result = new File(person, "0");
        } else {
            result = new File(person, "1");
        }
        try {
            FileUtils.forceMkdir(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private static String getUserId() {
        IdEntity currentUser = UserUtils.getCurrentUser();
        if (currentUser == null) {
            currentUser = (IdEntity) ContextHolderUtils.getSession().getAttribute("user"); // cms 用户
        }
        return currentUser != null ? currentUser.getId() : null;
    }


    public static String uploadFile(HttpServletRequest request, int mod) {
        return uploadFile(null, mod);
    }

}
