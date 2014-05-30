package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.files;

import pl.agh.iet.i.toik.cloudsync.gui.components.BeanItemTable;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;

import com.vaadin.data.Property;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Table;

public class FilesTable extends BeanItemTable<CloudFile> {
	

	public FilesTable() {
		super(CloudFile.class);
		addGeneratedColumn("icon", new ColumnGenerator() {
			
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				CloudFile file = (CloudFile) itemId;
				if(file != null) {
					if(file instanceof BackFile)
						return createIcon("back.ico");
					else if(file.isDirectory())
						return createIcon("folder.ico");
					else 
						return createIcon("file.ico");		
				}
				return null;
			}
		});
		setVisibleColumns(new Object[]{"icon","name","size","creationDate"});
		setColumnHeader("creationDate", "Creation Date");
		setColumnHeader("icon", "");
		setSortEnabled(false);
		
	}


	@Override
	public boolean removeAllItems() {
		boolean result = super.removeAllItems();
		addItem(new BackFile());
		return result;
	}


	@Override
	protected String formatPropertyValue(Object rowId, Object colId,
			Property<?> property) {
		if(property.getType().equals(Long.class)){
			if( ((Long)property.getValue()).compareTo(Long.valueOf(-1l))== 0)
				return "N/A";
					
		}else if(property.getValue() == null) {
			return "N/A";
		}
		
		return super.formatPropertyValue(rowId, colId, property);
	}
	
	
	private Image createIcon(String icon) {
		Image image = new Image();
		image.setSource(new ThemeResource(icon));
		return image;
	}
	
	
	
	private class BackFile extends CloudFile {

		public BackFile() {
			super("..", null, true, null, "-1", -1l);
			
		}
		
	}

	

}
