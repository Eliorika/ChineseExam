CREATE OR REPLACE FUNCTION get_answers_count_last_7_days(in_user_id bigint, in_course_id bigint)
    RETURNS bigint AS $$
DECLARE
    count_result bigint;
BEGIN
    SELECT COUNT(*) INTO count_result
    FROM answers_statistics join assessment_materials on answers_statistics.question_id = assessment_materials.id
                            join  tests on assessment_materials.test_id = tests.id
                            join lessons on tests.lesson_id = lessons.id
    WHERE user_id = in_user_id
      AND response_date_time >= CURRENT_TIMESTAMP - INTERVAL '7 days'
      AND course_id = in_course_id;

    RETURN count_result;
END;
$$ LANGUAGE plpgsql;

GO

CREATE OR REPLACE FUNCTION get_average_correctness_last_7_days(in_user_id bigint, in_course_id bigint)
    RETURNS numeric AS $$
DECLARE
    avg_result numeric;
BEGIN
    SELECT AVG(CAST(correctness AS INT)) * 100 INTO avg_result
    FROM answers_statistics join assessment_materials on answers_statistics.question_id = assessment_materials.id
                            join  tests on assessment_materials.test_id = tests.id
                            join lessons on tests.lesson_id = lessons.id
    WHERE user_id = in_user_id
      AND response_date_time >= CURRENT_TIMESTAMP - INTERVAL '7 days'
      AND course_id = in_course_id;

    RETURN avg_result;
END;
$$ LANGUAGE plpgsql;


GO


CREATE OR REPLACE FUNCTION get_answers_count_last_7_days_per_question(in_user_id bigint, in_question_id bigint)
    RETURNS bigint AS $$
DECLARE
    count_result bigint;
BEGIN
    SELECT COUNT(*) INTO count_result
    FROM answers_statistics
    WHERE user_id = in_user_id
      AND response_date_time >= CURRENT_TIMESTAMP - INTERVAL '7 days'
      AND answers_statistics.question_id = in_question_id;

    RETURN count_result;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION get_answers_count_last_7_days_per_question(in_user_id bigint, in_question_id bigint)
    RETURNS bigint AS $$
DECLARE
    count_result bigint;
BEGIN
    SELECT COUNT(*) INTO count_result
    FROM answers_statistics
    WHERE user_id = in_user_id
      AND response_date_time >= CURRENT_TIMESTAMP - INTERVAL '7 days'
      AND answers_statistics.question_id = in_question_id;

    RETURN count_result;
END;
$$ LANGUAGE plpgsql;

go

CREATE OR REPLACE FUNCTION get_resource_materials_for_user(in_user_id integer, in_course_id integer)
    RETURNS TABLE(material_name text)
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        WITH stats AS (
            SELECT question_id, COUNT(*) AS incorrect_answers_count
            FROM answers_statistics
                     JOIN assessment_materials ON answers_statistics.question_id = assessment_materials.id
                     JOIN tests ON assessment_materials.test_id = tests.id
                     JOIN lessons ON tests.lesson_id = lessons.id
            WHERE answers_statistics.user_id = in_user_id
              AND response_date_time >= CURRENT_TIMESTAMP - INTERVAL '1 month'
              AND correctness = FALSE
              AND lessons.course_id = in_course_id
            GROUP BY question_id
            HAVING COUNT(*) > (SELECT * FROM get_answers_count_last_7_days_per_question(in_user_id, question_id))/4
        )
        SELECT DISTINCT ON (resource_materials.material_name) resource_materials.material_name::text
        FROM resource_materials
                 JOIN assessment_reference_table ON resource_materials.id = assessment_reference_table.resource_id
        WHERE assessment_reference_table.assessment_id IN (SELECT question_id FROM stats)
        ORDER BY resource_materials.material_name;
END;
$$;

ALTER FUNCTION get_resource_materials_for_user(integer, integer) OWNER TO arina;
