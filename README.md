# WindCharge
WindCharge é um plugin para Minecraft 1.21 que adiciona um item especial chamado "Wind Charge" ao jogo.

Instalação
Para instalar o WindCharge, siga os passos abaixo:

Clone o repositório:

```
git clone https://github.com/Pedream27/WindCharge
```
Compile o plugin usando Gradle:

```
cd WindCharge
```
```
./gradlew build
```
Copie o arquivo .jar gerado na pasta build/libs para a pasta de plugins do seu servidor Minecraft.

Inicie o servidor. O plugin será carregado automaticamente.

Comandos
O WindCharge vem com um comando principal para dar o item "Wind Charge" aos jogadores.
```
/wc dar <type> <amount> [player]
```
type: Tipo de Wind Charge.
amount: Quantidade de Wind Charges.
player (opcional): Nome do jogador. Se omitido, o item será dado ao jogador que executou o comando.
Exemplo de Uso
Para dar 5 Wind Charges do tipo "basic" ao jogador "Steve":

```
/wc dar basic 5 Steve
```
