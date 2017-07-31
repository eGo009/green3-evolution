/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.action;

import com.green3.evolution.game.GameConstants;
import com.green3.evolution.model.CommonEntity;

import java.sql.Connection;
import java.util.Map;
/**
 *
 * @author Alex_Ihnatsiuck
 */
public class JoinGameAction extends GameAction{
    
    
    
    @Override
    public CommonEntity doAction(Connection connection, Map<String,Object> params){
        String userId = (String)params.get(GameConstants.PARAM_USER_ID);
        String gameId = (String)params.get(GameConstants.PARAM_GAME_ID);        
        getGameManager().addPlayerToGame(connection, Integer.valueOf(gameId), userId);
        return null;
    }
}
