package org.komea.event.queries.formulas;

import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.komea.event.model.KomeaEvent;

public class FormulasTest {

    private static final List<KomeaEvent> events = Lists.newArrayList();
    private static final String KEY = "value";
    private static final String NUMBER = "number";

    @BeforeClass
    public static void beforeClass() {
        addEvent(null, null);
        addEvent(5, (Number) null);
        addEvent(1, (String) null);
        addEvent(9, (Date) null);
        addEvent(3, new Date());
        addEvent(null, "");
        addEvent(8, "123");
        addEvent(7, "a");
        addEvent(2, 456);
        addEvent(null, Math.PI);
    }

    private static void addEvent(final Number value, final Object number) {
        final KomeaEvent event = new KomeaEvent();
        event.put(KEY, value);
        event.put(NUMBER, number);
        events.add(event);
    }

    private IFormula getFormula(final FormulaType type) {
        return FormulaUtils.fromFormulaDto(new FormulaDto(type, KEY));
    }

    private double calculate(final FormulaType type) {
        return getFormula(type).calculate(events).doubleValue();
    }

    private void assertResult(final double expected, final FormulaType type) {
        Assert.assertEquals(expected, calculate(type), 1 / 1000000d);
    }

    @Test
    public void testFormulaCount() {
        assertResult(10d, FormulaType.COUNT);
    }

    @Test
    public void testFormulaSum() {
        assertResult(35d, FormulaType.SUM);
    }

    @Test
    public void testFormulaAverage() {
        assertResult(5d, FormulaType.AVERAGE);
    }

    @Test
    public void testFormulaMaxValue() {
        assertResult(9d, FormulaType.MAX_VALUE);
    }

    @Test
    public void testFormulaMinValue() {
        assertResult(1d, FormulaType.MIN_VALUE);
    }

    @Test
    public void testFormulaDiffValue() {
        assertResult(8d, FormulaType.DIFF_VALUE);
    }

    @Test
    public void testFormulaOldestValue() {
        assertResult(2d, FormulaType.OLDEST);
    }

    @Test
    public void testFormulaMostRecentValue() {
        assertResult(5d, FormulaType.MOST_RECENT);
    }

    @Test
    public void testFormulaDiffTime() {
        assertResult(3d, FormulaType.DIFF_TIME);
    }

    @Test
    public void testNumberValue() {
        final FormulaSum sum = new FormulaSum(NUMBER);
        sum.calculate(events);
    }

}
