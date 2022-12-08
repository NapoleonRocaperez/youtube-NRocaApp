package com.example.nrocaapp.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nrocaapp.R;
import com.example.nrocaapp.utils.FileUtil;

import java.io.File;

public class PostActivity extends AppCompatActivity {
    ImageView imageViewPost1;
    File Imagefile;
    Button ButtonPost;
    private final  int Gallery_REQUEST_CODE=1;
    private ActivityResultLauncher<Intent> intentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //onActivityResult();//nueva forma

        imageViewPost1=findViewById(R.id.ImageViewPost1);
        imageViewPost1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

   private void openGallery() {
        Intent galleyIntent=new Intent(Intent.ACTION_GET_CONTENT);
        galleyIntent.setType("image/*");
        startActivityForResult(galleyIntent,Gallery_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_REQUEST_CODE && requestCode==RESULT_OK){
            try {
                Imagefile= FileUtil.from(this,data.getData());
                imageViewPost1.setImageBitmap(BitmapFactory.decodeFile(Imagefile.getAbsolutePath()));
            }catch (Exception e){
                Log.d("ERROR","Se produjo un error"+e.getMessage());
                Toast.makeText(this, "Se produjo un error", Toast.LENGTH_SHORT).show();
            }

        }

    }

  /* private void openGallery() {
        Intent galleyIntent=new Intent();
        galleyIntent.setType("image/*");
        galleyIntent.setAction(Intent.ACTION_GET_CONTENT);
        intentLauncher.launch(galleyIntent);
    }

    private  void onActivityResult(){
           intentLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                   result -> {
                       if(result.getResultCode() == Activity.RESULT_OK){
                           Intent data = result.getData();
                           if(result.getResultCode() ==Gallery_REQUEST_CODE){
                               try {
                                   Imagefile= FileUtil.from(this,data.getData());
                                   imageViewPost1.setImageBitmap(BitmapFactory.decodeFile(Imagefile.getAbsolutePath()));
                               }catch (Exception e){
                                   Log.d("ERROR","Se produjo un error"+e.getMessage());
                                   Toast.makeText(this, "Se produjo un error", Toast.LENGTH_SHORT).show();
                               }
                           }

                       }

                   });

    }*/
}