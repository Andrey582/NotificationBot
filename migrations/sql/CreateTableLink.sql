--changeSet Ganushkin-Andrey:08-03-2024-create-table-link

create table link
(
    id bigint generated always as identity,
    link_url text not null,
    last_check_time timestamp with time zone not null,
    primary key (id),
    unique (link_url)
)
