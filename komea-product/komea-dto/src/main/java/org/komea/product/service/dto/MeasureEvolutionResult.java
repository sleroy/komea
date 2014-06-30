package org.komea.product.service.dto;

import java.io.Serializable;
import java.util.List;

public class MeasureEvolutionResult implements Serializable {

    private static final long serialVersionUID = 1L;

    public static boolean hasValues(List<MeasureEvolutionResult> measureEvolutionResults) {
        for (final MeasureEvolutionResult measureEvolutionResult : measureEvolutionResults) {
            if (measureEvolutionResult.getMeasureResult().hasValue()) {
                return true;
            }
        }
        return false;
    }

    private MeasureResult measureResult;
    private Double oldValue;

    public MeasureEvolutionResult() {
    }

    public MeasureEvolutionResult(MeasureResult measureResult, Double oldValue) {
        this.measureResult = measureResult;
        this.oldValue = oldValue;
    }

    public MeasureResult getMeasureResult() {
        return measureResult;
    }

    public void setMeasureResult(MeasureResult measureResult) {
        this.measureResult = measureResult;
    }

    public Double getOldValue() {
        return oldValue;
    }

    public void setOldValue(Double oldValue) {
        this.oldValue = oldValue;
    }

    public boolean hasOldValue() {
        return oldValue != null;
    }

    @Override
    public String toString() {
        return "MeasureEvolutionResult{" + "measureResult=" + measureResult + ", oldValue=" + oldValue + '}';
    }

}
