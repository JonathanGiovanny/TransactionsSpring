package com.jjo.transactiontest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jjo.transactiontest.model.TagEntity;
import com.jjo.transactiontest.service.impl.TagServiceImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("tags")
@RequiredArgsConstructor
public class TagController {

	private @NonNull TagServiceImpl tagService;

	@GetMapping
	@ResponseBody
	public Page<TagEntity> getAll(@PageableDefault(size = 20) Pageable pageable) {
		return tagService.getAll(pageable);
	}

}
