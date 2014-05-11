package pl.agh.iet.i.toik.cloudsync.gui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class GUIConfig {

	@Bean  
    public ResourceBundleMessageSource messageSource() {  
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();  
        source.setBasename("messages/captions");
        source.setUseCodeAsDefaultMessage(true);  
        return source;  
    }  
}
