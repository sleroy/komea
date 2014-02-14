package org.komea.product.database.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.ProjectCriteria;

public interface ProjectDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    int countByCriteria(ProjectCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    int deleteByCriteria(ProjectCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    int insert(Project record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    int insertSelective(Project record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    List<Project> selectByCriteriaWithRowbounds(ProjectCriteria example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    List<Project> selectByCriteria(ProjectCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    Project selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    int updateByCriteriaSelective(@Param("record") Project record, @Param("example") ProjectCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    int updateByCriteria(@Param("record") Project record, @Param("example") ProjectCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    int updateByPrimaryKeySelective(Project record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    int updateByPrimaryKey(Project record);
}