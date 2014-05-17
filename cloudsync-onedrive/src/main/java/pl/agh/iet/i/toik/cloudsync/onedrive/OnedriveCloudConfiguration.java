package pl.agh.iet.i.toik.cloudsync.onedrive;

import com.sun.jersey.api.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.agh.iet.i.toik.cloudsync.onedrive.util.JSONResolver;

@Configuration
@ComponentScan
public class OnedriveCloudConfiguration {

    @Bean
    public Client client() {
        return Client.create();
    }

    @Bean
    public JSONResolver jsonResolver() {
        return new JSONResolver();
    }
}
