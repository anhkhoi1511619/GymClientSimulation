package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.test.connection.Client;
import com.example.test.connection.DataAnalyze;
import com.example.test.vpt.VPTCommServer;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    static TextView str;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new VPTCommServer().startVPTCommServer();
//        EditText edtCmd = findViewById(R.id.edit_cmd);
//        EditText edtDiaNo = findViewById(R.id.edit_DiaNo);
//        EditText edtRouteID = findViewById(R.id.edit_RouteID);
//        EditText edtStartTime = findViewById(R.id.edit_StartTime);
//        EditText edtStopSeq = findViewById(R.id.edit_StopSeq);
//        EditText edtSignalStrength = findViewById(R.id.edit_SignalStrength);
//        DataAnalyze.startDataAnalyze();
//
//        findViewById(R.id.btnStartClient).setOnClickListener(view -> {
//            new Client().startClient(this);
//        });
//        findViewById(R.id.btnSend).setOnClickListener(view -> {
//            int cmd = Integer.parseInt(edtCmd.getText().toString().trim());
//            int DiaNo = Integer.parseInt(edtDiaNo.getText().toString().trim());
//            int RouteID = Integer.parseInt(edtRouteID.getText().toString().trim());
//            int StartTime = Integer.parseInt(edtStartTime.getText().toString().trim());
//            int StopSeq = Integer.parseInt(edtStopSeq.getText().toString().trim());
//            int SignalStrength = Integer.parseInt(edtSignalStrength.getText().toString().trim());
//
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("cmd", cmd);
//                jsonObject.put("DiaNo", DiaNo);
//                jsonObject.put("RouteID", RouteID);
//                jsonObject.put("StartTime", StartTime);
//                jsonObject.put("StopSeq", StopSeq);
//                jsonObject.put("SignalStrength", SignalStrength);
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//            sendMessage(0, jsonObject);
//        });
    }

    private void sendMessage(int cmd, JSONObject value) {
        Message message = Message.obtain();
        message.what = cmd;
        message.obj = (JSONObject) value;
        Log.d(DataAnalyze.TAG, "Receive message from background: "+message.obj.toString());
        DataAnalyze.analyzeHandler.sendMessage(message);
    }
}