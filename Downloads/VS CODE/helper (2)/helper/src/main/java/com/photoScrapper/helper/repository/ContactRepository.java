package com.photoScrapper.helper.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.photoScrapper.helper.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
  Optional<Contact> findByPhone(String phone);
  boolean existsByPhone(String phone);
}