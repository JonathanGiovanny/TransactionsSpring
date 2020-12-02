package com.jjo.transactiontest.service.impl;

import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jjo.transactiontest.model.InstrumentEntity;
import com.jjo.transactiontest.model.TagEntity;
import com.jjo.transactiontest.repository.InstrumentRepository;
import com.jjo.transactiontest.util.Errors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstrumentServiceImpl {

	private @NonNull InstrumentRepository instrumentRepository;
	private @NonNull TagServiceImpl tagService;

	public InstrumentEntity getInstrument(UUID id) {
		log.debug("Getting one Instrument: {}", id);
		return instrumentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Errors.ENTITY_NOT_FOUND.getTechMessage()));
	}

	@Transactional(readOnly = true)
	public Page<InstrumentEntity> getAll(Pageable pageable) {
		return instrumentRepository.findAll(pageable);
	}

	/**
	 * Throws exception if it is called directly, because there is not existing transaction
	 * @param entity
	 * @return
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	public InstrumentEntity transactionalProMandatory(InstrumentEntity entity) {
		InstrumentEntity opEntity = Optional.ofNullable(entity)
				.orElse(InstrumentEntity.builder()
					.withName("Propagation Mandatory")
					.build());
		return instrumentRepository.save(opEntity);
	}

	/**
	 * Creates a Transaction, and then calls the Mandatory one, that works fine.
	 * @param entity
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public InstrumentEntity transactionalProRequiredDefault(InstrumentEntity entity) {
		InstrumentEntity opEntity = Optional.ofNullable(entity)
				.orElse(InstrumentEntity.builder()
					.withName("Propagation Required")
					.withDescription("First REQUIRED, then MANDATORY")
					.build());
		return transactionalProMandatory(opEntity);
	}

	/**
	 * Creates a transaction in this point, then call the other service that will validate
	 * the NEVER and throw the exception
	 * @param entity
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void transactionalProNeverFailed(InstrumentEntity entity) {
		instrumentRepository.save(entity);

		TagEntity tagEntity = TagEntity.builder()
				.withName("Tag REQUIRES, then NEVER")
				.build();
		tagService.proNever(tagEntity);
	}

	/**
	 * This method will be called as a NEVER,
	 * when the .save(Entity) is called, then a transaction is created as REQUIRED
	 * BUT this transaction is limited to that transaction (save),
	 * THEN called the NEVER on other service, and again,
	 * given that the REQUIRED was limited to the save,
	 * then the same will happen
	 * @param entity
	 */
	@Transactional(propagation = Propagation.NEVER)
	public void transactionalProNever(InstrumentEntity entity) {
		instrumentRepository.save(entity);

		TagEntity tagEntity = TagEntity.builder()
				.withName("Tag NEVER, then NEVER")
				.build();
		tagService.proNever(tagEntity);
	}

	/**
	 * Creates a transaction on this method, then call another method with NEVER,
	 * given that it is the same service, then it is called as REQUIRED, at the end
	 * given that there is an exception at the end, all will be roll-backed
	 * @param entity
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void transactionalProNeverSameService(InstrumentEntity entity) {
		instrumentRepository.save(entity);
		transactionalProNever(entity);

		throw new RuntimeException("Break Everything!!");
	}

	/**
	 * 
	 * @param entity
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void transactionalProRequiresNew(InstrumentEntity entity) {
		instrumentRepository.save(entity);

		TagEntity tagEntity = TagEntity.builder()
				.withName("Tag REQUIRES_NEW")
				.build();
		tagService.proRequiresNew(tagEntity);

		throw new RuntimeException("Break Everything!!");
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void transactionalProNotSupported() {
		
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void transactionalProSupports() {
	}

	@Transactional(propagation = Propagation.NESTED)
	public void transactionalProNested() {
		
	}

	@Transactional(isolation = Isolation.DEFAULT)
	public void transactionalIsoDefault() {
		
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void transactionalIsoReadCommited() {
		
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public void transactionalIsoReadUncommited() {
		
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public void transactionalIsoRepeatableRead() {
		
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void transactionalIsoSerializable() {
		
	}
}
