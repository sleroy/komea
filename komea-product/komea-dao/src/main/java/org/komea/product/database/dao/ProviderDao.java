package org.komea.product.database.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.komea.product.database.model.Provider;
import org.komea.product.database.model.ProviderCriteria;

public interface ProviderDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvd
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    int countByCriteria(ProviderCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvd
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    int deleteByCriteria(ProviderCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvd
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvd
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    int insert(Provider record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvd
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    int insertSelective(Provider record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvd
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    List<Provider> selectByCriteria(ProviderCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvd
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    Provider selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvd
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    int updateByCriteriaSelective(@Param("record") Provider record, @Param("example") ProviderCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvd
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    int updateByCriteria(@Param("record") Provider record, @Param("example") ProviderCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvd
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    int updateByPrimaryKeySelective(Provider record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvd
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    int updateByPrimaryKey(Provider record);
}