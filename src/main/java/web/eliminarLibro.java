package web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.Conexion;

@WebServlet(name = "eliminarLibro", urlPatterns = {"/eliminarLibro"})
public class eliminarLibro extends HttpServlet {

    /**
     * Handles the HTTP <code>DELETE</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        String id = request.getParameter("id");
        System.out.println("Id : " + id);
        try ( PrintWriter out = response.getWriter()) {
            String lista = new Conexion().eliminarLibro(id);
            System.out.println("LIsta - eliminar: " + lista);
            out.println(lista);
            out.close();
        }

    }

}
