package org.zyf.cloud.common.utils;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zyf.cloud.common.exception.FileException;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 描述：文件操作类
 *
 * @author yanfengzhang
 * @date 2019-11-12 14:26
 */
public class FileUtils {
    public Logger log = LogManager.getLogger(this.getClass());

    public void checkDirecctory(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
            String cmd = "chmod 755 -R " + path;
            log.debug("FileUtils:checkDirecctory() excute cmd:" + cmd);
            if (!RuntimeUtils.executeShell(cmd)) {
                log.error("FileUtils:checkDirecctory()" + cmd + " failed.");
            }
        }
    }

    public void append(File file, String text) {
        write(file, text, true);
    }

    public String getFileExtention(File file) {
        try {
            return getFileExtention(file.getCanonicalPath());
        } catch (IOException e) {
        }
        return "";
    }

    public String getFileExtention(String fileName) {
        int index = ZyfStringUtils.indexOf(fileName, '.', ZyfStringUtils.lastIndexOfAny(fileName, new String[]{"\\", "/"}));

        return index > 0 ? ZyfStringUtils.substring(fileName, index + 1) : "";
    }

    public String getFileName(File file) {
        if (file == null) {
            return "";
        }
        try {
            return getFileName(file.getCanonicalPath());
        } catch (IOException e) {
        }
        return "";
    }

    public String getFileName(String fileName) {
        if (ZyfStringUtils.isBlank(fileName)) {
            return "";
        }
        int index = ZyfStringUtils.lastIndexOfAny(fileName, new String[]{"\\", "/"});
        return ZyfStringUtils.substring(fileName, index + 1);
    }

    public String readText(File file, String charset) {
        Validate.notNull(file);
        Validate.isTrue(file.canRead());
        FileInputStream fis = null;
        String ret = null;
        try {
            fis = new FileInputStream(file);
            long contentLength = file.length();
            byte[] ba = new byte[(int) contentLength];
            fis.read(ba);
            ret = new String(ba, charset);
        } catch (FileNotFoundException e) {
            throw FileException.fileIsNotExistException(file.getAbsolutePath());
        } catch (Exception e) {
            throw FileException.readFileFailException(file.getAbsolutePath());
        } finally {
            CloseUtils.closeFileInputStream(fis);
        }
        return ret;
    }

    public String readText(File file) {
        return readText(file, "utf-8");
    }

    public void write(File file, String text) {
        write(file, text, false);
    }

    public void write(File file, String text, boolean append) {
        Validate.notNull(file);
        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(file, append);
            fo.write(text.getBytes());
            fo.flush();
        } catch (FileNotFoundException e) {
            throw FileException.fileIsNotExistException(file.getAbsolutePath());
        } catch (Exception ex) {
            throw FileException.writeFileFailException(file.getAbsolutePath());
        } finally {
            CloseUtils.closeFileOutputStream(fo);
        }
    }

    public boolean deleteFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        String cmd = "rm -rf " + path;
        boolean r = RuntimeUtils.executeShell(cmd);
        if (r) {
            log.info("FileUtils Delete " + path + " Succeed.");
            return true;
        }
        log.info("FileUtils Command execute error!" + path);
        return false;
    }

    public boolean untarFileBySh(String basedir, String localFilePath) {
        String[] cmd = {"/bin/sh", "-c", "mkdir " + basedir + "; cd " + basedir + ";tar zxmf " + localFilePath};
        log.info("FileUtils untarFileBySh cmd=" + cmd[2]);
        boolean flag = RuntimeUtils.executeShell(cmd);
        if (!flag) {
            log.error("do tar zxmf all zip file sh failed!!" + localFilePath);
        }
        return flag;
    }


    private void deleteDirectory(String filename) throws Exception {
        File delfile = new File(filename);
        if (delfile.exists()) {
            if (delfile.isDirectory()) {
                File[] childfile = delfile.listFiles();
                if ((null != childfile) && (childfile.length > 0)) {
                    for (int i = 0; i < childfile.length; i++) {
                        deleteDirectory(childfile[i].getAbsolutePath());
                    }
                    delfile.delete();
                } else {
                    delfile.delete();
                }
            } else {
                delfile.delete();
            }
        }
    }

    public boolean unzipFile(String dir, String fileurl) {
        int buffer = 1024;
        FileInputStream fis = null;
        ZipInputStream zis = null;
        FileOutputStream fos = null;
        BufferedOutputStream dest = null;

        File rootdir = new File(dir);
        try {
            if (!rootdir.exists()) {
                log.info("unzipFile() create dir :" + rootdir);
                rootdir.mkdirs();
            }
            fis = new FileInputStream(fileurl);
            zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                log.debug("unzipFile() extracting: " + entry);

                byte[] data = new byte[buffer];
                if ((entry.isDirectory()) || (entry.getName().endsWith("\\"))) {
                    File subdir = null;
                    if (entry.getName().endsWith("\\")) {
                        log.debug("isdirectory! and endswith \\!!!");
                        String entrynametemp = entry.getName().replaceAll("\\\\", "/");
                        subdir = new File(dir + entrynametemp);
                    } else {
                        subdir = new File(dir + entry.getName());
                    }
                    if (!subdir.exists()) {
                        subdir.mkdirs();
                        log.debug("unzipFile() create sub_dir:" + dir + entry.getName());
                    }
                } else {
                    fos = new FileOutputStream(dir + entry.getName());

                    log.debug("unzipFile() creating files : " + dir + entry.getName());

                    dest = new BufferedOutputStream(fos, buffer);
                    while (zis.read(data, 0, buffer) != -1) {
                        dest.write(data, 0, zis.read(data, 0, buffer));
                    }
                    CloseUtils.closeBufferedOutputStream(dest);
                    CloseUtils.closeFileOutputStream(fos);
                }
            }
            return true;
        } catch (Exception ex) {
            int count;
            log.error("UnzipFile " + fileurl + " Error!", ex);
            return false;
        } finally {
            CloseUtils.closeBufferedOutputStream(dest);
            CloseUtils.closeFileOutputStream(fos);
            CloseUtils.closeZipInputStream(zis);
            CloseUtils.closeFileInputStream(fis);
        }
    }

    public void deleteFiles(String dir, FilenameFilter filter) {
        File files = new File(dir);
        deleteFiles(files, filter);
    }

    public void deleteFiles(File files, FilenameFilter filter) {
        try {
            if (null == files) {
                log.debug("deleteFiles() files is null!");
                return;
            }
            if (!files.isDirectory()) {
                log.debug("deleteFiles() " + files.getName() + " is not a directory!");
                return;
            }
            File[] filelist = files.listFiles(filter);
            if ((filelist == null) || (filelist.length == 0)) {
                log.debug("deleteFiles() " + files.getName() + " has no files!");
                return;
            }
            boolean flag = false;
            for (int i = 0; i < filelist.length; i++) {
                File file = filelist[i];
                if (file.isFile()) {
                    flag = file.delete();
                    if (flag) {
                        log.info("delete " + file + " success.");
                    } else {
                        log.error("delete " + file + " failed.");
                    }
                } else if (file.isDirectory()) {
                    String filePath = file.getAbsolutePath();
                    String cmd = "rm -rf " + filePath;
                    if (log.isInfoEnabled()) {
                        log.info(" delete old unzip dir:" + cmd);
                    }
                    boolean result = RuntimeUtils.executeShell(cmd);
                    if (!result) {
                        log.error("excute cmd :" + cmd + " failed.");
                    }
                }
            }
        } catch (Exception e) {
            log.error("deleteFiles failed!", e);
        }
    }

    public String getDirectory(String absolutePath) {
        if (ZyfStringUtils.isBlank(absolutePath)) {
            return "";
        }
        int index = ZyfStringUtils.lastIndexOfAny(absolutePath, new String[]{"\\", "/"});
        return ZyfStringUtils.substring(absolutePath, 0, index);
    }

    public void write2Disk(String filePath, Object obj) {
        if (ZyfStringUtils.isBlank(filePath)) {
            log.debug("write2Disk file[" + filePath + "] is not found");
            return;
        }
        File file = new File(filePath);
        if ((file.exists()) && (file.isFile())) {
            file.delete();
        }
        ObjectOutputStream out = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            out = new ObjectOutputStream(fos);
            if (null != obj) {
                out.writeObject(obj);
            }
        } catch (Exception ex) {
            log.error("write2Disk -- Writing java Object failed.", ex);
        } finally {
            CloseUtils.closeObjectOutputStream(out);
            CloseUtils.closeFileOutputStream(fos);
        }
    }

    public Object loadFromDisk(String filePath) {
        if (ZyfStringUtils.isBlank(filePath)) {
            log.debug("loadFromDisk file[" + filePath + "] is not found");
            return null;
        }
        File file = new File(filePath);
        if (!file.isFile()) {
            log.debug("loadFromDisk file[" + filePath + "] is not found");
            return null;
        }
        ObjectInputStream in = null;
        Object obj = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);
            if (null != in) {
                obj = in.readObject();
            }
        } catch (Exception ex) {
            log.error("loadFromDisk read file[" + filePath + "] failure", ex);
        } finally {
            CloseUtils.closeObjectInputStream(in);
            CloseUtils.closeFileInputStream(fis);
        }
        return obj;
    }

    public String[] getFileList(String dirpath) {
        File file = new File(dirpath);
        if (!file.isDirectory()) {
            log.debug("loadFromDisk dir[" + dirpath + "] is not found");
            return null;
        }
        File[] tempList = file.listFiles();
        int len = tempList.length;
        String[] fileStrs = new String[len];
        for (int i = 0; i < len; i++) {
            fileStrs[i] = tempList[i].getName();
        }
        return fileStrs;
    }

    public boolean cpFile(String remotePath, String fileName, String localPath) {
        if (!remotePath.endsWith("/")) {
            remotePath = remotePath + "/";
        }
        if (!localPath.endsWith("/")) {
            localPath = localPath + "/";
        }
        try {
            String[] cmd = {"/bin/sh", "-c", "cp -af " + remotePath + fileName + " " + localPath + "; chmod 755 " + localPath + fileName
                    + "; chown zyf:root " + localPath + fileName};

            boolean ret = RuntimeUtils.executeShell(cmd);
            if (log.isInfoEnabled()) {
                log.info("FileUtils.cpFile cmd=" + cmd[2] + ", ret=" + ret);
            }
            return ret;
        } catch (Exception e) {
            log.error("FileUtils.cpFile failed.", e);
        }
        return false;
    }

    public boolean cpFile(String remotePath, String localPath) {
        if (!remotePath.endsWith("/")) {
            remotePath = remotePath + "/";
        }
        if (!localPath.endsWith("/")) {
            localPath = localPath + "/";
        }
        try {
            String[] cmd = {"/bin/sh", "-c", "mkdir -p " + localPath + "; find " + remotePath + " -print0 | xargs -0 touch" + "; cp -af "
                    + remotePath + "* " + localPath + "; chmod -R 755 " + localPath + "; chown -R zyf:root " + localPath};

            boolean ret = RuntimeUtils.executeShell(cmd);
            if (log.isInfoEnabled()) {
                log.info("FileUtils.cpFile cmd=" + cmd[2] + ", ret=" + ret);
            }
            return ret;
        } catch (Exception e) {
            log.error("FileUtils.cpFile failed.", e);
        }
        return false;
    }
}
