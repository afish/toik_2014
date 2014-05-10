package pl.agh.iet.i.toik.cloudsync.logic;

import java.util.List;

/**
 * Service using for obtaining cloud service providers.
 */
public interface CloudService {

	public abstract List<Cloud> getAllClouds();

	/** Adds the given cloud to this repository. */
	public abstract void registerCloud(Cloud cloud);

	/** Removes the given cloud to this repository. */
	public abstract void deregisterCloud(Cloud cloud);

}