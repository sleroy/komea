package org.komea.events.queries.formulas;

public abstract class FormulaUtils {

    public static IFormula fromFormulaDto(final FormulaDto dto) {
        final String key = dto.getKey();
        switch (dto.getType()) {
            case COUNT:
                return new FormulaCount();
            case MOST_RECENT:
                return new FormulaMostRecent(key);
            case OLDEST:
                return new FormulaOldest(key);
            case AVERAGE:
                return new FormulaAverage(key);
            case DIFF_TIME:
                return new FormulaDiffTime(key);
            case DIFF_VALUE:
                return new FormulaDiffValue(key);
            case MAX_VALUE:
                return new FormulaMaxValue(key);
            case MIN_VALUE:
                return new FormulaMinValue(key);
            case SUM:
                return new FormulaSum(key);
        }
        return null;
    }

    private FormulaUtils() {
    }

}
