package utils;

import model.Administrador;

public class AdministradorSingleton {
	
	private Administrador usuario;
	private final static AdministradorSingleton INSTANCE = new AdministradorSingleton();

	public AdministradorSingleton() {
		  }

	public static AdministradorSingleton getInstance() {
		return INSTANCE;
	}

	public void setAdmin(Administrador u) {
		this.usuario = u;
	}

	public Administrador getAdmin() {
		return this.usuario;

	}
}
