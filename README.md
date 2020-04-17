


# JDBC App

 - Compulsory - all bullets
 - Optional - all bullets
 - Bonus - all bullets


## Tasks regarding Compulsory part

 - [x] Database, user and tables creation
 - src\main\resources\script.sql
 
 - [x] Create the _singleton_ class _Database_ that manages a connection to the database
 - src\main\java\db\Database
 - [x] DAO classes
 - src\main\java\dao\ArtistController
 - src\main\java\dao\AlbumController
 
 - [x] Implement a simple test using your classes
 - src\test\java\AlbumManagerTest

## Tasks regarding Optional part

 - [x] Create the necessary table(s) in order to store _charts_ in the database (a chart contains some albums in a specific order)
 - src\main\resources\script.sql
 
 - [x] Create an object-oriented model of the data managed by the Java application.
 - src\main\java\entities
 
 - [x] Generate random data and insert it into the database.
 - src\main\java\app\AlbumManager\insertRandomData
 
 - [x] Display the ranking of the artists, considering their positions in the charts
 - src\main\java\dao\ChartAlbumController
 - src\main\java\dao\ChartController\displayRanking
 - Output example:

    Choose chart id: 1
    
    Ranking:
    Rank. Album - Artist
    
    2. Saxophone - Renoir
    3. Electric Guitar - Eminem
    4. Acoustic Guitar - Vincent
    7. Drums - Picasso
    9. Bass Guitar - Bernini
    11. Harp - Edward Hopper
    13. Xylophone - Eminem
    15. Bass Guitar - Joan Miro
    18. Organ - Munch
    19. Piano - Picasso
    20. Flute - Manet
    23. Harmonica - Ansel Adams
    26. Saxophone - Eminem
    27. Electric Guitar - Renoir
       
    
    Process finished with exit code 0


 - [x] (*) For additional points, you may consider generating suggestive HTML reports, using [FreeMarker](https://freemarker.apache.org/) or other reporting tool.
 - [demo](https://codepen.io/johnycode/pen/OJyPVmR)
 - Configuration: src\main\java\freemarker\FreeMarkerConfiguration
 - Template: templates\template.html
 - Result: report.html
 - Method: src\main\java\dao\ChartController\generateHTMLReport

## Tasks regarding Bonus part

 - [x] Use a _connection pool_ in order to manage database connections (**Apache Commons DBCP**)
 - src\main\java\db\ConnectionPool

 - [x] [ThreadPoolExecutor](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ThreadPoolExecutor.html)
 - src\main\java\thread_pool_executor

 - [x] Use [Visual VM](https://visualvm.github.io/) in order to monitor the execution of your application.
 -  resources/visualvm.png 

