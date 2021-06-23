package com.example.robotev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.io.IOException;

public class ModeActivity extends AppCompatActivity {
    public BluetoothConnection bluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_main);

        bluetooth = new BluetoothConnection();

        // To notify user for permission to enable bt, if needed
        AlertDialog.Builder builder = new AlertDialog.Builder(ModeActivity.this);

        builder.setMessage("Voulez-vous activer le Bluetooth ?");
        builder.setPositiveButton("Accepter", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                bluetooth.setBtPermission(true);
                bluetooth.reply();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                bluetooth.setBtPermission(false);
                bluetooth.reply();
            }
        });

        // Create the AlertDialog
        AlertDialog btPermissionAlert = builder.create();

        Context context = getApplicationContext();
        //CharSequence text1 = getString(R.string.bt_enabled);
        CharSequence text1 = getString(R.string.bt_disabled);
        CharSequence text2 = getString(R.string.bt_failed);


        Toast btDisabledToast = Toast.makeText(context, text1, Toast.LENGTH_LONG);
        Toast btFailedToast = Toast.makeText(context, text2, Toast.LENGTH_LONG);

        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.PREFERENCESEV3), Context.MODE_PRIVATE);

        if(!bluetooth.initBT()){
            // User did not enable Bluetooth
            btDisabledToast.show();
            Intent intent = new Intent(ModeActivity.this, AccueilConnect.class);
            startActivity(intent);
        }

        Toast.makeText(context,getString(R.string.CLEEV3), Toast.LENGTH_LONG).show();

//        String adresseMac = getIntent().getExtras().getString("adressemac");


        try {
            if(!bluetooth.connectToEV3("00:16:53:80:43:05")){
                //Cannot connect to given mac address, return to connect activity
                btFailedToast.show();
                Intent intent = new Intent(ModeActivity.this, AccueilConnect.class);
                startActivity(intent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Création des widgets - Mode par défaut --> Dépiler des objets
        TextView pile = findViewById(R.id.pile);
        TextView objetA = findViewById(R.id.objetA);
        TextView objetB = findViewById(R.id.objetB);
        TextView depileMode = findViewById(R.id.depileMode);
        TextView echangeMode = findViewById(R.id.echangeMode);
        echangeMode.setVisibility(View.INVISIBLE);
        EditText posPile = findViewById(R.id.posPile);
        EditText posObjetA = findViewById(R.id.posObjetA);
        EditText posObjetB = findViewById(R.id.posObjetB);
        ImageView disconnect = findViewById(R.id.disconnect);
        ImageView lrb = findViewById(R.id.ledRBras);
        ImageView lrr = findViewById(R.id.ledRRotation);
        ImageView lrp = findViewById(R.id.ledRPince);
        ImageView lvb = findViewById(R.id.ledVBras);
        lvb.setVisibility(View.INVISIBLE);
        ImageView lvr = findViewById(R.id.ledVRotation);
        lvr.setVisibility(View.INVISIBLE);
        ImageView lvp = findViewById(R.id.ledVPince);
        lvp.setVisibility(View.INVISIBLE);

        final int[] test = new int[1];


        Button retour = findViewById(R.id.retourE);
        retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ModeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        Button echange = findViewById(R.id.echange);
        echange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    bluetooth.writeMessage(1);
                    Thread.sleep(300);

                    String objetA = posObjetA.getText().toString();
                    int a = Integer.parseInt(objetA);
                    System.out.println("A = "+a);


                    for(int i = 1;i<=5; i++){
                        if (a > 127){
                            bluetooth.writeMessage(127);
                            Thread.sleep(300);
                            a = a - 127;
                        } else {
                            bluetooth.writeMessage(a);
                            Thread.sleep(300);
                            a = 0;
                        }
                    }

                    String objetB = posObjetB.getText().toString();
                    int b = Integer.parseInt(objetB);
                    System.out.println("B = "+b);

                    for(int i = 1;i<=5; i++){
                        if (b > 127){
                            bluetooth.writeMessage(127);
                            Thread.sleep(300);
                            b = b - 127;
                        } else {
                            bluetooth.writeMessage(b);
                            Thread.sleep(300);
                            b = 0;
                        }
                    }


                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }

            }
        });

        Button depile = findViewById(R.id.depile);
        depile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {

                    bluetooth.writeMessage(2);
                    Thread.sleep(300);

                    String pile = posPile.getText().toString();
                    int p = Integer.parseInt(pile);
                    System.out.println("Pile = "+p);


                    for(int i = 1;i<=5; i++){
                        if (p > 127){
                            bluetooth.writeMessage(127);
                            Thread.sleep(300);
                            p = p - 127;
                        } else {
                            bluetooth.writeMessage(p);
                            Thread.sleep(300);
                            p = 0;
                        }
                    }

                    String objetA = posObjetA.getText().toString();
                    int a = Integer.parseInt(objetA);
                    System.out.println("A = "+a);


                    for(int i = 1;i<=5; i++){
                        if (a > 127){
                            bluetooth.writeMessage(127);
                            Thread.sleep(300);
                            a = a - 127;
                        } else {
                            bluetooth.writeMessage(a);
                            Thread.sleep(300);
                            a = 0;
                        }
                    }

                    String objetB = posObjetB.getText().toString();
                    int b = Integer.parseInt(objetB);
                    System.out.println("B = "+b);

                    for(int i = 1;i<=5; i++){
                        if (b > 127){
                            bluetooth.writeMessage(127);
                            Thread.sleep(300);
                            b = b - 127;
                        } else {
                            bluetooth.writeMessage(b);
                            Thread.sleep(300);
                            b = 0;
                        }
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        disconnect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ModeActivity.this,MainActivity.class);
                startActivity(intent);
                try {
                    bluetooth.writeMessage(3);
                    Thread.sleep(100);
                    bluetooth.socket_ev3_1.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Switch mode = findViewById(R.id.mode);
        mode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mode.isChecked()) {

                    echange.setVisibility(View.INVISIBLE);
                    echangeMode.setVisibility(View.INVISIBLE);
                    depileMode.setVisibility(View.VISIBLE);
                    depile.setVisibility(View.VISIBLE);
                    pile.setVisibility(View.VISIBLE);
                    posPile.setVisibility(View.VISIBLE);
                } else {

                    echange.setVisibility(View.VISIBLE);
                    echangeMode.setVisibility(View.VISIBLE);
                    depileMode.setVisibility(View.INVISIBLE);
                    depile.setVisibility(View.INVISIBLE);
                    pile.setVisibility(View.INVISIBLE);
                    posPile.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }


}
