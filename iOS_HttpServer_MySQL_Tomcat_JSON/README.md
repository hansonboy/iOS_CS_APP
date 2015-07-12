#菜鸟讲述如何实现APP开发的客户端和服务器模型。
## iOS_HttpServer_MySQL_Tomcat_JSON
### The app server intends to implement that  through the tomcat server can get data from MySQL and transport  data to  client by JSON model.
#####so here is the list of what we have implemented:
1.packaging the JDBC and reflect method to transfer between the Java Bean and MySQL data
  we should know the difference between Class  and Class<?> and class<T> and Object
2.use Json as a transfom format beween client and server
