package org.zyf.cloud.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * 描述：运行类
 *
 * @author yanfengzhang
 * @date 2019-11-12 14:28
 */
public abstract class RuntimeUtils {
    private static Logger log = LogManager.getLogger();

    public static boolean executeShell(StringBuffer result, String... command) {
        return executeShell(0, null, result, command);
    }

    public static boolean executeShell(String seperator, StringBuffer result, String... command) {
        return executeShell(0, seperator, result, command);
    }

    public static String executeShellReturnLastStr(String... command) {
        StringBuffer result = new StringBuffer();
        if (!executeShell(0, System.getProperty("line.separator"), result, command)) {
            return "";
        }
        String[] r = result.toString().split(System.getProperty("line.separator"));
        if (r.length == 0) {
            return "";
        }
        return r[(r.length - 1)];
    }

    public static boolean executeShell(String... command) {
        return executeShell(0, null, null, command);
    }

    public static boolean executeShell(int timeout, String separator, StringBuffer result, String... command) {
        if ((command == null) || ((command.length != 1) && (command.length != 3))) {
            log.error("cmd is blank error!");
            return false;
        }
        if (log.isDebugEnabled()) {
            log.debug("cmd:" + ZyfStringUtils.join(command));
        }
        String s = "";
        try {
            s = CmdThread.exeSafe(timeout, separator, command);
        } catch (Exception e) {
            log.error("CmdThread.exeSafe() failed!", e);
            return false;
        }
        if (result != null) {
            result.append(s);
        }
        return true;
    }

    static class CmdThread implements Runnable {
        static final int NOT_END = -12345;
        private String[] cmd;
        StringBuffer outsb = new StringBuffer();
        private Process process;
        int cmdvalue = -12345;
        boolean syserr = false;
        String seperator = null;

        @Override
        public void run() {
            BufferedReader out = null;
            BufferedReader error = null;
            try {
                if (this.cmd.length == 1) {
                    this.process = Runtime.getRuntime().exec(this.cmd[0]);
                } else {
                    this.process = Runtime.getRuntime().exec(this.cmd);
                }
                out = new BufferedReader(new InputStreamReader(this.process.getInputStream()));
                error = new BufferedReader(new InputStreamReader(this.process.getErrorStream()));
                String line = null;
                StringBuilder errorBuffer = new StringBuilder();
                while ((line = error.readLine()) != null) {
                    errorBuffer.append(line);
                }
                if (errorBuffer.length() > 0) {
                    RuntimeUtils.log.error("Runtime exec error:" + errorBuffer.toString());
                    this.syserr = true;
                } else {
                    if (this.seperator == null) {
                        this.seperator = "";
                    }
                    while ((line = out.readLine()) != null) {
                        this.outsb.append(line).append(this.seperator);
                    }
                    this.outsb.delete(this.outsb.length() - this.seperator.length(), this.outsb.length());
                    if (RuntimeUtils.log.isDebugEnabled()) {
                        RuntimeUtils.log.debug("Runtime exec out:" + this.outsb.toString());
                    }
                    this.cmdvalue = this.process.waitFor();
                }
                return;
            } catch (Exception ex) {
                RuntimeUtils.log.error("Runtime exec error!", ex);
                this.syserr = true;
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                    }
                }
                if (error != null) {
                    try {
                        error.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }

        public CmdThread(String seperator, String... cmd) {
            this.seperator = seperator;
            this.cmd = cmd;
        }

        public int getProcState() {
            return this.cmdvalue;
        }

        public boolean isSysError() {
            return this.syserr;
        }

        public String getOut() {
            return this.outsb.toString();
        }

        public void destroy() {
            if (this.process != null) {
                this.process.destroy();
            }
        }

        private static String exeSafe(int timeout, String separate, String... cmd) throws Exception {
            long startTime = System.currentTimeMillis();
            CmdThread cmdT = new CmdThread(separate, cmd);

            boolean ifend = false;
            if (timeout > 0) {
                Thread run = new Thread(cmdT);
                run.start();

                int val = -12345;
                while (System.currentTimeMillis() - startTime < timeout * 1000) {
                    val = cmdT.getProcState();
                    if (val == -12345) {
                        try {
                            Thread.sleep(3000L);
                        } catch (Exception e) {
                            RuntimeUtils.log.error("thread sleep failed", e);
                        }
                    } else {
                        ifend = true;
                    }
                }
                run.stop();
            } else {
                cmdT.run();
                ifend = true;
            }
            if ((ifend) && (!cmdT.isSysError())) {
                return cmdT.getOut();
            }
            cmdT.destroy();
            throw new Exception("exeSafe error or timeout!");
        }
    }

    public static Set<Thread> getAllThreads() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup parent = null;
        while ((parent = group.getParent()) != null) {
            group = parent;
        }
        Set<Thread> resultSet = new HashSet<Thread>();
        Thread[] threads = new Thread[group.activeCount()];
        group.enumerate(threads);
        for (Thread thread : threads) {
            if ((thread != null) && (thread.isAlive())) {
                resultSet.add(thread);
            }
        }
        return resultSet;
    }

    public static String getThreadStackTrace(Thread thread) {
        if (thread == null) {
            return "EMPTY THREAD";
        }
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackTraceElements = thread.getStackTrace();
        sb.append("\n");
        sb.append(thread.getName() + ":" + thread.getThreadGroup().getName() + "(" + thread.getState() + ")");
        for (StackTraceElement e : stackTraceElements) {
            sb.append("\t" + e.getClassName() + "." + e.getMethodName() + "(" + e.getFileName() + ":" + e.getLineNumber() + ")\n");
        }
        return sb.toString();
    }

    public static String dumpAllAliveThreadStackTrace() {
        Set<Thread> set = getAllThreads();
        StringBuilder sb = new StringBuilder();
        sb.append("\n***********************************************************************\n");
        sb.append("*****                    YOUR THREADS DUMP                        ******\n");
        sb.append("***********************************************************************\n");
        for (Thread thread : set) {
            sb.append(getThreadStackTrace(thread));
        }
        return sb.toString();
    }
}

