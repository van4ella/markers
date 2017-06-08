package db;

import entity.Feature;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

public class FeatureDAO {

    public void insert(Feature feature) {
        SqlSession sqlSession = MySqlSessionFactory.getSqlSessionFactory().openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);

        mapper.insertFeature(feature);
        sqlSession.commit();
        sqlSession.close();
    }

    public void delete(int featureId) {
        SqlSession sqlSession = MySqlSessionFactory.getSqlSessionFactory().openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);

        mapper.deleteFeature(featureId);
        sqlSession.commit();
        sqlSession.close();
    }

    public void update(Feature feature) {
        SqlSession sqlSession = MySqlSessionFactory.getSqlSessionFactory().openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);

        mapper.updateFeature(feature);
        sqlSession.commit();
        sqlSession.close();
    }

    public List<Feature> select() {
        SqlSession sqlSession = MySqlSessionFactory.getSqlSessionFactory().openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);

        List<Feature> queryResult = mapper.selectFeatures();

        sqlSession.close();
        return queryResult;
    }

}