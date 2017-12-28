package rizki.learnfirebase;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class UserList extends ArrayAdapter<User>{

    private Activity context;
    private List<User> Users;
    DatabaseReference databaseReference;
    EditText editName;


    public UserList(@NonNull Activity context, List<User> Users, DatabaseReference databaseReference, EditText txtName ) {
        super(context, R.layout.layout_tampil, Users);
        this.context = context;
        this.Users = Users;
        this.databaseReference = databaseReference;
        this.editName = editName;

    }

    public View getView(int pos, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_tampil, null, true);

        TextView TxtNama = (TextView) listViewItem.findViewById(R.id.txtname);
        Button idDelete = (Button) listViewItem.findViewById(R.id.iddelete);
        Button idUpdate = (Button) listViewItem.findViewById(R.id.idupdate);

        final User User = Users.get(pos);
        TxtNama.setText(User.getNama());

        idDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child(User.getId()).removeValue();
            }
        });

        idUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editName.setText(User.getNama());
                MainActivity.userID= User.getId();
            }
        });
        return listViewItem;
    }

}
