/**
 *
 */
package org.komea.event.queries.column.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.queries.column.IColumn;
import org.komea.event.queries.result.IResultMapper;
import org.komea.event.queries.rows.IRow;
import org.komea.event.queries.rows.impl.MapRow;

/**
 * Defines a result mapper returnings a list of columns as a hashmap.
 *
 * @author sleroy
 */
public class ColumnMapper implements IResultMapper<FlatEvent> {

    public static class ColumnDefinition {

        private IColumn<FlatEvent, ?> column;
        private String columnName;

        /**
         * @param _column
         * @param _columnName
         */
        public ColumnDefinition(final IColumn<FlatEvent, ?> _column,
                final String _columnName) {
            super();
            column = _column;
            columnName = _columnName;
        }

        /**
         *
         */
        public void begin() {
            column.begin();

        }

        /**
         * Returns the column value.
         */
        public Object end() {

            return column.end();
        }

        public IColumn<FlatEvent, ?> getColumn() {
            return column;
        }

        public String getColumnName() {
            return columnName;
        }

        /**
         * Process the flat event.
         *
         * @param _event the event
         * @return the value.
         */
        public void process(final FlatEvent _event) {
            column.process(_event);

        }

        public void setColumn(final IColumn<FlatEvent, ?> _column) {
            column = _column;
        }

        public void setColumnName(final String _columnName) {
            columnName = _columnName;
        }
    }

    /**
     * Returns a new instanceof of column mapper
     *
     * @return a new instan
     */
    public static ColumnMapper create() {

        return new ColumnMapper();
    }

    private final List<ColumnDefinition> columns = Lists.newArrayList();

    /*
     * (non-Javadoc)
     * @see org.komea.event.queries.IResultMapper#begin()
     */
    @Override
    public void begin() {
        for (final ColumnDefinition column : columns) {
            column.begin();
        }
    }

    /**
     * Returns the current instance
     *
     * @return the current instance;
     */
    public IResultMapper<FlatEvent> build() {

        return this;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.event.queries.IResultMapper#end()
     */
    @Override
    public List<? extends IRow> end() {
        final Map<String, Object> mapper = Maps.newHashMap();
        for (final ColumnDefinition column : columns) {
            mapper.put(column.getColumnName(), column.end());
        }
        return Collections.singletonList(new MapRow(mapper));
    }

    /**
     * Adds a new column
     *
     * @param _column the column generator.
     * @param _columnName
     */
    public ColumnMapper newColumn(final String _columnName,
            final IColumn<FlatEvent, ?> _column) {
        columns.add(new ColumnDefinition(_column, _columnName));
        return this;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.event.queries.IResultMapper#process(java.lang.Object)
     */
    @Override
    public List<? extends IRow> process(final FlatEvent _event) {
        for (final ColumnDefinition column : columns) {
            column.process(_event);
        }
        return null;

    }

}
