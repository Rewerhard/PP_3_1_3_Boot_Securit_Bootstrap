package ru.kata.spring.boot_security.demo.controller.advice;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class MyControllerAdvice {

   /* @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public String constraintViolationException(SQLIntegrityConstraintViolationException ex, Model model) {
        model.addAttribute("showModaltip" , true);
        return "redirect:/admin/list";
    }*/
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public String constraintViolationException(SQLIntegrityConstraintViolationException ex, Model model) {
        model.addAttribute("name" , ex.getClass().getSimpleName());
        model.addAttribute("message", ex.getMessage());
        return "error";
    }
}



