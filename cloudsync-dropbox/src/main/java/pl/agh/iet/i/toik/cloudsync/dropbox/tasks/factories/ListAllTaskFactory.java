package pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.ListAllTask;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.params.ListAllTaskParams;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;

public class ListAllTaskFactory {

	public ListAllTask create(DbxClient dbxClient, CloudFile directory) {
		ListAllTaskParams listAllTaskParams = new ListAllTaskParams(directory);
		Callable<List<CloudFile>> callable = this.getCallable(dbxClient, listAllTaskParams);
		ListAllTask listAllTask = new ListAllTask(callable);
		return listAllTask;
	}

	private Callable<List<CloudFile>> getCallable(final DbxClient dbxClient, final ListAllTaskParams listAllTaskParams) {
		final String directory = listAllTaskParams.getDirectory().getFullPath();

		return new Callable<List<CloudFile>>() {
			@Override
			public List<CloudFile> call() throws Exception {
				try {
					List<CloudFile> files = listAllDirectories(dbxClient, directory);
					return files;
				} catch (DbxException e) {
					// TODO: logger
				}
				return new LinkedList<CloudFile>();
			}
		};
	}
	
	private List<CloudFile> listAllDirectories(DbxClient dbxClient, String root) throws DbxException {
		List<CloudFile> files = new LinkedList<CloudFile>();
        DbxEntry.WithChildren children = dbxClient.getMetadataWithChildren(root);
        if (children == null || children.children == null || children.children.isEmpty()) {
            return files;
        }
        for (DbxEntry child : children.children) {
        	String name = child.name;
        	Boolean isDirectory = child.isFolder();
        	String fullPath = child.path; //TODO: + "/" + child.name ? 
        	//TODO: id
        	String id = "0";
        	//TODO: get size
        	Long size = 0L;
        	//TODO: creation date
        	Date creationDate = new Date();
        	CloudFile file = new CloudFile(name, creationDate, isDirectory, fullPath, id, size);
            files.add(file);
        }
		return files;
	}

}
