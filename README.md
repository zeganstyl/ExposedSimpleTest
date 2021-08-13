# ExposedSimpleTest
简单的性能测试

原生JDBC 关闭自动提交 <br/>
testNative(connection) //insert time: -, elect time: -

原生JDBC 开启自动提交<br/>
testNative2(connection) // insert time: -, select time: -

exposed框架<br/>
testExposed(database) // insert time: -, elect time: -
