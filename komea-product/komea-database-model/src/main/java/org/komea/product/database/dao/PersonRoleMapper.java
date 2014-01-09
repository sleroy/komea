package org.komea.product.database.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.PersonRoleCriteria;

public interface PersonRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pero
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    int countByExample(PersonRoleCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pero
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    int deleteByExample(PersonRoleCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pero
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    int deleteByPrimaryKey(Integer idPersonRole);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pero
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    int insert(PersonRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pero
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    int insertSelective(PersonRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pero
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    List<PersonRole> selectByExample(PersonRoleCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pero
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    PersonRole selectByPrimaryKey(Integer idPersonRole);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pero
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    int updateByExampleSelective(@Param("record") PersonRole record, @Param("example") PersonRoleCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pero
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    int updateByExample(@Param("record") PersonRole record, @Param("example") PersonRoleCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pero
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    int updateByPrimaryKeySelective(PersonRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pero
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    int updateByPrimaryKey(PersonRole record);
}