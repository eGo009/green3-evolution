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
public class LossTailAction extends GameAction{
    
    
    
    @Override
    public CommonEntity doAction(Connection connection, Map<String,Object> params){
        String playerId = (String)params.get(GameConstants.PARAM_PLAYER_ID);
        String propertyId = (String)params.get(GameConstants.PARAM_PROPERTY_ID);    
        String animalId = (String)params.get(GameConstants.PARAM_ANIMAL_ID);   
        String linkedAnimalId = (String)params.get(GameConstants.PARAM_LINKED_ANIMAL_ID);
        int gameId = (Integer)params.get(GameConstants.PARAM_GAME_ID);
        getGameManager().lossTail(gameId, Integer.valueOf(playerId), Integer.valueOf(animalId), Integer.valueOf(linkedAnimalId), Integer.valueOf(propertyId), connection);
        return null;
    }
}
