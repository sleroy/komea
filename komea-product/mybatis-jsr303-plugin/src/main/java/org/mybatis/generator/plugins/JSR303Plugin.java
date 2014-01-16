package org.mybatis.generator.plugins;

import java.sql.Types;
import java.util.List;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class JSR303Plugin extends PluginAdapter {

    public JSR303Plugin() {
        super();
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
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
