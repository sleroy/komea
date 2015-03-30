package org.komea.product.eventory.rest.dto;

public class MessageComplexEventDto extends ComplexEventDto {

	private String longMessage;

	public MessageComplexEventDto() {
		super();
	}

	public String getLongMessage() {
		return this.longMessage;
	}

	public void setLongMessage(final String longMessage) {
		this.longMessage = longMessage;
	}

}
