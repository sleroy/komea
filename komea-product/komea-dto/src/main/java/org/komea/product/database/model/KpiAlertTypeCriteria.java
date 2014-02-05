package org.komea.product.database.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.komea.product.database.enums.Operator;
import org.komea.product.database.enums.Severity;

public class KpiAlertTypeCriteria {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_kpia
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_kpia
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_kpia
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public KpiAlertTypeCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
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
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_kpia
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table kom_kpia
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> severityCriteria;

        protected List<Criterion> operatorCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            severityCriteria = new ArrayList<Criterion>();
            operatorCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getSeverityCriteria() {
            return severityCriteria;
        }

        protected void addSeverityCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            severityCriteria.add(new Criterion(condition, value, "org.apache.ibatis.type.EnumOrdinalTypeHandler"));
            allCriteria = null;
        }

        protected void addSeverityCriterion(String condition, Severity value1, Severity value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            severityCriteria.add(new Criterion(condition, value1, value2, "org.apache.ibatis.type.EnumOrdinalTypeHandler"));
            allCriteria = null;
        }

        public List<Criterion> getOperatorCriteria() {
            return operatorCriteria;
        }

        protected void addOperatorCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            operatorCriteria.add(new Criterion(condition, value, "org.apache.ibatis.type.EnumOrdinalTypeHandler"));
            allCriteria = null;
        }

        protected void addOperatorCriterion(String condition, Operator value1, Operator value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            operatorCriteria.add(new Criterion(condition, value1, value2, "org.apache.ibatis.type.EnumOrdinalTypeHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || severityCriteria.size() > 0
                || operatorCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(severityCriteria);
                allCriteria.addAll(operatorCriteria);
            }
            return allCriteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
            allCriteria = null;
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andKpiAlertKeyIsNull() {
            addCriterion("kpiAlertKey is null");
            return (Criteria) this;
        }

        public Criteria andKpiAlertKeyIsNotNull() {
            addCriterion("kpiAlertKey is not null");
            return (Criteria) this;
        }

        public Criteria andKpiAlertKeyEqualTo(String value) {
            addCriterion("kpiAlertKey =", value, "kpiAlertKey");
            return (Criteria) this;
        }

        public Criteria andKpiAlertKeyNotEqualTo(String value) {
            addCriterion("kpiAlertKey <>", value, "kpiAlertKey");
            return (Criteria) this;
        }

        public Criteria andKpiAlertKeyGreaterThan(String value) {
            addCriterion("kpiAlertKey >", value, "kpiAlertKey");
            return (Criteria) this;
        }

        public Criteria andKpiAlertKeyGreaterThanOrEqualTo(String value) {
            addCriterion("kpiAlertKey >=", value, "kpiAlertKey");
            return (Criteria) this;
        }

        public Criteria andKpiAlertKeyLessThan(String value) {
            addCriterion("kpiAlertKey <", value, "kpiAlertKey");
            return (Criteria) this;
        }

        public Criteria andKpiAlertKeyLessThanOrEqualTo(String value) {
            addCriterion("kpiAlertKey <=", value, "kpiAlertKey");
            return (Criteria) this;
        }

        public Criteria andKpiAlertKeyLike(String value) {
            addCriterion("kpiAlertKey like", value, "kpiAlertKey");
            return (Criteria) this;
        }

        public Criteria andKpiAlertKeyNotLike(String value) {
            addCriterion("kpiAlertKey not like", value, "kpiAlertKey");
            return (Criteria) this;
        }

        public Criteria andKpiAlertKeyIn(List<String> values) {
            addCriterion("kpiAlertKey in", values, "kpiAlertKey");
            return (Criteria) this;
        }

        public Criteria andKpiAlertKeyNotIn(List<String> values) {
            addCriterion("kpiAlertKey not in", values, "kpiAlertKey");
            return (Criteria) this;
        }

        public Criteria andKpiAlertKeyBetween(String value1, String value2) {
            addCriterion("kpiAlertKey between", value1, value2, "kpiAlertKey");
            return (Criteria) this;
        }

        public Criteria andKpiAlertKeyNotBetween(String value1, String value2) {
            addCriterion("kpiAlertKey not between", value1, value2, "kpiAlertKey");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andSeverityIsNull() {
            addCriterion("severity is null");
            return (Criteria) this;
        }

        public Criteria andSeverityIsNotNull() {
            addCriterion("severity is not null");
            return (Criteria) this;
        }

        public Criteria andSeverityEqualTo(Severity value) {
            addSeverityCriterion("severity =", value, "severity");
            return (Criteria) this;
        }

        public Criteria andSeverityNotEqualTo(Severity value) {
            addSeverityCriterion("severity <>", value, "severity");
            return (Criteria) this;
        }

        public Criteria andSeverityGreaterThan(Severity value) {
            addSeverityCriterion("severity >", value, "severity");
            return (Criteria) this;
        }

        public Criteria andSeverityGreaterThanOrEqualTo(Severity value) {
            addSeverityCriterion("severity >=", value, "severity");
            return (Criteria) this;
        }

        public Criteria andSeverityLessThan(Severity value) {
            addSeverityCriterion("severity <", value, "severity");
            return (Criteria) this;
        }

        public Criteria andSeverityLessThanOrEqualTo(Severity value) {
            addSeverityCriterion("severity <=", value, "severity");
            return (Criteria) this;
        }

        public Criteria andSeverityIn(List<Severity> values) {
            addSeverityCriterion("severity in", values, "severity");
            return (Criteria) this;
        }

        public Criteria andSeverityNotIn(List<Severity> values) {
            addSeverityCriterion("severity not in", values, "severity");
            return (Criteria) this;
        }

        public Criteria andSeverityBetween(Severity value1, Severity value2) {
            addSeverityCriterion("severity between", value1, value2, "severity");
            return (Criteria) this;
        }

        public Criteria andSeverityNotBetween(Severity value1, Severity value2) {
            addSeverityCriterion("severity not between", value1, value2, "severity");
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

        public Criteria andAverageSinceIsNull() {
            addCriterion("averageSince is null");
            return (Criteria) this;
        }

        public Criteria andAverageSinceIsNotNull() {
            addCriterion("averageSince is not null");
            return (Criteria) this;
        }

        public Criteria andAverageSinceEqualTo(Date value) {
            addCriterionForJDBCDate("averageSince =", value, "averageSince");
            return (Criteria) this;
        }

        public Criteria andAverageSinceNotEqualTo(Date value) {
            addCriterionForJDBCDate("averageSince <>", value, "averageSince");
            return (Criteria) this;
        }

        public Criteria andAverageSinceGreaterThan(Date value) {
            addCriterionForJDBCDate("averageSince >", value, "averageSince");
            return (Criteria) this;
        }

        public Criteria andAverageSinceGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("averageSince >=", value, "averageSince");
            return (Criteria) this;
        }

        public Criteria andAverageSinceLessThan(Date value) {
            addCriterionForJDBCDate("averageSince <", value, "averageSince");
            return (Criteria) this;
        }

        public Criteria andAverageSinceLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("averageSince <=", value, "averageSince");
            return (Criteria) this;
        }

        public Criteria andAverageSinceIn(List<Date> values) {
            addCriterionForJDBCDate("averageSince in", values, "averageSince");
            return (Criteria) this;
        }

        public Criteria andAverageSinceNotIn(List<Date> values) {
            addCriterionForJDBCDate("averageSince not in", values, "averageSince");
            return (Criteria) this;
        }

        public Criteria andAverageSinceBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("averageSince between", value1, value2, "averageSince");
            return (Criteria) this;
        }

        public Criteria andAverageSinceNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("averageSince not between", value1, value2, "averageSince");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("operator is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("operator is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorEqualTo(Operator value) {
            addOperatorCriterion("operator =", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotEqualTo(Operator value) {
            addOperatorCriterion("operator <>", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThan(Operator value) {
            addOperatorCriterion("operator >", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(Operator value) {
            addOperatorCriterion("operator >=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThan(Operator value) {
            addOperatorCriterion("operator <", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThanOrEqualTo(Operator value) {
            addOperatorCriterion("operator <=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorIn(List<Operator> values) {
            addOperatorCriterion("operator in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotIn(List<Operator> values) {
            addOperatorCriterion("operator not in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorBetween(Operator value1, Operator value2) {
            addOperatorCriterion("operator between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotBetween(Operator value1, Operator value2) {
            addOperatorCriterion("operator not between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNull() {
            addCriterion("enabled is null");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNotNull() {
            addCriterion("enabled is not null");
            return (Criteria) this;
        }

        public Criteria andEnabledEqualTo(Boolean value) {
            addCriterion("enabled =", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotEqualTo(Boolean value) {
            addCriterion("enabled <>", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThan(Boolean value) {
            addCriterion("enabled >", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThanOrEqualTo(Boolean value) {
            addCriterion("enabled >=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThan(Boolean value) {
            addCriterion("enabled <", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThanOrEqualTo(Boolean value) {
            addCriterion("enabled <=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledIn(List<Boolean> values) {
            addCriterion("enabled in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotIn(List<Boolean> values) {
            addCriterion("enabled not in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledBetween(Boolean value1, Boolean value2) {
            addCriterion("enabled between", value1, value2, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotBetween(Boolean value1, Boolean value2) {
            addCriterion("enabled not between", value1, value2, "enabled");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table kom_kpia
     *
     * @mbggenerated do_not_delete_during_merge Wed Feb 05 12:12:21 CET 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table kom_kpia
     *
     * @mbggenerated Wed Feb 05 12:12:21 CET 2014
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