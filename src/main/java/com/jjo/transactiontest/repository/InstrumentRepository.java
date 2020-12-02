package com.jjo.transactiontest.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jjo.transactiontest.model.InstrumentEntity;

public interface InstrumentRepository extends JpaRepository<InstrumentEntity, UUID> {

}
