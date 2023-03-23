### Usage

#### Config database
Don't forget to create two tables named `temperature` and `humidity`, or you can use other table if you like, but you need to make some change in the method of `doGet()` of `GetDataServlet`, to make sure generate the correct sql statement. 

```java
sql = "select * from " + your table + " where (date <= '" + endDate + "' AND date >= '" + strDate + "')";
```

I use MySQL in this demo, you can add other database by implement the `Database` interface.

And go to `getConnection()` method of `GetDataServlet`, and change the first line:

```java
Database database = new MysqlDatabase("jdbc:mysql://localhost:3306/greenhouse", "root", "778899");
// to
Database database = new YourDatabase("your config", "root", "778899");
```

#### Test
```shell
curl -X GET "http://localhost:8080/humidity?start=2023-03-19&end=2023-03-21"

[{"value":23.0,"date":"2023-03-19"},{"value":25.0,"date":"2023-03-19"},{"value":25.0,"date":"2023-03-20"},{"value":28.0,"date":"2023-03-20"}]

curl -X GET "http://localhost:8080/temperature?start=2023-03-19&end=2023-03-21"
```


