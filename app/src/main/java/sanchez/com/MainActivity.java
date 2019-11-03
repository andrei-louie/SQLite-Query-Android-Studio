package sanchez.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper DB;
    EditText getUsername,getPassword;
    Button Create_Account,Login_Account;
    public String Save_Info = "Save";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB = new DatabaseHelper(this);
        getUsername = (EditText)findViewById(R.id.txtUsername);
        getPassword = (EditText)findViewById(R.id.txtPass);
        Login_Account = (Button)findViewById(R.id.btnlogin);
        Create_Account = (Button)findViewById(R.id.btncreateaccount);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        Login_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String setUsername = getUsername.getText().toString();
                String setPassword = getPassword.getText().toString();
                Boolean chkUsernamePassword = DB.username_password(setUsername,setPassword);
                if(chkUsernamePassword==true){
                    Intent i = new Intent(MainActivity.this,Accounts_Record.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(),"Invalid Username and Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }
    private void createAccount() {
        Intent intent = new Intent(this,Account_Details.class);
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,Save_Info);
        intent.setType("text/plain");
        startActivity(intent);
    }
}
