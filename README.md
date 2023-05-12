## CSF

## Server side

### Windows
```
set DO_STORAGE_KEY=
set DO_STORAGE_SECRETKEY=
set DO_STORAGE_ENDPOINT=sgp1.digitaloceanspaces.com
set DO_STORAGE_ENDPOINT_REGION=sgp1
```

### Macos/Linux
```
export DO_STORAGE_KEY=
export DO_STORAGE_SECRETKEY=
export DO_STORAGE_ENDPOINT=sgp1.digitaloceanspaces.com
export DO_STORAGE_ENDPOINT_REGION=sgp1
```

```
mvn clean spring-boot:run
```

## Client side
```
ng serve --proxy-config proxy-config.js
```

## Railway Deployment
```
ng build
```
copy all the files on the dist folder to the server static folder.