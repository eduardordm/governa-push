package br.com.governa.push.repository;

import br.com.governa.push.domain.Mensagem;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "mensagem", path = "mensagem")
public interface MensagemRepository extends PagingAndSortingRepository<Mensagem, Long> {

}
