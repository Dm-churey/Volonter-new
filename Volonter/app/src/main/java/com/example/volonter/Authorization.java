package com.example.volonter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.volonter.model.User;
import com.example.volonter.preferences.UserPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Authorization extends AppCompatActivity {

    private TextView authorization_text;
    private EditText login, password;
    private Button vxod_button, registracia_button;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    ConstraintLayout root;

//    public static Activity a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        UserPreferences pref= new UserPreferences(this);
        if (pref.getEntered()) {
            Intent intent = new Intent(Authorization.this, MainActivity.class);
            startActivity(intent);
        }

        //a = this;

        authorization_text = findViewById(R.id.authorization_text);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        vxod_button = findViewById(R.id.vxod_button);
        registracia_button = findViewById(R.id.registracia_button);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("User");

        root = findViewById(R.id.root_element);

        registracia_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterWindow();
            }
        });

        //Авторизация
        vxod_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(Authorization.this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(login.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        UserPreferences pref= new UserPreferences(view.getContext());
                                        pref.setEntered(true);
                                        Intent intent = new Intent(Authorization.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(Authorization.this, "Неправильный логин или пароль", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
//                auth();
            }
        });
    }

//    private void auth() {
//        String log = login.getText().toString();
//        String pas = password.getText().toString();
//        new Entrance(this, log, pas).execute();
//    }

    private void showRegisterWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Зарегистрироваться");
        dialog.setMessage("Введите все данные для регистрации");

        LayoutInflater inflater = LayoutInflater.from(this);
        View register_window = inflater.inflate(R.layout.register_window, null);
        dialog.setView(register_window);

        EditText name = register_window.findViewById(R.id.name_field);
        EditText lastname = register_window.findViewById(R.id.lastname_field);
        EditText patronymic = register_window.findViewById(R.id.patronymic_field);
        EditText age = register_window.findViewById(R.id.age_field);
        EditText phone = register_window.findViewById(R.id.phone_field);
        EditText login = register_window.findViewById(R.id.login_field);
        EditText password = register_window.findViewById(R.id.password_field);
        //EditText post = register_window.findViewById(R.id.post_field);
        Spinner post = register_window.findViewById(R.id.post_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.post_spin, android.R.layout.simple_spinner_dropdown_item);
        post.setAdapter(adapter);
        EditText divizion = register_window.findViewById(R.id.divizion_field);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(TextUtils.isEmpty(name.getText().toString())) {
                    Snackbar.make(root, "Введите ваше имя", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(lastname.getText().toString())) {
                    Snackbar.make(root, "Введите вашу фамилию", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(patronymic.getText().toString())) {
                    Snackbar.make(root, "Введите ваше отчетсво", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(age.getText().toString())) {
                    Snackbar.make(root, "Введите ваш возраст", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(phone.getText().toString())) {
                    Snackbar.make(root, "Введите ваш номер телефона", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(login.getText().toString())) {
                    Snackbar.make(root, "Введите логин для личного кабинета", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(password.getText().toString().length() < 5) {
                    Snackbar.make(root, "Введите пароль, который больше 5 символов", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(divizion.getText().toString())) {
                    Snackbar.make(root, "Введите ваш дивизион", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //Регистрация
                auth.createUserWithEmailAndPassword(login.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                User user = new User();
                                user.setName(name.getText().toString());
                                user.setLastname(lastname.getText().toString());
                                user.setPatronymic(patronymic.getText().toString());
                                user.setAge(age.getText().toString());
                                user.setPhone(phone.getText().toString());
                                user.setLogin(login.getText().toString());
                                user.setPassword(password.getText().toString());
                                user.setPost(post.getSelectedItem().toString());
                                user.setDivizion(divizion.getText().toString());

                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()) //ключ при регистрации
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Snackbar.make(root, "Пользователь добавлен", Snackbar.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
            }
        });

        dialog.show();
    }
}