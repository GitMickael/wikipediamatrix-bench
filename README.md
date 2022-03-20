# Objectif
Le but ici est de créer un extracteur de tableau depuis wikipedia de sorte à faire des fichiers csv.
Le résultat sera donc une API qu'il sera possible d'appeler

## QuickStart
Le projet utilise java 11.
- Pour lancer les tests :
```bash
cd wikimatrix
mvn test
```
A partir du fichier wikiurls.txt se trouvant dans le inputdata, des csv vont être créé dans le dossier output.

- Pour lancer le webservice :
```
cd wikimatrix
mvn spring-boot:run
```
Celui permet d'extraire tous les tableaux depuis une page wikipedia.
- Le seul endpoint disponible est donc le suivant : `http://localhost:8080/get_tables_from_wikipedia`
Il prend en paramètre un argument `page_name` et renvoie la liste des tableaux disponible sur cette page.

Voici un exemple d'utilisation :
`http://localhost:8080/get_tables_from_wikipedia?page_name=Comparison_of_ALGOL_68_and_C`

## Design Patern Utilisé
Nous avons choisi d'utiliser le design patern Factory pour l'extractor et le parser par le biais d'une interface de sorte à rendre l'implémentation d'une autre méthode d'extraction plus aisé, sans avoir besoin de changer l'intégralité du code.
En effet, pour ajouter une autre méthode d'extraction, il suffit simplement d'implémenter de nouvelles classes implémentant les interfaces associées. Une fois cela fait, il ne reste qu'à changer l'instanciation de la classe et tout est fonctionnel !

Le design patern factory a également été utilisé au pour le serializer de sorte à ce qu'on puisse facilement implémenter d'autre sérializer tel que JSON ou du XML.

## Limite du projet
- Le projet en l'état actuel rencontre encore des difficultés à bien ordonner les différentes informations dans les bonnes colonnes.
- L'API est actuellement assez peu adaptable et ne permet de renvoyer que du json. Il pourrait être intéressant d'utiliser également le design patern de la factory pour améliorer la maniabilité.
- Les tests sont actuellement limités et mériteraient d'être bien plus avancés, notamment avec des cas classiques de tableau, choses que l'on dû faire au début de notre projet mais qu'on a malheureusement pas implémenté à ce stade :(
- Tout le travail est réalisé dans l'optique d'ajouter par la suite un autre extracteur, d'autres formats de fichier !