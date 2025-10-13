package com.example.listview;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listview.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<User> users = new ArrayList<>();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.titleTB);
        getSupportActionBar().setTitle("Каталог пользователей");

        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, users);
        binding.listUsersLV.setAdapter(adapter);

        binding.saveBTN.setOnClickListener(v -> {
            if (binding.editUserOneET.getText().isEmpty() || binding.editUserSecondET.getText().isEmpty()) {
                Toast.makeText(this, "Введите имя и возраст", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = binding.editUserOneET.getText().toString();
            int age = Integer.parseInt(binding.editUserSecondET.getText().toString());
            users.add(new User(name, age));

            adapter.notifyDataSetChanged();
            binding.editUserOneET.getText().clear();
            binding.editUserSecondET.getText().clear();
        });

        binding.listUsersLV.setOnItemClickListener((parent, view, position, id) -> {
            User user = adapter.getItem(position);
            adapter.remove(user);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.exit) {
            finish();
            Toast.makeText(this, "Программа завершена", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}