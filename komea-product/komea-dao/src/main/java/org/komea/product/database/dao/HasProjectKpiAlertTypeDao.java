package org.komea.product.database.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.komea.product.database.model.HasProjectKpiAlertTypeCriteria;
import org.komea.product.database.model.HasProjectKpiAlertTypeKey;

public interface HasProjectKpiAlertTypeDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_kpia
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    int countByCriteria(HasProjectKpiAlertTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_kpia
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    int deleteByCriteria(HasProjectKpiAlertTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_kpia
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    int deleteByPrimaryKey(HasProjectKpiAlertTypeKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_kpia
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    int insert(HasProjectKpiAlertTypeKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_kpia
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    int insertSelective(HasProjectKpiAlertTypeKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_kpia
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    List<HasProjectKpiAlertTypeKey> selectByCriteriaWithRowbounds(HasProjectKpiAlertTypeCriteria example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_kpia
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    List<HasProjectKpiAlertTypeKey> selectByCriteria(HasProjectKpiAlertTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_kpia
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    int updateByCriteriaSelective(@Param("record") HasProjectKpiAlertTypeKey record, @Param("example") HasProjectKpiAlertTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_kpia
     *
     * @mbggenerated Wed Feb 19 11:03:52 CET 2014
     */
    int updateByCriteria(@Param("record") HasProjectKpiAlertTypeKey record, @Param("example") HasProjectKpiAlertTypeCriteria example);
}