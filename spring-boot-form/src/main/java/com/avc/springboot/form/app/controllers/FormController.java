package com.avc.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import java.util.HashMap;
//import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.avc.springboot.form.app.editors.NombreMayusculaEditor;
import com.avc.springboot.form.app.editors.PaisPropertyEditor;
import com.avc.springboot.form.app.editors.RolePropertyEditor;
import com.avc.springboot.form.app.models.domain.Pais;
import com.avc.springboot.form.app.models.domain.Role;
import com.avc.springboot.form.app.models.domain.Usuario;
import com.avc.springboot.form.app.services.IPaisService;
import com.avc.springboot.form.app.services.IRoleService;
import com.avc.springboot.form.app.validation.UsuarioValidador;

@Controller
@SessionAttributes("usuario")
public class FormController {

	@Autowired
	private UsuarioValidador validador;
	
	@Autowired
	private IPaisService paisService;
	
	@Autowired
	private PaisPropertyEditor paisEditor; 
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private RolePropertyEditor roleEditor;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validador);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		//binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(Date.class, "fechaNacimiento" ,new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(String.class, new NombreMayusculaEditor());
		
		binder.registerCustomEditor(Pais.class, "pais", paisEditor);
		binder.registerCustomEditor(Role.class, "roles", roleEditor);
	}
	
	@ModelAttribute("generos")
	public List<String> generos(){
		return Arrays.asList("Hombre", "Mujer");
	}
	
	@ModelAttribute("listaRolesString")
	public List<String> listaRolesString(){
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_USER");
		roles.add("ROLE_MODERATOR");
		return roles;
	}
	
	@ModelAttribute("listaRolesMap")
	public Map<String, String> listaRolesMap(){
		Map<String, String> roles = new HashMap<String, String>();
		roles.put("ROLE_ADMIN", "Administrador");
		roles.put("ROLE_USER", "Usuario");
		roles.put("ROLE_MODERATOR", "Moderador");
		return roles;
	}	
	
	@ModelAttribute("listaRoles")
	public List<Role> listaRoles(){
		return roleService.listar();
	}
	
	@ModelAttribute("listaPaises")
	public List<Pais> listaPaises(){
		return paisService.listar();
		/*return Arrays.asList(
				new Pais(1, "ES", "España"),
				new Pais(2, "MX", "Mexico"), 
				new Pais(3, "CL", "Chile"), 
				new Pais(4, "CO", "Colombia"), 
				new Pais(5, "VE", "Venezuela")
				);*/
	}
	
	@ModelAttribute("paises")
	public List<String> paises(){
		return Arrays.asList("España", "Mexico", "Chile", "Colombia", "Venezuela");
	}
	
	@ModelAttribute("paisesMap")
	public Map<String, String> paisesMap(){
		Map<String, String> paises = new HashMap<String, String>();
		paises.put("ES", "España");
		paises.put("MX", "México");
		paises.put("CL", "Chile");
		paises.put("CO", "Colombia");
		paises.put("VE", "Venezuela");
		return paises;
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		Usuario usuario = new Usuario();
		usuario.setId("12.555.555-K");
		usuario.setNombre("Juan");
		usuario.setApellido("Martinez");
		usuario.setUsername("user");
		usuario.setEmail("s@s");
		usuario.setCuenta(10);
		usuario.setHabilitar(true);
		usuario.setValorSecreto("No se puede decir");
		usuario.setPais(new Pais(2, "MX", "Mexico"));
		usuario.setRoles(Arrays.asList(new Role(2, "Usuario", "ROLE_USER")));
		model.addAttribute("titulo", "Formulario usuarios");	
		model.addAttribute("usuario", usuario);
		return "form";
	}

	@PostMapping("/form")
	public String procesarForm(
			//@Valid @ModelAttribute("user") Usuario usuario,	
			@Valid Usuario usuario,
			BindingResult result, 
			Model model) {

		//validador.validate(usuario, result);
		
		if (result.hasErrors()) {
			/*
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			model.addAttribute("error", errores);*/
			model.addAttribute("titulo", "Resultado del formulario");
			return "form";
		}
		//model.addAttribute("usuario", usuario);
		return "redirect:/ver";
	}
	
	@GetMapping("/ver")
	public String ver(@SessionAttribute(name = "usuario", required = false) Usuario usuario, Model model, SessionStatus status) {
		
		if (usuario==null) {
			return "redirect:/form";
		}
		
		model.addAttribute("titulo", "Resultado del formulario");
		
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
