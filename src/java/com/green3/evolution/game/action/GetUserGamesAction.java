/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.action;

import com.green3.evolution.action.DBAction;
import com.green3.evolution.game.GameConstants;
import com.green3.evolution.game.model.GameBoard;
import com.green3.evolution.game.model.GamesContainer;
import com.green3.evolution.model.CommonEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Alex_Ihnatsiuck
 */
public class GetUserGamesAction extends DBAction{
    
    
    
    @Override
    public CommonEntity doAction(Connection connection, Map<String,Object> params){
        String userId = (String)params.get(GameConstants.PARAM_USER_ID);
        GamesContainer games = new GamesContainer();
        try{
            PreparedStatement getGamesByUserStatement = connection.prepareStatement("SELECT g.id, g.state FROM ev_g_player AS p \n" +
"JOIN ev_g_game AS g ON p.game_id=g.id\n" +
"WHERE p.user_id=?;");
            getGamesByUserStatement.setString(1, userId);
            ResultSet rs = getGamesByUserStatement.executeQuery();
            
            while (rs.next()) {
                GameBoard game = new GameBoard();
                game.setId(rs.getInt("id"));
                int state = rs.getInt("state");
                game.setStatus(state);
                switch (state){
                    case 0:
                        games.getCreatedGames().add(game);
                        break;
                    case 1:
                        games.getOngoingGames().add(game);
                        break;
                    case 2:
                        games.getFinishedGames().add(game);
                        break;
                }
             }      
        }
        catch (SQLException ex){
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return games;
    }
}
