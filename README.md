# user

`Not Mirco Service`

基于JWT加密的注册和登陆模拟

## Development

To start your application in the dev profile, simply run:

    ./gradlew

## Building for production

To optimize the platform application for production, run:

    ./gradlew -Pprod clean bootRepackage

To ensure everything worked, run:

    java -jar build/libs/*.jar
