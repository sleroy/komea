package org.komea.product.database.model;

import java.util.ArrayList;
import java.util.List;

public class ProjectCriteria {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public ProjectCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
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
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
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

        public Criteria andProjectKeyIsNull() {
            addCriterion("projectKey is null");
            return (Criteria) this;
        }

        public Criteria andProjectKeyIsNotNull() {
            addCriterion("projectKey is not null");
            return (Criteria) this;
        }

        public Criteria andProjectKeyEqualTo(String value) {
            addCriterion("projectKey =", value, "projectKey");
            return (Criteria) this;
        }

        public Criteria andProjectKeyNotEqualTo(String value) {
            addCriterion("projectKey <>", value, "projectKey");
            return (Criteria) this;
        }

        public Criteria andProjectKeyGreaterThan(String value) {
            addCriterion("projectKey >", value, "projectKey");
            return (Criteria) this;
        }

        public Criteria andProjectKeyGreaterThanOrEqualTo(String value) {
            addCriterion("projectKey >=", value, "projectKey");
            return (Criteria) this;
        }

        public Criteria andProjectKeyLessThan(String value) {
            addCriterion("projectKey <", value, "projectKey");
            return (Criteria) this;
        }

        public Criteria andProjectKeyLessThanOrEqualTo(String value) {
            addCriterion("projectKey <=", value, "projectKey");
            return (Criteria) this;
        }

        public Criteria andProjectKeyLike(String value) {
            addCriterion("projectKey like", value, "projectKey");
            return (Criteria) this;
        }

        public Criteria andProjectKeyNotLike(String value) {
            addCriterion("projectKey not like", value, "projectKey");
            return (Criteria) this;
        }

        public Criteria andProjectKeyIn(List<String> values) {
            addCriterion("projectKey in", values, "projectKey");
            return (Criteria) this;
        }

        public Criteria andProjectKeyNotIn(List<String> values) {
            addCriterion("projectKey not in", values, "projectKey");
            return (Criteria) this;
        }

        public Criteria andProjectKeyBetween(String value1, String value2) {
            addCriterion("projectKey between", value1, value2, "projectKey");
            return (Criteria) this;
        }

        public Criteria andProjectKeyNotBetween(String value1, String value2) {
            addCriterion("projectKey not between", value1, value2, "projectKey");
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

        public Criteria andIdCustomerIsNull() {
            addCriterion("idCustomer is null");
            return (Criteria) this;
        }

        public Criteria andIdCustomerIsNotNull() {
            addCriterion("idCustomer is not null");
            return (Criteria) this;
        }

        public Criteria andIdCustomerEqualTo(Integer value) {
            addCriterion("idCustomer =", value, "idCustomer");
            return (Criteria) this;
        }

        public Criteria andIdCustomerNotEqualTo(Integer value) {
            addCriterion("idCustomer <>", value, "idCustomer");
            return (Criteria) this;
        }

        public Criteria andIdCustomerGreaterThan(Integer value) {
            addCriterion("idCustomer >", value, "idCustomer");
            return (Criteria) this;
        }

        public Criteria andIdCustomerGreaterThanOrEqualTo(Integer value) {
            addCriterion("idCustomer >=", value, "idCustomer");
            return (Criteria) this;
        }

        public Criteria andIdCustomerLessThan(Integer value) {
            addCriterion("idCustomer <", value, "idCustomer");
            return (Criteria) this;
        }

        public Criteria andIdCustomerLessThanOrEqualTo(Integer value) {
            addCriterion("idCustomer <=", value, "idCustomer");
            return (Criteria) this;
        }

        public Criteria andIdCustomerIn(List<Integer> values) {
            addCriterion("idCustomer in", values, "idCustomer");
            return (Criteria) this;
        }

        public Criteria andIdCustomerNotIn(List<Integer> values) {
            addCriterion("idCustomer not in", values, "idCustomer");
            return (Criteria) this;
        }

        public Criteria andIdCustomerBetween(Integer value1, Integer value2) {
            addCriterion("idCustomer between", value1, value2, "idCustomer");
            return (Criteria) this;
        }

        public Criteria andIdCustomerNotBetween(Integer value1, Integer value2) {
            addCriterion("idCustomer not between", value1, value2, "idCustomer");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table kom_proj
     *
     * @mbggenerated do_not_delete_during_merge Fri Feb 14 12:56:55 CET 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table kom_proj
     *
     * @mbggenerated Fri Feb 14 12:56:55 CET 2014
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