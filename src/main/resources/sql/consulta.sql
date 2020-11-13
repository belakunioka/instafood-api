SELECT DISTINCT r.id, r.titulo, r.imagem FROM receita r
	INNER JOIN ingrediente i 
		INNER JOIN produto p ON p.id = i.produto_id
    ON i.receita_id = r.id
	INNER JOIN receita_utensilio ru 
		INNER JOIN utensilio u 
	ON r.id = ru.receita_id 
	INNER JOIN receita_tag rt 
		INNER JOIN tag t ON t.id = rt.tag_id
	ON r.id = ru.receita_id
WHERE p.id IN (9) # Cenoura
	AND u.id IN (2, 3) # Forma de Bolo e Liquidificador
    AND t.id IN (1); # Sem culpa