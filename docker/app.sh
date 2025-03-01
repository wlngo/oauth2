#!/bin/bash
# shellcheck disable=SC1126
# shellcheck disable=SC2046
mkdir -p /opt/gc-logs
java -Xms1024m -Xmx1024m \
  -Xlog:gc:/opt/gc-logs/gc-$(date '+%Y-%m-%d').log:time \
  -XX:+HeapDumpOnOutOfMemoryError \
  -XX:HeapDumpPath=/opt/heapdump/dump-$(date '+%Y-%m-%d').hprof \
  -Dfile.encoding=UTF-8 \
  -Xshare:off \
  --add-opens java.base/java.lang=ALL-UNNAMED \
  --add-opens java.base/java.io=ALL-UNNAMED \
  --add-opens java.base/java.math=ALL-UNNAMED \
  --add-opens java.base/java.net=ALL-UNNAMED \
  --add-opens java.base/java.nio=ALL-UNNAMED \
  --add-opens java.base/java.security=ALL-UNNAMED \
  --add-opens java.base/java.text=ALL-UNNAMED \
  --add-opens java.base/java.time=ALL-UNNAMED \
  --add-opens java.base/java.util=ALL-UNNAMED \
  --add-opens java.base/jdk.internal.access=ALL-UNNAMED \
  --add-opens java.base/jdk.internal.misc=ALL-UNNAMED \
  --add-opens java.base/java.util=ALL-UNNAMED \
  --add-opens java.base/java.lang=ALL-UNNAMED \
  --add-opens java.base/jdk.internal.perf=ALL-UNNAMED \
  --add-exports java.base/jdk.internal.perf=ALL-UNNAMED \
  --add-opens java.management/sun.management.counter.perf=ALL-UNNAMED \
  --add-opens java.management/sun.management.counter=ALL-UNNAMED \
  -Dspring.profiles.active=prod \
   oauth2-service.jar
