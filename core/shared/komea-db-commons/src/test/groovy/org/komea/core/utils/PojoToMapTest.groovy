package org.komea.core.utils

import spock.lang.Specification




class PojoToMapTest extends Specification {

	class A {
		int fieldA
		int fieldB
		int fieldC
	}

	class B {
		int fieldA
		int fieldB
		int fieldC

		int getFieldC() {
			throw new UnsupportedOperationException()
		}
	}

	def convertPojoInMap() {
		when:
		def temp = new PojoToMap()
		def res = temp.convertPojoInMap new A()
		then:
		res.size() == 3
	}

	def convertPojoInMapError() {
		when:
		def temp = new PojoToMap()
		def res = temp.convertPojoInMap new B()
		then:
		res.size() == 2
	}
}


