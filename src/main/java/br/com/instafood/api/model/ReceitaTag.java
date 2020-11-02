package br.com.instafood.api.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "receita_tag")
@NoArgsConstructor
public class ReceitaTag {
	
	@EmbeddedId
	private ReceitaTagID id;
	
	@Column(name = "receita_id", insertable=false, updatable=false)
	@Getter @Setter private int receitaId;
	
	@Column(name = "tag_id", insertable=false, updatable=false)
	@Getter @Setter private int tagId;

}
