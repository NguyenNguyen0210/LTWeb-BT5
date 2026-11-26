package vn.edu.hcmute.bt5.web;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
class FormBinding {
    @InitBinder
    void bindForms(WebDataBinder binder) {
        binder.setDeclarativeBinding(true);
    }
}
