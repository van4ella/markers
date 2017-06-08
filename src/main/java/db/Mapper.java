package db;

import entity.Feature;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface Mapper {

    @Select("SELECT * FROM features")
    List<Feature> selectFeatures();

    @Insert("INSERT INTO features(featureCoordinates) VALUE(#{featureCoordinates})")
    void insertFeature(Feature feature);

    @Delete("DELETE FROM features WHERE featureId = #{featureId}")
    void deleteFeature(int featureId);

    @Update("UPDATE features SET featureCoordinates = #{featureCoordinates} WHERE featureId = #{featureId}")
    void updateFeature(Feature feature);
}