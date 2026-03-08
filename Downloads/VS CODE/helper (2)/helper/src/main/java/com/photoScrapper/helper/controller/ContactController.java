package com.photoScrapper.helper.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photoScrapper.helper.entity.Contact;
import com.photoScrapper.helper.repository.ContactRepository;

@RestController
@RequestMapping("/contacts")
public class ContactController {

  private final ContactRepository repo;

  public ContactController(ContactRepository repo) { this.repo = repo; }

  @GetMapping
  public List<Contact> list() { return repo.findAll(); }

  @GetMapping("/{id}")
  public ResponseEntity<Contact> get(@PathVariable Long id) {
    return repo.findById(id).map(ResponseEntity::ok)
       .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    repo.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}