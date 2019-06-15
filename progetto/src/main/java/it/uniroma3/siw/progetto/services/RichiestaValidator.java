package it.uniroma3.siw.progetto.services;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.progetto.models.Richiesta;

public class RichiestaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Richiesta.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telefono", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codiceFiscale", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "indirizzo", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "civico", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "paese", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "regione", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cap", "required");
	}

}
