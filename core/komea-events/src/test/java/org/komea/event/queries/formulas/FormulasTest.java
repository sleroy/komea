package org.komea.event.queries.formulas;

import com.google.common.collect.Lists;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.komea.event.model.impl.KomeaEvent;

public class FormulasTest {

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

    private static void addEvent(final Number value, final Serializable number) {
        final KomeaEvent event = new KomeaEvent();
        event.put(KEY, value);
        event.put(NUMBER, number);
        events.add(event);
    }

    private static final List<KomeaEvent> events = Lists.newArrayList();

    private static final String KEY = "value";

    private static final String NUMBER = "number";

    @Test
    public void testFormulaAverage() {
        this.assertResult(5d, FormulaType.AVERAGE);
    }

    @Test
    public void testFormulaCount() {
        this.assertResult(10d, FormulaType.COUNT);
    }

    @Test
    public void testFormulaDiffTime() {
        this.assertResult(3d, FormulaType.DIFF_TIME);
    }

    @Test
    public void testFormulaDiffValue() {
        this.assertResult(8d, FormulaType.DIFF_VALUE);
    }

    @Test
    public void testFormulaMaxValue() {
        this.assertResult(9d, FormulaType.MAX_VALUE);
    }

    @Test
    public void testFormulaMinValue() {
        this.assertResult(1d, FormulaType.MIN_VALUE);
    }

    @Test
    public void testFormulaMostRecentValue() {
        this.assertResult(5d, FormulaType.MOST_RECENT);
    }

    @Test
    public void testFormulaOldestValue() {
        this.assertResult(2d, FormulaType.OLDEST);
    }

    @Test
    public void testFormulaSum() {
        this.assertResult(35d, FormulaType.SUM);
    }

    @Test
    public void testNumberValue() {
        final FormulaSum sum = new FormulaSum(NUMBER);
        sum.calculate(events);
    }

    @Test
    public void test() {
        final FormulaDto formula = new FormulaDto();
        formula.setKey(formula.getKey());
        formula.setType(formula.getType());
        FormulaDto.of(FormulaType.SUM, "value").toString();
    }

    private void assertResult(final double expected, final FormulaType type) {
        Assert.assertEquals(expected, this.calculate(type), 1 / 1000000d);
    }

    private double calculate(final FormulaType type) {
        return this.getFormula(type).calculate(events).doubleValue();
    }

    private IFormula getFormula(final FormulaType type) {
        return FormulaUtils.fromFormulaDto(new FormulaDto(type, KEY));
    }

}
