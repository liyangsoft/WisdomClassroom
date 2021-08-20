package com.example.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.base.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class GroupFourService extends Service {
    public static Socket socket;
    private static PrintWriter pw;
    private static InputStream inputStream;

    @Override
    public void onCreate() {
        super.onCreate();
        startClient(Constants.groupIP4, Constants.groupPort);
    }

    public static void startClient(final String address, final int port) {
        if (address == null) {
            return;
        }
        initSocket(address, port);
    }

    private static void initSocket(final String address, final int port) {
        if (socket == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i("tcp", "启动客户端");
                        socket = new Socket(address, port);
                        Log.i("tcp", "客户端连接成功");
                        pw = new PrintWriter(socket.getOutputStream());

                        inputStream = socket.getInputStream();

                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while ((len = inputStream.read(buffer)) != -1) {
                            String data = new String(buffer, 0, len);
                            Log.i("tcp", "收到服务器的数据---------------------------------------------:" + data);
//                            EventBus.getDefault().post(new MessageClient(data));
                        }
                        Log.i("tcp", "客户端断开连接");


                    } catch (Exception EE) {
                        EE.printStackTrace();
                        Log.i("tcp", "客户端无法连接服务器");
                        try {
                            Thread.sleep(5000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        releaseSocket(address, port);

                    } finally {

                    }
                }
            }).start();
        }
    }

    private static void releaseSocket(final String address, final int port) {

        try {
            if (null != socket) {
                socket.close();
                socket = null;
            }
            if (null != inputStream) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (null != pw) {
            pw.close();
        }

        initSocket(address, port);
    }

    public static void sendTcpMessage(final String msg) {
        if (socket != null && socket.isConnected()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket.getOutputStream().write(msg.getBytes());
                        socket.getOutputStream().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
