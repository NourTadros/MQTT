package com.android.youth.mqttandroid;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginMQTT extends AppCompatActivity {
EditText hostport;
EditText username;
Button Next;
Intent intent;
EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mqtt);

        password=(EditText) findViewById((R.id.passtext));
       String pw=password.getText().toString();

        username=(EditText) findViewById(R.id.username);
        String user=username.getText().toString();

        hostport = (EditText) findViewById(R.id.hostport);
        String host = hostport.getText().toString();

        Next=(Button) findViewById(R.id.Next);
        Next.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("Next", hostport.getText().toString());
                intent.putExtra("user",username.getText().toString());
                intent.putExtra("password",password.getText().toString());
                startActivity(intent);
            }
        });}

        public void onButtonClick(View v){
            nextpage(v);

        }

    public void nextpage(View v){

        startActivity(new Intent(this, MainActivity.class));
    }

}
