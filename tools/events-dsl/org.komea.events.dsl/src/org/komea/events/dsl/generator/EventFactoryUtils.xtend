package org.komea.events.dsl.generator

import org.komea.events.dsl.eventDsl.Event
import org.komea.events.dsl.eventDsl.EventProperty
import org.komea.events.dsl.eventDsl.SimpleProperty
import org.komea.events.dsl.eventDsl.ListProperty
import org.komea.events.dsl.eventDsl.EventFactory

class EventFactoryUtils {

	def getAllRequiredProperties(Event event) {
		return event.properties.filter[p|p.required]
		
	}
	
	def getFactoryPath(EventFactory factory){
			if(factory.path==null){
			return "src/main/java";
		}else{
			return factory.path;
		}
	}
	def getFactoryPackage(EventFactory factory){
		if(factory.package==null){
			return "org.komea.event.factory";
		}else{
			return factory.package;
		}
	}
	
	def getFactoryFile(EventFactory factory){
		return factory.factoryPackage.replace(".","/")+"/"+factory.factoryName+".java"
	}
	
	def getFactoryName(EventFactory factory){
		return factory.name+"EventFactory";
	}
	def dispatch isRequired(EventProperty p) {
		return false;
	}

	def dispatch isRequired(SimpleProperty p) {
		return p.value == null;
	}

	def dispatch isRequired(ListProperty p) {
		return p.values.empty;
	}
}
