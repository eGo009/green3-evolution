/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.action;

import com.green3.evolution.action.DBAction;
import com.green3.evolution.game.model.GameBoard;
import com.green3.evolution.model.CommonEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 *
 * @author Alex_Ihnatsiuck
 */
public class NewGameAction extends DBAction{
    
    
    
    @Override
    public CommonEntity doAction(Connection connection){
        try{
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
        }
        return null;
    }
}
