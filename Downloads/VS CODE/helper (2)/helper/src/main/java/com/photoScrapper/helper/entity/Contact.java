package com.photoScrapper.helper.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;
    
    // --- ADD THIS FIELD ---
    private String email;

    private String sourceFile;

    @Column(length = 2000)
    private String rawSnippet;

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getSourceFile() { return sourceFile; }
    public String getRawSnippet() { return rawSnippet; }

    // --- ADD GETTER FOR EMAIL ---
    public String getEmail() { return email; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setSourceFile(String sourceFile) { this.sourceFile = sourceFile; }
    public void setRawSnippet(String rawSnippet) { this.rawSnippet = rawSnippet; }
    
    // --- ADD SETTER FOR EMAIL ---
    public void setEmail(String email) { this.email = email; }
    
    // --- Add a no-arg constructor (JPA needs this) ---
    public Contact() {
    }

    // (Keep your existing equals and hashCode methods here)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass()!= o.getClass()) return false;
        Contact contact = (Contact) o;
        return phone!= null? phone.equals(contact.phone) : false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone);
    }
}