package com.jjo.transactiontest.config;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
		options = options.including(Include.STACK_TRACE, Include.MESSAGE);
		Map<String, Object> originalAttributes = super.getErrorAttributes(webRequest, options);

		final String userMessage;
		final String techMessage;

		userMessage = originalAttributes.get("error").toString();
		techMessage = originalAttributes.get("message").toString();

		Map<String, Object> errorAttributes = new LinkedHashMap<>();
		errorAttributes.put("userMessage", userMessage);
		errorAttributes.put("techMessage", techMessage);
		errorAttributes.put("timestamp", LocalDateTime.now());
		errorAttributes.put("path", originalAttributes.get("path"));
		return errorAttributes;
	}

}
