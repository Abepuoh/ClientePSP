package model;

import java.io.Serializable;

public class Cuenta implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String numero;
	private Double saldo;
	private Usuario usuario;
	
	public Cuenta() {
		this(-1L,"default",-1.1,new Usuario());
	}
	
	public Cuenta(Long id, String numero, Double saldo, Usuario usuario) {
		super();
		this.id = id;
		this.numero = numero;
		this.saldo = saldo;
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}

	public String getNumero() {
		return numero;
	}

	public Double getSaldo() {
		return saldo;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuenta other = (Cuenta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cuenta [id=" + id + ", numero=" + numero + ", saldo=" + saldo + ", usuario=" + usuario + "]";
	}

    public Object getNumeroCuentaProperty() {
        return null;
    }
	
}
