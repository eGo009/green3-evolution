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
public class BlockingAttackAction extends GameAction{
        
    
    @Override
    public CommonEntity doAction(Connection connection, Map<String,Object> params){
        String targetAnimalId = (String)params.get(GameConstants.PARAM_ANIMAL_ID);
        String predatorId = (String)params.get(GameConstants.PARAM_LINKED_ANIMAL_ID);
        String playerId = (String)params.get(GameConstants.PARAM_PLAYER_ID);
        String propertyId = (String)params.get(GameConstants.PARAM_PROPERTY_ID);
        int gameId = (Integer)params.get(GameConstants.PARAM_GAME_ID);
 
        boolean success = getGameManager().attackAnimal(gameId, Integer.valueOf(playerId), Integer.valueOf(predatorId), Integer.valueOf(targetAnimalId), false, Integer.valueOf(propertyId), connection);
        if (!success){
            CommonEntity error = new Error(2);
            return error;
        }
        return null;
    }
}
