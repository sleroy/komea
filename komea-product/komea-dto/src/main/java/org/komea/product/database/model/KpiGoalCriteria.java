package org.komea.product.database.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KpiGoalCriteria {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_kpigoal
     *
     * @mbggenerated Mon May 26 14:58:02 CEST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_kpigoal
     *
     * @mbggenerated Mon May 26 14:58:02 CEST 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_kpigoal
     *
     * @mbggenerated Mon May 26 14:58:02 CEST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpigoal
     *
     * @mbggenerated Mon May 26 14:58:02 CEST 2014
     */
    public KpiGoalCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpigoal
     *
     * @mbggenerated Mon May 26 14:58:02 CEST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpigoal
     *
     * @mbggenerated Mon May 26 14:58:02 CEST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpigoal
     *
     * @mbggenerated Mon May 26 14:58:02 CEST 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpigoal
     *
     * @mbggenerated Mon May 26 14:58:02 CEST 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpigoal
     *
     * @mbggenerated Mon May 26 14:58:02 CEST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpigoal
     *
     * @mbggenerated Mon May 26 14:58:02 CEST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpigoal
     *
     * @mbggenerated Mon May 26 14:58:02 CEST 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpigoal
     *
     * @mbggenerated Mon May 26 14:58:02 CEST 2014
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpigoal
     *
     * @mbggenerated Mon May 26 14:58:02 CEST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpigoal
     *
     * @mbggenerated Mon May 26 14:58:02 CEST 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table kom_kpigoal
     *
     * @mbggenerated Mon May 26 14:58:02 CEST 2014
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdKpiIsNull() {
            addCriterion("idKpi is null");
            return (Criteria) this;
        }

        public Criteria andIdKpiIsNotNull() {
            addCriterion("idKpi is not null");
            return (Criteria) this;
        }

        public Criteria andIdKpiEqualTo(Integer value) {
            addCriterion("idKpi =", value, "idKpi");
            return (Criteria) this;
        }

        public Criteria andIdKpiNotEqualTo(Integer value) {
            addCriterion("idKpi <>", value, "idKpi");
            return (Criteria) this;
        }

        public Criteria andIdKpiGreaterThan(Integer value) {
            addCriterion("idKpi >", value, "idKpi");
            return (Criteria) this;
        }

        public Criteria andIdKpiGreaterThanOrEqualTo(Integer value) {
            addCriterion("idKpi >=", value, "idKpi");
            return (Criteria) this;
        }

        public Criteria andIdKpiLessThan(Integer value) {
            addCriterion("idKpi <", value, "idKpi");
            return (Criteria) this;
        }

        public Criteria andIdKpiLessThanOrEqualTo(Integer value) {
            addCriterion("idKpi <=", value, "idKpi");
            return (Criteria) this;
        }

        public Criteria andIdKpiIn(List<Integer> values) {
            addCriterion("idKpi in", values, "idKpi");
            return (Criteria) this;
        }

        public Criteria andIdKpiNotIn(List<Integer> values) {
            addCriterion("idKpi not in", values, "idKpi");
            return (Criteria) this;
        }

        public Criteria andIdKpiBetween(Integer value1, Integer value2) {
            addCriterion("idKpi between", value1, value2, "idKpi");
            return (Criteria) this;
        }

        public Criteria andIdKpiNotBetween(Integer value1, Integer value2) {
            addCriterion("idKpi not between", value1, value2, "idKpi");
            return (Criteria) this;
        }

        public Criteria andEntityIDIsNull() {
            addCriterion("entityID is null");
            return (Criteria) this;
        }

        public Criteria andEntityIDIsNotNull() {
            addCriterion("entityID is not null");
            return (Criteria) this;
        }

        public Criteria andEntityIDEqualTo(Integer value) {
            addCriterion("entityID =", value, "entityID");
            return (Criteria) this;
        }

        public Criteria andEntityIDNotEqualTo(Integer value) {
            addCriterion("entityID <>", value, "entityID");
            return (Criteria) this;
        }

        public Criteria andEntityIDGreaterThan(Integer value) {
            addCriterion("entityID >", value, "entityID");
            return (Criteria) this;
        }

        public Criteria andEntityIDGreaterThanOrEqualTo(Integer value) {
            addCriterion("entityID >=", value, "entityID");
            return (Criteria) this;
        }

        public Criteria andEntityIDLessThan(Integer value) {
            addCriterion("entityID <", value, "entityID");
            return (Criteria) this;
        }

        public Criteria andEntityIDLessThanOrEqualTo(Integer value) {
            addCriterion("entityID <=", value, "entityID");
            return (Criteria) this;
        }

        public Criteria andEntityIDIn(List<Integer> values) {
            addCriterion("entityID in", values, "entityID");
            return (Criteria) this;
        }

        public Criteria andEntityIDNotIn(List<Integer> values) {
            addCriterion("entityID not in", values, "entityID");
            return (Criteria) this;
        }

        public Criteria andEntityIDBetween(Integer value1, Integer value2) {
            addCriterion("entityID between", value1, value2, "entityID");
            return (Criteria) this;
        }

        public Criteria andEntityIDNotBetween(Integer value1, Integer value2) {
            addCriterion("entityID not between", value1, value2, "entityID");
            return (Criteria) this;
        }

        public Criteria andUntilDateIsNull() {
            addCriterion("untilDate is null");
            return (Criteria) this;
        }

        public Criteria andUntilDateIsNotNull() {
            addCriterion("untilDate is not null");
            return (Criteria) this;
        }

        public Criteria andUntilDateEqualTo(Date value) {
            addCriterion("untilDate =", value, "untilDate");
            return (Criteria) this;
        }

        public Criteria andUntilDateNotEqualTo(Date value) {
            addCriterion("untilDate <>", value, "untilDate");
            return (Criteria) this;
        }

        public Criteria andUntilDateGreaterThan(Date value) {
            addCriterion("untilDate >", value, "untilDate");
            return (Criteria) this;
        }

        public Criteria andUntilDateGreaterThanOrEqualTo(Date value) {
            addCriterion("untilDate >=", value, "untilDate");
            return (Criteria) this;
        }

        public Criteria andUntilDateLessThan(Date value) {
            addCriterion("untilDate <", value, "untilDate");
            return (Criteria) this;
        }

        public Criteria andUntilDateLessThanOrEqualTo(Date value) {
            addCriterion("untilDate <=", value, "untilDate");
            return (Criteria) this;
        }

        public Criteria andUntilDateIn(List<Date> values) {
            addCriterion("untilDate in", values, "untilDate");
            return (Criteria) this;
        }

        public Criteria andUntilDateNotIn(List<Date> values) {
            addCriterion("untilDate not in", values, "untilDate");
            return (Criteria) this;
        }

        public Criteria andUntilDateBetween(Date value1, Date value2) {
            addCriterion("untilDate between", value1, value2, "untilDate");
            return (Criteria) this;
        }

        public Criteria andUntilDateNotBetween(Date value1, Date value2) {
            addCriterion("untilDate not between", value1, value2, "untilDate");
            return (Criteria) this;
        }

        public Criteria andValueIsNull() {
            addCriterion("value is null");
            return (Criteria) this;
        }

        public Criteria andValueIsNotNull() {
            addCriterion("value is not null");
            return (Criteria) this;
        }

        public Criteria andValueEqualTo(Double value) {
            addCriterion("value =", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotEqualTo(Double value) {
            addCriterion("value <>", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueGreaterThan(Double value) {
            addCriterion("value >", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueGreaterThanOrEqualTo(Double value) {
            addCriterion("value >=", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueLessThan(Double value) {
            addCriterion("value <", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueLessThanOrEqualTo(Double value) {
            addCriterion("value <=", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueIn(List<Double> values) {
            addCriterion("value in", values, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotIn(List<Double> values) {
            addCriterion("value not in", values, "value");
            return (Criteria) this;
        }

        public Criteria andValueBetween(Double value1, Double value2) {
            addCriterion("value between", value1, value2, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotBetween(Double value1, Double value2) {
            addCriterion("value not between", value1, value2, "value");
            return (Criteria) this;
        }

        public Criteria andFrequencyIsNull() {
            addCriterion("frequency is null");
            return (Criteria) this;
        }

        public Criteria andFrequencyIsNotNull() {
            addCriterion("frequency is not null");
            return (Criteria) this;
        }

        public Criteria andFrequencyEqualTo(String value) {
            addCriterion("frequency =", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyNotEqualTo(String value) {
            addCriterion("frequency <>", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyGreaterThan(String value) {
            addCriterion("frequency >", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyGreaterThanOrEqualTo(String value) {
            addCriterion("frequency >=", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyLessThan(String value) {
            addCriterion("frequency <", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyLessThanOrEqualTo(String value) {
            addCriterion("frequency <=", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyLike(String value) {
            addCriterion("frequency like", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyNotLike(String value) {
            addCriterion("frequency not like", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyIn(List<String> values) {
            addCriterion("frequency in", values, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyNotIn(List<String> values) {
            addCriterion("frequency not in", values, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyBetween(String value1, String value2) {
            addCriterion("frequency between", value1, value2, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyNotBetween(String value1, String value2) {
            addCriterion("frequency not between", value1, value2, "frequency");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table kom_kpigoal
     *
     * @mbggenerated do_not_delete_during_merge Mon May 26 14:58:02 CEST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table kom_kpigoal
     *
     * @mbggenerated Mon May 26 14:58:02 CEST 2014
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}