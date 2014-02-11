package org.komea.product.database.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.komea.product.database.model.HasPersonGroupKpiAlertTypeCriteria;
import org.komea.product.database.model.HasPersonGroupKpiAlertTypeKey;

public interface HasPersonGroupKpiAlertTypeDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_kpia_pegr
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    int countByCriteria(HasPersonGroupKpiAlertTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_kpia_pegr
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    int deleteByCriteria(HasPersonGroupKpiAlertTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_kpia_pegr
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    int deleteByPrimaryKey(HasPersonGroupKpiAlertTypeKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_kpia_pegr
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    int insert(HasPersonGroupKpiAlertTypeKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_kpia_pegr
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    int insertSelective(HasPersonGroupKpiAlertTypeKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_kpia_pegr
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    List<HasPersonGroupKpiAlertTypeKey> selectByCriteriaWithRowbounds(HasPersonGroupKpiAlertTypeCriteria example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_kpia_pegr
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    List<HasPersonGroupKpiAlertTypeKey> selectByCriteria(HasPersonGroupKpiAlertTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_kpia_pegr
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    int updateByCriteriaSelective(@Param("record") HasPersonGroupKpiAlertTypeKey record, @Param("example") HasPersonGroupKpiAlertTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_kpia_pegr
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    int updateByCriteria(@Param("record") HasPersonGroupKpiAlertTypeKey record, @Param("example") HasPersonGroupKpiAlertTypeCriteria example);
}