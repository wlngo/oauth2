# 使用官方的 OpenJDK 作为基础镜像
FROM eclipse-temurin:21

# 设置工作目录
WORKDIR /opt/oauth2
# 将项目的构建文件复制到容器中
COPY target/oauth2*.jar oauth2-service
COPY docker/app.sh app.sh
# 暴露应用程序运行所需的端口
EXPOSE 8050
# 启动应用程序
CMD ["/bin/sh", "-c", "/opt/oauth2/app.sh"]
