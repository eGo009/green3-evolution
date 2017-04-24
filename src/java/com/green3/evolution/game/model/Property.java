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
public class Property implements Serializable, CommonEntity{
    
    private int id;
    private int type;
    private String description;
    
    public void setId(int pId){
        id = pId;
    }

    public int getId(){
        return id;
    } 
    
    public void setType(int pType){
        type = pType;
    }

    public int getType(){
        return type;
    }
    
    public void setDescription(String pDescription){
        description = pDescription;
    }

    public String getDescription(){
        return description;
    }
    
}
