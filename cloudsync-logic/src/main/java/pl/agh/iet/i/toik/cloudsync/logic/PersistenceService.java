package pl.agh.iet.i.toik.cloudsync.logic;

/**
 * Simple key-value persistent storage.
 */
public interface PersistenceService {
	public abstract <T> void put(String id, T obj);

	public abstract <T> T get(String id);

	public abstract void remove(String id);
}