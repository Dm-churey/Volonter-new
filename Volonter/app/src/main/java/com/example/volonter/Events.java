package com.example.volonter;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.volonter.ViewHolder.SportViewHolder;

import com.example.volonter.adapters.CategoryAdapter;
import com.example.volonter.adapters.EventAdapter;
import com.example.volonter.model.Category;
import com.example.volonter.model.Event;
import com.example.volonter.model.EventApplication;
import com.example.volonter.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Events extends AppCompatActivity {

    private TextView events_app, events_open_text;
    private Button events_button1, events_button2;
    private ListView list_events;

    FirebaseDatabase db;
    DatabaseReference events, eventsRef, users, register;
    ConstraintLayout element;
    String id;

    private RecyclerView recyclerView, categoryRecycler;
    RecyclerView.LayoutManager layoutManager;
    static CategoryAdapter categoryAdapter;
    SearchView search_event;
    ArrayList<Event> event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        events_app = findViewById(R.id.events_app);
        events_open_text = findViewById(R.id.events_open_text);
        events_button1 = findViewById(R.id.events_button1);
        events_button2 = findViewById(R.id.events_button2);

        element = findViewById(R.id.events_element);;

        //search_event = findViewById(R.id.search_event);

        eventsRef = FirebaseDatabase.getInstance().getReference().child("Event");
        recyclerView = findViewById(R.id.recycler_events);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        db = FirebaseDatabase.getInstance();
        users = db.getReference("User");

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        users.child(userId).child("post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String post = snapshot.getValue(String.class);
                if (post.equals("Волонтер")){
                    events_button1.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Events.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        events_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showActivityNewEvents();
            }
        });

        events_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterEventWindow();
            }
        });

        //Поиск по ивентам
//        search_event.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filter (newText);
//                return true;
//            }
//        });

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "Спортивное"));
        categoryList.add(new Category(2, "Патриотическое"));
        categoryList.add(new Category(3, "Духовное"));
        categoryList.add(new Category(4, "Социальное"));
        categoryList.add(new Category(5, "Культорное"));

        setCategoryRecycler(categoryList);

    }

//    private void filter(String newText) {
//
//         ArrayList<Event> filteredList = new ArrayList<>();
//        for (Event singleEvent : hold) {
//            if (singleEvent.getName_events().toLowerCase().contains(newText.toLowerCase())
//            || singleEvent.getDirection().toLowerCase().contains(newText.toLowerCase())
//            || singleEvent.getOrganizer().toLowerCase().contains(newText.toLowerCase())
//            || singleEvent.getPlace().toLowerCase().contains(newText.toLowerCase())) {
//                filteredList.add(singleEvent);
//            }
//        }
//        AdapterSearch adapterSearch = new AdapterSearch(filteredList);
//        recyclerView.setAdapter(adapterSearch);
//
//    }

    private void setCategoryRecycler(List<Category> categoryList) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        categoryRecycler = findViewById(R.id.categoryRecycler);
        categoryRecycler.setLayoutManager(layoutManager);

        categoryAdapter = new CategoryAdapter(this, categoryList);
        categoryRecycler.setAdapter(categoryAdapter);
    }

