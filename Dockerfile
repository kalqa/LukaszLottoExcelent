FROM openjdk:18.0.1
ADD target/LukaszLottoExcelent-1.0-SNAPSHOT.jar .
EXPOSE 8000
CMD java -jar LukaszLottoExcelent-1.0-SNAPSHOT.jar