# xy-inc: REST API de teste

Para análise do código, selecionar no Eclipse a opção Import -> Existing Maven Projects, apontando para a pasta raiz deste repositório.

O build se encontra automatizado via Maven, bastando executar os goals 'clean, compile, install' no projeto agregador 'xy-inc'. Os testes automatizados serão executados durante o build, gerando análise de impacto em tempo de compilação.

Finalizado o build, o artefato principal será o arquivo .war, localizado no projeto 'xy-inc-rest/targeg/xy-inc-rest.war'.

# Instalação

1. Obter um container JEE, por exemplo o Tomcat 8: http://tomcat.apache.org/ 
2. Instalar o MySql Community Server: http://dev.mysql.com/downloads/mysql/ . Para comodidade, manter a porta padrão 3306 e criar um usuário com nome: xyinc e senha: xyinc. Além disso, criar um novo schema (database) chamada xyinc.
3. Realizar o deploy do arquivo .war no Tomcat.

# API

Após o deploy da aplicação, a REST api estará disponível com os endpoints:

GET http://<computer:port>/xy-inc-rest/v1/products (Listar todos os produtos)

GET http://<computer:port>/xy-inc-rest/v1/products/<id> (Detalhes de um produto)

POST http://<computer:port>/xy-inc-rest/v1/products (e objeto Product no formato JSON como Body) (Cria um produto)

PUT http://<computer:port>/xy-inc-rest/v1/products/<ID> (e objeto Product no formato JSON como Body) (Edita um produto)

DELETE http://<computer:port>/xy-inc-rest/v1/products/<id> (Deleta um produto)
