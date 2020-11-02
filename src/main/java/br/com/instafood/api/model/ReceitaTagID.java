package br.com.instafood.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
public class ReceitaTagID implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7033028006890716071L;

	@Column(name = "receita_id")
	@Getter @Setter private int receitaId;
	
	@Column(name = "tag_id")
	@Getter @Setter private int tagId;

}
