CREATE TABLE sections (
    id integer NOT NULL,
    page_id integer,
    name character varying(255),
    "position" integer,
    visible boolean DEFAULT false,
    content_type character varying(255),
    content text,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    updatecount character varying(255),
    yardoc boolean DEFAULT false
);
