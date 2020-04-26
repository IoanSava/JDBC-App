



# JDBC App

✅ Compulsory - all bullets
 
✅ Optional - all bullets

✅ Bonus - all bullets


## Tasks regarding Compulsory part ⭐

 ✔️ Database, user and tables creation
 - src\main\resources\script.sql
 
✔️ Create the _singleton_ class _**Database**_ that manages a connection to the database
 - src\main\java\db\Database

 ✔️ **DAO** classes
 - src\main\java\dao\ArtistController
 - src\main\java\dao\AlbumController
 
✔️ Implement a simple test using your classes
 - src\test\java\AlbumManagerTest

## Tasks regarding Optional part ⭐⭐

✔️ Create the necessary table(s) in order to store _**charts**_ in the database (a chart contains some albums in a specific order)
 - src\main\resources\script.sql
 
✔️ Create an object-oriented model of the data managed by the Java application
 - src\main\java\entities
 
✔️ Generate random data and insert it into the database.
 - src\main\java\app\AlbumManager\insertRandomData
 
✔️ Display the **ranking** of the artists, considering their positions in the charts
 - src\main\java\dao\ChartAlbumController
 - src\main\java\dao\ChartController\displayRanking

✔️ :trophy: For additional points, you may consider generating suggestive HTML reports, using [FreeMarker](https://freemarker.apache.org/) or other reporting tool.
 - [demo](https://codepen.io/johnycode/pen/OJyPVmR)
 - Configuration: src\main\java\freemarker\FreeMarkerConfiguration
 - Template: templates\template.html
 - Result: report.html
 - Method: src\main\java\dao\ChartController\generateHTMLReport

## Tasks regarding Bonus part ⭐⭐⭐

 ✔️ Use a _connection pool_ in order to manage database connections (**Apache Commons DBCP**)
 - src\main\java\db\ConnectionPool

 ✔️ [ThreadPoolExecutor](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ThreadPoolExecutor.html)
 - src\main\java\thread_pool_executor

✔️ Use [Visual VM](https://visualvm.github.io/) in order to monitor the execution of your application.
 -  resources/visualvm.png 

