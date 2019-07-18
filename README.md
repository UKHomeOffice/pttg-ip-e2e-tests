# pttg-ip-e2e-tests
IPS end to end integration test suite 

To run the test locally, make sure that you have docker and docker-compose installed, as well as VNC viewer.

From the project root directory, execute the following command:

`docker-compose -f docker-compose.yml build`

This will build the necessary images needed by the docker compose file. One of the images built will be the one created
by the Dockerfile_e2e file that contains the tests that will have to run.

After building the images, you can bring up the whole environment by executing:

`docker-compose -f docker-compose.yml up`

This will bring up all the services mentioned in the docker-compose file and execute the tests.

It's possible to not execute the tests, by just commenting out the `pttg-ip-e2e-tests` service in the docker-compose.yml.

After the environments are up, selenium hub will be located at:

http://localhost:4444/

and the UI will be located at:

http://localhost:8000/#!/familymigration

If tests are run automatically from the docker container, the results will be located in the `out` directory.

Tests can also be run manually via IntelliJ or command line once the docker-compose is up.
To run the test manually and see their outcome it's possible to run the VNC Viewer
and point it at localhost:5900. It shouldn't require a password to connect and if the chrome
box is up, it will connect to it. Once the tests are kicked off and selenium is triggered, you
should be able to see the browser appearing into the remote chrome box.