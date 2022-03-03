package model;

import java.io.Serializable;

public class Paquete<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	int opcion;
	T objeto;
	double cantidad;
	Boolean resultado;


	public Boolean getResultado() {
		return resultado;
	}

	public void setResultado(Boolean resultado) {
		this.resultado = resultado;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public int getOpcion() {
		return opcion;
	}

	public void setOpcion(int opcion) {
		this.opcion = opcion;
	}

	public T getObjeto() {
		return objeto;
	}

	public void setObjeto(T objeto) {
		this.objeto = objeto;
	}

	@Override
	public String toString() {
		return "Paquete [opcion=" + opcion + ", objeto=" + objeto + ", cantidad=" + cantidad + "]";
	}

}
