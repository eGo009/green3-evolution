/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.servlet;

import com.green3.evolution.action.Action;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.green3.evolution.game.GameConstants;
import com.green3.evolution.game.action.GameActionType;
import com.green3.evolution.game.factory.GameActionFactory;
import com.green3.evolution.game.model.GameBoard;
import com.green3.evolution.model.CommonEntity;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Alex_Ihnatsiuck
 */
public class ListCreatedGamesServlet extends GameServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Action action = GameActionFactory.createAction(GameActionType.LIST_CREATED_GAMES);
        if (null==action){
            return;
        }
        Map<String,Object> params = createParamsMap(request, GameActionType.LIST_CREATED_GAMES);
        CommonEntity model = action.execute(params);
        
        request.setAttribute("gameContainer", model);
        getServletContext().getRequestDispatcher("/jsp/hosted-games.jsp").forward(request, response);        
    }
}
