package com.jjo.transactiontest.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Errors {

	ENTITY_NOT_FOUND("element.not_found", "Entity not found on the DB");
	
	private String userMessage;
	private String techMessage;

}
