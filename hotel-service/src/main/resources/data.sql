INSERT INTO hotel (name, address, star_rating, created_at, updated_at)
VALUES ('Marriott', '123 Street, City', 4, NOW(), NOW()),
       ('Hilton', '456 Avenue, City', 5, NOW(), NOW());

INSERT INTO room (room_number, capacity, price_per_night, hotel_id, created_at, updated_at)
VALUES ('101', 2, 120.00, 1, NOW(), NOW()),
       ('102', 2, 150.00, 1, NOW(), NOW()),
       ('201', 3, 200.00, 2, NOW(), NOW());