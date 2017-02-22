package com.lpf.greendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.lpf.greendao.gen.User;
import com.lpf.greendao.gen.UserDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mContent;
    private Button mBtnAdd;
    private Button mBtnDelete;
    private Button mBtnUpdate;
    private Button mBtnQuery;
    private ListView mListView;

    private UserAdapter mAdapter;
    private List<User> mUserList = new ArrayList<User>();

    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();

        initGreenDao();
    }

    private void initGreenDao() {
        mUserDao = GreenDaoManager.getInstance().getSession().getUserDao();
        mUserList = GreenDaoManager.getInstance().getSession().getUserDao().queryBuilder().build().list();
        mAdapter = new UserAdapter(this, mUserList);
        mListView.setAdapter(mAdapter);
    }

    private void initViews() {
        mContent = (EditText) findViewById(R.id.content);
        mBtnAdd = (Button) findViewById(R.id.btnAdd);
        mBtnDelete = (Button) findViewById(R.id.btnDelete);
        mBtnUpdate = (Button) findViewById(R.id.btnUpdate);
        mBtnQuery = (Button) findViewById(R.id.btnQuery);
        mListView = (ListView) findViewById(R.id.list);


        mBtnAdd.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mBtnUpdate.setOnClickListener(this);
        mBtnQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                insertUser(null, mContent.getText().toString());
                break;
            case R.id.btnDelete:
                if (mUserList != null && mUserList.size() > 0) {
                    deleteUser(mUserList.get(0).getName());
                }
                break;
            case R.id.btnUpdate:
                if (mUserList != null && mUserList.size() > 0) {
                    updateUser(mUserList.get(0).getName(),mContent.getText().toString());
                }
                break;
            case R.id.btnQuery:
                queryUser(mContent.getText().toString());
                break;
        }
        mAdapter.notifyDataSetChanged();
    }

    // insert new user
    private void insertUser(Long id, String name) {
        User user = new User(id, name);
        mUserDao.insertOrReplace(user);
        refreshListView();
    }

    // delete user
    private void deleteUser(String name) {
        User deleteUser = mUserDao.queryBuilder().where(UserDao.Properties.Name.eq(name)).build().unique();
        if (deleteUser != null) {
            mUserDao.deleteByKey(deleteUser.getId());
            Toast.makeText(this, "delete success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "not exists", Toast.LENGTH_SHORT).show();
        }
        refreshListView();
    }

    // update user
    private void updateUser(String prevName,String newName){
        User updateUser = mUserDao.queryBuilder().where(UserDao.Properties.Name.eq(prevName)).build().unique();
        if(updateUser!=null){
            updateUser.setName(newName);
            mUserDao.update(updateUser);
            Toast.makeText(this, "update success", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "not exists", Toast.LENGTH_SHORT).show();
        }
        refreshListView();
    }

    // query one user
    private void queryUser(String name){
        List result = mUserDao.queryBuilder().where(UserDao.Properties.Name.like(name)).build().list();

        Toast.makeText(this, "search results:"+result.size(), Toast.LENGTH_SHORT).show();
    }

    // query one user
    private void queryUserList(){
        List result = mUserDao.queryBuilder().build().list();
        Toast.makeText(this, "search results:"+result.size(), Toast.LENGTH_SHORT).show();
    }

    //refresh listview
    private void refreshListView() {
        mContent.setText("");
        mUserList.clear();
        mUserList.addAll(mUserDao.queryBuilder().build().list());
        mAdapter.notifyDataSetChanged();
    }
}
