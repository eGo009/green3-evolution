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
public class PassFeedingAction extends GameAction{
    
    
    
    @Override
    public CommonEntity doAction(Connection connection, Map<String,Object> params){
        String playerIdStr = (String)params.get(GameConstants.PARAM_PLAYER_ID);
        int playerId = Integer.valueOf(playerIdStr);
        getGameManager().passStageTurn(connection, playerId);
        getGameManager().activatePlayer(playerId, connection);
        return null;
    }
}
