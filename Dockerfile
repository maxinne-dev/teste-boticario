FROM gradle:latest
LABEL authors="maxinne"

EXPOSE 8080

WORKDIR /project
COPY . .
RUN gradle :build

ENTRYPOINT ["gradle", ":bootRun"]