create or replace FUNCTION get_translation(answer_id_param bigint)
    RETURNS TABLE(id bigint, variant character varying)
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT a2.id, CAST(a2.variant AS character varying)
        FROM
            dictionary AS d
                JOIN answers_variant AS a1 ON d.variant_id = a1.id
                JOIN answers_variant AS a2 ON d.variant_translate = a2.id
        WHERE
                a1.id = answer_id_param;

    IF NOT FOUND THEN
        RETURN QUERY
            SELECT a1.id, CAST(a1.variant AS character varying)
            FROM
                dictionary AS d
                    JOIN answers_variant AS a1 ON d.variant_id = a1.id
                    JOIN answers_variant AS a2 ON d.variant_translate = a2.id
            WHERE
                    a2.id = answer_id_param;
    END IF;
END;
$$;