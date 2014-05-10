package pl.agh.iet.i.toik.cloudsync.logic;

/**
 * Simple key-value persistent storage.
 */
public interface PersistenceService {
	/** Each component should use unique prefix, e.g. logic can use "logic", GUI can use "gui". */
	public abstract <T> void put(String prefix, String id, T obj);

	/** Each component should use unique prefix, e.g. logic can use "logic", GUI can use "gui". */
	public abstract <T> T get(String prefix, String id);

	/** Each component should use unique prefix, e.g. logic can use "logic", GUI can use "gui". */
	public abstract void remove(String prefix, String id);
}