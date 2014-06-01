package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.accounts;

import java.util.Map;

import com.vaadin.ui.Component;

public interface AccountPropertiesProvider  extends Component {

	public Map<String, Object> getAccountProperties();
	
	public void setAccountProperties(Map<String, Object> properties);

	public String getTokenPropertyId();

	public String getToken();

	public void setTokenOnly(boolean tokenOnly);
}

