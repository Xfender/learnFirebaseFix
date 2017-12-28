package rizki.learnfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnSave;
    EditText txtNama;
    DatabaseReference databaseReference;
    ListView ListData;
    List<User> Users;
    public static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Users = new ArrayList<User>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        btnSave =(Button) findViewById(R.id.idsave);
        txtNama =(EditText) findViewById(R.id.idnama);
        ListData = (ListView) findViewById(R.id.idlistview);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtNama.getText().toString();

                if(TextUtils.isEmpty(userID)){
                    String id= databaseReference.push().getKey();
                    User User = new User(id, name);
                    databaseReference.child(id).setValue(User);

                    Toast.makeText(MainActivity.this, "User Berhasil di Buat", Toast.LENGTH_SHORT).show();

                } else{
                    databaseReference.child(userID).child("name").setValue(name);

                    Toast.makeText(MainActivity.this, "User Berhasil di Update", Toast.LENGTH_SHORT).show();
                }

                txtNama.setText(null);
            }
        });
    }

    protected void onStart(){
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users.clear();

                for(DataSnapshot posSnapshot: dataSnapshot.getChildren()){
                    User User = posSnapshot.getValue(rizki.learnfirebase.User.class);
                    Users.add(User);
                }

                UserList userAdapter = new UserList(MainActivity.this, Users, databaseReference, txtNama);
                ListData.setAdapter(userAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
