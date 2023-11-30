package com.example.test.connection;


import android.content.Context;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client {
    static ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
    final String TAG = Client.class.getSimpleName();
    Context context;
    static Socket socket;
    DataOutputStream dot;
    DataInputStream din;
    public void startClient(Context context) {
        this.context = context;
        pool.scheduleAtFixedRate(new Connection(), 1, 1, TimeUnit.SECONDS);
    }

    public class Connection implements Runnable {
        @Override
        public void run() {
            try {
                socket = new Socket("192.168.254.30", 53001);
                Log.d(TAG, "socket is connect: "+socket.isConnected() + " close:  "+socket.isClosed());
                dot = new DataOutputStream(socket.getOutputStream());
                din = new DataInputStream(socket.getInputStream());

                String sendStr = DataAnalyze.strSendToServer;
//                byte[] bytes = sendStr.getBytes(StandardCharsets.US_ASCII);
                byte[] bytes = new byte[]{2,0,16,-16,2,6,0,0,0,0,0,18,48,0,0,0,0,-1,0,116,67,3};
//                dot.writeInt(bytes.length);
                dot.write(bytes);
                int input = din.read();

                dot.flush();
                din.close();
                dot.close();
                socket.close();
                Log.d(TAG, "input: "+input);
            } catch (IOException e) {
                Log.d(TAG, "error in stream: "+e);
            }
        }
    }
}
