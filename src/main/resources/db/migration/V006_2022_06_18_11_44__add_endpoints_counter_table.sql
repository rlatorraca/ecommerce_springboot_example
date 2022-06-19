CREATE TABLE public.endpoint_access_counter (
    endpoint_name character varying(255) NOT NULL,
    quantity_access bigint NOT NULL
);

INSERT INTO public.endpoint_access_counter (endpoint_name,quantity_access) VALUES ('queryLegalPersonByBusinessNumber', 0);
INSERT INTO public.endpoint_access_counter (endpoint_name,quantity_access) VALUES ('queryLegalPersonByProvincialRegistration', 0);
INSERT INTO public.endpoint_access_counter (endpoint_name,quantity_access) VALUES ('queryLegalPersonByName', 0);
INSERT INTO public.endpoint_access_counter (endpoint_name,quantity_access) VALUES ('saveNaturalPerson', 0);
INSERT INTO public.endpoint_access_counter (endpoint_name,quantity_access) VALUES ('saveLegalPerson', 0);
INSERT INTO public.endpoint_access_counter (endpoint_name,quantity_access) VALUES ('queryPostalCode', 0);
INSERT INTO public.endpoint_access_counter (endpoint_name,quantity_access) VALUES ('queryNaturalPersonByName', 0);
INSERT INTO public.endpoint_access_counter (endpoint_name,quantity_access) VALUES ('queryNaturalPersonBySinNumber', 0);
INSERT INTO public.endpoint_access_counter (endpoint_name,quantity_access) VALUES ('queryBusinessNumberReceitaFederal', 0);

ALTER TABLE public.endpoint_access_counter ADD CONSTRAINT endpoint_name_unique UNIQUE (endpoint_name);