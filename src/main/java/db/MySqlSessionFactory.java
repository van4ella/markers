package db;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

class MySqlSessionFactory {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        String resource = "MyBatisConfig.xml";
        InputStream inputStream;

        try {
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static SqlSessionFactory getSqlSessionFactory(){
        return sqlSessionFactory;
    }
}