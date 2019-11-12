package org.zyf.cloud.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 描述：关闭资源
 *
 * @author yanfengzhang
 * @date 2019-11-12 14:03
 */
public class CloseUtils {
    private static Logger log = LogManager.getLogger();

    public static void closeFileInputStream(FileInputStream fis) {
        if (null != fis) {
            try {
                fis.close();
            } catch (Exception e) {
                log.error("close FileInputStream error", e);
            }
        }
    }

    public static void closeFileOutputStream(FileOutputStream fos) {
        if (null != fos) {
            try {
                fos.flush();
            } catch (Exception e) {
                log.error("flush FileOutputStream error", e);
            }
            try {
                fos.close();
            } catch (Exception e) {
                log.error("close FileOutputStream error", e);
            }
        }
    }

    public static void closeBufferedOutputStream(BufferedOutputStream bos) {
        if (null != bos) {
            try {
                bos.flush();
            } catch (Exception e) {
                log.error("flush BufferedOutputStream error", e);
            }
            try {
                bos.close();
            } catch (Exception e) {
                log.error("close BufferedOutputStream error", e);
            }
        }
    }

    public static void closeZipInputStream(ZipInputStream zis) {
        if (null != zis) {
            try {
                zis.close();
            } catch (Exception e) {
                log.error("close ZipInputStream error", e);
            }
        }
    }

    public static void closeBufferedReader(BufferedReader br) {
        if (null != br) {
            try {
                br.close();
            } catch (Exception e) {
                log.error("close BufferedReader error", e);
            }
        }
    }

    public static void closeBufferedWriter(BufferedWriter bw) {
        if (null != bw) {
            try {
                bw.flush();
            } catch (Exception e) {
                log.error("flush BufferedWriter error", e);
            }
            try {
                bw.close();
            } catch (Exception e) {
                log.error("close BufferedWriter error", e);
            }
        }
    }

    public static void closeInputStream(InputStream is) {
        if (null != is) {
            try {
                is.close();
            } catch (Exception e) {
                log.error("close InputStream error", e);
            }
        }
    }

    public static void closeOutputStream(OutputStream os) {
        if (null != os) {
            try {
                os.flush();
            } catch (Exception e) {
                log.error("flush OutputStream error", e);
            }
            try {
                os.close();
            } catch (Exception e) {
                log.error("close OutputStream error", e);
            }
        }
    }

    public static void closeInputStreamReader(InputStreamReader isr) {
        if (null != isr) {
            try {
                isr.close();
            } catch (Exception e) {
                log.error("close InputStreamReader error", e);
            }
        }
    }

    public static void closeOutputStreamWriter(OutputStreamWriter osw) {
        if (null != osw) {
            try {
                osw.flush();
            } catch (Exception e) {
                log.error("flush OutputStreamWriter error", e);
            }
            try {
                osw.close();
            } catch (Exception e) {
                log.error("close OutputStreamWriter error", e);
            }
        }
    }

    public static void closeZipOutputStream(ZipOutputStream zos) {
        if (null != zos) {
            try {
                zos.flush();
            } catch (Exception e) {
                log.error("flush ZipOutputStream error", e);
            }
            try {
                zos.close();
            } catch (Exception e) {
                log.error("close OutputStreamWriter error", e);
            }
        }
    }

    public static void flushOutputStreamWriter(OutputStreamWriter osw) {
        if (null != osw) {
            try {
                osw.flush();
            } catch (Exception e) {
                log.error("flush OutputStreamWriter error", e);
            }
        }
    }

    public static void flushOutputStream(OutputStream os) {
        if (null != os) {
            try {
                os.flush();
            } catch (Exception e) {
                log.error("flush OutputStream error", e);
            }
        }
    }

    public static void closePrintStream(PrintStream ps) {
        if (null != ps) {
            try {
                ps.flush();
            } catch (Exception e) {
                log.error("flush PrintStream error", e);
            }
            try {
                ps.close();
            } catch (Exception e) {
                log.error("close PrintStream error", e);
            }
        }
    }

    public static void closeRandomAccessFile(RandomAccessFile ras) {
        if (null != ras) {
            try {
                ras.close();
            } catch (Exception e) {
                log.error("close RandomAccessFile error", e);
            }
        }
    }

    public static void closeFileReader(FileReader fr) {
        if (null != fr) {
            try {
                fr.close();
            } catch (Exception e) {
                log.error("close FileReader error", e);
            }
        }
    }

    public static void closeFileWriter(FileWriter fw) {
        if (null != fw) {
            try {
                fw.flush();
            } catch (Exception e) {
                log.error("flush FileWriter error", e);
            }
            try {
                fw.close();
            } catch (Exception e) {
                log.error("close FileWriter error", e);
            }
        }
    }

    public static void closeByteArrayOutputStream(ByteArrayOutputStream baos) {
        if (null != baos) {
            try {
                baos.flush();
            } catch (Exception e) {
                log.error("flush ByteArrayOutputStream error", e);
            }
            try {
                baos.close();
            } catch (Exception e) {
                log.error("close ByteArrayOutputStream error", e);
            }
        }
    }

    public static void closeInflaterInputStream(InflaterInputStream iis) {
        if (null != iis) {
            try {
                iis.close();
            } catch (Exception e) {
                log.error("close InflaterInputStream error", e);
            }
        }
    }

    public static void closeObjectInputStream(ObjectInputStream ois) {
        if (null != ois) {
            try {
                ois.close();
            } catch (Exception e) {
                log.error("close ObjectInputStream error", e);
            }
        }
    }

    public static void closeObjectOutputStream(ObjectOutputStream oos) {
        if (null != oos) {
            try {
                oos.flush();
            } catch (Exception e) {
                log.error("flush ObjectOutputStream error", e);
            }
            try {
                oos.close();
            } catch (Exception e) {
                log.error("close ObjectOutputStream error", e);
            }
        }
    }

    public static void closePrintWriter(PrintWriter pw) {
        if (null != pw) {
            try {
                pw.flush();
            } catch (Exception e) {
                log.error("flush PrintWriter error", e);
            }
            try {
                pw.close();
            } catch (Exception e) {
                log.error("close PrintWriter error", e);
            }
        }
    }

    public static void closePipedInputStream(PipedInputStream pis) {
        if (null != pis) {
            try {
                pis.close();
            } catch (Exception e) {
                log.error("close PipedInputStream error", e);
            }
        }
    }

    public static void closePipedOutputStream(PipedOutputStream pos) {
        if (null != pos) {
            try {
                pos.flush();
            } catch (Exception e) {
                log.error("flush PipedOutputStream error", e);
            }
            try {
                pos.close();
            } catch (Exception e) {
                log.error("close PipedOutputStream error", e);
            }
        }
    }

    public static void closeStringReader(StringReader sr) {
        if (null != sr) {
            try {
                sr.close();
            } catch (Exception e) {
                log.error("close StringReader error", e);
            }
        }
    }

    public static void closeZipFile(ZipFile zipFile) {
        if (null != zipFile) {
            try {
                zipFile.close();
            } catch (Exception e) {
                log.error("close ZipFile error", e);
            }
        }
    }
}
