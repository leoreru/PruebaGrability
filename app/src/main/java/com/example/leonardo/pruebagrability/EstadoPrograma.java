package com.example.leonardo.pruebagrability;

/**
 * Created by Leonardo on 25/08/2016.
 * Es un singleton
 */
public class EstadoPrograma {

	private static EstadoPrograma sEstadoPrograma;

	private boolean mEnLinea;
	private byte mIndResolucion;//0-pequeña 1-mediana 2-grande

	public static EstadoPrograma get() {
		if (sEstadoPrograma == null) {
			sEstadoPrograma = new EstadoPrograma();
		}
		return sEstadoPrograma;
	}

	private EstadoPrograma(){
		mEnLinea = true;
	}

	public boolean isEnLinea() {
		return mEnLinea;
	}

	public void setEnLinea(boolean enLinea) {
		mEnLinea = enLinea;
	}

	public byte getIndResolucion() {
		return mIndResolucion;
	}

	public void setIndResolucion(byte indResolucion) {
		mIndResolucion = indResolucion;
	}
}
