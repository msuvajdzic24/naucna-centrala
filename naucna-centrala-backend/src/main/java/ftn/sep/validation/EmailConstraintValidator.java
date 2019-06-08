package ftn.sep.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailConstraintValidator implements ConstraintValidator<EmailValidation, String>{

    @Override
    public void initialize(EmailValidation string) {

    }

    @Override
    public boolean isValid(String customField, ConstraintValidatorContext ctx) {

        if(customField == null) {
            return false;
        }
        return customField.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }

}

