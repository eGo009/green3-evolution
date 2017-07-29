/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.action.core;

import com.green3.evolution.core.model.User;
import com.green3.evolution.game.CoreConstants;
import com.green3.evolution.model.CommonEntity;
import java.sql.Connection;
import java.util.Map;
/**
 *
 * @author Alex_Ihnatsiuck
 */
public class LoginAction extends CoreAction{
    
    @Override
    public CommonEntity doAction(Connection con, Map<String,Object> params){
        String login = (String)params.get(CoreConstants.PARAM_USER_LOGIN);
        String password = (String)params.get(CoreConstants.PARAM_USER_PASSWORD);
        User user = getCoreManager().getUser(login, password, con);
        
        return user;
    }
}
