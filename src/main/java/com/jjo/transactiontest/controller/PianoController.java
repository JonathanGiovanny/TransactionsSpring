package com.jjo.transactiontest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jjo.transactiontest.model.InstrumentEntity;
import com.jjo.transactiontest.service.impl.InstrumentServiceImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("pianos")
@RequiredArgsConstructor
public class PianoController {

	private @NonNull InstrumentServiceImpl instrumentService;

	@GetMapping
	@ResponseBody
	public Page<InstrumentEntity> getAll(@PageableDefault(size = 20) Pageable pageable) {
		return instrumentService.getAll(pageable);
	}

	@PostMapping("pro-mandatory")
	@ResponseBody
	public InstrumentEntity callProMandatory() {
		return instrumentService.transactionalProMandatory(null);
	}

	@PostMapping("pro-required")
	@ResponseBody
	public InstrumentEntity callProRequired() {
		return instrumentService.transactionalProRequiredDefault(null);
	}

	@PostMapping("pro-never/failed")
	@ResponseBody
	public void callProNeverProRequired() {
		InstrumentEntity opEntity = InstrumentEntity.builder()
					.withName("Propagation Never")
					.withDescription("First REQURIES_NEW, then NEVER")
					.build();
		instrumentService.transactionalProNeverFailed(opEntity);
	}

	@PostMapping("pro-never")
	@ResponseBody
	public void callProNever() {
		InstrumentEntity opEntity = InstrumentEntity.builder()
				.withName("Propagation Never")
				.withDescription("First NEVER, then NEVER")
				.build();
		instrumentService.transactionalProNever(opEntity);
	}

	@PostMapping("pro-never/same-service")
	@ResponseBody
	public void callProNeverSameService() {
		InstrumentEntity opEntity = InstrumentEntity.builder()
				.withName("Propagation Never")
				.withDescription("First REQUIRES_NEW, then NEVER")
				.build();
		instrumentService.transactionalProNeverSameService(opEntity);
	}

	@PostMapping("pro-requires-new/failed")
	@ResponseBody
	public void callProRequiredThenRequiresNew() {
		InstrumentEntity opEntity = InstrumentEntity.builder()
				.withName("Propagation Never")
				.withDescription("First NEVER")
				.build();
		instrumentService.transactionalProRequiresNew(opEntity);
	}

}
