package org.mybatis.generator.plugins;

import java.sql.Types;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Interface;
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
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable); //To change body of generated methods, choose Tools | Templates.
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
                // && !introspectedTable.getPrimaryKeyColumns().contains(introspectedColumn)) {
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

}
