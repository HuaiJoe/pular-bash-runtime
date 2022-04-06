FROM openjdk:8

ENV APP_HOME=/user/local/service
ENV TZ='Asia/Shanghai'

COPY archive ${APP_HOME}/my_pulsar_homework

WORKDIR ${APP_HOME}/my_pulsar_homework
ENTRYPOINT ["/bin/sh", "-c","./bin/receive_then_send_to_pulsar.sh -u pulsar://192.168.49.2:30734 -f oed/pulsar/oed-topic -t oed/pulsar/oed-topic2 -n sub1"]