/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.model;

import com.green3.evolution.model.CommonEntity;
import java.io.Serializable;

/**
 *
 * @author Alex_Ihnatsiuck
 */
public class Error implements Serializable, CommonEntity{
    
    private int code;
    
    
    public Error(int pCode){
        code = pCode;
    }
   
    public void setCode(int pCode){
        code = pCode;
    }

    public int getCode(){
        return code;
    }

}
