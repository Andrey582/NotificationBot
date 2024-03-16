insert into chat (chat_id)
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
    (1, 1, 'test'),
    (1, 2, 'secondtest'),
    (1, 3, 'thirdtest'),
    (2, 2, 'secondtest'),
    (2, 3, null)

