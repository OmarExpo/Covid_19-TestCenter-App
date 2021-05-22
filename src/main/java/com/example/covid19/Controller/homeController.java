package com.example.covid19.Controller;

import com.example.covid19.Model.*;
import com.example.covid19.Repository.RepoInterface;
import com.example.covid19.Service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
public class homeController {
    String TestCenterName;
    String UserName;
    int tcID=0;
    String cpr1="598358948935";
    User currentUser;
    String testStatus;
    String currentAppointment;
    String userAppointment = "";


    @Autowired
    ServiceInterface serviceInterface;



    @GetMapping("/")
    public String showDashboard() throws IOException{

        List<Appointment> allAppointments = serviceInterface.fetchAllAppointments();
        Date myDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(myDate);
        String month = strDate.substring(3,5);
        String day = strDate.substring(0, 2);

        saveLogs(myDate.toString() +"\n"+getIpAddress());

        for(int i=0;i<allAppointments.size();i++) {
            String day1 = allAppointments.get(i).getLocalDateTime().toString().substring(8, 10);
            String month1 = allAppointments.get(i).getLocalDateTime().toString().substring(5, 7);


            if ((Integer.parseInt(day1) < Integer.parseInt(day)) && (Integer.parseInt(month) <= Integer.parseInt(month1))){
                long cpr = Long.parseLong(allAppointments.get(i).getCpr());
                serviceInterface.deleteAppointment(cpr);


            }
        }




        return "home/dashboard";
    }

    @GetMapping("/userByrole")
    public String showUserByrole(){
        return "home/userByrole";
    }

    @GetMapping("/chooseTestCenter")
    public String showChooseTestCenter(){
        return "home/chooseTestCenter";
    }




    @GetMapping("/makeAppointment")
    public String showMakeAppointment(Model model){
        List<TimeSlots> mytimeSlots = serviceInterface.fetchAllTimeSlots();
        List<TestCenter> myCenters = serviceInterface.fetchTestCenter();
        List<Appointment> allAppointments = serviceInterface.fetchAllAppointments();
       String appointmentDetails="";


        for(int i =0;i<allAppointments.size();i++){
            if(allAppointments.get(i).getCpr().equals(currentUser.getCpr())){
                appointmentDetails = "You have an appointment on  " + allAppointments.get(i).getLocalDateTime().toString();
            }
        }


        model.addAttribute("allAppointments",allAppointments);
        model.addAttribute("appointmentDetails",appointmentDetails);
        model.addAttribute("timeSlots",mytimeSlots);
        model.addAttribute("myCenters",myCenters);
        model.addAttribute("mytestcenter",TestCenterName);
        return "home/makeAppointment";
    }
    @GetMapping("/getCoronaPass")
    public String showCoronaPass(Model model) {
        List<User> userList = serviceInterface.fetchAllUser();
        if (currentUser.getTsID() == 2) {

            Date today = new Date();
            Date afterTomorrow = new Date(System.currentTimeMillis() + 86400 * 1000 * 2);
            model.addAttribute("today", today);
            model.addAttribute("afterTomorrow", afterTomorrow);
            model.addAttribute("userList", userList);
            model.addAttribute("myUser", currentUser);
            model.addAttribute("testStatus", testStatus);
            return "home/getCoronaPass";
        } else if (currentUser.getTsID() == 1) {
            model.addAttribute("haMessage", "Message from health authorities: \nYour status is positive please get isolated and contact your own doctor.");
            return "home/index";
        } else {
            model.addAttribute("haMessage", "Message from health authorities: \nYou are not tested yet please make a test to get corona pass");
            return "home/index";
        }

    }



