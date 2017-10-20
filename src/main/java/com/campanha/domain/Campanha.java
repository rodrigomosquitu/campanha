package com.campanha.domain;

import java.text.ParseException;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.campanha.util.Util;

@Document
public class Campanha{
	
	@Id
	int id;
	
	String nome;	
	int idTimeCoracao;
	Date dataInicial;
	Date dataFinal;
	String vigencia;	

	public Campanha() {
		super();
	}
	
	public Campanha(int i, String nome, int idTimeCoracao, Date dataInicial, Date dataFinal) {
		super();
		this.id = i;
		this.nome = nome;
		this.idTimeCoracao = idTimeCoracao;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
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
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getVigencia() throws ParseException {
		return Util.getDateFormat(dataInicial) + " - " + Util.getDateFormat(dataFinal);
	}	
	
}