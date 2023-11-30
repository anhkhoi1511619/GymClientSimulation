package com.example.test.connection;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class DataAnalyze {
    public final static String TAG = DataAnalyze.class.getSimpleName();
    public static Handler analyzeHandler = new Handler();
    public static String strSendToServer = "020010F0020600000000000123000000000000FFDB03";
    public static void startDataAnalyze(){
        HandlerThread clientHandler = new HandlerThread("Client Handler");
        clientHandler.start();
        analyzeHandler = new Handler(clientHandler.getLooper()) {
            @Override
            public void handleMessage(Message msg)
            {
                Log.d(TAG, "Receive message from background: "+msg.what);
                Log.d(TAG, "Receive data from background: "+msg.obj.toString());
                JSONObject object = (JSONObject) msg.obj;
                switch (msg.what){
                    case 0:
                        analyze(object);
                        Log.d(TAG, "Send data from background: "+strSendToServer);
                        break;
                    case 1:
                        strSendToServer = strSendToServer.substring(0,12)+object+
                                strSendToServer.substring(12+6);
                        break;
                    case 2:
                        strSendToServer = strSendToServer.substring(0,18)+object+
                                strSendToServer.substring(18+8);
                        break;
                    case 3:
                        strSendToServer = strSendToServer.substring(0,26)+object+
                                strSendToServer.substring(26+6);
                        break;
                    case 4:
                        strSendToServer = strSendToServer.substring(0,32)+object+
                                strSendToServer.substring(32+4);
                        break;
                    case 5:
                        strSendToServer = strSendToServer.substring(0,36)+object+
                                            strSendToServer.substring(36+4);
                        break;
                    default:
                        break;

                }
                Log.d(TAG, "Receive all data from background: "+strSendToServer);
            }

            private void analyze(JSONObject object) {
                try {
                    int cmd = object.getInt("cmd");
                    int DiaNo = object.getInt("DiaNo");
                    int RouteID = object.getInt("RouteID");
                    int StartTime = object.getInt("StartTime");
                    int StopSeq = object.getInt("StopSeq");
                    int SignalStrength = object.getInt("SignalStrength");

                    @SuppressLint("DefaultLocale") String strCmd = String.format("%02d",cmd);
                    String strDiaNo = Integer.toHexString(DiaNo);
                    String strDiaNo6s = ("000000"+strDiaNo).substring(strDiaNo.length());
                    @SuppressLint("DefaultLocale") String strRouteID = String.format("%08d",RouteID);
                    @SuppressLint("DefaultLocale") String strStartTime = String.format("%06d",StartTime);
                    @SuppressLint("DefaultLocale") String strStopSeq = String.format("%04d",StopSeq);
                    String strSignalStrength = Integer.toHexString(SignalStrength);
                    String strSignalStrength4s = ("0000"+strSignalStrength).substring(strSignalStrength.length());

                    String ret = strCmd+"06"+strDiaNo6s+strRouteID+strStartTime+strStopSeq+strSignalStrength4s;

                    strSendToServer = strSendToServer.substring(0,8)+ret+strSendToServer.substring(8+32);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

}
