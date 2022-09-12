package br.com.clone.unionmangas.service;

import java.time.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import br.com.clone.unionmangas.controller.AuthorController;
import br.com.clone.unionmangas.dto.author.AuthorGetDto;
import br.com.clone.unionmangas.exception.NegocioException;
import br.com.clone.unionmangas.model.Author;
import br.com.clone.unionmangas.repository.AuthorRepository;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Page<AuthorGetDto> findByName(final Pageable pageable, final String name) {
        final Page<AuthorGetDto> response = this.authorRepository.findByName(pageable, name).map(AuthorGetDto::of);
        response.forEach(r -> r.add(linkTo(methodOn(AuthorController.class).findById(r.getIdAuthor())).withSelfRel()));
        return response;
    }

    public Author findByName(final String name) {
        var response = this.authorRepository.findByName(name);
        return response;
    }

    public AuthorGetDto findById(final Long idAuthor) {
        final var db = this.authorRepository.findById(idAuthor)
                .orElseThrow(() -> new NegocioException("Author not found"));

        final var response = AuthorGetDto.of(db);
        response.add(linkTo(methodOn(AuthorController.class).findById(idAuthor)).withSelfRel());
        return response;
    }

    public AuthorGetDto create(final Author author) {
        author.setAge(this.calcAge(author.getBirthdate()));
        final var db = this.authorRepository.save(author);
        final var response = AuthorGetDto.of(db);
        response.add(linkTo(methodOn(AuthorController.class).findById(db.getIdAuthor())).withSelfRel());
        return response;
    }

    private Integer calcAge(LocalDate birthdate) {
        final var currentDate = LocalDate.now();
        final Period period = Period.between(birthdate, currentDate);
        Integer age = period.getYears();
        return age;
    }

}
