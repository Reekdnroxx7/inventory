package com.x404.beat.core.util;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;


public class UploadFile {
    private MultipartHttpServletRequest request;

    private MultipartFile multipartFile;

    public UploadFile(HttpServletRequest request) {
        super();
        this.request = (MultipartHttpServletRequest) request;
        init();
    }

    private void init() {
        Map<String, MultipartFile> fileMap = request.getFileMap();
        for (Entry<String, MultipartFile> name : fileMap.entrySet()) {
            multipartFile = name.getValue();
            break;
        }
    }

    /**
     * Return the original filename in the client's filesystem.
     * <p>This may contain path information depending on the browser used,
     * but it typically will not with any other than Opera.
     *
     * @return the original filename, or the empty String if no file
     * has been chosen in the multipart form, or <code>null</code>
     * if not defined or not available
     */
    public String getOrgFileName() {
        return multipartFile.getOriginalFilename();
    }


    public File save2web(String path, boolean rename) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String fileName;
        fileName = this.getOrgFileName();
        if (rename) {
            String suffix = FileUtils.getExtend(fileName);
            fileName = UUIDUtils.next() + "." + suffix;
        }
        File destFile = new File(dir, fileName);
        try {
            org.apache.commons.io.FileUtils.writeByteArrayToFile(destFile, multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return destFile;
    }


}
