/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.action;

import com.green3.evolution.action.Action;
import com.green3.evolution.game.model.GameBoard;
import com.green3.evolution.model.CommonEntity;

import java.sql.Connection;
import java.sql.DriverManager;
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
public abstract class DBAction implements Action{
    
       
    @Override
    public final CommonEntity execute(Map<String,Object> params){
        registerDBDriver();
        
        Connection connection = getConnection();
        CommonEntity result = doAction(connection, params);
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private void registerDBDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
        }
        catch (SQLException e) {
            throw new IllegalStateException("Cannot register the driver!", e);
        }
    }

    private Connection getConnection() {
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/evolutiondb";
        String username = "root";
        String password = "vitautas4";
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return connection;
    }

    public abstract CommonEntity doAction(Connection connection, Map<String,Object> params);    
}
