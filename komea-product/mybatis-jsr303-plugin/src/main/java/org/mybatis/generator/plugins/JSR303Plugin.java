package org.mybatis.generator.plugins;

import java.sql.Types;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class JSR303Plugin extends PluginAdapter {

    public JSR303Plugin() {
        super();
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {

        final String replaceMapper = properties.getProperty("replaceMapper");
        if (stringHasValue(replaceMapper)) {
            final Pattern pattern = Pattern.compile("Mapper$");
            String oldType = introspectedTable.getMyBatis3JavaMapperType();
            final Matcher matcher = pattern.matcher(oldType);
            oldType = matcher.replaceAll(replaceMapper);
            introspectedTable.setMyBatis3JavaMapperType(oldType);
        }

        String replaceExample = properties.getProperty("replaceExample");
        if (stringHasValue(replaceExample)) {
            Pattern pattern = Pattern.compile("Example$");

            String oldType = introspectedTable.getExampleType();
            Matcher matcher = pattern.matcher(oldType);
            oldType = matcher.replaceAll(replaceExample);
            introspectedTable.setExampleType(oldType);

            oldType = introspectedTable.getUpdateByExampleStatementId();
            matcher = pattern.matcher(oldType);
            oldType = matcher.replaceAll(replaceExample);
            introspectedTable.setUpdateByExampleStatementId(oldType);

            oldType = introspectedTable.getUpdateByExampleWithBLOBsStatementId();
            matcher = pattern.matcher(oldType);
            oldType = matcher.replaceAll(replaceExample);
            introspectedTable.setUpdateByExampleWithBLOBsStatementId(oldType);

            oldType = introspectedTable.getCountByExampleStatementId();
            matcher = pattern.matcher(oldType);
            oldType = matcher.replaceAll(replaceExample);
            introspectedTable.setCountByExampleStatementId(oldType);

            oldType = introspectedTable.getDeleteByExampleStatementId();
            matcher = pattern.matcher(oldType);
            oldType = matcher.replaceAll(replaceExample);
            introspectedTable.setDeleteByExampleStatementId(oldType);

            oldType = introspectedTable.getSelectByExampleStatementId();
            matcher = pattern.matcher(oldType);
            oldType = matcher.replaceAll(replaceExample);
            introspectedTable.setSelectByExampleStatementId(oldType);

            oldType = introspectedTable.getSelectByExampleWithBLOBsStatementId();
            matcher = pattern.matcher(oldType);
            oldType = matcher.replaceAll(replaceExample);
            introspectedTable.setSelectByExampleWithBLOBsStatementId(oldType);

            pattern = Pattern.compile("ExampleSelective$");
            replaceExample += "Selective";

            oldType = introspectedTable.getUpdateByExampleSelectiveStatementId();
            matcher = pattern.matcher(oldType);
            oldType = matcher.replaceAll(replaceExample);
            introspectedTable.setUpdateByExampleSelectiveStatementId(oldType);

        }
    }

    @Override
    public boolean modelFieldGenerated(Field field,
            TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
            IntrospectedTable introspectedTable, ModelClassType modelClassType) {

        if (!introspectedColumn.isNullable()
                && (introspectedColumn.getJdbcType() != Types.INTEGER
                || !introspectedColumn.getActualColumnName().startsWith("id"))) {
            topLevelClass.addImportedType("javax.validation.constraints.NotNull");
            field.addAnnotation("@NotNull");
        }
        if (introspectedColumn.isStringColumn()) {
            topLevelClass.addImportedType("javax.validation.constraints.Size");
            field.addAnnotation("@Size(min = 0, max = " + introspectedColumn.getLength() + ")");
        }
//        if (introspectedColumn.getJdbcType() == Types.INTEGER) {
//            topLevelClass.addImportedType("javax.validation.constraints.Max");
//            field.addAnnotation("@Max(value=2147483647)");
//            topLevelClass.addImportedType("javax.validation.constraints.Min");
//            field.addAnnotation("@Min(value=-2147483648)");
//        }
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn,
                introspectedTable, modelClassType);
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return modelClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return modelClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(
            TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return modelClassGenerated(topLevelClass, introspectedTable);
    }

    private boolean modelClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        setEntityType(topLevelClass, introspectedTable);
//        addKpiMethods(topLevelClass, introspectedTable);
        return true;
    }

    private void addKpiMethods(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if ("org.komea.product.database.model.Kpi".equals(topLevelClass.getType().toString())) {
            final FullyQualifiedJavaType javaTypeIEntity
                    = new FullyQualifiedJavaType("org.komea.product.database.api.IEntity");
            topLevelClass.addImportedType(javaTypeIEntity);
            final Parameter parameter = new Parameter(javaTypeIEntity, "_entity");

            final Method computeKPIEsperKey = new Method("computeKPIEsperKey");
            computeKPIEsperKey.setVisibility(JavaVisibility.PUBLIC);
            computeKPIEsperKey.addParameter(parameter);
            computeKPIEsperKey.setReturnType(FullyQualifiedJavaType.getStringInstance());
            computeKPIEsperKey.addBodyLine("return \"KPI_\" + getKpiKey() + \"_T_\" + "
                    + "getEntityType().ordinal() + \"_ENTITY_\" + _entity.getId();");
            topLevelClass.addMethod(computeKPIEsperKey);

            final Method getCronHistoryJobName = new Method("getCronHistoryJobName");
            getCronHistoryJobName.setVisibility(JavaVisibility.PUBLIC);
            getCronHistoryJobName.addParameter(parameter);
            getCronHistoryJobName.setReturnType(FullyQualifiedJavaType.getStringInstance());
            getCronHistoryJobName.addBodyLine("return \"HISTORY_\" + computeKPIEsperKey(_entity);");
            topLevelClass.addMethod(getCronHistoryJobName);

            final Method constructor = new Method("Kpi");
            constructor.setVisibility(JavaVisibility.PUBLIC);
            constructor.setConstructor(true);
            for (final IntrospectedColumn column : introspectedTable.getNonBLOBColumns()) {
                final String fieldName = column.getActualColumnName();
                Parameter param = new Parameter(column.getFullyQualifiedJavaType(), "_" + fieldName);
                constructor.addParameter(param);
                constructor.addBodyLine("this." + fieldName + " = _" + fieldName + ";");
            }
            topLevelClass.addMethod(constructor);

        }
    }

    private void setEntityType(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String entityType = introspectedTable.getTableConfigurationProperty("entityType");
        if (entityType != null) {
            final FullyQualifiedJavaType javaIEntityType
                    = new FullyQualifiedJavaType("org.komea.product.database.api.IEntity");
            topLevelClass.addImportedType(javaIEntityType);
            topLevelClass.addSuperInterface(javaIEntityType);
            final FullyQualifiedJavaType javaEntityType
                    = new FullyQualifiedJavaType("org.komea.product.database.enums.EntityType");
            topLevelClass.addImportedType(javaEntityType);
            final Method method = new Method("entityType");
            method.addAnnotation("@Override");
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setReturnType(javaEntityType);
            if ("PERSON_GROUP".equals(entityType)) {
                entityType = "this.getType().name()";
                method.addBodyLine("return EntityType.valueOf(" + entityType + ");");
            } else {
                method.addBodyLine("return EntityType." + entityType + ";");
            }
            topLevelClass.addMethod(method);
        }
    }

}
