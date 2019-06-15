package it.uniroma3.siw.progetto.services;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.progetto.models.Foto;

public class FotoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Foto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
	}

}
