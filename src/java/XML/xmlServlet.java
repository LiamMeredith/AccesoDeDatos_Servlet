/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import Main.NewServlet;
import SQL.tableAlumnos;
import generated.Alumno;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Liam-Portatil
 */
public class xmlServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
        Alumno al = new Alumno();
        //Set name
        al.setName(asignaturas[0][0]);

        //Set subjects
        Alumno.Asignaturas asign = new Alumno.Asignaturas();
        for (int con = 0; con < asignaturas.length; con++) {
                asign.getAsignatura().add(asignaturas[con][1]);
        }

        //Set tutorials
        Alumno.Tutorias tuto = new Alumno.Tutorias();
        for (int con = 0; con < tutorias.length; con++) {
            tuto.getTutoria().add(tutorias[con][2]);
        }

        al.setAsignaturas(asign);
        al.setTutorias(tuto);

        //Charges JAXB Parser
        JAXBinding jb = new JAXBinding();

        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jb.objectToXml(al));

    }
}
