package com.lpf.learn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lpf.learn.util.PreferenceUtil;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraActivity extends AppCompatActivity {


//    @BindView(R.id.button)
//    Button takePhoto;
//    @BindView(R.id.photoImage)
//    ImageView photoImage;

    ArrayList<String> datas = new ArrayList<String>();

    public static final int TAKE_PHOTO = 1;

    public Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
//        ButterKnife.bind(this);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage = new File(getExternalCacheDir(), "output_img.jpg");

                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(Build.VERSION.SDK_INT >=24){
                    uri = FileProvider.getUriForFile(CameraActivity.this,"com.example.camera.fileprovider",outputImage);
                }else{
                    uri = Uri.fromFile(outputImage);
                }

                //start camera
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
//                    try {
//                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
//                        ((ImageView)findViewById(R.id.photoImage)).setImageBitmap(bitmap);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }


                    // use picasso
//                    Picasso.with(CameraActivity.this)
//                            .load(uri)
//                            .placeholder(R.mipmap.loading)
//                            .error(R.mipmap.loading)
//                            .resize(400,400)
//                            .centerCrop()
//                            .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_CACHE)  //查看大图时放弃使用内存缓存，图片从网络下载后会缓存到磁盘中，加载会从磁盘中加载，可加快内存的回收
//                            .into((ImageView)findViewById(R.id.photoImage));


                    //use glide
                    Glide.with(CameraActivity.this)
                            .load(uri)
                            .placeholder(R.mipmap.loading)
                            .error(R.mipmap.loading)
                            .override(400,400)
                            .centerCrop()
                            .into((ImageView)findViewById(R.id.photoImage));
                }
                break;
            default:
                break;
        }
    }
}
