

**Licenciatura em Engenharia Informática e de Computadores**





# Trabalho Prático


# BGG





Projeto de Programação Dispositivos Móveis
2019 / 2020 inverno





Pedro Tereso 44015

Tiago Pereira 43592

**Docente:** Eng.º Paulo Pereira





**Turma:** LI51D


## **Introdução**

O objetivo deste trabalho era o desenvolvimento de uma aplicação móvel para dispositivos _Android_ recorrendo à linguagem de programação _Kotlin_.

A grande temática do projeto é jogos de tabuleiro. Toda a informação apresentada na aplicação tem por base os resultados dos pedidos realizados à _API &quot;Board Games Atlas&quot;_

O utilizador final passa a ser capaz de efetuar pesquisas de jogos específicos por nome, autor ou artista, bem como verificar os jogos mais populares do momento, criar listas personalizadas de jogos (_e.g_ Lista de Compras, Favoritos do Jorge) e ainda a criação de um ou mais perfis de jogo para os quais poderá receber uma notificação quando um novo jogo que se enquadre no perfil é lançado para o mercado.

Todos os dados referentes à utilização pessoal de cada utilizador (_e.g_ Perfis de Jogo e Listas Personalizadas) serão guardadas localmente no dispositivo o que permitirá uma correta experiência de utilização.







## **Actividades da Aplicação**

O projeto está dividido em vários ecrãs que serão apresentados ao utilizador consoante as suas escolhas de navegação:

## **Actividade Menu**

A actividade de menu apresenta no ecrã o menu principal de navegação da aplicação e responde aos toques no ecrã navegando para a secção desejada.


## **Actividades Base**

Esta classe abstrata, tal como o nome indica, é o esquema para a maioria das outras actividades e exige que para serem descendentes dela, implementem alguns métodos que asseguram o normal funcionamento das actividades filho.

### **Actividades Jogos**

Esta, à semelhança da classe base também é uma classe abstrata.

Como deriva de actividade base é obrigada a implementar os métodos básicos bem como a definir o comportamento de possíveis actividades que possam vir a descender dela para apresentação de listas de jogos



#### **Actividade Jogos Com Paginação**

Esta actividade é a primeira a ser apresentada que realmente é totalmente responsável pelo seu comportamento e deriva hierarquicamente de actividade base e actividade jogos.

O seu uso principal é na função de procura e apresentação dos jogos mais populares do momento (tcp._Trending_).

A apresentação da lista de jogos respeita o conceito de paginação que economiza as interações com o servidor de dados o que permite uma melhor gestão quer de dados quer de bateria.







#### **Actividade Jogos Com Paginação E Menu**

A actividade jogos com paginação e menu assume-se como uma extensão da actividade acima.

Utiliza a [_ActionBar_](https://developer.android.com/training/appbar) para produzir um menu de procura de jogos. É possível pesquisar por Autor, Artista e Editora.

O seu uso principal é na função de procura e apresentação dos jogos resultantes (tcp._Search_).



#### **Actividade Vista Detalhada Jogo**

A actividade vista detalhada do jogo, ao contrário das anteriores, e recorrendo ao padrão de desenho (_MVC_) apenas define a parte de _View_.

Para o seu correto funcionamento é obrigatório a passagem, na forma de _Intent_, do objeto _Game_ a ser apresentado.

Aqui serão mostrados vários detalhes do jogo de tabuleiro tais como:

- Número Jogadores

- Tempo de Jogo

- Idade Mínima Recomendada

- Preço

- Avaliação Média

- Ano de Lançamento

- Descrição

####

### **Actividades Listas Personalizadas de Jogos do Utilizador**

Esta, á semelhança da classe base e da classe jogos também é uma classe abstrata.

Como deriva de actividade base é obrigada a implementar os métodos básicos bem como a definir o comportamento de possíveis actividades que possam vir a descender dela para apresentação de listas de listas personalizadas de jogos.

#### **Actividade Lista de Coleções (Listas Personalizadas de Jogos)**

Aqui se concentrarão todas as listas personalizadas de jogos criadas pelo utilizador.

Cada uma é caracterizada por uma imagem aleatória gerada a partir de um conjunto guardado numa pasta do projeto e por um título que é alterável.

Também é nesta actividade que será possível editar as coleções, removendo as indesejadas, atualizando assim a tabela na base de dados que corresponde a esta lista.

Para apagar basta deslizar o &quot;cartão&quot; que representa a lista e não clicar no botão &quot;_UNDO_&quot; que será mostrado na parte inferior do ecrã.

#### **Actividade Adicionar Jogo À**  **Coleção**

Esta actividade é a responsável pela a apresentação no ecrã das atuais listas que o utilizador poderá ter criado e será exibida quando for premido o botão.

### **Actividades Base de Favoritos**

Esta, á semelhança da classe base, da classe jogo e da classe listas personalizadas de jogos de jogos do utilizador também é uma classe abstrata.

Como deriva de actividade base é obrigada a implementar os métodos básicos bem como a definir o comportamento de possíveis actividades que possam vir a descender dela para apresentação de listas de perfis de jogos.

#### **Actividade Favoritos**

A actividade Favoritos deriva hierarquicamente da classe base de favoritos que por sua vez, deriva de base.

Assim conclui-se que representa um acrescentamento de funções das anteriores, similares a algumas das actividades já apresentadas.

Depois de listas de jogos e de coleções, esta classe é caracterizada por listas de perfis de jogo.

Como era de esperar, também é possível fazer a gestão de perfis de jogo deslizando o cartão que o representa para a direita para apagar o mesmo.



#### **Actividade Novo Perfil De Jogo**

A actividade novo perfil de jogo está encarregue da criação e respetiva notificação de alteração do conteúdo da tabela na base de dados onde estão todos os perfis de jogo que o utilizador criou.

O objetivo desta funcionalidade é o usuário ser notificado sempre que houver uma nova adição de um jogo no mercado que cumpra os requisitos que o mesmo especificou.





Para isso é confrontado com o preenchimento de alguns dados que irão dar forma ao perfil. É possível designar algumas características de jogos tais como:

- Categoria

- Mecânica

- Artista

- Editora

Para a escolha da categoria e da mecânica foi necessário restringir a escolha do utilizador usando informações da API de dados (Board Game Atlas).

Para tal foram criados dois fragmentos de auxiliam o usuário na sua escolha. Antes da apresentação dos mesmos no ecrã, foi necessário a obtenção e organização das informações recolhidas da API.
 
## **Conclusão**

Com a realização deste projeto ficamos a conhecer melhor todas as funcionalidades e potencialidades da linguagem de desenvolvimento Kotlin.

Num mundo dependente de aplicações, o desenvolvimento de uma, enquadra-se no espectro de um ensino global e atual.
