package com.example.temaJar.models;

import jakarta.persistence.*;

@Entity
@Table(name="Cv_Habilidades")
public class Cv_Habilidades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_habilidad") // Ajusta el nombre si prefieres otro
    private Habilidades habilidades;

    public Cv_Habilidades() {
    }

    public Cv_Habilidades(Long id, Habilidades habilidades) {
        this.id = id;
        this.habilidades = habilidades;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Habilidades getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(Habilidades habilidades) {
        this.habilidades = habilidades;
    }

    @Override
    public String toString() {
        return "Cv_Habilidades{" +
                "id=" + id +
                ", habilidades=" + habilidades +
                '}';
    }
}
