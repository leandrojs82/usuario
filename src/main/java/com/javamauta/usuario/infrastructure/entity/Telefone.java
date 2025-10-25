package com.javamauta.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "telefone")
public class Telefone {

    @Id
    @SequenceGenerator(name = "telefone_seq", sequenceName = "telefone_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "telefone_seq")
    private Long id;
    @Column(name = "telefone", length = 10)
    private String numero;
    @Column(name = "ddd", length = 3)
    private String ddd;

}
