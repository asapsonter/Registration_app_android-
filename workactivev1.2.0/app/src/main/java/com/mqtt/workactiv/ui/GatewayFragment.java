package com.mqtt.workactiv.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.mqtt.workactiv.R;
import com.mqtt.workactiv.databinding.FragmentGatewayBinding;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class GatewayFragment extends Fragment {
    private GatewayFragment binding;
    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GatewayViewModel settingsViewModel =
                new ViewModelProvider(this).get(GatewayViewModel.class);
        com.mqtt.workactiv.databinding.FragmentGatewayBinding binding = FragmentGatewayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        Button button = view.findViewById(R.id.gateway_back_settings);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_gatewayFragment_to_navigation_setting);
            }
        });
        /*binding.gateway_back_settings.onSetClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            navController.navigate(R.id.action_gatewayFragment_to_navigation_setting);
            }*/


        ///////////             Author: Umer
        ///////////////////////////////////////////////////////////
        readFromFile(getActivity());                // Read to file Function
        Button btnQRScan = (Button) view.findViewById(R.id.btnQRScan);
        btnQRScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Message :: ", "Btn working");
                scanCode();
            }
        });

        Button gateway_saveBtn = (Button) view.findViewById(R.id.gateway_saveBtn);
        gateway_saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Msg is :: ", "Password Saved");
                TextInputEditText inputScan_password = (TextInputEditText) getView().findViewById(R.id.inputScan_password);
                String passText = inputScan_password.getText().toString();
                Context context = getContext();
                updateConfigFile(passText, context);
            }
        });

        ///////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////

    }



    ///////////             Author: Umer
    ///////////////////////////////////////////////////////////

    private void scanCode()
    {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Press Volume up button to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLaucher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result->
    {
        if(result.getContents() !=null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            ///     Convert to JSON and Save to the APP dir
            JsonElement jsonElement = JsonParser.parseString(result.getContents());
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Log.d("URL ::  ", jsonObject.get("Curl").toString());
            Log.d("uName ::  ", jsonObject.get("uname").toString());
            Log.d("uPass ::  ", jsonObject.get("upass").toString());
            ///     Save to File
            Context context = getActivity();
            //writeToFile(result.getContents(), context);
            //readFromFile(context);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    writeToFile(result.getContents());          // Write to file Function
                    //readFromFile(getActivity());                // Read to file Function
                    dialogInterface.dismiss();
                }
            }).show();

        }
    });

    ////////////    Write QR Code Data to File Function

    public void writeToFile(String data){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getActivity().openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    ////////////    Read Data from config File Function

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
                //TextInputEditText inputScan_password = (TextInputEditText) getView().findViewById(R.id.inputScan_password);
                //inputScan_password.setText("Please Input password");
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            TextInputEditText inputScan_password = (TextInputEditText) getView().findViewById(R.id.inputScan_password);
            inputScan_password.setText("Please Input password");
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        Log.d("This is Read ::", ret);
        return ret;
    }

    public void updateConfigFile(String passText, Context context){
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

                //TextInputEditText inputScan_password = (TextInputEditText) getView().findViewById(R.id.inputScan_password);
                //inputScan_password.getText();

                if (!passText.isEmpty()) {
                    ///     Update the Config File and save it
                    JsonElement jsonElement = JsonParser.parseString(ret);
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    Log.d("URL ::  ", jsonObject.get("Curl").toString());
                    Log.d("uName ::  ", jsonObject.get("uname").toString());
                    Log.d("uPass ::  ", jsonObject.get("upass").toString());
                    //jsonObject.add("upass", "1234567");
                    jsonObject.addProperty("upass", "1234567");
                    writeToFile(jsonObject.toString());

                    Log.d("AFTER SAVE and update::  ", jsonObject.toString());
                    ///     Save to File

                    Toast.makeText(getActivity(), "Password Saved :: " + passText,
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "Please Input the Password",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            //TextInputEditText inputScan_password = (TextInputEditText) getView().findViewById(R.id.inputScan_password);
            //inputScan_password.setText("Please Input password");
            Toast.makeText(getActivity(), "Please Scan QR Code First ",
                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        Log.d("This is Read ::", ret);

    }

    ///////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }
}
