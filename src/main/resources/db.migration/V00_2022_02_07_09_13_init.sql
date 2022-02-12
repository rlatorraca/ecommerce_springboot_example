--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.25
-- Dumped by pg_dump version 12.9 (Ubuntu 12.9-0ubuntu0.20.04.1)

-- Started on 2022-02-12 12:23:51 AST

SET statement_timeout = 0;
SET lock_timeout = 0;
--SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2341 (class 1262 OID 16384)
-- Name: rlsp_shoponline_db; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE rlsp_shoponline_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.utf8' LC_CTYPE = 'en_US.utf8';


ALTER DATABASE rlsp_shoponline_db OWNER TO postgres;

--\connect rlsp_shoponline_db

SET statement_timeout = 0;
SET lock_timeout = 0;
--SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 219 (class 1255 OID 18317)
-- Name: validatepersonkey(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.validatepersonkey() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
declare exist integer;

/* NEW = carrega os dados que estao sendo inseridos (INSERT) e atualizados (UPDATE) */
/* OLD = carrega os dados da linha antiga antes de atualizar (UPDATE) */
BEGIN
	exist = (select count(1) from natural_person where id = NEW.person_id);
	if(exist <=0) then
		exist = (select count(1) from legal_person where id = NEW.person_id);
		if(exist <= 0) then
			RAISE EXCEPTION 'ID or PK not found to do an asssociation [RLSP]';
end if;
end if;
return NEW;
END;
$$;


ALTER FUNCTION public.validatepersonkey() OWNER TO postgres;

--
-- TOC entry 220 (class 1255 OID 18580)
-- Name: validatepersonproviderkey(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.validatepersonproviderkey() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
declare exist integer;

/* NEW = carrega os dados que estao sendo inseridos (INSERT) e atualizados (UPDATE) */
/* OLD = carrega os dados da linha antiga antes de atualizar (UPDATE) */
BEGIN
	exist = (select count(1) from natural_person where id = NEW.person_provider_id);
	if(exist <=0) then
		exist = (select count(1) from legal_person where id = NEW.person_provider_id);
		if(exist <= 0) then
			RAISE EXCEPTION 'ID or PK not found to do an asssociation [RLSP]';
end if;
end if;
return NEW;
END;
$$;


ALTER FUNCTION public.validatepersonproviderkey() OWNER TO postgres;

SET default_tablespace = '';

--
-- TOC entry 198 (class 1259 OID 18358)
-- Name: address; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.address (
                                id bigint NOT NULL,
                                address_line_01 character varying(255),
                                address_line_02 character varying(255),
                                address_type character varying(255) NOT NULL,
                                city character varying(255) NOT NULL,
                                country character varying(255) NOT NULL,
                                neighborhood character varying(255) NOT NULL,
                                number character varying(255) NOT NULL,
                                province character varying(255) NOT NULL,
                                zip_postal_code character varying(255) NOT NULL,
                                person_id bigint NOT NULL
);


ALTER TABLE public.address OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 18366)
-- Name: discount_coupon; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.discount_coupon (
                                        id bigint NOT NULL,
                                        coupon_description character varying(255) NOT NULL,
                                        coupon_validate date NOT NULL,
                                        percentual_value_discount numeric(19,2),
                                        real_value_discount numeric(19,2)
);


ALTER TABLE public.discount_coupon OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 18371)
-- Name: invoice_item_product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.invoice_item_product (
                                             id bigint NOT NULL,
                                             quantity double precision NOT NULL,
                                             product_id bigint NOT NULL,
                                             purchase_invoice_id bigint NOT NULL
);


ALTER TABLE public.invoice_item_product OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 18376)
-- Name: item_sale_ecommerce; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.item_sale_ecommerce (
                                            id bigint NOT NULL,
                                            quantity double precision NOT NULL,
                                            product_id bigint NOT NULL,
                                            product_sales_ecommerce_id bigint NOT NULL
);


ALTER TABLE public.item_sale_ecommerce OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 18381)
-- Name: legal_person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.legal_person (
                                     id bigint NOT NULL,
                                     email character varying(255) NOT NULL,
                                     name character varying(255) NOT NULL,
                                     telephone character varying(255) NOT NULL,
                                     business_number character varying(255) NOT NULL,
                                     category character varying(255),
                                     city_registration character varying(255),
                                     comercial_name character varying(255) NOT NULL,
                                     legal_name character varying(255) NOT NULL,
                                     province_registration character varying(255) NOT NULL
);


