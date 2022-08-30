package com.mqtt.workactiv;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mqtt.workactiv.databinding.ActivityMainBinding;
import com.mqtt.workactiv.ui.SharedViewModel;
import com.mqtt.workactiv.ui.dbSchema;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static MqttClient client;
    dbSchema mydb;

    // View Model
    private SharedViewModel viewModel;
    private static String Status = "Connecting ...";

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mydb = new dbSchema(MainActivity.this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // View Model
        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

        this.getWindow().setStatusBarColor(getColor(R.color.gray_background));
//        this.getWindow().setNavigationBarColor(getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        hideNavBar();

        // View Model
        viewModel.getSelectedItem().observe(this, item -> {
            Log.e("MainActivity Status", item);
        });
        ///////////             Author: Umer
        ///////////////////////////////////////////////////////////
        Button btn = (Button) findViewById(R.id.createDB);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] encodedPayload = new byte[0];
                try {
                    String payload = "";
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    client.publish("bluetooth/learn/9", message);
                    client.setCallback(new MqttCallback() {

                        @Override
                        // Called when the client lost the connection to the broker
                        public void connectionLost(Throwable cause) {
                            System.out.println("client lost connection " + cause);
                        }

                        @Override
                        public void messageArrived(String topic, MqttMessage message) throws Exception {
                            // Received Bytes Conversions
                            //System.out.println("MQTT Received Data :: " +topic + ": " + Arrays.toString(message.getPayload()));
                            //String s = new String(cc, StandardCharsets.UTF_8);
                            String upTime;
                            upTime = "time";
                            byte[] payload = message.getPayload();
                            String text = new String(payload, "UTF-8");
                            Log.d("msg :: ", text + "------------" + topic);
                            JSONObject testV=new JSONObject(new String(payload));
                            String UUID = topic.split("/")[3];
                            String Elm = topic.split("/")[4];
                            String devName = testV.getString("name");
                            insertToDB(UUID, devName, Elm, testV);


                            //Log.d(("ON Topic -> "+ topic)+ " Received Data IS --> ", text);
                        }

                        @Override
                        // Called when an outgoing publish is complete
                        public void deliveryComplete(IMqttDeliveryToken token) {
                            System.out.println("delivery complete " + token);
                        }
                    });
                    client.subscribe("bluetooth_out/read_db/9/#",0);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            }
        });
        /*
        try {
            Context context = MainActivity.this;
            ///     Read Config File to connect MQTT Broker
            JsonElement jsonElement = JsonParser.parseString(readFromFile(context));
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Log.d("URL ::  ", jsonObject.get("Curl").toString());
            Log.d("uName ::  ", jsonObject.get("uname").toString());
            Log.d("uPass ::  ", jsonObject.get("upass").toString());
            // Toast.makeText(context, "Password Saved :: " + jsonObject.get("upass").toString(),
            //        Toast.LENGTH_LONG).show();
            String mqttBrokerSocket = String.valueOf(jsonObject.get("mqttBrokerSocket"));
            String uName = String.valueOf(jsonObject.get("uname"));
            String uPass = String.valueOf(jsonObject.get("upass"));
            // String QOS = jsonObject.get("QOS").toString();

            Log.d("Test :: ", mqttBrokerSocket);


            ConnectMQTT(mqttBrokerSocket, uName, uPass);
        }catch (Exception e){
            Log.e("Message :: ", "Error With File : " + e.toString());;
        }
        */

        MqttConnectAsyncTaskRunner runner = new MqttConnectAsyncTaskRunner();
        runner.execute();

        ///////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////
    }



    public void hideNavBar(){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            WindowInsetsControllerCompat windowInsetsController =
                    new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());

            windowInsetsController.setSystemBarsBehavior(
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            );
            windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars());
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hideNavBar();
    }

    ///////////             Author: Umer
    ///////////////////////////////////////////////////////////

    public String readFromFile(Context context){

        String ret = "";
        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        Log.d("This is Read ::", ret);
        return ret;
    }

    ////////////    Connect to the MQTT Broker

    public void ConnectMQTT(String brokerSocket, String uName, String uPass) throws MqttException {
        MemoryPersistence persistence = new MemoryPersistence();
        String clientId = MqttClient.generateClientId();
        uName = uName.replace("\"", "");
        uPass = uPass.replace("\"", "");
        brokerSocket = brokerSocket.replace("\"", "");
        client = new MqttClient(brokerSocket, clientId, persistence);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setUserName(uName);
        connOpts.setPassword(uPass.toCharArray());
        connOpts.setCleanSession(true);
        connOpts.setAutomaticReconnect(true);
        Log.d("MSG :: ", "try");
        while (true) {
            //Log.d("MESSAGE is :: ", " :: " + client.isConnected());
            try {
                if (!client.isConnected()) {
                    client.connect(connOpts);
                    Log.d("Message :: ", "Connected to the MQTT Broker :: " + client.isConnected());
                    //update status => connected
                    //Log.d("Text is ::",connectionStatus.getText().toString());
                    //Toast.makeText(MainActivity.this, "Connected to the MQTT Broker ", Toast.LENGTH_LONG).show();
                    Status= "";
                    Status= "Connected";
                }
                else{
                    //settingsfragment.UpdateStatus("Dicsonnected");
                    Status= "";
                    Status= "Connected";
                }
            } catch (MqttException me) {
                Status= "";
                Status= "Disconnected";
                //settingsfragment.UpdateStatus("Dicsonnected");
                System.out.println("Reason ::" + me.getReasonCode());
                System.out.println("Msg :: " + me.getMessage());
                System.out.println("Loc :: " + me.getLocalizedMessage());
                System.out.println("Cause :: " + me.getCause());
                System.out.println("Excep :: " + me);
                me.printStackTrace();

            }
        }
    }

    ////////////    MultiThread async Task

    private class MqttConnectAsyncTaskRunner extends AsyncTask<String, String, String> {
        private String resp;

        @Override
        protected String doInBackground(String... params) {
            try {
                Context context = MainActivity.this;
                ///     Read Config File to connect MQTT Broker
                JsonElement jsonElement = JsonParser.parseString(readFromFile(context));
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                Log.d("URL ::  ", jsonObject.get("Curl").toString());
                Log.d("uName ::  ", jsonObject.get("uname").toString());
                Log.d("uPass ::  ", jsonObject.get("upass").toString());
                // Toast.makeText(context, "Password Saved :: " + jsonObject.get("upass").toString(),
                //        Toast.LENGTH_LONG).show();
                String mqttBrokerSocket = String.valueOf(jsonObject.get("mqttBrokerSocket"));
                String uName = String.valueOf(jsonObject.get("uname"));
                String uPass = String.valueOf(jsonObject.get("upass"));
                // String QOS = jsonObject.get("QOS").toString();

                Log.d("Test :: ", mqttBrokerSocket);


                statusEmitterInALoop();
                ConnectMQTT(mqttBrokerSocket, uName, uPass);
                //viewModel.selectItem("connected");
                //Thread.sleep(5000);
                //Status = "Connected";



            }catch (Exception e){
                //Status = "Disconnected";
                //tst("disconnected");
                //viewModel.selectItem("disconnected");
                Log.e("Message :: ", "Error With File : " + e.toString());
            }

            return null;
        }
        @Override
        protected void onPostExecute(String result) {}

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(String... text) {}
    }

    ///// Insert data to the database
    public void insertToDB(String uuid, String devName, String elm, JSONObject testV){
        Log.d("UUID :: ",uuid);
        Log.d("Device Name :: ",devName);
        Log.d("Element ID :: ",elm);
        Log.d("Object :: ", testV.toString());
        // check UUID EXIST?
        if(mydb.chkUUID(uuid)){
            // Update into Table with latest Details
            if(mydb.updateData(uuid, devName, elm, "tm", testV.toString())){
                Log.d("Exists and Data Inserted :: ", "Success");
            }
        }
        else{
            // Insert into Table new data
            if(mydb.insertData(uuid, devName, elm, "tm", testV.toString())) {
                Log.d("Not Exist and Data Updated :: ", "Success");
            }
        }
    }


    void statusEmitterInALoop() {
        new Thread(() -> {
            // a potentially time consuming task
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.runOnUiThread(() -> {
                    //Log.e("MainActivity Status", Status);
                    if (Objects.equals(viewModel.getSelectedItem().getValue(), "connected")) {
                        viewModel.selectItem(Status);
                    } else {
                        viewModel.selectItem(Status);
                    }
                });
            }
        }).start();
    }

    public void tst(String x){
        this.runOnUiThread(() -> {
            //Log.e("MainActivity Status", Status);
            if (Objects.equals(viewModel.getSelectedItem().getValue(), "connected")) {
                viewModel.selectItem(Status);
            } else {
                viewModel.selectItem(Status);
            }
        });
    }

}