package sg.edu.rp.c346.smsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
  Button btnSend, btnSendMsg;
  EditText etCon, etTo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    checkPermission();
    btnSend = findViewById(R.id.button);
    etCon = findViewById(R.id.editTextContent);
    etTo = findViewById(R.id.editTextTo);
    btnSendMsg = findViewById(R.id.button2);

    btnSend.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String destination = etTo.getText().toString();
        String content = etCon.getText().toString();
        String[] count = destination.split(","); // Split the numbers

        if(count.length <1){
          SmsManager smsManager = SmsManager.getDefault();
          smsManager.sendTextMessage(destination,null,content,null,null);

          Toast toast = Toast.makeText(getApplicationContext(),"Message Sent", Toast.LENGTH_LONG);
          toast.show();
        }else{
          String [] array = destination.split(",");

          Toast.makeText(getApplicationContext(),"Message sent" ,Toast.LENGTH_LONG).show();
          for(int i = 0; i <array.length; i ++){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(array[i], null,content,null,null);
          }
        }
      }
    });
    btnSendMsg.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String message = etCon.getText().toString();
        String sendTo = etTo.getText().toString();

        Uri sms_uri = Uri.parse("smsto:+" + sendTo);

        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        sms_intent.putExtra("sms_body", message);

        startActivity(sms_intent);
        etCon.setText("");

      }
    });
  }
  private void checkPermission() {
    int permissionSendSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
    int permissionRecvSMS = ContextCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS);
    if(permissionSendSMS != PackageManager.PERMISSION_GRANTED && permissionRecvSMS != PackageManager.PERMISSION_GRANTED){
      String[] permissionNeeded = new String[] {Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS};
      ActivityCompat.requestPermissions(this,permissionNeeded,1);
    }
  }

}