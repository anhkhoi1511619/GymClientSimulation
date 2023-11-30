package com.example.test.vpt;

import android.util.Log;

import com.example.test.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VPTCommServer {
    private ServerSocket serverSocket;
    private Socket socket;
    InputStream is;
    OutputStream os;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    public void startVPTCommServer(){
        executorService.submit(runnable);
    }
    final Runnable runnable = () -> {
        byte[] rxByteData = new byte[1300];
        try {
            serverSocket = new ServerSocket(8080);
            while (true) {
                socket = serverSocket.accept();
                is = socket.getInputStream();
                os = socket.getOutputStream();
                byte[] tRxByteData = new byte[13000];
                Arrays.fill(tRxByteData,(byte) 0);
                int rxDataSize = is.read(tRxByteData);
                System.arraycopy(tRxByteData, 0, rxByteData, 0, rxDataSize);
                rxByteData = Arrays.copyOf(rxByteData, rxDataSize);

                Log.d("VALUE", "value by array byte: "+Arrays.toString(rxByteData));
                Log.d("VALUE", "value by String: "+ Utils.format(rxByteData));

                os.write(new byte[]{1,2,3});
                os.flush();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
}
