# imdb_neo4j
Display Imdb from NEO4J database 

// 1. Download the files name.basics.tsv, title.basics.tsv, title.principals.tsv and title.ratings.tsv into Downloads/
// 2. Do one of the following, for testing or for real. The files to be imported need to be in the Neo4j import directory.

// for testing purposes, import only the top 10000 lines :
head -10000 Downloads/name.basics.tsv > /usr/local/Cellar/neo4j/3.5.8/libexec/import/name.basics.tsv.head
head -10000 Downloads/title.basics.tsv > /usr/local/Cellar/neo4j/3.5.8/libexec/import/title.basics.tsv.head
head -10000 Downloads/title.principals.tsv > /usr/local/Cellar/neo4j/3.5.8/libexec/import/title.principals.tsv.head
head -10000 Downloads/title.ratings.tsv > /usr/local/Cellar/neo4j/3.5.8/libexec/import/title.ratings.tsv.head

// for real : import ALL :
mv Downloads/name.basics.tsv /usr/local/Cellar/neo4j/3.5.8/libexec/import/name.basics.tsv
mv Downloads/title.basics.tsv /usr/local/Cellar/neo4j/3.5.8/libexec/import/title.basics.tsv
mv Downloads/title.principals.tsv /usr/local/Cellar/neo4j/3.5.8/libexec/import/title.principals.tsv
mv Downloads/title.ratings.tsv /usr/local/Cellar/neo4j/3.5.8/libexec/import/title.ratings.tsv

// 3. Loading in Neo4J, to get into the cypher-shell :
cypher-shell -u neo4j -p a

// in cypher-shell, load Person
using periodic commit 500
load csv with headers from "file:///name.basics.tsv" as row fieldterminator "\t"
create (p:Person {nconst:row.nconst, primaryName:row.primaryName, birthYear:toInt(row.birthYear)
, deathYear:CASE row.deathYear WHEN "\\N" THEN null ELSE toInt(row.deathYear) END
, primaryProfession:split(row.primaryProfession, ","), knownForTitles:split(row.knownForTitles, ",")});
// You might get issues like :
// At /usr/local/Cellar/neo4j/3.5.8/libexec/import/name.basics.tsv @ position 258014088 -  there's a field starting with a quote and whereas it ends that quote there seems to be characters in that field after that ending quote. That isn't supported. This is what I read: 'Luna" '
//

// load Movie
using periodic commit 500
load csv with headers from "file:///title.basics.tsv" as row fieldterminator "\t"
create (p:Movie {tconst:row.tconst, titleType:row.titleType, primaryTitle:row.primaryTitle, originalTitle:row.originalTitle
, isAdult:CASE row.isAdult WHEN "1" THEN true ELSE false END
, startYear:CASE row.startYear WHEN "\\N"  THEN null ELSE toInt(row.startYear) END
, runtimeMinutes:CASE row.runtimeMinutes WHEN "\\N" THEN null ELSE toInt(row.runtimeMinutes) END
, genres:split(row.genres, ",")});

// after loading the nodes, and before putting the relationships, add the constraints
create constraint on (p:Person) assert p.nconst is unique
create constraint on (m:Movie) assert m.tconst is unique

// relationship PLAYS_IN
using periodic commit 500
load csv with headers from "file:///title.principals.tsv" as row fieldterminator "\t"
match (p:Person {nconst: row.nconst}), (m:Movie {tconst: row.tconst})
create (p) -[r:PLAYS_IN {category: row.category
, job:CASE row.job WHEN "\\N" THEN "" ELSE row.job END
, characters:CASE row.characters WHEN "\\N" THEN "" ELSE row.characters END}]-> (m) ;
// You might bump into issues like :
--> At /usr/local/Cellar/neo4j/3.5.8/libexec/import/title.principals.tsv @ position 10448883 -  there's a field starting with a quote and whereas it ends that quote there seems to be characters in that field after that ending quote. That isn't supported. This is what I read: 'The Captain and the Kids" '
--> At /usr/local/Cellar/neo4j/3.5.8/libexec/import/title.principals.tsv @ position 12831990 -  there's a field starting with a quote and whereas it ends that quote there seems to be characters in that field after that ending quote. That isn't supported. This is what I read: 'Observations of a Mere Man" '
--> At /usr/local/Cellar/neo4j/3.5.8/libexec/import/title.principals.tsv @ position 21433652 -  there's a field starting with a quote and whereas it ends that quote there seems to be characters in that field after that ending quote. That isn't supported. This is what I read: 'Danny Dunn" '
--> At /usr/local/Cellar/neo4j/3.5.8/libexec/import/title.principals.tsv @ position 24898191 -  there's a field starting with a quote and whereas it ends that quote there seems to be characters in that field after that ending quote. That isn't supported. This is what I read: 'The Story of Lindy Leigh" '
// 35368810 lines
// Maybe caused by a bug in Neo4J? Seems solved by splitting the file up into 2M-lined files by :
split -l 2000000 /usr/local/Cellar/neo4j/3.5.8/libexec/import/title.principals.tsv1 /usr/local/Cellar/neo4j/3.5.8/libexec/import/title.principals.tsv
// and then loading the smaller files sequentially :
load csv with headers from "file:///title.principals.tsvaa" as row fieldterminator "\t"
match (p:Person {nconst: row.nconst}), (m:Movie {tconst: row.tconst})
create (p) -[r:PLAYS_IN {category: row.category
, job:CASE row.job WHEN "\\N" THEN "" ELSE row.job END
, characters:CASE row.characters WHEN "\\N" THEN "" ELSE row.characters END}]-> (m) ;

// load ratings as new properties of the Movie-nodes
using periodic commit 500
load csv with headers from "file:///title.ratings.tsv" as row fieldterminator "\t"
match (m:Movie {tconst: row.tconst})
set m.averageRating = row.averageRating, m.numVotes = row.numVotes;

// In case you need to restart :
match (n:Person) delete n;
match (m:Movie) delete m;
match ()-[r:playsIn]->() delete r;

