package org.komea.product.database.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.komea.product.database.model.ProviderSetting;
import org.komea.product.database.model.ProviderSettingCriteria;

public interface ProviderSettingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvds
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    int countByExample(ProviderSettingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvds
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    int deleteByExample(ProviderSettingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvds
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvds
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    int insert(ProviderSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvds
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    int insertSelective(ProviderSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvds
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    List<ProviderSetting> selectByExample(ProviderSettingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvds
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    ProviderSetting selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvds
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    int updateByExampleSelective(@Param("record") ProviderSetting record, @Param("example") ProviderSettingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvds
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    int updateByExample(@Param("record") ProviderSetting record, @Param("example") ProviderSettingCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvds
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    int updateByPrimaryKeySelective(ProviderSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_pvds
     *
     * @mbggenerated Thu Jan 16 12:45:33 CET 2014
     */
    int updateByPrimaryKey(ProviderSetting record);
}