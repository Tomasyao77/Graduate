package com.whut.tomasyao.age_estimation.util;
/**
 * Created by zouy on 20-1-8.
 */

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Java执行shell脚本工具类
 */
public class ShellExcutor {
    //    private static Logger log = Logger.getLogger(ShellExcutor.class);
    private static String base = "/media/zouy/workspace/gitcloneroot/age-estimation-pytorch";

    /**
     * Java执行shell脚本入口
     * @param shellName 脚本文件名
     * @throws Exception
     */
    public String service(String shellName) throws Exception{
        try {
            //拼接完整的脚本目录
            String shellPath = base + "/start.sh";
            //执行脚本
            return callScript(shellPath);
        } catch (Exception e) {
            System.out.println("ShellExcutor异常" + e.getMessage());
            throw e;
        }
    }

    /**
     * 脚本文件具体执行及脚本执行过程探测
     * @param script 脚本文件绝对路径
     * @throws Exception
     */
    private String callScript(String script) throws Exception{
        try {
            String cmd = " " + script + " graduate_age_estimation";
            System.out.println("cmd: " + cmd);

            //启动独立线程等待process执行完成
            CommandWaitForThread commandThread = new CommandWaitForThread(cmd);
            commandThread.start();

            while (!commandThread.isFinish()) {
                System.out.println("shell: " + script + " 还未执行完毕,100ms后重新探测");
                Thread.sleep(100);
            }

            //检查脚本执行结果状态码
            if(commandThread.getExitValue() != 0){
                throw new Exception("shell " + script + "执行失败,exitValue = " + commandThread.getExitValue());
            }
            return "shell " + script + "执行成功,exitValue = " + commandThread.getExitValue();
        }
        catch (Exception e){
            throw new Exception("执行脚本发生异常,脚本路径" + script, e);
        }
    }

    /**
     * 脚本函数执行线程
     */
    public class CommandWaitForThread extends Thread {

        private String cmd;
        private boolean finish = false;
        private int exitValue = -1;

        public CommandWaitForThread(String cmd) {
            this.cmd = cmd;
        }

        public void run(){
            try {
                //执行脚本并等待脚本执行完成
                Process process = Runtime.getRuntime().exec(cmd);

                //写出脚本执行中的过程信息
                BufferedReader infoInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line = "";
                while ((line = infoInput.readLine()) != null) {
                    System.out.println(line);
                }
                while ((line = errorInput.readLine()) != null) {
                    System.out.println(line);
                }
                infoInput.close();
                errorInput.close();

                //阻塞执行线程直至脚本执行完成后返回
                this.exitValue = process.waitFor();
            } catch (Throwable e) {
                System.out.println("CommandWaitForThread accure exception,shell " + cmd);
                exitValue = 110;
            } finally {
                finish = true;
            }
        }

        public boolean isFinish() {
            return finish;
        }

        public void setFinish(boolean finish) {
            this.finish = finish;
        }

        public int getExitValue() {
            return exitValue;
        }
    }
}