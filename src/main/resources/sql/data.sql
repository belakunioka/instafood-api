USE instafood;

INSERT INTO usuario (nome, email, senha, data_criacao, ativo, confirmado) VALUES ("Maria Joana", "maria.joana@gmail.com", "$2a$10$/dyTAdrrjgF5SB6wmXdvCOM1C2iG5J0onGlcGp1Eiq2TEbcwGNnWW", NOW(), TRUE, TRUE);
INSERT INTO usuario (nome, email, senha, data_criacao, ativo, confirmado) VALUES ("Jose Luiz", "jose.luiz@gmail.com", "$2a$10$b7aDLfOYo28wfXhzGrhOlu3gQRTrjNqNYKx60DTbvwiqpGmssmPDm", NOW(), TRUE, TRUE);

INSERT INTO utensilio (nome) VALUES ("Batedeira");
INSERT INTO utensilio (nome) VALUES ("Forno");
INSERT INTO utensilio (nome) VALUES ("Liquidificador");
INSERT INTO utensilio (nome) VALUES ("Fogão");
INSERT INTO utensilio (nome) VALUES ("Microondas");

INSERT INTO tag (nome) VALUES ("Sem culpa");
INSERT INTO tag (nome) VALUES ("Glúten free");
INSERT INTO tag (nome) VALUES ("Lactose free");
INSERT INTO tag (nome) VALUES ("Vegano");
INSERT INTO tag (nome) VALUES ("Vegetariano");

INSERT INTO produto (nome) VALUES ("Farinha de trigo");
INSERT INTO produto (nome) VALUES ("Açúcar");
INSERT INTO produto (nome) VALUES ("Chocolate em pó");
INSERT INTO produto (nome) VALUES ("Fermento em pó");
INSERT INTO produto (nome) VALUES ("Ovo");
INSERT INTO produto (nome) VALUES ("Manteiga");
INSERT INTO produto (nome) VALUES ("Leite");
INSERT INTO produto (nome) VALUES ("Óleo");
INSERT INTO produto (nome) VALUES ("Cenoura");
INSERT INTO produto (nome) VALUES ("Mandioca");
INSERT INTO produto (nome) VALUES ("Creme de leite");
INSERT INTO produto (nome) VALUES ("Margarina");
INSERT INTO produto (nome) VALUES ("Carne seca");
INSERT INTO produto (nome) VALUES ("Cebola");
INSERT INTO produto (nome) VALUES ("Alho");
INSERT INTO produto (nome) VALUES ("Tomate");
INSERT INTO produto (nome) VALUES ("Sal");
INSERT INTO produto (nome) VALUES ("Pimenta");
INSERT INTO produto (nome) VALUES ("Queijo ralado");
INSERT INTO produto (nome) VALUES ("Frango");
INSERT INTO produto (nome) VALUES ("Abacate");
INSERT INTO produto (nome) VALUES ("Cacau em pó");
INSERT INTO produto (nome) VALUES ("Canela em pó");
INSERT INTO produto (nome) VALUES ("Melado de cana");
INSERT INTO produto (nome) VALUES ("Essência de baunilha");
INSERT INTO produto (nome) VALUES ("Polvilho doce");
INSERT INTO produto (nome) VALUES ("Polvilho azedo");
INSERT INTO produto (nome) VALUES ("Água");
INSERT INTO produto (nome) VALUES ("Azeite de oliva");
INSERT INTO produto (nome) VALUES ("Macarrão");
INSERT INTO produto (nome) VALUES ("Bacon");
INSERT INTO produto (nome) VALUES ("Creme de leite");
INSERT INTO produto (nome) VALUES ("Farinha integral");
INSERT INTO produto (nome) VALUES ("Pimentão verde");
INSERT INTO produto (nome) VALUES ("Pimentão vermelho");
INSERT INTO produto (nome) VALUES ("Pimentão amarelo");
INSERT INTO produto (nome) VALUES ("Molho shoyo");
INSERT INTO produto (nome) VALUES ("Maizena");
INSERT INTO produto (nome) VALUES ("Amendoim torrado");



/*
 * Receita
 * Bolo de Chocolate
 */
