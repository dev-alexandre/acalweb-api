package br.com.acalapi.controller.filtro;

import lombok.Data;

@Data
public class Filtro {

	private int size;
	private int page;
	
	public Filtro() {
		this.size = 10;
		this.page = 0;
	}

}