ALTER TABLE public.legal_person OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 18389)
-- Name: natural_person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.natural_person (
                                       id bigint NOT NULL,
                                       email character varying(255) NOT NULL,
                                       name character varying(255) NOT NULL,
                                       telephone character varying(255) NOT NULL,
                                       birthday date,
                                       sin_number character varying(255) NOT NULL
);


ALTER TABLE public.natural_person OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 18397)
-- Name: payment_method; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.payment_method (
                                       id bigint NOT NULL,
                                       description character varying(255) NOT NULL
);


ALTER TABLE public.payment_method OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 18402)
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
                                id bigint NOT NULL,
                                product_active boolean NOT NULL,
                                alert_stock_quantity boolean,
                                click_quantity integer,
                                product_depth double precision NOT NULL,
                                description text NOT NULL,
                                product_height double precision NOT NULL,
                                link_youtube character varying(255),
                                "ṕroduct_name" character varying(255) NOT NULL,
                                stock_quantity integer NOT NULL,
                                unit_type character varying(255) NOT NULL,
                                product_value numeric(19,2) NOT NULL,
                                product_weight double precision NOT NULL,
                                product_width double precision NOT NULL
);


ALTER TABLE public.product OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 18410)
-- Name: product_brand; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_brand (
                                      id bigint NOT NULL,
                                      description character varying(255) NOT NULL
);


ALTER TABLE public.product_brand OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 18415)
-- Name: product_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_category (
                                         id bigint NOT NULL,
                                         description character varying(255) NOT NULL
);


ALTER TABLE public.product_category OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 18420)
-- Name: product_evaluation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_evaluation (
                                           id bigint NOT NULL,
                                           description character varying(255) NOT NULL,
                                           grade integer NOT NULL,
                                           person_id bigint NOT NULL,
                                           product_id bigint NOT NULL
);


ALTER TABLE public.product_evaluation OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 18425)
-- Name: product_image; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_image (
                                      id bigint NOT NULL,
                                      source_image text NOT NULL,
                                      thumbnail text NOT NULL,
                                      product_id bigint NOT NULL
);


ALTER TABLE public.product_image OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 18433)
-- Name: product_sales_ecommerce; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_sales_ecommerce (
                                                id bigint NOT NULL,
                                                days_to_delivery integer NOT NULL,
                                                delivery_date timestamp without time zone NOT NULL,
                                                delivery_value numeric(19,2) NOT NULL,
                                                sale_date timestamp without time zone NOT NULL,
                                                total_discount numeric(19,2),
                                                total_value numeric(19,2) NOT NULL,
                                                billing_address_id bigint NOT NULL,
                                                discount_coupon_id bigint,
                                                payment_method_id bigint NOT NULL,
                                                person_id bigint NOT NULL,
                                                sales_invoice_id bigint NOT NULL,
                                                shipping_address_id bigint NOT NULL
);


ALTER TABLE public.product_sales_ecommerce OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 18438)
-- Name: purchase_invoice; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.purchase_invoice (
                                         id bigint NOT NULL,
                                         date_sale date NOT NULL,
                                         description character varying(255),
                                         discount_value numeric(19,2),
                                         federal_taxes numeric(19,2) NOT NULL,
                                         invoice_number character varying(255) NOT NULL,
                                         invoice_serie character varying(255) NOT NULL,
                                         provincial_taxes numeric(19,2) NOT NULL,
                                         total_value numeric(19,2) NOT NULL,
                                         person_id bigint NOT NULL,
                                         trade_payable_id bigint NOT NULL
);


ALTER TABLE public.purchase_invoice OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 18446)
-- Name: role_access; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role_access (
                                    id bigint NOT NULL,
                                    description character varying(255) NOT NULL
);


ALTER TABLE public.role_access OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 18451)
-- Name: sales_invoice; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sales_invoice (
                                      id bigint NOT NULL,
                                      invoice_number character varying(255) NOT NULL,
                                      invoice_serie character varying(255) NOT NULL,
                                      invoice_type character varying(255) NOT NULL,
                                      pdf text NOT NULL,
                                      xml text NOT NULL,
                                      product_sales_ecommerce_id bigint NOT NULL
);


