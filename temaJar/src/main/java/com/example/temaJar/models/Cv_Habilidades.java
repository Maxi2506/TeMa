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
    @JoinColumn(name = "id_cv_base")
    private Cv_Base cv_base;

    @ManyToOne
    @JoinColumn(name = "id_habilidad")
    private Habilidades habilidades;

    public Cv_Habilidades() {
    }

    public Cv_Habilidades(Long id, Cv_Base cv_base, Habilidades habilidades) {
        this.id = id;
        this.cv_base = cv_base;
        this.habilidades = habilidades;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cv_Base getCv_base() { return cv_base; }
    public void setCv_base(Cv_Base cv_base) { this.cv_base = cv_base; }

    public Habilidades getHabilidades() { return habilidades; }
    public void setHabilidades(Habilidades habilidades) { this.habilidades = habilidades; }

    @Override
    public String toString() {
        return "Cv_Habilidades{" +
                "id=" + id +
                ", cv_base_id=" + (cv_base != null ? cv_base.getId() : "null") +
                ", habilidades=" + habilidades +
                '}';
    }
}
