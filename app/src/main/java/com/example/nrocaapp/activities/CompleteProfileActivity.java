package com.example.nrocaapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nrocaapp.R;
import com.example.nrocaapp.models.User;
import com.example.nrocaapp.providers.AuthProviders;
import com.example.nrocaapp.providers.UserProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;


public class CompleteProfileActivity extends AppCompatActivity {



    TextInputEditText mTextInputUsername;
    Button mButtonConfirmar;
    AuthProviders mAuthProvider;
    UserProvider mUserProvider;
    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);



        mTextInputUsername=findViewById(R.id.TextInputUsernameCp);

        mButtonConfirmar=findViewById(R.id.ButtonConfirmar);

        mAuthProvider=new AuthProviders();
        mUserProvider=new UserProvider();

        mDialog =new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un momento")
                .setCancelable(false).build();



        mButtonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String username=mTextInputUsername.getText().toString();


        if (!username.isEmpty()){
            upDateUser(username);


        }else {
            Toast.makeText(this, "para continuar inserta todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    private void upDateUser(final String username) {
        String id =mAuthProvider.getUid();
        User user=new User();
        user.setUsername(username);
        user.setId(id);
        mDialog.show();
        mUserProvider.update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mDialog.dismiss();
                if(task.isSuccessful()){
                    Intent intent=new Intent(CompleteProfileActivity.this,HomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(CompleteProfileActivity.this, "No se puedo alamacenar el usuario en la base de datos ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}