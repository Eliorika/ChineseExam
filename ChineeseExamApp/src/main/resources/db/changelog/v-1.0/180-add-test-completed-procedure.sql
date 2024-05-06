CREATE OR REPLACE FUNCTION check_answer_statistic(input_test_id bigint, input_user_id bigint)
    RETURNS BOOLEAN AS $$
DECLARE
    exists_statistic BOOLEAN;
BEGIN
    SELECT EXISTS (
        SELECT 1
        FROM answers_statistics
        WHERE answers_statistics.question_id IN (
            SELECT id
            FROM assessment_materials
            WHERE test_id = input_test_id
        )
          AND user_id = input_user_id
    )
    INTO exists_statistic;

    RETURN exists_statistic;
END;
$$ LANGUAGE plpgsql;
