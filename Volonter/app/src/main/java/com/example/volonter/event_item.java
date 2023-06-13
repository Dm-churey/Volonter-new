//package com.example.volonter;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.volonter.model.Event;
//
//public class event_item extends AppCompatActivity {
//    private Event event;
//
//    private Button events_item_button;
//    private EditText info_name, info_organizer, info_date, info_place, info_direction;
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.event_item);
//
//
//        events_item_button = findViewById(R.id.events_item_button);
//
//        events_item_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showEventInformation();
//            }
//        });
//    }
//
//    private void showEventInformation() {
//
//
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        dialog.setTitle("Информация о мероприятии");
//        dialog.setMessage("Ознакомьтесь");
//
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View event_info = inflater.inflate(R.layout.event_info, null);
//        dialog.setView(event_info);
//
//        EditText name = event_info.findViewById(R.id.info_name);
//        EditText organizer = event_info.findViewById(R.id.info_organizer);
//        EditText date = event_info.findViewById(R.id.info_date);
//        EditText place = event_info.findViewById(R.id.info_place);
//        EditText direction = event_info.findViewById(R.id.info_direction);
//        Bundle extras = getIntent().getExtras();
//        if(extras != null){
//            name.setText((String)extras.get("event_title"));
//            organizer.setText((String)extras.get("event_title"));
//            date.setText((String)extras.get("event_title"));
//            place.setText((String)extras.get("event_title"));
//            direction.setText((String)extras.get("event_title"));
//        }
//
//        name.setText(event.getName_events());
//        organizer.setText(event.getOrganizer());
//        dialog.setNegativeButton("Назад", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//
////        private void eventInfoDisplay(final EditText info_name, final EditText info_organizer,final EditText info_date, final EditText info_place, final EditText info_direction) {
////
////        }
//    }
//}
