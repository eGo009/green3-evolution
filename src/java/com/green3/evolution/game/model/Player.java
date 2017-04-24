/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.model;

import com.green3.evolution.model.CommonEntity;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Alex_Ihnatsiuck
 */
public class Player implements Serializable, CommonEntity{
    
    private int id;
    private String user;
    
    private List<String> cardsOnHand;
    private List<String> animals; 
    
    public void setId(int pId){
        id = pId;
    }

    public int getId(){
        return id;
    } 
    
    
    public void setUser(String pUser){
        user = pUser;
    }

    public String getUser(){
        return user;
    } 
    
    
    public void setCardsOnHand(List<String> pCardsOnHand){
        cardsOnHand = pCardsOnHand;
    }

    public List<String> getCardsOnHand(){
        return cardsOnHand;
    } 
    
    
    public void setPlayers(List<String> pAnimals){
        animals = pAnimals;
    }

    public List<String> getPlayers(){
        return animals;
    }
    
}
