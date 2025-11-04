# Full-Stack Template 


## Instalation

```bash

git clone https://github.com/devero11/fullstack-server-setup.git


```

## Starting the server

The project is made of 3 components:
 1. Angular
 2. Spring-Boot
 3. Postgres

All 3 components need to be running for everything to work:

1. Start-up Postgres using Docker-Compose:

```bash

    # Navigate to postgres directory
    cd postgres
    
    # Run docker-compose
    # The -d flag runs docker-compose in detached mode (runs in background, lets you use the terminal after)
    docker-compose up -d   

    # Check the status of the database server
    sudo docker ps

    #Status should be Up + some amount of time

```

2. Start-up Spring-Boot using Maven:

```bash

    # Navigate to Spring-Boot directory
    cd ../spring-boot
    
    # Run spring-boot
    ./mvnw spring-boot:run
    
    # Unfortunately, Spring-Boot takes over your shell
    # You could run nohup ./mvnw spring-boot:run but you will still get all the logs when they come
    
    # Use tmux like a big man or just make another terminal instance/tab


```

3. Start-up Angular using npm:

```bash

    # Navigate to Angular directory
    cd ../angular
    
    # Run Angular 
    npm run ng serve #or 'ng serve' if that works(pls dont copy the comment as well) 
    
    #Same as spring-boot, angular takes over the shell    


```

4. Go to [localhost:4200](http://localhost:4200)

Predefined routes are /login /register /user





