package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.accounts;

import java.util.HashMap;
import java.util.Map;

import pl.agh.iet.i.toik.cloudsync.logic.CloudType;

import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
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
				return new DropboxCloudAccountContent();
			case ONEDRIVE:
				return new MicrosoftCloudAccountContent();
			case GOOGLEDRIVE:
				return new GoogleCloudAccountContent();
			default:
				return null;
		}
	}

	private String getCloudLink(CloudType cloudType) {
		switch (cloudType) {
		case GOOGLEDRIVE:
			return "https://accounts.google.com/o/oauth2/auth?access_type=online&approval_prompt=auto&client_id=1003330706141-633o0oabcinl1b18cajq7ta9koremm1s.apps.googleusercontent.com&redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=code&scope=https://www.googleapis.com/auth/drive ";
		case DROPBOX:
			return "https://www.dropbox.com/1/oauth2/authorize?locale=pl_PL&client_id=hn2bx52zyvavui7&response_type=code";
		case ONEDRIVE:
			return "https://login.live.com/oauth20_authorize.srf?client_id=%s&scope=wl.offline_access+wl.skydrive_update&response_type=code&redirect_uri=https://login.live.com/oauth20_desktop.srf";
		default:
			return "";
		}
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
			props.put("cloud.type", CloudType.GOOGLEDRIVE);
			return props;
		}

	}
	
	private class DropboxCloudAccountContent extends VerticalLayout implements AccountPropertiesProvider {

		private Label linkLabel;
		private TextField tokenField;

		public DropboxCloudAccountContent() {
			setSpacing(true);
			linkLabel = new Label();
			linkLabel.setContentMode(ContentMode.HTML);
			linkLabel.setValue("<a href='"+getCloudLink(CloudType.DROPBOX)+"' target = '_blank'> Get token </a>" );
			tokenField = new TextField("Token");
			addComponent(linkLabel);
			addComponent(tokenField);
		}
		@Override
		public Map<String, Object> getAccountProperties() {
			Map<String, Object> props = new HashMap<String,Object>();
			props.put("cloud.dropbox.code", tokenField.getValue());
			props.put("cloud.type", CloudType.DROPBOX);
			return props;
		}

	}

	private class MicrosoftCloudAccountContent extends VerticalLayout implements AccountPropertiesProvider {

		private Link link;
		private TextField clientIdField;
		private TextField clientSecretField;
		private TextField clientCodeField;

		private final String htmlFormat = getCloudLink(CloudType.ONEDRIVE);
		
		public MicrosoftCloudAccountContent() {
			setSpacing(true);
			link = new Link();
			link.setCaption("Get token");
			link.setTargetName("_blank");
			
			clientIdField = new TextField("Client ID");
			clientSecretField = new TextField("Client secret");
			clientCodeField = new TextField("Code");
			addComponent(link);
			addComponent(clientIdField);
			addComponent(clientSecretField);
			addComponent(clientCodeField);
			
			clientIdField.addTextChangeListener(new TextChangeListener() {
				
				@Override
				public void textChange(TextChangeEvent event) {
					String linkHtml = String.format(htmlFormat, event.getText());
					link.setResource(new ExternalResource(linkHtml));
					
				}
			});
			
		}
		@Override
		public Map<String, Object> getAccountProperties() {
			Map<String, Object> props = new HashMap<String,Object>();
			props.put("client_id", clientIdField.getValue());
			props.put("client_secret", clientSecretField.getValue());
			props.put("code", clientCodeField.getValue());
			props.put("cloud.type", CloudType.ONEDRIVE);
			return props;
		}

	}
	
	@Override
	public Map<String, Object> getAccountProperties() {
		return provider.getAccountProperties();
	}
}
