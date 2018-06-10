javac -d output/appfuncs appsrc/appfuncs/appfuncs/demo/Test.java  appsrc/appfuncs/module-info.java
javac --module-path output -d output/appstart appsrc/appstart/module-info.java appsrc/appstart/appstart/demo/AppDemo.java
java --module-path output -m appstart/appstart.demo.AppDemo
