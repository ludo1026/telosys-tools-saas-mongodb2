package demo;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * MongoDB configuration.
 */
@Configuration
public class MongodbConfiguration {

    public @Bean
    Mongo mongo() throws Exception {
        return new MongoClient();
    }

    public @Bean
    MongoTemplate mongoTemplateUsers() throws Exception {
        return new MongoTemplate(mongo(), "db_users");
    }

}
