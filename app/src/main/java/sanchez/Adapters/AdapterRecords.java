package sanchez.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sanchez.Model.Accounts;
import sanchez.com.Account_Details;
import sanchez.com.Accounts_Record;
import sanchez.com.DatabaseHelper;
import sanchez.com.MainActivity;
import sanchez.com.R;

public class AdapterRecords extends BaseAdapter {

    public static final String EXTRA_RESPONSE = "EXTRA_RESPONSE";
    private Context context;
    private ArrayList<Accounts> arrayList;
    private LayoutInflater inflater;
    public String Update_Info = "Update";
    DatabaseHelper DB;

    public AdapterRecords(Context context,ArrayList<Accounts> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public Accounts getItem(int position){return arrayList.get(position);}

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class viewHolder{
        TextView tv_id,tv_name,tv_username,tv_password;
        ImageView ivEditItem,ivDeleteItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            DB = new DatabaseHelper(context);
            viewHolder holder = new viewHolder();


            if (convertView==null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.adapter_accounts_record, null);
                holder.tv_id = (TextView) convertView.findViewById(R.id.tvID);
                holder.tv_name = (TextView) convertView.findViewById(R.id.tvFullname);
                holder.tv_username = (TextView) convertView.findViewById(R.id.tvUsername);
                holder.tv_password = (TextView) convertView.findViewById(R.id.tvPassword);
                holder.ivDeleteItem = (ImageView)convertView.findViewById(R.id.ivdelete);
                holder.ivEditItem = (ImageView)convertView.findViewById(R.id.ivEdit);

                convertView.setTag(holder);
            }else {
                holder = (viewHolder) convertView.getTag();
            }

            final Accounts accounts = arrayList.get(position);

            holder.tv_id.setText(String.valueOf(accounts.getId()));
            holder.tv_name.setText(accounts.getName());
            holder.tv_username.setText(accounts.getUsername());
            holder.tv_password.setText(accounts.getPassword());

            holder.ivEditItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        Intent intent = new Intent(context.getApplicationContext(),Account_Details.class);
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        intent.putExtra(Intent.EXTRA_TEXT,Update_Info);
                        intent.setType("text/plain");
                        intent.putExtra("ID",String.valueOf(accounts.getId()));
                        intent.putExtra("Fullname",accounts.getName());
                        intent.putExtra("Username",accounts.getUsername());
                        intent.putExtra("Password",accounts.getPassword());
                        context.getApplicationContext().startActivity(intent);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
            });
            holder.ivDeleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(context)
                                .setTitle("Delete Account Records...")
                                .setMessage("Do you want to delete this record?")
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which){
                                        DB.deleteRecords(accounts.getId());
                                        Toast.makeText(context,"Successfully Delete!",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(context.getApplicationContext(),Accounts_Record.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                        context.getApplicationContext().startActivity(intent);
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which){
                                        dialog.dismiss();
                                    }
                                })
                                .create();
                        alertDialog.show();
                    }catch (Exception e){
                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });

        return convertView;
    }
}
