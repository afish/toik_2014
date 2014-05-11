package pl.agh.iet.i.toik.cloudsync.logic;

import java.io.Serializable;

/**
 * Simple key-value persistent storage.
 */
public interface PersistenceService {
	/** Each component should use unique prefix, e.g. logic can use "logic", GUI can use "gui". */
	public abstract <T extends Serializable> void put(String prefix, String id, T obj);

	/** Each component should use unique prefix, e.g. logic can use "logic", GUI can use "gui". */
	public abstract <T extends Serializable> T get(String prefix, String id);

	/** Each component should use unique prefix, e.g. logic can use "logic", GUI can use "gui". */
	public abstract void remove(String prefix, String id);

    /** Each component should use unique prefix, e.g. logic can use "logic", GUI can use "gui". */
    public abstract boolean has(String prefix, String id);

    /** Each component should use unique prefix, e.g. logic can use "logic", GUI can use "gui". */
    public abstract int getNextFromSequence(String prefix, String sequenceName);
}