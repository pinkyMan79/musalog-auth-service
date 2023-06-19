package one.terenin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;

@SpringBootApplication
public class MusAuthenticationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusAuthenticationServiceApplication.class, args);
    }

}
