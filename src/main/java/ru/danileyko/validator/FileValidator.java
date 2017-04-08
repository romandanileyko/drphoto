package ru.danileyko.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.sym.error;

/**
 * Created by danil on 25.03.2017.
 */
@Component
public class FileValidator implements Validator {
    @Override
    public void validate(Object uploadedFile, Errors errors) {
        MultipartFile file = (MultipartFile) uploadedFile;

        if (file.getSize() == 0) {
            errors.rejectValue("photo", "uploadForm.selectFile");
        }
        if(!(file.getContentType().toLowerCase().equals("image/jpg")
                || file.getContentType().toLowerCase().equals("image/jpeg")
                || file.getContentType().toLowerCase().equals("image/png"))){
            errors.rejectValue("photo", "format.file");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
