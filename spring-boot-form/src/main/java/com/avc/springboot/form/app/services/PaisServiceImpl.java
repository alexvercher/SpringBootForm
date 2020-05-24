package com.avc.springboot.form.app.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.avc.springboot.form.app.models.domain.Pais;

@Service
public class PaisServiceImpl implements IPaisService {

	private List<Pais> lista;
	
	
	public PaisServiceImpl() {
		this.lista = Arrays.asList(
				new Pais(1, "ES", "España"),
				new Pais(2, "MX", "Mexico"), 
				new Pais(3, "CL", "Chile"), 
				new Pais(4, "CO", "Colombia"), 
				new Pais(5, "VE", "Venezuela")
				);
	}

	@Override
	public List<Pais> listar() {
		return lista;
	}

	@Override
	public Pais buscarId(Integer id) {
		Pais resultado = null;
		
		for (Pais pais : lista) {
			if(id ==  	pais.getId()) {
				resultado = pais;
				break;
			}
		}
		return resultado;
	}

}