ALTER TABLE public.sales_invoice OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 18324)
-- Name: seq_address; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_address
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_address OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 18326)
-- Name: seq_discount_coupon; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_discount_coupon
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_discount_coupon OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 18328)
-- Name: seq_item_sale_ecommerce; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_item_sale_ecommerce
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_item_sale_ecommerce OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 18330)
-- Name: seq_payment_method; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_payment_method
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_payment_method OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 18332)
-- Name: seq_person; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_person
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_person OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 18334)
-- Name: seq_product; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_product
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_product OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 18336)
-- Name: seq_product_brand; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_product_brand
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_product_brand OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 18338)
-- Name: seq_product_category; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_product_category
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_product_category OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 18340)
-- Name: seq_product_image; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_product_image
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_product_image OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 18342)
-- Name: seq_product_sales_ecommerce; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_product_sales_ecommerce
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_product_sales_ecommerce OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 18344)
-- Name: seq_purchase_invoice; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_purchase_invoice
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_purchase_invoice OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 18346)
-- Name: seq_role_access; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_role_access
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_role_access OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 18348)
-- Name: seq_sales_invoice; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_sales_invoice
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_sales_invoice OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 18350)
-- Name: seq_tracking_status; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_tracking_status
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_tracking_status OWNER TO postgres;

--
-- TOC entry 195 (class 1259 OID 18352)
-- Name: seq_trade_payable; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_trade_payable
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_trade_payable OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 18354)
-- Name: seq_trade_receivable; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_trade_receivable
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_trade_receivable OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 18356)
-- Name: seq_user_system; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_user_system
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_user_system OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 18459)
-- Name: tracking_status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tracking_status (
                                        id bigint NOT NULL,
                                        city character varying(255),
                                        country character varying(255),
                                        distribution_hub character varying(255),
                                        province character varying(255),
                                        status character varying(255),
                                        product_sales_ecommerce_id bigint NOT NULL
);


ALTER TABLE public.tracking_status OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 18467)
-- Name: trade_payable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.trade_payable (
                                      id bigint NOT NULL,
                                      description character varying(255) NOT NULL,
                                      due_date date NOT NULL,
                                      payment_date date,
                                      status_debtor character varying(255) NOT NULL,
                                      total_discount numeric(19,2),
                                      total_value numeric(19,2) NOT NULL,
                                      person_id bigint NOT NULL,
                                      person_provider_id bigint NOT NULL
);


ALTER TABLE public.trade_payable OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 18475)
-- Name: trade_receivable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.trade_receivable (
                                         id bigint NOT NULL,
                                         description character varying(255) NOT NULL,
                                         due_date date NOT NULL,
                                         payment_date date,
                                         status_creditor character varying(255) NOT NULL,
                                         total_discoutn numeric(19,2),
                                         total_value numeric(19,2) NOT NULL,
                                         person_id bigint NOT NULL
);


ALTER TABLE public.trade_receivable OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 18483)
-- Name: user_role_access; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_role_access (
                                         user_system_id bigint NOT NULL,
                                         role_access_id bigint NOT NULL
);


ALTER TABLE public.user_role_access OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 18486)
-- Name: user_system; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_system (
                                    id bigint NOT NULL,
                                    last_password_date date NOT NULL,
                                    login character varying(255) NOT NULL,
                                    password character varying(255) NOT NULL,
                                    person_id bigint NOT NULL
);


ALTER TABLE public.user_system OWNER TO postgres;

