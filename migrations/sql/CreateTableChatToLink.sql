create table chat_to_link
(
    chat_id bigint,
    link_id bigint,
    foreign key (chat_id) references chat (id),
    foreign key (link_id) references link (id)
)
