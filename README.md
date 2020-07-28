
### 1. Introduction

The main purpose of this project is the familiarization and the evaluation of the RDF-3X (RDF-
Triple Express) [1] engine, an engine which manages scalable RDF data and achieves excellent
performance, outperforming the previously best RDF systems.
For evaluating the performance of RDF-3X, we used two large datasets with different
characteristics and compared the query run-times. All experiments were conducted on DELL PC with a
Core 2 Quad processor, 4 GBytes of memory, and running a 64-bit Linux Ubuntu OS. We repeated all
queries five times and took the mean value of them in order to avoid artifacts caused by OS activity.

### 2. Barton dataset

For the first experiment we used the Barton Library dataset [2] and the SPARQL queries
proposed as a benchmark in [1][3]. The Barton dataset, is a complete dump of the MIT Libraries
Barton catalog and is available in the web in two formats: MODS and MODS/RDF. Due to the fact that
RDF-3X can import only NTriples/Turtle RDF data, we had to convert the downloaded MODS/RDF
data into Ntriples data format. Firstly, we got the 11,221 files of the dataset and we converted them in
62 files of mostly 1.000.000 triples using the Jena framework [4] in order to be processed more easily.
Thereafter, as described in [3], we converted these data into NTriple format using the Redland parser [5]
and more precisely the Raptor RDF Syntax Library [6] (After the usual configure — make — make install
the conversion can be run like so: rapper -e -o ntriples – file://bartonX.rdf# > bartonX.nt.). Finally, we merged the
62 .nt files into one and we loaded the resulted NTriple form dataset into RDF-3X engine. The original
data was 4.5 GB in MODS/RDF format, 6.5 GB in triple form and 2.7 GB in the RDF-3X store
including all indexes and the string dictionary.
After we had downloaded the RDF-3X version 1.7 source code, we edited the rdf3xload.cpp
and rdf3xquery.cpp so as to be able to count the load and queries times since RDF-3X doesn't support
this functionality. After building it, we loaded the Barton dataset in 12,40 min (mean time), occupying
2.7 GB space. As mentioned in paper, Barton dataset was loaded in 13 min and had size 2.8 GB (Table
1).



|              |    Barton dataset |      |   Yago2 dataset |    |
|--------------|:------------|-----------:|----------|----------:|
|              | Load time   |  DB size  | Load time | DB size |
| RDF-3X-0.3.7 |  12.40 min  |  2.7 GB   | 15.10 min | 2.7 GB  |
| RDF-3X-0.3.5 |    13 min   |  2.8 GB   |    -      |   -     |

(^) Load timeBarton dataset DB size Load timeYago 2 dataset^ DB size
RDF-3X-0.3. 7 12.40 min 2.7 GB 15.10 min 2.7 GB
RDF-3X-0.3.5 13 min 2.8 GB - -
Table 1: Database load after triple construction
The difference in query times is rational because our DB size is slightly smaller than that of
paper's, our PC has better characteristics than that of paper's, and finally the version of the RDF-3X
source code we used is the latest one, released in the end of 2011 whereas the engine that was used in


the paper was the first one, released in 2010. After we had loaded the dataset we run the queries
presented in the Appendix. The query run times are shown in table 2 and figure 2.

|              |  Q1  | Q2 | Q3 |Q4 |Q5|  Q6 |   Q7 |
|--------------|------|----|---|----|----|---|-----|
| RDF-3X-0.3.7 |  0.001 | 0.84 |2.07 | 1.45 | 0.48 | 1.05| 1.12 |
| RDF-3X-0.3.5 | 0.001 | 1.17 |2.22 | 1.58 | 0.49 | 1.20| 1.26 |



```
Table 2 : Query run-times in seconds for the Barton dataset
```
```
Figure 2 : Query run-times in 10 -^2 seconds for the Barton dataset
```
The observation is that RDF-3X performs a little better in our case for the same reasons we mentioned
before.

### 3. YAGO2 Dataset

The Barton dataset is relatively homogeneous, as it describes library data. As a second dataset we
therefore used Yago2 [7], an extension of YAGO [8] knowledge base, which consists of facts extracted
from Wikipedia (exploiting the infoboxes and category system of Wikipedia), GeoNames and
integrated with the WordNet thesaurus. It contains 447 million facts about 9.8 million entities. Human
evaluation confirmed an accuracy of 95% of the facts in YAGO2. YAGO2 dataset is available in
Ntriples format consuming 2.0 GB space. We used YAGO2 dataset and not the YAGO one used in [1]
since we weren't able to run the queries presented there.

After downloading YAGO2, we loaded YAGO2 dataset in 15.10 min, occupying 2.7 GB for all
indexes and the string dictionary (figure 1). After we had loaded the dataset, we run the queries

```
0
```
```
20
```
```
40
```
```
60
```
```
80
```
```
100
```
```
120
```
```
140
```
```
160
```
```
A1 A2 A3 A4 A5 A6 A7 A
```
```
Run
```
**- time (10^
- 2sec)**

```
Queries
```
## Barton dataset

```
RDF-3X-0.
RDF-3X-0.
```

presented in the Appendix. These queries are not identical to those in the paper. We tried to reformulate
YAGO queries presented in [1] in order to be applicable in the YAGO2 dataset, by changing
namespaces and predicate names. These queries have three different application scenarios, entity-
oriented, relationship-oriented, and queries with unknown predicates. These queries are more "natural"
than the Barton queries, as they are standard SPARQL queries without any aggregations and with
explicitly given predicates. On the other hand, the queries are much larger (requiring more many-to-
many joins) and thus more difficult to optimize and execute. The query run times are shown in Table 3
and figure 3.

A1 A2 A3 B1 B2 B3 C1 C
RDF-3X-0.3. 7 0.0 3 0.06 0.05 0.06 0.07 0.03 0.75 1.
RDF-3X-0.3. 5 0.02 0.02 0.02 0.01 0.05 0.01 0.61 1.

```
Table 3 : Query run-times in seconds for the Yago 2 dataset for RDF-3X-0.3. 7 and Yago dataset for
RDF-3X-0.3. 5.
```
```
Figure 3: Query run-times in 10 -^2 seconds for the Yago 2 dataset for RDF-3X-0.3. 7 and Yago dataset for
RDF-3X-0.3. 5.
```
In this experiment, we observe that the query run-times are a little greater in our case except of query
A8. This occurs mostly because the queries we examined had less literals in the object section of the
query graph pattern and thus the system had to obtain more result sets, resulting in greater query run-
times. Another fact is that the YAGO2 dataset is a little bigger than simple YAGO dataset since
YAGO2 is an extension of YAGO dataset and as a consequence we expect that when a dataset is bigger
than another, the query run-times will be increased.

```
0
```
```
20
```
```
40
```
```
60
```
```
80
```
```
100
```
```
120
```
```
140
```
```
160
```
```
A1 A2 A3 A4 A5 A6 A7 A
```
```
Run
```
**- time (10^
- 2 sec)**

```
Queries
```
## YAGO2 and YAGO dataset

```
RDF-3X-0.
RDF-3X-0.
```

## Appendix SPARQL Queries

### Barton Dataset.

prefix rdfs: [http://www.w3.org/2000/01/rdf-schema#](http://www.w3.org/2000/01/rdf-schema#)
prefix rdf: [http://www.w3.org/1999/02/22-rdf-syntax-ns#](http://www.w3.org/1999/02/22-rdf-syntax-ns#)
prefix barton: [http://simile.mit.edu/2006/01/ontologies/mods3#](http://simile.mit.edu/2006/01/ontologies/mods3#)
prefix bartonLng: [http://simile.mit.edu/2006/01/language/iso639-2b](http://simile.mit.edu/2006/01/language/iso639-2b)

Q1: select count ?c where {?a a ?c}

Q2: select count ?bp where {?as a barton:Text; ?bp ?bo. ?bp rdf:type ?x}

Q3: select duplicates ?bp ?bo where {?as a barton:Text; ?bp ?bo.}

Q4: select duplicates ?bp ?bo where {?as a barton:Text; ?bp ?bo; barton:language bartonLng:fre.}

Q5: select ?as ?co where {?as barton:origin <info:marcorg/DLC>; barton:records ?bo. ?bo a ?co. filter
(?co != barton:Text)}

Q6: select count ?ap where {{?as a barton:Text} union {?as barton:records []; a barton:Text} ?as ?ap
[].}

Q7:select ?as ?bo ?co where {?as barton:point "end"; barton:encoding ?bo; a ?co}

Yago2 Dataset.

prefix rdfs: [http://www.w3.org/2000/01/rdf-schema#](http://www.w3.org/2000/01/rdf-schema#)
prefix rdf: [http://www.w3.org/1999/02/22-rdf-syntax-ns#](http://www.w3.org/1999/02/22-rdf-syntax-ns#)
prefix yago: [http://www.mpii.de/yago/resource/](http://www.mpii.de/yago/resource/)

A1: select ?GivenName ?FamilyName where {?p yago:hasGivenName ?GivenName. ?p
yago:hasFamilyName ?FamilyName. ?p rdf:type ?scientist. ?scientist rdfs:label ”scientist”. ?p
yago:wasBornIn ?city. ?city yago:isLocatedIn ?switzerland. ?switzerland
yago:hasPreferredName ”Switzerland”. ?p yago:hasAcademicAdvisor ?a. ?a
yago:wasBornIn ?city2. ?city2 yago:isLocatedIn ?germany.}

A2: select ?n ?x where {?a yago:hasPreferredName ?n. ?a rdf:type ?actor. ?actor rdfs:label "actor". ?a
yago:livesIn ?city. ?a yago:actedIn ?m1. ?a yago:directed ?m2. ?city yago:isLocatedIn ?s. ?m
rdf:type ?movie. ?movie rdfs:label "movie". ?m2 rdf:type ?movie2. ?movie2 rdfs:label "movie".}

A3. select distinct ?n ?co where { ?p yago:isCalled ?n. { ?p rdf:type ?actor. ?actor rdfs:label "actor"}


union { ?p rdf:type ?actor. ?actor rdfs:label "athlete"} ?p yago:wasBornIn ?c. ?c
yago:isLocatedIn ?co. ?p rdf:type ?t.}

B1. select distinct ?name1 ?name2 where {?a1 yago:hasPreferredName ?name1. ?a
yago:hasPreferredName ?name2. ?a1 yago:actedIn ?movie. ?a1 yago:actedIn ?c1. ?a
yago:livesIn ?c2. ?a2 yago:actedIn ?movie. ?c1 yago:isLocatedIn ?a. ?c2 yago:isLocatedIn ?b.}

B2. select ?name1 ?name2 where {?p1 yago:hasPreferredName ?name1. ?p
yago:hasPreferredName ?name2. ?p1 yago:isMarriedTo ?p2. ?p1 yago:wasBornIn ?city. ?p
yago:wasBornIn ?city.}

B3. select distinct ?name1 ?name2 where {?p1 yago:hasFamilyName ?name1. ?p
yago:hasFamilyName ?name2. ?p1 rdf:type ?scientist1. ?p2 rdf:type ?scientist2. ?scientist1 rdfs:label
"scientist". ?scientist2 rdfs:label "scientist". ?p1 yago:hasWonPrize ?award. ?p
yago:hasWonPrize ?award. ?p1 yago:wasBornIn ?city. ?p2 yago:wasBornIn ?city.}

C1. select distinct ?name1 ?name2 where {?p1 yago:hasFamilyName ?name1. ?p
yago:hasFamilyName ?name2. ?p1 rdf:type ?scientist. ?p2 rdf:type ?scientist. ?scientist rdfs:label
"scientist". ?p1 ?c ?city. ?p2 ?c2 ?city. ?city rdf:type ?cityType. ?cityType rdfs:label "city".}

C2. select distinct ?name where {?p yago:hasPreferredName ?name. ?p ?any1 ?c1. ?p ?any2 ?c2. ?c
rdf:type ?city. ?c2 rdf:type ?city2. ?city2 rdfs:label "city". ?city rdfs:label "city". ?c
yago:isCalled ?London. ?c2 yago:isCalled ?Paris.}


References

[1] T. Neumann and G. Weikum. RDF-3X: a RISC-style engine for RDF. Proceedings of the
VLDB Endowment, 1(1):647–659, 2008.

[2] [http://simile.mit.edu/wiki/Dataset:_Barton](http://simile.mit.edu/wiki/Dataset:_Barton)

[3] D. J. Abadi, A. Marcus, S. R. Madden, and K. Hollenbach. Scalable semantic web data
management using vertical partitioning. In VLDB ’07: Proceedings of the 33rd international
conference on Very large databases, pages 411–422. VLDB Endowment, 2007.

[4] K. Wilkinson, C. Sayers, H. Kuno, D. Reynolds, et al. Efficient RDF storage and retrieval in
Jena2. In Proceedings of SWDB, volume 3, pages 7–8. Citeseer, 2003.

[5] [http://librdf.org/](http://librdf.org/)

[6] [http://librdf.org/raptor/](http://librdf.org/raptor/)

[7] Johannes Hoffart , Fabian M. Suchanek , Klaus Berberich , Gerhard Weikum. YAGO2: A
Spatially and Temporally Enhanced Knowledge Base from Wikipedia.Research Report MPI-I-
2010 - 5 - 007, Max-Planck-Institut für Informatik, November 2010.

[8] Fabian M. Suchanek, Gjergji Kasneci, and Gerhard Weikum. Yago: a core of semantic
knowledge. In WWW, pages 697–706, 2007.


