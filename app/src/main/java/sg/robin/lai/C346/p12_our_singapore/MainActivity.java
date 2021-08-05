package sg.robin.lai.C346.p12_our_singapore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Island> al;
    ListView lv;
    CustomAdapter ca;
    Button show , Add, showAll;
    DBHelper dbh = new DBHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show = findViewById(R.id.btnShow);
        Add = findViewById(R.id.btnAdd);
        showAll = findViewById(R.id.btnshowA);
        lv = findViewById(R.id.lv);

        al = new ArrayList<Island>();
        ca = new CustomAdapter(this,R.layout.row,al);
        lv.setAdapter(ca);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                al.clear();

                String rating  = "5";
                al.addAll(dbh.getAllIslands(rating));

                ca.notifyDataSetChanged();
            }
        });

        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                al.clear();

                al.addAll(dbh.getAllIslands());

                ca.notifyDataSetChanged();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Island data = al.get(position);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.activity_edit,null);

                final EditText editID = viewDialog.findViewById(R.id.etID);
                final EditText editName = viewDialog.findViewById(R.id.etName);
                final EditText editDesc = viewDialog.findViewById(R.id.etDesc);
                final EditText editArea = viewDialog.findViewById(R.id.etArea);
                final RatingBar editRB = viewDialog.findViewById(R.id.ratingBar);
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);

                myBuilder.setView(viewDialog);
                myBuilder.setTitle("Add New Island");
                editID.setText(data.getId()+"");
                editID.setEnabled(false);
                editName.setText(data.getName());
                editDesc.setText(data.getDescription());
                editArea.setText(data.getArea()+"");
                editRB.setRating(data.getStar());

                myBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(editName.getText().toString().trim().length() != 0 && editDesc.getText().toString().trim().length() != 0
                                && editArea.getText().toString().trim().length() != 0)
                        {
                            String name = editName.getText().toString().trim();
                            String desc = editDesc.getText().toString().trim();
                            int area = Integer.parseInt(editArea.getText().toString().trim());
                            float rating = editRB.getRating();

                            data.setName(name);
                            data.setDescription(desc);
                            data.setArea(area);
                            data.setStar(rating);
                            dbh.updateIsland(data);
                            ca.notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Data Incomplete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                myBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View viewDialog = inflater.inflate(R.layout.delete,null);
                        final TextView tvConfirmIsland = viewDialog.findViewById(R.id.textViewIsland);
                        AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                        myBuilder.setView(viewDialog);
                        myBuilder.setTitle("Danger");
                        tvConfirmIsland.setText(data.getName());
                        myBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbh.deleteIsland(data.getId());
                                ca.notifyDataSetChanged();
                                showAll.performClick();
                            }
                        });
                        myBuilder.setNegativeButton("No",null);
                        AlertDialog myDialog = myBuilder.create();
                        myDialog.show();
                    }
                });

                myBuilder.setNeutralButton("Cancel",null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.activity_add,null);

                final EditText addName = viewDialog.findViewById(R.id.etAddName);
                final EditText addDesc = viewDialog.findViewById(R.id.etAddDesc);
                final EditText addArea = viewDialog.findViewById(R.id.etAddArea);
                final RatingBar addRB = viewDialog.findViewById(R.id.ratingBarAdd);
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);

                myBuilder.setView(viewDialog);
                myBuilder.setTitle("Add New Island");
                myBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(addName.getText().toString().trim().length() != 0 && addDesc.getText().toString().trim().length() != 0
                         && addArea.getText().toString().trim().length() != 0)
                        {
                            String name = addName.getText().toString().trim();
                            String desc = addDesc.getText().toString().trim();
                            int area = Integer.parseInt(addArea.getText().toString().trim());
                            float rating = addRB.getRating();

                            long result = dbh.insertIsland(name,desc,area,rating);
                            ca.notifyDataSetChanged();
                            showAll.performClick();
                            if(result != 1)
                            {
                                Toast.makeText(MainActivity.this,"Island Inserted",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Insert Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Data Incomplete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                myBuilder.setNegativeButton("Cancel",null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

        showAll.performClick();
    }
}