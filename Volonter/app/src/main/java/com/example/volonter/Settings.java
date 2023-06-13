package com.example.volonter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.volonter.Prevalent.Prevalent;
import com.example.volonter.model.User;
import com.example.volonter.preferences.UserPreferences;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Settings extends AppCompatActivity {

    private CircleImageView settings_account_image;
    private EditText settings_name, settings_lastname, settings_age;
    private TextView close_settings, save_settings;
    private String checker = "";
    private Uri imageUri;
    private StorageReference storageProfilePictureRef;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings_account_image = findViewById(R.id.settings_account_image);
        settings_name = findViewById(R.id.settings_name);
        settings_lastname = findViewById(R.id.settings_lastname);
        settings_age = findViewById(R.id.settings_age);
        close_settings = findViewById(R.id.close_settings);
        save_settings = findViewById(R.id.save_settings);

        storageProfilePictureRef = FirebaseStorage.getInstance().getReference().child("Profile pictures");

        userInfoDisplay (settings_account_image, settings_name, settings_lastname, settings_age);

        close_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);
            }
        });

        save_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checker.equals("clicked")){
                    userInfoSaved();
                } else {
                    updateOnlyUserInfo();
                }
            }
        });

        settings_account_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(Settings.this);
            }
        });

    }

    private void userInfoDisplay(final CircleImageView settings_account_image,final EditText settings_name,final EditText settings_lastname,final EditText settings_age) {

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
                        String phone = dataSnapshot.child("lastname").getValue().toString();
                        String address = dataSnapshot.child("age").getValue().toString();

                        Picasso.get().load(image).into(settings_account_image);
                        settings_name.setText(name);
                        settings_lastname.setText(phone);
                        settings_age.setText(address);
                    }
                    if (dataSnapshot.child("name").exists())
                    {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String phone = dataSnapshot.child("lastname").getValue().toString();
                        String address = dataSnapshot.child("age").getValue().toString();

                        settings_name.setText(name);
                        settings_lastname.setText(phone);
                        settings_age.setText(address);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            settings_account_image.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText(this, "Ошибка. Попробуйте еще раз", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(Settings.this, Settings.class));
            finish();
        }
    }

    private void userInfoSaved() {
        if (TextUtils.isEmpty(settings_name.getText().toString())) {
            Toast.makeText(this, "Заполните имя", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(settings_lastname.getText().toString())) {
            Toast.makeText(this, "Заполните фамилию", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(settings_age.getText().toString())) {
            Toast.makeText(this, "Заполните возраст", Toast.LENGTH_SHORT).show();
        } else if (checker.equals("clicked")) {
            uploadImage();
        }
    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Оюновляемся ...");
        progressDialog.setMessage("Пожалуйста подождите");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (imageUri != null) {

            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

            final StorageReference fileRef = storageProfilePictureRef
                    .child(currentFirebaseUser.getUid()+ ".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUrl = task.getResult();
                                String myUrl = downloadUrl.toString();

                                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User");

                                HashMap<String, Object> userMap = new HashMap<>();
                                userMap. put("name", settings_name.getText().toString());
                                userMap. put("lastname", settings_lastname.getText().toString());
                                userMap. put("age", settings_age.getText().toString());
                                userMap. put("image", myUrl);
                                ref.child(currentFirebaseUser.getUid()).updateChildren(userMap);
                                //ref.child(Prevalent.currentOnlineUser.getId()).updateChildren(userMap);

                                progressDialog.dismiss();

                                startActivity(new Intent(Settings.this, MainActivity.class));
                                Toast.makeText(Settings.this, "Информация успешно сохранена", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Settings.this, "Ошибка", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Изображение не выбрано", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateOnlyUserInfo() {

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name", settings_name.getText().toString());
        userMap.put("lastname", settings_lastname.getText().toString());
        userMap.put("age", settings_age.getText().toString());
        ref.child(currentFirebaseUser.getUid()).updateChildren(userMap);

        startActivity(new Intent(Settings.this, MainActivity.class));
        Toast.makeText(Settings.this, "Успешно сохранено", Toast.LENGTH_SHORT).show();
        finish();
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
            Intent intentMain = new Intent(Settings.this, MainActivity.class);
            startActivity(intentMain);
        }

        if (item.getItemId() == 1) {
            Intent intentAccount = new Intent(Settings.this, Account.class);
            startActivity(intentAccount);
        }

        if (item.getItemId() == 2) {
            Intent intentEvents = new Intent(Settings.this, Events.class);
            startActivity(intentEvents);
        }

        if (item.getItemId() == 3) {
            Intent intentSettings = new Intent(Settings.this, Settings.class);
            startActivity(intentSettings);
        }

        return super.onOptionsItemSelected(item);
    }
}