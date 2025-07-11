package com.deliverytech.delivery.exceptions;

public class EntityNotFoundException extends BusinessException {

	private static final long serialVersionUID = 1L;

	private final String entityName;
	private final Object entityId;

	public EntityNotFoundException(String entityName, Object entityId) {
		super(String.format("%s com ID %s não foi encontrado(a)", entityName, entityId));
		this.entityName = entityName;
		this.entityId = entityId;
		this.setErrorCode("ENTITY_NOT_FOUND");
	}

	public EntityNotFoundException(String message) {
		super(message);
		this.entityName = null;
		this.entityId = null;
		this.setErrorCode("ENTITY_NOT_FOUND");
	}

	public String getEntityName() {
		return entityName;
	}

	public Object getEntityId() {
		return entityId;
	}
}