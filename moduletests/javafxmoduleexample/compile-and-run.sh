javac -d appsrc/output --module-source-path appsrc/ appsrc/jfxmodules/module-info.java appsrc/jfxmodules/com/shermanmarshall/jfxmodules/Test.java appsrc/jfxcode/module-info.java appsrc/jfxcode/com/shermanmarshall/jfxcode/JFXTest.java 

java --module-path appsrc/output -m jfxcode/com.shermanmarshall.jfxcode.JFXTest
