package com.lpf.bmob;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lpf.bmob.bean.Person;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 *@author liupengfei
 *@time 17/2/7 上午11:07
 */
  
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initEvents();
    }

    private void initEvents() {
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person p = new Person();
                p.setName("liupengfei");
                p.setAddress("beijing");
                p.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (null == e) {
                            Toast.makeText(MainActivity.this, "add success,return " + s, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "add failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        findViewById(R.id.btnQuery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
                bmobQuery.getObject("c7b96d8bfe", new QueryListener<Person>() {
                    @Override
                    public void done(Person person, BmobException e) {
                        if(null == e){
                            Toast.makeText(MainActivity.this, "query success", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "query failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Person p = new Person();
                p.setAddress("shanghai");
                p.update("c7b96d8bfe", new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(null == e){
                            Toast.makeText(MainActivity.this, "update success"+p.getUpdatedAt(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "update failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Person p = new Person();
                p.setObjectId("0c0bd09adf");
                p.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(null == e){
                            Toast.makeText(MainActivity.this, "delete success"+p.getUpdatedAt(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "delete failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}
