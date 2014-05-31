package pl.agh.iet.i.toik.cloudsync.logic.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import pl.agh.iet.i.toik.cloudsync.logic.PersistenceService;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class PersistenceServiceImpl implements PersistenceService {

    private String dbFileName = "database.db";
	private static Logger logger = LoggerFactory.getLogger(PersistenceServiceImpl.class);

    private Map<String, Map<String, Integer>> sequences;
    private Map<String, Map<String, Serializable>> database;

    public PersistenceServiceImpl(){
        loadDatabase();
    }

    public PersistenceServiceImpl(String dbFileName){
        this.dbFileName = dbFileName;
        loadDatabase();
    }

	@Override
	public <T extends Serializable> void put(String prefix, String id, T obj) {
		logger.info("Putting key: " + id + ", value:" + obj + ", prefix:" + prefix);

        if (!database.containsKey(prefix)){
            database.put(prefix, new HashMap<String, Serializable>());
        }
        database.get(prefix).put(id, obj);
		saveDatabase();
	}

	@Override
	public <T extends Serializable> T get(String prefix, String id) {
		logger.info("Getting key: " + id + ", prefix:" + prefix);

        if (database.containsKey(prefix)){
            Map<String, Serializable> prefixDatabaseChunk = database.get(prefix);
            if (prefixDatabaseChunk.containsKey(id)){
                return (T)prefixDatabaseChunk.get(id);
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
	}

	@Override
	public void remove(String prefix, String id) {
		logger.info("Removing key: " + id + ", prefix:" + prefix);

        if (database.containsKey(prefix)){
            Map<String, Serializable> prefixDatabaseChunk = database.get(prefix);
            if (prefixDatabaseChunk.containsKey(id)){
                prefixDatabaseChunk.remove(id);
                saveDatabase();
            }
        }
	}

    @Override
    public boolean has(String prefix, String id) {
        logger.info("Checking existence of key: " + id + ", prefix:" + prefix);

        return database.containsKey(prefix) && database.get(prefix).containsKey(id);
    }

    @Override
    public int getNextFromSequence(String prefix, String sequenceName) {
        logger.info("Getting sequence: " + prefix + "," + sequenceName);

        if (!sequences.containsKey(prefix)){
            sequences.put(prefix, new HashMap<String, Integer>());
        }

        if (!sequences.get(prefix).containsKey(sequenceName)){
            sequences.get(prefix).put(sequenceName, 0);
        }

        int returnVal = sequences.get(prefix).get(sequenceName);
        sequences.get(prefix).put(sequenceName, returnVal + 1);
        saveDatabase();

        return returnVal;
    }

    private void saveDatabase(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dbFileName));
            oos.writeObject(database);
            oos.writeObject(sequences);
        } catch (IOException e) {
            logger.error("Unable to save to " + dbFileName);
            e.printStackTrace();
        }
    }

    private void loadDatabase(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dbFileName));
            database = (Map<String, Map<String, Serializable>>) ois.readObject();
            sequences = (Map<String, Map<String, Integer>>) ois.readObject();
        } catch (IOException e) {
            logger.warn("Unable to load " + dbFileName + ". Creating empty database.");
            database = new HashMap<String, Map<String, Serializable>>();
            sequences = new HashMap<String, Map<String, Integer>>();
            saveDatabase();
        } catch (ClassNotFoundException e) {
            logger.warn("Unable to deserialize " + dbFileName + ". Creating empty database.");
            database = new HashMap<String, Map<String, Serializable>>();
            sequences = new HashMap<String, Map<String, Integer>>();
            saveDatabase();
        }
    }
}
