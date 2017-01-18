package com.lpf.learn;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//read contacts from cellphone
public class GetContactsActivity extends AppCompatActivity {
    
    ListView contactsListView;
    ArrayAdapter<String> contactsAdapter;
    ArrayList<String> contactsList = new ArrayList<>();

    Uri contactsUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    String contactsName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
    String contactsNumber = ContactsContract.CommonDataKinds.Phone.NUMBER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_contacts);
        
        initViews();
    }

    private void initViews() {
        contactsListView = (ListView) findViewById(R.id.contacts);
        
        contactsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, contactsList);
        contactsListView.setAdapter(contactsAdapter);
        
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }else{
            readContacts();
        }
    }

    //read contacts
    private void readContacts() {
        Cursor cursor = null;
        try{
            cursor = getContentResolver().query(contactsUri,null,null,null,null);
            if(cursor!=null){
                while(cursor.moveToNext()){
                    String displayName = cursor.getString(cursor.getColumnIndex(contactsName));
                    String number = cursor.getString(cursor.getColumnIndex(contactsNumber));
                    contactsList.add(displayName+"\n"+number);
                }
                contactsAdapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    readContacts();
                }else{
                    Toast.makeText(this, "just denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
