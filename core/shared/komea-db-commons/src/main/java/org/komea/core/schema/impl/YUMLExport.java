package org.komea.core.schema.impl;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IReference;

public class YUMLExport {

	public static String buildYumlSchemaUrl(final IKomeaSchema schema) {
		StringBuilder sb = new StringBuilder();
		sb.append("http://yuml.me/diagram/scruffy/class/");
		for (IEntityType type : schema.getTypes()) {
			declare(type, sb);
			sb.append(",");
		}
		Iterator<IEntityType> iterator = schema.getTypes().iterator();
		while (iterator.hasNext()) {
			IEntityType type = iterator.next();
			edges(type, sb);
			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		return sb.toString();
	}
	
	public static void exportToHtml(final File output, final IKomeaSchema schema) throws IOException{
		StringBuilder sb = new StringBuilder();
		sb.append("<html>\n<body>\n<img src='");
		sb.append(buildYumlSchemaUrl(schema));
		sb.append("'>\n</body>\n</html>");

		FileUtils.write(output, sb.toString());
	}

	private static void edges(final IEntityType type, final StringBuilder sb) {
		boolean first = true;
		// inheritancy
		if(type.getSuperType()!=null){
			sb.append("[").append(type.getName()).append("]");
			sb.append("^-");
			sb.append("[").append(type.getSuperType().getName()).append("]");
			first=false;
		}
		// references
	
		for (IReference property : type.getProperties()) {
			if (!property.getType().isPrimitive()) {
				if (!first) {
					sb.append(",");
				} else {
					first = false;
				}
				sb.append("[").append(type.getName()).append("]");
				sb.append(property.getName());
				switch (property.getKind()) {
				case CONTAINMENT:
					sb.append("++");
					break;
				case AGGREGATION:
					sb.append("+");
					break;

				default:

				}

				sb.append("->");
				switch (property.getArity()) {
				case ONE:
					if (property.isMandatory()) {
						sb.append("1");
					} else {
						sb.append("0..1");
					}
					break;
				case MANY:

					sb.append("*");

					break;
				default:

				}
				sb.append("[").append(property.getType().getName()).append("]");
			}
		}
	}

	private static void declare(final IEntityType type, final StringBuilder sb) {
		sb.append("[").append(type.getName()).append("|");
		boolean first = true;
		for (IReference property : type.getProperties()) {
			if (property.getType().isPrimitive()) {
				if (!first) {
					sb.append(";");
				} else {
					first = false;
				}
				sb.append(property.getName()).append(" : ")
						.append(property.getType().getName());
				if (property.isMany()) {
					sb.append("*");
				}
			}
		}
		sb.append("]");
	}

}
