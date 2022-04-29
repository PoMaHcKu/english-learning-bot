create or replace procedure create_word_groups(bigint[], group_id bigint)
    language plpgsql
as
$$
declare
    is_group_not_exists boolean;
    cur_word_id                   bigint;
begin
    is_group_not_exists := (select id from words_group where id = group_id) IS NULL;
    if is_group_not_exists then
        raise notice 'group will be created: %', group_id;
        insert into words_group (id, title) values (group_id, concat('Group - ', group_id));
    end if;
    foreach cur_word_id in array $1
        loop
            raise notice 'cur_id: %, group_id: %', cur_word_id, group_id;
            insert into words_group_words (word_id, words_group_id) values (cur_word_id, group_id);
        end loop;
end;
$$;