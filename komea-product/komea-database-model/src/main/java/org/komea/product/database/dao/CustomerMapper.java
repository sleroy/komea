package org.komea.product.database.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.CustomerCriteria;

public interface CustomerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_customer
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    int countByExample(CustomerCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_customer
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    int deleteByExample(CustomerCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_customer
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    int deleteByPrimaryKey(Integer idCustomer);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_customer
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    int insert(Customer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_customer
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    int insertSelective(Customer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_customer
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    List<Customer> selectByExample(CustomerCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_customer
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    Customer selectByPrimaryKey(Integer idCustomer);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_customer
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    int updateByExampleSelective(@Param("record") Customer record, @Param("example") CustomerCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_customer
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    int updateByExample(@Param("record") Customer record, @Param("example") CustomerCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_customer
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    int updateByPrimaryKeySelective(Customer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_customer
     *
     * @mbggenerated Thu Jan 09 12:21:15 CET 2014
     */
    int updateByPrimaryKey(Customer record);
}