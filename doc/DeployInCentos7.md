##安装mongodb
1. 下载

	 	wget -c https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-2.6.5.tgz

2. 解压安装

		tar zxvf mongodb-linux-x86_64-2.6.5.tgz
		mongodb-linux-x86_64-2.6.5
	配置

		cd mongodb
		mkdir db
		mkdir logs
		cd bin
		vi mongodb.conf

		dbpath=mongodb/data
		logpath=mongodb/logs/log.log
		port=27017
		fork=true
		nohttpinterface=true
3. 启动

		mongodb/bin/mongod --dbpath=mongodb/data --logpath mongodb/logs/log.log -fork
4. 进入mongodb的shell模式 

		 mongodb/bin/mongo
		 
##安装nodejs
1. 确认服务器有nodejs编译及依赖相关软件，如果没有可通过运行以下命令安装

		yum -y install gcc gcc-c++ openssl-devel
2. 下载NodeJS源码包并解压。

		wget -c http://nodejs.org/dist/v0.10.29/node-v0.10.29.tar.gz
		tar zxvf node-v0.10.29.tar.gz

3. 配置、编译、安装。

		./configure
		make 
		make install
		
##安装maven
Centos7 concludes maven, So you could build or run maven project directly. 