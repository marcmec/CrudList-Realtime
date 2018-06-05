package sample.marcos.testefcm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sample.marcos.testefcm.model.Computador;

public class MainActivity extends AppCompatActivity {

    public static final String TAG= "marc";
    EditText editText,editIp;
    ListView listView;
    DatabaseReference myRef;
    FirebaseDatabase database;
    Computador selectPc;

    private List<Computador> listComp= new ArrayList<Computador>();
    private ArrayAdapter<Computador> computadorArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText= (EditText) findViewById(R.id.edit_nome);
        editIp= (EditText) findViewById(R.id.edit_ip);
        listView= (ListView) findViewById(R.id.list_pc);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectPc= (Computador)adapterView.getItemAtPosition(i);
                editText.setText(selectPc.getNomePc());
                editIp.setText(selectPc.getNomeIp());
            }

            });
        //chamadas
       initialFirebase();
       eventData();
    }

    public void initialFirebase(){
        FirebaseApp.initializeApp(MainActivity.this);
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        myRef = database.getReference();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
       if(id== R.id.add){
           Computador c= new Computador();
           c.setID(UUID.randomUUID().toString());
           c.setNomePc(editText.getText().toString());
           c.setNomeIp(editIp.getText().toString());
           myRef.child("Computadores").child(c.getID()).setValue(c);
           clearSpace();
       }else if(id==R.id.refresh){
           Computador c= new Computador();
           c.setID(selectPc.getID());
           c.setNomePc(editText.getText().toString().trim());
           c.setNomeIp(editIp.getText().toString().trim());
           myRef.child("Computadores").child(c.getID()).setValue(c);
           clearSpace();
       } else if(id== R.id.del){
           Computador c= new Computador();
           c.setID(selectPc.getID());
           myRef.child("Computadores").child(c.getID()).removeValue();
           clearSpace();
       }

        return true;
    }
    public void clearSpace(){
        editText.setText("");
        editIp.setText("");
    }

    private void eventData(){
        myRef.child("Computadores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listComp.clear();
                for (DataSnapshot obj:dataSnapshot.getChildren()){
                    Computador pc = obj.getValue(Computador.class);
                    listComp.add(pc);
                }
                computadorArrayAdapter= new ArrayAdapter<Computador>(MainActivity.this, android.R.layout.simple_list_item_1, listComp);
                listView.setAdapter(computadorArrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