--
-- TOC entry 2315 (class 0 OID 18358)
-- Dependencies: 198
-- Data for Name: address; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2316 (class 0 OID 18366)
-- Dependencies: 199
-- Data for Name: discount_coupon; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2317 (class 0 OID 18371)
-- Dependencies: 200
-- Data for Name: invoice_item_product; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2318 (class 0 OID 18376)
-- Dependencies: 201
-- Data for Name: item_sale_ecommerce; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2319 (class 0 OID 18381)
-- Dependencies: 202
-- Data for Name: legal_person; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2320 (class 0 OID 18389)
-- Dependencies: 203
-- Data for Name: natural_person; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2321 (class 0 OID 18397)
-- Dependencies: 204
-- Data for Name: payment_method; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2322 (class 0 OID 18402)
-- Dependencies: 205
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2323 (class 0 OID 18410)
-- Dependencies: 206
-- Data for Name: product_brand; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2324 (class 0 OID 18415)
-- Dependencies: 207
-- Data for Name: product_category; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2325 (class 0 OID 18420)
-- Dependencies: 208
-- Data for Name: product_evaluation; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2326 (class 0 OID 18425)
-- Dependencies: 209
-- Data for Name: product_image; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2327 (class 0 OID 18433)
-- Dependencies: 210
-- Data for Name: product_sales_ecommerce; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2328 (class 0 OID 18438)
-- Dependencies: 211
-- Data for Name: purchase_invoice; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2329 (class 0 OID 18446)
-- Dependencies: 212
-- Data for Name: role_access; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2330 (class 0 OID 18451)
-- Dependencies: 213
-- Data for Name: sales_invoice; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2331 (class 0 OID 18459)
-- Dependencies: 214
-- Data for Name: tracking_status; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2332 (class 0 OID 18467)
-- Dependencies: 215
-- Data for Name: trade_payable; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2333 (class 0 OID 18475)
-- Dependencies: 216
-- Data for Name: trade_receivable; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2334 (class 0 OID 18483)
-- Dependencies: 217
-- Data for Name: user_role_access; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2335 (class 0 OID 18486)
-- Dependencies: 218
-- Data for Name: user_system; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2343 (class 0 OID 0)
-- Dependencies: 181
-- Name: seq_address; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_address', 1, false);


--
-- TOC entry 2344 (class 0 OID 0)
-- Dependencies: 182
-- Name: seq_discount_coupon; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_discount_coupon', 1, false);


--
-- TOC entry 2345 (class 0 OID 0)
-- Dependencies: 183
-- Name: seq_item_sale_ecommerce; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_item_sale_ecommerce', 1, false);


--
-- TOC entry 2346 (class 0 OID 0)
-- Dependencies: 184
-- Name: seq_payment_method; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_payment_method', 1, false);


--
-- TOC entry 2347 (class 0 OID 0)
-- Dependencies: 185
-- Name: seq_person; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_person', 1, false);


--
-- TOC entry 2348 (class 0 OID 0)
-- Dependencies: 186
-- Name: seq_product; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_product', 1, false);


--
-- TOC entry 2349 (class 0 OID 0)
-- Dependencies: 187
-- Name: seq_product_brand; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_product_brand', 1, false);


--
-- TOC entry 2350 (class 0 OID 0)
-- Dependencies: 188
-- Name: seq_product_category; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_product_category', 1, false);


--
-- TOC entry 2351 (class 0 OID 0)
-- Dependencies: 189
-- Name: seq_product_image; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_product_image', 1, false);


--
-- TOC entry 2352 (class 0 OID 0)
-- Dependencies: 190
-- Name: seq_product_sales_ecommerce; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_product_sales_ecommerce', 1, false);


--
-- TOC entry 2353 (class 0 OID 0)
-- Dependencies: 191
-- Name: seq_purchase_invoice; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_purchase_invoice', 1, false);


--
-- TOC entry 2354 (class 0 OID 0)
-- Dependencies: 192
-- Name: seq_role_access; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_role_access', 1, false);


--
-- TOC entry 2355 (class 0 OID 0)
-- Dependencies: 193
-- Name: seq_sales_invoice; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_sales_invoice', 1, false);


--
-- TOC entry 2356 (class 0 OID 0)
-- Dependencies: 194
-- Name: seq_tracking_status; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_tracking_status', 1, false);


--
-- TOC entry 2357 (class 0 OID 0)
-- Dependencies: 195
-- Name: seq_trade_payable; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_trade_payable', 1, false);


--
-- TOC entry 2358 (class 0 OID 0)
-- Dependencies: 196
-- Name: seq_trade_receivable; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_trade_receivable', 1, false);


--
-- TOC entry 2359 (class 0 OID 0)
-- Dependencies: 197
-- Name: seq_user_system; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_user_system', 1, false);


--
-- TOC entry 2111 (class 2606 OID 18365)
-- Name: address address_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);


