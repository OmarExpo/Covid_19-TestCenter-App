package com.example.covid19.Service;

import com.example.covid19.Model.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ServiceInterface {
    public List<User> fetchAllUser();
    public User addUser(User user);
    public int updateTestCenterId(String cpr,int tcID);
    public List<User> fetchCpr();
    public List<Appointment> fetchAllAppointments();
    public void addAppointment(String cpr, int tcID, LocalDateTime dateTime);
    public List<TimeSlots> fetchAllTimeSlots();
    public User fetchSingleUser(int id);
    public User updateUser(User user);
    public boolean deleteUser(int id);
    public boolean deleteAppointment(long cpr);
    public List<TestCenter> fetchTestCenter();
    public TestCenter fetchTestCenterByCpr( long cpr);
    public List<User>searchByName(String keyword);
    public List<User> fetchAllNegative();
    public List<User> fetchAllPositive();
    public List<User> fetchAllByCpr(long cpr);
    public void addDates(String cpr, int tsID, Date testStatusDate, int vsID, Date vaccinStatusDate);
    public void updateTestStatusDate(String cpr, int tsID, Date testStatusDate);
    public void updateVaccinStatusDate(String cpr, int vsID, Date vaccinStatusDate);
    public List<TestStatusDate> fetchAllTestStatusDate();
    public List<Imessage> fetchAllImessage();
    public void addImessage(String cpr,String messageI);
    public boolean deleteMessage(String cpr);
    public List<Vmessage> fetchAllVmessage();
    public void addVmessage(String cpr,String messageV);
    public boolean deleteVmessage(String cpr);
    public boolean deleteTestStatusDate(String cpr);
    public void updateImessage(String cpr,String imessage);
    public void updateVmessage(String cpr, String vmessage);

}
