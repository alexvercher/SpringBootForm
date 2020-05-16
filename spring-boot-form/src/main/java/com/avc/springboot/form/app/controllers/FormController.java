package com.avc.springboot.form.app.controllers;

//import java.util.HashMap;
//import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.avc.springboot.form.app.models.domain.Usuario;
import com.avc.springboot.form.app.validation.UsuarioValidador;

@Controller
@SessionAttributes("usuario")
public class FormController {

	@Autowired
	private UsuarioValidador validador;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validador);
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		Usuario usuario = new Usuario();
		usuario.setId("123.55-K");
		usuario.setNombre("Juan");
		usuario.setApellido("Martinez");
		model.addAttribute("titulo", "Formulario usuarios");	
		model.addAttribute("usuario", usuario);
		return "form";
	}

	@PostMapping("/form")
	public String procesarForm(
			//@Valid @ModelAttribute("user") Usuario usuario,	
			@Valid Usuario usuario,
			BindingResult result, 
			Model model,
			SessionStatus status) {

		validador.validate(usuario, result);
		
		model.addAttribute("titulo", "Resultado del formulario");
		
		if (result.hasErrors()) {
			/*
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			model.addAttribute("error", errores);*/
			return "form";
		}
		model.addAttribute("usuario", usuario);
		status.setComplete();
		return "resultado";
	}
	
	/*
	@PostMapping("/form")
	public String procesarForm(
			@RequestParam(name = "username") String username,
			@RequestParam String password,
			@RequestParam String email,
			Model model) {
		
		Usuario usuario = new Usuario(username, password, email);
		
		model.addAttribute("titulo", "Resultado del formulario");
		model.addAttribute("usuario", usuario);
		return "resultado";
	}*/
	
}
