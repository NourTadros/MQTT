package com.android.youth.mqttandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    MqttAndroidClient client;
    TextView subText;
    TextView mymsg;
    MqttConnectOptions options;
    //private TextView switchStatus;
    private Switch mySwitch;
    private Switch mySwitch2;
    Intent intent;
    TextView host;
    TextView User;
    TextView Pass;
    EditText topicview;
    Button subscribe;


    //static String MqttHost=	"tcp://m12.cloudmqtt.com:14886";
    //static String username="rnghsoam";
    //static String password="TbwH-S_X4FLy";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        host = (TextView)findViewById(R.id.host);
        host.setText(getIntent().getStringExtra("Next"));
        String MqttHost=host.getText().toString();

        User=(TextView) findViewById(R.id.username);
        User.setText(getIntent().getStringExtra("user"));
        String username=User.getText().toString();

        Pass=(TextView) findViewById(R.id.password);
        Pass.setText(getIntent().getStringExtra("password"));
        String password=Pass.getText().toString();

        topicview =(EditText) findViewById((R.id.topic));


        mymsg=(TextView) findViewById(R.id.mymsg) ;
        subText= (TextView)findViewById(R.id.subText);
        mySwitch  = (Switch) findViewById(R.id.Hi);
        mySwitch.setOnCheckedChangeListener(this);
        mySwitch2=(Switch)findViewById(R.id.Bye);
        mySwitch2.setOnCheckedChangeListener(this);


        String clientId = MqttClient.generateClientId();
         client =new MqttAndroidClient(this.getApplicationContext(), MqttHost,clientId);
        options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());


        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                    setSubscription();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MainActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
            subText.setText(new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });


        subscribe = (Button) findViewById(R.id.subs);

        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSubscription();
            }
        });
    }
    public void pub(View v){

        String topic = topicview.getText().toString();
        Log.i("topicview", "topicview :" +topic);
        String conv=mymsg.getText().toString();
       String message = conv;
            try {
                client.publish(topic, message.getBytes(), 0, false);
            } catch (MqttException e) {
                e.printStackTrace();
            }

    }
    public void setSubscription(){
        try{
            Log.i("sub", topicview.getText().toString());
            client.subscribe(topicview.getText().toString(),0);
        }
        catch(MqttException e){
            e.printStackTrace();
        }
    }
    public void conn(View v){
        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                    setSubscription();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MainActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void disconn(View v){
        try {
            IMqttToken token = client.disconnect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MainActivity.this, "Disconnection failed", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mySwitch.setTextOn("Hi");
        String msg= (String) mySwitch.getTextOn();
        mySwitch2.setTextOn("Bye");
        String msg2= (String) mySwitch2.getTextOn();

        if(mySwitch.isChecked()){
            mymsg.setText(msg);
        }
        else if(mySwitch2.isChecked()){
            mymsg.setText(msg2);
        }

        else{
            mymsg.setText("Off");
        }
    }
    }





//        publish=(Button) findViewById(R.id.publish);
//
////subscribe
//        Sub=(Button) findViewById(R.id.subs);
//        String clientId = MqttClient.generateClientId();
//
//        final MqttAndroidClient client =
//                new MqttAndroidClient(this.getApplicationContext(), "tcp://broker.hivemq.com:1883",
//                        clientId);
//
//        try {
//            IMqttToken token = client.connect();
//            token.setActionCallback(new IMqttActionListener() {
//                @Override
//                public void onSuccess(IMqttToken asyncActionToken) {
//                    // We are connected
//
//                }
//
//                @Override
//                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//                    // Something went wrong e.g. connection timeout or firewall problems
//
//
//                }
//            });
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//        Sub.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String topicview = "foo/bar";
//                int qos = 1;
//                try {
//                    IMqttToken subToken = client.subscribe(topicview, qos);
//                    subToken.setActionCallback(new IMqttActionListener() {
//                        @Override
//                        public void onSuccess(IMqttToken asyncActionToken) {
//                            // The message was published
//                        }
//
//                        @Override
//                        public void onFailure(IMqttToken asyncActionToken,
//                                              Throwable exception) {
//                            // The subscription could not be performed, maybe the user was not
//                            // authorized to subscribe on the specified topicview e.g. using wildcards
//
//                        }
//                    });
//                } catch (MqttException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        //connect
//        button=(Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String clientId = MqttClient.generateClientId();
//                MqttAndroidClient client =
//                        new MqttAndroidClient(MainActivity.this, "tcp://broker.hivemq.com:1883",
//                                clientId);
//
//                try {
//                    IMqttToken token = client.connect();
//                    token.setActionCallback(new IMqttActionListener() {
//                        @Override
//                        public void onSuccess(IMqttToken asyncActionToken) {
//                            Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        @Override
//                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//                            Toast.makeText(MainActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//                } catch (MqttException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        client.setCallback(new MqttCallback() {
//            @Override
//            public void connectionLost(Throwable cause) {
//
//            }
//
//            @Override
//            public void messageArrived(String topicview, MqttMessage message) throws Exception {
//                Toast.makeText(MainActivity.this, new String (message.getPayload()), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void deliveryComplete(IMqttDeliveryToken token) {
//
//            }
//        });
//    }
//}
