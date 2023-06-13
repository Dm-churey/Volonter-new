package com.example.volonter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.volonter.preferences.UserPreferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Account extends AppCompatActivity {
    private CircleImageView imageAccount;
    private TextView account_name, account_lastname, account_post;
    private Button exit_button;
    private StorageReference storageProfilePictureRef;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        imageAccount = findViewById(R.id.imageAccount);
        account_name = findViewById(R.id.account_name);
        account_lastname = findViewById(R.id.account_lastname);
        account_post = findViewById(R.id.account_post);

        storageProfilePictureRef = FirebaseStorage.getInstance().getReference().child("Profile pictures");

        userDisplay(imageAccount, account_name,account_lastname, account_post);

        // Кнопка выхода из аккаунта
        exit_button = findViewById(R.id.exit_button);
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserPreferences uPref = new UserPreferences(view.getContext());
                uPref.setEntered(false);
                Intent intent = new Intent(Account.this, Authorization.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void userDisplay(final CircleImageView imageAccount, final TextView account_name, final TextView account_lastname, final TextView account_post) {

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("User").child(currentFirebaseUser.getUid());

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("image").exists())
                    {
                        String image = dataSnapshot.child("image").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String lastname = dataSnapshot.child("lastname").getValue().toString();
                        String post = dataSnapshot.child("post").getValue().toString();

                        Picasso.get().load(image).into(imageAccount);
                        account_name.setText(name);
                        account_lastname.setText(lastname);
                        account_post.setText(post);
                    }
                    if (dataSnapshot.child("name").exists())
                    {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String lastname = dataSnapshot.child("lastname").getValue().toString();
                        String post = dataSnapshot.child("post").getValue().toString();

                        account_name.setText(name);
                        account_lastname.setText(lastname);
                        account_post.setText(post);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override //Меню
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 0, 0, "Главная");
        menu.add(0, 1, 1, "Личный кабинет");
        menu.add(0, 2, 2, "Мероприятия");
        menu.add(0, 3, 3, "Настройки");

        return super.onCreateOptionsMenu(menu);
    }

    @Override //Переход по страницам в меню
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == 0) {
            Intent intentMain = new Intent(Account.this, MainActivity.class);
            startActivity(intentMain);
        }

        if (item.getItemId() == 1) {
            Intent intentAccount = new Intent(Account.this, Account.class);
            startActivity(intentAccount);
        }

        if (item.getItemId() == 2) {
            Intent intentEvents = new Intent(Account.this, Events.class);
            startActivity(intentEvents);
        }

        if (item.getItemId() == 3) {
            Intent intentSettings = new Intent(Account.this, Settings.class);
            startActivity(intentSettings);
        }

        return super.onOptionsItemSelected(item);
    }
}