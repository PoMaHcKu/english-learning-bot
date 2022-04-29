create or replace procedure divide_words()
    language plpgsql
as
$$
declare
    group_start   bigint;
    group_end     bigint;
    group_id      bigint;
    is_exist_data boolean := true;
    ids           bigint[];
begin
    group_start := 1;
    group_end := 75;
    group_id := 1;

    while is_exist_data
        loop
            is_exist_data :=
                        (select count(id)
                         from bot_data.public.word_relation
                         where id >= group_start
                           and id < group_end) > 0;
            if is_exist_data then
                ids := (select array(select id
                                     from bot_data.public.word_relation
                                     where id >= group_start
                                       and id < group_end));
                call create_word_groups(ids, group_id);
                raise notice 'Group: %, from % to %', group_id, group_start, group_end;
                raise notice 'Ids: %', ids;
                group_id := group_id + 1;
                group_start := group_end;
                group_end := group_end + 75;
            end if;
        end loop;
end;
$$;