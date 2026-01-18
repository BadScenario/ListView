package com.example.listview;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.listview.models.User;

import java.util.ArrayList;
import java.util.List;


public class MainViewModel extends ViewModel {

    private MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();

    public MainViewModel() {
        usersLiveData.setValue(new ArrayList<>());
    }

    public MutableLiveData<List<User>> getUsers() {
        return usersLiveData;
    }

    public void addUser(User user) {
        List<User> currentList = usersLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
            List<User> newList = new ArrayList<>(currentList);
            newList.add(user);
            usersLiveData.setValue(newList);
    }
    public void removeUser(User user) {
        List<User> currentList = usersLiveData.getValue();
        if (currentList != null) {
            List<User> newList = new ArrayList<>(currentList);
            newList.remove(user);
            usersLiveData.setValue(newList);
        }
    }
}
