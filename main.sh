#!/bin/bash
set -e

JDK11=$HOME/sources/jdk11

mv(){
   echo 'Using Java 11' && JAVA_HOME=$JDK11 && ./mvnw "${@}"
}

install (){
    mv install
}

run(){
    echo 'Using Java 11' && $JDK11/bin/java -jar rest/target/rest-0.0.1-uber.jar
}

create(){
    curl --header "Content-Type: application/json" \
            --request POST \
            "http://localhost:8080/games"
}

move(){
    gameId=$1
    pitId=$2
    curl --header "Content-Type: application/json" \
            --request PUT \
            "http://localhost:8080/games/${gameId}/pits/${pitId}"
}


$@
