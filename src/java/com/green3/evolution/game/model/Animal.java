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
public class Animal implements Serializable, CommonEntity{
    
    private int id;
   
    private List<Property> properties;
    
   
    public void setId(int pId){
        id = pId;
    }

    public int getId(){
        return id;
    }
    
    
    public void setProperties(List<Property> pProperties){
        properties = pProperties;
    }

    public List<Property> getProperties(){
        return properties;
    }
    
  
}
