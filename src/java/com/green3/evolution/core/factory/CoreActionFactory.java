/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.core.factory;


import com.green3.evolution.action.EmptyAction;
import com.green3.evolution.action.Action;
import com.green3.evolution.action.core.CoreActionType;
import com.green3.evolution.action.core.LoginAction;
/**
 *
 * @author Alex_Ihnatsiuck
 */
public class CoreActionFactory {
    
    public static Action createAction(CoreActionType actionType){
        Action action = null;
        switch (actionType){
            case LOGIN: 
                action = new LoginAction();
                break;
            default:
                action = new EmptyAction();
        }
        return action;
    }

}
