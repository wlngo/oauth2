# 使用官方的 OpenJDK 作为基础镜像
FROM eclipse-temurin:21
#设置时区
ENV TZ Asia/Shanghai
# 设置工作目录
WORKDIR /opt/oauth2-service
# 将项目的构建文件复制到容器中
COPY target/oauth2*.jar oauth2-service.jar
COPY docker/app.sh app.sh
# 赋予脚本执行权限
RUN chmod +x app.sh
# 暴露应用程序运行所需的端口
EXPOSE 8050
# 启动应用程序
CMD ["/bin/sh", "-c", "/opt/oauth2-service/app.sh"]
