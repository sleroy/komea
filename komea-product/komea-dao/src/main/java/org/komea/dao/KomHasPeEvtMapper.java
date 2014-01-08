package org.komea.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.komea.model.KomHasPeEvtExample;
import org.komea.model.KomHasPeEvtKey;

public interface KomHasPeEvtMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_pe_evt
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    int countByExample(KomHasPeEvtExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_pe_evt
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    int deleteByExample(KomHasPeEvtExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_pe_evt
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    int deleteByPrimaryKey(KomHasPeEvtKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_pe_evt
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    int insert(KomHasPeEvtKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_pe_evt
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    int insertSelective(KomHasPeEvtKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_pe_evt
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    List<KomHasPeEvtKey> selectByExample(KomHasPeEvtExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_pe_evt
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    int updateByExampleSelective(@Param("record") KomHasPeEvtKey record, @Param("example") KomHasPeEvtExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_pe_evt
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    int updateByExample(@Param("record") KomHasPeEvtKey record, @Param("example") KomHasPeEvtExample example);
}