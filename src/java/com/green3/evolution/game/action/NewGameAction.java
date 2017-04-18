/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.action;

import com.green3.evolution.action.DBAction;
import com.green3.evolution.game.GameConstants;
import com.green3.evolution.game.model.GameBoard;
import com.green3.evolution.model.CommonEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Alex_Ihnatsiuck
 */
public class NewGameAction extends DBAction{
    
    
    
    @Override
    public CommonEntity doAction(Connection connection, Map<String,Object> params){
        int gameId = createNewGame(connection);
        String userId = (String)params.get(GameConstants.PARAM_USER_ID);
        addPlayerToGame(gameId, userId);
        GameBoard game = new GameBoard();
        game.setId(gameId);
       /* try{
            PreparedStatement getCardsStatement = connection.prepareStatement("SELECT c.id, p.type, p.description  FROM ev_g_card AS c \n" +
"  JOIN ev_g_card_property AS cp ON c.id=cp.card_id\n" +
"  JOIN ev_g_property AS p ON cp.property_id=p.ID\n" +
"ORDER BY c.id;");
            ResultSet rs = getCardsStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int type = rs.getInt("type");
                String description = rs.getString("description");
                System.out.println(id + "  " + type+"   "+description);
            }
        }
        catch (SQLException sex){
            throw new IllegalStateException("Cannot get cards from database!", sex);
        }*/
       return game;
    }
    
    private int createNewGame(Connection connection){
        int newGameId = -1;
        try {
            PreparedStatement statement = connection.prepareStatement("insert into ev_g_game (state) VALUES (?);",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,0);
            statement.execute();
            /*int affectedRows = statement.executeUpdate();
            if (affectedRows==0){
                throw new SQLException("Creating user failed, no affected rows.");
            }*/
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                newGameId = generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
        } catch (SQLException ex) {
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newGameId;
    }
    private void addPlayerToGame(int gameId, String userId){
        
    }   
}
