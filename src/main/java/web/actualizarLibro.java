package web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.Conexion;

@WebServlet(name = "actualizarLibro", urlPatterns = {"/actualizarLibro"})
public class actualizarLibro extends HttpServlet {

    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        String id = "", nombre = "", autor = "", fechaPublicacion = "", editorial = "", isbn = "";
        int total = 0;

        id = request.getParameter("id");
        nombre = request.getParameter("nombre");
        autor = request.getParameter("autor");
        fechaPublicacion = request.getParameter("fecha");
        editorial = request.getParameter("editorial");
        isbn = request.getParameter("isbn");
        total = Integer.parseInt(request.getParameter("total"));

        if (isVacio(nombre) || isVacio(autor) || isVacio(fechaPublicacion)
                || isVacio(editorial) || isVacio(isbn)) {
            try ( PrintWriter out = response.getWriter()) {
                response.setContentType("text/plain");
                out.println("Algun parametro vacio");
                return;
            }
        }

        try ( PrintWriter out = response.getWriter()) {
            out.println(new Conexion().actualizarLibro(id, nombre, autor, fechaPublicacion, editorial, isbn, total));
            out.close();
        }

    }

    public boolean isVacio(String str) {
        return str.length() > 0 ? false : true;
    }

}
