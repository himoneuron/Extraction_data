package com.photoScrapper.helper.dto;

/**
 * Data Transfer Object for Contact information.
 * This class is manually implemented with constructors, getters, and setters.
 */
public class ContactDTO {
    private String name;
    private String phone;
    private String email;
    private String rawSnippet;

    /**
     * Default no-argument constructor.
     * Required for deserialization (e.g., by Jackson).
     */
    public ContactDTO() {
    }

    /**
     * Constructor for primary fields.
     *
     * @param name  The contact's name.
     * @param phone The contact's phone number.
     */
    public ContactDTO(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    /**
     * Constructor including the raw snippet.
     *
     * @param name       The contact's name.
     * @param phone      The contact's phone number.
     * @param rawSnippet The raw text block from which the contact was extracted.
     */
    public ContactDTO(String name, String phone, String rawSnippet) {
        this.name = name;
        this.phone = phone;
        this.rawSnippet = rawSnippet;
    }

    /**
     * Full constructor including all fields.
     *
     * @param name       The contact's name.
     * @param phone      The contact's phone number.
     * @param email      The contact's email address.
     * @param rawSnippet The raw text block.
     */
    public ContactDTO(String name, String phone, String email, String rawSnippet) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.rawSnippet = rawSnippet;
    }

    // --- Manual Getters and Setters ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getRawSnippet() {
        return rawSnippet;
    }

    public void setRawSnippet(String rawSnippet) {
        this.rawSnippet = rawSnippet;
    }

    // --- Utility Methods ---

    @Override
    public String toString() {
        return "ContactDTO{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}