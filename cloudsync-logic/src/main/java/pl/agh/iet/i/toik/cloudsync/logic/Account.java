package pl.agh.iet.i.toik.cloudsync.logic;

import java.io.Serializable;
import java.util.Map;

/**
 * Account POJO class holding all its information.
 */
public class Account implements Serializable{
	private static final long serialVersionUID = 1L;
	/** Globally unique ID */
	private final String id;
	/** Human readable name */
	private final String name;
	/** Property list. Feel free to insert anything here. */
	private final Map<String, Object> propList;

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Map<String, Object> getPropertyList() {
		return this.propList;
	}

	public Account(String id, String name, Map<String, Object> propList) {
		this.id = id;
		this.name = name;
		this.propList = propList;
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
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", propList="
				+ propList + "]";
	}
}
