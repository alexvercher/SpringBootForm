package com.avc.springboot.form.app.services;

import java.util.List;

import com.avc.springboot.form.app.models.domain.Pais;

public interface IPaisService {

	public List<Pais> listar();
	public Pais buscarId(Integer id);
}
