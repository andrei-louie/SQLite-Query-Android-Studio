package sanchez.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
    String s_id,s_name,s_username,s_pass,btn_save,btn_update;
    private LayoutInflater inflater;
    ListView lvRecords;
    Context context;


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

            btn_update = bundle.getString(Intent.EXTRA_TEXT);

            s_id = bundle.getString("ID");
            s_name = bundle.getString("Fullname");
            s_username = bundle.getString("Username");
            s_pass = bundle.getString("Password");
        }

        txtName.setText(s_name);
        txtUser.setText(s_username);
        txtPass.setText(s_pass);
        txtConfirmPass.setText(s_pass);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menus) {
        getMenuInflater().inflate(R.menu.menu_bar, menus);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menu_id = item.getItemId();
        String getName = txtName.getText().toString();
        String getUser = txtUser.getText().toString();
        String getPass = txtPass.getText().toString();
        String getConfirmPass = txtConfirmPass.getText().toString();
        DB = new DatabaseHelper(this);
        Boolean chkUsername = DB.chkUser(getUser);
        if (menu_id==R.id.btnSave){

            if (getName.isEmpty()){
                txtName.setError("This field is required!");
            } else if (getUser.isEmpty()){
                txtUser.setError("This field is required!");
            } else if (getPass.isEmpty()){
                txtPass.setError("This field is required!");
            } else if (getConfirmPass.isEmpty()){
                txtConfirmPass.setError("This field is required!");
            } else {
                if (getPass.equals(getConfirmPass)){
                    if (chkUsername == true) {
                        if (btn_save.equals("Save_Info")) {
                            Boolean insert = DB.insert(getName, getUser, getPass);
                            if (insert == true) {
                                Toast.makeText(getApplicationContext(), "Register Successfully!", Toast.LENGTH_SHORT).show();
                                txtName.requestFocus();
                                txtName.setText("");
                                txtUser.setText("");
                                txtPass.setText("");
                                txtConfirmPass.setText("");
                            }
                        } else if (btn_update.equals("Update_Info")) {
                            final int getID = Integer.parseInt(s_id);
                            DB.updateRecords(getID,txtName.getText().toString(),txtUser.getText().toString(),txtPass.getText().toString());
                            Toast.makeText(getApplicationContext(),"Records Successfully Update!",Toast.LENGTH_SHORT).show();
                            lvRecords = (ListView)findViewById(R.id.lvAccountsRecord);
                            finish();
                            Intent i = new Intent(Account_Details.this, Accounts_Record.class);
                            startActivityForResult(i, 1);
                        }
                    }else {txtUser.setError("Username Already Exists!");}
                }else {
                        txtConfirmPass.setError("Password Must Be The Same To Confirm Password!");
                    }

                }
            } else if(menu_id==R.id.btnClose){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
