##��װmongodb
1. ����

	 	wget -c https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-2.6.5.tgz

2. ��ѹ��װ

		tar zxvf mongodb-linux-x86_64-2.6.5.tgz
		mongodb-linux-x86_64-2.6.5
	����

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
3. ����

		mongodb/bin/mongod --dbpath=mongodb/data --logpath mongodb/logs/log.log -fork
4. ����mongodb��shellģʽ 

		 mongodb/bin/mongo
		 
##��װnodejs
1. ȷ�Ϸ�������nodejs���뼰���������������û�п�ͨ�������������װ

		yum -y install gcc gcc-c++ openssl-devel
2. ����NodeJSԴ�������ѹ��

		wget -c http://nodejs.org/dist/v0.10.29/node-v0.10.29.tar.gz
		tar zxvf node-v0.10.29.tar.gz

3. ���á����롢��װ��

		./configure
		make 
		make install
		
##��װmaven
Centos7 concludes maven, So you could build or run maven project directly. 