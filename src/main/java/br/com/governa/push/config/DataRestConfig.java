package br.com.governa.push.config;

import br.com.governa.push.domain.EntidadeBasica;
import br.com.governa.push.domain.Mensagem;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.atteo.classindex.ClassIndex;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.repository.support.Repositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.MediaType;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@Import(RepositoryRestMvcConfiguration.class)
public class DataRestConfig extends RepositoryRestMvcConfiguration {

    @Autowired
    private MessageSource messageSource;

    /**
     * Configura o Spring-data-rest
     * @param config
     */
    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        super.configureRepositoryRestConfiguration(config);
        try {
            config.setBaseUri(new URI("/api"));
            config.setReturnBodyOnCreate(true);
            configExposeIds(config);
            config.setDefaultMediaType(MediaType.APPLICATION_JSON);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Percorre todos os dominios e configura a exposicao do ID na resposta do Spring-data-rest
     * @param config
     */
    private void configExposeIds (RepositoryRestConfiguration config) {
        config.exposeIdsFor(Mensagem.class);
        config.exposeIdsFor(EntidadeBasica.class);
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource);
        return validatorFactoryBean;
    }


    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setBeanName("characterEncodingFilter");
        characterEncodingFilter.setEncoding("UTF-8");

        registrationBean.setFilter(characterEncodingFilter);
        return registrationBean;
    }

    /**
     * Corrige questao do erro 500, ver: https://jira.spring.io/browse/DATAREST-260
     */
    @Bean
    @Override
    public ValidatingRepositoryEventListener validatingRepositoryEventListener(ObjectFactory<Repositories> repositories) {
        ValidatingRepositoryEventListener listener = new ValidatingRepositoryEventListener(repositories);
        configureValidatingRepositoryEventListener(listener);
        listener.addValidator("afterCreate", validator());
        listener.addValidator("beforeCreate", validator());
        return listener;
    }

    @Override
    protected void configureJacksonObjectMapper(ObjectMapper objectMapper) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

}
