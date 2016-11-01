/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import SQL.tableAlumnos;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Liam-Portatil
 */
@WebServlet(name = "NewServlet", urlPatterns = {"/NewServlet"})
public class NewServlet extends HttpServlet {

    /**
     * Gets SQL students for initial state
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //SQL object that gets students
        tableAlumnos a = new tableAlumnos();
        String[][] alumnos = null;
        try {
            alumnos = a.getSelect();
        } catch (SQLException ex) {
            Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        /**
         * Charges Array with all of the students
         */
        JSONArray objArray = new JSONArray();
        String json = "[";
        for (int c = 0; c < alumnos.length; c++) {
            objArray = new JSONArray();
            for (int con = 0; con < alumnos[c].length; con++) {
                objArray.add(alumnos[c][con]);
            }
            json += objArray + ",";
        }
        json = json.substring(0, json.length() - 1);
        json += "]";

        /**
         * Sends Json parameter
         */
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().print(json);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Gets selected value from options POST
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str = null;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }

        //Converts value to a suitable int that represents SQL index
        String name = sb.toString();
        name = name.replaceAll("\\D+", "");
        int index = 0;
        try {
            index = Integer.parseInt(name);
        } catch (Exception ex) {
            index = -1;
        }
        index++;

        //Charges SQL information from the selected value
        tableAlumnos a = new tableAlumnos();
        String[][] asignaturas = null;
        String[][] tutorias = null;
        try {
            asignaturas = a.getAsignaturas(index + "");
            tutorias = a.getTutorias(index + "");
        } catch (SQLException ex) {
            Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Creates null Object to insert values from SQL
        JSONObject json = new JSONObject();
        json.put("name", asignaturas[0][0]);

        JSONObject jsonAsignaturas = new JSONObject();
        for (int con = 0; con < asignaturas.length; con++) {
            jsonAsignaturas.put("a" + con, asignaturas[con][1]);
        }

        JSONObject jsonTutorias = new JSONObject();
        for (int con = 0; con < tutorias.length; con++) {
            jsonTutorias.put("t" + con, tutorias[con][2]);
        }

        JSONArray all = new JSONArray();
        all.add(json);
        all.add(jsonAsignaturas);
        all.add(jsonTutorias);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(all);

    }

}
