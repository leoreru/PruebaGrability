package com.example.leonardo.pruebagrability;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo on 23/08/2016.
 * Es una clase singleton
 */
public class InfoProgramas {
	private static InfoProgramas sInfoProgramas;
	private List<String> mCategorias;
	private List<InfoPrograma> mProgramas;

	public static InfoProgramas get() {
		if (sInfoProgramas == null) {
			sInfoProgramas = new InfoProgramas();
		}
		return sInfoProgramas;
	}

	private InfoProgramas() {
		mCategorias = new ArrayList<String>();
		mProgramas = new ArrayList<InfoPrograma>();
	}

	public List<String> getCategorias() {
		return mCategorias;
	}

	public void setCategorias(List<String> categorias) {
		mCategorias = categorias;
	}

	public List<InfoPrograma> getProgramas() {
		return mProgramas;
	}

	public void setProgramas(List<InfoPrograma> programas) {
		mProgramas = programas;
	}
}
