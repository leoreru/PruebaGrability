package com.example.leonardo.pruebagrability;

import java.io.Serializable;

/**
 * Created by Leonardo on 23/08/2016.
 */
public class InfoPrograma implements Serializable {
	private String mNombre;
	private String mCategoria;
	private String mDescripcion;
	private String mEmpresa;
	private String mUrlsImagenes[];
	private String mRutasImagenes[];

	public InfoPrograma(){
		mUrlsImagenes = new String[3];
		mRutasImagenes = new String[3];
	}

	public String getNombre() {
		return mNombre;
	}

	public void setNombre(String nombre) {
		mNombre = nombre;
	}

	public String getCategoria() {
		return mCategoria;
	}

	public void setCategoria(String categoria) {
		mCategoria = categoria;
	}

	public String getDescripcion() {
		return mDescripcion;
	}

	public void setDescripcion(String descripcion) {
		mDescripcion = descripcion;
	}

	public String getEmpresa() {
		return mEmpresa;
	}

	public void setEmpresa(String empresa) {
		mEmpresa = empresa;
	}

	public String[] getUrlsImagenes() {
		return mUrlsImagenes;
	}

	public void setUrlsImagenes(String[] urlsImagenes) {
		mUrlsImagenes = urlsImagenes;
	}

	public String[] getRutasImagenes() {
		return mRutasImagenes;
	}

	public void setRutasImagenes(String[] rutasImagenes) {
		mRutasImagenes = rutasImagenes;
	}

}
