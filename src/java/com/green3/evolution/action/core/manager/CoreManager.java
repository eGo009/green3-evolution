/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.action.core.manager;

import com.green3.evolution.game.manager.*;
import com.green3.evolution.game.action.*;
import com.green3.evolution.action.DBAction;
import com.green3.evolution.core.model.User;
import com.green3.evolution.game.GameConstants;
import com.green3.evolution.game.model.Card;
import com.green3.evolution.game.model.GameBoard;
import com.green3.evolution.game.model.GamesContainer;
import com.green3.evolution.game.model.Player;
import com.green3.evolution.game.model.Property;
import com.green3.evolution.model.CommonEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex_Ihnatsiuck
 */
public class CoreManager {

    public User getUser(String login, String password, Connection con) {
        User user = new User();
        try {
            PreparedStatement getUserStatement = con.prepareStatement("SELECT u.id, u.email FROM ev_user AS u WHERE u.login=? and u.password=?;");
            getUserStatement.setString(1, login);
            getUserStatement.setString(2, password);
            ResultSet rs = getUserStatement.executeQuery();

            while (rs.next()) {
                user.setId(rs.getInt("id"));
                String email = rs.getString("email");
                user.setEmail(email);
                user.setLogin(login);
                user.setPassword(password);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewGameAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    } 
}
