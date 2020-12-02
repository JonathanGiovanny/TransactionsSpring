package com.jjo.transactiontest.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jjo.transactiontest.model.TagEntity;
import com.jjo.transactiontest.repository.TagRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl {

	private @NonNull TagRepository tagRepository;

	public Page<TagEntity> getAll(Pageable pageable) {
		log.debug("Getting all the Tags");
		return tagRepository.findAll(pageable);
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
	public void proRequiresNew(TagEntity entity) {
		tagRepository.save(entity);
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.NEVER)
	public void proNever(TagEntity entity) {
		tagRepository.save(entity);
	}

}
