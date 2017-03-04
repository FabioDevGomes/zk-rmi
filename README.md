
O programa implementado permite a criação e a listagem de contas e também de movimentações, os objetos são criados pelo ZK Framework e enviados ao ServiceImpl através da api RMI para serem persistidos com o uso da especificação JPA.  


Para executar a aplicação siga os passo abaixo:  

1 - Encontre a classe ServiceImpl.java  
2 - Execute o método main, que será a classe remota, em Java Application  
3 - Execute a aplicação em um servidor ou container (ex.: tomcat)  

Tela de inclusão de contas:
![Screenshot](screenshot.png)

Tela de realização de movimentações
![Screenshot](movimentacao.png)