INSERT INTO receita (titulo, imagem, data_criacao, tipo, tempo_preparo, rendimento, instrucoes, usuario_id) 
	VALUES ("Bolo de Chocolate", "bolo%20de%20chocolate.jpg", "2020-09-08", "Doce", "40 min", "40 porções", "Em uma batedeira, bata as claras em neve, acrescente as gemas, o açúcar e bata novamente.\nAdicione a farinha, o chocolate em pó, o fermento, o leite e bata por mais alguns minutos.\nDespeje a massa em uma forma untada e leve para assar em forno médio (180° C), preaquecido, por 40 minutos.\nPara a cobertura, em uma panela, leve a fogo médio o chocolate em pó, a manteiga e o leite, deixe até ferver.\nDespeje quente sobre o bolo já assado.\nEspere esfriar e pode saborear a vontade!", 1);
    
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (1, 1, 2, "xícaras");
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (1, 2, 2, "xícaras");
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (1, 3, 6, "colheres (sopa)");
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (1, 4, 1, "colher (sopa)");
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (1, 5, 6, "unidades");
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (1, 6, 2, "colheres (sopa)");
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (1, 7, 2, "xícaras");
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (1, 3, 1, "xícara");

INSERT INTO receita_utensilio (receita_id, utensilio_id) VALUES (1, 1);
INSERT INTO receita_utensilio (receita_id, utensilio_id) VALUES (1, 2);

INSERT INTO receita_tag (receita_id, tag_id) VALUES (1, 1);
INSERT INTO receita_tag (receita_id, tag_id) VALUES (1, 2);

/*
 * Receita
 * Bolo de Cenoura
 */
 INSERT INTO receita (titulo, imagem, data_criacao, tipo, tempo_preparo, rendimento, instrucoes, usuario_id) 
	VALUES ("Bolo de Cenoura", "bolo-de-cenoura-768x512.jpg", "2020-09-22", "Doce", "40 min", "8 porções", "Em um liquidificador, adicione a cenoura, os ovos e o óleo, depois misture.\nAcrescente o açúcar e bata novamente por 5 minutos.\nEm uma tigela ou na batedeira, adicione a farinha de trigo e depois misture novamente.\nAcrescente o fermento e misture lentamente com uma colher.\nAsse em um forno preaquecido a 180° C por aproximadamente 40 minutos.\nDespeje em uma tigela a manteiga, o chocolate em pó, o açúcar e o leite, depois misture.\nLeve a mistura ao fogo e continue misturando até obter uma consistência cremosa, depois despeje a calda por cima do bolo.", 1);

INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (2, 8, 0.5, "xícara (chá)");
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (2, 9, 3, "médias");
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (2, 5, 4, "unidade");
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (2, 2, 2, "xícaras (chá)");
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (2, 1, 2.5, "xícaras (chá)");
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (2, 4, 1, "colher (sopa)");
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (2, 6, 1, "colher (sopa)");
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (2, 3, 3, "colher (sopa)");
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (2, 2, 1, "xícara (chá)");
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (2, 7, 1, "xícara (chá)");

INSERT INTO receita_utensilio (receita_id, utensilio_id) VALUES (2, 2);
INSERT INTO receita_utensilio (receita_id, utensilio_id) VALUES (2, 3);

INSERT INTO receita_tag (receita_id, tag_id) VALUES (2, 1);
INSERT INTO receita_tag (receita_id, tag_id) VALUES (2, 2);

/*
 * Receita
 * Escondidinho de Carne Seca
 */
 INSERT INTO receita (titulo, imagem, data_criacao, tipo, tempo_preparo, rendimento, instrucoes, usuario_id) 
	VALUES ("Escondidinho de Carne Seca", "202944_original.jpg", "2020-10-05", "Salgado", "60 min", "6 porções", "Esprema a mandioca ainda quente e leve em uma panela com a margarina e sal.\nQuando estiverem bem misturados acrescente o creme de leite, misture e reserve.\nRefogue a cebola e o alho em um fio de azeite.\nAcrescente a carne-seca desfiada e deixe fritar um pouco.\nAcrescente os tomates e deixe cozinhar até ficarem murchos e acerte o sal se achar necessário.\nEm um refratário untado com azeite, coloque uma camada do purê de mandioca, a carne seca e termine com o restante do purê.\nPolvilhe com queijo parmesão ralado e leve ao forno pra gratinar.\n", 2);

INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (3, 10, 1, "kg");
INSERT INTO ingrediente (receita_id, produto_id, quantidade) VALUES (3, 11, 1);
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (3, 12, 2, "colheres");
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (3, 13, 0.5, "kg");
INSERT INTO ingrediente (receita_id, produto_id, quantidade) VALUES (3, 14, 1);
INSERT INTO ingrediente (receita_id, produto_id, quantidade, unidade) VALUES (3, 15, 4, "dentes");
INSERT INTO ingrediente (receita_id, produto_id, quantidade) VALUES (3, 16, 2);
INSERT INTO ingrediente (receita_id, produto_id) VALUES (3, 17);
INSERT INTO ingrediente (receita_id, produto_id) VALUES (3, 18);

INSERT INTO receita_utensilio (receita_id, utensilio_id) VALUES (3, 3);