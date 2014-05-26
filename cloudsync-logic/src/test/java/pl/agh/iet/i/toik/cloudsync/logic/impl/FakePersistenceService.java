package pl.agh.iet.i.toik.cloudsync.logic.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import pl.agh.iet.i.toik.cloudsync.logic.PersistenceService;

public class FakePersistenceService implements PersistenceService {
	private Map<String, Object> map = new HashMap<>();
	
	@Override
	public void remove(String prefix, String id) {
		map.remove(prefix + id);
	}

	@Override
	public <T extends Serializable> void put(String prefix, String id, T obj) {
		map.put(prefix + id, obj);
	}

	@Override
	public boolean has(String prefix, String id) {
		return map.containsKey(prefix + id);
	}

	@Override
	public int getNextFromSequence(String prefix, String sequenceName) {
		return 0;
	}

	@Override
	public <T extends Serializable> T get(String prefix, String id) {
		return (T) map.get(prefix + id);
	}
}
