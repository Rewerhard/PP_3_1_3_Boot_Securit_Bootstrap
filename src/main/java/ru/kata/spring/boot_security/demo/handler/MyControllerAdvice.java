package ru.kata.spring.boot_security.demo.handler;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class MyControllerAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    public String entityNotFound(EntityNotFoundException ex, Model model) {
        model.addAttribute("showModaltip", true);
        model.addAttribute("name", ex.getClass().getSimpleName());
        model.addAttribute("message", "Пользователь не найден");
        return "redirect:/admin/list";
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public String constraintViolationException(SQLIntegrityConstraintViolationException ex, Model model) {
        model.addAttribute("showModaltip", true);
        model.addAttribute("name", ex.getClass().getSimpleName());
        model.addAttribute("message", "Данный @email уже занят");
        return "redirect:/admin/list";
    }
}



