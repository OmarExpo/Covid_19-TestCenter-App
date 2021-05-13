package com.example.covid19.Repository;

import com.example.covid19.Model.Appointment;
import com.example.covid19.Model.TimeSlots;
import com.example.covid19.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepoImple implements RepoInterface {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<User> fetchAllUser() {
        String sql = "Select * from User";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    public User addUser(User user) {

        String sql = "Insert into User(name,userName,password,cpr,phone ) values(?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getName(), user.getUserName(), user.getPassword(), user.getCpr(), user.getPhone());
        return null;
    }

    @Override
    public int updateTestCenterId(String cpr, int tcID) {
        String sql = "update User set " +
                "tcID = ? " +
                "where cpr = ?";
        jdbcTemplate.update(sql, tcID, cpr);

        return 0;
    }

    @Override
    public List<User> fetchCpr() {
        String sql = "Select cpr from User";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return jdbcTemplate.query(sql, rowMapper);

    }
    public void addAppointment(String cpr,int tcID,String day,String month,String year,String hour,String minute) {
        String sql = "Insert into Appointment(cpr,tcID,day,month,year,hour,minute) values(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, cpr,tcID, day, month, year, hour, minute);
    }

    @Override
    public List<TimeSlots> fetchAllTimeSlots() {
        String sql = "Select * from TimeSlots";
        RowMapper<TimeSlots> rowMapper1 = new BeanPropertyRowMapper<>(TimeSlots.class);
        return jdbcTemplate.query(sql, rowMapper1);
    }


    public List<Appointment> fetchAllAppointments() {
        String sql = "Select * from Appointment";
        RowMapper<Appointment> rowMapper1 = new BeanPropertyRowMapper<>(Appointment.class);
        return jdbcTemplate.query(sql, rowMapper1);
    }



}
