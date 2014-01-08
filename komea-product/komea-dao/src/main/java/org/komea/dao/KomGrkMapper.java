package org.komea.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.komea.model.KomGrk;
import org.komea.model.KomGrkExample;

public interface KomGrkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_grk
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    int countByExample(KomGrkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_grk
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    int deleteByExample(KomGrkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_grk
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    int deleteByPrimaryKey(Integer idGroupKind);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_grk
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    int insert(KomGrk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_grk
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    int insertSelective(KomGrk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_grk
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    List<KomGrk> selectByExample(KomGrkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_grk
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    KomGrk selectByPrimaryKey(Integer idGroupKind);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_grk
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    int updateByExampleSelective(@Param("record") KomGrk record, @Param("example") KomGrkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_grk
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    int updateByExample(@Param("record") KomGrk record, @Param("example") KomGrkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_grk
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    int updateByPrimaryKeySelective(KomGrk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_grk
     *
     * @mbggenerated Wed Jan 08 15:34:10 CET 2014
     */
    int updateByPrimaryKey(KomGrk record);
}