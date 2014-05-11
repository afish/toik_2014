package pl.agh.iet.i.toik.cloudsync.logic;


import java.util.List;

/**
 * Service using for registering cloud service providers.
 */
public interface CloudService {

    /** Returns a list of public information of all registered clouds. */
    public abstract List<CloudInformation> getAllClouds();
	/**
	 * Adds the given cloud to this repository.
	 * 
	 * @throws IllegalArgumentException if a cloud with the same id was already registered.
	 */
	public abstract void registerCloud(Cloud cloud);

	/** Removes the given cloud to this repository. */
	public abstract void deregisterCloud(Cloud cloud);
}