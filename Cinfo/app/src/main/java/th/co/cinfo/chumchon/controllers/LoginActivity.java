package th.co.cinfo.chumchon.controllers;

import android.net.ConnectivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import th.co.cinfo.chumchon.R;
import th.co.cinfo.chumchon.models.*;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin;
    EditText etUsername, etPassword;
    String pUsername, pPassword;
    ModelLogin modelLogin;
    ModelToken modelToken;
    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        modelLogin = new ModelLogin();
        modelToken = new ModelToken();

        btnLogin.setOnClickListener(this);
    }

    private void init() {
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = manager.getActiveNetworkInfo();
                if(netInfo != null && netInfo.isConnectedOrConnecting()){
                    try {
                        pUsername = etUsername.getText().toString();
                        pPassword = Sha1Hash.SHA1(etPassword.getText().toString());
                        pUsername = "demo00124@900001";
                        pPassword = "d8154449f9e858f9068e36bd73cfdc478f67c0c9";

                        String loginStatus = ModelLogin.getByName(pUsername, pPassword,"status");
                        String loginToken = ModelLogin.getByName(pUsername, pPassword,"token");

                        SharedPreferences sp = getSharedPreferences("myStorage", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("token", loginToken);
                        editor.commit();

                        //Toast.makeText(this,"Login Token: " + sp.getString("strToken", "NoData"), Toast.LENGTH_SHORT).show();

                        if(loginStatus.equals("OK")){
                            intent = new Intent(getApplicationContext(), F01_12Activity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(this,"ชื่อผู้ใช้หรือรหัสผ่าน ไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(this,"Connected", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"การเชื่อมต่อมีปัญหา", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
