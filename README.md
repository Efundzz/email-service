## Steps to push docker image to ECR
#### To install latest dependencies:
```bash
mvn clean install
```

#### To increment the application version:
```bash
mvn versions:set -DnewVersion=0.0.2-SNAPSHOT
```

#### To build the image, start docker and then run the below command. The image with the version set would be created.
```bash
mvn spring-boot:build-image
```

#### To push the docker image:
```bash
docker push 247423452789.dkr.ecr.eu-west-1.amazonaws.com/verification-service:0.0.2-SNAPSHOT
```

------------------------------------------------------------------------------------------

## Configuration needed before updating dependencies in local/ uploading the docker image to ECR
#### To login to aws
```bash
aws ecr get-login-password --region eu-west-1 --profile efundzz | docker login --username AWS --password-stdin 247423452789.dkr.ecr.eu-west-1.amazonaws.com
```

#### Before you check for access or publish your code, do the following step:
```bash
export EFUNDZZ_CODEARTIFACT_AUTH_TOKEN_EU_WEST1=$(aws codeartifact get-authorization-token --domain efundzz --domain-owner 247423452789 --profile efundzz --region eu-west-1 --query authorizationToken --output text)
```

#### To check if the export is done successfully, the below command should not return empty
```bash
echo $EFUNDZZ_CODEARTIFACT_AUTH_TOKEN_EU_WEST1
```

------------------------------------------------------------------------------------------

## Steps to configure aws in local:
#### To configure aws in your machine (if not done already):
```bash 
aws configure --profile efundzz
```

#### To check already configured aws details:
```bash
aws configure list --profile efundzz
```

#### To check access to efundzz codeartifact:
```bash
aws codeartifact list-domains --profile efundzz
```
```bash
aws codeartifact list-repositories-in-domain --domain efundzz --profile efundzz 
```
```bash
aws codeartifact describe-repository --domain efundzz --repository dbconnect --profile efundzz
```
