package org.komea.product.eventory.database.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.komea.product.eventory.database.model.AggregationFormula;
import org.komea.product.eventory.database.model.EntityValue;
import org.komea.product.eventory.database.model.EventStorage;
import org.komea.product.eventory.database.model.EventStorageCriteria;
import org.komea.product.eventory.storage.api.IFieldPredicate;

public interface EventStorageDao {
	/**
	 * Aggregation formula
	 *
	 */
	List<EntityValue> aggregateOnPeriod(AggregationFormula formula);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table kom_evt_storage
	 *
	 * @mbggenerated Sun Nov 16 11:22:00 CET 2014
	 */
	int countByCriteria(EventStorageCriteria example);

	/**
	 * Count by the given field
	 *
	 * @param fieldPredicate
	 * @return
	 */
	int countByField(IFieldPredicate fieldPredicate);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table kom_evt_storage
	 *
	 * @mbggenerated Sun Nov 16 11:22:00 CET 2014
	 */
	int deleteByCriteria(EventStorageCriteria example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table kom_evt_storage
	 *
	 * @mbggenerated Sun Nov 16 11:22:00 CET 2014
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table kom_evt_storage
	 *
	 * @mbggenerated Sun Nov 16 11:22:00 CET 2014
	 */
	int insert(EventStorage record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table kom_evt_storage
	 *
	 * @mbggenerated Sun Nov 16 11:22:00 CET 2014
	 */
	int insertSelective(EventStorage record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table kom_evt_storage
	 *
	 * @mbggenerated Sun Nov 16 11:22:00 CET 2014
	 */
	List<EventStorage> selectByCriteria(EventStorageCriteria example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table kom_evt_storage
	 *
	 * @mbggenerated Sun Nov 16 11:22:00 CET 2014
	 */
	List<EventStorage> selectByCriteriaWithRowbounds(
			EventStorageCriteria example, RowBounds rowBounds);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table kom_evt_storage
	 *
	 * @mbggenerated Sun Nov 16 11:22:00 CET 2014
	 */
	EventStorage selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table kom_evt_storage
	 *
	 * @mbggenerated Sun Nov 16 11:22:00 CET 2014
	 */
	int updateByCriteria(@Param("record") EventStorage record,
			@Param("example") EventStorageCriteria example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table kom_evt_storage
	 *
	 * @mbggenerated Sun Nov 16 11:22:00 CET 2014
	 */
	int updateByCriteriaSelective(@Param("record") EventStorage record,
			@Param("example") EventStorageCriteria example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table kom_evt_storage
	 *
	 * @mbggenerated Sun Nov 16 11:22:00 CET 2014
	 */
	int updateByPrimaryKey(EventStorage record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table kom_evt_storage
	 *
	 * @mbggenerated Sun Nov 16 11:22:00 CET 2014
	 */
	int updateByPrimaryKeySelective(EventStorage record);

}