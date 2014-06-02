package pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.DropboxCallable;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.ListAllTask;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.params.ListAllTaskParams;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxEntry.File;
import com.dropbox.core.DbxException;

public class ListAllTaskFactory {

	private static Logger logger = LoggerFactory.getLogger(ListAllTaskFactory.class);

	public ListAllTask create(DbxClient dbxClient, CloudFile directory) {
		ListAllTaskParams listAllTaskParams = new ListAllTaskParams(directory);
		DropboxCallable<List<CloudFile>> callable = this.getCallable(dbxClient, listAllTaskParams);
		ListAllTask listAllTask = new ListAllTask(callable);
		return listAllTask;
	}

	private DropboxCallable<List<CloudFile>> getCallable(final DbxClient dbxClient, final ListAllTaskParams listAllTaskParams) {
		final String directory = listAllTaskParams.getDirectory().getFullPath();

		return new DropboxCallable<List<CloudFile>>() {
			@Override
			public List<CloudFile> call() throws Exception {
				try {
					this.setProgress(0.1f);
					List<CloudFile> files = listAllDirectories(dbxClient, directory);
					this.setProgress(1.0f);
					return files;
				} catch (DbxException e) {
					logger.error("Problem with listing", e.getMessage());
				}
				return new LinkedList<CloudFile>();
			}
		};
	}

	private List<CloudFile> listAllDirectories(DbxClient dbxClient, String root) throws DbxException {
		List<CloudFile> files = new LinkedList<CloudFile>();
		DbxEntry.WithChildren entry = dbxClient.getMetadataWithChildren(root);
		if (entry == null || entry.children == null || entry.children.isEmpty()) {
			return files;
		}
		for (DbxEntry child : entry.children) {
			String name = child.name;
			Boolean isDirectory = child.isFolder();
			File dbxFile = !isDirectory ? (DbxEntry.File) child : null;
			String fullPath = child.path;
			if(fullPath.endsWith(name + "/" + name)) {
				//TODO: improve it
				String newPath = fullPath.replaceFirst(name + "/", "");
				fullPath = newPath;
			}
			// TODO: id
			String id = "0";
			Long size = isDirectory ? -1L : dbxFile.numBytes;
			// TODO: creation date
			Date creationDate = isDirectory ? new Date() : dbxFile.lastModified;
			CloudFile file = new CloudFile(name, creationDate, isDirectory, fullPath, id, size);
			files.add(file);
		}
		return files;
	}
}
