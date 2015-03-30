package org.komea.schema.generators;

import com.google.common.base.Objects;
import java.io.File;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IReference;
import org.komea.core.schema.IType;
import org.komea.core.schema.Primitive;
import org.komea.core.schema.ReferenceKind;

@SuppressWarnings("all")
public class KomeaSchemaPojoGenerator {
  private File output;
  
  private String opackage;
  
  public KomeaSchemaPojoGenerator(final File output, final String opackage) {
    this.output = output;
    this.opackage = opackage;
  }
  
  public void generate(final IKomeaSchema schema) {
    try {
      List<IEntityType> _types = schema.getTypes();
      for (final IEntityType type : _types) {
        {
          File out = null;
          boolean _contains = this.opackage.contains(".");
          if (_contains) {
            final String[] fragments = this.opackage.split(".");
            File _file = FileUtils.getFile(this.output, fragments);
            out = _file;
          } else {
            File _file_1 = FileUtils.getFile(this.output, this.opackage);
            out = _file_1;
          }
          String _name = type.getName();
          String _firstUpper = StringExtensions.toFirstUpper(_name);
          String _plus = (_firstUpper + ".java");
          File _file_2 = FileUtils.getFile(out, _plus);
          CharSequence _compile = this.compile(type);
          FileUtils.write(_file_2, _compile);
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public CharSequence compile(final IEntityType e) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    _builder.append(this.opackage, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      List<IReference> _properties = e.getProperties();
      final Function1<IReference, Boolean> _function = new Function1<IReference, Boolean>() {
        public Boolean apply(final IReference ref) {
          return Boolean.valueOf(ref.isMany());
        }
      };
      Iterable<IReference> _filter = IterableExtensions.<IReference>filter(_properties, _function);
      boolean _isEmpty = IterableExtensions.isEmpty(_filter);
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("import java.util.List;");
        _builder.newLine();
        _builder.append("import java.util.ArrayList;");
        _builder.newLine();
      }
    }
    {
      List<IReference> _properties_1 = e.getProperties();
      final Function1<IReference, Boolean> _function_1 = new Function1<IReference, Boolean>() {
        public Boolean apply(final IReference ref) {
          IType _type = ref.getType();
          return Boolean.valueOf(_type.equals(Primitive.DATE));
        }
      };
      Iterable<IReference> _filter_1 = IterableExtensions.<IReference>filter(_properties_1, _function_1);
      boolean _isEmpty_1 = IterableExtensions.isEmpty(_filter_1);
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        _builder.append("import java.util.Date;");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    String _name = e.getName();
    _builder.append(_name, "");
    _builder.append(" ");
    {
      IEntityType _superType = e.getSuperType();
      boolean _notEquals = (!Objects.equal(_superType, null));
      if (_notEquals) {
        _builder.append(" extends ");
        IEntityType _superType_1 = e.getSuperType();
        String _name_1 = _superType_1.getName();
        String _firstUpper = StringExtensions.toFirstUpper(_name_1);
        _builder.append(_firstUpper, "");
      }
    }
    _builder.append("{");
    _builder.newLineIfNotEmpty();
    {
      List<IReference> _properties_2 = e.getProperties();
      for(final IReference ref : _properties_2) {
        _builder.append("\t");
        _builder.append("private ");
        String _jtype = this.jtype(ref);
        _builder.append(_jtype, "\t");
        _builder.append(" ");
        String _name_2 = ref.getName();
        _builder.append(_name_2, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    String _name_3 = e.getName();
    _builder.append(_name_3, "\t");
    _builder.append("(){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("super();");
    _builder.newLine();
    {
      List<IReference> _properties_3 = e.getProperties();
      for(final IReference ref_1 : _properties_3) {
        _builder.append("\t");
        {
          boolean _isMany = ref_1.isMany();
          if (_isMany) {
            _builder.append("\tthis.");
            String _name_4 = ref_1.getName();
            _builder.append(_name_4, "\t");
            _builder.append("=new ArrayList<");
            IType _type = ref_1.getType();
            String _jtype_1 = this.jtype(_type);
            _builder.append(_jtype_1, "\t");
            _builder.append(">();");
          }
        }
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      List<IReference> _properties_4 = e.getProperties();
      for(final IReference ref_2 : _properties_4) {
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        CharSequence _ter = this.getter(ref_2);
        _builder.append(_ter, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        CharSequence _setter = this.setter(ref_2);
        _builder.append(_setter, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    CharSequence _stringOutput = this.stringOutput(e);
    _builder.append(_stringOutput, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence setter(final IReference ref) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/**");
    _builder.newLine();
    _builder.append("* Set ");
    String _name = ref.getName();
    _builder.append(_name, "");
    _builder.append(" ");
    {
      IType _type = ref.getType();
      boolean _isPrimitive = _type.isPrimitive();
      boolean _not = (!_isPrimitive);
      if (_not) {
        ReferenceKind _kind = ref.getKind();
        String _name_1 = _kind.name();
        String _lowerCase = _name_1.toLowerCase();
        _builder.append(_lowerCase, "");
      }
    }
    _builder.append(".");
    _builder.newLineIfNotEmpty();
    _builder.append("* ");
    _builder.newLine();
    _builder.append("* @return");
    _builder.newLine();
    _builder.append("*/");
    _builder.newLine();
    _builder.append("public void set");
    String _name_2 = ref.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name_2);
    _builder.append(_firstUpper, "");
    _builder.append("(");
    String _jtype = this.jtype(ref);
    _builder.append(_jtype, "");
    _builder.append(" _values){");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("this.");
    String _name_3 = ref.getName();
    _builder.append(_name_3, " ");
    _builder.append("=_values;");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence getter(final IReference ref) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/**");
    _builder.newLine();
    _builder.append("* Get ");
    String _name = ref.getName();
    _builder.append(_name, "");
    _builder.append(" ");
    {
      IType _type = ref.getType();
      boolean _isPrimitive = _type.isPrimitive();
      boolean _not = (!_isPrimitive);
      if (_not) {
        ReferenceKind _kind = ref.getKind();
        String _name_1 = _kind.name();
        String _lowerCase = _name_1.toLowerCase();
        _builder.append(_lowerCase, "");
      }
    }
    _builder.append(".");
    _builder.newLineIfNotEmpty();
    _builder.append("* ");
    _builder.newLine();
    _builder.append("* @return");
    _builder.newLine();
    _builder.append("*/");
    _builder.newLine();
    _builder.append("public ");
    String _jtype = this.jtype(ref);
    _builder.append(_jtype, "");
    _builder.append(" get");
    String _name_2 = ref.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name_2);
    _builder.append(_firstUpper, "");
    _builder.append("(){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("return this.");
    String _name_3 = ref.getName();
    _builder.append(_name_3, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private String jtype(final IReference ref) {
    boolean _isMany = ref.isMany();
    if (_isMany) {
      IType _type = ref.getType();
      String _jtype = this.jtype(_type);
      String _plus = ("List<" + _jtype);
      return (_plus + ">");
    } else {
      IType _type_1 = ref.getType();
      return this.jtype(_type_1);
    }
  }
  
  private CharSequence stringOutput(final IEntityType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("public String toString() {");
    _builder.newLine();
    _builder.append("\t");
    List<IReference> _allProperties = type.getAllProperties();
    final Function1<IReference, Boolean> _function = new Function1<IReference, Boolean>() {
      public Boolean apply(final IReference e) {
        IType _type = e.getType();
        return Boolean.valueOf(_type.isPrimitive());
      }
    };
    final Iterable<IReference> properties = IterableExtensions.<IReference>filter(_allProperties, _function);
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("return \"");
    String _name = type.getName();
    _builder.append(_name, "\t");
    _builder.append(" ");
    {
      boolean _isEmpty = IterableExtensions.isEmpty(properties);
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("[");
        {
          boolean _hasElements = false;
          for(final IReference ref : properties) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate("+\", ", "\t");
            }
            String _name_1 = ref.getName();
            _builder.append(_name_1, "\t");
            _builder.append("=\"+this.get");
            String _name_2 = ref.getName();
            String _firstUpper = StringExtensions.toFirstUpper(_name_2);
            _builder.append(_firstUpper, "\t");
            _builder.append("()");
          }
        }
        _builder.append("+\"]");
      }
    }
    _builder.append("\";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
  
  private String jtype(final IType type) {
    return type.getName();
  }
}
