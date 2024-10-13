package com.example.catalogeservice.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class BookValidationTests {
    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public static String mockISBN = "1234567890";

    public static Book getBook(String number) {
        return new Book(number, "title", "author", 9.9);
    }

    @Test
    void happyCase() {
        var book = getBook(mockISBN);
        Set<ConstraintViolation<Book>> validations = validator.validate(book);
        assertThat(validations).isEmpty();
    }

    @Test
    void validationFailed() {
        var book = getBook("a1234567890");
        Set<ConstraintViolation<Book>> validations = validator.validate(book);
        assertThat(validations).hasSize(1);
        assertThat(validations.iterator().next().getMessage()).isEqualTo("The ISBN format must be valid.");
    }

}
