package br.com.governa.push.domain;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Mensagem extends EntidadeBasica {

    @Email
    private String email;

    @Length(max = 250)
    @NotNull
    @Column(length = 300, nullable = false)
    private String mensagem;


    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
