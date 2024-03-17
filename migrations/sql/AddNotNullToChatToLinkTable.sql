--changeSet Ganushkin-Andrey:13-03-2024-add-not-null-to-chat-to-link-table

alter table chat_to_link alter column chat_id set not null;
alter table chat_to_link alter column link_id set not null;
