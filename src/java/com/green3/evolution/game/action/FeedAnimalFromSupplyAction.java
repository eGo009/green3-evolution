/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.action;

import com.green3.evolution.game.GameConstants;
import com.green3.evolution.model.CommonEntity;
import com.green3.evolution.game.model.Error;

import java.sql.Connection;
import java.util.Map;
/**
 *
 * @author Alex_Ihnatsiuck
 */
public class FeedAnimalFromSupplyAction extends GameAction{
        
    
    @Override
    public CommonEntity doAction(Connection connection, Map<String,Object> params){
        int gameId = (Integer)params.get(GameConstants.PARAM_GAME_ID);
        String animalId = (String)params.get(GameConstants.PARAM_ANIMAL_ID);
        String playerId = (String)params.get(GameConstants.PARAM_PLAYER_ID);
 
        boolean success = getGameManager().feedAnimalFromFoodSupply(Integer.valueOf(animalId), Integer.valueOf(playerId), gameId, connection);
        if (!success){
            CommonEntity error = new Error(2);
            return error;
        }
        else{
            getGameManager().checkAndApplyVivaparous(Integer.valueOf(playerId), Integer.valueOf(animalId), connection);
        }
        return null;
    }
}
