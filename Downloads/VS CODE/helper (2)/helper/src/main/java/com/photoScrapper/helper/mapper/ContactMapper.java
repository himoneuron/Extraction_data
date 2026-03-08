package com.photoScrapper.helper.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.photoScrapper.helper.dto.ContactDTO;
import com.photoScrapper.helper.entity.Contact;

@Mapper(componentModel = "spring")
public interface ContactMapper {

  @Mappings({
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "sourceFile", ignore = true),
      @Mapping(target = "rawSnippet", ignore = true)
  })
  Contact toEntity(ContactDTO dto);

    // Simple entity -> DTO mapping
    ContactDTO toDto(Contact entity);
}