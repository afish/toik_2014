package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.accounts;

import java.util.HashMap;
import java.util.Map;

import pl.agh.iet.i.toik.cloudsync.logic.CloudType;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class CloudAccountContent extends HorizontalLayout  implements AccountPropertiesProvider {

	
	private AccountPropertiesProvider provider;

	public CloudAccountContent(CloudType cloudType) {
		this.provider = getProvider(cloudType);
		addComponent(provider);
		setSpacing(true);
	}
	
	public void setCloudType(CloudType cloudType){
		removeComponent(provider);
		provider = getProvider(cloudType);
		addComponent(provider);
	}
	
	private AccountPropertiesProvider getProvider(CloudType cloudType) {
		switch(cloudType){
			case DROPBOX:
			case ONEDRIVE:
			case GOOGLEDRIVE:
				return new GoogleCloudAccountContent();
		}
		return new GoogleCloudAccountContent();
	}

	private String getCloudLink(CloudType cloudType) {
		switch(cloudType){
			case GOOGLEDRIVE:
			return "https://accounts.google.com/o/oauth2/auth?access_type=online&approval_prompt=auto&client_id=1003330706141-633o0oabcinl1b18cajq7ta9koremm1s.apps.googleusercontent.com&redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=code&scope=https://www.googleapis.com/auth/drive ";
		case DROPBOX:
			break;
		case ONEDRIVE:
			break;
		
		}
		return "";
	}

	private class GoogleCloudAccountContent extends VerticalLayout implements AccountPropertiesProvider {

		private Label linkLabel;
		private TextField tokenField;

		public GoogleCloudAccountContent() {
			setSpacing(true);
			linkLabel = new Label();
			linkLabel.setContentMode(ContentMode.HTML);
			linkLabel.setValue("<a href='"+getCloudLink(CloudType.GOOGLEDRIVE)+"' target = '_blank'> Get token </a>" );
			tokenField = new TextField("Token");
			addComponent(linkLabel);
			addComponent(tokenField);
		}
		@Override
		public Map<String, Object> getAccountProperties() {
			Map<String, Object> props = new HashMap<String,Object>();
			props.put("cloud.google.code", tokenField.getValue());
			return props;
		}

	}

	@Override
	public Map<String, Object> getAccountProperties() {
		return provider.getAccountProperties();
	}
}