--
-- TOC entry 2113 (class 2606 OID 18370)
-- Name: discount_coupon discount_coupon_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.discount_coupon
    ADD CONSTRAINT discount_coupon_pkey PRIMARY KEY (id);


--
-- TOC entry 2115 (class 2606 OID 18375)
-- Name: invoice_item_product invoice_item_product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice_item_product
    ADD CONSTRAINT invoice_item_product_pkey PRIMARY KEY (id);


--
-- TOC entry 2117 (class 2606 OID 18380)
-- Name: item_sale_ecommerce item_sale_ecommerce_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_sale_ecommerce
    ADD CONSTRAINT item_sale_ecommerce_pkey PRIMARY KEY (id);


--
-- TOC entry 2119 (class 2606 OID 18388)
-- Name: legal_person legal_person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.legal_person
    ADD CONSTRAINT legal_person_pkey PRIMARY KEY (id);


--
-- TOC entry 2121 (class 2606 OID 18396)
-- Name: natural_person natural_person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.natural_person
    ADD CONSTRAINT natural_person_pkey PRIMARY KEY (id);


--
-- TOC entry 2123 (class 2606 OID 18401)
-- Name: payment_method payment_method_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.payment_method
    ADD CONSTRAINT payment_method_pkey PRIMARY KEY (id);


--
-- TOC entry 2127 (class 2606 OID 18414)
-- Name: product_brand product_brand_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_brand
    ADD CONSTRAINT product_brand_pkey PRIMARY KEY (id);


--
-- TOC entry 2129 (class 2606 OID 18419)
-- Name: product_category product_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_category
    ADD CONSTRAINT product_category_pkey PRIMARY KEY (id);


--
-- TOC entry 2131 (class 2606 OID 18424)
-- Name: product_evaluation product_evaluation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_evaluation
    ADD CONSTRAINT product_evaluation_pkey PRIMARY KEY (id);


--
-- TOC entry 2133 (class 2606 OID 18432)
-- Name: product_image product_image_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_image
    ADD CONSTRAINT product_image_pkey PRIMARY KEY (id);


--
-- TOC entry 2125 (class 2606 OID 18409)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- TOC entry 2135 (class 2606 OID 18437)
-- Name: product_sales_ecommerce product_sales_ecommerce_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_sales_ecommerce
    ADD CONSTRAINT product_sales_ecommerce_pkey PRIMARY KEY (id);


--
-- TOC entry 2137 (class 2606 OID 18445)
-- Name: purchase_invoice purchase_invoice_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_invoice
    ADD CONSTRAINT purchase_invoice_pkey PRIMARY KEY (id);


--
-- TOC entry 2139 (class 2606 OID 18450)
-- Name: role_access role_access_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role_access
    ADD CONSTRAINT role_access_pkey PRIMARY KEY (id);


--
-- TOC entry 2141 (class 2606 OID 18458)
-- Name: sales_invoice sales_invoice_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_invoice
    ADD CONSTRAINT sales_invoice_pkey PRIMARY KEY (id);


--
-- TOC entry 2143 (class 2606 OID 18466)
-- Name: tracking_status tracking_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tracking_status
    ADD CONSTRAINT tracking_status_pkey PRIMARY KEY (id);


--
-- TOC entry 2145 (class 2606 OID 18474)
-- Name: trade_payable trade_payable_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trade_payable
    ADD CONSTRAINT trade_payable_pkey PRIMARY KEY (id);


--
-- TOC entry 2147 (class 2606 OID 18482)
-- Name: trade_receivable trade_receivable_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trade_receivable
    ADD CONSTRAINT trade_receivable_pkey PRIMARY KEY (id);


--
-- TOC entry 2149 (class 2606 OID 18495)
-- Name: user_role_access uk_6tg9le1bxknkw4v36s07709ia; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role_access
    ADD CONSTRAINT uk_6tg9le1bxknkw4v36s07709ia UNIQUE (role_access_id);


--
-- TOC entry 2151 (class 2606 OID 18497)
-- Name: user_role_access unique_access_user; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role_access
    ADD CONSTRAINT unique_access_user UNIQUE (user_system_id, role_access_id);


