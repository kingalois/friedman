package at.am.friedman.data;

public final class IdBasedObject implements IdBasedObjectInterface {

	private int id;

	public static IdBasedObject of(int id) {
		return new IdBasedObject(id);
	}

	private IdBasedObject(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		IdBasedObject other = (IdBasedObject) obj;
		if (id != other.getId())
			return false;
		return true;
	}

}
