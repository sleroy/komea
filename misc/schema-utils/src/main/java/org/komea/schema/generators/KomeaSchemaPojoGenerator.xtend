package org.komea.schema.generators

import java.io.File
import org.apache.commons.io.FileUtils
import org.komea.core.schema.IEntityType
import org.komea.core.schema.IKomeaSchema
import org.komea.core.schema.IReference
import org.komea.core.schema.IType
import org.komea.core.schema.Primitive

class KomeaSchemaPojoGenerator {

	File output;
	String opackage;
	
	new(File output, String opackage){
		this.output=output;
		this.opackage=opackage;	
	}
	
	def void generate(IKomeaSchema schema){
		for(IEntityType type : schema.types){
			var File out ;
			if(opackage.contains(".")){
				val fragments = opackage.split(".");
				out = FileUtils.getFile(output,fragments);
			}else{
				out = FileUtils.getFile(output,opackage);
			}

			FileUtils.write(FileUtils.getFile(out,type.name.toFirstUpper+".java"),type.compile);
		}
	}	
	
	def compile(IEntityType e)'''
	package «opackage»;
	
	«IF !e.properties.filter[ref | ref.many].empty»
	import java.util.List;
	import java.util.ArrayList;
	«ENDIF»
	«IF !e.properties.filter[ref | ref.type.equals(Primitive.DATE)].empty»
	import java.util.Date;
	«ENDIF»
	
	
	public class «e.name» «IF e.superType!=null» extends «e.superType.name.toFirstUpper»«ENDIF»{
		«FOR ref: e.properties»
		private «ref.jtype» «ref.name»;
		«ENDFOR»
		
		public «e.name»(){
			super();
		«FOR ref: e.properties»
			«IF ref.many»	this.«ref.name»=new ArrayList<«ref.type.jtype»>();«ENDIF»
		«ENDFOR»
		}
		
		«FOR ref: e.properties»
		
		«ref.getter»
		
		«ref.setter»
		«ENDFOR»
		«e.stringOutput»
		
	}
	'''
	private def setter(IReference ref)'''
		/**
		* Set «ref.name» «IF !ref.type.primitive»«ref.kind.name.toLowerCase»«ENDIF».
		* 
		* @return
		*/
		public void set«ref.name.toFirstUpper»(«ref.jtype» _values){
		 this.«ref.name»=_values;
		}
	'''
	
	private def getter(IReference ref)'''
		/**
		* Get «ref.name» «IF !ref.type.primitive»«ref.kind.name.toLowerCase»«ENDIF».
		* 
		* @return
		*/
		public «ref.jtype» get«ref.name.toFirstUpper»(){
			return this.«ref.name»;
		}
	'''
	private def jtype(IReference ref){
		if(ref.many){
			return "List<"+ref.type.jtype+">"
		}else{
			return ref.type.jtype
		}
	}
	
	private def stringOutput(IEntityType type)'''
	@Override
	public String toString() {
		«val properties = type.allProperties.filter[e|e.type.primitive] »
		return "«type.name» «IF !properties.empty»[«FOR ref: properties SEPARATOR '+", '»«ref.name»="+this.get«ref.name.toFirstUpper»()«ENDFOR»+"]«ENDIF»";
	}
	
	'''
	private def jtype(IType type){
		return type.name;
	}
}