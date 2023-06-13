package com.example.volonter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            Intent intentMain = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intentMain);
        }

        if (item.getItemId() == 1) {
            Intent intentAccount = new Intent(MainActivity.this, Account.class);
            startActivity(intentAccount);
        }

        if (item.getItemId() == 2) {
            Intent intentEvents = new Intent(MainActivity.this, Events.class);
            startActivity(intentEvents);
        }

        if (item.getItemId() == 3) {
            Intent intentSettings = new Intent(MainActivity.this, Settings.class);
            startActivity(intentSettings);
        }

        return super.onOptionsItemSelected(item);
    }
}