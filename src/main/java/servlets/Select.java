package servlets;

import db.FeatureDAO;
import entity.Feature;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Evgeniy Evzerov on 25.05.17.
 * VIstar
 */
public class Select extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        FeatureDAO featureDAO = new FeatureDAO();
        List<Feature> features = featureDAO.select();

        JSONObject jsonString = new JSONObject();

        for (Feature feature : features) {
            jsonString.put(feature.getFeatureId().toString(), feature.getFeatureCoordinates());
        }

        resp.setContentType("application/json");
        resp.getWriter().write(jsonString.toJSONString());
    }
}
