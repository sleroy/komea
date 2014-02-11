package org.komea.product.database.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.KpiAlertTypeCriteria;

public interface KpiAlertTypeDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    int countByCriteria(KpiAlertTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    int deleteByCriteria(KpiAlertTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    int insert(KpiAlertType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    int insertSelective(KpiAlertType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    List<KpiAlertType> selectByCriteriaWithRowbounds(KpiAlertTypeCriteria example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    List<KpiAlertType> selectByCriteria(KpiAlertTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    KpiAlertType selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    int updateByCriteriaSelective(@Param("record") KpiAlertType record, @Param("example") KpiAlertTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    int updateByCriteria(@Param("record") KpiAlertType record, @Param("example") KpiAlertTypeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    int updateByPrimaryKeySelective(KpiAlertType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Tue Feb 11 16:06:30 CET 2014
     */
    int updateByPrimaryKey(KpiAlertType record);
}