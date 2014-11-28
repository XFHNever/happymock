##Mongodb
1. 下载镜像文件

		docker pull dockerfile/mongodb
2. run container
		
		sudo docker run -d -p 27017:27017 --name db dockerfile/mongodb


more from <a href="https://registry.hub.docker.com/u/dockerfile/mongodb/">dockerfile / mongodb</a>

##java+maven
1. download image
 
		sudo docker pull jamesdbloom/docker-java7-maven

2. run

		docker run -i -t -p 8080:8080 --name myjava --link db:db --rm jamesdbloom/docker-java7-maven 
3. git clone

		git clone <your repo url>
4. 修改mongodb配置。修改host
5. 修改pom.xml
 	
		<plugin>
			<artifactId>maven-compiler-plugin</artifactId>
			<version>2.3.2</version>
			<configuration>
				<source>1.6</source>
				<target>1.6</target>
			</configuration>
		</plugin>
6. package

		mvn package
7. run jar

		java -jar happy_mock_happy_work-1.0-SNAPSHOT.jar start -p 8080 -k 8081

##nodejs
1. 获取MongoDB的IPAddress

		sudo docker inspect ContainerID | grep -i ipaddr
2. 修改项目中连接Mongodb的host为上一步获取的IPAddress。
3. 获取myjava的IPAddress，修改/routers/mockitems.js中的compile  中的host为此处获取的address
4. 构建nodejs

	Dockerfile

		FROM dockerfile/nodejs
		
		RUN npm install -g bower grunt-cli
		
		# Install NPM dependencies
		
		ADD . /src
		
		WORKDIR /src
		
		
		RUN cd /src;  npm install
		
		EXPOSE 3000
		
		
		CMD ["npm", "start"]

	目录下，构建

		sudo docker build -t fuxie/mes .

5. 运行container
	
		sudo docker run -d -p 3000:3000 --name web --link mydb:mydb --link myjava:myjava fuxie/mes


##指令
1. centos关闭指定端口的进程

		kill -9  port
2. port has already been allocated

		sudo service docker restart

