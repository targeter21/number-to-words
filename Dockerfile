FROM maven:3.6.0-jdk-8-alpine as builder
COPY . /home/maven/complete
WORKDIR /home/maven/complete
RUN mvn package

FROM oracle/graalvm-ce:1.0.0-rc11 as graalvm
COPY --from=builder /home/maven/complete/ /home/maven/complete/
WORKDIR /home/maven/complete
RUN native-image --no-server \
                 --class-path /home/maven/complete/target/*.jar \
                 -H:+ReportUnsupportedElementsAtRuntime \
                 -H:+AllowVMInspection \
                 -H:Name=complete \
                 -H:Class=com.interview.Interview

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/maven/complete/complete .
ENTRYPOINT ["./complete"]
