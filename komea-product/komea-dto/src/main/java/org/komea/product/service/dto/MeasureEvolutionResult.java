package org.komea.product.service.dto;

import java.io.Serializable;

public class MeasureEvolutionResult implements Serializable {

    private static final long serialVersionUID = 1L;

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
