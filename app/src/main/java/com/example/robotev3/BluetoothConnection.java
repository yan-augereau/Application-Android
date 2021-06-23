package com.example.robotev3;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import android.util.Log;

import static java.util.UUID.fromString;


public class BluetoothConnection {

    //UUID correspondant au bluetooth de l'appareil android
    private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    //Adaptateur bluetooth de l'appareil
    BluetoothAdapter localAdapter;
    //Socket de l'EV3
    public BluetoothSocket socket_ev3_1,socket_nxt2;
    boolean success=false;
    private boolean btPermission=false;
    private boolean alertReplied=false;
    public Socket socket;

    public void reply(){
        this.alertReplied = true;
    }
    public void setBtPermission(boolean btPermission) {
        this.btPermission = btPermission;
    }

    /*
    Initialise la connexion bluetooth de l'appareil
    Renvoi true si la connexion bluetooth est effective
     */
    public boolean initBT(){
        localAdapter=BluetoothAdapter.getDefaultAdapter();
        return localAdapter.isEnabled();
    }

    /*
    Permet la connexion avec la brique EV3
    @param : macAdd, l'adresse mac du boitier
    Renvoi true si la connexion est effective
     */
    public boolean connectToEV3(String macAdd) throws IOException {
        //String macAdd = "00:16:53:80:43:05";
        BluetoothDevice ev3_1 = localAdapter.getRemoteDevice(macAdd);
        try {
            socket_ev3_1 = ev3_1.createRfcommSocketToServiceRecord(fromString(SPP_UUID));
            socket_ev3_1.connect();
            success = true;
        } catch (IOException e) {
            Log.d("Bluetooth","Err: Device not found or cannot connect " + macAdd);
            success=false;
        }
        return success;
    }

    /*
    Permet d'écrire un message et de l'envoyer en bluetooth
     */
    public void writeMessage(int msg) throws InterruptedException, IOException {
        Writer outputMessage = new OutputStreamWriter(socket_ev3_1.getOutputStream());
        outputMessage.write(msg);
        outputMessage.flush();
        Thread.sleep(1000);
    }

    public int readMessage() throws InterruptedException, IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(socket_ev3_1.getInputStream()));
        return in.read();
    }
}
