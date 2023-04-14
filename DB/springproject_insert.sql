
-- -----------------------------------------------------
-- Table `springproject`.`promotion`
-- -----------------------------------------------------
ALTER TABLE `springproject`.`promotion` AUTO_INCREMENT = 1;

INSERT INTO `springproject`.`promotion` (`name`, `startDate`, `endDate`, `type`) 
VALUES 
('Chrismas Sale', '2023-04-01', '2023-07-15', 'SITEWIDE_DISCOUNT_10'),
('Easter Sale', '2023-04-01', '2023-07-15', 'SITEWIDE_DISCOUNT_20'),
('New Customer Promotion', '2023-06-01', '2023-06-30', 'Free Shipping'),
('Black Friday','2023-07-01', '2023-07-15', 'Buy One Get One Free');

-- -----------------------------------------------------
-- Table `springproject`.`user`
-- -----------------------------------------------------
ALTER TABLE `springproject`.`user` AUTO_INCREMENT = 1;

INSERT INTO `springproject`.`user` (`username`, `password`, `role`, `email`, `address`, `phone`)
VALUES ('jay', '123', 'CUSTOMER', 'john123@gmail.com', '123 Main St, Anytown, USA', '+1 (555) 123-4567');

INSERT INTO `springproject`.`user` (`username`, `password`, `role`, `email`, `address`, `phone`)
VALUES ('admin', '123', 'ADMIN', 'jane456@gmail.com', '456 Elm St, Anytown, USA', '+1 (555) 234-5678');


-- -----------------------------------------------------
-- Table `springproject`.`purchase`
-- -----------------------------------------------------
ALTER TABLE `springproject`.`purchase` AUTO_INCREMENT = 1;

INSERT INTO `springproject`.`purchase` (`userId`, `timeStamp`, `total_price`, `promotionId`)
VALUES (1, '2023-04-08 10:00:00', 50.00, 1);

INSERT INTO `springproject`.`purchase` (`userId`, `timeStamp`, `total_price`, `promotionId`)
VALUES (1, '2023-04-09 11:00:00', 75.00, 2);

INSERT INTO `springproject`.`purchase` (`userId`, `timeStamp`, `total_price`, `promotionId`)
VALUES (1, '2023-04-10 12:00:00', 100.00, NULL);


-- -----------------------------------------------------
-- Table `springproject`.`category`
-- -----------------------------------------------------
ALTER TABLE `springproject`.`category` AUTO_INCREMENT = 1;

INSERT INTO `springproject`.`category` (`name`) VALUES ('Appetizers');
INSERT INTO `springproject`.`category` (`name`) VALUES ('Entrees');
INSERT INTO `springproject`.`category` (`name`) VALUES ('Desserts');
INSERT INTO `springproject`.`category` (`name`) VALUES ('Beverages');
INSERT INTO `springproject`.`category` (`name`) VALUES ('Specials');



-- -----------------------------------------------------
-- Table `springproject`.`product`
-- -----------------------------------------------------
ALTER TABLE `springproject`.`product` AUTO_INCREMENT = 1;

INSERT INTO `springproject`.`product` (`name`, `description`, `imagePath`, `categoryid`, `price`, `salesCount`, `isOnSale`, `discountPercent`) 
VALUES ('Cheeseburger', 'A juicy beef patty with melted cheese on a soft bun.', 'https://example.com/images/cheeseburger.jpg', 1, 9.99, 50, 0, NULL);

INSERT INTO `springproject`.`product` (`name`, `description`, `imagePath`, `categoryid`, `price`, `salesCount`, `isOnSale`, `discountPercent`) 
VALUES ('Pepperoni Pizza', 'Classic pizza with tomato sauce, mozzarella cheese, and pepperoni.', 'https://example.com/images/pepperoni_pizza.jpg', 1, 12.99, 35, 1, 0.1);

INSERT INTO `springproject`.`product` (`name`, `description`, `imagePath`, `categoryid`, `price`, `salesCount`, `isOnSale`, `discountPercent`) 
VALUES ('Fried Chicken Sandwich', 'Crispy fried chicken on a toasted bun with lettuce and mayo.', 'https://example.com/images/fried_chicken_sandwich.jpg', 2, 8.99, 20, 0, NULL);

INSERT INTO `springproject`.`product` (`name`, `description`, `imagePath`, `categoryid`, `price`, `salesCount`, `isOnSale`, `discountPercent`) 
VALUES ('Spicy Tuna Roll', 'Sushi roll filled with spicy tuna, avocado, and cucumber.', 'https://example.com/images/spicy_tuna_roll.jpg', 3, 6.99, 15, 1, 0.5);


-- -----------------------------------------------------
-- Table `springproject`.`purchase_details`
-- -----------------------------------------------------
ALTER TABLE `springproject`.`purchase_details` AUTO_INCREMENT = 1;

INSERT INTO `springproject`.`purchase_details` (`purchaseId`, `productId`, `quantity`, `price`, `isOnSale`, `discountPercent`) 
VALUES (1, 2, 3, 10.50, 1, 0.2),
       (1, 3, 1, 25.00, 0, null),
       (2, 1, 2, 7.99, 0, null),
       (3, 4, 1, 50.00, 1, 0.5); 

-- -----------------------------------------------------
-- Table `springproject`.`favorite`
-- -----------------------------------------------------
ALTER TABLE `springproject`.`favorite` AUTO_INCREMENT = 1;

INSERT INTO `springproject`.`favorite` (`userId`, `productId`) 
VALUES (1, 2),
       (1, 3),
       (1, 1),
       (1, 4); 


-- -----------------------------------------------------
-- Table `springproject`.`rating`
-- -----------------------------------------------------
ALTER TABLE `springproject`.`rating` AUTO_INCREMENT = 1;

INSERT INTO `springproject`.`rating` (`userId`, `productId`, `rating`) 
VALUES (1, 2, 4),
       (1, 3, 3),
       (1, 1, 5),
       (1, 4, 2); 

-- -----------------------------------------------------
-- Table `springproject`.`search_history`
-- -----------------------------------------------------
ALTER TABLE `springproject`.`search_history` AUTO_INCREMENT = 1;

INSERT INTO `springproject`.`search_history` (`userId`, `phrase`, `timeStamp`) 
VALUES (1, 'burger', '2022-04-07 15:30:00'),
       (1, 'fries', '2022-04-07 16:45:00'),
       (1, 'pizza', '2022-04-07 12:00:00'),
       (1, 'sandwich', '2022-04-07 14:15:00'); 
