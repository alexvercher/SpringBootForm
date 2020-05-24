package com.avc.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avc.springboot.form.app.services.IPaisService;

@Component
public class PaisPropertyEditor extends PropertyEditorSupport{

	@Autowired
	private IPaisService paisService;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text != null && text.length() > 0) {
			try {
				Integer id = Integer.parseInt(text);
				this.setValue(paisService.buscarId(id));
			} catch (NumberFormatException e) {
				setValue(null);
			}
		}else {
			setValue(null);
		}

	}
	
}
