package com.example.robotev3;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AccueilConnect extends AppCompatActivity {
    //Composants de la vue de cette actvité
    private Button validationMAC;
    private EditText adresseMAC;

    //Préférences partagées pour l'appareil android
    SharedPreferences sharedpreferences;

    /*
    Lancée à la création de la vue
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_connect);

        TextView saisie = findViewById(R.id.saisie);
        TextView patiente = findViewById(R.id.patiente);
        patiente.setVisibility(View.INVISIBLE);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        sharedpreferences = getSharedPreferences(getString(R.string.PREFERENCESEV3), Context.MODE_PRIVATE);

        validationMAC = (Button) findViewById(R.id.connectButton);
        adresseMAC = (EditText) findViewById(R.id.editMacAddText);
        adresseMAC.setText(sharedpreferences.getString(getString(R.string.CLEEV3),""));

        validationMAC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(!validationAdresseMac(adresseMAC.getText().toString())){
                    //Notifie l'utilisateur qu'il faut entrer une adresse MAC
                    AlertDialog.Builder builder = new AlertDialog.Builder(AccueilConnect.this);

                    builder.setMessage("Veuillez entrer une adresse mac");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //l'Utilisateur clique sur le bouton OK
                        }
                    });

                    //Crée l'alerte
                    AlertDialog alerteMac = builder.create();
                    alerteMac.show();
                } else {
                    SharedPreferences.Editor speditor = sharedpreferences.edit();
                    speditor.putString(getString(R.string.CLEEV3), adresseMAC.getText().toString());
                    speditor.commit();

                    //Passe à la vue de la télécommande
                    Intent intent = new Intent(AccueilConnect.this, ModeActivity.class);
                    intent.putExtra("adressemac",adresseMAC.getText().toString().toUpperCase());
                    startActivity(intent);

                    saisie.setVisibility(View.INVISIBLE);
                    validationMAC.setVisibility(View.INVISIBLE);
                    adresseMAC.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    patiente.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    /*
    Renvoi true si l'adresse MAC est validée
     */
    private boolean validationAdresseMac(String adresse) {
        return adresse.length() == 17;
    }



}
