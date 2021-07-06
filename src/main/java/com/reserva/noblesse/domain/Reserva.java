package com.reserva.noblesse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Reserva.
 */
@Entity
@Table(name = "reserva")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data_hora", nullable = false)
    private ZonedDateTime dataHora;

    @Column(name = "notas")
    private String notas;

    @Column(name = "aprovado")
    private Boolean aprovado;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "reservas" }, allowSetters = true)
    private Espaco espaco;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reserva id(Long id) {
        this.id = id;
        return this;
    }

    public ZonedDateTime getDataHora() {
        return this.dataHora;
    }

    public Reserva dataHora(ZonedDateTime dataHora) {
        this.dataHora = dataHora;
        return this;
    }

    public void setDataHora(ZonedDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getNotas() {
        return this.notas;
    }

    public Reserva notas(String notas) {
        this.notas = notas;
        return this;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Boolean getAprovado() {
        return this.aprovado;
    }

    public Reserva aprovado(Boolean aprovado) {
        this.aprovado = aprovado;
        return this;
    }

    public void setAprovado(Boolean aprovado) {
        this.aprovado = aprovado;
    }

    public User getUser() {
        return this.user;
    }

    public Reserva user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Espaco getEspaco() {
        return this.espaco;
    }

    public Reserva espaco(Espaco espaco) {
        this.setEspaco(espaco);
        return this;
    }

    public void setEspaco(Espaco espaco) {
        this.espaco = espaco;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reserva)) {
            return false;
        }
        return id != null && id.equals(((Reserva) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reserva{" +
            "id=" + getId() +
            ", dataHora='" + getDataHora() + "'" +
            ", notas='" + getNotas() + "'" +
            ", aprovado='" + getAprovado() + "'" +
            "}";
    }
}