    @GetMapping("/signUp")
    public String showSignUp(){
        return "home/signUp";
    }
    @GetMapping("/gdpr")
    public String showGdpr(){
        return "home/gdpr";
    }
    @GetMapping("/index")
    public String showIndex(Model model){
        List<Appointment> allAppointments = serviceInterface.fetchAllAppointments();
        List<User> allUsers = serviceInterface.fetchAllUser();
        String appointmentDetails="";
        int  testStatusID=0;
        int vaccinNameID=0;
        String recentCpr = "";
        String testCenterMessage = "";
        String vaccinCenterMessage = "";

        for(int i =0;i<allAppointments.size();i++){
            if(allAppointments.get(i).getCpr().equals(currentUser.getCpr())){
                appointmentDetails = "You have an appointment on  " + allAppointments.get(i).getLocalDateTime().toString();
                recentCpr = currentUser.getCpr();
            }
        }
        for(int i=0;i<allUsers.size();i++){
            if(allUsers.get(i).getCpr().equals(recentCpr)){
                testStatusID = allUsers.get(i).getTsID();
                vaccinNameID = allUsers.get(i).getVacNameID();
            }

        }
        switch (testStatusID){
            case 0: testCenterMessage = "You are not yet tested or your test result is not ready";
                break;
            case 1 : testCenterMessage = "Your test status is positive.";
                break;
            default: testCenterMessage = "Your test status is negative ";
                break;

        }
        switch (vaccinNameID){
            case 0: vaccinCenterMessage = "You are not called for vaccination";
                break;
            case 1 : vaccinCenterMessage = "you are vaccinated 1st time";
                break;
            default: vaccinCenterMessage= "Your vaccination is completed";
                break;

        }
        model.addAttribute("currentUser",currentUser.getUserName());
        model.addAttribute("testCenterMessage",testCenterMessage);
        model.addAttribute("vaccinCenterMessage",vaccinCenterMessage);
       model.addAttribute("userAppointment",userAppointment);
        model.addAttribute("TestCenterName",TestCenterName);
        return "home/index";
    }
    @GetMapping("/login")
    public String showlogin(){
        return "home/login";
    }
    @PostMapping("/user")
    public ModelAndView login(@ModelAttribute LoginCheck loginCheck, Model model) {

        if ((loginCheck.getUserName().equals("admin")) && (loginCheck.getPassword().equals("admin123"))) {
            ModelAndView modelAndView = new ModelAndView("administrator/administratorDash");

            return modelAndView;

        } else if ((loginCheck.getUserName().equals("secretary")) && (loginCheck.getPassword().equals("secretary123"))) {
            ModelAndView modelAndView = new ModelAndView("secretary/secretaryDash");

            return modelAndView;
        } else if ((loginCheck.getUserName().equals("secretaryI")) && (loginCheck.getPassword().equals("si123"))) {
            ModelAndView modelAndView = new ModelAndView("infectionCenter/secretaryIdash");

            return modelAndView;
        }

        else if (logIncheck1(loginCheck.getUserName(), (loginCheck.getPassword()))) {
            System.out.println(logIncheck1(loginCheck.getUserName(), (loginCheck.getPassword())));
            ModelAndView modelAndView = new ModelAndView("home/chooseTestCenter");
            UserName = loginCheck.getUserName();
            String vennueName = TestCenterName;
            //this.repoInterface.updateTestCenterId(cpr1,tcID);

           model.addAttribute("UserName",UserName);
           model.addAttribute("vennueName",vennueName);
           model.addAttribute("currentAppointment",currentAppointment);
            return modelAndView;
        } else {
            ModelAndView modelAndView1 = new ModelAndView("home/dashboard");

            return modelAndView1;
        }
    }

