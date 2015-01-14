CREATE TABLE `tais_food_exp_settlement` (
  `settlement_id` varchar(254) NOT NULL,
  `user_id` varchar(45) DEFAULT NULL,
  `food_allowance_funds` decimal(28,20) DEFAULT NULL,
  `last_settlement_date` date DEFAULT NULL,
  `remarks` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`settlement_id`)
) 

CREATE TABLE `tais_food_exp_users` (
  `settlement_id` varchar(254) NOT NULL,
  `sequence_id` int(11) NOT NULL,
  `food_exp_user_id` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`settlement_id`,`sequence_id`),
  KEY `tais_food_exp_users_settlement_idx` (`settlement_id`,`sequence_id`)
) 

CREATE TABLE `tais_food_expenditure` (
  `id` varchar(254) NOT NULL,
  `expense_date` datetime DEFAULT NULL,
  `expense_amount` decimal(28,20) DEFAULT NULL,
  `expense_status` int(11) DEFAULT NULL,
  `description` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`id`)
) 

CREATE TABLE `tais_food_expense` (
  `user_id` varchar(254) NOT NULL,
  `sequence_id` int(11) NOT NULL,
  `food_exp_item` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`sequence_id`),
  KEY `tais_food_expense_user_idx` (`user_id`,`sequence_id`)
) 

CREATE TABLE `tais_user` (
  `user_id` varchar(254) NOT NULL,
  `employee_code` varchar(254) DEFAULT NULL,
  `marital_status` int(11) DEFAULT NULL,
  `personal_phone` varchar(254) DEFAULT NULL,
  `home_phone` varchar(254) DEFAULT NULL,
  `job_location` int(11) DEFAULT NULL,
  `designation` int(11) DEFAULT NULL,
  `profile_photo_url` varchar(800) DEFAULT NULL,
  `account_status` int(11) DEFAULT NULL,
  `iseligible_foodexpense` tinyint(4) DEFAULT NULL,
  `is_admin` tinyint(4) DEFAULT NULL,
  `food_exp_start_date` datetime DEFAULT NULL,
  `expense_status` tinyint(15) DEFAULT NULL,
  `settled_expense_amount` decimal(28,20) DEFAULT NULL,
  `food_exp_end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `tais_user_user_idx` (`user_id`)
) 
CREATE TABLE tais_food_exp_settlement (
	settlement_id 		VARCHAR(254)	NOT NULL,
	food_allowance_funds 	DECIMAL(28, 20)	NULL,
	last_settlement_date 	DATE	NULL,
	remarks 		VARCHAR(254)	NULL,
	PRIMARY KEY(settlement_id)
);

CREATE TABLE tais_food_exp_users (
	settlement_id 		VARCHAR(254)	NOT NULL REFERENCES tais_food_exp_settlement(settlement_id),
	sequence_id 		INT	NOT NULL,
	food_exp_user_id 	VARCHAR(254)	NULL REFERENCES dps_user(id),
	PRIMARY KEY(settlement_id, sequence_id)
);

CREATE INDEX tais_food_exp_users_settlement_idx ON tais_food_exp_users(settlement_id, sequence_id);