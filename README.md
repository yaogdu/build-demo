JDK 版本 ： 1.8

使用方式

1. 检测应用是否启动成功
http://localhost:8080/index
返回结果 「Welcome」 即表示启动成功；

2. 提交任务
curl http://localhost:8080/submit-tasks
返回结果「Tasks completed and results written to file!」 即表示数据写入成功；
任务会把结果写到「results.txt」文件中；

3. 中断任务
curl http://localhost:8080/cancel-tasks
会返回已经生成的结果

4. 查看生成的结果，即results.txt内容
curl http://localhost:8080/read-results
会读取results.txt文件中的内容
