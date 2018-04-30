package com.ceyon.contactsapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ContactAdapter contactAdapter;
    public static final int add = 1;
    public static final int list = 2;
    public int sortPosition;
    final List<Contact> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get views
        FloatingActionButton btnAddNewContact =  (FloatingActionButton) findViewById(R.id.btnAddNewContact);
       // Spinner spinnerSort = (Spinner) findViewById(R.id.spinSort);
        SearchView search = (SearchView) findViewById(R.id.searchView);
        final ListView lstInfo = (ListView) findViewById(R.id.lstContacts);

              //Creating and populating the sort spinner
        items.add(new Contact("bindu","9866152119","sureddybindureddy@gmail.com"));
        items.add(new Contact("indu","9000613111","indusureddy01@gmail.com"));
        items.add(new Contact("madhuri","9866653534","madhuri@gmail.com"));
        items.add(new Contact("sweetha","9494556785","sweetha@gmail.com"));
        items.add(new Contact("surenderReddy","9494565779","surenderreddy@gmail.com"));
        items.add(new Contact("priyanka","1412287582","priya@gmail.com"));

        //linking contact adapter
        contactAdapter = new ContactAdapter(this, items);
        lstInfo.setAdapter(contactAdapter);



       search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactAdapter.getFilter().filter(newText);
                return false;
            }
        });

        btnAddNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
                //intent.putExtra("", "");
               startActivity(intent);
                }

        });

        lstInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
                intent.putExtra("contact", (Contact) view.getTag());
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, list);
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case add:
                    Contact contactToAdd = (Contact) data.getSerializableExtra("addNewContact");

                    if (contactToAdd != null) {
                        contactAdapter.add(contactToAdd);
                        contactAdapter.sortContacts(sortPosition);
                        contactAdapter.notifyDataSetChanged();
                    }
                    break;
                case list:
                    Contact contactToDelete = (Contact) data.getSerializableExtra("deleteContact");
                    Contact updatedContact = (Contact) data.getSerializableExtra("updatedContact");
                    if (updatedContact == null) {
                        if (contactToDelete != null) {
                            Toast.makeText(this, "Contact deleted",
                                    Toast.LENGTH_LONG).show();
                            contactAdapter.remove(contactToDelete);
                            //contactAdapter.sortContacts(sortPosition);
                            contactAdapter.notifyDataSetChanged();
                        }
                    } else {

                        Toast.makeText(this, "Contact updated",
                                Toast.LENGTH_LONG).show();
                        contactAdapter.remove(contactToDelete);
                        contactAdapter.add(updatedContact);
                        contactAdapter.sortContacts(sortPosition);
                        contactAdapter.notifyDataSetChanged();
                    }
                    break;

            }
        }
    }

}

