package com.example.service.smartControl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.hutool.core.util.HexUtil;

public class SocketService extends Service {
    /*socket*/
    private ServerSocket server;

    private SocketBinder sockerBinder = new SocketBinder();

    private static Map clientList = new ConcurrentHashMap<String,Socket>();

    @Override
    public IBinder onBind(Intent intent) {
        /*初始化socket*/
        initSocket();
        return sockerBinder;
    }

    public void sendData(String jsonStr) {
        try{
            Collection<Socket> sockets= clientList.values();
            for (Socket socket : sockets){
                OutputStream outputStream = socket.getOutputStream();
                String hexStr = HexUtil.encodeHexStr(jsonStr);
                byte[] byteArray = HexUtil.decodeHex(hexStr);
                outputStream.write(byteArray);
                outputStream.flush();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public class SocketBinder extends Binder {

        /*返回SocketService 在需要的地方可以通过ServiceConnection获取到SocketService  */
        public SocketService getService() {
            return SocketService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            try {
                server = new ServerSocket(6011);
                Log.i("SocketService",">>>服务启动,等待终端的连接\n");
                while (true) {
                    // 开启监听
                    Socket socket = server.accept();
                    // 获取手机连接的地址及端口号
                    final String address = socket.getRemoteSocketAddress().toString();
                    ServerThread thread = new ServerThread(socket);
                    thread.start();
                    clientList.put(address,socket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private void initSocket() {
        new Thread(runnable).start();
    }

    @Override
    public void onDestroy() {
        if (server != null) {
            try {
                server.close();

            } catch (IOException e) {
            }
            server = null;
        }
        super.onDestroy();
        Log.i("SocketService", "onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent){
        Log.i("SocketService","this is onUnbind");
        return super.onUnbind(intent);
    }
}