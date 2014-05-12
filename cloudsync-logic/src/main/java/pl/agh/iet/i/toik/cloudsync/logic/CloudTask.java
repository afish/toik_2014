package pl.agh.iet.i.toik.cloudsync.logic;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Class representing async cloud task. See {@link FutureTask} class for detailed API.
 */
public abstract class CloudTask<T> extends FutureTask<T> {
	public CloudTask(Callable<T> callable) {
		super(callable);
	}

	/** Returns progress ratio as a number in range [0.0, 1.0]. */
	public abstract float getProgress();
}