--
-- TOC entry 2153 (class 2606 OID 18493)
-- Name: user_system user_system_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_system
    ADD CONSTRAINT user_system_pkey PRIMARY KEY (id);


--
-- TOC entry 2171 (class 2620 OID 18588)
-- Name: address validatepersonkey_address_person_insert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkey_address_person_insert BEFORE INSERT ON public.address FOR EACH ROW EXECUTE PROCEDURE public.validatepersonkey();


--
-- TOC entry 2170 (class 2620 OID 18587)
-- Name: address validatepersonkey_address_person_update; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkey_address_person_update BEFORE UPDATE ON public.address FOR EACH ROW EXECUTE PROCEDURE public.validatepersonkey();


--
-- TOC entry 2173 (class 2620 OID 18594)
-- Name: product_sales_ecommerce validatepersonkey_productsalesecommerce_person_insert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkey_productsalesecommerce_person_insert BEFORE INSERT ON public.product_sales_ecommerce FOR EACH ROW EXECUTE PROCEDURE public.validatepersonkey();


--
-- TOC entry 2172 (class 2620 OID 18593)
-- Name: product_sales_ecommerce validatepersonkey_productsalesecommerce_person_update; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkey_productsalesecommerce_person_update BEFORE UPDATE ON public.product_sales_ecommerce FOR EACH ROW EXECUTE PROCEDURE public.validatepersonkey();


--
-- TOC entry 2175 (class 2620 OID 18590)
-- Name: purchase_invoice validatepersonkey_purchaseinvoice_person_insert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkey_purchaseinvoice_person_insert BEFORE INSERT ON public.purchase_invoice FOR EACH ROW EXECUTE PROCEDURE public.validatepersonkey();


--
-- TOC entry 2174 (class 2620 OID 18589)
-- Name: purchase_invoice validatepersonkey_purchaseinvoice_person_update; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkey_purchaseinvoice_person_update BEFORE UPDATE ON public.purchase_invoice FOR EACH ROW EXECUTE PROCEDURE public.validatepersonkey();


--
-- TOC entry 2177 (class 2620 OID 18582)
-- Name: trade_payable validatepersonkey_tradepayable_person_insert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkey_tradepayable_person_insert BEFORE INSERT ON public.trade_payable FOR EACH ROW EXECUTE PROCEDURE public.validatepersonkey();


--
-- TOC entry 2176 (class 2620 OID 18581)
-- Name: trade_payable validatepersonkey_tradepayable_person_update; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkey_tradepayable_person_update BEFORE UPDATE ON public.trade_payable FOR EACH ROW EXECUTE PROCEDURE public.validatepersonkey();


--
-- TOC entry 2179 (class 2620 OID 18584)
-- Name: trade_payable validatepersonkey_tradepayable_personprovider_insert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkey_tradepayable_personprovider_insert BEFORE INSERT ON public.trade_payable FOR EACH ROW EXECUTE PROCEDURE public.validatepersonproviderkey();


--
-- TOC entry 2178 (class 2620 OID 18583)
-- Name: trade_payable validatepersonkey_tradepayable_personprovider_update; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkey_tradepayable_personprovider_update BEFORE UPDATE ON public.trade_payable FOR EACH ROW EXECUTE PROCEDURE public.validatepersonproviderkey();


--
-- TOC entry 2181 (class 2620 OID 18586)
-- Name: trade_receivable validatepersonkey_tradereceivable_person_insert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkey_tradereceivable_person_insert BEFORE INSERT ON public.trade_receivable FOR EACH ROW EXECUTE PROCEDURE public.validatepersonkey();


--
-- TOC entry 2180 (class 2620 OID 18585)
-- Name: trade_receivable validatepersonkey_tradereceivable_person_update; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkey_tradereceivable_person_update BEFORE UPDATE ON public.trade_receivable FOR EACH ROW EXECUTE PROCEDURE public.validatepersonkey();


--
-- TOC entry 2183 (class 2620 OID 18592)
-- Name: user_system validatepersonkey_usersystem_person_insert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkey_usersystem_person_insert BEFORE INSERT ON public.user_system FOR EACH ROW EXECUTE PROCEDURE public.validatepersonkey();


--
-- TOC entry 2182 (class 2620 OID 18591)
-- Name: user_system validatepersonkey_usersystem_person_update; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkey_usersystem_person_update BEFORE UPDATE ON public.user_system FOR EACH ROW EXECUTE PROCEDURE public.validatepersonkey();


