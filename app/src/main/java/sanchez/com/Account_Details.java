package sanchez.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Account_Details extends AppCompatActivity {
    View convertViews;
    DatabaseHelper DB;
    EditText txtName,txtUser,txtPass,txtConfirmPass;
    Button btn_details,Back_Application;
    public String s_id,s_name,s_username,s_pass,btn_save,btn_update;
    private LayoutInflater inflater;
    ListView lvRecords;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__details);

        DB = new DatabaseHelper(this);

        txtName = (EditText)findViewById(R.id.txtfullname);
        txtUser = (EditText)findViewById(R.id.txtUser);
        txtPass = (EditText)findViewById(R.id.txtPass);
        txtConfirmPass = (EditText)findViewById(R.id.txtConfirmPass);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        btn_details = (Button)findViewById(R.id.btnSave);
        Intent intent = getIntent();

        if (intent != null)
        {
            Bundle bundle = intent.getExtras();
            btn_save = bundle.getString(Intent.EXTRA_TEXT);
            btn_details.setText(btn_save);

            btn_update = bundle.getString(Intent.EXTRA_TEXT);
            btn_details.setText(btn_update);

            s_id = bundle.getString("ID");
            s_name = bundle.getString("Fullname");
            s_username = bundle.getString("Username");
            s_pass = bundle.getString("Password");
        }

        txtName.setText(s_name);
        txtUser.setText(s_username);
        txtPass.setText(s_pass);
        txtConfirmPass.setText(s_pass);

        Back_Application = (Button)findViewById(R.id.btnBack);

        btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getName = txtName.getText().toString();
                String getUser = txtUser.getText().toString();
                String getPass = txtPass.getText().toString();
                String getConfirmPass = txtConfirmPass.getText().toString();
                if (getName.isEmpty() || getUser.isEmpty() || getPass.isEmpty() || getConfirmPass.isEmpty()){
                    Toast.makeText(getApplicationContext(),"All Fields Must Be Required!",Toast.LENGTH_SHORT).show();
                } else {
                    if (getPass.equals(getConfirmPass)){
                            Boolean chkUsername = DB.chkUser(getUser);
                            if (chkUsername == true) {
                                if (btn_details.getText().toString().equalsIgnoreCase("Save")) {
                                    Boolean insert = DB.insert(getName, getUser, getPass);
                                    if (insert == true) {
                                        Toast.makeText(getApplicationContext(), "Register Successfully!", Toast.LENGTH_SHORT).show();
                                        txtName.requestFocus();
                                        txtName.setText("");
                                        txtUser.setText("");
                                        txtPass.setText("");
                                        txtConfirmPass.setText("");
                                    }
                                }else if(btn_details.getText().toString().equalsIgnoreCase("Update"))
                                {
                                    final int getID = Integer.parseInt(s_id);
                                    DB.updateRecords(getID,txtName.getText().toString(),txtUser.getText().toString(),txtPass.getText().toString());
                                    Toast.makeText(getApplicationContext(),"Records Successfully Update!",Toast.LENGTH_SHORT).show();
                                    lvRecords = (ListView)findViewById(R.id.lvAccountsRecord);
                                    Intent i = new Intent(Account_Details.this, Accounts_Record.class);
                                    startActivityForResult(i, 1);
                                    finish();
                                }
                            }
                            else
                                {
                                    Toast.makeText(getApplicationContext(),"Username Already Exists!",Toast.LENGTH_SHORT).show();
                                }
                    } else {
                        Toast.makeText(getApplicationContext(),"Password Must Be The Same To Confirm Password!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Back_Application.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
