# 基础镜像使用java

FROM java:8

# 作者

MAINTAINER irony

# VOLUME 指定临时文件目录为/tmp，在主机/var/lib/docker目录下创建了一个临时文件并链接到容器的/tmp

VOLUME /tmp

# 将jar包添加到容器中并更名为zzyy_docker.jar

ADD zhidao-0.0.1-SNAPSHOT.jar /zhidao-back-1.0.jar

# 运行jar包

ENTRYPOINT ["java","-jar","/zhidao-back-1.0.jar"]

#暴露9000端口作为微服务

EXPOSE 9000