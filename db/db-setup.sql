-- generate a new password for the db schema user we are going to create (using lastpass or the like)
--
-- run ```kubectl get secret rds-admin-creds -o yaml``` to get secrets, including postgresql password
-- look for db_root_pass: <psql password>
--
--  run ```echo <psql password> | base64 --decode``` to decode the db root password
--
-- run ```kubectl get pods``` to find the name of the database pod
-- look for the pod 'spon-psql-client-' and copy its name
--
-- run ```kubectl exec -it <pod name> /bin/bash``` to run a shell on the pod
--
-- run ```psql -h$APP_DB_HOSTNAME -U$APP_DB_USERNAME -dpostgres -W``` in the  shell on the pod. You will be prompted for the db root password (obtained above)
--
-- run ```\c pttgdev``` to connect to pttgdev database§
--
-- run the ```create user``` command (from below) to create a user for the schema with the new password generated above for the new schema user, in place of password 'pttg'
--
-- run the ```CREATE SCHEMA``` command (from below) to create the schema
--
-- run the ```grant all privileges``` command (from below) to give the new user privileges on the new schema
--
-- add the secret (password for the new schema user) to Kubernetes (see ```https://kubernetes.io/docs/concepts/configuration/secret/```)
-- *****  USE ```echo -n mysecret | base64``` to encode the password to avoid an extra newline character  (postgres wont accept it otherwise) ******


create database pttgdev;

-- connect to pttgdev once logged in as root
\c pttgdev

CREATE SCHEMA IF NOT EXISTS pttg_rps;

ALTER SCHEMA pttg_rps OWNER TO postgres;

create user pttg with password 'pttg';

grant all privileges on SCHEMA pttg_rps to pttg;
