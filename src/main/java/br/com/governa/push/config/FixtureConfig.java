package br.com.governa.push.config;

import br.com.governa.push.domain.Mensagem;
import br.com.governa.push.repository.MensagemRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.inject.Inject;


@Service
public class FixtureConfig implements InitializingBean {

    @Inject
    MensagemRepository mensagemRepository;

    public void criaMensagens() {
        for(int i = 1; i < 3; i++) {
            Mensagem m = new Mensagem();
            m.setMensagem("Ola Mundo #" + i);
            m.setEmail("email@email" + i + ".com.br");
            mensagemRepository.save(m);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        criaMensagens();
    }
}
