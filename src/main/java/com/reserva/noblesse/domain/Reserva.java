package com.reserva.noblesse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
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

    @Size(max = 140)
    @Column(name = "notas", length = 140)
    private String notas;

    @OneToMany(mappedBy = "reserva")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "reserva" }, allowSetters = true)
    private Set<Espaco> espacos = new HashSet<>();

    @ManyToOne
    private User user;

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

    public Set<Espaco> getEspacos() {
        return this.espacos;
    }

    public Reserva espacos(Set<Espaco> espacos) {
        this.setEspacos(espacos);
        return this;
    }

    public Reserva addEspaco(Espaco espaco) {
        this.espacos.add(espaco);
        espaco.setReserva(this);
        return this;
    }

    public Reserva removeEspaco(Espaco espaco) {
        this.espacos.remove(espaco);
        espaco.setReserva(null);
        return this;
    }

    public void setEspacos(Set<Espaco> espacos) {
        if (this.espacos != null) {
            this.espacos.forEach(i -> i.setReserva(null));
        }
        if (espacos != null) {
            espacos.forEach(i -> i.setReserva(this));
        }
        this.espacos = espacos;
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
            "}";
    }
}
