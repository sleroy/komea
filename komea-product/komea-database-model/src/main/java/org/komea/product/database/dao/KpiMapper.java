package org.komea.product.database.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;

public interface KpiMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    int countByExample(KpiCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    int deleteByExample(KpiCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    int insert(Kpi record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    int insertSelective(Kpi record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    List<Kpi> selectByExampleWithBLOBs(KpiCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    List<Kpi> selectByExample(KpiCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    Kpi selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    int updateByExampleSelective(@Param("record") Kpi record, @Param("example") KpiCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    int updateByExampleWithBLOBs(@Param("record") Kpi record, @Param("example") KpiCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    int updateByExample(@Param("record") Kpi record, @Param("example") KpiCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    int updateByPrimaryKeySelective(Kpi record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    int updateByPrimaryKeyWithBLOBs(Kpi record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Mon Jan 13 11:27:31 CET 2014
     */
    int updateByPrimaryKey(Kpi record);
}