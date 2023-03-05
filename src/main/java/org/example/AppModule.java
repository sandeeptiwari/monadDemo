package org.example;

import com.google.inject.AbstractModule;
import org.example.config.AppConfig;
import org.example.repo.PersonRepository;
import org.example.supports.HttpHelper;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        // Bind an implementation of MyService to the MyService interface
        bind(PersonRepository.class).toInstance(new PersonRepository(new AppConfig()));
        bind(AppService.class).toInstance(new AppServiceImpl(new HttpHelper()));
    }
}