//    public static void showEventsByCategory(int category) {
//        List<Event> filterEvents = new ArrayList<>();
//
//        for(Event c: )
//    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Event> options = new FirebaseRecyclerOptions.Builder<Event>()
                .setQuery(eventsRef, Event.class).build();

        FirebaseRecyclerAdapter<Event, SportViewHolder> adapter = new FirebaseRecyclerAdapter<Event, SportViewHolder> (options) {
            @Override
            public SportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
                SportViewHolder holder = new SportViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull SportViewHolder holder, int position,@NonNull Event model) {
                holder.events_item_text.setText(model.getName_events());
                holder.events_item_organizer.setText("Организатор: " + model.getOrganizer());
                holder.events_item_date.setText("Дата: " + model.getData());
                holder.events_item_place.setText("Место: " + model.getPlace());
                holder.events_item_direction.setText("Направление: " + model.getDirection());
//                holder.events_item_button.setOnClickListener(view -> {
//                    Intent intent = new Intent(view.getContext(), event_item.class);
//                    intent.putExtra("event_title", model.getName_events());
//                    intent.putExtra("event_title", model.getOrganizer());
//                    intent.putExtra("event_title", model.getData());
//                    intent.putExtra("event_title", model.getPlace());
//                    intent.putExtra("event_title", model.getDirection());
//                    view.getContext().startActivity(intent);
//                });
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void showRegisterEventWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Зарегистрируйтесь на мероприятие");
        dialog.setMessage("Введите все данные");

        LayoutInflater inflater = LayoutInflater.from(this);
        View register_event_window = inflater.inflate(R.layout.register_event_window, null);
        dialog.setView(register_event_window);

        EditText name = register_event_window.findViewById(R.id.register_event_name);
        EditText fio = register_event_window.findViewById(R.id.register_event_fio);
        EditText mail = register_event_window.findViewById(R.id.register_event_mail);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (TextUtils.isEmpty(name.getText().toString())) {
                    Snackbar.make(element, "Введите название", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(fio.getText().toString())) {
                    Snackbar.make(element, "Введите дату", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mail.getText().toString())) {
                    Snackbar.make(element, "Введите имя организатора", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //Регистрация на мериприятие
                db = FirebaseDatabase.getInstance();
                register = db.getReference("EventApplication");

                String key = register.push().getKey();

                EventApplication application = new EventApplication();
                application.setName_event(name.getText().toString());
                application.setFio(fio.getText().toString());
                application.setMail(mail.getText().toString());
                register.child(key).setValue(application)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Snackbar.make(element,"Заявка отправлена", Snackbar.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        dialog.show();
    }

    private void showActivityNewEvents() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Создайте мероприятие");
        dialog.setMessage("Введите все данные");

        LayoutInflater inflater = LayoutInflater.from(this);
        View activity_new_events = inflater.inflate(R.layout.activity_new_events, null);
        dialog.setView(activity_new_events);

        EditText name = activity_new_events.findViewById(R.id.name_events_field);
        EditText data = activity_new_events.findViewById(R.id.data_field);
        EditText organizer = activity_new_events.findViewById(R.id.organizer_field);
        EditText place = activity_new_events.findViewById(R.id.place_field);
        Spinner direction = activity_new_events.findViewById(R.id.direction_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.napravlenie, android.R.layout.simple_spinner_dropdown_item);
        direction.setAdapter(adapter);

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
                    Snackbar.make(element, "Введите название", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(data.getText().toString())) {
                    Snackbar.make(element, "Введите дату", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(organizer.getText().toString())) {
                    Snackbar.make(element, "Введите имя организатора", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(place.getText().toString())) {
                    Snackbar.make(element, "Введите место проведения", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //Создание нового мероприятия
                db = FirebaseDatabase.getInstance();
                events = db.getReference("Event");

                id = events.push().getKey();

                Event event = new Event();
                event.setName_events(name.getText().toString());
                event.setData(data.getText().toString());
                event.setOrganizer(organizer.getText().toString());
                event.setPlace(place.getText().toString());
                event.setDirection(direction.getSelectedItem().toString());
                events.child(id).setValue(event)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Snackbar.make(element,"Мероприятие добавлено", Snackbar.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        dialog.show();
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
            Intent intentMain = new Intent(Events.this, MainActivity.class);
            startActivity(intentMain);
        }

        if (item.getItemId() == 1) {
            Intent intentAccount = new Intent(Events.this, Account.class);
            startActivity(intentAccount);
        }

        if (item.getItemId() == 2) {
            Intent intentEvents = new Intent(Events.this, Events.class);
            startActivity(intentEvents);
        }

        if (item.getItemId() == 3) {
            Intent intentSettings = new Intent(Events.this, Settings.class);
            startActivity(intentSettings);
        }

        return super.onOptionsItemSelected(item);
    }
}