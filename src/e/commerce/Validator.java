/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package e.commerce;
//import javax.swing.*;
/**
 *
 * @author huzai
 */
public class Validator {
    public boolean EmailValidation(String Text){
        return Text.contains("@gmail.com");
    }
    public boolean PasswordValidation(String Text){
       String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{6,}$";
        return Text.matches(regex);
    }
    public boolean ContactValidation(String Text){
        return Text.length()==11;
    }
    public boolean isInteger(String text) {
    try {
        Integer.valueOf(text);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }}
    public boolean isDouble(String text) {
    try {
        Double.valueOf(text);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
}

}
