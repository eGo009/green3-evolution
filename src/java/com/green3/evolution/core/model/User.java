/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.core.model;

import com.green3.evolution.game.model.*;
import com.green3.evolution.model.CommonEntity;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Alex_Ihnatsiuck
 */
public class User implements Serializable, CommonEntity{
    
    private int id;
    private String login;
    private String password;
    private String email;
    
    public void setId(int pId){
        id = pId;
    }

    public int getId(){
        return id;
    } 
    
    
    public void setLogin(String pLogin){
        login = pLogin;
    }

    public String getLogin(){
        return login;
    } 
    
    public void setPassword(String pPassword){
        password = pPassword;
    }

    public String getPassword(){
        return password;
    } 
    
    public void setEmail(String pEmail){
        email = pEmail;
    }

    public String getEmail(){
        return email;
    }    
}