--
-- TOC entry 2160 (class 2606 OID 18528)
-- Name: product_sales_ecommerce billing_address_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_sales_ecommerce
    ADD CONSTRAINT billing_address_fk FOREIGN KEY (billing_address_id) REFERENCES public.address(id);


--
-- TOC entry 2161 (class 2606 OID 18533)
-- Name: product_sales_ecommerce discount_coupon_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_sales_ecommerce
    ADD CONSTRAINT discount_coupon_fk FOREIGN KEY (discount_coupon_id) REFERENCES public.discount_coupon(id);


--
-- TOC entry 2162 (class 2606 OID 18538)
-- Name: product_sales_ecommerce payment_method_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_sales_ecommerce
    ADD CONSTRAINT payment_method_fk FOREIGN KEY (payment_method_id) REFERENCES public.payment_method(id);


--
-- TOC entry 2154 (class 2606 OID 18498)
-- Name: invoice_item_product product_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice_item_product
    ADD CONSTRAINT product_fk FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- TOC entry 2156 (class 2606 OID 18508)
-- Name: item_sale_ecommerce product_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_sale_ecommerce
    ADD CONSTRAINT product_fk FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- TOC entry 2158 (class 2606 OID 18518)
-- Name: product_evaluation product_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_evaluation
    ADD CONSTRAINT product_fk FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- TOC entry 2159 (class 2606 OID 18523)
-- Name: product_image product_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_image
    ADD CONSTRAINT product_fk FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- TOC entry 2157 (class 2606 OID 18513)
-- Name: item_sale_ecommerce product_sales_ecommerce_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_sale_ecommerce
    ADD CONSTRAINT product_sales_ecommerce_fk FOREIGN KEY (product_sales_ecommerce_id) REFERENCES public.product_sales_ecommerce(id);


--
-- TOC entry 2166 (class 2606 OID 18558)
-- Name: sales_invoice product_sales_ecommerce_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sales_invoice
    ADD CONSTRAINT product_sales_ecommerce_fk FOREIGN KEY (product_sales_ecommerce_id) REFERENCES public.product_sales_ecommerce(id);


--
-- TOC entry 2167 (class 2606 OID 18563)
-- Name: tracking_status product_sales_ecommerce_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tracking_status
    ADD CONSTRAINT product_sales_ecommerce_fk FOREIGN KEY (product_sales_ecommerce_id) REFERENCES public.product_sales_ecommerce(id);


--
-- TOC entry 2155 (class 2606 OID 18503)
-- Name: invoice_item_product purchase_invoice_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice_item_product
    ADD CONSTRAINT purchase_invoice_fk FOREIGN KEY (purchase_invoice_id) REFERENCES public.purchase_invoice(id);


--
-- TOC entry 2168 (class 2606 OID 18568)
-- Name: user_role_access role_access_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role_access
    ADD CONSTRAINT role_access_fk FOREIGN KEY (role_access_id) REFERENCES public.role_access(id);


--
-- TOC entry 2163 (class 2606 OID 18543)
-- Name: product_sales_ecommerce sales_invoice_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_sales_ecommerce
    ADD CONSTRAINT sales_invoice_fk FOREIGN KEY (sales_invoice_id) REFERENCES public.sales_invoice(id);


--
-- TOC entry 2164 (class 2606 OID 18548)
-- Name: product_sales_ecommerce shipping_address_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_sales_ecommerce
    ADD CONSTRAINT shipping_address_fk FOREIGN KEY (shipping_address_id) REFERENCES public.address(id);


--
-- TOC entry 2165 (class 2606 OID 18553)
-- Name: purchase_invoice trade_payable_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_invoice
    ADD CONSTRAINT trade_payable_fk FOREIGN KEY (trade_payable_id) REFERENCES public.trade_payable(id);


--
-- TOC entry 2169 (class 2606 OID 18573)
-- Name: user_role_access user_system_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role_access
    ADD CONSTRAINT user_system_fk FOREIGN KEY (user_system_id) REFERENCES public.user_system(id);


--
-- TOC entry 2342 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2022-02-12 12:23:51 AST

--
-- PostgreSQL database dump complete
--
