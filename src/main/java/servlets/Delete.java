package servlets;

import db.FeatureDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Delete extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("idToDelete");
        FeatureDAO featureDAO = new FeatureDAO();

        featureDAO.delete(Integer.parseInt(query));
    }
}
