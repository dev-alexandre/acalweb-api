package br.com.acalapi.controller.filtro;

import lombok.Data;

@Data
public class Filtro {

	private int size;
	private int page;
	private String name;
	private Boolean ativo;
	
	public Filtro() {
		this.size = 10;
		this.page = 0;
		this.name = "";
	}

}