package servlets;

import db.FeatureDAO;
import entity.Feature;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Update extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer queryId = Integer.parseInt(req.getParameter("idToUpdate"));
        String queryCoordinates = req.getParameter("coordinatesToUpdate");
        FeatureDAO featureDAO = new FeatureDAO();

        Feature feature = new Feature();
        feature.setFeatureId(queryId);
        feature.setFeatureCoordinates(queryCoordinates);

        featureDAO.update(feature);
    }
}