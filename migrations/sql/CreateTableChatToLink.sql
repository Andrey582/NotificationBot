--changeSet Ganushkin-Andrey:08-03-2024-create-table-chat-to-link

create table chat_to_link
(
    chat_id bigint not null,
    link_id bigint not null,
    name text,
    foreign key (chat_id) references chat (id),
    foreign key (link_id) references link (id),
    unique (chat_id, link_id),
    unique (chat_id, name)
)
