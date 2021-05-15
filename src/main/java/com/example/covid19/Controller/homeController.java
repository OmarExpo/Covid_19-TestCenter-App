package com.example.covid19.Controller;

import com.example.covid19.Model.*;
import com.example.covid19.Repository.RepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class homeController {
    String TestCenterName;
    String UserName;
    int tcID;
    String cpr1="598358948935";
    User currentUser;
    String testStatus;
    String currentAppointment;


    @Autowired
    RepoInterface repoInterface;



    @GetMapping("/")
    public String showDashboard(){
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
        List<TimeSlots> mytimeSlots = repoInterface.fetchAllTimeSlots();
        model.addAttribute("timeSlots",mytimeSlots);
        model.addAttribute("TestCenterName",TestCenterName);
        return "home/makeAppointment";
    }
    @GetMapping("/getCoronaPass")
    public String showCoronaPass(Model model){
        List<User> userList =repoInterface.fetchAllUser();
        if (currentUser.getTsID()==0){
            return "home/index";
        }
        else {
        Date today = new Date();
        Date afterTomorrow = new Date(System.currentTimeMillis() + 86400 * 1000 * 2);
        model.addAttribute("today",today);
        model.addAttribute("afterTomorrow",afterTomorrow);
        model.addAttribute("userList",userList);
        model.addAttribute("myUser",currentUser);
        model.addAttribute("testStatus",testStatus);


        return "home/getCoronaPass";}

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
       model.addAttribute("currentAppointment",currentAppointment);
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
            ModelAndView modelAndView = new ModelAndView("home/dashboard");

            return modelAndView;

        } else if ((loginCheck.getUserName().equals("secretary")) && (loginCheck.getPassword().equals("secretary123"))) {
            ModelAndView modelAndView = new ModelAndView("home/dashboard");

            return modelAndView;
        } else if (logIncheck1(loginCheck.getUserName(), (loginCheck.getPassword()))) {
            System.out.println(logIncheck1(loginCheck.getUserName(), (loginCheck.getPassword())));
            ModelAndView modelAndView = new ModelAndView("home/index");
            UserName = loginCheck.getUserName();
            String vennueName = TestCenterName;
            this.repoInterface.updateTestCenterId(cpr1,tcID);

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

       List<User> userList =repoInterface.fetchAllUser();
       for(int i=0;i<userList.size();i++){
           if(userList.get(i).getCpr().equals(user.getCpr())){
               error = "This cpr is already in use, please veryfi cpr number if it is correct you have already signedUp kindly go to login page.";
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

        this.repoInterface.addUser(user);

        return "home/login";
    }
    @PostMapping("/testCenterName")
    public String testme(@ModelAttribute TestCenter testCenter, Model model) {
       TestCenterName = testCenter.getCname();
       if(TestCenterName.equals("CPH-Center")){
           tcID =1;
       }
        else if(TestCenterName.equals("CPH-North")){
            tcID =2;
        }
       else if(TestCenterName.equals("CPH-South")){
           tcID =3;
       }
       else if(TestCenterName.equals("CPH-East")){
           tcID =4;
       }
       else{
           tcID = 5;
       }

       model.addAttribute("TestCenterName",TestCenterName);
        return "home/login";
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

        List<Appointment> appList = repoInterface.fetchAllAppointments();
        model.addAttribute("appList",appList);
        String cprError = "You have already an appointment on";
        String timeMatch = "This time has already been taken by someone else please take another time. ";

        for (int i = 0; i < appList.size(); i++) {
            if (appList.get(i).getCpr().equals(cpr1)) {
                model.addAttribute("cprError", cprError);
                currentAppointment = appList.get(i).getDay()+"-"+appList.get(i).getMonth()+"-"+appList.get(i).getYear()+" at "+appList.get(i).getHour()+":"+ appList.get(i).getMinute();
                model.addAttribute("bookedAppointment",currentAppointment);
                return "home/makeAppointment";

            } else if (appList.get(i).getDay().equals(day) && appList.get(i).getHour().equals(hour) && appList.get(i).getMinute().equals(minute) && appList.get(i).getTcID()== tcID) {
                model.addAttribute("timeMatch", timeMatch);
                return "home/makeAppointment";
            } else {
                continue;
            } }

       repoInterface.addAppointment(cpr1,tcID, day, month, year, hour, minute);
        return "home/makeAppointment";
    }













    public boolean logIncheck1(String userName, String password) {
        boolean correct = false;
        List<User> userList = repoInterface.fetchAllUser();
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
                System.out.println(cpr1);
                /*
                currentUser.setName(userList.get(i).getName());
                currentUser.setCpr(userList.get(i).getCpr());
                currentUser.setPhone(userList.get(i).getPhone());
                currentUser.setStatus(userList.get(i).getStatus());
                currentUser.setAppointment(currentUser.getAppointment()); */
                break;
            } else {
                correct = false;
            }
        }
        return correct;
    }








}
