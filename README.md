# CoronaBlockchain
In diesem Programm wird das Grundgerüst für eine Blockchain umgesetzt.
Am Beispiel einer Coronatest Anwenden werden die Corona-Testergebnisse in einer lokalen Blockchain festgehalten.

Bildschirmausgabe:

Hauptbuch anzeigen, Anzahldatensaetze=6

Nr|VorgaengerBlockHash                                              | BlockHash                                                      | Data (LaufNr-Raetsel)

1|0000000000000000000000000000000000000000000000000000000000000000|cafe0eec648162da05d2623570b55f7da2ca203476bc3426de3ce923ab4c17de| Coronatest;Harry Potter;31.07.1980;10.02.2022;negativ (-5269364213904519537)

2|cafe0eec648162da05d2623570b55f7da2ca203476bc3426de3ce923ab4c17de|cafe0912faf4aaa596be637d7f09a386e6a3be73ab82dac18dae7fe0469e6246| Coronatest;Hermine Granger;15.10.1981;11.02.2022;positiv (5207978260830555373)

3|cafe0912faf4aaa596be637d7f09a386e6a3be73ab82dac18dae7fe0469e6246|cafe03c77ff339d4243fd9d4331de2b134a95b21fb396bae874945de026d551f| Coronatest;Weasley Fred Nr-1;1.12.1982;13.02.2022;negativ (7232436761723851534)

4|cafe03c77ff339d4243fd9d4331de2b134a95b21fb396bae874945de026d551f|cafe0f3fdf3a84024465efc1d1832496f4cee8644b1921313ad4f48e408ef82a| Coronatest;Weasley Fred Nr-2;2.12.1982;13.02.2022;negativ (9036143324183059007)

5|cafe0f3fdf3a84024465efc1d1832496f4cee8644b1921313ad4f48e408ef82a|cafe0c83e623535e9c85c55a7a5eb433d837edfcaac3a99befbd37c48c7706bc| Coronatest;Weasley Fred Nr-3;3.12.1982;13.02.2022;negativ (2495936184758203515)

6|cafe0c83e623535e9c85c55a7a5eb433d837edfcaac3a99befbd37c48c7706bc|cafe0ecddf9e559083128630909b305f23274eefaa5e8ffd8e96e6a5e01b56e0| Coronatest;Weasley Fred Nr-4;4.12.1982;13.02.2022;negativ (9027328232479703683)

Blockchain korrekt: true

--------------------------------------------------------------------------------------------
Menue: a=addBlock, d=showBlockChain, e=editBlock1(nur Daten), r=editBlock2(+HashGenerierung),
       l=lade/s=speichern Hauptbuch, f=Suche Blockfehler, q=Programmende, Eingabe= 
