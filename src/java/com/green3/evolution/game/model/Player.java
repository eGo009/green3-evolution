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
    
    private List<Card> cardsOnHand;
    private List<Animal> animals;
    private int actionOrder;
    
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
    
    
    public void setCardsOnHand(List<Card> pCardsOnHand){
        cardsOnHand = pCardsOnHand;
    }

    public List<Card> getCardsOnHand(){
        return cardsOnHand;
    } 
    
    
    public void setAnimals(List<Animal> pAnimals){
        animals = pAnimals;
    }

    public List<Animal> getAnimals(){
        return animals;
    }

    /**
     * @return the actionOrder
     */
    public int getActionOrder() {
        return actionOrder;
    }

    /**
     * @param actionOrder the actionOrder to set
     */
    public void setActionOrder(int actionOrder) {
        this.actionOrder = actionOrder;
    }
    
    
    
}
