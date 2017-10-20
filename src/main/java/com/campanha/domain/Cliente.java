package com.campanha.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Cliente{
	
	@Id
	int id;
	
	String nome;	
	String email;	
	Date dataNasc;
	int idTimeCoracao;

	List<Campanha> campanhas;
	
	public Cliente() {
		super();
	}
	
	public Cliente(int i, String nome, int idTimeCoracao, Date dataNasc, String email) {
		super();
		this.id = i;
		this.nome = nome;
		this.idTimeCoracao = idTimeCoracao;
		this.dataNasc = dataNasc;
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getIdTimeCoracao() {
		return idTimeCoracao;
	}
	public void setIdTimeCoracao(int idTimeCoracao) {
		this.idTimeCoracao = idTimeCoracao;
	}
	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Campanha> getCampanhas() {
		return campanhas;
	}

	public void setCampanhas(List<Campanha> campanhas) {
		this.campanhas = campanhas;
	}
	
	
}