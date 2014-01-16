package org.komea.product.database.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.komea.product.database.model.HasProjectPersonCriteria;
import org.komea.product.database.model.HasProjectPersonKey;

public interface HasProjectPersonMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_pe
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    int countByExample(HasProjectPersonCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_pe
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    int deleteByExample(HasProjectPersonCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_pe
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    int deleteByPrimaryKey(HasProjectPersonKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_pe
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    int insert(HasProjectPersonKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_pe
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    int insertSelective(HasProjectPersonKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_pe
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    List<HasProjectPersonKey> selectByExample(HasProjectPersonCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_pe
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    int updateByExampleSelective(@Param("record") HasProjectPersonKey record, @Param("example") HasProjectPersonCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_has_proj_pe
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    int updateByExample(@Param("record") HasProjectPersonKey record, @Param("example") HasProjectPersonCriteria example);
}