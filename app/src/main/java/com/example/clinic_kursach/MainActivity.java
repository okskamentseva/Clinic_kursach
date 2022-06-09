package com.example.clinic_kursach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView tvHello, tvPolis, tvEmail;
    TextView clinic, doctor, spec, date, time;
    RelativeLayout relativeLayout;
    Button buttonDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        tvHello = findViewById(R.id.textViewHello);
        tvPolis = findViewById(R.id.textViewPolis);
        tvEmail = findViewById(R.id.textViewEmail);

        clinic = findViewById(R.id.clinic);
        doctor = findViewById(R.id.doctor);
        spec = findViewById(R.id.tip);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        relativeLayout = findViewById(R.id.parentRelative);
        buttonDel = findViewById(R.id.buttonDel);

        Button btnExit = findViewById(R.id.buttonExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users").child(currentUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Record record = snapshot.child("record").getValue(Record.class);
                if (user != null) {
                    tvHello.setText("Здравствуйте, " + user.surname + " " + user.name + " " + user.fromfather);
                    tvPolis.setText("Вы зашли под полисом: " + user.polis);
                    tvEmail.setText("Ваша почта: " + user.email);

                    if(record != null) {
                        relativeLayout.setVisibility(View.VISIBLE);
                        clinic.setText(record.getClinic());
                        doctor.setText(record.getDoctor());
                        spec.setText(record.getSpec());
                        date.setText(record.getDate());
                        time.setText(record.getTime());
                        buttonDel.setVisibility(View.VISIBLE);
                        buttonDel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                deleteRecord();
                            }
                        });
                    }else {
                        relativeLayout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        CardView btnBooking = findViewById(R.id.cardBooking);
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booking();
            }
        });

        CardView btnTimetable = findViewById(R.id.cardCalendar);
        btnTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar();
            }
        });
    }

    private void deleteRecord() {
        relativeLayout.setVisibility(View.GONE);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("users").child("record");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot querySnapshot : snapshot.getChildren()) {
                    querySnapshot.getRef().setValue(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Ошибка удаления", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void booking() {
        Intent intent = new Intent(this, BookingActivity.class);
        startActivity(intent);
        finish();
    }

    private void calendar() {
        Intent intent = new Intent(this, TimetableActivity.class);
        startActivity(intent);
        finish();
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}