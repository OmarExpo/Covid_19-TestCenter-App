package com.example.covid19.Repository;

import com.example.covid19.Model.Appointment;
import com.example.covid19.Model.TimeSlots;
import com.example.covid19.Model.User;

import java.util.List;

public interface RepoInterface {
    public List<User> fetchAllUser();
    public User addUser(User user);
    public int updateTestCenterId(String cpr,int tcID);
    public List<User> fetchCpr();
    public List<Appointment> fetchAllAppointments();
    public void addAppointment(String cpr,int tcID,String day,String month,String year,String hour,String minute);
    public List<TimeSlots> fetchAllTimeSlots();

}
