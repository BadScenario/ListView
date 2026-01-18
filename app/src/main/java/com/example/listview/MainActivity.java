package com.example.listview;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.listview.databinding.ActivityMainBinding;
import com.example.listview.models.User;
import com.example.listview.utils.MyDialog;
import com.example.listview.utils.Removable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Removable {

    private ArrayAdapter<User> adapter;
    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.titleTB);
        getSupportActionBar().setTitle("Каталог пользователей");

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, new ArrayList<>());
        binding.listUsersLV.setAdapter(adapter);

        mainViewModel.getUsers().observe(this, users -> {
            adapter.clear();
            if (users != null) {
                adapter.addAll(users);
            }
            adapter.notifyDataSetChanged();
        });

        binding.saveBTN.setOnClickListener(v -> {
            if (binding.editUserOneET.getText().isEmpty() || binding.editUserSecondET.getText().isEmpty()) {
                Toast.makeText(this, "Введите имя и возраст", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = binding.editUserOneET.getText().toString();
            int age = Integer.parseInt(binding.editUserSecondET.getText().toString());
            User user = new User(name, age);

            mainViewModel.addUser(user);

            binding.editUserOneET.getText().clear();
            binding.editUserSecondET.getText().clear();
        });

        binding.listUsersLV.setOnItemClickListener((parent, view, position, id) -> {
            User user = adapter.getItem(position);
            MyDialog dialog = new MyDialog();
            Bundle args = new Bundle();
            args.putSerializable("User", user);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "Custom");
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

    @Override
    public void remove(User user) {
        mainViewModel.removeUser(user);
    }
}