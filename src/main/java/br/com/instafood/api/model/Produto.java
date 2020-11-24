package br.com.instafood.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class Produto {

    @Id
    @Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private int id;
    
    @Column(unique = true)
    @NotNull(message = "O campo nome não pode ser nulo")
    @Size(min = 2, max = 60, message = "O campo nome deve conter entre 2 e 60 caracteres")
    @Getter @Setter private String nome;
}