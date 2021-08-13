# ExposedSimpleTest
简单的性能测试

原生JDBC 关闭自动提交 <br/>
testNative(connection) //insert time: 11086ms ,select time: 4312ms

原生JDBC 开启自动提交<br/>
testNative2(connection) // insert time: 9324ms  select time: 2529ms

exposed框架<br/>
testExposed(database) // insert time: 37041ms ,select time: 27878ms
