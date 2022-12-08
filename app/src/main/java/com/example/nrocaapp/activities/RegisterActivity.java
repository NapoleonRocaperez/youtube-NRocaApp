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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {

    CircleImageView mCircleImageView;
    TextInputEditText mTextInputUsername;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    TextInputEditText mTextInputConfirmPassword;
    Button mButtonRegister;
    AuthProviders mAuthProvider;
    UserProvider mUserProvider;
    AlertDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mCircleImageView=findViewById(R.id.circleimageback);
        mTextInputUsername=findViewById(R.id.TextInputUsernameR);
        mTextInputEmail=findViewById(R.id.TextViewInputEmailR);
        mTextInputPassword=findViewById(R.id.TextViewInputPasswordR);
        mTextInputConfirmPassword=findViewById(R.id.TextInputConfirmPassword);
        mButtonRegister=findViewById(R.id.BtnRegister);

        mAuthProvider= new AuthProviders();
        mUserProvider=new UserProvider();

        mDialog =new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un momento")
                .setCancelable(false).build();


        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
         String username=mTextInputUsername.getText().toString();
         String email=mTextInputEmail.getText().toString();
         String password=mTextInputPassword.getText().toString();
         String confirmpassword=mTextInputConfirmPassword.getText().toString();

         if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmpassword.isEmpty()){
             if (isEmailValid(email)){
                  if(password.equals(confirmpassword)){
                        if(password.length() >=6){
                            createUser(email,password,username);
                        }else {
                            Toast.makeText(this, "Las contraseñas debe tener 6 caracteres", Toast.LENGTH_SHORT).show();
                        }

                  }else{
                      Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                  }

             }else {
                 Toast.makeText(this, "Has insertado todos los campos pero el correo no es valido", Toast.LENGTH_SHORT).show();
             }


         }else {
             Toast.makeText(this, "para continuar inserta todos los campos", Toast.LENGTH_SHORT).show();
         }

    }

    private void createUser(final String email,String password, final String username) {
        mDialog.show();
        mAuthProvider.register(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String id=mAuthProvider.getUid();

                    User user=new User();
                    user.setId(id);
                    user.setEmail(email);
                    user.setUsername(username);
                    user.setPassword(password);
                    mUserProvider.create(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mDialog.dismiss();
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "El usuario se almaceno correctamente", Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(RegisterActivity.this,HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            }else {
                                Toast.makeText(RegisterActivity.this, "no se pudo almacenar en la base de datos", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    Toast.makeText(RegisterActivity.this, "El usuario se registro correctamente", Toast.LENGTH_SHORT).show();
                }else {
                    mDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}