# Polyglot Data System  Applied on M8 | Secondo Progetto Big Data


# Abstract
Lo slogan è ormai famoso nella comunità dei database. “One size does not fit all”. Se i motori di archiviazione dei dati sono congrui ai dati da archiviare, le prestazioni delle applicazioni ad alta intensità di dati vengono notevolmente migliorate.
Al giorno d'oggi, le aziende hanno sempre più il bisogno di gestire dati di grandi dimensioni e sopratutto di diversa natura, infatti vengono utilizzate delle architetture a servizi o microservizi che modellano perfettamente le esigenze del business dell'azienda utilizzando diverse tecnologie per ogni servizio rilasciato, tra cui diversi modelli di storage a seconda del tipo di dato che tratta il servizio. L'approccio utilizzato fino ad ora per la gestione dei sistemi e dei dati eterogenei è stato quello di utilizzare un Federated Database, il quale permette di unificare in un unica federazione le diverse tecnologie utilizzate per salvare dati di diversa natura. Negli ultimi anni, seguendo l'idea per cui "One size does not fit all", sono nati dei sistemi per gestire DB di diversa natura(SQL/NoSQL/MySQL) attraverso dei sistemi Polystore, i quali permettono di utilizzare il linguaggio nativo dei sistemi con lo stesso risultato che riportavano i FDBMS. Ovviamente i Polystore sono sistemi più complessi ma che permettono all'utente di utilizzare tutte le sfaccettature del linguaggio nativo che si sta utilizzando.
Nello stato dell'arte attuale sono state individuate due valide soluzione: BigDAWG e Polypheny.
In questo elaborato è stato scelto di utilizzare BigDAWG come middleware ed è stato implementato sul nostro sistema M8 Polystore, che permette all'utente di esplorare e analizzare i dati attraverso la nostra web-app senza che sia a conoscenza della presenza del middleware utilizzato.




Requisiti
------------

Questo progetto richiede l'installazione di più moduli e alcuni requisiti di sistema:

Sistema Operativo Linux | Ubuntu (18.04 LTS ) (https://www.ubuntu-it.org/download)

Docker for Linux (https://docs.docker.com/engine/install/ubuntu/)

BigDAWG Project scaricabile dal terminale con il seguente comando (git clone https://github.com/bigdawg-istc/bigdawg.git)


I requsiti richiesti sono stati scelti a seguito dei testing che abbiamo effettuato sul nostro calcolatore

Come utilizzare
------------
Per utilizzarlo bisogna scaricare aver installato tutti i requisiti nella sezione precendente, successivamente runnare prima di tutto BigDAWG project seguendo i seguenti passi:

1. Aprire la directory BigDAWG e navigare fino alla cartella "bigdawg/provisions" 
2. Runnare il seguente docker script  "./setup_bigdawg_docker.sh"
3. Controllare che tutti i contenitore docker siano avviati tramite il comando "docker ps"

Una volta che tutti i contentiori docker sono in esecuzione possiamo avviare il client in due modi differenti:

1. Importare il client attraverso un IDE (es. Eclipse), navigare a "Project_second\M8BigDawgClient\src\main\java\client\application.java" ed runnare come java application.
2. Scaricare il file "m8Client.jar" presente nella cartella Project_second, aprire il terminale ed eseguire il comando "java -jar m8Client.jar".




Examples 
------------
A seguire sono riportati alcuni screen del nostro sistema.
![slider](https://user-images.githubusercontent.com/51997286/126874899-3acbf947-09d0-4107-81e3-296736d1e39f.png)
![home](https://user-images.githubusercontent.com/51997286/126874907-548baf16-f0a3-400f-a4dc-3ac2db3452e3.png)
![queryPanel](https://user-images.githubusercontent.com/51997286/126874915-a622749d-3c90-433a-b02a-c6104004ee2e.png)
![queryResult](https://user-images.githubusercontent.com/51997286/126874921-7f411236-ae74-43a1-9ffd-831f4822f3c4.png)
![catalog](https://user-images.githubusercontent.com/51997286/126874922-1903a775-cc06-4a57-b52a-24ad97f273e5.png)
![castPanel](https://user-images.githubusercontent.com/51997286/126874925-f231246a-923e-4f93-92b5-2ea259989c9b.png)
![castResults](https://user-images.githubusercontent.com/51997286/126874928-2952752c-be83-4a63-b4a8-950a6017b89b.png)






Maintainers
------------
- Pier Vincenzo De Lellis
- Francesco Foresi
