package com.example.service.smartControl;

import android.util.Log;

import com.example.service.smartControl.entity.GatewayAuth;
import com.example.service.smartControl.entity.HeartBeatPack;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;

public class ServerThread extends Thread {
    private Socket m_socket;
    // 该线程是否正在运行
    private boolean m_isRuning = false;

    public ServerThread(Socket socket) {
        this.m_socket = socket;
    }

    @Override
    public void start() {
        if (m_isRuning) {
            System.out.println(">>>线程" + this.getId() + "启动失败,该线程正在执行");
            return;
        } else {
            m_isRuning = true;
            super.start();
        }
    }

    @Override
    public void run()
    {
        //字节输入流
        InputStream inputStream = null;
        //字节输出流
        OutputStream outputStream = null;
        try
        {
            inputStream = m_socket.getInputStream();
            outputStream = m_socket.getOutputStream();
            String info = "";
            //按byte读
            byte[] bytes = new byte[64];
            while (m_isRuning)
            {
                //返回下次调用可以不受阻塞地从此流读取或跳过的估计字节数,如果等于0则表示已经读完
                if (inputStream.available() > 0)
                {
                    int len = inputStream.read(bytes);
                    String tempStr = new String(bytes, 0, len);
                    info += tempStr;
                    //已经读完
                    if (inputStream.available() == 0)
                    {
                       Log.i("Socket",">>>线程" + this.getId() + "收到:" + info);
                        try {
                            List<String> sendStrs=new ArrayList<String>();
                            //组包判断
                            this.chargeJson(info,sendStrs);
                            if(this.validate(info)){
                                sendStrs.add(this.fromJsonStr(info));
                            }
                            for(String responseStr:sendStrs){
                                if(StrUtil.isNotEmpty(responseStr)){
                                    String hexStr = HexUtil.encodeHexStr(responseStr);
                                    byte[] byteArray = HexUtil.decodeHex(hexStr);
                                    outputStream.write(byteArray);
                                    outputStream.flush();
                                    System.out.println(">>>线程" + this.getId() + "回应:" + responseStr);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("不是json数据");
                        }
                        //重置,不然每次收到的数据都会累加起来
                        info = "";
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //关闭资源
        finally
        {
            System.out.println(">>>线程" + this.getId() + "的连接已断开\n");
            try
            {
                if (outputStream != null)
                    outputStream.close();
                if (inputStream != null)
                    inputStream.close();
                if (m_socket != null)
                    m_socket.close();
                m_isRuning = false;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    //解析json字符串
    private String fromJsonStr(String jsonStr){
        Gson gson = new Gson();
        try{
            if(jsonStr.indexOf("code")==-1){
                gson.fromJson(jsonStr, GatewayAuth.class);//认证信息
            }else if(jsonStr.indexOf("\"code\":101")>-1){
                HeartBeatPack heartbeat =gson.fromJson(jsonStr, HeartBeatPack.class);//心跳信息
                if (heartbeat.getCode() == 101 && heartbeat.getGw() != null) {
                    String responseStr = "{\"code\":1001,\"result\":0,\"timestamp\":" + new Date().getTime()
                            + "}";
                    return responseStr;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    //组包判断
    private void chargeJson(String info,List<String> sendStrs){
        if(info.indexOf("}{")>-1){
            String jsonStr=info.substring(0,info.indexOf("}{")+1);
            sendStrs.add(this.fromJsonStr(jsonStr));
            info=info.substring(info.indexOf("}{")+1);
            chargeJson(info, sendStrs);
        }
    }

    public static boolean validate(String jsonStr) {
        try{
            JsonParser.parseString(jsonStr).getAsJsonObject();
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
