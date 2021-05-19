package com.example.covid19.Repository;

import com.example.covid19.Model.Appointment;
import com.example.covid19.Model.TestCenter;
import com.example.covid19.Model.TimeSlots;
import com.example.covid19.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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

        String sql = "Insert into User(name,userName,password,cpr,phone,tcID,vcID,tsID,vsID,vacNameID ) values(?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getName(), user.getUserName(), user.getPassword(), user.getCpr(), user.getPhone(),user.getTcID(),user.getVcID(),user.getTsID(),user.getVsID(),user.getVacNameID());
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
    public void addAppointment(String cpr, int tcID, LocalDateTime dateTime) {
        String sql = "Insert into Appointment(cpr,tcID,localDateTime) values(?,?,?)";
        jdbcTemplate.update(sql, cpr,tcID,dateTime);
    }




    /*
    public void addAppointment(String cpr,int tcID,String day,String month,String year,String hour,String minute) {
        String sql = "Insert into Appointment(cpr,tcID,day,month,year,hour,minute) values(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, cpr,tcID, day, month, year, hour, minute);
    }
*/
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


    public User updateUser(User user) {
        String sqlQuery = "update User set " +
                "name = ?, userName = ?,password = ?,cpr = ?, phone= ?, tcID = ?, vcID = ?, tsID = ?, vsID = ?, vacNameID = ? "+
                "where userID = ?";
        jdbcTemplate.update(sqlQuery
                , user.getName()
                , user.getUserName()
                , user.getPassword()
                , user.getCpr()
                , user.getPhone()
                ,user.getTcID()
                ,user.getVcID()
                ,user.getTsID()
                ,user.getVsID()
                ,user.getVacNameID()
                ,user.getUserID());
        return user;
    }
    public User fetchSingleUser(int id) {
        String sql = "Select * from User where userID = ?";
        return this.jdbcTemplate.queryForObject(sql, new RowMapper<User>(){
            @Override
            public User mapRow(ResultSet rs, int rownum) throws SQLException
            {
                User user = new User();
                user.setUserID(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setUserName(rs.getString(3));
                user.setPassword(rs.getString(4));
                user.setCpr(rs.getString(5));
                user.setPhone(rs.getString(6));
                user.setTcID(rs.getInt(7));
                user.setVcID(rs.getInt(8));
                user.setTsID(rs.getInt(9));
                user.setVsID(rs.getInt(10));
                user.setVacNameID(rs.getInt(11));
                return user;
            }},id); }



    public boolean deleteUser(int id) {

        String querry = "DELETE FROM User WHERE userID = ?";
        jdbcTemplate.update(querry, id);
        return true;
    }
    public boolean deleteAppointment(long cpr) {
        String sql = "DELETE FROM Appointment where cpr = ?";
        jdbcTemplate.update(sql, cpr);
        return true;
    }

    @Override
    public List<TestCenter> fetchTestCenter() {
        String sql = "SELECT * FROM TestCenter";
        RowMapper<TestCenter> rowMapper2 = new BeanPropertyRowMapper<>(TestCenter.class);
        return jdbcTemplate.query(sql, rowMapper2);
    }

    @Override
    public TestCenter fetchTestCenterByCpr(long cpr) {
        String sql = "SELECT tcID FROM Appointment Where cpr = ?";
        return this.jdbcTemplate.queryForObject(sql, new RowMapper<TestCenter>(){
            @Override
            public TestCenter mapRow(ResultSet rs, int rownum) throws SQLException
            {
                TestCenter tc = new TestCenter();
                tc.setTcID(rs.getInt(1));
                tc.setCname(rs.getString(2));
                return tc;
            }},cpr);}
@Override
    public List<User> searchByName(String keyword){
        String sql = "SELECT * FROM User WHERE name=?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return jdbcTemplate.query(sql,rowMapper,keyword);
    }
    public List<User> fetchAllPositive() {
        String sql = "Select * from User where tsID = 1";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<User> fetchAllByCpr(long cpr) {
        String sql = "SELECT * FROM User WHERE cpr=?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return jdbcTemplate.query(sql,rowMapper,cpr);
    }

    public List<User> fetchAllNegative() {
        String sql = "Select * from User where tsID = 2";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

}
