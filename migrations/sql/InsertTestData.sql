--changeSet Ganushkin-Andrey:13-03-2024-insert-test-data

insert into chat (id)
values
    (123),
    (234),
    (345);

insert into link (link_url, last_check_time)
values
    ('http://test.com', now()),
    ('http://secondtest.com', now()),
    ('http://thirdtest.com', now()),
    ('http://deletetest.com', now());

insert into chat_to_link (chat_id, link_id, name)
values
    (123, 1, 'test'),
    (123, 2, 'secondtest'),
    (123, 3, 'thirdtest'),
    (234, 2, 'secondtest'),
    (234, 3, null)

