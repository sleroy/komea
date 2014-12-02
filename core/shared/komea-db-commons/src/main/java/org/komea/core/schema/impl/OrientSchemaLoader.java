package org.komea.core.schema.impl;

import java.util.Collection;
import java.util.Map;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.IReference;
import org.komea.core.schema.Primitive;
import org.komea.core.schema.ReferenceArity;
import org.komea.core.schema.ReferenceKind;

import com.google.common.collect.Maps;
import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

public class OrientSchemaLoader {


	private final IKomeaSchemaFactory factory=new KomeaSchemaFactory();
	
	public OrientSchemaLoader() {
		// TODO Auto-generated constructor stub
	}
	public IKomeaSchema load(final String name,final OrientGraph database){
		IKomeaSchema schema = this.factory.newSchema(name);
		SchemaMapping mapping = new SchemaMapping();
		OSchema oschema = database.getRawGraph().getMetadata().getSchema();
		for(OClass oclass : oschema.getClasses()){
			if(isEntityType(oclass)){
				IEntityType etype = mapping.getOrCreateEntityType(oclass);
				schema.addType(etype);
			}
		}
		return schema;
	}

	private boolean isEntityType(final OClass etype) {
		return !etype.getName().equals("V") && etype.isSubClassOf("V");
	}
	
	
	private static class SchemaMapping {
		private final IKomeaSchemaFactory factory=new KomeaSchemaFactory();
		private final Map<OClass, IEntityType> typesMap=Maps.newHashMap();
		
		public IEntityType getOrCreateEntityType(final OClass oclass){
			IEntityType etype = this.typesMap.get(oclass);
			if(etype==null){
				etype = createEntityType(oclass);	
			}
			return etype;
		}

		private IEntityType createEntityType(final OClass oclass) {
			IEntityType etype = this.factory.newEntity(oclass.getName());
			Collection<OProperty> properties = oclass.properties();
			for (OProperty oProperty : properties) {
				etype.addProperty(createAttribute(oProperty));
			}
			if(oclass.getSuperClass()!=null){
				etype.setSuperType(getOrCreateEntityType(oclass.getSuperClass()));
			}
			return etype;
		}

		private IReference createAttribute(final OProperty oProperty) {
			OType otype = oProperty.getType();
			OType otypeLinked = oProperty.getLinkedType();
			
			Primitive ptype;
			if(otype.isMultiValue()){
				ptype = Primitive.valueOf(otypeLinked.name());
			}else{
				ptype = Primitive.valueOf(otype.name());
			}
			IReference reference = this.factory.newReference(oProperty.getName(), ptype).setKind(ReferenceKind.CONTAINMENT);
			if(otype.isMultiValue()){
				reference.setArity(ReferenceArity.MANY);
			}
			return reference;
		}
 	}
}
