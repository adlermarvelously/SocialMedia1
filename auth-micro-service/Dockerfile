# Burada kendimize ait özel bir docker imajı oluşturuyoruz.
# ilk olarak oluşturacağımız sistem için işletim sistemini seçiyoruz
# burada biz içinde işletim sistemi ve javaJDK17 olan sürümü seçiyoruz
# docker pull amazoncorretto:17.0.8-alpine3.18
FROM amazoncorretto:17.0.8-alpine3.18
# kendi projemizi bu bilgisayarın içine kopyalamak için işlem yapıyoruz
COPY build/libs/auth-micro-service-1.0-SNAPSHOT.jar app.jar
# son olarak uygulamamızın çalışması için onu başlatıyoruz.
ENTRYPOINT ["java","-jar","/app.jar"]