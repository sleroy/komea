package org.komea.product.database.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.KpiWithBLOBs;

public interface KpiMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    int countByExample(KpiCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    int deleteByExample(KpiCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    int insert(KpiWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    int insertSelective(KpiWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    List<KpiWithBLOBs> selectByExampleWithBLOBs(KpiCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    List<Kpi> selectByExample(KpiCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    KpiWithBLOBs selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    int updateByExampleSelective(@Param("record") KpiWithBLOBs record, @Param("example") KpiCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    int updateByExampleWithBLOBs(@Param("record") KpiWithBLOBs record, @Param("example") KpiCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    int updateByExample(@Param("record") Kpi record, @Param("example") KpiCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    int updateByPrimaryKeySelective(KpiWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    int updateByPrimaryKeyWithBLOBs(KpiWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpi
     *
     * @mbggenerated Thu Jan 09 19:15:19 CET 2014
     */
    int updateByPrimaryKey(Kpi record);
}