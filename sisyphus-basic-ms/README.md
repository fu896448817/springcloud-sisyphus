# thoth-basic-ms

* **通用基础服务**


* **v0.1.0-SNAPSHOT**：Project init


java -jar  -Dconfig.profile=test -XX:+PrintGCDateStamps -XX:+PrintGCDetails -verbose:gc -Xloggc:/home/thoth/gc/thoth_data_gc.%t.log
-XX:ErrorFile=/home/thoth/gc/thoth_data_hs_err_%t.log 
-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/home/thoth/gc/
   -Djava.awt.headless=true -Djava.awt.headless=true -jar thoth-data-rest-0.0.1-SNAPSHOT-exec.jar