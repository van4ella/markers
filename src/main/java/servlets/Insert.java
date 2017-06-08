package servlets;

import db.FeatureDAO;
import entity.Feature;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Insert extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("wktCoordinates");
        FeatureDAO featureDAO = new FeatureDAO();

        Feature feature = new Feature();
        feature.setFeatureCoordinates(query);

        featureDAO.insert(feature);
    }
}