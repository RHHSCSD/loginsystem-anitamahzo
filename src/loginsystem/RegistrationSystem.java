/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anita
 */
public class RegistrationSystem {
    private int numOfUsers = 0;
    private final String delimeter = ",";
    
    //constructor
    public RegistrationSystem() {
        
    }

    //setters and getters
    public int getNumOfUsers() {
        return numOfUsers;
    }

    public void setNumOfUsers(int numOfUsers) {
        this.numOfUsers = numOfUsers;
    }
    
    //methods 
    File f = new File("User.txt");
    File pw = new File("dictbadpass.txt");
    
    
    public void signUp(String userFirstName, String userLastName, String user, String passWord, String emailAcc) {
            String encryptedPW = encrypt(passWord);
            boolean userTaken = isUser(user);
            boolean goodPW = checkPwStrength(passWord);
            
            if(userTaken == false && goodPW == true) {
        try {
            PrintWriter pw = new PrintWriter(f);
            pw.println(userFirstName + delimeter + userLastName + delimeter + user + delimeter +  encryptedPW + delimeter + emailAcc);
            pw.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
    } else {
                System.out.println("This username is already taken, or the password is not strong enough. Please try again!");
            }
   
        User newUser = new User(userFirstName, userLastName, user, encryptedPW, emailAcc);
        numOfUsers += 1;
    }
    
    public boolean isUser(String user) {
        boolean isUser = false;
        try {
            Scanner sc = new Scanner(f);
            while(sc.hasNext()) {
                if(sc.next().equals(user)) {
                    System.out.println(sc.next());
                    isUser = true;
                } else {
                isUser = false;
                }
            }
            sc.close();
        } catch (FileNotFoundException ex) {}
        
    return isUser;
    }
    
    public boolean isPW(String pass) {
       boolean isPW = false;
        try {
            Scanner sc = new Scanner(f);
            while(sc.hasNext()) {
                if(sc.next().equals(pass)) {
                    isPW = true;
                } else {
                    isPW = false;
                }
            }
            sc.close();
        } catch (FileNotFoundException ex) {}
     return isPW;
    }
    
    public boolean checkPwStrength(String pass) {
        String password = encrypt(pass);
        boolean goodPW = false;
        try {
            Scanner sc = new Scanner(pw);
            while(sc.hasNext()) {
                String pw = sc.next();
                if(pw.equals(password)) {
                 goodPW = false;
                } else {
                 goodPW = true;
                }
            }
            sc.close();
        } catch (FileNotFoundException ex) {}
        return goodPW;
    }
    
    public String encrypt(String pass) {
        String encryptedPassword="";
        try {
            String password = pass;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            for (int i = 0; i < byteData.length; ++i) {
                encryptedPassword += (Integer.toHexString((byteData[i] & 0xFF) |
                        0x100).substring(1,3));
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RegistrationSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encryptedPassword;
    }
    
    
    public boolean logIn(String user, String pass) {
        String encryptedPass = encrypt(pass);
        boolean goodUser = isUser(user);
        boolean goodPW = isPW(encryptedPass);
        boolean logIn;
        
        if(goodUser == true && goodPW == true) {
            logIn = true;
        } else {
            logIn = false;
            System.out.println("This username and password are not a match. Please try again.");
        }
        return logIn;
    }
    
    public void loadUsers() {
        ArrayList<String> users = new ArrayList<>();
        try {
            Scanner sc = new Scanner(f);
            while(sc.hasNextLine()) {
                users.add(sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
        
        for(int i = 0; i<users.size(); i++) {
            String user = users.get(i);
            System.out.print(user);
            numOfUsers += 1;
        }
    }
}
