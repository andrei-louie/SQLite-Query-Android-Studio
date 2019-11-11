package sanchez.com;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import sanchez.Adapters.AdapterRecords;
import sanchez.Model.Accounts;

public class Accounts_Record extends AppCompatActivity {
    private static final String TAG = "Accounts_Record";
    Context context;
    DatabaseHelper DB;
    ListView lvRecordLists;
    ArrayList<Accounts> arrayList;
    AdapterRecords adapterRecords;
    Accounts ac;
    SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts__record);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );


        shared = getSharedPreferences("sanchez", context.MODE_PRIVATE);
        arrayList = new ArrayList<>();
        DB = new DatabaseHelper(this);
        fetch_records();
    }

    //Display All Accounts Record
    public void fetch_records(){
        lvRecordLists = findViewById(R.id.lvAccountsRecord);
        arrayList = DB.getAllRecords();
        adapterRecords = new AdapterRecords(this,arrayList);
        try {
            lvRecordLists.setAdapter(adapterRecords);
            adapterRecords.notifyDataSetChanged();
            lvRecordLists.invalidate();
            lvRecordLists.refreshDrawableState();
        }
        catch (Exception e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menu_id = item.getItemId();
        if (menu_id==R.id.btnlogout){
            new AlertDialog.Builder(this)
                    .setTitle("Logout Account...")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            shared.edit().remove(DB.TABLE_USERNAME).commit();
                            Toast.makeText(Accounts_Record.this, "You've been Successfully Logout", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getBaseContext(), MainActivity.class));
                            Accounts_Record.this.finish();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWindowFocusChanged(boolean hasfocus) {
        super.onWindowFocusChanged(hasfocus);
       fetch_records();
    }
    @Override
    protected void onResume() {
        if(!shared.contains(DB.TABLE_USERNAME)) {
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            Accounts_Record.this.finish();
        }
        super.onResume();
    }


}
