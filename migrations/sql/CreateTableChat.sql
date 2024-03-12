create table chat
(
    id bigint generated always as identity,
    chat_id bigint not null,
    primary key (id),
    unique (chat_id)
)
