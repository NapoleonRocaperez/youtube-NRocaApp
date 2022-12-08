package com.example.nrocaapp.providers;

import android.content.Context;

import com.example.nrocaapp.utils.CompressorBitmapImage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Date;

public class ImageProvider {

    StorageReference mStorage;

    public ImageProvider() {
        mStorage= FirebaseStorage.getInstance().getReference();
    }

    public UploadTask save(Context context, File file){
        byte[] imageByte= CompressorBitmapImage.getImage(context,file.getPath(),500,500);
        StorageReference storage=mStorage.child(new Date()+".jpg");
        UploadTask task= storage.putBytes(imageByte);
        return task;
    }
}
