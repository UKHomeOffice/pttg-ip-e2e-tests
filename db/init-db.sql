--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.2
-- Dumped by pg_dump version 9.6.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- Name: reference_data; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE DATABASE RPS_AC_DB;

CREATE SCHEMA pttg_rps;

ALTER SCHEMA pttg_rps OWNER TO postgres;

CREATE SCHEMA pttg_access;

ALTER SCHEMA pttg_access OWNER TO postgres;

SET search_path = pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