    @PostMapping("/createList")
    public String create(@ModelAttribute User user,Model model) {
        String error = "";

       List<User> userList =serviceInterface.fetchAllUser();
       for(int i=0;i<userList.size();i++){
           if(userList.get(i).getCpr().equals(user.getCpr())){
               error = "This cpr is already in use, please veryfi cpr number if it is correct you have already signedUp kindly go to login page.";
              model.addAttribute("error",error) ;
              return"home/signup";
           }

           if(userList.get(i).getName().equals(user.getName())){
               error = "This name is already in use, please add any charecter after your name";
               model.addAttribute("error",error) ;
               return"home/signup";
           }
           if(userList.get(i).getPassword().equals(user.getPassword())){
               error = "This password  is not approved by our system, please provide diffrent password";
               model.addAttribute("error",error) ;
               return"home/signup";
           }

           if(user.getCpr().length()!=10){
               error = "please provide valid cpr number without '-' 0101804949 (not valid -->010180-4949)";
               model.addAttribute("error",error) ;
               return"home/signup";
           }
           if(userList.get(i).getUserName().equals((user.getUserName()))){
               error = "This username is  already taken by someone else, please enter different username";
               model.addAttribute("error",error) ;
               return"home/signup";
           }
           if(user.getUserName().length()> 30){
               error ="you have choosen a very long name please give a shorter name arround 30 character";
           }
       }

        this.serviceInterface.addUser(user);

        return "home/login";
    }
    @PostMapping("/testCenterName")
    public String testme(@ModelAttribute TestCenter testCenter, Model model) {
        TestCenterName = testCenter.getCname();
        System.out.println(TestCenterName);
       if(TestCenterName.equals("CPH-Center")){
           tcID =1;
           serviceInterface.updateTestCenterId(currentUser.getCpr(),tcID);
       }
        else if(TestCenterName.equals("CPH-North")){
            tcID =2;
           serviceInterface.updateTestCenterId(currentUser.getCpr(),tcID);
        }
       else if(TestCenterName.equals("CPH-South")){
           tcID =3;
           serviceInterface.updateTestCenterId(currentUser.getCpr(),tcID);
       }
       else if(TestCenterName.equals("CPH-East")){
           tcID =4;
           serviceInterface.updateTestCenterId(currentUser.getCpr(),tcID);
       }
       else{
           tcID = 5;
           serviceInterface.updateTestCenterId(currentUser.getCpr(),tcID);
       }

        List<User> allUsers = serviceInterface.fetchAllUser();
        List<Appointment> allAppointments = serviceInterface.fetchAllAppointments();
        int  testStatusID=0;
        int vaccinNameID=0;
        String appointmentTestCenterName="";
        int aTcID = 0;
        String recentCpr = "";
        String testCenterMessage = "";
        String vaccinCenterMessage = "";
        for(int i = 0;i<allAppointments.size();i++){
           if (allAppointments.get(i).getCpr().equals(currentUser.getCpr())){

               aTcID = allAppointments.get(i).getTcID();
               switch (aTcID){
                   case 1: appointmentTestCenterName = "CPH-Center";
                   break;
                   case 2: appointmentTestCenterName ="CPH-North";
                   break;
                   case 3: appointmentTestCenterName = "CPH-South";
                   break;
                   case 4: appointmentTestCenterName = "CPH-East";
                   break;
                   case 5: appointmentTestCenterName = "CPH-West";
                   break;
                   default:appointmentTestCenterName ="not available please contact secretary office";
                   break;

               }



               userAppointment =  allAppointments.get(i).getLocalDateTime().toString() +"Test center";
           }
       }

        for(int i=0;i<allUsers.size();i++){
            if(allUsers.get(i).getCpr().equals(recentCpr)){
                testStatusID = allUsers.get(i).getTsID();
                vaccinNameID = allUsers.get(i).getVacNameID();
            }

        }
        switch (testStatusID){
            case 0: testCenterMessage = "You are not yet tested or your test result is not ready";
                break;
            case 1 : testCenterMessage = "Your test status is positive.";
                break;
            default: testCenterMessage = "Your test status is negative ";
                break;

        }
        switch (vaccinNameID){
            case 0: vaccinCenterMessage = "You are not called for vaccination";
                break;
            case 1 : vaccinCenterMessage = "you are vaccinated 1st time";
                break;
            default: vaccinCenterMessage= "Your vaccination is completed";
                break;

        }
        model.addAttribute("appointmentTestCenterName",appointmentTestCenterName);
        model.addAttribute("testCenterMessage",testCenterMessage);
        model.addAttribute("vaccinCenterMessage",vaccinCenterMessage);
        model.addAttribute("userAppointment",userAppointment);
       model.addAttribute("TestCenterName",TestCenterName);
       model.addAttribute("currentUser",currentUser.getUserName());

        return "home/index";
    }

@GetMapping("/takeDate")
public String getDateTime(@ModelAttribute DateAndTime dt, Model model) {

    String cpr = dt.getCpr();
    Date dateM = dt.getMydate();
    String timeM = dt.getTime();
    model.addAttribute("dateM",dateM);
    model.addAttribute("timeM",timeM);
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    String strDate = formatter.format(dateM);
    String day = strDate.substring(0, 2);
    String month = strDate.substring(3, 5);
    String year = strDate.substring(6);
    String hour = timeM.substring(0, 2);
    String minute = timeM.substring(3);
    String receivedDate = year+"-"+month+"-"+day+" "+hour+":"+minute+":00";

    String str = (year +"-"+month+"-"+day+" "+hour+":"+minute);
    DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime dateTime = LocalDateTime.parse(str,dtf);

    List<Appointment> appList = serviceInterface.fetchAllAppointments();
    model.addAttribute("appList",appList);
    String cprError = "You have already an appointment on";
    String timeMatch = "This time has already been taken by someone else please take another time. ";

    for (int i = 0; i < appList.size(); i++) {
        if (appList.get(i).getCpr().equals(cpr1)) {
            model.addAttribute("cprError", cprError);
            currentAppointment = " "+appList.get(i).getLocalDateTime().toString()+" ";
            model.addAttribute("bookedAppointment",currentAppointment);
            return "home/makeAppointment";

        } else if (appList.get(i).getLocalDateTime().toString().equals(receivedDate) && appList.get(i).getTcID()== tcID) {
            model.addAttribute("timeMatch", timeMatch);
            return "home/makeAppointment";
        } else {
            continue;
        } }

    serviceInterface.addAppointment(cpr1,tcID,dateTime);
    return "home/makeAppointment";
}
    @GetMapping("/DirectAppointment")
    public String makeDirectAppointment(@ModelAttribute DateAndTime dt, Model model) {

        String cpr = dt.getCpr();
        Date dateM = dt.getMydate();
        String timeM = dt.getTime();
        int tcID = dt.getTcID();
        model.addAttribute("dateM",dateM);
        model.addAttribute("timeM",timeM);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(dateM);
        String day = strDate.substring(0, 2);
        String month = strDate.substring(3, 5);
        String year = strDate.substring(6);
        String hour = timeM.substring(0, 2);
        String minute = timeM.substring(3);
        String receivedDate = year+"-"+month+"-"+day+" "+hour+":"+minute+":00";
        String str = (year +"-"+month+"-"+day+" "+hour+":"+minute);
        DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str,dtf);
        List<Appointment> appList = serviceInterface.fetchAllAppointments();
        model.addAttribute("appList",appList);
        String cprError = "You have already an appointment on";
        String timeMatch = "This time has already been taken by someone else please take another time. ";

