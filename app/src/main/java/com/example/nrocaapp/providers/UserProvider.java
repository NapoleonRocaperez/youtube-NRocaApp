package com.example.nrocaapp.providers;

import com.example.nrocaapp.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserProvider {

    private CollectionReference mCollection;  /* se crea el contructor*/

    public  UserProvider(){
        mCollection=FirebaseFirestore.getInstance().collection( "Users");

    }

    public Task<DocumentSnapshot> getUser(String id){
        return mCollection.document(id).get();
    }

    public Task<Void> create(User user) {
        return  mCollection.document(user.getId()).set(user);
    }

    public Task<Void> update(User user) {
        Map<String,Object>map=new HashMap<>();
        map.put("username",user.getUsername());


        return  mCollection.document(user.getId()).update(map);
    }


}
