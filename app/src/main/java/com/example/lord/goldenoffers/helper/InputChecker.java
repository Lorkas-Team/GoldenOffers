package com.example.lord.goldenoffers.helper;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputChecker {

    private static final String REGEX_NAME = "^[A-Za-z0-9._ -]{4,30}$";
    private static final String REGEX_DESCRIPTION = "^[A-Za-z0-9,._ -]+$";
    private static final String REGEX_EMAIL = "^[A-Za-z0-9._-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$";
    private static final String REGEX_PASSWORD = "^[A-Za-z0-9.!@#$&-]{6,20}$";
    private static final String REGEX_AFM = "^[0-9]{9}$";
    private static final String REGEX_PRICE = "^[0-9]{1,4}([.][0-9]{1,3})?$";
    private static final String REGEX_DATE = "^20[0-9]{2}-[0-1][0-9]-[0-3][0-9]$";

    private static boolean isInputValid(String regexPattern, String strInput) {
        CharSequence chSeqInput = strInput;
        Pattern pattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(chSeqInput);
        return matcher.matches();
    }

    public static List<Object> isUserRegisterInputValid(
            String username, String email, String strPassword, String strPasswordRepeat) {

        boolean error = false;
        String msgError = null;
        String unvalidInput = null;

        if(username.isEmpty()) {
            error = true;
            msgError = "Username field is empty.";
            unvalidInput = "username";
        } else if (!isInputValid(REGEX_NAME, username)) {
            error = true;
            msgError = "Username must be between 4 and 15 characters.\nAllowed symbols : ._ -";
            unvalidInput = "username";
        } else if (email.isEmpty()) {
            error = true;
            msgError = "Email field is empty.";
            unvalidInput = "email";
        } else if (!isInputValid(REGEX_EMAIL,email)) {
            error = true;
            msgError = "Not valid Email.\nEmail example : name-lastname@host.com";
            unvalidInput = "email";
        } else if(strPassword.isEmpty() || strPasswordRepeat.isEmpty()) {
            error = true;
            msgError = "Password field is empty.";
            unvalidInput = "password";
        } else if (!isInputValid(REGEX_PASSWORD, strPassword) || !isInputValid(REGEX_PASSWORD, strPasswordRepeat)) {
            error = true;
            msgError = "Password must be between 6 and 20 characters.\nAllowed symbols : .!@#$&-";
            unvalidInput = "password";
        } else if (!strPassword.equals(strPasswordRepeat)) {
            error = true;
            msgError = "Passwords must be the same.";
            unvalidInput = "password";
        }

        List<Object> response = new ArrayList<>();
        response.add(error);
        response.add(msgError);
        response.add(unvalidInput);
        return response;
    }

    public static List<Object> isBusinessRegisterInputValid(
            String name, String email, String strPassword,
            String strPasswordRepeat, String owner, String strAFM) {

        boolean error = false;
        String msgError = null;
        String unvalidInput = null;

        if(email.isEmpty()) {
            error = true;
            msgError = "Email field is empty.";
            unvalidInput = "email";
        } else if(!isInputValid(REGEX_EMAIL, email)) {
            error = true;
            msgError = "Not valid Email.\nEmail example : name-lastname@host.com";
            unvalidInput = "email";
        } else if(strPassword.isEmpty() || strPasswordRepeat.isEmpty()) {
            error = true;
            msgError = "Password field is empty.";
            unvalidInput = "password";
        } else if (!isInputValid(REGEX_PASSWORD, strPassword) || !isInputValid(REGEX_PASSWORD, strPasswordRepeat)) {
            error = true;
            msgError = "Password must be between 6 and 20 characters.\nAllowed symbols : .!@#$&-";
            unvalidInput = "password";
        } else if (!strPassword.equals(strPasswordRepeat)) {
            error = true;
            msgError = "Passwords must be the same.";
            unvalidInput = "password";
        } else if(name.isEmpty()) {
            error = true;
            msgError = "Name field is empty.";
            unvalidInput = "name";
        } else if (!isInputValid(REGEX_NAME, name)) {
            error = true;
            msgError = "Name must be between 4 and 15 characters.\nAllowed symbols : ._ -";
            unvalidInput = "name";
        } else if(owner.isEmpty()) {
            error = true;
            msgError = "Owner field is empty.";
            unvalidInput = "owner";
        } else if(!isInputValid(REGEX_NAME, owner)) {
            error = true;
            msgError = "Owner must be between 4 and 15 characters.\nAllowed symbols : ._ -";
            unvalidInput = "owner";
        } else if(strAFM.isEmpty()) {
            error = true;
            msgError = "AFM field is empty.";
            unvalidInput = "afm";
        } else if(!isInputValid(REGEX_AFM, strAFM)) {
            error = true;
            msgError = "AFM must be 9 numbers.";
            unvalidInput = "afm";
        }

        List<Object> response = new ArrayList<>();
        response.add(error);
        response.add(msgError);
        response.add(unvalidInput);
        return response;
    }

    public static List<Object> isLoginInputValid(String email, String strPassword) {

        boolean error = false;
        String msgError = null;
        String unvalidInput = null;

        if (email.isEmpty()) {
            error = true;
            msgError = "Email field is empty.";
            unvalidInput = "email";
        } else if (!isInputValid(REGEX_EMAIL, email)) {
            error = true;
            msgError = "Not valid Email.\nEmail example : name-lastname@host.com";
            unvalidInput = "email";
        } else if(strPassword.isEmpty()) {
            error = true;
            msgError = "Password field is empty.";
            unvalidInput = "password";
        } else if (!isInputValid(REGEX_PASSWORD, strPassword)) {
            error = true;
            msgError = "Password must be between 6 and 20 characters.\nAllowed symbols : .!@#$&-";
            unvalidInput = "password";
        }

        List<Object> response = new ArrayList<>();
        response.add(error);
        response.add(msgError);
        response.add(unvalidInput);
        return response;
    }

    public static List<Object> isAddDesireInputValid(String nameDesire, String strPriceLow, String strPriceHigh) {

        boolean error = false;
        String msgError = null;
        String unvalidInput = null;

        if (nameDesire.isEmpty()) {
            error = true;
            msgError = "Desires Name field is empty.";
            unvalidInput = "name";
        } else if (!isInputValid(REGEX_NAME, nameDesire)) {
            error = true;
            msgError = "Desire Name must be between 4 and 15 characters.\nAllowed symbols : ._ -";
            unvalidInput = "name";
        } else if(strPriceLow.isEmpty()) {
            error = true;
            msgError = "Price Low field is empty.";
            unvalidInput = "price_low";
        } else if(strPriceHigh.isEmpty()) {
            error = true;
            msgError = "Price High field is empty.";
            unvalidInput = "price_high";
        } else if((Integer.parseInt(strPriceLow) <= 0 || Integer.parseInt(strPriceHigh) <= 0)) {
            error = true;
            msgError = "Price must be greater than 0.";
            unvalidInput = "both_prices";
        } else if (!isInputValid(REGEX_PRICE, strPriceLow)) {
            error = true;
            msgError = "Price must be number.";
            unvalidInput = "price_low";
        } else if (!isInputValid(REGEX_PRICE, strPriceHigh)) {
            error = true;
            msgError = "Price must be number.";
            unvalidInput = "price_high";
        } else if (Float.parseFloat(strPriceLow) >= Float.parseFloat(strPriceHigh)) {
            error = true;
            msgError = "Price High must be GREATER than Price Low.";
            unvalidInput = "price_high";
        }

        List<Object> response = new ArrayList<>();
        response.add(error);
        response.add(msgError);
        response.add(unvalidInput);
        return response;
    }

    public static List<Object> isAddOfferInputValid(String productName, String strPrice, String strRegistDate, String strExpDate, String description) {

        boolean error = false;
        String msgError = null;
        String unvalidInput = null;

        if (productName.isEmpty()) {
            error = true;
            msgError = "Product Name field is empty.";
            unvalidInput = "name";
        } else if (!isInputValid(REGEX_NAME, productName)) {
            error = true;
            msgError = "Product Name must be between 4 and 15 characters.\nAllowed symbols : ._ -";
            unvalidInput = "name";
        } else if(strRegistDate.isEmpty()) {
            error = true;
            msgError = "Registration Date field is empty.";
            unvalidInput = "reg_date";
        } else if (!isInputValid(REGEX_DATE, strRegistDate)) {
            error = true;
            msgError = "Not valid Date.";
            unvalidInput = "reg_date";
        } else if(strExpDate.isEmpty()) {
            error = true;
            msgError = "Exp Date field is empty.";
            unvalidInput = "exp_date";
        } else if (!isInputValid(REGEX_DATE, strExpDate)) {
            error = true;
            msgError = "Not valid Date.";
            unvalidInput = "exp_date";
        } else if(strPrice.isEmpty()) {
            error = true;
            msgError = "Price field is empty.";
            unvalidInput = "price";
        } else if((Integer.parseInt(strPrice) <= 0)) {
            error = true;
            msgError = "Price must be greater than 0.";
            unvalidInput = "price";
        } else if (!isInputValid(REGEX_PRICE, strPrice)) {
            error = true;
            msgError = "Price must be number.";
            unvalidInput = "price";
        } else if(!description.isEmpty() && !isInputValid(REGEX_DESCRIPTION, description)) {
            error = true;
            msgError = "Description contains not allowed symbols.\nAllowed symbols : ._ -";
            unvalidInput = "description";
        } else if (isRegDateAfterExpDate(strRegistDate, strExpDate)) {
            error = true;
            msgError = "Exp Date must be AFTER Registration Date.";
            unvalidInput = "both_dates";
        }

        List<Object> response = new ArrayList<>();
        response.add(error);
        response.add(msgError);
        response.add(unvalidInput);
        return response;
    }

    private static boolean isRegDateAfterExpDate(String strRegistDate, String strExpDate) {

        String[] tokenReg = strRegistDate.split("-");
        int yearReg = Integer.parseInt(tokenReg[0]);
        int monthReg = Integer.parseInt(tokenReg[1]);
        int dayReg = Integer.parseInt(tokenReg[2]);

        String[] tokenExp = strExpDate.split("-");
        int yearExp = Integer.parseInt(tokenExp[0]);
        int monthExp = Integer.parseInt(tokenExp[1]);
        int dayExp = Integer.parseInt(tokenExp[2]);
        return ((yearReg > yearExp) || ((monthReg > monthExp) && (yearReg == yearExp))) ||
                ((yearReg == yearExp) && (monthReg == monthExp) && (dayReg >= dayExp));
    }
}
