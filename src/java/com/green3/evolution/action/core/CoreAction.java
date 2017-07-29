/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.action.core;

import com.green3.evolution.action.DBAction;
import com.green3.evolution.action.core.manager.CoreManager;

/**
 *
 * @author Alex_Ihnatsiuck
 */
public abstract class CoreAction extends DBAction{
    
    private CoreManager coreManager = new CoreManager();
    
    protected CoreManager getCoreManager(){
        return coreManager;
    }
}
