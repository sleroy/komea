package org.komea.product.database.dto;

import java.util.Date;
import java.util.logging.Logger;
import org.komea.product.database.enums.Operator;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.KpiAlertType;

public class AlertTypeDto extends KpiAlertType {

    private static final Logger LOGGER = Logger.getLogger(AlertTypeDto.class.getName());
    private ProviderType providerType;

    public AlertTypeDto() {
    }

    public AlertTypeDto(KpiAlertType alertType, ProviderType providerType) {
        this(providerType, alertType.getId(), alertType.getIdKpi(), alertType.getKpiAlertKey(),
                alertType.getName(), alertType.getDescription(), alertType.getSeverity(),
                alertType.getValue(), alertType.getAverageSince(), alertType.getOperator(), alertType.getEnabled());
    }

    public AlertTypeDto(ProviderType providerType, Integer id, Integer idKpi,
            String kpiAlertKey, String name, String description, Severity severity,
            Double value, Date averageSince, Operator operator, Boolean enabled) {
        super(id, idKpi, kpiAlertKey, name, description, severity, value, averageSince, operator, enabled);
        this.providerType = providerType;
    }

    public ProviderType getProviderType() {
        return providerType;
    }

    public void setProviderType(ProviderType providerType) {
        this.providerType = providerType;
    }

}
