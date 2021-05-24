package com.example.covid19.Service;

import com.example.covid19.Model.*;
import com.example.covid19.Repository.RepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ServiceImple implements ServiceInterface {
    @Autowired
    RepoInterface repoInterface;

    @Override
    public List<User> fetchAllUser() {

        return repoInterface.fetchAllUser();
    }

    @Override
    public User addUser(User user) {

        return repoInterface.addUser(user);
    }

    @Override
    public int updateTestCenterId(String cpr, int tcID) {
        return repoInterface.updateTestCenterId(cpr, tcID);
    }

    @Override
    public List<User> fetchCpr() {
        return repoInterface.fetchCpr();

    }

    @Override
    public void addAppointment(String cpr, int tcID, LocalDateTime dateTime) {
        repoInterface.addAppointment(cpr, tcID, dateTime);
    }

    @Override
    public List<TimeSlots> fetchAllTimeSlots() {
        return repoInterface.fetchAllTimeSlots();
    }

    @Override
    public List<Appointment> fetchAllAppointments() {
        return repoInterface.fetchAllAppointments();
    }

    @Override
    public User updateUser(User user) {
        return repoInterface.updateUser(user);
    }

    @Override
    public User fetchSingleUser(int id) {
        return repoInterface.fetchSingleUser(id);
    }


    @Override
    public boolean deleteUser(int id) {

        return repoInterface.deleteUser(id);
    }

    @Override
    public boolean deleteAppointment(long cpr) {
        return repoInterface.deleteAppointment(cpr);
    }

    @Override
    public List<TestCenter> fetchTestCenter() {
        return repoInterface.fetchTestCenter();
    }

    @Override
    public TestCenter fetchTestCenterByCpr(long cpr) {
        return repoInterface.fetchTestCenterByCpr(cpr);
    }

    @Override
    public List<User> searchByName(String keyword) {
        return repoInterface.searchByName(keyword);
    }

    @Override
    public List<User> fetchAllPositive() {
        return repoInterface.fetchAllPositive();
    }

    @Override
    public List<User> fetchAllByCpr(long cpr) {
        return repoInterface.fetchAllByCpr(cpr);
    }

    @Override
    public void addDates(String cpr, int tsID, Date testStatusDate, int vsID, Date vaccinStatusDate) {
        repoInterface.addDates(cpr, tsID, testStatusDate, vsID, vaccinStatusDate);
    }

    @Override
    public void updateTestStatusDate(String cpr, Date testStatusDate) {
        repoInterface.updateTestStatusDate(cpr, testStatusDate);
    }

    @Override
    public void updateVaccinStatusDate(String cpr, Date vaccinStatusDate) {
        repoInterface.updateVaccinStatusDate(cpr, vaccinStatusDate);
    }

    @Override
    public List<TestStatusDate> fetchAllTestStatusDate() {
        return repoInterface.fetchAllTestStatusDate();
    }


    @Override
    public List<User> fetchAllNegative() {
        return repoInterface.fetchAllNegative();
    }

}
