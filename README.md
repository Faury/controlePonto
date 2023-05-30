# controlePonto

Projeto que representa um controle de ponto, possui as funções de inserir batida e gera relatorio mensal.

O projeto possui dois endpoints:

- POST localhost:8080/v1/batidas: deve enviar a data e a hora no corpo, exemplo { "dataHora": "2023-06-02T12:00:00" }
- GET localhost:8080/v1/folhas-de-ponto/{mes} : deve ter o formato YYYY-MM

Esse projeto usa um banco de dados H2 em memória, portanto se o aplicativo for reiniciado todos os dados são apagados,
caso seja necessário manter os dados entre uma execução e outra é só alterar em /ponto/src/main/resources/application.properties
a propriedade spring.datasource.url para jdbc:h2:file:./demodb por exemplo.

Os testes unitários estão em JUNIT 4, caso precise rodar não esquecer de selecionar essa versão, no JUNIT 5 não aparecerá nada.
