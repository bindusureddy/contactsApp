package com.ceyon.contactsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        final EditText contactName = (EditText) findViewById(R.id.addName);
        final EditText contactEmail = (EditText) findViewById(R.id.addEmail);
        final EditText contactCell = (EditText) findViewById(R.id.addCellNumber);

        final Button btnAdd = (Button) findViewById(R.id.btnAdd);
        final Button btnDelete = (Button) findViewById(R.id.btnDelete);
         final Button btnEdit = (Button) findViewById(R.id.btnEdit);
        final Button btnSave = (Button) findViewById(R.id.btnSave);


    Bundle bundle;
        if (((bundle = getIntent().getExtras()) != null) && (bundle.getSerializable("contact") != null)) {
            final Contact contactObj = (Contact) bundle.getSerializable("contact");
            if (contactObj != null) {
                btnAdd.setVisibility(View.INVISIBLE);
                btnSave.setVisibility(View.INVISIBLE);
                contactName.setText(contactObj.Name);
                //contactSurname.setText(contactObj.surname);
                contactEmail.setText(contactObj.email);
                contactCell.setText(contactObj.cellNumber);
                contactName.setEnabled(false);
               // contactSurname.setEnabled(false);
                contactEmail.setEnabled(false);
                contactCell.setEnabled(false);

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnAdd.setVisibility(View.INVISIBLE);
                        btnEdit.setVisibility(View.INVISIBLE);
                        btnSave.setVisibility(View.VISIBLE);
                        contactName.setEnabled(true);
                       // contactSurname.setEnabled(true);
                        contactEmail.setEnabled(true);
                        contactCell.setEnabled(true);

                    }
                });

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (contactName.length() <= 0  || contactEmail.length() <= 0 || contactCell.length() <= 0) {
                            Toast.makeText(EditContactActivity.this, "Please fill in all fields",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Contact updatedContact = new Contact(contactName.getText().toString(), contactCell.getText().toString(), contactEmail.getText().toString());
                            Intent backIntent = new Intent();
                            backIntent.putExtra("deleteContact", contactObj);
                            backIntent.putExtra("updatedContact", updatedContact);
                            setResult(RESULT_OK, backIntent);
                            finish();
                        }
                    }
                });

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent backIntent = new Intent();
                        backIntent.putExtra("deleteContact", contactObj);
                        setResult(RESULT_OK, backIntent);
                        finish();
                    }
                });
            }
        } else {
            btnDelete.setVisibility(View.INVISIBLE);
            btnEdit.setVisibility(View.INVISIBLE);
            btnSave.setVisibility(View.INVISIBLE);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String name = String.valueOf(contactName.getText());
                    //String surname = String.valueOf(contactSurname.getText());
                    String email = String.valueOf(contactEmail.getText());
                    String cell = String.valueOf(contactCell.getText());

                    if (name.length() <= 0  || email.length() <= 0 || cell.length() <= 0) {
                        Toast.makeText(EditContactActivity.this, "Please fill in all fields",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Contact newContact = new Contact(name, cell, email);
                        Intent backIntent = new Intent();
                        backIntent.putExtra("addNewContact", newContact);
                        setResult(RESULT_OK, backIntent);
                        finish();
                    }
                }
            });
        }
    }
}
