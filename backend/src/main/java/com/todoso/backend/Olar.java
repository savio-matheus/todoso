package com.todoso.backend;

class Olar {
	private final String nome;

	public Olar(String nome) {
		this.nome = nome;
	}

	public String getnome() {
		return (this.nome == null) ? "" : this.nome;
	}
}