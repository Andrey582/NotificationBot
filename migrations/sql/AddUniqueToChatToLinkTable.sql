alter table chat_to_link add unique (chat_id, link_id);
alter table chat_to_link add unique (chat_id, name);