        for (int i = 0; i < appList.size(); i++) {
            if (appList.get(i).getCpr().equals(cpr)) {
                model.addAttribute("cprError", cprError);
                currentAppointment = appList.get(i).getLocalDateTime().toString();
                //currentAppointment = appList.get(i).getDay()+"-"+appList.get(i).getMonth()+"-"+appList.get(i).getYear()+" at "+appList.get(i).getHour()+":"+ appList.get(i).getMinute();
                model.addAttribute("bookedAppointment",currentAppointment);
                return "secretary/makeDirectAppointment";

            } else if (appList.get(i).getLocalDateTime().toString().equals(receivedDate) && appList.get(i).getTcID()== tcID) {
                model.addAttribute("timeMatch", timeMatch);
                return "secretary/makeDirectAppointment";
            } else {
                continue;
            } }

        serviceInterface.addAppointment(cpr,tcID,dateTime);
        return "secretary/secretaryDash";
    }





    public boolean logIncheck1(String userName, String password) {
        boolean correct = false;
        List<User> userList = serviceInterface.fetchAllUser();
        for (int i = 0; i < userList.size(); i++) {
            if ((userList.get(i).getUserName().equals(userName)) && (userList.get(i).getPassword().equals(password))) {
                correct = true;
                cpr1 = userList.get(i).getCpr();
                currentUser = userList.get(i);
                if(currentUser.getTsID()==0){
                    testStatus = "Not tested";
                }
                if(currentUser.getTsID()==1){
                    testStatus = "Positive";
                }
                if(currentUser.getTsID()==2){
                    testStatus = "Negative";
                }

                break;
            } else {
                correct = false;
            }
        }
        return correct;
    }


    @GetMapping("/makeDirectAppointment")
    public String showDirectAppointment(Model model) {
        List<TimeSlots> mytimeSlots = serviceInterface.fetchAllTimeSlots();
        model.addAttribute("timeSlots",mytimeSlots);
        return "secretary/makeDirectAppointment";
    }

    @GetMapping("/delete/{id}")
    public String deleteMe(@PathVariable(value = "id") int id) {
        List<Appointment> appList = serviceInterface.fetchAllAppointments();
        User user = serviceInterface.fetchSingleUser(id);
        long cprA = Long.parseLong(user.getCpr());

        for(int i=0;i<appList.size();i++){

            if(appList.get(i).getCpr().equals(user.getCpr())){

                serviceInterface.deleteAppointment(cprA);
            }
        }
        serviceInterface.deleteUser(id);

        return ("redirect:/deleteUser");
    }


    @GetMapping("/deleteUser")
    public String showDeleteUser(Model model) {
        List<User> userList = serviceInterface.fetchAllUser();
        model.addAttribute("userList",userList);
        return "administrator/deleteUser";
    }

    @GetMapping("/UpdateUserHome")
    public String showUserUpdateHome(Model model) {
        List<User> userList = serviceInterface.fetchAllUser();
        model.addAttribute("userList",userList);
        return "secretary/UpdateUserHome";
    }


    @GetMapping("/updateHome/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model md) {
        User user = serviceInterface.fetchSingleUser(id);
        md.addAttribute("user", user);
        return "secretary/userUpdatePage";
    }

    @PostMapping("/updateHome")
    public String saveEmployee(@ModelAttribute("user") User user) {
        this.serviceInterface.updateUser(user);
        return "redirect:/UpdateUserHome";
    }
    @GetMapping("/infectionDash")
    public String showInfectionDash(){
        return "infectionCenter/infectionDash";
    }

    @GetMapping("/searchByName")
    public String searchByName(@ModelAttribute User val, Model model) {
        String name = val.getName();
        List<User> searchList = serviceInterface.searchByName(name);
        model.addAttribute("searchList", searchList);
        return "secretary/searchByName";
    }
    @GetMapping("/searchByPositive")
    public String searchByPositive(@ModelAttribute User val, Model model) {
        List<User> positiveList = serviceInterface.fetchAllPositive();
        model.addAttribute("positiveList", positiveList);
        return "secretary/searchByName";
    }
    @GetMapping("/searchByNegative")
    public String searchByNegative(@ModelAttribute User val, Model model) {
        List<User> negativeList = serviceInterface.fetchAllNegative();
        model.addAttribute("negativeList", negativeList);
        return "secretary/searchByName";
    }

    @GetMapping("/secretaryDash")
    public String showSecretaryDash() {

        return "secretary/secretaryDash";
    }
    @GetMapping("/searchByCpr")
    public String searchByCpr(@ModelAttribute User val, Model model) {
        long cprL = Long.parseLong(val.getCpr());
        List<User> searchListCpr = serviceInterface.fetchAllByCpr(cprL);
        model.addAttribute("searchListCpr", searchListCpr);
        return "secretary/searchByName";
    }

// write log(date,time and ip of the local device ) into log.txt file
    public void saveLogs(String myLogs) throws IOException {
       try {
           File file = new File("log.txt");
           BufferedWriter out = new BufferedWriter(new FileWriter(file));
           out.write(myLogs);
           out.close();
       }catch (IOException e){
           e.printStackTrace();
       }
    }

    // source of inspiration https://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java

public String getIpAddress() throws UnknownHostException {
    InetAddress IP= InetAddress.getLocalHost();
    String ip ="Local ip address of user machine := "+IP.getHostAddress();
        return ip;
}



}
