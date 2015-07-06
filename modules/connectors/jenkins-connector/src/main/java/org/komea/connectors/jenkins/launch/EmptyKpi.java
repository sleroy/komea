package org.komea.connectors.jenkins.launch;

public class EmptyKpi {

    public static String getFormula() {
        return "import org.komea.eventory.api.cache.BackupDelay\n"
                + "import org.komea.eventory.api.engine.IDynamicDataQuery\n"
                + "import org.komea.product.database.dto.KpiResult\n"
                + "import org.komea.product.database.enums.*\n"
                + "import org.joda.time.DateTime\n"
                + "  \n"
                + "public class EmptyProjectValue implements IDynamicDataQuery<KpiResult> {\n"
                + "    \n"
                + "    @Override\n"
                + "    public BackupDelay getBackupDelay() { \n"
                + "        return BackupDelay.DAY;\n"
                + "    }\n"
                + "    \n"
                + "    @Override\n"
                + "    public KpiResult getResult(DateTime fromPeriod, DateTime toPeriod) {\n"
                + "        return new KpiResult();\n"
                + "    }\n"
                + "    \n"
                + "    @Override\n"
                + "    public KpiResult getResult() {\n"
                + "        return new KpiResult();\n"
                + "    }\n"
                + "\n"
                + "    @Override\n"
                + "    public boolean requiresPreviousResults() {\n"
                + "        return false;\n"
                + "    }\n"
                + "}\n"
                + "\n"
                + "query = new EmptyProjectValue();\n"
                + "query";
    }
}
