
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class EnumGetterSetterGenerator {

    private final static String ENUMS_PACKAGE = "org.komea.product.database.enums";
    private final static String MODELS_DIR = "src/main/java/org/komea/product/database/model";
    private final static String TEMPLATE_FILE = "src/mybatis/resources/enumGetterSetterTemplate.txt";
    private final static String SQLMAP_SRC_DIR = "src/main/java/org/komea/product/database/sqlmap";
    private final static String SQLMAP_DEST_DIR = "src/main/resources/org/komea/product/database/sqlmap";

    public static void main(String[] args) throws IOException {
        FileUtils.moveDirectory(new File(SQLMAP_SRC_DIR), new File(SQLMAP_DEST_DIR));

        String[][] data = new String[][]{
            // CLASS, ENUM, TABLE, COLUMN
            {"Provider", "ProviderType", "kom_pvd", "providerType"},
            {"KpiAlertType", "Operator", "kom_kpia", "operator"},
            {"KpiAlertType", "Severity", "kom_kpia", "severity"},
            {"EventType", "Severity", "kom_evt", "severity"},
            {"EventType", "EntityType", "kom_evt", "entityType"},
            {"Kpi", "EntityType", "kom_kpi", "entityType"},
            {"Kpi", "ValueDirection", "kom_kpi", "valueDirection"},
            {"Kpi", "ValueType", "kom_kpi", "valueType"}
        };
        for (String[] params : data) {
            addGetterSetterCode(params[0], params[1], params[2], params[3]);
        }
    }

    private static void addGetterSetterCode(final String className, final String enumm,
            final String table, final String column) throws IOException {
        final File javaFile = new File(MODELS_DIR + "/" + className + ".java");
        String code = FileUtils.readFileToString(javaFile);
        final String template = FileUtils.readFileToString(new File(TEMPLATE_FILE));
        final String addedCode = generateEnumGetterSetter(table, column, ENUMS_PACKAGE, enumm, template);
        code = code.substring(0, code.lastIndexOf('}')) + addedCode + '}';
        FileUtils.writeStringToFile(javaFile, code);
    }

    private static String generateEnumGetterSetter(final String table, final String column,
            final String enumsPackage, final String enumm, final String template) {
        final String fullEnum = enumsPackage + '.' + enumm;
        final String fullColumn = table + '.' + column;
        final String enumName = Character.toLowerCase(enumm.charAt(0)) + enumm.substring(1);
        String string = template;
        string = string.replace("$fullColumn", fullColumn);
        string = string.replace("$fullEnum", fullEnum);
        string = string.replace("$enumName", enumName);
        string = string.replace("$enum", enumm);
        string = string.replace("$column", column);
        return string;
    }
}
