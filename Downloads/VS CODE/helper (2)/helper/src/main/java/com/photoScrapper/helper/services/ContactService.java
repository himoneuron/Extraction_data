package com.photoScrapper.helper.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photoScrapper.helper.dto.ContactDTO;
import com.photoScrapper.helper.entity.Contact;
import com.photoScrapper.helper.repository.ContactRepository;

@Service
public class ContactService {

  private final ContactRepository repo;

  public ContactService(ContactRepository repo) {
    this.repo = repo;
  }

  @Transactional
  public List<ContactDTO> saveAll(List<ContactDTO> dtos, String sourceFile, String rawText) {
    List<ContactDTO> saved = new ArrayList<>();

    for (ContactDTO dto : dtos) {
      repo.findByPhone(dto.getPhone()).ifPresentOrElse(existing -> {
        // (This is your existing logic for updating names)
        String newName = dto.getName();
        String oldName = existing.getName();
        if (newName!= null && (oldName == null || newName.length() > oldName.length())) {
          existing.setName(newName);
        }
        
        // --- ADD THIS LOGIC ---
        // If we found an email and the existing contact doesn't have one, update it.
        if (dto.getEmail()!= null && existing.getEmail() == null) {
            existing.setEmail(dto.getEmail());
        }
        repo.save(existing);
        
      }, () -> {
        // (This is your logic for creating a new contact)
        Contact c = new Contact();
        c.setName(dto.getName());
        c.setPhone(dto.getPhone());
        
        // --- ADD THIS LINE ---
        c.setEmail(dto.getEmail()); 
        
        c.setSourceFile(sourceFile);
        c.setRawSnippet(rawText == null? null :
            rawText.substring(0, Math.min(rawText.length(), 2000)));
        repo.save(c);
        saved.add(dto);
      });
    }

    return saved;
  }
}