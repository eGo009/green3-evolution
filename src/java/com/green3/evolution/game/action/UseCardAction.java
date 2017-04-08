/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.action;

import com.green3.evolution.action.Action;
import com.green3.evolution.model.CommonEntity;
/**
 *
 * @author Alex_Ihnatsiuck
 */
public class UseCardAction implements Action{
    
    @Override
    public CommonEntity execute(){
        System.out.println("Use card");
        return null;
    }
}
