package com.x404.beat.core.util;

import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * 文件操作工具类
 *
 * @author 张代浩
 */
public class FileUtils {
//	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 获取文件扩展名
     *
     * @param filename
     * @return
     */
    public static String getExtend(String filename) {
        return getExtend(filename, "");
    }

    /**
     * 获取文件扩展名
     *
     * @param filename
     * @return
     */
    public static String getExtend(String filename, String defExt) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf('.');

            if ((i > 0) && (i < (filename.length() - 1))) {
                return (filename.substring(i + 1)).toLowerCase();
            }
        }
        return defExt.toLowerCase();
    }

    /**
     * 获取文件名称[不含后缀名]
     *
     * @param
     * @return String
     */
    public static String getFilePrefix(String fileName) {
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, splitIndex).replaceAll("\\s*", "");
    }

    /**
     * 获取文件名称[不含后缀名]
     * 不去掉文件目录的空格
     *
     * @param
     * @return String
     */
    public static String getFilePrefix2(String fileName) {
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, splitIndex);
    }

    /**
     * 文件复制
     *方法摘要：这里一句话描述方法的用途
     *@param
     *@return void
     */
//	public static void copyFile(String inputFile,String outputFile)  {
//		File sFile = new File(inputFile);
//		File tFile = new File(outputFile);
//		int temp = 0;
//		byte[] buf = new byte[4096];
//		FileOutputStream fos = null;
//		FileInputStream fis = null;
//        try {
//        	if(!tFile.exists()){
//        		tFile.createNewFile();
//        	}
//        	 fis = new FileInputStream(sFile);
//        	 fos = new FileOutputStream(tFile);
//        	while((temp = fis.read(buf))!=-1){
//        		fos.write(buf, 0, temp);
//            }
//        } catch (IOException e) {
//        	logger.error("复制文件失败",e);
//        } finally{
//    		IOUtils.closeQuietly(fos);
//    		IOUtils.closeQuietly(fis);
//        }
//	}

    /**
     * 判断文件是否为图片<br>
     * <br>
     *
     * @param filename 文件名<br>
     *                 判断具体文件类型<br>
     * @return 检查后的结果<br>
     * @throws Exception
     */
    public static boolean isPicture(String filename) {
        // 文件名称为空的场合
        if (StringUtils.isEmpty(filename)) {
            // 返回不和合法
            return false;
        }
        // 获得文件后缀名
        //String tmpName = getExtend(filename);
        String tmpName = filename;
        // 声明图片后缀名数组
        String imgeArray[][] = {{"bmp", "0"}, {"dib", "1"},
                {"gif", "2"}, {"jfif", "3"}, {"jpe", "4"},
                {"jpeg", "5"}, {"jpg", "6"}, {"png", "7"},
                {"tif", "8"}, {"tiff", "9"}, {"ico", "10"}};
        // 遍历名称数组
        for (int i = 0; i < imgeArray.length; i++) {
            // 判断单个类型文件的场合
            if (imgeArray[i][0].equals(tmpName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断文件是否为DWG<br>
     * <br>
     *
     * @param filename 文件名<br>
     *                 判断具体文件类型<br>
     * @return 检查后的结果<br>
     * @throws Exception
     */
    public static boolean isDwg(String filename) {
        // 文件名称为空的场合
        if (StringUtils.isEmpty(filename)) {
            // 返回不和合法
            return false;
        }
        // 获得文件后缀名
        String tmpName = getExtend(filename);
        // 声明图片后缀名数组
        if (tmpName.equals("dwg")) {
            return true;
        }
        return false;
    }

    /**
     * 删除指定的文件
     *
     * @param strFileName 指定绝对路径的文件名
     * @return 如果删除成功true否则false
     */
    public static boolean delete(String strFileName) {
        File fileDelete = new File(strFileName);

        if (!fileDelete.exists() || !fileDelete.isFile()) {
            return false;
        }

        return fileDelete.delete();
    }
}
