package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.accounts;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.events.EventBusListenerMethod;
import org.vaadin.spring.i18n.I18N;

import pl.agh.iet.i.toik.cloudsync.gui.components.AbstractWindowView;
import pl.agh.iet.i.toik.cloudsync.gui.components.WindowView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.accounts.AddAccountWindow.AddAccountWindowPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.OpenAddWindowEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.Presenter;
import pl.agh.iet.i.toik.cloudsync.logic.CloudInformation;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@VaadinComponent
public class AddAccountWindow extends
		AbstractWindowView<AddAccountWindowPresenter> implements
		WindowView<AddAccountWindowPresenter> {

	@Autowired
	private I18N captions;
	private TextField accountNameField;
	private ComboBox cloudTypeComboBox;
	private Button cancelButton;
	private Button addButton;
	private CloudAccountContent cloudContent;

	public interface AddAccountWindowPresenter extends Presenter {

		public void addAccount(String name,
				Map<String, Object> properties);

		@EventBusListenerMethod
		public void onOpenAddAccountWindow(
				org.vaadin.spring.events.Event<OpenAddWindowEvent> event);

		public List<CloudInformation> getAllClouds();
	}

	@PostConstruct
	private void init() {

		setListeners();
	}

	private void setListeners() {
		addButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getPresenter().addAccount(accountNameField.getValue(),
						cloudContent.getAccountProperties());
				close();

			}
		});

		cancelButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				close();

			}
		});

		cloudTypeComboBox.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				cloudContent.setCloudType(((CloudInformation) event
						.getProperty().getValue()).getCloudType());

			}
		});
	}

	@Override
	protected Component createWindowContent() {
		VerticalLayout contentLayout = new VerticalLayout();
		contentLayout.setSpacing(true);
		contentLayout.setMargin(true);
		contentLayout
				.addComponent(cloudTypeComboBox = createCloudTypeComboBox());
		contentLayout.addComponent(cloudContent = new CloudAccountContent(
				(((CloudInformation) cloudTypeComboBox.getValue())
						.getCloudType())));
		contentLayout.addComponent(accountNameField = new TextField(captions
				.get("account.name.field")));
		contentLayout.addComponent(createFooterLayout());
		return contentLayout;
	}

	private Component createFooterLayout() {
		HorizontalLayout footerLayout = new HorizontalLayout();
		footerLayout.setSpacing(true);
		footerLayout.setSizeFull();
		footerLayout.addComponent(addButton = new Button(captions
				.get("add.button")));
		footerLayout.addComponent(cancelButton = new Button(captions
				.get("cancel.button")));
		cancelButton.setSizeUndefined();
		addButton.setSizeUndefined();
		footerLayout
				.setComponentAlignment(cancelButton, Alignment.BOTTOM_RIGHT);
		footerLayout.setComponentAlignment(addButton, Alignment.BOTTOM_LEFT);
		return footerLayout;
	}

	private ComboBox createCloudTypeComboBox() {
		ComboBox cloudTypeComboBox = new ComboBox(
				captions.get("cloud.type.combobox"));
		List<CloudInformation> clouds = getPresenter().getAllClouds();
		for (CloudInformation cloud : clouds) {
			cloudTypeComboBox.addItem(cloud);
			cloudTypeComboBox.setItemCaption(cloud,
					cloud.getHumanReadableName());
		}
		cloudTypeComboBox.select(clouds.iterator().next());
		cloudTypeComboBox.setNullSelectionAllowed(false);
		cloudTypeComboBox.setImmediate(true);
		return cloudTypeComboBox;
	}

	@Override
	protected String getWindowCaption() {
		return captions.get("add.account.window");
	}
}
